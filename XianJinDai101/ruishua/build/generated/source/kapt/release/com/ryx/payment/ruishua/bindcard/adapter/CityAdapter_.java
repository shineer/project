//
// DO NOT EDIT THIS FILE.
// Generated using AndroidAnnotations 4.3.0.
// 
// You can create a larger work that contains this file and distribute that work under terms of your choice.
//

package com.ryx.payment.ruishua.bindcard.adapter;

import android.content.Context;

public final class CityAdapter_
    extends CityAdapter
{
    private Context context_;

    private CityAdapter_(Context context) {
        context_ = context;
        init_();
    }

    public static CityAdapter_ getInstance_(Context context) {
        return new CityAdapter_(context);
    }

    private void init_() {
        this.context = context_;
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }
}
