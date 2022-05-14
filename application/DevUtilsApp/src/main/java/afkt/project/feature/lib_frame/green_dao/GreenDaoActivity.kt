package afkt.project.feature.lib_frame.green_dao

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.database.green.GreenManager
import afkt.project.database.green.module.note.bean.Note
import afkt.project.database.green.module.note.bean.NotePicture
import afkt.project.database.green.module.note.bean.NoteType
import afkt.project.databinding.ActivityDatabaseBinding
import afkt.project.model.item.RouterPath
import afkt_replace.core.lib.utils.log.log_dTag
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.ChineseUtils
import dev.utils.common.RandomUtils
import gen.greendao.NotePictureDao
import java.util.*
import kotlin.math.abs

/**
 * detail: GreenDao 使用
 * @author Ttt
 */
@Route(path = RouterPath.LIB_FRAME.GreenDaoActivity_PATH)
class GreenDaoActivity : BaseActivity<ActivityDatabaseBinding>() {

    val adapter = GreenDaoAdapter()

    override fun baseLayoutId(): Int = R.layout.activity_database

    override fun initValue() {
        super.initValue()
        ToastTintUtils.info("侧滑可进行删除, 长按拖动位置")

        // 初始化布局管理器、适配器
        binding.vidRefresh.setAdapter(adapter)
        // 加载数据
        loadData(true)
    }

    override fun initListener() {
        super.initListener()
        // 刷新事件
        binding.vidRefresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshlayout: RefreshLayout) {
                loadData(true)
            }

            override fun onLoadMore(refreshlayout: RefreshLayout) {
                loadData(false)
            }
        })

        // ===============
        // = Item 滑动处理 =
        // ===============

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            /**
             * 获取动作标识
             * 动作标识分 : dragFlags 和 swipeFlags
             * dragFlags : 列表滚动方向的动作标识 ( 如竖直列表就是上和下, 水平列表就是左和右 )
             * wipeFlags : 与列表滚动方向垂直的动作标识 ( 如竖直列表就是左和右, 水平列表就是上和下 )
             */
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                // 如果你不想上下拖动, 可以将 dragFlags = 0
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN

                // 如果你不想左右滑动, 可以将 swipeFlags = 0
                val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

                // 最终的动作标识 ( flags ) 必须要用 makeMovementFlags() 方法生成
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            /**
             * 是否开启 item 长按拖拽功能
             */
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition
                Collections.swap(adapter.dataList, fromPosition, toPosition)
                adapter.notifyItemMoved(fromPosition, toPosition)
                return true
            }

            /**
             * 当 item 侧滑出去时触发 ( 竖直列表是侧滑, 水平列表是竖滑 )
             * @param viewHolder
             * @param direction 滑动的方向
             */
            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val position = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                    val note = adapter.removeDataAt(position)
                    adapter.notifyItemRemoved(position)
                    // 删除文章
//                    GreenManager.getNoteDatabase().noteDao.delete(note)
                    GreenManager.getNoteDatabase().noteDao.deleteByKey(note.id)
                    // 删除文章图片
                    val deleteQuery = GreenManager.getNoteDatabase().notePictureDao
                        .queryBuilder()
                        .where(NotePictureDao.Properties.NoteId.eq(note.id))
                        .buildDelete()
                    deleteQuery.executeDeleteWithoutDetachingEntities()
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.vidRefresh.getRecyclerView())
        binding.vidAddBtn.setOnClickListener {
            var addNumber: Int // 添加数据量
            if (adapter.isDataEmpty) { // 不存在数据
                randomData(13.also { addNumber = it })
                // 加载数据
                loadData(true)
            } else {
                randomData(RandomUtils.getRandom(2, 6).also { addNumber = it })
            }
            // 进行提示
            ToastTintUtils.success(String.format("添加 %s 条数据成功, 上拉加载数据", addNumber))
        }
    }

    // ==========
    // = 数据相关 =
    // ==========

    /**
     * 随机添加数据
     */
    private fun randomData() {
        val note = Note()
        note.date = Date()
        note.text = ChineseUtils.randomWord(RandomUtils.getRandom(6, 15))
        note.comment = ChineseUtils.randomWord(RandomUtils.getRandom(12, 50))
        note.type = NoteType.values()[RandomUtils.getRandom(0, 3)]
        // 添加数据
        val noteId = GreenManager.getNoteDatabase().noteDao.insert(note)
        // 不等于文本
        if (note.type != NoteType.TEXT) {
            val pictures: MutableList<NotePicture> = ArrayList()
            for (i in 0 until RandomUtils.getRandom(1, 5)) {
                val notePicture = NotePicture()
                notePicture.noteId = noteId
                notePicture.picture = String.format(
                    "https://picsum.photos/id/%s/30%s",
                    RandomUtils.getRandom(5, 21),
                    RandomUtils.getRandom(0, 10)
                )
                pictures.add(notePicture)
            }
            GreenManager.getNoteDatabase().notePictureDao.insertInTx(pictures)
        }
    }

    /**
     * 随机添加数据
     * @param number 随机数量
     */
    private fun randomData(number: Int) {
        for (i in 0 until number) {
            randomData()
        }
    }

    /**
     * 加载数据
     * @param refresh 是否刷新
     */
    private fun loadData(refresh: Boolean) {
        // 刷新则重置页数
        if (refresh) adapter.page.reset()
        val notes = offsetLimitCalculate(refresh)

//        // 正常只需要这个, 没有添加功能则不需要计算偏差值
//        val notes = GreenManager.getNoteDatabase().noteDao
//            .queryBuilder()
//            .offset(adapter.page.page * adapter.page.pageSize)
//            .limit(adapter.page.pageSize).list()

        // 存在数据则累加页数
        if (notes.isNotEmpty()) adapter.page.nextPage()
        if (!refresh && notes.isEmpty()) ToastTintUtils.normal("已加载至最后一页啦")
        // 设置数据源
        adapter.addLists(!refresh, notes)
        // 结束刷新、加载
        binding.vidRefresh.finishRefreshOrLoad(refresh)
    }

    /**
     * 页数偏移值计算处理
     * @param refresh 是否刷新
     * 为什么需要特殊计算 :
     * 正常到最后一页没有数据是禁止加载更多
     * 为了演示分页实现功能, 显示添加数据按钮并且不限制加载更多功能
     * 可能导致新增数据 + 原有数据刚好 = 页数 * 每页条数, 导致无法加载下一页
     */
    private fun offsetLimitCalculate(refresh: Boolean): List<Note> {
        val offset: Int
        val limit: Int

        val pageSize = adapter.page.pageSize
        if (refresh) {
            offset = 0
            limit = pageSize
        } else {
            // 获取当前数据条数
            val size = adapter.dataSize
            // 计算当前数据实际页数
            val page = size / pageSize
            val remainder = size % pageSize
            if (remainder == 0) {
                offset = page * pageSize
                limit = pageSize
            } else {
                val diff = abs(page * pageSize - size)
                offset = size
                limit = pageSize * 2 - diff
            }
        }
        log_dTag(
            tag = TAG,
            message = "offset: $offset, limit: $limit"
        )
        // 请求数据
        return GreenManager.getNoteDatabase().noteDao
            .queryBuilder()
            .offset(offset)
            .limit(limit).list()
    }
}