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
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.rey.material.widget.Button;
import com.ryx.payment.ruishua.R;
import com.ryx.payment.ruishua.usercenter.adapter.WithDrawCardListAdapter_;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.builder.PostActivityStarter;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class WithdrawListImActivity_
    extends WithdrawListImActivity
    implements HasViews, OnViewChangedListener
{
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(R.layout.activity_withdraw_list_im);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        this.cardListAdapter = WithDrawCardListAdapter_.getInstance_(this);
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

    public static WithdrawListImActivity_.IntentBuilder_ intent(Context context) {
        return new WithdrawListImActivity_.IntentBuilder_(context);
    }

    public static WithdrawListImActivity_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new WithdrawListImActivity_.IntentBuilder_(fragment);
    }

    public static WithdrawListImActivity_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new WithdrawListImActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        this.mBackImg = ((ImageView) hasViews.findViewById(R.id.tileleftImg));
        this.mAddCardImg = ((ImageView) hasViews.findViewById(R.id.tilerightImg));
        this.mShowBankList = ((SwipeMenuListView) hasViews.findViewById(R.id.lv_show_bank));
        this.mWithDrawMoney = ((EditText) hasViews.findViewById(R.id.et_withdraw_balance));
        this.mWithDrawPwd = ((EditText) hasViews.findViewById(R.id.et_withdraw_pwd));
        this.mWithDrawMoneyLine = ((LinearLayout) hasViews.findViewById(R.id.ll_withdraw_balance));
        this.mWithDrawPwdLine = ((LinearLayout) hasViews.findViewById(R.id.ll_withdraw_pwd));
        this.mNormalActionLL = ((LinearLayout) hasViews.findViewById(R.id.ll_action_normal));
        this.mImmediateActionLL = ((LinearLayout) hasViews.findViewById(R.id.ll_action_immediate));
        this.mNormalActionTv = ((TextView) hasViews.findViewById(R.id.tv_normal));
        this.mNormalActionTipTv = ((TextView) hasViews.findViewById(R.id.tv_normal_tip));
        this.mImmediateActionTv = ((TextView) hasViews.findViewById(R.id.tv_immediate));
        this.mImmediateActionTipTv = ((TextView) hasViews.findViewById(R.id.tv_immediate_tip));
        this.mCommitBtn = ((Button) hasViews.findViewById(R.id.btn_true));
        this.mWithDrawTips = ((TextView) hasViews.findViewById(R.id.tv_tip_balance));
        this.mMobileMac = ((EditText) hasViews.findViewById(R.id.et_mac));
        this.mWithDrawMacLine = ((LinearLayout) hasViews.findViewById(R.id.ll_withdraw_mac));
        this.mMacSendTv = ((TextView) hasViews.findViewById(R.id.tv_again_mac));
        this.mWithDrawAll = ((TextView) hasViews.findViewById(R.id.tv_withdraw_all));
        this.warmprompt_tv = ((TextView) hasViews.findViewById(R.id.warmprompt_tv));
        if (this.mBackImg!= null) {
            this.mBackImg.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    WithdrawListImActivity_.this.closeWindow();
                }
            }
            );
        }
        if (this.mMacSendTv!= null) {
            this.mMacSendTv.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    WithdrawListImActivity_.this.sendMac();
                }
            }
            );
        }
        if (this.mImmediateActionLL!= null) {
            this.mImmediateActionLL.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    WithdrawListImActivity_.this.immediateChecked();
                }
            }
            );
        }
        if (this.mNormalActionLL!= null) {
            this.mNormalActionLL.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    WithdrawListImActivity_.this.normalChecked();
                }
            }
            );
        }
        if (this.mCommitBtn!= null) {
            this.mCommitBtn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    WithdrawListImActivity_.this.withDrawCommit();
                }
            }
            );
        }
        if (this.mWithDrawAll!= null) {
            this.mWithDrawAll.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    WithdrawListImActivity_.this.setWithDrawAll();
                }
            }
            );
        }
        if (this.mAddCardImg!= null) {
            this.mAddCardImg.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    WithdrawListImActivity_.this.addCard();
                }
            }
            );
        }
        if (this.mWithDrawMoney!= null) {
            this.mWithDrawMoney.setOnFocusChangeListener(new OnFocusChangeListener() {

                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    WithdrawListImActivity_.this.focusChange(view, hasFocus);
                }
            }
            );
        }
        if (this.mWithDrawPwd!= null) {
            this.mWithDrawPwd.setOnFocusChangeListener(new OnFocusChangeListener() {

                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    WithdrawListImActivity_.this.focusChange(view, hasFocus);
                }
            }
            );
        }
        if (this.mMobileMac!= null) {
            this.mMobileMac.setOnFocusChangeListener(new OnFocusChangeListener() {

                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    WithdrawListImActivity_.this.focusChange(view, hasFocus);
                }
            }
            );
        }
        initViews();
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<WithdrawListImActivity_.IntentBuilder_>
    {
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, WithdrawListImActivity_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), WithdrawListImActivity_.class);
            fragment_ = fragment;
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            super(fragment.getActivity(), WithdrawListImActivity_.class);
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
