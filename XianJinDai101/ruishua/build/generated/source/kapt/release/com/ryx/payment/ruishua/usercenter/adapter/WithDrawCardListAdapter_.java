//
// DO NOT EDIT THIS FILE.
// Generated using AndroidAnnotations 4.3.0.
// 
// You can create a larger work that contains this file and distribute that work under terms of your choice.
//

package com.ryx.payment.ruishua.usercenter.adapter;

import android.content.Context;

public final class WithDrawCardListAdapter_
    extends WithDrawCardListAdapter
{
    private Context context_;

    private WithDrawCardListAdapter_(Context context) {
        context_ = context;
        init_();
    }

    public static WithDrawCardListAdapter_ getInstance_(Context context) {
        return new WithDrawCardListAdapter_(context);
    }

    private void init_() {
        this.context = context_;
        init();
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }
}
