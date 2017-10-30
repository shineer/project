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
import android.widget.TextView;
import com.rey.material.widget.Button;
import com.ryx.payment.ruishua.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.builder.PostActivityStarter;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class ResetPassword_
    extends ResetPassword
    implements HasViews, OnViewChangedListener
{
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(R.layout.activity_reset_password);
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

    public static ResetPassword_.IntentBuilder_ intent(Context context) {
        return new ResetPassword_.IntentBuilder_(context);
    }

    public static ResetPassword_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new ResetPassword_.IntentBuilder_(fragment);
    }

    public static ResetPassword_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new ResetPassword_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        this.devtypearl = ((AutoRelativeLayout) hasViews.findViewById(R.id.devtypearl));
        this.mBackImg = ((ImageView) hasViews.findViewById(R.id.tileleftImg));
        this.mMsgImg = ((ImageView) hasViews.findViewById(R.id.tilerightImg));
        this.mMacEditText = ((EditText) hasViews.findViewById(R.id.et_mac));
        this.mAgainMacTextView = ((TextView) hasViews.findViewById(R.id.tv_again_mac));
        this.mPhoneNumberEditText = ((EditText) hasViews.findViewById(R.id.et_phonenumber));
        this.mResetNextBtn = ((Button) hasViews.findViewById(R.id.btn_reset_next));
        this.mUserNameEdit = ((EditText) hasViews.findViewById(R.id.et_username));
        this.mIdentificationCardEdit = ((EditText) hasViews.findViewById(R.id.et_identification_card));
        this.mPhoneNumberLL = ((LinearLayout) hasViews.findViewById(R.id.ll_phonenumber));
        this.mUserNameLL = ((LinearLayout) hasViews.findViewById(R.id.ll_username));
        this.mIdentificationCardLL = ((LinearLayout) hasViews.findViewById(R.id.ll_identification_card));
        this.mMacLL = ((LinearLayout) hasViews.findViewById(R.id.ll_mac));
        this.devtypeImg = ((ImageView) hasViews.findViewById(R.id.devtypeImg));
        this.mBranchNametv = ((TextView) hasViews.findViewById(R.id.mBranchNametv));
        this.devtypeallyout = ((AutoLinearLayout) hasViews.findViewById(R.id.devtypeallyout));
        this.linelineyout = ((LinearLayout) hasViews.findViewById(R.id.linelineyout));
        if (this.devtypearl!= null) {
            this.devtypearl.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    ResetPassword_.this.selectdevtypelist();
                }
            }
            );
        }
        if (this.mBackImg!= null) {
            this.mBackImg.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    ResetPassword_.this.setBackClick();
                }
            }
            );
        }
        if (this.mAgainMacTextView!= null) {
            this.mAgainMacTextView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    ResetPassword_.this.sendMac();
                }
            }
            );
        }
        if (this.mResetNextBtn!= null) {
            this.mResetNextBtn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    ResetPassword_.this.resetNextClick();
                }
            }
            );
        }
        if (this.mPhoneNumberEditText!= null) {
            this.mPhoneNumberEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    ResetPassword_.this.focusChange(view, hasFocus);
                }
            }
            );
        }
        if (this.mUserNameEdit!= null) {
            this.mUserNameEdit.setOnFocusChangeListener(new OnFocusChangeListener() {

                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    ResetPassword_.this.focusChange(view, hasFocus);
                }
            }
            );
        }
        if (this.mIdentificationCardEdit!= null) {
            this.mIdentificationCardEdit.setOnFocusChangeListener(new OnFocusChangeListener() {

                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    ResetPassword_.this.focusChange(view, hasFocus);
                }
            }
            );
        }
        if (this.mMacEditText!= null) {
            this.mMacEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    ResetPassword_.this.focusChange(view, hasFocus);
                }
            }
            );
        }
        initViews();
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<ResetPassword_.IntentBuilder_>
    {
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, ResetPassword_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), ResetPassword_.class);
            fragment_ = fragment;
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            super(fragment.getActivity(), ResetPassword_.class);
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
