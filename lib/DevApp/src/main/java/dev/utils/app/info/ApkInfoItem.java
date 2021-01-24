package dev.utils.app.info;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.text.format.Formatter;

import androidx.annotation.Keep;

import java.io.File;
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
import dev.utils.common.ConvertUtils;
import dev.utils.common.FileUtils;

/**
 * detail: APK 信息 Item
 * @author Ttt
 */
public final class ApkInfoItem {

    // 日志 TAG
    private static final String TAG = ApkInfoItem.class.getSimpleName();

    @Keep // APP 基本信息实体类
    private final AppInfoBean     appInfoBean;
    @Keep // APP MD5 签名
    private final String          appMD5;
    @Keep // APP SHA1 签名
    private final String          appSHA1;
    @Keep // APP SHA256 签名
    private final String          appSHA256;
    @Keep // APP 最低支持 Android SDK 版本
    private       int             minSdkVersion = -1;
    @Keep // APP 兼容 SDK 版本
    private final int             targetSdkVersion;
    @Keep // APP 安装包大小
    private final String          apkLength;
    @Keep // 证书对象
    private       X509Certificate cert;
    @Keep // 证书生成日期
    private       Date            notBefore;
    @Keep // 证书有效期
    private       Date            notAfter;
    @Keep // 证书是否过期
    private       boolean         effective;
    @Keep // 证书发布方
    private       String          certPrincipal;
    @Keep // 证书版本号
    private       String          certVersion;
    @Keep // 证书算法名称
    private       String          certSigAlgName;
    @Keep // 证书算法 OID
    private       String          certSigAlgOID;
    @Keep // 证书机器码
    private       String          certSerialnumber;
    @Keep // 证书 DER 编码
    private       String          certDERCode;
    @Keep // APP 参数集
    private final List<KeyValue>  listKeyValues = new ArrayList<>();

    /**
     * 获取 ApkInfoItem
     * @param packageInfo {@link PackageInfo}
     * @return {@link ApkInfoItem}
     */
    protected static ApkInfoItem get(final PackageInfo packageInfo) {
        try {
            return new ApkInfoItem(packageInfo);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "get");
        }
        return null;
    }

    /**
     * 初始化 ApkInfoItem
     * @param packageInfo {@link PackageInfo}
     */
    private ApkInfoItem(final PackageInfo packageInfo) {
        // 获取 Context
        Context context = DevUtils.getContext();
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // =
        // 获取 APP 信息
        appInfoBean = new AppInfoBean(packageInfo);
        // 获取签名信息
        Signature[] signatures = SignaturesUtils.getSignaturesFromApk(new File(appInfoBean.getSourceDir()));
        // =
        // APP MD5 签名
        appMD5 = SignaturesUtils.signatureMD5(signatures);
        // APP SHA1
        appSHA1 = SignaturesUtils.signatureSHA1(signatures);
        // APP SHA256
        appSHA256 = SignaturesUtils.signatureSHA256(signatures);
        // 属于 7.0 以上才有的方法
        if (AppCommonUtils.isN()) {
            // APP 最低支持 Android SDK 版本
            minSdkVersion = packageInfo.applicationInfo.minSdkVersion;
        }
        // APP 兼容 SDK 版本
        targetSdkVersion = packageInfo.applicationInfo.targetSdkVersion;
        // APP 安装包大小
        apkLength = Formatter.formatFileSize(DevUtils.getContext(), FileUtils.getFileLength(appInfoBean.getSourceDir()));

        // 是否保存
        boolean isError = false;
        // 临时签名信息
        List<KeyValue> listTemps = new ArrayList<>();

        try {
            // 证书对象
            cert = SignaturesUtils.getX509Certificate(signatures);
            // 证书生成日期
            notBefore = cert.getNotBefore();
            // 证书有效期
            notAfter = cert.getNotAfter();
            // 设置有效期
            StringBuilder builder = new StringBuilder();
            builder.append(sdf.format(notBefore));
            builder.append(" ").append(context.getString(R.string.dev_str_to)).append(" "); // 至
            builder.append(sdf.format(notAfter));
            builder.append("\n\n");
            builder.append(notBefore);
            builder.append(" ").append(context.getString(R.string.dev_str_to)).append(" ");
            builder.append(notAfter);
            // 保存有效期转换信息
            String effectiveStr = builder.toString();
            // 证书是否过期
            effective = false;
            try {
                cert.checkValidity();
                // CertificateExpiredException ( 证书已过期 )
                // CertificateNotYetValidException ( 证书不再有效 )
            } catch (CertificateExpiredException ce) {
                effective = true;
            } catch (CertificateNotYetValidException ce) {
                effective = true;
            }
            // 证书发布方
            certPrincipal = cert.getIssuerX500Principal().toString();
            // 证书版本号
            certVersion = String.valueOf(cert.getVersion());
            // 证书算法名称
            certSigAlgName = cert.getSigAlgName();
            // 证书算法 OID
            certSigAlgOID = cert.getSigAlgOID();
            // 证书机器码
            certSerialnumber = cert.getSerialNumber().toString();
            try {
                // 证书 DER 编码
                certDERCode = ConvertUtils.toHexString(cert.getTBSCertificate());
            } catch (CertificateEncodingException e) {
            }
            // 证书有效期
            listTemps.add(KeyValue.get(R.string.dev_str_effective, effectiveStr));
            // 判断是否过期
            listTemps.add(KeyValue.get(R.string.dev_str_iseffective, effective ? context.getString(R.string.dev_str_overdue) : context.getString(R.string.dev_str_notoverdue)));
            // 证书发布方
            listTemps.add(KeyValue.get(R.string.dev_str_principal, certPrincipal));
            // 证书版本号
            listTemps.add(KeyValue.get(R.string.dev_str_version, certVersion));
            // 证书算法名称
            listTemps.add(KeyValue.get(R.string.dev_str_sigalgname, certSigAlgName));
            // 证书算法 OID
            listTemps.add(KeyValue.get(R.string.dev_str_sigalgoid, certSigAlgOID));
            // 证书机器码
            listTemps.add(KeyValue.get(R.string.dev_str_dercode, certSerialnumber));
            // 证书 DER 编码
            listTemps.add(KeyValue.get(R.string.dev_str_serialnumber, certDERCode));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "ApkInfoItem");
            isError = true;
        }

        // = 保存集合 =

        // APP 包名
        listKeyValues.add(KeyValue.get(R.string.dev_str_packname, appInfoBean.getAppPackName()));
        // 没报错才存储 MD5 信息
        if (!isError) {
            // APP MD5 签名
            listKeyValues.add(KeyValue.get(R.string.dev_str_md5, appMD5));
        }
        // APP 版本号 ( 主要用于 APP 内部版本判断 int 类型 )
        listKeyValues.add(KeyValue.get(R.string.dev_str_version_code, String.valueOf(appInfoBean.getVersionCode())));
        // APP 版本名 ( 主要用于对用户显示版本信息 )
        listKeyValues.add(KeyValue.get(R.string.dev_str_version_name, appInfoBean.getVersionName()));
        // 安装包地址
        listKeyValues.add(KeyValue.get(R.string.dev_str_apk_uri, appInfoBean.getSourceDir()));
        // 没报错才存储 SHA 信息
        if (!isError) {
            // APP SHA1
            listKeyValues.add(KeyValue.get(R.string.dev_str_sha1, appSHA1));
            // APP SHA256
            listKeyValues.add(KeyValue.get(R.string.dev_str_sha256, appSHA256));
        }
        // APP 最低支持 Android SDK 版本
        listKeyValues.add(KeyValue.get(R.string.dev_str_minsdkversion, minSdkVersion + " ( " + AppCommonUtils.convertSDKVersion(minSdkVersion) + "+ )"));
        // APP 兼容 SDK 版本
        listKeyValues.add(KeyValue.get(R.string.dev_str_targetsdkversion, targetSdkVersion + " ( " + AppCommonUtils.convertSDKVersion(targetSdkVersion) + "+ )"));
        // 获取 APK 大小
        listKeyValues.add(KeyValue.get(R.string.dev_str_apk_length, apkLength));
        // 没报错才存储 其他签名信息
        if (!isError) {
            listKeyValues.addAll(listTemps);
        }
    }

    /**
     * 获取 AppInfoBean
     * @return {@link AppInfoBean}
     */
    public AppInfoBean getAppInfoBean() {
        return appInfoBean;
    }

    /**
     * 获取 List 信息键对值集合
     * @return APP 信息键对值集合
     */
    public List<KeyValue> getListKeyValues() {
        return listKeyValues;
    }

    /**
     * 获取 APP MD5 签名
     * @return APP MD5 签名
     */
    public String getAppMD5() {
        return appMD5;
    }

    /**
     * 获取 APP SHA1 签名
     * @return APP SHA1 签名
     */
    public String getAppSHA1() {
        return appSHA1;
    }

    /**
     * 获取 APP SHA256 签名
     * @return APP SHA256 签名
     */
    public String getAppSHA256() {
        return appSHA256;
    }

    /**
     * 获取 APP 最低支持 Android SDK 版本
     * @return APP 最低支持 Android SDK 版本
     */
    public int getMinSdkVersion() {
        return minSdkVersion;
    }

    /**
     * 获取 APP 兼容 SDK 版本
     * @return APP 兼容 SDK 版本
     */
    public int getTargetSdkVersion() {
        return targetSdkVersion;
    }

    /**
     * 获取 APP 安装包大小
     * @return APP 安装包大小
     */
    public String getApkLength() {
        return apkLength;
    }

    /**
     * 获取证书对象
     * @return {@link X509Certificate}
     */
    public X509Certificate getX509Certificate() {
        return cert;
    }

    /**
     * 获取证书生成日期
     * @return 证书生成日期
     */
    public Date getNotBefore() {
        return notBefore;
    }

    /**
     * 获取证书有效期
     * @return 证书有效期
     */
    public Date getNotAfter() {
        return notAfter;
    }

    /**
     * 获取证书是否过期
     * @return {@code true} 过期, {@code false} 未过期
     */
    public boolean isEffective() {
        return effective;
    }

    /**
     * 获取证书发布方
     * @return 证书发布方
     */
    public String getCertPrincipal() {
        return certPrincipal;
    }

    /**
     * 获取证书版本号
     * @return 证书版本号
     */
    public String getCertVersion() {
        return certVersion;
    }

    /**
     * 获取证书算法名称
     * @return 证书算法名称
     */
    public String getCertSigAlgName() {
        return certSigAlgName;
    }

    /**
     * 获取证书算法 OID
     * @return 证书算法 OID
     */
    public String getCertSigAlgOID() {
        return certSigAlgOID;
    }

    /**
     * 获取证书机器码
     * @return 证书机器码
     */
    public String getCertSerialnumber() {
        return certSerialnumber;
    }

    /**
     * 获取证书 DER 编码
     * @return 证书 DER 编码
     */
    public String getCertDERCode() {
        return certDERCode;
    }
}