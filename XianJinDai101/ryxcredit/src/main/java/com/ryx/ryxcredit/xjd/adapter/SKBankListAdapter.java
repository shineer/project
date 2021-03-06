package com.ryx.ryxcredit.xjd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryx.quickadapter.inter.OnListItemClickListener;
import com.ryx.quickadapter.recyclerView.HelperAdapter;
import com.ryx.quickadapter.recyclerView.HelperViewHolder;
import com.ryx.ryxcredit.R;
import com.ryx.ryxcredit.beans.bussiness.cardrepayment.CcardRepaymentResponse;
import com.ryx.ryxcredit.utils.CBanksUtils;
import com.ryx.ryxcredit.utils.CStringUnit;

import java.util.List;

/**
 * 收款账户列表
 */

public class SKBankListAdapter extends HelperAdapter<CcardRepaymentResponse.ResultBean> {

    private OnListItemClickListener mOnItemClickListener = null;
    Context context;
    private View view;

    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public SKBankListAdapter(List<CcardRepaymentResponse.ResultBean> data, Context context, int... layoutId) {
        super(data, context, layoutId);
        this.context=context;
    }

    public void setOnItemClickListener(OnListItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    protected void HelperBindData(HelperViewHolder viewHolder, final int position, final CcardRepaymentResponse.ResultBean item) {

        if (null != item) {
            view = viewHolder.getItemView();
            ImageView imgView =  (ImageView)view.findViewById(R.id.bankimgview);
            TextView tv_bankName = (TextView)view.findViewById(R.id.banknametextview);
            TextView tv_account = (TextView)view.findViewById(R.id.tv_paybankcards_account);
            TextView tv_status =(TextView)view.findViewById(R.id.tv_status);
            TextView tv_debit_card =(TextView)view.findViewById(R.id.tv_debit_card);
            TextView tv_credit_card =(TextView)view.findViewById(R.id.tv_credit_card);
            tv_bankName.setText(item.getBank_name());
            tv_account.setText(CStringUnit.cardJiaMi(item.getCard_num()));
            int status = item.getStatus();
            int card_type = item.getCard_type();
            if(status==0){
                tv_status.setVisibility(View.VISIBLE);
                view.setBackgroundColor(Color.parseColor("#e5e5e5"));
            }else{
                tv_status.setVisibility(View.INVISIBLE);
                view.setBackgroundColor(Color.WHITE);
                if(card_type ==0){
                    tv_debit_card.setVisibility(View.VISIBLE);
                }else if(card_type ==1){
                    tv_credit_card.setVisibility(View.VISIBLE);
                }
            }
            CBanksUtils.selectIcoidToImgView(context,item.getBank_title_code(),imgView);
            if(this.mOnItemClickListener!=null){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(v,position,item);
                    }
                });
            }
        }
    }
}
