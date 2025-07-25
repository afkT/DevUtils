package afkt.project.feature.ui_effect.capture_picture

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.ActivityCapturePictureRecyBinding
import afkt.project.databinding.AdapterCapturePictureBinding
import afkt.project.feature.ui_effect.capture_picture.AdapterBean.Companion.newAdapterBeanList
import afkt.project.model.helper.RandomHelper
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dev.adapter.DevDataAdapterExt
import dev.base.DevSource
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.engine.DevEngine
import dev.engine.storage.OnDevInsertListener
import dev.engine.storage.StorageItem
import dev.engine.storage.StorageResult
import dev.utils.app.CapturePictureUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.app.helper.view.ViewHelper
import dev.utils.common.FileUtils
import dev.utils.common.RandomUtils

/**
 * detail: CapturePictureUtils RecyclerView 截图
 * @author Ttt
 */
class CapturePictureRecyActivity :
    BaseProjectActivity<ActivityCapturePictureRecyBinding, AppViewModel>(
        R.layout.activity_capture_picture_recy, simple_Agile = {
            if (it is CapturePictureRecyActivity) {
                it.apply {
                    // 截图按钮
                    val view = QuickHelper.get(AppCompatTextView(this))
                        .setText("截图")
                        .setBold()
                        .setTextColors(ResourceUtils.getColor(R.color.white))
                        .setTextSizeBySp(15.0F)
                        .setPaddingLeft(30)
                        .setPaddingRight(30)
                        .setOnClick {
                            // 支持三种布局 GridLayoutManager、LinearLayoutManager、StaggeredGridLayoutManager
                            // 以及对于的横、竖屏效果截图
                            DevEngine.getStorage()?.insertImageToExternal(
                                StorageItem.createExternalItem("recy.jpg"),
                                DevSource.create(
                                    CapturePictureUtils.snapshotByRecyclerView(binding.vidRv)
                                ),
                                object : OnDevInsertListener {
                                    override fun onResult(
                                        result: StorageResult,
                                        params: StorageItem?,
                                        source: DevSource?
                                    ) {
                                        showToast(
                                            result.isSuccess(),
                                            "保存成功\n${FileUtils.getAbsolutePath(result.getFile())}",
                                            "保存失败"
                                        )
                                    }
                                }
                            )
                        }.getView<View>()
                    toolbar?.addView(view)

//                    binding.vidRv.layoutManager = LinearLayoutManager(this)
//                    binding.vidRv.layoutManager =
//                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//
//                    binding.vidRv.layoutManager = GridLayoutManager(this, 3)
//                    binding.vidRv.layoutManager =
//                        GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false)
//
//                    binding.vidRv.layoutManager =
//                        StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL)

                    binding.vidRv.layoutManager =
                        StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

                    binding.vidRv.adapter =
                        object :
                            DevDataAdapterExt<AdapterBean, DevBaseViewBindingVH<AdapterCapturePictureBinding>>() {

                            init {
                                setDataList(newAdapterBeanList(15), false)
                            }

                            override fun onCreateViewHolder(
                                parent: ViewGroup,
                                viewType: Int
                            ): DevBaseViewBindingVH<AdapterCapturePictureBinding> {
                                return newBindingViewHolder(
                                    parent,
                                    R.layout.adapter_capture_picture
                                )
                            }

                            override fun onBindViewHolder(
                                holder: DevBaseViewBindingVH<AdapterCapturePictureBinding>,
                                position: Int
                            ) {
                                val item = getDataItem(position)
                                ViewHelper.get()
                                    .setText(item.title, holder.binding.vidTitleTv)
                                    .setText(item.content, holder.binding.vidContentTv)
                            }
                        }
                }
            }
        }
    )


/**
 * detail: 适配器实体类
 * @author Ttt
 */
open class AdapterBean(
    // 标题
    val title: String,
    // 内容
    val content: String,
) {

    companion object {

        /**
         * 创建适配器实体类
         * @param position 索引
         * @return [AdapterBean]
         */
        private fun newAdapterBean(position: Int): AdapterBean {
            val number = RandomUtils.getRandom(10, 100) + (10 + position / 3) * 3
            return AdapterBean(
                title = RandomHelper.randomWord(2),
                content = "${position + 1}." + RandomHelper.randomWordRange(max = number)
            )
        }

        /**
         * 获取适配器实体类集合
         * @param count 集合总数
         * @return 适配器实体类集合
         */
        fun newAdapterBeanList(count: Int): List<AdapterBean> {
            val lists = mutableListOf<AdapterBean>()
            for (i in 0 until count) {
                lists.add(newAdapterBean(i))
            }
            return lists
        }
    }
}