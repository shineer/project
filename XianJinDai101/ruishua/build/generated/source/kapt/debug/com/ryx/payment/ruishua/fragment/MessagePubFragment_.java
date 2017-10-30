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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.cjj.MaterialRefreshLayout;
import com.ryx.payment.ruishua.R;
import com.ryx.payment.ruishua.adapter.MsgListAdapter_;
import com.zhy.autolayout.AutoLinearLayout;
import org.androidannotations.api.builder.FragmentBuilder;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class MessagePubFragment_
    extends com.ryx.payment.ruishua.fragment.MessagePubFragment
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
            contentView_ = inflater.inflate(R.layout.fragment_message_public_content, container, false);
        }
        return contentView_;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView_ = null;
        materialRefreshLayout = null;
        lv_pubmsg = null;
        lay_top = null;
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        this.adapter = MsgListAdapter_.getInstance_(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static MessagePubFragment_.FragmentBuilder_ builder() {
        return new MessagePubFragment_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        this.materialRefreshLayout = ((MaterialRefreshLayout) hasViews.findViewById(R.id.materialRefreshLayout));
        this.lv_pubmsg = ((ListView) hasViews.findViewById(R.id.lv_pubmsg));
        this.lay_top = ((AutoLinearLayout) hasViews.findViewById(R.id.lay_top));
        if (this.lv_pubmsg!= null) {
            ((AdapterView<?> ) this.lv_pubmsg).setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MessagePubFragment_.this.getClickItem(position);
                }
            }
            );
        }
        afterView();
    }

    public static class FragmentBuilder_
        extends FragmentBuilder<MessagePubFragment_.FragmentBuilder_, com.ryx.payment.ruishua.fragment.MessagePubFragment>
    {

        @Override
        public com.ryx.payment.ruishua.fragment.MessagePubFragment build() {
            MessagePubFragment_ fragment_ = new MessagePubFragment_();
            fragment_.setArguments(args);
            return fragment_;
        }
    }
}
