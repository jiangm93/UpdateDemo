package com.jiangm.update;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jiangm.update.uptutil.ApkInfo;
import com.jiangm.update.uptutil.DownloadManager;


public class MainActivity extends AppCompatActivity {


    private static final int WRITE_EXTERNAL_STORAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //检查版本是否大于M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE);
            }else {
                showToast("权限已申请");
            }
        }


        int versionCode = 7;
        String versionStr = "1.2.3";
        String downurl = "http://iu2018.jdiu168.com/app1.1.2.apk";
        String description = "本次更新。。。。。。。。。。。。。";
        boolean mandatory = false;
        String versionName="更新app";

        ApkInfo apkinfo = new ApkInfo(
                downurl,
                versionStr,
                null,
                versionCode,
                "dome.apk",
                description,
                versionCode,
                versionName,
                versionCode,
                mandatory ? true
                        : false);

        DownloadManager downloadManager= new DownloadManager(this,false);
        downloadManager.checkDownload(apkinfo,"http://zanchebang.20080531.com/");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("权限已申请");
            } else {
                showToast("权限已拒绝");
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showToast(String string){
        Toast.makeText(MainActivity.this,string,Toast.LENGTH_LONG).show();
    }

}
