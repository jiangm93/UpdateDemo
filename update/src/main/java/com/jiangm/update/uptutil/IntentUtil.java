
package com.jiangm.update.uptutil;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

/**
 * 网络状态检查
 */
public class IntentUtil {

    public static interface Type {
        String _WIFI = "WIFI";
        String _GPRS = "GPRS";
        String _CMWAP = "CMWAP";
    }

    /**
     * 监测当前时候是否有可用网络
     *
     * @param context
     * @return 返回true，当前有可用网络 返回false，当前无可用网络
     */
    public static boolean isConnect(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

//    public static boolean isConnecting(Context context) {
//        try {
//            InetAddress ad = InetAddress.getByName(context.getResources().getString(R.string.host));
//            boolean state = false;// 测试是否可以达到该地址
//            try {
//                state = ad.isReachable(5000);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (state) {
//                System.out.println("连接成功" + ad.getHostAddress());
//                return true;
//            } else {
//                System.err.println("连接失败");
//                return false;
//            }
//        } catch (UnknownHostException e) {
//            System.err.println("连接失败");
//            return false;
//        }
//
//        // return false;
//    }

    /**
     * 获取网络类型
     * 
     * @param mContext
     * @return
     */
    public static String getNetType(Context mContext) {
        ConnectivityManager manager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        State stategprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        State statewifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (State.CONNECTED == statewifi && State.CONNECTED == stategprs) {
            return Type._WIFI;
        }
        if (State.CONNECTED != stategprs && State.CONNECTED == statewifi) {
            return Type._WIFI;
        }
        if (State.CONNECTED != statewifi && State.CONNECTED == stategprs) {
            Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
            try {
                ContentResolver cr = mContext.getContentResolver();
                Cursor cursor = cr.query(PREFERRED_APN_URI,
                        new String[] {
                                "_id", "apn", "type"
                        }, null, null, null);
                cursor.moveToFirst();
                if (cursor.isAfterLast()) {
                    return Type._GPRS;
                }
                String apn = cursor.getString(1);
                if (apn.toUpperCase().equals("CMWAP")) {
                    return Type._CMWAP;
                } else if (apn.toUpperCase().equals("CMNET")) {
                    return Type._GPRS;
                } else {
                    return Type._GPRS;
                }
            } catch (Exception ep) {
                ep.printStackTrace();
            }
        }
        return Type._GPRS;
    }

    /**
     * 获取当前版本标示号
     * 
     * @param mContext
     * @return
     */
    public static int getCurrentVersionCode(Context mContext) {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取当前版本号
     * 
     * @param mContext
     * @return
     */
    public static String getCurrentVersionName(Context mContext) {
        /*
         * try { return
         * mContext.getPackageManager().getPackageInfo(mContext.getPackageName
         * (), 0).versionName; } catch (NameNotFoundException e) {
         * e.printStackTrace(); return ""; }
         */
        // 获取packagemanager的实例
        PackageManager packageManager = mContext.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 检查是否存在sd卡
     * 
     * @param mContext
     * @return
     */
    public static boolean checkSoftStage(Context mContext) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // 判断是否存在SD卡
            return true;
        } else {
            Toast.makeText(mContext, "检测到手机没有存储卡,请安装了内存卡后再升级。", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    /**
     * 监测URL地址是否有效
     * 
     * @param url
     * @return
     */
    public static boolean checkURL(String url) {
        try {
            URL u = new URL(url);
            HttpURLConnection urlConn = (HttpURLConnection) u.openConnection();
            urlConn.connect();
            if (urlConn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /* 判断是否为数字 */
    public static String isNaN(String msg) {
        Pattern pattern = Pattern.compile("^[+-]?\\d*[.]?\\d*$");
        Matcher isNum = pattern.matcher(msg);
        if (isNum.matches()) {
            return Double.parseDouble(msg) + "";
        } else {
            return "no";
        }
    }


}
