package dev.utils.app.info;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.support.annotation.Keep;
import android.text.format.Formatter;

import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.R;
import dev.utils.app.AppCommonUtils;
import dev.utils.app.SignaturesUtils;
import dev.utils.common.FileUtils;

/**
 * detail: App 信息Item
 * @author Ttt
 */
public final class AppInfoItem {

    // 日志 TAG
    private static final String TAG = AppInfoItem.class.getSimpleName();
    @Keep// App 基本信息实体类
    private AppInfoBean appInfoBean;
    @Keep // App MD5 签名
    private String appMD5;
    @Keep // App SHA1 签名
    private String appSHA1;
    @Keep // App SHA256 签名
    private String appSHA256;
    @Keep // App 最低支持版本
    private int minSdkVersion = -1;
    @Keep // App 兼容sdk版本
    private int targetSdkVersion = -1;
    @Keep // App 安装包大小
    private String apkLength;
    @Keep // 证书对象
    private X509Certificate cert;
    @Keep // 证书生成日期
    private Date notBefore;
    @Keep // 证书有效期
    private Date notAfter;
    @Keep // 证书是否过期 {@code true} 过期, {@code false} 未过期
    private boolean effective;
    @Keep // 证书发布方
    private String certPrincipal;
    @Keep // 证书版本号
    private String certVersion;
    @Keep // 证书算法名称
    private String certSigalgname;
    @Keep // 证书算法OID
    private String certSigalgoid;
    @Keep // 证书机器码
    private String certSerialnumber;
    @Keep // 证书 DER编码
    private String certDercode;
    @Keep // App 参数集
    private List<KeyValueBean> listKeyValues = new ArrayList<>();

    /**
     * 初始化 AppInfoItem 对象
     * @param pInfo
     * @return
     */
    protected static AppInfoItem obtain(final PackageInfo pInfo) {
        try {
            return new AppInfoItem(pInfo);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "obtain");
        }
        return null;
    }

    /**
     * 初始化 AppInfoItem 对象
     * @param pInfo
     */
    private AppInfoItem(final PackageInfo pInfo) {
        // 获取 Context
        Context context = DevUtils.getContext();
        // 格式化日期
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // =
        // 获取 App 信息
        appInfoBean = new AppInfoBean(pInfo);
        // App 签名MD5
        appMD5 = SignaturesUtils.signatureMD5(pInfo.signatures);
        // App SHA1
        appSHA1 = SignaturesUtils.signatureSHA1(pInfo.signatures);
        // App SHA256
        appSHA256 = SignaturesUtils.signatureSHA256(pInfo.signatures);
        // 属于7.0以上才有的方法
        if (AppCommonUtils.isN()) {
            // App 最低支持版本
            minSdkVersion = pInfo.applicationInfo.minSdkVersion;
        }
        // App 兼容sdk版本
        targetSdkVersion = pInfo.applicationInfo.targetSdkVersion;
        // App 安装包大小
        apkLength = Formatter.formatFileSize(DevUtils.getContext(), FileUtils.getFileLength(appInfoBean.getSourceDir()));
        // 证书对象
        cert = SignaturesUtils.getX509Certificate(pInfo.signatures);
        // 证书生成日期
        notBefore = cert.getNotBefore();
        // 证书有效期
        notAfter = cert.getNotAfter();
        // 设置有效期
        StringBuilder builder = new StringBuilder();
        builder.append(dFormat.format(notBefore));
        builder.append(" " + context.getString(R.string.dev_str_to) + " "); // 至
        builder.append(dFormat.format(notAfter));
        builder.append("\n\n");
        builder.append(notBefore);
        builder.append(" " + context.getString(R.string.dev_str_to) + " ");
        builder.append(notAfter);
        // 保存有效期转换信息
        String effectiveStr = builder.toString();
        // 证书是否过期 {@code true} 过期, {@code false} 未过期
        effective = false;
        try {
            cert.checkValidity();
            // CertificateExpiredException - 如果证书已过期。
            // CertificateNotYetValidException - 如果证书不再有效。
        } catch (CertificateExpiredException ce) {
            effective = true;
        } catch (CertificateNotYetValidException ce) {
            effective = true;
        }
        // 证书发布方
        certPrincipal = cert.getIssuerX500Principal().toString();
        // 证书版本号
        certVersion = cert.getVersion() + "";
        // 证书算法名称
        certSigalgname = cert.getSigAlgName();
        // 证书算法OID
        certSigalgoid = cert.getSigAlgOID();
        // 证书机器码
        certSerialnumber = cert.getSerialNumber().toString();
        try {
            // 证书 DER 编码
            certDercode = SignaturesUtils.toHexString(cert.getTBSCertificate());
        } catch (CertificateEncodingException e) {
        }

        // = 保存集合 =

        // App 包名
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_packname, appInfoBean.getAppPackName()));
        // App 签名MD5
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_md5, appMD5));
        // App 版本号 - 主要用于app内部版本判断 int 类型
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_version_code, appInfoBean.getVersionCode() + ""));
        // App 版本名 - 主要用于对用户显示版本信息
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_version_name, appInfoBean.getVersionName()));
        // App SHA1
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_sha1, appSHA1));
        // App SHA256.
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_sha256, appSHA256));
        // App 首次安装时间
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_first_install_time, dFormat.format(pInfo.firstInstallTime)));
        // 获取最后一次更新时间
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_last_update_time, dFormat.format(pInfo.lastUpdateTime)));
        // App 最低支持版本
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_minsdkversion, minSdkVersion + " ( " + AppCommonUtils.convertSDKVersion(minSdkVersion) + "+ )"));
        // App 兼容sdk版本
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_targetsdkversion, targetSdkVersion + " ( " + AppCommonUtils.convertSDKVersion(targetSdkVersion) + "+ )"));
        // Apk 大小
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_apk_length, apkLength));
        // 证书有效期
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_effective, effectiveStr));
        // 判断是否过期
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_iseffective, effective ? context.getString(R.string.dev_str_overdue) : context.getString(R.string.dev_str_notoverdue)));
        // 证书发布方
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_principal, certPrincipal));
        // 证书版本号
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_version, certVersion));
        // 证书算法名称
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_sigalgname, certSigalgname));
        // 证书算法OID
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_sigalgoid, certSigalgoid));
        // 证书机器码
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_dercode, certSerialnumber));
        // 证书 DER 编码
        listKeyValues.add(KeyValueBean.get(R.string.dev_str_serialnumber, certDercode));
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

    /**
     * 获取 App MD5 签名
     * @return
     */
    public String getAppMD5() {
        return appMD5;
    }

    /**
     * 获取 App SHA1 签名
     * @return
     */
    public String getAppSHA1() {
        return appSHA1;
    }

    /**
     * 获取 App SHA256 签名
     * @return
     */
    public String getAppSHA256() {
        return appSHA256;
    }

    /**
     * 获取 App 最低支持版本
     * @return
     */
    public int getMinSdkVersion() {
        return minSdkVersion;
    }

    /**
     * 获取 App 兼容sdk版本
     * @return
     */
    public int getTargetSdkVersion() {
        return targetSdkVersion;
    }

    /**
     * 获取 App 安装包大小
     * @return
     */
    public String getApkLength() {
        return apkLength;
    }

    /**
     * 获取证书对象
     * @return
     */
    public X509Certificate getX509Certificate() {
        return cert;
    }

    /**
     * 获取证书生成日期
     * @return
     */
    public Date getNotBefore() {
        return notBefore;
    }

    /**
     * 获取证书有效期
     * @return
     */
    public Date getNotAfter() {
        return notAfter;
    }

    /**
     * 获取证书是否过期 {@code true} 过期, {@code false} 未过期
     * @return
     */
    public boolean isEffective() {
        return effective;
    }

    /**
     * 获取证书发布方
     * @return
     */
    public String getCertPrincipal() {
        return certPrincipal;
    }

    /**
     * 获取证书版本号
     * @return
     */
    public String getCertVersion() {
        return certVersion;
    }

    /**
     * 获取证书算法名称
     * @return
     */
    public String getCertSigalgname() {
        return certSigalgname;
    }

    /**
     * 获取证书算法OID
     * @return
     */
    public String getCertSigalgoid() {
        return certSigalgoid;
    }

    /**
     * 获取证书机器码
     * @return
     */
    public String getCertSerialnumber() {
        return certSerialnumber;
    }

    /**
     * 获取证书 DER编码
     * @return
     */
    public String getCertDercode() {
        return certDercode;
    }
}
