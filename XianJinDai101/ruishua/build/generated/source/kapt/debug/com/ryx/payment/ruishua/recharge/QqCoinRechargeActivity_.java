//
// DO NOT EDIT THIS FILE.
// Generated using AndroidAnnotations 4.3.0.
// 
// You can create a larger work that contains this file and distribute that work under terms of your choice.
//

package com.ryx.payment.ruishua.recharge;

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
import com.ryx.payment.ruishua.R;
import com.ryx.payment.ruishua.recharge.adapter.QqCoinAccountAdapter_;
import com.ryx.payment.ruishua.widget.RyxGridView;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.builder.PostActivityStarter;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class QqCoinRechargeActivity_
    extends QqCoinRechargeActivity
    implements HasViews, OnViewChangedListener
{
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(R.layout.activity_qq_coin_recharge);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        this.gridViewAdapter = QqCoinAccountAdapter_.getInstance_(this);
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

    public static QqCoinRechargeActivity_.IntentBuilder_ intent(Context context) {
        return new QqCoinRechargeActivity_.IntentBuilder_(context);
    }

    public static QqCoinRechargeActivity_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new QqCoinRechargeActivity_.IntentBuilder_(fragment);
    }

    public static QqCoinRechargeActivity_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new QqCoinRechargeActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        this.edt_accout = ((EditText) hasViews.findViewById(R.id.edt_accout));
        this.gv_account = ((RyxGridView) hasViews.findViewById(R.id.gv_account));
        View view_tilerightImg = hasViews.findViewById(R.id.tilerightImg);
        View view_tileleftImg = hasViews.findViewById(R.id.tileleftImg);
        View view_btn_recharge = hasViews.findViewById(R.id.btn_recharge);

        if (view_tilerightImg!= null) {
            view_tilerightImg.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    QqCoinRechargeActivity_.this.showHelp();
                }
            }
            );
        }
        if (view_tileleftImg!= null) {
            view_tileleftImg.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    QqCoinRechargeActivity_.this.closeWindow();
                }
            }
            );
        }
        if (view_btn_recharge!= null) {
            view_btn_recharge.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    QqCoinRechargeActivity_.this.doRecharge();
                }
            }
            );
        }
        initViews();
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<QqCoinRechargeActivity_.IntentBuilder_>
    {
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, QqCoinRechargeActivity_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), QqCoinRechargeActivity_.class);
            fragment_ = fragment;
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            super(fragment.getActivity(), QqCoinRechargeActivity_.class);
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
