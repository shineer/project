//
// DO NOT EDIT THIS FILE.
// Generated using AndroidAnnotations 4.3.0.
// 
// You can create a larger work that contains this file and distribute that work under terms of your choice.
//

package com.ryx.payment.ruishua.usercenter;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.rey.material.widget.Button;
import com.ryx.payment.ruishua.R;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.builder.PostActivityStarter;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class RegisterEnterPhoneNumber_
    extends RegisterEnterPhoneNumber
    implements HasViews, OnViewChangedListener
{
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(R.layout.activity_register_enter_phone_number);
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

    public static RegisterEnterPhoneNumber_.IntentBuilder_ intent(Context context) {
        return new RegisterEnterPhoneNumber_.IntentBuilder_(context);
    }

    public static RegisterEnterPhoneNumber_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new RegisterEnterPhoneNumber_.IntentBuilder_(fragment);
    }

    public static RegisterEnterPhoneNumber_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new RegisterEnterPhoneNumber_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        this.mBackImg = ((ImageView) hasViews.findViewById(R.id.tileleftImg));
        this.mMsgImg = ((ImageView) hasViews.findViewById(R.id.tilerightImg));
        this.mPhoneNumber = ((EditText) hasViews.findViewById(R.id.et_phonenumber));
        this.mShowPwdView = ((ImageView) hasViews.findViewById(R.id.iv_show_pwd_status));
        this.mPwdEditText = ((EditText) hasViews.findViewById(R.id.et_password));
        this.mRegisterNext = ((Button) hasViews.findViewById(R.id.btn_register_next));
        this.mRuiShuaAgreement = ((TextView) hasViews.findViewById(R.id.tv_ruishua_agreement));
        this.mRuiShuafenRunAgreement = ((TextView) hasViews.findViewById(R.id.tv_ruishua_fenRunAgreement));
        this.mMobileLine = ((LinearLayout) hasViews.findViewById(R.id.layout_mobile));
        this.mPwdLine = ((LinearLayout) hasViews.findViewById(R.id.layout_pwd));
        this.mBranchRl = ((RelativeLayout) hasViews.findViewById(R.id.rl_branch));
        this.mBranchName = ((TextView) hasViews.findViewById(R.id.tv_branch_name));
        this.mBranchIcon = ((ImageView) hasViews.findViewById(R.id.iv_branch_icon));
        this.layout_branch = ((LinearLayout) hasViews.findViewById(R.id.layout_branch));
        this.code_layout = ((LinearLayout) hasViews.findViewById(R.id.code_layout));
        this.et_code = ((EditText) hasViews.findViewById(R.id.et_code));
        this.et_code_line = ((LinearLayout) hasViews.findViewById(R.id.et_code_line));
        this.and_tv = ((TextView) hasViews.findViewById(R.id.and_tv));
        if (this.mBranchRl!= null) {
            this.mBranchRl.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    RegisterEnterPhoneNumber_.this.showBranchList();
                }
            }
            );
        }
        if (this.mRuiShuaAgreement!= null) {
            this.mRuiShuaAgreement.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    RegisterEnterPhoneNumber_.this.showAgreement();
                }
            }
            );
        }
        if (this.mRuiShuafenRunAgreement!= null) {
            this.mRuiShuafenRunAgreement.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    RegisterEnterPhoneNumber_.this.showfenRunAgreement();
                }
            }
            );
        }
        if (this.mShowPwdView!= null) {
            this.mShowPwdView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    RegisterEnterPhoneNumber_.this.setPwdStatus();
                }
            }
            );
        }
        if (this.mBackImg!= null) {
            this.mBackImg.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    RegisterEnterPhoneNumber_.this.setBackClick();
                }
            }
            );
        }
        if (this.mRegisterNext!= null) {
            this.mRegisterNext.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    RegisterEnterPhoneNumber_.this.onNextClick();
                }
            }
            );
        }
        if (this.mPhoneNumber!= null) {
            this.mPhoneNumber.setOnFocusChangeListener(new OnFocusChangeListener() {

                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    RegisterEnterPhoneNumber_.this.focusChange(view, hasFocus);
                }
            }
            );
        }
        if (this.mPwdEditText!= null) {
            this.mPwdEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    RegisterEnterPhoneNumber_.this.focusChange(view, hasFocus);
                }
            }
            );
        }
        this.rl_branch = this.mBranchRl;
        initViews();
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<RegisterEnterPhoneNumber_.IntentBuilder_>
    {
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, RegisterEnterPhoneNumber_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), RegisterEnterPhoneNumber_.class);
            fragment_ = fragment;
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            super(fragment.getActivity(), RegisterEnterPhoneNumber_.class);
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
