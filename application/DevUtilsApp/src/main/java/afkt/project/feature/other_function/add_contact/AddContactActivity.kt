package afkt.project.feature.other_function.add_contact

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.ActivityAddContactBinding
import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.DialogInterface
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.*
import android.provider.ContactsContract.RawContacts
import android.view.View
import dev.engine.permission.IPermissionEngine
import dev.expand.engine.permission.permission_request
import dev.utils.app.*
import dev.utils.app.DialogUtils.DialogListener
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.ConvertUtils
import dev.utils.common.StringUtils
import dev.utils.common.thread.DevThreadManager
import dev.utils.common.validator.ValiToPhoneUtils
import dev.utils.common.validator.ValidatorUtils
import java.util.concurrent.atomic.AtomicInteger

/**
 * detail: 添加联系人
 * @author Ttt
 */
class AddContactActivity : BaseProjectActivity<ActivityAddContactBinding, AppViewModel>(
    R.layout.activity_add_contact, simple_Agile = {
        if (it is AddContactActivity) {
            it.apply {
                binding.vidAddBtn.setOnClickListener(View.OnClickListener {
                    if (running) {
                        ToastTintUtils.warning("运行中")
                        return@OnClickListener
                    }
                    permission_request(
                        permissions = arrayOf(
                            Manifest.permission.WRITE_CONTACTS
                        ),
                        callback = object : IPermissionEngine.Callback {
                            override fun onGranted() {
                                // 联系人创建校验
                                createCheck()
                            }

                            override fun onDenied(
                                grantedList: List<String>,
                                deniedList: List<String>,
                                notFoundList: List<String>
                            ) {
                                ToastTintUtils.error("请开启联系人写入权限")
                            }
                        }
                    )
                })
            }
        }
    }
) {

    // 待创建总数
    var count = 0

    // 是否运行中
    var running = false

    // 递增数
    var index = AtomicInteger()

    /**
     * 联系人创建校验
     */
    private fun createCheck() {
        // 获取手机号码开头、结尾
        val start = EditTextUtils.getText(binding.vidStartEt)
        val end = EditTextUtils.getText(binding.vidEndEt)
        // 判断是否符合条件
        val temp = start + end
        if (!ValidatorUtils.isNumber(temp)) {
            ToastTintUtils.error("请输入数字")
            return
        }
        val length = temp.length
        if (length >= 11) {
            ToastTintUtils.error("开头与结尾需少于11位")
            return
        }
        val diff = 11 - length
        // 待生成号码总数
        val middle = ConvertUtils.toInt("1" + StringUtils.forString(diff, "0"))
        // 临时号码
        val tempNumber = start + StringUtils.forString(diff, "0") + end
        val tempNumber2 = start + StringUtils.forString(diff, "9") + end
        // 判断是否手机号
        if (!ValiToPhoneUtils.isPhone(tempNumber)) {
            ToastTintUtils.error("请输入正确的手机号")
            return
        }
        // 进行提示
        val builder = StringBuilder()
        builder.append("将会创建 ").append(middle).append(" 条联系人数据\n")
        builder.append(tempNumber).append(" - ").append(tempNumber2)
        DialogUtils.createAlertDialog(
            mActivity, "创建提示", builder.toString(),
            "取消", "创建", object : DialogListener() {
                override fun onLeftButton(dialog: DialogInterface) {
                    dialog.dismiss()
                }

                override fun onRightButton(dialog: DialogInterface) {
                    dialog.dismiss()
                    // 创建联系人
                    createContact(start, end, middle)
                }
            }).show()
    }

    /**
     * 创建联系人
     * @param start 开头
     * @param end   结尾
     * @param count 创建总数
     */
    private fun createContact(
        start: String,
        end: String,
        count: Int
    ) {
        ToastTintUtils.normal("创建中...")
        ViewUtils.setVisibility(true, binding.vidTipsTv)
        KeyBoardUtils.closeKeyboard()
        this.count = count
        running = true
        index.set(0)
        // 线程数
        val threadCount = 5
        // 平均数
        val average = count / 5
        // 间隔数
        val intervals = IntArray(threadCount)
        for (i in 0 until threadCount) {
            intervals[i] = (i + 1) * average
        }
        for (i in 0 until threadCount) {
            if (i == 0) {
                createThread(start, end, 0, intervals[i], threadCount)
            } else {
                createThread(start, end, intervals[i - 1], intervals[i], threadCount)
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
    private fun createThread(
        start: String,
        end: String,
        first: Int,
        interval: Int,
        threadCount: Int
    ) {
        DevThreadManager.getInstance(threadCount).execute(Runnable {
            if (first == 0) {
                val length = interval.toString().length
                for (i in first until interval) {
                    val length1 = i.toString().length
                    if (length1 == length) { // 长度相同则不需要补 0
                        val phoneNumber = start + i + end
                        addContact(phoneNumber, phoneNumber)
                    } else {
                        val zero = StringUtils.forString(length - length1, "0")
                        val phoneNumber = start + zero + i + end
                        addContact(phoneNumber, phoneNumber)
                    }
                    // 更新提示语
                    handler.sendEmptyMessage(0)
                    // 如果页面销毁了则不处理
                    if (ActivityUtils.isFinishing(mActivity)) return@Runnable
                }
            } else {
                for (i in first until interval) {
                    val phoneNumber = start + i + end
                    addContact(phoneNumber, phoneNumber)
                    // 更新提示语
                    handler.sendEmptyMessage(0)
                    // 如果页面销毁了则不处理
                    if (ActivityUtils.isFinishing(mActivity)) return@Runnable
                }
            }
        })
    }

    /**
     * 添加联系人
     * @param name        姓名
     * @param phoneNumber 手机号
     */
    private fun addContact(
        name: String?,
        phoneNumber: String?
    ) {
        // 创建一个空的 ContentValues
        val values = ContentValues()
        // 向 RawContacts.CONTENT_URI 空值插入, 先获取 Android 系统返回的 rawContactId
        // 后面要基于此 id 插入值
        val rawContactUri = contentResolver.insert(RawContacts.CONTENT_URI, values)
        rawContactUri?.let {
            val rawContactId = ContentUris.parseId(it)
            values.clear()
            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
            // 内容类型
            values.put(ContactsContract.Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
            // 联系人名字
            values.put(StructuredName.GIVEN_NAME, name)
            // 向联系人 URI 添加联系人名字
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, values)
            values.clear()
            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
            values.put(ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
            // 联系人的电话号码
            values.put(Phone.NUMBER, phoneNumber)
            // 电话类型
            values.put(Phone.TYPE, Phone.TYPE_MOBILE)
            // 向联系人电话号码 URI 添加电话号码
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, values)
            values.clear()
            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
            values.put(ContactsContract.Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
            // 联系人的 Email 地址
            values.put(Email.DATA, "jtongttt@gmail.com")
            // 电子邮件的类型
            values.put(Email.TYPE, Email.TYPE_WORK)
            // 向联系人 Email URI 添加 Email数据
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, values)
        }
    }

    @SuppressLint("HandlerLeak")
    private var handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val value = index.getAndIncrement()
            if (count == value + 1) {
                val tips = "$count 条数据, 创建成功"
                TextViewUtils.setText(binding.vidTipsTv, tips)
                ToastTintUtils.success(tips)
                running = false
            } else {
                TextViewUtils.setText(
                    binding.vidTipsTv,
                    "需创建 " + count + " 条数据, 已创建 " + (value + 1) + " 条"
                )
            }
        }
    }
}