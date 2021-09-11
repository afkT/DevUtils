package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityCapturePictureGridBinding
import afkt.project.databinding.AdapterCapturePictureBinding
import afkt.project.model.bean.AdapterBean
import afkt.project.model.bean.AdapterBean.Companion.newAdapterBeanList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import dev.base.DevSource
import dev.base.widget.BaseTextView
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
 * detail: CapturePictureUtils GridView 截图
 * @author Ttt
 */
@Route(path = RouterPath.CapturePictureGridActivity_PATH)
class CapturePictureGridActivity : BaseActivity<ActivityCapturePictureGridBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_capture_picture_grid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 截图按钮
        val view = QuickHelper.get(BaseTextView(this))
            .setText("截图")
            .setBold()
            .setTextColors(ResourceUtils.getColor(R.color.white))
            .setTextSizeBySp(15.0f)
            .setPaddingLeft(30)
            .setPaddingRight(30)
            .setOnClick { v: View? ->
                val bitmap = CapturePictureUtils.snapshotByGridView(binding.vidAcpGrid)
//                // 保存 ListView 一样效果
//                bitmap = CapturePictureUtils.snapshotByGridView(
//                    binding.vidAcpGrid,
//                    Bitmap.Config.ARGB_8888,
//                    true
//                )

                DevEngine.getStorage()?.insertImageToExternal(
                    StorageItem.createExternalItem(
                        "grid.jpg"
                    ),
                    DevSource.create(
                        bitmap
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
    }

    override fun initValue() {
        super.initValue()

        val lists = newAdapterBeanList(15)
        // 设置适配器
        binding.vidAcpGrid.adapter = object : BaseAdapter() {
            override fun getCount(): Int {
                return lists.size
            }

            override fun getItem(position: Int): AdapterBean {
                return lists[position]
            }

            override fun getItemId(position: Int): Long {
                return position.toLong()
            }

            override fun getView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val adapterBean = getItem(position)
                // 初始化 View 设置 TextView
                val _binding = AdapterCapturePictureBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ViewHelper.get()
                    .setText(adapterBean.title, _binding.vidAcpTitleTv)
                    .setText(adapterBean.content, _binding.vidAcpContentTv)
                return _binding.root
            }
        }
    }
}