package afkt.project.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import afkt.project.R;
import dev.utils.app.ClipboardUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.helper.ViewHelper;
import dev.utils.app.info.KeyValueBean;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: Key Value 适配器
 * @author Ttt
 */
public class KeyValueAdapter extends BaseQuickAdapter<KeyValueBean, BaseViewHolder> {

    public KeyValueAdapter(@Nullable List<KeyValueBean> data) {
        super(R.layout.adapter_key_value, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KeyValueBean item) {
        ViewHelper.get().setVisibility(helper.getLayoutPosition() == 0, helper.getView(R.id.vid_akv_line))
                .setText(helper.getView(R.id.vid_akv_key_tv), item.getKey())
                .setText(helper.getView(R.id.vid_akv_value_tv), item.getValue())
                .setOnClicks(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = item.toString();
                        // 复制到剪切板
                        ClipboardUtils.copyText(text);
                        // 进行提示
                        ToastTintUtils.success(ResourceUtils.getString(R.string.copy_suc) + " -> " + text);
                    }
                }, helper.getView(R.id.vid_akv_linear));
    }
}
