package com.ryx.ryxcredit.ryd.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rey.material.app.BottomSheetDialog;
import com.rey.material.app.ThemeManager;
import com.rey.material.widget.Button;
import com.ryx.quickadapter.inter.NoDoubleClickListener;
import com.ryx.ryxcredit.R;
import com.ryx.ryxcredit.RyxcreditConfig;
import com.ryx.ryxcredit.activity.BaseActivity;
import com.ryx.ryxcredit.beans.ReqAction;
import com.ryx.ryxcredit.beans.bussiness.cardrepayment.CcardRepaymentRequest;
import com.ryx.ryxcredit.beans.bussiness.cardrepayment.CcardRepaymentResponse;
import com.ryx.ryxcredit.beans.bussiness.loancalculate.CLoanCalculateResquest;
import com.ryx.ryxcredit.beans.bussiness.product.CproductRequest;
import com.ryx.ryxcredit.ryd.adapter.CProductTermAdapter;
import com.ryx.ryxcredit.ryd.bean.borrow.CXJDProductCaculateResponse;
import com.ryx.ryxcredit.services.ICallback;
import com.ryx.ryxcredit.utils.CBanksUtils;
import com.ryx.ryxcredit.utils.CDateUtil;
import com.ryx.ryxcredit.utils.CLogUtil;
import com.ryx.ryxcredit.utils.CNummberUtil;
import com.ryx.ryxcredit.utils.CStringUnit;
import com.ryx.ryxcredit.views.KeyboardChangeListener;
import com.ryx.ryxcredit.widget.RyxCreditLoadDialog;
import com.ryx.ryxcredit.xjd.bean.borrow.CXJDProductDetailResponse;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import static com.ryx.ryxcredit.R.id.c_longest_list;


/**
 * 瑞易贷--我要借款
 */

public class RYDBorrowMoneyActivity extends BaseActivity implements KeyboardChangeListener.KeyBoardListener {

    private Button borrowBtn;
    private ImageView img_right;
    private BottomSheetDialog mBwLongestTimeDialog;

    private CXJDProductDetailResponse.ResultBean product;//产品详情

    private AutoRelativeLayout lay_borrow_tem;//借款期限
    private TextView tv_borrow_time;//借款期限
    private TextView c_tv_product_name;//产品名称

    private EditText c_borrow_money_amount_et;//最低借款金额
    private boolean isJumped = false;//是否跳转，到了确认借款页面
    private boolean isTimeout;//贷款试算接口，请求是否超时

    private int selectIdx = 0;//选择的子产品位置

    //收款账户
    private AutoRelativeLayout lay_skbank;//收款银行
    private TextView tv_skbankNo;//银行卡号
    private ImageView img_skbank;//银行logo
    private TextView c_tv_bind;//去绑定文字提示
    private ImageView img_skright;
    private String skbankCode;//银行编码
    private String skCardNo = "";//银行卡号

    //还款账户
    private String hkCardNo = "";//银行卡号
    private String hkBankCode = "";//银行编号
    private String hkBankName = "";//收款银行名称
    private AutoRelativeLayout lay_kkbank;//还款银行
    private ImageView hkBankImg;//银行logo
    private TextView tv_hk;//还款银行
    private TextView c_tv_choose;//"请选择"文字
    private String hk_phoneNo;//手机号,银行预留手机号

    private KeyboardChangeListener mKeyboardChangeListener;

    private AutoLinearLayout lay_caculate_money;//总金额，利息，服务费

    private ImageView img_ask;//服务费旁边的问号按钮
    DecimalFormat df = new DecimalFormat("#0.00");
    //子产品列表
    private List<CXJDProductDetailResponse.ResultBean.SubProductsBean> subProductsBeanList;
    //用户选择的子产品
    private CXJDProductDetailResponse.ResultBean.SubProductsBean selected_subProduct;

    //金额，合同金额，利息，服务费
    private TextView c_tv_total_amount, c_tv_interest_fee, c_tv_service_fee;
    private double total_amount, tv_interest_fee, tv_service_fee;

    //private AutoRelativeLayout lay_repayplan;//还款计划
    private boolean is_opened;//是否是业务时间
    private String unborrowTime;//非业务时间
    private int agreement_id;
    private String expired_date;
    private int card_type;
    private double term_amount;
    private String term_amount_double;
    private double sub_cost_amount;
    private String sub_cost_amount_double;
    private double other_cost_amount;
    private String other_cost_amount_double;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_activity_ryd_borrow);
        setTitleLayout("我要借款", true, false);
        getIntentData();
        requestData();


     //   initListen();
    }

    private void initListen() {
        c_borrow_money_amount_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!"".equalsIgnoreCase(c_borrow_money_amount_et.getText().toString())
                        &&Double.parseDouble(c_borrow_money_amount_et.getText().toString()) > avilableAmount){
                    c_borrow_money_amount_et.setText(df.format(avilableAmount));
                }
            }
        });
    }

    private double avilableAmount;//可用金额

    //可用额度，
    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("avilableAmount"))
                avilableAmount = intent.getDoubleExtra("avilableAmount", 0.00);
            if (intent.hasExtra("is_opened"))
                is_opened = intent.getBooleanExtra("is_opened", true);
            if (intent.hasExtra("unborrowTime"))
                unborrowTime = intent.getStringExtra("unborrowTime");
        }
    }

    private void initViews() {
        mKeyboardChangeListener = new KeyboardChangeListener(this);
        mKeyboardChangeListener.setKeyBoardListener(this);
        borrowBtn = (Button) findViewById(R.id.c_borrow_money_btn);
        borrowBtn.setOnClickListener(clickListener);

        //借款期限
        lay_borrow_tem = (AutoRelativeLayout) findViewById(R.id.lay_borrow_tem);
        lay_borrow_tem.setOnClickListener(clickListener);
        tv_borrow_time = (TextView) findViewById(R.id.tv_borrow_time);

        // 产品名称
        c_tv_product_name = (TextView) findViewById(R.id.c_tv_product_name);
        // 最低借款金额
        c_borrow_money_amount_et = (EditText) findViewById(R.id.c_borrow_money_amount_et);
        if(df.format(product.getCeiling_purchasable_amount())!=null) {
            c_borrow_money_amount_et.setText(df.format(product.getCeiling_purchasable_amount()));
        }else {
            c_borrow_money_amount_et.setText("");
         }
        //收款银行
        lay_skbank = (AutoRelativeLayout) findViewById(R.id.lay_skbank);
        lay_skbank.setOnClickListener(clickListener);
        tv_skbankNo = (TextView) findViewById(R.id.c_tv_skbankNo);
        img_skbank = (ImageView) findViewById(R.id.c_skbank_iv);
        c_tv_bind = (TextView) findViewById(R.id.c_tv_bind);
        img_skright = (ImageView) findViewById(R.id.img_skright);

        //还款银行
        lay_kkbank = (AutoRelativeLayout) findViewById(R.id.lay_kkbank);
        lay_kkbank.setOnClickListener(clickListener);
        tv_hk = (TextView) findViewById(R.id.c_tv_dkbankNo);
        hkBankImg = (ImageView) findViewById(R.id.c_dkbank_iv);
        lay_caculate_money = (AutoLinearLayout) findViewById(R.id.lay_caculate_money);
        img_ask = (ImageView) findViewById(R.id.img_ask);
        img_ask.setOnClickListener(clickListener);
        c_tv_choose = (TextView) findViewById(R.id.c_tv_choose);

        //合同金额、利息、服务费
        c_tv_total_amount = (TextView) findViewById(R.id.c_tv_total_amount);
        c_tv_interest_fee = (TextView) findViewById(R.id.c_tv_interest_fee);
        c_tv_service_fee = (TextView) findViewById(R.id.c_tv_service_fee);

        //还款计划
 //       lay_repayplan = (AutoRelativeLayout) findViewById(lay_repayplan);
 //       lay_repayplan.setOnClickListener(clickListener);
    }

    private int shoukuanzhanghuAction =1;
    private int huankuanzhanghuAction = 2;
    NoDoubleClickListener clickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View v) {
            if (v.getId() == R.id.c_borrow_money_btn) {
                borrowSure();
            } else if (v.getId() == R.id.lay_borrow_tem) {
                showBorrowLTime();
            }
            //收款银行
            else if (v.getId() == R.id.lay_skbank) {
                initskData(shoukuanzhanghuAction);

            }
            //还款银行
            else if (v.getId() == R.id.lay_kkbank) {
                initskData(huankuanzhanghuAction);

            }
            //服务费旁边的问号按钮
            else if (v.getId() == R.id.img_ask) {
                showHelpPopWindow();
            }
/*            else if (v.getId() == lay_repayplan) {
                //无还款计划，
                if (caculateRepaymentList == null) {
                    return;
                }
                Intent intent = new Intent(RYDBorrowMoneyActivity.this, XJDRepayPlanActivity.class);
                intent.putExtra("caculateRepaymentList", (Serializable) caculateRepaymentList);
                if (loanCal != null)
                    intent.putExtra("total_amount", loanCal.getTotal_amount());
                startActivity(intent);
            }*/
        }
    };
    List<CcardRepaymentResponse.ResultBean> depositCardList;


    //收款银行
    private void initskData(final int bank) {
        CcardRepaymentRequest requestRepayment = new CcardRepaymentRequest();
        requestRepayment.setVersion(RyxcreditConfig.getVersion());
        httpsPost(this, requestRepayment, ReqAction.CARD_PAYMENT_LIST, CcardRepaymentResponse.class, new ICallback<CcardRepaymentResponse>() {
            @Override
            public void success(CcardRepaymentResponse ccardRepaymentResponse) {
                if (ccardRepaymentResponse.getResult() != null) {
                    depositCardList = ccardRepaymentResponse.getResult();
                    int ccardRepaymentCode = ccardRepaymentResponse.getCode();
                    if (ccardRepaymentCode==5031) {
                        showMaintainDialog();
                    } else {
                   /* for (int i = 0; i < depositCardList.size(); i++) {
                        card_type = depositCardList.get(i).getCard_type();
                    }*/
                        if (bank == 1) {
                            shoukuanzhanghuAction();
                        } else if (bank == 2) {
                            huankuanzhanghuAction();
                        }
                    }
                    RyxCreditLoadDialog.getInstance(getApplicationContext()).dismiss();
                }
            }

            @Override
            public void failture(String tips) {
                CLogUtil.showToast(getApplicationContext(), tips);
                RyxCreditLoadDialog.getInstance(getApplicationContext()).dismiss();
            }
        });
    }

    public void showHelpPopWindow() {
        if (loanCal == null) {
            return;
        }
        View view = LayoutInflater.from(this).inflate(R.layout.c_xjd_borrow_help_popview, null);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_content.setText(getString(R.string.xjd_content_amount));
        //到账金额
        TextView arriveTv = (TextView) view.findViewById(R.id.tv_arriveAmount);
        String amountStr = c_borrow_money_amount_et.getText().toString().trim();
        arriveTv.setText(amountStr);
        arriveTv.setVisibility(View.GONE);
        //服务费
        TextView interestTvv = (TextView) view.findViewById(R.id.tv_interest);
        interestTvv.setVisibility(View.GONE);
        //利息
      //  TextView serviceTv = (TextView) view.findViewById(R.id.tv_service_fee);
     //  serviceTv.setText(String.valueOf(loanCal.getOther_cost_amount())+"元");
        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        com.rey.material.app.Dialog.Builder builder = new com.rey.material.app.Dialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog);
        final com.rey.material.app.Dialog dialog = builder.build(RYDBorrowMoneyActivity.this);
        dialog.setContentView(view);
        dialog.show();
    }


    /**
     * 收款银行卡，查询，is_hk：判断是否是还款银行卡列表查询，cardinfo：查询到的收款银行卡列表
     */
    private void shoukuanzhanghuAction() {
        Intent intent = new Intent(RYDBorrowMoneyActivity.this, RYDBankSelectActivity.class);
        intent.putExtra("is_hk", false);
        intent.putExtra("cardinfo", (Serializable) depositCardList);
        if (product != null)
            intent.putExtra("paid_cash_card", product.getPaid_cash_card() + "");
        startActivityForResult(intent, 994);
    }

    /**
     * 收款银行卡信息
     */
    private void getShoukuanBank(CcardRepaymentResponse.ResultBean cardInfo) {
        if (cardInfo == null)
            return;
        skCardNo = cardInfo.getCard_num();
        img_skbank.setVisibility(View.VISIBLE);
        skbankCode = cardInfo.getBank_title_code();
        tv_skbankNo.setText(CStringUnit.cardJiaMi(skCardNo));
        card_type = cardInfo.getCard_type();
        CBanksUtils.selectIcoidToImgView(RYDBorrowMoneyActivity.this, cardInfo.getBank_title_code(), img_skbank);
    }

    /**
     * 还款银行卡列表
     */
    private void huankuanzhanghuAction() {
        Intent intent = new Intent(RYDBorrowMoneyActivity.this, RYDBankSelectActivity.class);
        intent.putExtra("is_hk", true);
        if (product != null)
            intent.putExtra("paid_cash_card", product.getRepaid_cash_card() + "");
        startActivityForResult(intent, 995);
    }

    /**
     * 还款银行卡信息
     */
    private void getHuanKuanBank(CcardRepaymentResponse.ResultBean dkCardInfo) {
        if (dkCardInfo == null) {
            return;
        }

        hkCardNo = dkCardInfo.getCard_num();
        hkBankCode = dkCardInfo.getBank_title_code();
        hkBankName = dkCardInfo.getBank_name();
        initViewSattus();
    }

    //还款卡内容显示
    private void initViewSattus() {
        tv_hk.setText(CStringUnit.cardJiaMi(hkCardNo));
        tv_hk.setVisibility(View.VISIBLE);
        hkBankImg.setVisibility(View.VISIBLE);
        c_tv_choose.setVisibility(View.GONE);
        CBanksUtils.selectIcoidToImgView(RYDBorrowMoneyActivity.this, hkBankCode, hkBankImg);
    }

    /**
     * 借款金额，输入框限定最多小数点后两位
     *
     * @param editText
     */
    public void setBorrowListener(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (".".equals(s.toString().trim().substring(0))) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!".".equals(s.toString().substring(1, 2))) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //借款期限
    private void showBorrowLTime() {
        if (subProductsBeanList == null || subProductsBeanList.size() == 0) {
            CLogUtil.showToast(RYDBorrowMoneyActivity.this, "获取产品失败，暂不可选择期限！");
            return;
        }
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        mBwLongestTimeDialog = new BottomSheetDialog(this, R.style.Material_App_BottomSheetDialog);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_choose_borrow_longest_time, null);
        ListView lv_longest = (ListView) v.findViewById(c_longest_list);
        CProductTermAdapter timeAdapter = new CProductTermAdapter(this, subProductsBeanList);
        lv_longest.setAdapter(timeAdapter);
        lv_longest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_subProduct = subProductsBeanList.get(position);
                tv_borrow_time.setText(selected_subProduct.getTerm_spans() + unit2text(subProductsBeanList.get(position).getSpan_unit()));
                mBwLongestTimeDialog.dismiss();
                productCalculate(2, false, false);
            }
        });

        Button btn_close = (Button) v.findViewById(R.id.c_close);
        btn_close.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                mBwLongestTimeDialog.dismiss();
            }
        });
        mBwLongestTimeDialog.contentView(v)
                .show();
    }

    /**
     * 获取产品详情
     */
    private void requestData() {
        CproductRequest request = new CproductRequest();
        request.setProduct_id("8007");
        request.setSub_product_id("600101");
        httpsPost(this, request, ReqAction.APPLICATION_PRODUCT_DETAIL, CXJDProductDetailResponse.class, new ICallback<CXJDProductDetailResponse>() {
            @Override
            public void success(CXJDProductDetailResponse CProductDetailResponse) {
                product = CProductDetailResponse.getResult();
                int CProductDetailCode = CProductDetailResponse.getCode();
                if (CProductDetailCode==5031) {
                    showMaintainDialog();
                } else {
                    if (product != null)
                            initViews();
                            bindView(product);
                            productCalculate(2, false, false);

                }

            }

            @Override
            public void failture(String tips) {
                CLogUtil.showToast(RYDBorrowMoneyActivity.this, tips);
                RyxCreditLoadDialog.getInstance(getApplicationContext()).dismiss();
            }
        });
    }

    /**
     * 单位标签转汉字
     *
     * @param unit
     * @return
     */
    private String unit2text(String unit) {
        if ("M".equals(unit))
            return "个月";
        if ("D".equals(unit))
            return "天";
        return "";
    }

    private int pro_len = 0;

    /**
     * 绑定产品详情view
     *
     * @param bean
     */
    private void bindView(CXJDProductDetailResponse.ResultBean bean) {
        c_tv_product_name.setText(bean.getProduct_descr());
        subProductsBeanList = bean.getSub_products();
        if (subProductsBeanList == null || subProductsBeanList.size() == 0) {
            CLogUtil.showToast(RYDBorrowMoneyActivity.this, "没有产品");
            return;
        }
        selected_subProduct = subProductsBeanList.get(0);//默认选中第一个子产品
        tv_borrow_time.setText(selected_subProduct.getTerm_spans() + unit2text(selected_subProduct.getSpan_unit()));
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString("可借" + df.format(product.getCeiling_purchasable_amount()) + "元,"+"最低借款金额" +df.format(product.getFloor_purchasable_amount())+"元");
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        c_borrow_money_amount_et.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
        setBorrowListener(c_borrow_money_amount_et);
    }


    //清空,试算金额内容
    private void setBlankCal() {
        c_tv_total_amount.setText("¥0.00");
        c_tv_interest_fee.setText("¥0.00");
        c_tv_service_fee.setText("¥0.00");
    }


    private CXJDProductCaculateResponse.ResultBean loanCal;//产品试算结果

    //产品试算后的还款计划
    private List<CXJDProductCaculateResponse.ResultBean.RepaymentsBean> caculateRepaymentList;

    /**
     * 产品试算
     *
     * @param isBorrow  是否是点击的借款按钮
     * @param showToast 是否需要提示用户输入的金额是空
     * @maxTimes 超时后请求网络的次数
     */
    private void productCalculate(final int maxTimes, boolean isBorrow, boolean showToast) {
        //清空计算出来的合同金额，还款金额，还款日期
        setBlankCal();
        loanCal = null;
        if (product == null || product.getSub_products() == null || product.getSub_products().size() == 0)
            return;
        CXJDProductDetailResponse.ResultBean.SubProductsBean subProductsBean = selected_subProduct;
        if (TextUtils.isEmpty(c_borrow_money_amount_et.getText().toString().trim()) || "0".equals(c_borrow_money_amount_et.getText().toString())) {
            if (showToast)
                CLogUtil.showToast(RYDBorrowMoneyActivity.this, "请输入金额");
            return;
        }
        double loanAmount = CNummberUtil.parseDouble(c_borrow_money_amount_et.getText().toString().trim(), 0);
        if (loanAmount < product.getFloor_purchasable_amount()) {
            //CLogUtil.showToast(RYDBorrowMoneyActivity.this, "金额不能低于" + product.getFloor_purchasable_amount());
            CLogUtil.showToast(RYDBorrowMoneyActivity.this, "亲，您的借款金额要大于等于"+df.format(product.getFloor_purchasable_amount())+"元" );
            return;
        }

        if (loanAmount > Double.parseDouble(df.format(product.getCeiling_purchasable_amount()))) {
            CLogUtil.showToast(RYDBorrowMoneyActivity.this, "金额不能高于" + df.format(product.getCeiling_purchasable_amount()));
            return;
        }
        caculateMoney(subProductsBean, loanAmount, maxTimes, isBorrow);
    }

    private void caculateMoney(final CXJDProductDetailResponse.ResultBean.SubProductsBean subProductsBean, final double loanAmount, final int maxtimes, final boolean isBorrow) {
        CLoanCalculateResquest request = new CLoanCalculateResquest();
        request.setProduct_id(product.getProduct_id());
        request.setSub_product_id(subProductsBean.getSub_product_id());
        request.setTerm(subProductsBean.getTerm());
        request.setLoan_amount(loanAmount);
        httpsPost(this, request, ReqAction.APPLICATION_LOAN_CALCULATE, CXJDProductCaculateResponse.class, new ICallback<CXJDProductCaculateResponse>() {
            @Override
            public void success(CXJDProductCaculateResponse productCaculateResponse) {
                loanCal = productCaculateResponse.getResult();
                caculateRepaymentList = loanCal.getRepayments();
                //待还金额
               term_amount =loanCal.getTerm_amount();
                //保留小数点后的待还金额
               term_amount_double =df.format(loanCal.getTerm_amount());
                sub_cost_amount = loanCal.getSub_cost_amount();//利息
                sub_cost_amount_double = df.format(sub_cost_amount);//利息
                other_cost_amount = loanCal.getOther_cost_amount();//服务费
                other_cost_amount_double = df.format(other_cost_amount);//服务费
                int productCaculateCode = productCaculateResponse.getCode();
                if (productCaculateCode==5031) {
                    showMaintainDialog();
                } else {
                    for (int i = 0; i < caculateRepaymentList.size(); i++) {
                        CXJDProductCaculateResponse.ResultBean.RepaymentsBean list = caculateRepaymentList.get(i);
                        expired_date = list.getExpired_date();
                    }
                    bindLoanCal();
                    isTimeout = false;
                    times = 0;
                    if (isBorrow) {
                        borrowSure();
                    }
                }
            }

            @Override
            public void failture(String tips) {
                CLogUtil.showToast(RYDBorrowMoneyActivity.this, tips);
                RyxCreditLoadDialog.getInstance(RYDBorrowMoneyActivity.this).dismiss();
                setBlankCal();
                //超时后重新请求试算接口
                isTimeout = true;
                if (times < maxtimes) {
                    times = times + 1;
                    caculateMoney(subProductsBean, loanAmount, maxtimes, isBorrow);
                }
            }
        });
    }

    /**
     * 跳转确认借款页面
     */
    private void borrowSure() {
        String amountStr = c_borrow_money_amount_et.getText().toString().trim();
        if (TextUtils.isEmpty(amountStr)) {
            CLogUtil.showToast(this, "请输入借款到账金额！");
            return;
        }
        Intent intent = new Intent(this, RYDSureBorrowActivity.class);
        Bundle bundle = new Bundle();
        if (TextUtils.isEmpty(skCardNo.trim())) {
            CLogUtil.showToast(this, "请完善您的收款账户信息！");
            return;
        }
        if (TextUtils.isEmpty(tv_hk.getText().toString().trim())) {
            CLogUtil.showToast(this, "请完善您的还款账户信息！");
            return;
        }
        if (!is_opened) {
            //CLogUtil.showToast(RYDBorrowMoneyActivity.this, "抱歉，" + unborrowTime + "非交易时间!");
            CLogUtil.showToast(RYDBorrowMoneyActivity.this, "抱歉，非交易时间无法操作借款及还款！");
            return;
        }
        if (product != null) {
            double loanAmount = CNummberUtil.parseDouble(amountStr, 0.00d);
            if (loanAmount < product.getFloor_purchasable_amount()) {
                CLogUtil.showToast(RYDBorrowMoneyActivity.this, "金额不能低于" + product.getFloor_purchasable_amount());
                return;
            }
            if (loanAmount > product.getCeiling_purchasable_amount()) {
                CLogUtil.showToast(RYDBorrowMoneyActivity.this, "金额不能高于" + product.getCeiling_purchasable_amount());
                return;
            }
            bundle.putString("product_descr", product.getProduct_descr());
            bundle.putString("product_id", product.getProduct_id());
            //传递产品编号
            if (product.getSub_products() != null && product.getSub_products().size() > 0)
                bundle.putString("sub_product_id", selected_subProduct.getSub_product_id());
            //服务协议
            bundle.putString("service_agreement_url", product.getService_agreement_url());
            //借款合同
            bundle.putString("contract_url", product.getContract_url() + "");
            //委托代扣
            bundle.putString("daikou_url", product.getAgreement_url() + "");

            //协议版本号
//            bundle.putDouble("contract_version", product.getContract_version());
            //委托代扣
//            bundle.putString("dkagreement_url", product.getAgreement_url() + "?PayBankAcctName=" + RyxcreditConfig.getRealName()
//                    + "&ddBankName=" + dkbankName + "&ddBankAcctNbr=" + CStringUnit.cardJiaMi(cardno2) + "");
        }
        if (!TextUtils.isEmpty(amountStr))
            bundle.putDouble("loan_amount", CNummberUtil.parseDouble(amountStr, 0));
        //试算接口请求超时
        if (isTimeout) {
            productCalculate(0, true, true);
            return;
        }
        if (loanCal != null) {
            bundle.putDouble("cost_amount", loanCal.getCost_amount());
            bundle.putDouble("total_amount", loanCal.getTotal_amount());
            //待还金额
            bundle.putDouble("term_amount", Double.parseDouble(term_amount_double));
            //合同版本号
            bundle.putInt("agreement_id",loanCal.getAgreement_id());
            String endDate = CDateUtil.DateToStrForRecord(
                        CDateUtil.parseDate(expired_date, "yyyyMMdd"));
            String beginDate = CDateUtil.DateToStrForRecord(
                        CDateUtil.parseDate(loanCal.getTerm_start_date(), "yyyyMMdd"));
            bundle.putString("date", beginDate + " - " + endDate);
            bundle.putString("end_date", expired_date);
            bundle.putDouble("cost_rate", loanCal.getCost_rate());
        } else {
            productCalculate(0, true, true);
//            CLogUtil.showToast(this, "借款信息有误，请重试！");
            return;
        }
        //收款账号
        bundle.putString("payment_card_num", skCardNo);
        //收款账号是否为信用卡还是储蓄卡
        bundle.putInt("card_type", card_type);
//        //收款银行卡号
        bundle.putString("payment_bankCode", skbankCode);
        //还款银行账号
        bundle.putString("repayment_bankCode", hkBankCode);
        //付款账号
        bundle.putString("repayment_card_num", hkCardNo);

        //还款保证金（利息）
        bundle.putString("Sub_cost_amount", String.valueOf(loanCal.getSub_cost_amount()));
        //利息
        bundle.putDouble("interest_rate", loanCal.getInterest_rate());
        //利率
        bundle.putString("brinterest", String.valueOf(loanCal.getInterest_rate()));

        //服务费
        bundle.putString("other_cost_amount", String.valueOf(loanCal.getOther_cost_amount()));
        //拆分的服务费率
        bundle.putString("other_cost_rate", String.valueOf(loanCal.getOther_cost_rate()));
        bundle.putString("sub_overdue_interest_rate", String.valueOf(loanCal.getSub_overdue_interest_rate()));
        bundle.putDouble("other_overdue_interest_rate", loanCal.getOther_overdue_interest_rate());
        bundle.putDouble("overdue_interest_rate", loanCal.getOverdue_interest_rate());
        bundle.putDouble("cost_rate", loanCal.getFee_rate());
        bundle.putString("sub_cost_rate", String.valueOf(loanCal.getSub_cost_rate()));
        bundle.putDouble("cost_rate", loanCal.getCost_rate());

        //信用咨询及管理服务协议
        bundle.putString("money_manage_url", product.getMoney_manage_url());
        //选择的借款期限
        bundle.putInt("borrowterm", selected_subProduct.getTerm_spans() );
        intent.putExtras(bundle);
        isJumped = true;
        startActivityForResult(intent, 999);
    }

    /**
     * 贷款试算，结果
     */
    private void bindLoanCal() {
        if (null != loanCal) {
            caculateRepaymentList = loanCal.getRepayments();
            agreement_id = loanCal.getAgreement_id();
            c_tv_total_amount.setText("¥" + term_amount_double + "");
            c_tv_interest_fee.setText("¥" + sub_cost_amount_double + "");
            c_tv_service_fee.setText("¥" + other_cost_amount_double+ "");
        }
    }


    private int times = 0;//超时次数

    /**
     * call back
     *
     * @param isShow         true is show else hidden
     * @param keyboardHeight keyboard height
     */
    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {
        if (!isShow && c_borrow_money_amount_et.isFocused() && !isJumped) {
            times = 0;
            productCalculate(2, false, false);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        isJumped = false;
        if (resultCode == 999) {
            this.finish();
        }
        //收款银行卡
        else if (requestCode == 994) {
            if (data != null)
                getShoukuanBank((CcardRepaymentResponse.ResultBean) data.getSerializableExtra("skCardInfo"));
        }
        //还款银行卡信息
        else if (requestCode == 995) {
            if (data == null)
                return;
            getHuanKuanBank((CcardRepaymentResponse.ResultBean) data.getSerializableExtra("hkCardInfo"));
        } else if (requestCode == 996) {

        } else if (requestCode == 997) {

        } else if (requestCode == 998) {

        }
    }
}
