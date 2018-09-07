
package com.jiangm.update.uptutil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiangm.update.R;


public class DownloadDialog extends Dialog {

    public interface DownloadInfoDialogResult {
        void uploadResult();
        void cancelResult();
    }

    private Long targetId;
    private String text;
    private DownloadInfoDialogResult dialogResult;
    private TextView dialogCancel;
    private TextView dialogConfirm;
    private TextView dialogTitle;
    private String comfire;
    private String cancel;
    private String description;
    private LinearLayout progressView;
    private View view;
    private CircleProgressBar progressBar;
    public DownloadDialog(Context context) {
        this(context, 0, null, null);
    }

    public DownloadDialog(Context context, int theme, String text,
                          DownloadInfoDialogResult result) {
        super(context, theme);
        this.dialogResult = result;
        this.text = text;
//        this.view=view;

    }


    public DownloadDialog(Context context, int theme, String text, String comfire, String cancel,
                          DownloadInfoDialogResult result) {
        super(context, theme);
        this.dialogResult = result;
        this.text = text;
        this.comfire = comfire;
        this.cancel=cancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.download_dialog);
        this.setCancelable(false);
        dialogCancel = (TextView) findViewById(R.id.dialog_cancel);
        dialogConfirm = (TextView) findViewById(R.id.dialog_confirm);
//        dialogTitle = (TextView) findViewById(R.id.dialog_title);

//        progressView= (LinearLayout) findViewById(R.id.progressView);
        progressBar = (CircleProgressBar)findViewById(R.id.progressbar);

//        dialogTitle.setText(text);
        if (!TextUtils.isEmpty(comfire)) {
            dialogConfirm.setText(comfire);
        }
        if(!TextUtils.isEmpty(cancel)){
            dialogCancel.setText(cancel);
        }

        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogResult.cancelResult();
                cancel();
            }
        });
        dialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogResult.uploadResult();
                cancel();
            }
        });

//        setProgressView(view);
    }
//    public  void setProgressView(View view){
//        progressView.addView(view);
//    }

    public void setDialogConfirmText(String texts){
        dialogConfirm.setText(texts);
    }

    public void setConfirmVisiable(){
        dialogConfirm.setVisibility(View.VISIBLE);
    }
    public void setCancelGone(){
        dialogCancel.setVisibility(View.GONE);
    }

    public CircleProgressBar getProgressBar(){
        return progressBar;
    }

}
