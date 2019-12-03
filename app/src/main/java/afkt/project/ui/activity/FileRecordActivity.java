package afkt.project.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import butterknife.BindView;
import dev.utils.app.toast.ToastTintUtils;
import utils_use.record.FileRecordUse;

/**
 * detail: 日志、异常文件记录保存
 * @author Ttt
 * <pre>
 *     {@link FileRecordUse}
 * </pre>
 */
public class FileRecordActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;

    @Override
    public int getLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValues() {
        super.initValues();

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonValue.getFileRecordButtonValues());
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this));
        vid_bvr_recy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
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