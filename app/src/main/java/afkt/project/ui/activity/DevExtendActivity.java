package afkt.project.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.base.config.PathConfig;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import butterknife.BindView;
import dev.callback.result.DevExResultCallback;
import dev.callback.result.DevResultCallback;
import dev.other.GsonUtils;
import dev.utils.app.logger.DevLogger;
import dev.utils.app.toast.ToastTintUtils;
import dev.utils.common.DateUtils;
import dev.utils.common.ThrowableUtils;

/**
 * detail: 通用结果回调类 ( 针对 DevResultCallback 进行扩展 )
 * @author Ttt
 */
public class DevExtendActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;
    // 拓展回调
    DevExResultCallback<String> dealResultCallback;

    @Override
    public int getLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValues() {
        super.initValues();

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonValue.getExtendButtonValues());
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this));
        vid_bvr_recy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {
                    case ButtonValue.BTN_EXTEND_SAVE:
                        String fileName = "save_" + DateUtils.getDateNow() + ".txt";
                        // 在默认的结果回调抽象类上使用
                        saveFile(PathConfig.SDP_TEXT_PATH, fileName, new DevResultCallback<Boolean>() {
                            @Override
                            public <E extends Throwable> void onError(E e) {
                                super.onError(e);
                                DevLogger.eTag(mTag, e);
                                ToastTintUtils.error("保存报错: " + ThrowableUtils.getThrowable(e));
                            }

                            @Override
                            public void onResult(String str, String msg, Boolean value) {
                                showToast(value, "文件保存成功", "文件保存失败");
                            }
                        });
                        break;
                    case ButtonValue.BTN_EXTEND_TRIGGER:
                        // 初始化保存参数
                        CallParams callParams = new CallParams();
                        callParams.userInfo = new UserInfo("测试用户名");
                        callParams.type = "测试类型";
                        callParams.hashMap.put(1, "asdasd");
                        // 保存参数
                        dealResultCallback.setExpandResult(callParams);
                        // 触发回调
                        dealResultCallback.onResult("数据", "消息", "结果 - 成功");
                        dealResultCallback.onResult(100, "数据", "消息", "结果 - 成功");
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

        // 扩展后的回调
        dealResultCallback = new DevExResultCallback<String>() {
            @Override
            public void onResult(String str, String msg, String value) {
                super.onResult(str, msg, value);
                // 因为内部继承扩展可以改变
                CallParams callParams = (CallParams) getExpandResult();

                StringBuilder builder = new StringBuilder();
                builder.append("\ntype: " + callParams.type);
                builder.append("\nuserInfo: " + GsonUtils.toJson(callParams.userInfo));
                builder.append("\nhashMap: " + callParams.hashMap.toString());
                DevLogger.dTag(mTag, "value: " + value + builder.toString());
            }

            @Override
            public void onResult(int type, String str, String msg, String value) {
                super.onResult(type, str, msg, value);
                // 因为内部继承扩展可以改变
                CallParams callParams = (CallParams) getExpandResult();

                StringBuilder builder = new StringBuilder();
                builder.append("\ntype: " + callParams.type);
                builder.append("\nuserInfo: " + GsonUtils.toJson(callParams.userInfo));
                builder.append("\nhashMap: " + callParams.hashMap.toString());
                DevLogger.dTag(mTag, "value: " + value + ", type: " + type + builder.toString());
            }
        };
    }

    // ==========
    // = 内部类 =
    // ==========

    /**
     * detail: 用户信息
     * @author Ttt
     */
    public static class UserInfo {

        public UserInfo(String name) {
            this.name = name;
        }

        public String name;
    }

    /**
     * detail: 回调参数扩展 ( 继承 DevExResultCallback.ExpandResult 进行扩展 )
     * @author Ttt
     */
    public static class CallParams extends DevExResultCallback.ExpandResult {
        // 用户信息
        public UserInfo userInfo;
        // 参数
        public HashMap<Integer, String> hashMap = new HashMap<>();
        // 类型
        public String type;
    }

    // ============
    // = 内部方法 =
    // ============

    /**
     * 保存文件
     * @param path     保存路径
     * @param fileName 文件名
     * @param callBack 回调方法
     */
    private static void saveFile(String path, String fileName, DevResultCallback<Boolean> callBack) {
        try {
            // 保存路径
            File file = new File(path, fileName);
            // 保存内容到一个文件
            FileOutputStream fos = new FileOutputStream(file);
            fos.write("扩展保存".getBytes());
            fos.close();
            // 触发回调
            if (callBack != null) {
                callBack.onResult(path, fileName, file.exists());
            }
        } catch (Exception e) {
            if (callBack != null) {
                callBack.onError(e);
            }
        }
    }
}