package dev.utils.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsPickerSessionContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import dev.utils.LogPrintUtils;

/**
 * detail: Android 17 系统联系人选择器工具类
 * @author Ttt
 * <pre>
 *     替代全量 {@code READ_CONTACTS}，由用户选择要分享的字段。
 *     需在 API 37+ 设备上使用；结果通过 Activity Result 返回。
 *     @see <a href="https://developer.android.com/about/versions/17/features/contact-picker">Contact Picker</a>
 * </pre>
 */
public final class ContactPickerUtils {

    private ContactPickerUtils() {
    }

    // 日志 TAG
    private static final String TAG = ContactPickerUtils.class.getSimpleName();

    /**
     * 请求字段：电话号码
     */
    public static final String DATA_FIELD_PHONE = "phone";
    /**
     * 请求字段：电子邮箱
     */
    public static final String DATA_FIELD_EMAIL = "email";

    /**
     * 是否支持系统联系人选择器
     * @return {@code true} API 37+, {@code false} 低版本
     */
    public static boolean isContactPickerSupported() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.CINNAMON_BUN;
    }

    /**
     * 获取选择联系人 Intent（默认请求电话 + 邮箱，单选）
     * @return {@link Intent}；低版本或异常时返回 null
     */
    @RequiresApi(api = Build.VERSION_CODES.CINNAMON_BUN)
    @Nullable
    public static Intent getPickContactsIntent() {
        return getPickContactsIntent(
                new String[]{DATA_FIELD_PHONE, DATA_FIELD_EMAIL}, 1, false
        );
    }

    /**
     * 获取选择联系人 Intent
     * <pre>
     *     {@code requestedDataFields} 元素可使用 {@link #DATA_FIELD_PHONE}、{@link #DATA_FIELD_EMAIL} 等常量。
     * </pre>
     * @param requestedDataFields 需要的数据字段
     * @param selectionLimit      最多可选联系人数，&lt;= 0 表示由系统默认
     * @param matchAllDataFields  是否要求联系人具备全部请求字段
     * @return {@link Intent}；低版本或异常时返回 null
     */
    @RequiresApi(api = Build.VERSION_CODES.CINNAMON_BUN)
    @Nullable
    public static Intent getPickContactsIntent(
            @Nullable final String[] requestedDataFields,
            final int selectionLimit,
            final boolean matchAllDataFields
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.CINNAMON_BUN) {
            LogPrintUtils.wTag(TAG, "getPickContactsIntent requires API 37+");
            return null;
        }
        try {
            Intent intent = new Intent(ContactsPickerSessionContract.ACTION_PICK_CONTACTS);
            if (requestedDataFields != null && requestedDataFields.length > 0) {
                intent.putExtra(
                        ContactsPickerSessionContract.EXTRA_PICK_CONTACTS_REQUESTED_DATA_FIELDS,
                        requestedDataFields
                );
            }
            if (selectionLimit > 0) {
                intent.putExtra(
                        ContactsPickerSessionContract.EXTRA_PICK_CONTACTS_SELECTION_LIMIT,
                        selectionLimit
                );
            }
            intent.putExtra(
                    ContactsPickerSessionContract.EXTRA_PICK_CONTACTS_MATCH_ALL_DATA_FIELDS,
                    matchAllDataFields
            );
            return intent;
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "getPickContactsIntent");
            return null;
        }
    }

    /**
     * 获取联系人选择器会话 Content Uri
     * <pre>
     *     用于查询已选联系人数据，同 {@link ContactsPickerSessionContract.Session#CONTENT_URI}。
     * </pre>
     * @return {@link Uri}
     */
    @RequiresApi(api = Build.VERSION_CODES.CINNAMON_BUN)
    @NonNull
    public static Uri getSessionContentUri() {
        return ContactsPickerSessionContract.Session.CONTENT_URI;
    }
}