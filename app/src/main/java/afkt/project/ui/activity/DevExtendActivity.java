package afkt.project.ui.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.base.config.PathConfig;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
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
public class DevExtendActivity extends BaseActivity<BaseViewRecyclerviewBinding> {

    // 拓展回调
    DevExResultCallback<String> dealResultCallback;

    @Override
    public int baseLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValue() {
        super.initValue();

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getExtendButtonValues());
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
                    case ButtonValue.BTN_EXTEND_SAVE:
                        String fileName = "save_" + DateUtils.getDateNow() + ".txt";
                        // 在默认的结果回调抽象类上使用
                        saveFile(PathConfig.AEP_TEXT_PATH, fileName, new DevResultCallback<Boolean>() {
                            @Override
                            public <E extends Throwable> void onError(E e) {
                                super.onError(e);
                                DevLogger.eTag(TAG, e);
                                ToastTintUtils.error("保存报错: " + ThrowableUtils.getThrowable(e));
                            }

                            @Override
                            public void onResult(
                                    String str,
                                    String msg,
                                    Boolean value
                            ) {
                                showToast(value, "文件保存成功", "文件保存失败");
                            }
                        });
                        break;
                    case ButtonValue.BTN_EXTEND_TRIGGER:
                        showToast(true, "触发成功, 请查看 Logcat");
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
    public void initOther() {
        super.initOther();

        // 扩展后的回调
        dealResultCallback = new DevExResultCallback<String>() {
            @Override
            public void onResult(
                    String str,
                    String msg,
                    String value
            ) {
                super.onResult(str, msg, value);
                // 因为内部继承扩展可以改变
                CallParams callParams = (CallParams) getExpandResult();

                StringBuilder builder = new StringBuilder();
                builder.append("\ntype: ").append(callParams.type);
                builder.append("\nuserInfo: ").append(GsonUtils.toJson(callParams.userInfo));
                builder.append("\nhashMap: ").append(callParams.hashMap.toString());
                DevLogger.dTag(TAG, "value: %s%s", value, builder.toString());
            }

            @Override
            public void onResult(
                    int type,
                    String str,
                    String msg,
                    String value
            ) {
                super.onResult(type, str, msg, value);
                // 因为内部继承扩展可以改变
                CallParams callParams = (CallParams) getExpandResult();

                StringBuilder builder = new StringBuilder();
                builder.append("\ntype: ").append(callParams.type);
                builder.append("\nuserInfo: ").append(GsonUtils.toJson(callParams.userInfo));
                builder.append("\nhashMap: ").append(callParams.hashMap.toString());
                DevLogger.dTag(TAG, "value: %s, type: %s%s", value, type, builder.toString());
            }
        };
    }

    // =========
    // = 内部类 =
    // =========

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
        public Map<Integer, String> hashMap = new HashMap<>();
        // 类型
        public String type;
    }

    // ===========
    // = 内部方法 =
    // ===========

    /**
     * 保存文件
     * @param path     保存路径
     * @param fileName 文件名
     * @param callback 回调方法
     */
    private static void saveFile(
            String path,
            String fileName,
            DevResultCallback<Boolean> callback
    ) {
        try {
            // 保存路径
            File file = new File(path, fileName);
            // 保存内容到一个文件
            FileOutputStream fos = new FileOutputStream(file);
            fos.write("扩展保存".getBytes());
            fos.close();
            // 触发回调
            if (callback != null) {
                callback.onResult(path, fileName, file.exists());
            }
        } catch (Exception e) {
            if (callback != null) {
                callback.onError(e);
            }
        }
    }
}