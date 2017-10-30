//
// DO NOT EDIT THIS FILE.
// Generated using AndroidAnnotations 4.3.0.
// 
// You can create a larger work that contains this file and distribute that work under terms of your choice.
//

package com.ryx.payment.ruishua.bindcard;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.TextView;
import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;
import com.ryx.payment.ruishua.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.builder.PostActivityStarter;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class MyCreditCardActivity_
    extends MyCreditCardActivity
    implements HasViews, OnViewChangedListener
{
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(R.layout.activity_my_credit_card);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static MyCreditCardActivity_.IntentBuilder_ intent(Context context) {
        return new MyCreditCardActivity_.IntentBuilder_(context);
    }

    public static MyCreditCardActivity_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new MyCreditCardActivity_.IntentBuilder_(fragment);
    }

    public static MyCreditCardActivity_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new MyCreditCardActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        this.edt_userName = ((EditText) hasViews.findViewById(R.id.edt_userName));
        this.edt_cardType = ((EditText) hasViews.findViewById(R.id.edt_cardType));
        this.edt_date = ((EditText) hasViews.findViewById(R.id.edt_date));
        this.edt_securityCode = ((EditText) hasViews.findViewById(R.id.edt_securityCode));
        this.edt_phoneNum = ((EditText) hasViews.findViewById(R.id.edt_phoneNum));
        this.cb_agree = ((CheckBox) hasViews.findViewById(R.id.cb_agree));
        this.tv_payProtocols = ((TextView) hasViews.findViewById(R.id.tv_payProtocols));
        this.btn_next = ((Button) hasViews.findViewById(R.id.btn_next));
        this.mMacEt = ((EditText) hasViews.findViewById(R.id.edt_mac));
        this.mSendMacTv = ((TextView) hasViews.findViewById(R.id.tv_again_mac));
        this.lay_contract = ((AutoRelativeLayout) hasViews.findViewById(R.id.lay_contract));
        this.edt_date_linerlayout = ((AutoLinearLayout) hasViews.findViewById(R.id.edt_date_linerlayout));
        this.securityCode_linerlayout = ((AutoLinearLayout) hasViews.findViewById(R.id.securityCode_linerlayout));
        if (this.tv_payProtocols!= null) {
            this.tv_payProtocols.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    MyCreditCardActivity_.this.showProtocols();
                }
            }
            );
        }
        initViews();
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<MyCreditCardActivity_.IntentBuilder_>
    {
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, MyCreditCardActivity_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), MyCreditCardActivity_.class);
            fragment_ = fragment;
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            super(fragment.getActivity(), MyCreditCardActivity_.class);
            fragmentSupport_ = fragment;
        }

        @Override
        public PostActivityStarter startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent, requestCode);
            } else {
                if (fragment_!= null) {
                    if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
                        fragment_.startActivityForResult(intent, requestCode, lastOptions);
                    } else {
                        fragment_.startActivityForResult(intent, requestCode);
                    }
                } else {
                    if (context instanceof Activity) {
                        Activity activity = ((Activity) context);
                        ActivityCompat.startActivityForResult(activity, intent, requestCode, lastOptions);
                    } else {
                        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
                            context.startActivity(intent, lastOptions);
                        } else {
                            context.startActivity(intent);
                        }
                    }
                }
            }
            return new PostActivityStarter(context);
        }
    }
}