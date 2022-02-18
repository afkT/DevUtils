package afkt.project.feature.other_function.dev_function

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.feature.ButtonAdapter
import afkt.project.model.item.ButtonList.fileRecordButtonValues
import afkt.project.model.item.ButtonValue
import com.alibaba.android.arouter.facade.annotation.Route
import dev.callback.DevItemClickCallback
import dev.utils.app.toast.ToastTintUtils
import utils_use.record.FileRecordUse

/**
 * detail: 日志、异常文件记录保存
 * @author Ttt
 * [FileRecordUse]
 */
@Route(path = RouterPath.OTHER_FUNCTION.FileRecordActivity_PATH)
class FileRecordActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()

        // 初始化布局管理器、适配器
        ButtonAdapter(fileRecordButtonValues)
            .setItemCallback(object : DevItemClickCallback<ButtonValue>() {
                override fun onItemClick(
                    buttonValue: ButtonValue,
                    param: Int
                ) {
                    when (buttonValue.type) {
                        ButtonValue.BTN_FILE_RECORD_UTILS -> {
                            showToast(true, "保存成功")
                            FileRecordUse.fileRecordUse()
                        }
                        else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
                    }
                }
            }).bindAdapter(binding.vidRv)
    }
}