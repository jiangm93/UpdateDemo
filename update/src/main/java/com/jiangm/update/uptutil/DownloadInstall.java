
package com.jiangm.update.uptutil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;


import com.jiangm.update.R;

import java.io.File;

/**
 * Created by dengt on 15-1-4.
 */
public class DownloadInstall implements DownloadCallback {

    private Context mContext;
    private String apkPath;
    private double apkVersion;
    private int apkCode;
    private boolean forceFlag;
    private LayoutInflater inflater;

    private TextView textView;
    private CircleProgressBar progressView;
    private DownloadDialog downloadDialog; // 下载弹出框
    private boolean interceptFlag = false; // 是否取消下载

    public DownloadInstall(Context mContext, String apkPath, double apkVersion, int apkCode, boolean forceFlag) {
        this.mContext = mContext;
        this.apkCode = apkCode;
        this.apkPath = apkPath;
        this.apkVersion = apkVersion;
        this.forceFlag=forceFlag;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public boolean onCancel() {
        return interceptFlag;
    }

    @Override
    public void onChangeProgress(int progress) {
        progressView.setCurrentProgress(progress); // 设置下载进度
//        textView.setText("进度：" + progress + "%");
    }

    @Override
    public void onCompleted(boolean success, String errorMsg) {

        if (success) { // 更新成功

//            installApk();
            if (downloadDialog != null) {
                progressView.setTopTitle("已完成");
                downloadDialog.setDialogConfirmText("马上安装");
                downloadDialog.setConfirmVisiable();
                downloadDialog.setCancelGone();

            }

        } else {
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDownloadPreare() {
        if (IntentUtil.checkSoftStage(mContext)) {
            File file = new File(Const.apkSavepath);
            if (!file.exists()) {
                file.mkdir();
            }
//            View view = inflater.inflate(R.layout.upgrade_apk, null);
//            textView = (TextView) view.findViewById(R.id.progressCount_text);
//            textView.setText("进度：0");
//            progressView = (CircleProgressBar) view.findViewById(R.id.progressbar);
//            progressView.setMaxProgress(100);
            downloadDialog=new DownloadDialog(mContext, R.style.InfoDialog, "正在下载新版本", new DownloadDialog.DownloadInfoDialogResult() {
                @Override
                public void uploadResult() {
                    installApk();
                }

                @Override
                public void cancelResult() {
                    interceptFlag = true;
                    if(forceFlag){
                        android.os.Process.killProcess(android.os.Process.myPid());   //获取PID
                        System.exit(0);
                    }else{
                        Intent intent = new Intent("com.jiangm.update.UPDATECANCEL");
                        mContext.sendBroadcast(intent);
                    }
                }
            });
            downloadDialog.show();
            progressView=downloadDialog.getProgressBar();
        }
    }

    /**
     * 安装apk
     */
    private void installApk() {
        File file = new File(apkPath);
        if (!file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, "com.jiangm.update.fileprovider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        }else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }

        mContext.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
