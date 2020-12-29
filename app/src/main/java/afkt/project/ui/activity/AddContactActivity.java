package afkt.project.ui.activity;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityAddContactBinding;
import dev.utils.app.ActivityUtils;
import dev.utils.app.DialogUtils;
import dev.utils.app.EditTextUtils;
import dev.utils.app.KeyBoardUtils;
import dev.utils.app.TextViewUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.permission.PermissionUtils;
import dev.utils.app.toast.ToastTintUtils;
import dev.utils.app.toast.ToastUtils;
import dev.utils.common.ConvertUtils;
import dev.utils.common.StringUtils;
import dev.utils.common.thread.DevThreadManager;
import dev.utils.common.validator.ValiToPhoneUtils;
import dev.utils.common.validator.ValidatorUtils;

/**
 * detail: 添加联系人
 * @author Ttt
 */
public class AddContactActivity
        extends BaseActivity<ActivityAddContactBinding> {

    // 待创建总数
    int           count;
    // 是否运行中
    boolean       running;
    // 递增数
    AtomicInteger index = new AtomicInteger();

    @Override
    public int baseLayoutId() {
        return R.layout.activity_add_contact;
    }

    @Override
    public void initValue() {
        super.initValue();
        binding.vidAacAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (running) {
                    ToastTintUtils.warning("运行中");
                    return;
                }
                PermissionUtils.permission(Manifest.permission.WRITE_CONTACTS)
                        .callback(new PermissionUtils.PermissionCallback() {
                            /**
                             * 授权通过权限回调
                             */
                            @Override
                            public void onGranted() {
                                // 联系人创建校验
                                createCheck();
                            }

                            /**
                             * 授权未通过权限回调
                             * @param grantedList  申请通过的权限
                             * @param deniedList   申请未通过的权限
                             * @param notFoundList 查询不到的权限 ( 包含未注册 )
                             */
                            @Override
                            public void onDenied(
                                    List<String> grantedList,
                                    List<String> deniedList,
                                    List<String> notFoundList
                            ) {
                                ToastUtils.showShort("请开启联系人写入权限.");
                            }
                        }).request(mActivity);
            }
        });
    }

    /**
     * 联系人创建校验
     */
    private void createCheck() {
        // 获取手机号码开头、结尾
        String start = EditTextUtils.getText(binding.vidAacStartEdit);
        String end   = EditTextUtils.getText(binding.vidAacEndEdit);
        // 判断是否符合条件
        String temp = start + end;
        if (!ValidatorUtils.isNumber(temp)) {
            ToastTintUtils.error("请输入数字");
            return;
        }
        int length = temp.length();
        if (length >= 11) {
            ToastTintUtils.error("开头与结尾需少于11位");
            return;
        }
        int diff = 11 - length;
        // 待生成号码总数
        int middle = ConvertUtils.toInt("1" + StringUtils.forString(diff, "0"));
        // 临时号码
        String tempNumber  = start + StringUtils.forString(diff, "0") + end;
        String tempNumber2 = start + StringUtils.forString(diff, "9") + end;
        // 判断是否手机号
        if (!ValiToPhoneUtils.isPhone(tempNumber)) {
            ToastTintUtils.error("请输入正确的手机号");
            return;
        }
        // 进行提示
        StringBuilder builder = new StringBuilder();
        builder.append("将会创建 ").append(middle).append(" 条联系人数据\n");
        builder.append(tempNumber).append(" - ").append(tempNumber2);
        DialogUtils.createAlertDialog(mContext, "创建提示", builder.toString(),
                "取消", "创建", new DialogUtils.DialogListener() {
                    @Override
                    public void onLeftButton(DialogInterface dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onRightButton(DialogInterface dialog) {
                        dialog.dismiss();
                        // 创建联系人
                        createContact(start, end, middle);
                    }
                }).show();
    }

    /**
     * 创建联系人
     * @param start 开头
     * @param end   结尾
     * @param count 创建总数
     */
    private void createContact(
            String start,
            String end,
            int count
    ) {
        ToastTintUtils.normal("创建中...");
        ViewUtils.setVisibility(true, binding.vidAacTipsTv);
        KeyBoardUtils.closeKeyboard();
        this.count = count;
        this.running = true;
        this.index.set(0);
        // 线程数
        int threadCount = 5;
        // 平均数
        int average = count / 5;
        // 间隔数
        int[] intervals = new int[threadCount];
        for (int i = 0; i < threadCount; i++) {
            intervals[i] = (i + 1) * average;
        }
        for (int i = 0; i < threadCount; i++) {
            if (i == 0) {
                createThread(start, end, 0, intervals[i], threadCount);
            } else {
                createThread(start, end, intervals[i - 1], intervals[i], threadCount);
            }
        }
    }

    /**
     * 创建线程
     * @param start       开头
     * @param end         结尾
     * @param first       起点
     * @param interval    间隔值 ( size )
     * @param threadCount 线程数量
     */
    private void createThread(
            String start,
            String end,
            int first,
            int interval,
            int threadCount
    ) {
        DevThreadManager.getInstance(threadCount).execute(new Runnable() {
            @Override
            public void run() {
                if (first == 0) {
                    int length = String.valueOf(interval).length();
                    for (int i = first; i < interval; i++) {
                        int length1 = String.valueOf(i).length();
                        if (length1 == length) { // 长度相同则不需要补 0
                            String phoneNumber = start + i + end;
                            addContact(phoneNumber, phoneNumber);
                        } else {
                            String zero        = StringUtils.forString(length - length1, "0");
                            String phoneNumber = start + zero + i + end;
                            addContact(phoneNumber, phoneNumber);
                        }
                        // 更新提示语
                        handler.sendEmptyMessage(0);
                        // 如果页面销毁了则不处理
                        if (ActivityUtils.isFinishing(mActivity)) return;
                    }
                } else {
                    for (int i = first; i < interval; i++) {
                        String phoneNumber = start + i + end;
                        addContact(phoneNumber, phoneNumber);
                        // 更新提示语
                        handler.sendEmptyMessage(0);
                        // 如果页面销毁了则不处理
                        if (ActivityUtils.isFinishing(mActivity)) return;
                    }
                }
            }
        });
    }

    /**
     * 添加联系人
     * @param name        姓名
     * @param phoneNumber 手机号
     */
    public void addContact(
            String name,
            String phoneNumber
    ) {
        // 创建一个空的 ContentValues
        ContentValues values = new ContentValues();

        // 向 RawContacts.CONTENT_URI 空值插入, 先获取 Android 系统返回的 rawContactId
        // 后面要基于此 id 插入值
        Uri  rawContactUri = getContentResolver().insert(RawContacts.CONTENT_URI, values);
        long rawContactId  = ContentUris.parseId(rawContactUri);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        // 内容类型
        values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
        // 联系人名字
        values.put(StructuredName.GIVEN_NAME, name);
        // 向联系人 URI 添加联系人名字
        getContentResolver().insert(Data.CONTENT_URI, values);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        // 联系人的电话号码
        values.put(Phone.NUMBER, phoneNumber);
        // 电话类型
        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
        // 向联系人电话号码 URI 添加电话号码
        getContentResolver().insert(Data.CONTENT_URI, values);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
        // 联系人的 Email 地址
        values.put(Email.DATA, "jtongttt@gmail.com");
        // 电子邮件的类型
        values.put(Email.TYPE, Email.TYPE_WORK);
        // 向联系人 Email URI 添加 Email数据
        getContentResolver().insert(Data.CONTENT_URI, values);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            int value = index.getAndIncrement();
            if (count == value + 1) {
                String tips = count + " 条数据, 创建成功";
                TextViewUtils.setText(binding.vidAacTipsTv, tips);
                ToastTintUtils.success(tips);
                running = false;
            } else {
                TextViewUtils.setText(binding.vidAacTipsTv, "需创建 " + count + " 条数据, 已创建 " + (value + 1) + " 条");
            }
        }
    };
}