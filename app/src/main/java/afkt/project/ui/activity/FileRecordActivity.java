package afkt.project.ui.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import dev.utils.app.toast.ToastTintUtils;
import utils_use.record.FileRecordUse;

/**
 * detail: 日志、异常文件记录保存
 * @author Ttt
 * <pre>
 *     {@link FileRecordUse}
 * </pre>
 */
public class FileRecordActivity
        extends BaseActivity<BaseViewRecyclerviewBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValue() {
        super.initValue();

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getFileRecordButtonValues());
        binding.vidBvrRecy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(
                    BaseQuickAdapter adapter,
                    View view,
                    int position
            ) {
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {
                    case ButtonValue.BTN_FILE_RECORD_ANALYSIS:
                        showToast(true, "保存成功");
                        FileRecordUse.analysisRecord();
                        break;
                    case ButtonValue.BTN_FILE_RECORD_UTILS:
                        showToast(true, "保存成功");
                        FileRecordUse.fileRecord();
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
    }
}