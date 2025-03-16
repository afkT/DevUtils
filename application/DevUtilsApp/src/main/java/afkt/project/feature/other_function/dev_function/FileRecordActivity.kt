package afkt.project.feature.other_function.dev_function

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.base.project.bindAdapter
import afkt.project.data_model.button.ButtonList.fileRecordButtonValues
import afkt.project.data_model.button.ButtonValue
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import com.therouter.router.Route
import dev.utils.app.toast.ToastTintUtils
import utils_use.record.FileRecordUse

/**
 * detail: 日志、异常文件记录保存
 * @author Ttt
 * [FileRecordUse]
 */
@Route(path = RouterPath.OTHER_FUNCTION.FileRecordActivity_PATH)
class FileRecordActivity : BaseProjectActivity<BaseViewRecyclerviewBinding, BaseProjectViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is FileRecordActivity) {
            it.apply {
                binding.vidRv.bindAdapter(fileRecordButtonValues) { buttonValue ->
                    when (buttonValue.type) {
                        ButtonValue.BTN_FILE_RECORD_UTILS -> {
                            showToast(true, "保存成功")
                            FileRecordUse.fileRecordUse()
                        }

                        else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
                    }
                }
            }
        }
    }
)