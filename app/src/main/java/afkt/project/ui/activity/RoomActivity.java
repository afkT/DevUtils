package afkt.project.ui.activity;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.database.room.RoomManager;
import afkt.project.database.room.module.note.bean.Note;
import afkt.project.database.room.module.note.bean.NoteAndPicture;
import afkt.project.database.room.module.note.bean.NotePicture;
import afkt.project.database.room.module.note.bean.NoteType;
import afkt.project.databinding.ActivityDatabaseBinding;
import afkt.project.ui.adapter.RoomAdapter;
import dev.assist.PageAssist;
import dev.engine.log.DevLogEngine;
import dev.utils.app.toast.ToastTintUtils;
import dev.utils.common.ChineseUtils;
import dev.utils.common.CollectionUtils;
import dev.utils.common.RandomUtils;

/**
 * detail: Room 使用
 * @author Ttt
 */
public class RoomActivity
        extends BaseActivity<ActivityDatabaseBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.activity_database;
    }

    @Override
    public void initValue() {
        super.initValue();

        ToastTintUtils.info("侧滑可进行删除, 长按拖动位置");

        // 初始化布局管理器、适配器
        binding.vidAdbRefresh.setAdapter(new RoomAdapter())
                .setPageAssist(new PageAssist<>(0, 8));
        // 加载数据
        loadData(true);
    }

    @Override
    public void initListener() {
        super.initListener();
        // 刷新事件
        binding.vidAdbRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadData(true);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                loadData(false);
            }
        });

        // ================
        // = Item 滑动处理 =
        // ================

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            /**
             * 获取动作标识
             * 动作标识分 : dragFlags 和 swipeFlags
             * dragFlags : 列表滚动方向的动作标识 ( 如竖直列表就是上和下, 水平列表就是左和右 )
             * wipeFlags : 与列表滚动方向垂直的动作标识 ( 如竖直列表就是左和右, 水平列表就是上和下 )
             */
            @Override
            public int getMovementFlags(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder
            ) {
                // 如果你不想上下拖动, 可以将 dragFlags = 0
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

                // 如果你不想左右滑动, 可以将 swipeFlags = 0
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

                // 最终的动作标识 ( flags ) 必须要用 makeMovementFlags() 方法生成
                int flags = makeMovementFlags(dragFlags, swipeFlags);
                return flags;
            }

            /**
             * 是否开启 item 长按拖拽功能
             */
            @Override
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target
            ) {
                RoomAdapter adapter      = binding.vidAdbRefresh.getAdapter();
                int         fromPosition = viewHolder.getAdapterPosition();
                int         toPosition   = target.getAdapterPosition();
                Collections.swap(adapter.getData(), fromPosition, toPosition);
                adapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            /**
             * 当 item 侧滑出去时触发 ( 竖直列表是侧滑, 水平列表是竖滑 )
             * @param viewHolder
             * @param direction 滑动的方向
             */
            @Override
            public void onSwiped(
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    int direction
            ) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                    RoomAdapter    adapter = binding.vidAdbRefresh.getAdapter();
                    NoteAndPicture nap     = adapter.getData().remove(position);
                    adapter.notifyItemRemoved(position);
                    // 删除文章
                    RoomManager.getNoteDatabase().getNoteDao().deleteNote(nap.note);
                    // 删除图片
                    if (CollectionUtils.isNotEmpty(nap.pictures)) {
                        int deleteCount = RoomManager.getNoteDatabase().getNoteDao()
                                .deleteNotePictures(
                                        CollectionUtils.toArrayT(nap.pictures)
                                );
                        DevLogEngine.getEngine().dTag(TAG, "删除图片数量: %s", deleteCount);
                    }
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(binding.vidAdbRefresh.getRecyclerView());

        binding.vidAdbAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int addNumber; // 添加数据量
                if (binding.vidAdbRefresh.getAdapter().getData().isEmpty()) { // 不存在数据
                    randomData(addNumber = 13);
                    // 加载数据
                    loadData(true);
                } else {
                    randomData(addNumber = RandomUtils.getRandom(2, 6));
                }
                // 进行提示
                ToastTintUtils.success(String.format("添加 %s 条数据成功, 上拉加载数据", addNumber));
            }
        });
    }

    // ===========
    // = 数据相关 =
    // ===========

    /**
     * 随机添加数据
     */
    private void randomData() {
        Note note = new Note();
        note.date = new Date();
        note.text = ChineseUtils.randomWord(RandomUtils.getRandom(6, 15));
        note.comment = ChineseUtils.randomWord(RandomUtils.getRandom(12, 50));
        note.type = NoteType.values()[RandomUtils.getRandom(0, 3)];
        // 添加数据
        Long noteId = RoomManager.getNoteDatabase().getNoteDao().insertNote(note);
        // 不等于文本
        if (note.type != NoteType.TEXT) {
            List<NotePicture> pictures = new ArrayList<>();
            for (int i = 0, len = RandomUtils.getRandom(1, 5); i < len; i++) {
                String pictureUrl = String.format("https://picsum.photos/id/%s/30%s", RandomUtils.getRandom(5, 21), RandomUtils.getRandom(0, 10));
                // 创建 NotePicture
                NotePicture notePicture = new NotePicture(noteId, pictureUrl);
                pictures.add(notePicture);
            }
            RoomManager.getNoteDatabase().getNoteDao().insertNotePictures(pictures);
        }
    }

    /**
     * 随机添加数据
     * @param number 随机数量
     */
    private void randomData(int number) {
        for (int i = 0; i < number; i++) {
            randomData();
        }
    }

    // =

    /**
     * 加载数据
     * @param refresh 是否刷新
     */
    private void loadData(boolean refresh) {
        PageAssist  pageAssist = binding.vidAdbRefresh.getPageAssist();
        RoomAdapter adapter    = binding.vidAdbRefresh.getAdapter();
        // 刷新则重置页数
        if (refresh) pageAssist.reset();

        List<NoteAndPicture> notes = offsetLimitCalculate(refresh);

        // 存在数据则累加页数
        if (!notes.isEmpty()) pageAssist.nextPage();
        if (!refresh && notes.isEmpty()) ToastTintUtils.normal("已加载至最后一页啦");

        if (refresh) {
            adapter.setNewInstance(notes);
        } else {
            adapter.addData(notes);
            adapter.notifyDataSetChanged();
        }

        // 结束刷新、加载
        binding.vidAdbRefresh.finishRefreshOrLoad(refresh);
    }

    /**
     * 页数偏移值计算处理
     * <pre>
     *     为什么需要特殊计算 :
     *     正常到最后一页没有数据是禁止加载更多
     *     为了演示分页实现功能, 显示添加数据按钮并且不限制加载更多功能
     *     可能导致新增数据 + 原有数据刚好 = 页数 * 每页条数, 导致无法加载下一页
     * </pre>
     * @param refresh 是否刷新
     */
    private List<NoteAndPicture> offsetLimitCalculate(boolean refresh) {
        int offset, limit;

        int pageSize = binding.vidAdbRefresh.getPageAssist().getPageSize();

        if (refresh) {
            offset = 0;
            limit = pageSize;
        } else {
            // 获取当前数据条数
            int size = binding.vidAdbRefresh.getAdapter().getData().size();
            // 计算当前数据实际页数
            int page      = size / pageSize;
            int remainder = size % pageSize;

            if (remainder == 0) {
                offset = page * pageSize;
                limit = pageSize;
            } else {
                int diff = Math.abs(page * pageSize - size);
                offset = size;
                limit = pageSize * 2 - diff;
            }
        }
        DevLogEngine.getEngine().dTag(TAG, "offset: %s, limit: %s", offset, limit);
        // 请求数据
        return RoomManager.getNoteDatabase().getNoteDao()
                .getNoteAndPictureLists(limit, offset);
    }
}