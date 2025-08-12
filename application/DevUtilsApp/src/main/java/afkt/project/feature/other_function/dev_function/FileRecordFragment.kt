package afkt.project.feature.other_function.dev_function

import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.BaseViewRecyclerviewBinding
import utils_use.record.FileRecordUse

/**
 * detail: 日志、异常文件记录保存
 * @author Ttt
 * [FileRecordUse]
 */
class FileRecordFragment : AppFragment<BaseViewRecyclerviewBinding, AppViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is FileRecordFragment) {
//            it.apply {
//                binding.vidRv.bindAdapter(fileRecordButtonValues) { buttonValue ->
//                    when (buttonValue.type) {
//                        ButtonValue.BTN_FILE_RECORD_UTILS -> {
//                            showToast(true, "保存成功")
//                            FileRecordUse.fileRecordUse()
//                        }
//
//                        else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
//                    }
//                }
//            }
        }
    }
)