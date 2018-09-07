
package com.jiangm.update.uptutil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.jiangm.update.R;

import java.io.InputStream;

import rx.Subscriber;

/**
 * 下载管理
 */
public class DownloadManager {

    private Context mContext;

    final static int CHECK_FAIL = 0;
    final static int CHECK_SUCCESS = 1;
    final static int CHECK_NOUPGRADE = 2;
    final static int CHECK_NETFAIL = 3;

    private ApkInfo apkinfo;
    //  private ProgressDialog progressDialog;
    private String verName;
    private int verCode;

    private boolean isAccord; // 是否主动检查软件升级
//    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public String BASEURL="";

    public DownloadManager(Context mContext, boolean isAccord) {
        this.mContext = mContext;
        this.isAccord = isAccord;
        verName = IntentUtil.getCurrentVersionName(mContext);
        verCode = IntentUtil.getCurrentVersionCode(mContext);
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
//            if (progressDialog != null) {
//                progressDialog.dismiss();
//            }
            switch (msg.what) {
                case CHECK_SUCCESS: {
                    showNoticeDialog();
                    break;
                }
                case CHECK_NOUPGRADE: { // 不需要更新
//                    if (isAccord)
//                        Toast.makeText(mContext, "当前版本是最新版", Toast.LENGTH_SHORT).show();
                    Intent intents = new Intent("com.jiangm.update.UPDATECANCEL");
                    mContext.sendBroadcast(intents);
                    break;
                }
                case CHECK_NETFAIL: {
                    if (isAccord)
                        Toast.makeText(mContext, "网络连接不正常", Toast.LENGTH_SHORT).show();
                    Intent intents = new Intent("com.jiangm.update.UPDATECANCEL");
                    mContext.sendBroadcast(intents);
                    break;
                }
                case CHECK_FAIL: {
//                    if (isAccord)
//                        Toast.makeText(mContext, "从服务器获取更新数据失败", Toast.LENGTH_SHORT).show();
                    Intent intents = new Intent("com.jiangm.update.UPDATECANCEL");
                    mContext.sendBroadcast(intents);
                    break;
                }
            }
        };
    };

    /* 检查下载更新 [apk下载入口] */
    public void checkDownload(ApkInfo apkinfo,String baseurl) {
        this.BASEURL = baseurl;
        this.apkinfo = apkinfo;
        if (!IntentUtil.isConnect(mContext)) { // 检查网络连接是否正常
            handler.sendEmptyMessage(CHECK_NETFAIL);
        }else{

            if (apkinfo != null && checkApkVercode()) {// 检查版本号
                handler.sendEmptyMessage(CHECK_SUCCESS);
            } else {
                handler.sendEmptyMessage(CHECK_NOUPGRADE);
            }
        }

    }
    private DownloadInstall downCallback;
    /* 弹出软件更新提示对话框 */
    private void showNoticeDialog() {


        StringBuffer sb = new StringBuffer();
        sb.append("最新版本号： "+apkinfo.getVersionName()+"\n")
                //   .append("文件大小："+apkinfo.getApkSize()+"\n")
                .append(apkinfo.getApkLog());
        DownloadInfoDialog downloadInfoDialog=new DownloadInfoDialog(mContext, R.style.InfoDialog, "检测到新版本",sb.toString(), "立即更新", "不，谢谢",new DownloadInfoDialog.DownloadInfoDialogResult() {

            @Override
            public void uploadResult() {

                downCallback = new DownloadInstall(mContext, Const.apkSavepath+apkinfo.getApkName(), apkinfo
                        .getVersionCode(), apkinfo.getApkCode(), apkinfo.isFourceUpdate());
                DownloadUtils downloadUtils= new DownloadUtils(BASEURL, new JsDownloadListener() {
                    @Override
                    public void onStartDownload() {
                        downCallback.onDownloadPreare();
                    }

                    @Override
                    public void onProgress(int progress) {
                        downCallback.onChangeProgress(progress);

                    }

                    @Override
                    public void onFinishDownload() {
                        downCallback.onCompleted(true,"下载完成");
                    }

                    @Override
                    public void onFail(String errorInfo) {
                        downCallback.onCancel();
                    }
                });
                downloadUtils.download(apkinfo.getDownloadUrl(),Const.apkSavepath+apkinfo.getApkName(),new Subscriber<InputStream>(){
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        downCallback.onCompleted(false,"下载失败，请检查网络");
                        downCallback.onCancel();
                    }

                    @Override
                    public void onNext(InputStream responseBody) {
                        downCallback.onCompleted(true,"下载完成");
                    }
                });

            }

            @Override
            public void cancelResult() {



                if (apkinfo.isFourceUpdate()){
                    android.os.Process.killProcess(android.os.Process.myPid());   //获取PID
                    System.exit(0);
                }else{
                    Intent intent = new Intent("com.jiangm.update.UPDATECANCEL");
                    mContext.sendBroadcast(intent);
                }
            }
        });
        downloadInfoDialog.show();


    }

    /**
     * 检查版本是否需要更新
     *
     * @return
     */
    private boolean checkApkVercode() {
        double versionCode = Double.valueOf(verCode);
        if (apkinfo.getVersionCode() > versionCode) {
            return true;
        } else {
            return false;
        }
    }

}
