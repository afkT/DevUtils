package afkt.project.ui.activity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.view.View;

import java.util.concurrent.atomic.AtomicInteger;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.ui.widget.BaseEditText;
import afkt.project.ui.widget.BaseTextView;
import butterknife.BindView;
import butterknife.OnClick;
import dev.utils.app.DialogUtils;
import dev.utils.app.EditTextUtils;
import dev.utils.app.KeyBoardUtils;
import dev.utils.app.toast.ToastTintUtils;
import dev.utils.common.ConvertUtils;
import dev.utils.common.StringUtils;
import dev.utils.common.validator.ValiToPhoneUtils;
import dev.utils.common.validator.ValidatorUtils;

/**
 * detail: 添加联系人
 * @author Ttt
 */
public class AddContactActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_aac_start_edit)
    BaseEditText vid_aac_start_edit;
    @BindView(R.id.vid_aac_end_edit)
    BaseEditText vid_aac_end_edit;
    @BindView(R.id.vid_aac_tips_tv)
    BaseTextView vid_aac_tips_tv;
    // = Object =
    // 待创建总数
    int           count;
    // 是否运行中
    boolean       runing;
    // 递增数
    AtomicInteger index = new AtomicInteger();


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
                if (runing) {
                    ToastTintUtils.warning("运行中");
                    return;
                }
                // 获取手机号码开头、结尾
                String start = EditTextUtils.getText(vid_aac_start_edit);
                String end = EditTextUtils.getText(vid_aac_end_edit);
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
                count = ConvertUtils.toInt("1" + StringUtils.forString(diff, "0"));
                // 临时号码
                String tempNumber = start + StringUtils.forString(diff, "0") + end;
                String tempNumber2 = start + StringUtils.forString(diff, "9") + end;
                // 判断是否手机号
                if (!ValiToPhoneUtils.isPhone(tempNumber)) {
                    ToastTintUtils.error("请输入正确的手机号");
                    return;
                }
                // 进行提示
                StringBuilder builder = new StringBuilder();
                builder.append("将会创建 " + count + " 条联系人数据\n");
                builder.append(tempNumber + " - " + tempNumber2);
                DialogUtils.createAlertDialog(mContext, "创建提示", builder.toString(),
                        "取消", "创建", new DialogUtils.DialogListener() {
                            @Override
                            public void onLeftButton(DialogInterface dialog) {
                                dialog.dismiss();
                            }

                            @Override
                            public void onRightButton(DialogInterface dialog) {
                                dialog.dismiss();
                                runing = true;
                                KeyBoardUtils.closeKeyboard();
                            }
                        }).show();
                break;
        }
    }

    /**
     * 添加联系人
     * @param name        姓名
     * @param phoneNumber 手机号
     */
    public void addContact(String name, String phoneNumber) {
        // 创建一个空的 ContentValues
        ContentValues values = new ContentValues();

        // 向 RawContacts.CONTENT_URI 空值插入, 先获取 Android 系统返回的 rawContactId
        // 后面要基于此 id 插入值
        Uri rawContactUri = getContentResolver().insert(RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
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
}