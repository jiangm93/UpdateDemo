
package com.jiangm.update.uptutil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jiangm.update.R;


public class DownloadInfoDialog extends Dialog {

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
    private TextView dialogContent;
    private String comfire;
    private String cancel;
    private String description;
    public DownloadInfoDialog(Context context) {
        this(context, 0, null,null, null);
    }

    public DownloadInfoDialog(Context context, int theme, String text, String description,
                              DownloadInfoDialogResult result) {
        super(context, theme);
        this.dialogResult = result;
        this.text = text;
        this.description=description;
    }


    public DownloadInfoDialog(Context context, int theme, String text, String description, String comfire, String cancel,
                              DownloadInfoDialogResult result) {
        super(context, theme);
        this.dialogResult = result;
        this.text = text;
        this.comfire = comfire;
        this.cancel=cancel;
        this.description=description;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dwonload_dialog_info);
        this.setCancelable(false);
        dialogCancel = (TextView) findViewById(R.id.dialog_cancel);
        dialogConfirm = (TextView) findViewById(R.id.dialog_confirm);
        dialogTitle = (TextView) findViewById(R.id.dialog_title);
        dialogContent = (TextView) findViewById(R.id.dialog_content);


        dialogTitle.setText(text);
        if (!TextUtils.isEmpty(comfire)) {
            dialogConfirm.setText(comfire);
        }
        if(!TextUtils.isEmpty(cancel)){
            dialogCancel.setText(cancel);
        }
        if(!TextUtils.isEmpty(description)){
            dialogContent.setText(description.replace("#@@#","\n"));
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

    }

    public void setDialogConfirmText(String texts){
        dialogConfirm.setText(texts);
    }
}
