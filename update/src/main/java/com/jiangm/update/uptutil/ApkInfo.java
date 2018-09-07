
package com.jiangm.update.uptutil;

import java.io.Serializable;

/**
 * apk更新信息
 */
public class ApkInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String downloadUrl; // 下载地址
    private String apkVersion; // apk版本
    private String apkSize; // apk文件大小
    private int apkCode; // apk版本号(更新必备)
    private String apkName; // apk名字
    private String apkLog; // apk更新日志
    private boolean fourceUpdate;//是否强制更新
    private int minVersionCode;//最小版本号
    private String versionName;//版本名
    private int versionCode;//版本号

    public ApkInfo(String downloadUrl, String apkVersion, String apkSize,
                   int apkCode, String apkName, String apkLog, int minVersionCode, String versionName,int versionCode, boolean fourceUpdate) {
        super();
        this.downloadUrl = downloadUrl;
        this.apkVersion = apkVersion;
        this.apkSize = apkSize;
        this.apkCode = apkCode;
        this.apkName = apkName;
        this.apkLog = apkLog;
        this.fourceUpdate=fourceUpdate;
        this.minVersionCode=minVersionCode;
        this.versionName=versionName;
        this.versionCode = versionCode;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public int getMinVersionCode() {
        return minVersionCode;
    }

    public void setMinVersionCode(int minVersionCode) {
        this.minVersionCode = minVersionCode;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isFourceUpdate() {
        return fourceUpdate;
    }

    public void setFourceUpdate(boolean fourceUpdate) {
        this.fourceUpdate = fourceUpdate;
    }

    /**
     * @return the downloadUrl
     */
    public String getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * @param downloadUrl the downloadUrl to set
     */
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }


    /**
     * @return the apkSize
     */
    public String getApkSize() {
        return apkSize;
    }

    /**
     * @param apkSize the apkSize to set
     */
    public void setApkSize(String apkSize) {
        this.apkSize = apkSize;
    }

    /**
     * @return the apkLog
     */
    public String getApkLog() {
        return apkLog;
    }

    /**
     * @param apkLog the apkLog to set
     */
    public void setApkLog(String apkLog) {
        this.apkLog = apkLog;
    }

    /**
     * @return the apkName
     */
    public String getApkName() {
        return apkName;
    }

    /**
     * @param apkName the apkName to set
     */
    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    /**
     * @return the apkCode
     */
    public int getApkCode() {
        return apkCode;
    }

    /**
     * @param apkCode the apkCode to set
     */
    public void setApkCode(int apkCode) {
        this.apkCode = apkCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
