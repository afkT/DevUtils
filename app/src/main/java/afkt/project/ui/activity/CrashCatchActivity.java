package afkt.project.ui.activity;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.base.config.PathConfig;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import butterknife.BindView;
import dev.utils.app.ActivityUtils;
import dev.utils.app.CrashUtils;
import dev.utils.app.toast.ToastTintUtils;
import dev.utils.common.DateUtils;
import dev.utils.common.FileRecordUtils;

/**
 * detail: 奔溃日志捕获
 * @author Ttt
 */
public class CrashCatchActivity extends BaseToolbarActivity {

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
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getCrashButtonValues());
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this));
        vid_bvr_recy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {
                    case ButtonValue.BTN_CRASH_CLICK_CATCH:
                        // 模拟奔溃
                        String data = null;
                        data.split(",");
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
    }

    @Override
    public void initOtherOperate() {
        super.initOtherOperate();

        // 捕获异常处理 => 在 BaseApplication 中调用
        CrashUtils.getInstance().init(getApplicationContext(), new CrashUtils.CrashCatchListener() {
            @Override
            public void handleException(Throwable ex) {
                // 保存日志信息
                FileRecordUtils.saveErrorLog(ex, PathConfig.SDP_ERROR_PATH, "crash_" + DateUtils.getDateNow() + ".txt");
            }

            @Override
            public void uncaughtException(Context context, Thread thread, Throwable ex) {
//                // 退出 JVM (Java 虚拟机 ) 释放所占内存资源, 0 表示正常退出、非 0 的都为异常退出
//                System.exit(-1);
//                // 从操作系统中结束掉当前程序的进程
//                android.os.Process.killProcess(android.os.Process.myPid());
                // 关闭 APP
                ActivityUtils.getManager().exitApplication();
                // 可开启定时任务, 延迟几秒启动 APP
            }
        });
    }
}