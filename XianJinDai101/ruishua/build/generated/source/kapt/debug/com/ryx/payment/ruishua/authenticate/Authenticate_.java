//
// DO NOT EDIT THIS FILE.
// Generated using AndroidAnnotations 4.3.0.
// 
// You can create a larger work that contains this file and distribute that work under terms of your choice.
//

package com.ryx.payment.ruishua.authenticate;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.ryx.payment.ruishua.R;
import com.zhy.autolayout.AutoLinearLayout;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.builder.PostActivityStarter;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class Authenticate_
    extends Authenticate
    implements HasViews, OnViewChangedListener
{
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(R.layout.activity_authenticate);
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

    public static Authenticate_.IntentBuilder_ intent(Context context) {
        return new Authenticate_.IntentBuilder_(context);
    }

    public static Authenticate_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new Authenticate_.IntentBuilder_(fragment);
    }

    public static Authenticate_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new Authenticate_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        this.ll_show_yewuguize_geti = ((AutoLinearLayout) hasViews.findViewById(R.id.ll_show_yewuguize_geti));
        this.yewuguize_geti = ((AutoLinearLayout) hasViews.findViewById(R.id.yewuguize_geti));
        this.yewuguize_shanghu = ((AutoLinearLayout) hasViews.findViewById(R.id.yewuguize_shanghu));
        this.iv_renzheng_2 = ((ImageView) hasViews.findViewById(R.id.iv_renzheng_2));
        this.iv_renzheng_1 = ((ImageView) hasViews.findViewById(R.id.iv_renzheng_1));
        this.tv_rule1 = ((TextView) hasViews.findViewById(R.id.tv_rule1));
        this.tv_rule2 = ((TextView) hasViews.findViewById(R.id.tv_rule2));
        View view_ll_show_yewuguize_shanghu = hasViews.findViewById(R.id.ll_show_yewuguize_shanghu);
        View view_ll_shanghu = hasViews.findViewById(R.id.ll_shanghu);
        View view_ll_geti = hasViews.findViewById(R.id.ll_geti);

        if (this.ll_show_yewuguize_geti!= null) {
            this.ll_show_yewuguize_geti.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    Authenticate_.this.showYewuguizeGeti();
                }
            }
            );
        }
        if (view_ll_show_yewuguize_shanghu!= null) {
            view_ll_show_yewuguize_shanghu.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    Authenticate_.this.showYewuguizeShanghu();
                }
            }
            );
        }
        if (view_ll_shanghu!= null) {
            view_ll_shanghu.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    Authenticate_.this.toShanghuMerchantInfoAddAct();
                }
            }
            );
        }
        if (view_ll_geti!= null) {
            view_ll_geti.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    Authenticate_.this.userinfoaddAct();
                }
            }
            );
        }
        afterView();
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<Authenticate_.IntentBuilder_>
    {
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, Authenticate_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), Authenticate_.class);
            fragment_ = fragment;
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            super(fragment.getActivity(), Authenticate_.class);
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