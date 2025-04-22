package afkt.project.feature.ui_effect.capture_picture

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.bean.AdapterBean
import afkt.project.data_model.bean.AdapterBean.Companion.newAdapterBeanList
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.ActivityCapturePictureRecyBinding
import afkt.project.databinding.AdapterCapturePictureBinding
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.therouter.router.Route
import dev.adapter.DevDataAdapterExt
import dev.base.DevSource
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import androidx.appcompat.widget.AppCompatTextView
import dev.engine.DevEngine
import dev.engine.storage.OnDevInsertListener
import dev.engine.storage.StorageItem
import dev.engine.storage.StorageResult
import dev.utils.app.CapturePictureUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.app.helper.view.ViewHelper
import dev.utils.common.FileUtils

/**
 * detail: CapturePictureUtils RecyclerView 截图
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.CapturePictureRecyActivity_PATH)
class CapturePictureRecyActivity :
    BaseProjectActivity<ActivityCapturePictureRecyBinding, BaseProjectViewModel>(
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