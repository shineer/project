//
// DO NOT EDIT THIS FILE.
// Generated using AndroidAnnotations 4.3.0.
// 
// You can create a larger work that contains this file and distribute that work under terms of your choice.
//

package com.ryx.payment.ruishua.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ryx.payment.ruishua.R;
import org.androidannotations.api.builder.FragmentBuilder;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class UserFragment_
    extends com.ryx.payment.ruishua.fragment.UserFragment
    implements HasViews, OnViewChangedListener
{
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private View contentView_;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    @Override
    public View findViewById(int id) {
        if (contentView_ == null) {
            return null;
        }
        return contentView_.findViewById(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(R.layout.frag_main_user, container, false);
        }
        return contentView_;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView_ = null;
        user_withdrawim_rl = null;
        userinfo_rl = null;
        mUserName = null;
        mPhoneNumber = null;
        mUserLevelImg = null;
        mMyMessageRl = null;
        mMessageCount = null;
        mTransferRl = null;
        mKeFuRl = null;
        mCoupon = null;
        rl_userauthresult = null;
        tv_balance = null;
        tv_drysk = null;
        dayTradeAllayout = null;
        tv_userinfomsg = null;
        iv_user_photo = null;
        iv_dev_use = null;
        iv_user_mymessage = null;
        iv_userauthresult = null;
        iv_user_coupon = null;
        iv_user_code = null;
        iv_user_kefu = null;
        iv_user_transfer = null;
        iv_user_withdrawim = null;
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        create();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static UserFragment_.FragmentBuilder_ builder() {
        return new UserFragment_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        this.user_withdrawim_rl = ((RelativeLayout) hasViews.findViewById(R.id.rl_user_withdrawim));
        this.userinfo_rl = ((RelativeLayout) hasViews.findViewById(R.id.userinfo_rl));
        this.mUserName = ((TextView) hasViews.findViewById(R.id.tv_user_name));
        this.mPhoneNumber = ((TextView) hasViews.findViewById(R.id.tv_user_phonenumber));
        this.mUserLevelImg = ((TextView) hasViews.findViewById(R.id.tv_user_level));
        this.mMyMessageRl = ((RelativeLayout) hasViews.findViewById(R.id.rl_user_message));
        this.mMessageCount = ((TextView) hasViews.findViewById(R.id.tv_user_bg_msg_number));
        this.mTransferRl = ((RelativeLayout) hasViews.findViewById(R.id.rl_user_transfer));
        this.mKeFuRl = ((RelativeLayout) hasViews.findViewById(R.id.rl_user_kefu));
        this.mCoupon = ((RelativeLayout) hasViews.findViewById(R.id.rl_user_coupon));
        this.rl_userauthresult = ((RelativeLayout) hasViews.findViewById(R.id.rl_userauthresult));
        this.tv_balance = ((TextView) hasViews.findViewById(R.id.tv_balance));
        this.tv_drysk = ((TextView) hasViews.findViewById(R.id.tv_drysk));
        this.dayTradeAllayout = ((LinearLayout) hasViews.findViewById(R.id.dayTradeAllayout));
        this.tv_userinfomsg = ((TextView) hasViews.findViewById(R.id.tv_userinfomsg));
        this.iv_user_photo = ((ImageView) hasViews.findViewById(R.id.iv_user_photo));
        this.iv_dev_use = ((ImageView) hasViews.findViewById(R.id.iv_dev_use));
        this.iv_user_mymessage = ((ImageView) hasViews.findViewById(R.id.iv_user_mymessage));
        this.iv_userauthresult = ((ImageView) hasViews.findViewById(R.id.iv_userauthresult));
        this.iv_user_coupon = ((ImageView) hasViews.findViewById(R.id.iv_user_coupon));
        this.iv_user_code = ((ImageView) hasViews.findViewById(R.id.iv_user_code));
        this.iv_user_kefu = ((ImageView) hasViews.findViewById(R.id.iv_user_kefu));
        this.iv_user_transfer = ((ImageView) hasViews.findViewById(R.id.iv_user_transfer));
        this.iv_user_withdrawim = ((ImageView) hasViews.findViewById(R.id.iv_user_withdrawim));
        View view_rl_dev_use = hasViews.findViewById(R.id.rl_dev_use);
        View view_rl_user_code = hasViews.findViewById(R.id.rl_user_code);

        if (this.mMyMessageRl!= null) {
            this.mMyMessageRl.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    UserFragment_.this.setUserMessage(view);
                }
            }
            );
        }
        if (this.mCoupon!= null) {
            this.mCoupon.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    UserFragment_.this.setUserCoupon(view);
                }
            }
            );
        }
        if (view_rl_dev_use!= null) {
            view_rl_dev_use.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    UserFragment_.this.setDevUse(view);
                }
            }
            );
        }
        if (view_rl_user_code!= null) {
            view_rl_user_code.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    UserFragment_.this.setuserCodeClick(view);
                }
            }
            );
        }
        if (this.rl_userauthresult!= null) {
            this.rl_userauthresult.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    UserFragment_.this.setUserauthresult(view);
                }
            }
            );
        }
        if (this.mTransferRl!= null) {
            this.mTransferRl.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    UserFragment_.this.setUserTransfer(view);
                }
            }
            );
        }
        if (this.userinfo_rl!= null) {
            this.userinfo_rl.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    UserFragment_.this.gotoRealName(view);
                }
            }
            );
        }
        if (this.mKeFuRl!= null) {
            this.mKeFuRl.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    UserFragment_.this.setKeFu();
                }
            }
            );
        }
        if (this.user_withdrawim_rl!= null) {
            this.user_withdrawim_rl.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    UserFragment_.this.setUser_withdrawim_rl(view);
                }
            }
            );
        }
        if (this.dayTradeAllayout!= null) {
            this.dayTradeAllayout.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    UserFragment_.this.showdayTrade();
                }
            }
            );
        }
        initViews();
    }

    public static class FragmentBuilder_
        extends FragmentBuilder<UserFragment_.FragmentBuilder_, com.ryx.payment.ruishua.fragment.UserFragment>
    {

        @Override
        public com.ryx.payment.ruishua.fragment.UserFragment build() {
            UserFragment_ fragment_ = new UserFragment_();
            fragment_.setArguments(args);
            return fragment_;
        }
    }
}