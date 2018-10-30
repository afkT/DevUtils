package dev.utils.app.info;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Keep;
import android.text.TextUtils;
import android.text.format.Formatter;

import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dev.DevUtils;
import dev.utils.R;
import dev.utils.app.AppCommonUtils;
import dev.utils.app.SignaturesUtils;
import dev.utils.common.FileUtils;

/**
 * detail: App 信息Item
 * Created by Ttt
 */
public final class AppInfoItem {

    // App 信息实体类
    @Keep
    private AppInfoBean appInfoBean;
    // App 参数集
    @Keep
    private List<KeyValueBean> listKeyValues = new ArrayList<>();

    private AppInfoItem(){
    }

    /**
     * 初始化并获取 AppInfoItem 对象
     * @param packName
     * @return {@link AppInfoItem}
     * @throws Exception
     */
    public static AppInfoItem obtain(String packName) throws Exception {
        // 如果包名为null, 则直接不处理
        if (TextUtils.isEmpty(packName)){
            return null;
        }
        // 获取 Context
        Context context = DevUtils.getContext();
        // 初始化包管理类
        PackageManager pManager = context.getPackageManager();
        // 获取对应的PackageInfo(原始的PackageInfo 获取 signatures 等于null,需要这样获取)
        PackageInfo pInfo = pManager.getPackageInfo(packName, PackageManager.GET_SIGNATURES); // 64
        // 初始化实体类
        AppInfoItem appInfoItem = new AppInfoItem();
        // 获取 App 信息
        appInfoItem.appInfoBean = new AppInfoBean(pInfo, pManager);
        // == 获取 ==
        // 格式化日期
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // ===========
        // App 签名MD5
        String md5 = SignaturesUtils.signatureMD5(pInfo.signatures);
        // App SHA1
        String sha1 = SignaturesUtils.signatureSHA1(pInfo.signatures);
        // App SHA256
        String sha256 = SignaturesUtils.signatureSHA256(pInfo.signatures);
        // App 首次安装时间
        String firstInstallTime = dFormat.format(pInfo.firstInstallTime);
        // 获取最后一次更新时间
        String lastUpdateTime = dFormat.format(pInfo.lastUpdateTime);
        // App 最低支持版本
        int minSdkVersion = -1;
        // 属于7.0以上才有的方法
        if (AppCommonUtils.isN()){
            minSdkVersion = pInfo.applicationInfo.minSdkVersion;
        }
        // App 兼容sdk版本
        int targetSdkVersion = pInfo.applicationInfo.targetSdkVersion;
        // 获取 App 安装包大小
        String apkLength = Formatter.formatFileSize(DevUtils.getContext(), FileUtils.getFileLength(appInfoItem.appInfoBean.getSourceDir()));
        // 获取证书对象
        X509Certificate cert = SignaturesUtils.getX509Certificate(pInfo.signatures);
        // 设置有效期
        StringBuilder sbEffective = new StringBuilder();
        sbEffective.append(dFormat.format(cert.getNotBefore()));
        sbEffective.append(" " + context.getString(R.string.dev_str_to) + " "); // 至
        sbEffective.append(dFormat.format(cert.getNotAfter()));
        sbEffective.append("\n\n");
        sbEffective.append(cert.getNotBefore());
        sbEffective.append(" " + context.getString(R.string.dev_str_to) + " ");
        sbEffective.append(cert.getNotAfter());
        // 获取有效期
        String effective = sbEffective.toString();
        // 证书是否过期 true = 过期,false = 未过期
        boolean isEffective = false;
        try {
            cert.checkValidity();
            // CertificateExpiredException - 如果证书已过期。
            // CertificateNotYetValidException - 如果证书不再有效。
        } catch (CertificateExpiredException ce) {
            isEffective = true;
        } catch (CertificateNotYetValidException ce) {
            isEffective = true;
        }
        // 判断是否过期
        String isEffectiveState = isEffective ? context.getString(R.string.dev_str_overdue) : context.getString(R.string.dev_str_notoverdue);
        // 证书发布方
        String principal = cert.getIssuerX500Principal().toString();
        // 证书版本号
        String version = cert.getVersion() + "";
        // 证书算法名称
        String sigalgname = cert.getSigAlgName();
        // 证书算法OID
        String sigalgoid = cert.getSigAlgOID();
        // 证书机器码
        String serialnumber = cert.getSerialNumber().toString();
        // 证书 DER编码
        String dercode = SignaturesUtils.toHexString(cert.getTBSCertificate());

        // ================
        // === 保存集合 ===
        // ================
        // App 包名
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_packname, appInfoItem.appInfoBean.getAppPackName()));
        // App 签名MD5
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_md5, md5));
        // App 版本号 - 主要用于app内部版本判断 int 类型
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_version_code, appInfoItem.appInfoBean.getVersionCode() + ""));
        // App 版本名 - 主要用于对用户显示版本信息
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_version_name, appInfoItem.appInfoBean.getVersionName()));
        // App SHA1
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_sha1, sha1));
        // App SHA256.
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_sha256, sha256));
        // App 首次安装时间
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_first_install_time, firstInstallTime));
        // 获取最后一次更新时间
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_last_update_time, lastUpdateTime));
        // App 最低支持版本
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_minsdkversion, minSdkVersion + " ( " + AppCommonUtils.convertSDKVersion(minSdkVersion) + "+ )"));
        // App 兼容sdk版本
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_targetsdkversion, targetSdkVersion + " ( " + AppCommonUtils.convertSDKVersion(targetSdkVersion) + "+ )"));
        // 获取 Apk 大小
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_apk_length, apkLength));
        // 获取有效期
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_effective, effective));
        // 判断是否过期
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_iseffective, isEffectiveState));
        // 证书发布方
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_principal, principal));
        // 证书版本号
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_version, version));
        // 证书算法名称
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_sigalgname, sigalgname));
        // 证书算法OID
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_sigalgoid, sigalgoid));
        // 证书机器码
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_dercode, serialnumber));
        // 证书 DER编码
        appInfoItem.listKeyValues.add(KeyValueBean.get(R.string.dev_str_serialnumber, dercode));

        // 返回实体类
        return appInfoItem;
    }

    /**
     * 获取 AppInfoBean
     * @return {@link AppInfoBean }
     */
    public AppInfoBean getAppInfoBean() {
        return appInfoBean;
    }

    /**
     * 获取 List<KeyValueBean>
     * @return App 信息键对值集合
     */
    public List<KeyValueBean> getListKeyValues() {
        return listKeyValues;
    }
}
