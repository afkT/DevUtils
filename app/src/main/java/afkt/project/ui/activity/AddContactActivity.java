package afkt.project.ui.activity;

import android.view.View;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import butterknife.OnClick;

/**
 * detail: 添加联系人
 * @author Ttt
 */
public class AddContactActivity extends BaseToolbarActivity {

    // = View =

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_contact;
    }

    @Override
    public void initValues() {
        super.initValues();
    }

    @OnClick({R.id.vid_aac_add_btn})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.vid_aac_add_btn:
                break;
        }
    }
}