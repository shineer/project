package com.ryx.payment.ruishua.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rey.material.app.Dialog;
import com.rey.material.widget.Button;
import com.ryx.payment.ruishua.R;
import com.ryx.payment.ruishua.listener.ConFirmDialogListener;
import com.ryx.quickadapter.inter.NoDoubleClickListener;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * Created by XCC on 2016/7/1.
 */
public class RyxSimpleConfirmDialog extends Dialog {
    TextView contenttv;
    Button cancel_btn,ok_btn,onlyok_btn;
    Context context;
    String address;
    ConFirmDialogListener dialogListener;
    AutoLinearLayout canokalllautoLinearLayout,onlyokLinearlayout;
    public RyxSimpleConfirmDialog(Context context) {
        super(context, R.style.SimpleDialogLight);
    }
    public RyxSimpleConfirmDialog(Context context,ConFirmDialogListener listener) {
        super(context, R.style.SimpleDialogLight);
        this.dialogListener=listener;
    }

    public RyxSimpleConfirmDialog(Context context, int theme,ConFirmDialogListener listener) {
        super(context, theme);
        this.context = context;
        this.dialogListener=listener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ryxsimpleconfirm);
        canokalllautoLinearLayout=(AutoLinearLayout) findViewById(R.id.cancelokall);
        canokalllautoLinearLayout.setVisibility(View.VISIBLE);

        onlyokLinearlayout=(AutoLinearLayout)findViewById(R.id.onlyok_all);
        onlyokLinearlayout.setVisibility(View.GONE);
        contenttv=(TextView) findViewById(R.id.contenttv);
        cancel_btn=(Button) findViewById(R.id.cancel_btn);
        ok_btn=(Button) findViewById(R.id.ok_btn);
        onlyok_btn=(Button)findViewById(R.id.onlyok_btn);
//        onlyok_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogListener.onPositiveActionClicked(RyxSimpleConfirmDialog.this);
//            }
//        });
        onlyok_btn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialogListener.onPositiveActionClicked(RyxSimpleConfirmDialog.this);
            }
        });

        cancel_btn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                dialogListener.onNegativeActionClicked(RyxSimpleConfirmDialog.this);
            }
        });
        ok_btn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                dialogListener.onPositiveActionClicked(RyxSimpleConfirmDialog.this);
            }
        });


    }

    /**
     * 设置内容
     * @param content
     */
    public void setContent(String content){
        contenttv.setText(content);
    }
    public void setContentgravity(int gravity){
        contenttv.setGravity(gravity);
    }

    /**
     * ok按钮设置内容
     * @param content
     */
    public void setOkbtnText(String content){
        ok_btn.setText(content);
    }

    /**
     * 取消按钮设置内容
     * @param content
     */
    public void setCancelbtnText(String content){
        cancel_btn.setText(content);
    }

    /**
     * 设置只显示确定按钮
     */
    public void setOnlyokLinearlayout(){
        onlyokLinearlayout.setVisibility(View.VISIBLE);
        canokalllautoLinearLayout.setVisibility(View.GONE);
    }

    /**
     * 设置监听事件
     * @param dialogListener
     */
    public void setOnClickListen(ConFirmDialogListener dialogListener){
        this.dialogListener=dialogListener;
    }

}
