//
// DO NOT EDIT THIS FILE.
// Generated using AndroidAnnotations 4.3.0.
// 
// You can create a larger work that contains this file and distribute that work under terms of your choice.
//

package com.ryx.payment.ruishua.sjfx;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ryx.payment.ruishua.R;
import com.zhy.autolayout.AutoLinearLayout;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.builder.PostActivityStarter;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class MyInvitationCodeActivity_
    extends MyInvitationCodeActivity
    implements HasViews, OnViewChangedListener
{
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(R.layout.activity_my_invitation_code);
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

    public static MyInvitationCodeActivity_.IntentBuilder_ intent(Context context) {
        return new MyInvitationCodeActivity_.IntentBuilder_(context);
    }

    public static MyInvitationCodeActivity_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new MyInvitationCodeActivity_.IntentBuilder_(fragment);
    }

    public static MyInvitationCodeActivity_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new MyInvitationCodeActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        this.iv_qrcodeimg = ((ImageView) hasViews.findViewById(R.id.iv_qrcodeimg));
        this.bt_save_local = ((Button) hasViews.findViewById(R.id.bt_save_local));
        this.tv_url = ((TextView) hasViews.findViewById(R.id.tv_url));
        this.bt_shareUrl = ((Button) hasViews.findViewById(R.id.bt_shareUrl));
        this.tv_invitationCode = ((TextView) hasViews.findViewById(R.id.tv_invitationCode));
        this.bt_copy = ((Button) hasViews.findViewById(R.id.bt_copy));
        this.qrcode_layout = ((AutoLinearLayout) hasViews.findViewById(R.id.qrcode_layout));
        this.nestedScrollView = ((NestedScrollView) hasViews.findViewById(R.id.nestedScrollView));
        this.nodatalayout = ((AutoLinearLayout) hasViews.findViewById(R.id.nodatalayout));
        this.textmsg_tv = ((TextView) hasViews.findViewById(R.id.textmsg_tv));
        if (this.bt_save_local!= null) {
            this.bt_save_local.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    MyInvitationCodeActivity_.this.dirSaveView();
                }
            }
            );
        }
        if (this.bt_shareUrl!= null) {
            this.bt_shareUrl.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    MyInvitationCodeActivity_.this.shareClick();
                }
            }
            );
        }
        if (this.bt_copy!= null) {
            this.bt_copy.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    MyInvitationCodeActivity_.this.copy(view);
                }
            }
            );
        }
        if (this.tv_url!= null) {
            this.tv_url.setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    MyInvitationCodeActivity_.this.copyUrl(view);
                    return true;
                }
            }
            );
        }
        afterView();
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<MyInvitationCodeActivity_.IntentBuilder_>
    {
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, MyInvitationCodeActivity_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), MyInvitationCodeActivity_.class);
            fragment_ = fragment;
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            super(fragment.getActivity(), MyInvitationCodeActivity_.class);
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
