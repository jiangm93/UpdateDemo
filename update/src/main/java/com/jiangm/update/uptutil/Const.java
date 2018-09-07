
package com.jiangm.update.uptutil;

import android.os.Environment;


/**
 * 常量类
 */
public final class Const {
    /* 在多少天内不检查升级 */
    public final static int defaultMinUpdateDay = 50;

    /* 检查是否升级url */
    public final static String apkCheckUpdateUrl = "";

    public static final String apkSavepath = Environment.getExternalStorageDirectory()+"//update/";

    public static final String saveFileName = apkSavepath +"update.apk";

}
