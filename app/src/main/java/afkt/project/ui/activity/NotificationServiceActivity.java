package afkt.project.ui.activity;

import android.support.v7.widget.RecyclerView;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import butterknife.BindView;

/**
 * detail: 通知栏监听服务 ( NotificationService )
 * @author Ttt
 * <pre>
 *     @see <a href="https://www.jianshu.com/p/981e7de2c7be"/>
 *     所需权限
 *     <uses-permission android:name="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE" />
 * </pre>
 */
public class NotificationServiceActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;

    @Override
    public int getLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initValues() {
        super.initValues();
    }

    @Override
    public void initListeners() {
        super.initListeners();
    }
}
