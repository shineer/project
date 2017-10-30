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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import com.cjj.MaterialRefreshLayout;
import com.ryx.payment.ruishua.R;
import com.zhy.autolayout.AutoLinearLayout;
import org.androidannotations.api.builder.FragmentBuilder;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class SweepCodeFragment_
    extends com.ryx.payment.ruishua.fragment.SweepCodeFragment
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
            contentView_ = inflater.inflate(R.layout.fragment_sweepcode, container, false);
        }
        return contentView_;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView_ = null;
        lv_payment = null;
        materialRefreshLayout = null;
        lay_top = null;
        iv_norecord = null;
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static SweepCodeFragment_.FragmentBuilder_ builder() {
        return new SweepCodeFragment_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        this.lv_payment = ((ListView) hasViews.findViewById(R.id.lv_payment));
        this.materialRefreshLayout = ((MaterialRefreshLayout) hasViews.findViewById(R.id.materialRefreshLayout));
        this.lay_top = ((AutoLinearLayout) hasViews.findViewById(R.id.lay_top));
        this.iv_norecord = ((ImageView) hasViews.findViewById(R.id.iv_norecord));
        afterView();
    }

    public static class FragmentBuilder_
        extends FragmentBuilder<SweepCodeFragment_.FragmentBuilder_, com.ryx.payment.ruishua.fragment.SweepCodeFragment>
    {

        @Override
        public com.ryx.payment.ruishua.fragment.SweepCodeFragment build() {
            SweepCodeFragment_ fragment_ = new SweepCodeFragment_();
            fragment_.setArguments(args);
            return fragment_;
        }
    }
}
