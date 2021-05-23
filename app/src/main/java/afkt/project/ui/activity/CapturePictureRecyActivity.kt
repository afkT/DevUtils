package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.PathConfig
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityCapturePictureRecyBinding
import afkt.project.databinding.AdapterCapturePictureBinding
import afkt.project.model.bean.AdapterBean
import afkt.project.model.bean.AdapterBean.Companion.newAdapterBeanList
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import dev.adapter.DevDataAdapterExt
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.base.widget.BaseTextView
import dev.utils.app.CapturePictureUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.QuickHelper
import dev.utils.app.helper.ViewHelper
import dev.utils.app.image.ImageUtils

/**
 * detail: CapturePictureUtils RecyclerView 截图
 * @author Ttt
 */
@Route(path = RouterPath.CapturePictureRecyActivity_PATH)
class CapturePictureRecyActivity : BaseActivity<ActivityCapturePictureRecyBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_capture_picture_recy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 截图按钮
        val view = QuickHelper.get(BaseTextView(this))
            .setText("截图")
            .setBold()
            .setTextColor(ResourceUtils.getColor(R.color.white))
            .setTextSizeBySp(15.0f)
            .setPaddingLeft(30)
            .setPaddingRight(30)
            .setOnClicks {
                val filePath = PathConfig.AEP_DOWN_IMAGE_PATH
                val fileName = "recy.jpg"
                val bitmap: Bitmap

                // 支持三种布局 GridLayoutManager、LinearLayoutManager、StaggeredGridLayoutManager
                // 以及对于的横、竖屏效果截图
                bitmap = CapturePictureUtils.snapshotByRecyclerView(binding.vidAcpRecy)
                val result = ImageUtils.saveBitmapToSDCardJPEG(bitmap, filePath + fileName)
                showToast(result, "保存成功\n" + (filePath + fileName), "保存失败")
            }.getView<View>()
        toolbar?.addView(view)
    }

    override fun initValue() {
        super.initValue()

//        binding.vidAcpRecy.layoutManager = LinearLayoutManager(this)
//        binding.vidAcpRecy.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//
//        binding.vidAcpRecy.layoutManager = GridLayoutManager(this, 3)
//        binding.vidAcpRecy.layoutManager =
//            GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false)

        binding.vidAcpRecy.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
//        binding.vidAcpRecy.layoutManager =
//            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL)

        binding.vidAcpRecy.adapter =
            object : DevDataAdapterExt<AdapterBean, DevBaseViewBindingVH<AdapterCapturePictureBinding>>() {

                init {
                    setDataList(newAdapterBeanList(15), false)
                }

                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): DevBaseViewBindingVH<AdapterCapturePictureBinding> {
                    return newBindingViewHolder(parent, R.layout.adapter_capture_picture)
                }

                override fun onBindViewHolder(
                    holder: DevBaseViewBindingVH<AdapterCapturePictureBinding>,
                    position: Int
                ) {
                    val item = getDataItem(position)
                    ViewHelper.get()
                        .setText(holder.binding.vidAcpTitleTv, item.title)
                        .setText(holder.binding.vidAcpContentTv, item.title)
                }
            }
    }
}