package afkt.project.features.other_function

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentOtherFunctionFileRecordBinding
import android.view.View
import dev.engine.extensions.toast.toast_showShort
import utils_use.record.FileRecordUse

/**
 * detail: 日志、异常文件记录保存
 * @author Ttt
 */
class FileRecordFragment : AppFragment<FragmentOtherFunctionFileRecordBinding, FileRecordViewModel>(
    R.layout.fragment_other_function_file_record, BR.viewModel
)

class FileRecordViewModel : AppViewModel() {

    val clickFileRecord = View.OnClickListener { view ->
        FileRecordUse.fileRecordUse()
        toast_showShort(text = "日志、异常文件记录保存成功")
    }
}