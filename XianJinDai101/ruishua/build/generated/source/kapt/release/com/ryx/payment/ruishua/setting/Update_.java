//
// DO NOT EDIT THIS FILE.
// Generated using AndroidAnnotations 4.3.0.
// 
// You can create a larger work that contains this file and distribute that work under terms of your choice.
//

package com.ryx.payment.ruishua.setting;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.rey.material.widget.Button;
import com.ryx.payment.ruishua.R;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.builder.PostActivityStarter;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class Update_
    extends Update
    implements HasViews, OnViewChangedListener
{
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(R.layout.activity_update);
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

    public static Update_.IntentBuilder_ intent(Context context) {
        return new Update_.IntentBuilder_(context);
    }

    public static Update_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new Update_.IntentBuilder_(fragment);
    }

    public static Update_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new Update_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        this.mVersionInfo = ((TextView) hasViews.findViewById(R.id.tv_version_info));
        this.mVersionName = ((TextView) hasViews.findViewById(R.id.tv_version_name));
        this.mUpdateBtn = ((Button) hasViews.findViewById(R.id.btn_update));
        this.mUpdateIv = ((ImageView) hasViews.findViewById(R.id.iv_update));
        this.rl_version_info = ((RelativeLayout) hasViews.findViewById(R.id.rl_version_info));
        View view_tileleftImg = hasViews.findViewById(R.id.tileleftImg);

        if (view_tileleftImg!= null) {
            view_tileleftImg.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    Update_.this.backBtnClick();
                }
            }
            );
        }
        if (this.mUpdateBtn!= null) {
            this.mUpdateBtn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    Update_.this.updateBtnClick();
                }
            }
            );
        }
        if (this.rl_version_info!= null) {
            this.rl_version_info.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    Update_.this.rl_version_infoClick();
                }
            }
            );
        }
        initViews();
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<Update_.IntentBuilder_>
    {
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, Update_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), Update_.class);
            fragment_ = fragment;
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            super(fragment.getActivity(), Update_.class);
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
