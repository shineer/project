package com.ryx.ryxcredit.xjd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryx.ryxcredit.R;
import com.ryx.ryxcredit.RyxcreditConfig;
import com.ryx.ryxcredit.activity.BaseActivity;
import com.ryx.ryxcredit.beans.ReqAction;
import com.ryx.ryxcredit.beans.bussiness.activateline.CActivateLineSearchRequest;
import com.ryx.ryxcredit.beans.bussiness.activateline.CActivateLineSearchResponse;
import com.ryx.ryxcredit.beans.bussiness.supplementarymaterials.CModifyPersonMaterialsRequest;
import com.ryx.ryxcredit.beans.bussiness.supplementarymaterials.CSupplementaryMaterialsRequest;
import com.ryx.ryxcredit.beans.bussiness.supplementarymaterials.CSupplementaryMaterialsResponse;
import com.ryx.ryxcredit.beans.pojo.Customer;
import com.ryx.ryxcredit.newactivity.LeadingDotsSuccessViews;
import com.ryx.ryxcredit.newactivity.newinter.NewIBaseInfoFragmentCallback;
import com.ryx.ryxcredit.newfragment.baseinfo.boss.RYDBasePersonalInfoFragmentJHSuccessed;
import com.ryx.ryxcredit.newfragment.baseinfo.boss.RYDBossBaseWorkInfoFragmentJHSuccess;
import com.ryx.ryxcredit.newfragment.baseinfo.boss.RYDBossCentBankFragment;
import com.ryx.ryxcredit.newfragment.baseinfo.boss.RYDBossFaceCollectFragment;
import com.ryx.ryxcredit.newfragment.baseinfo.boss.RYDFundWebViewFragment;
import com.ryx.ryxcredit.newfragment.baseinfo.boss.RYDJingDongFragment;
import com.ryx.ryxcredit.newfragment.baseinfo.boss.RYDSixChooseOneFragment;
import com.ryx.ryxcredit.newfragment.baseinfo.boss.RYDTaoBaoFragment;
import com.ryx.ryxcredit.newfragment.baseinfo.boss.RYDWebViewFragment;
import com.ryx.ryxcredit.ryd.fragment.RYDCallRecordsFragment;
import com.ryx.ryxcredit.services.ICallback;
import com.ryx.ryxcredit.utils.CLogUtil;


public class RKDBossBaseInfoSuccessActivity extends BaseActivity implements NewIBaseInfoFragmentCallback {
    public static final int BASEINFO_RESULT_CODE = 0x1002;
    private TextView mMobileService;
    private String productId;//产品编码
    private String user_level = "";//客户级别
    private ImageView iv_bacak;
    private ImageView credit_tileleftImg;
    private TextView tv_title;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    private String province;//省
    private String city;//市
    private String region;//区
    public String getUser_info_level() {
        return user_info_level;
    }

    public void setUser_info_level(String user_info_level) {
        this.user_info_level = user_info_level;
    }

    private String user_info_level = "";//客户资料级别
    private String service_authorize_url;//协议

    public String getService_authorize_url() {
        return service_authorize_url;
    }

    public void setService_authorize_url(String service_authorize_url) {
        this.service_authorize_url = service_authorize_url;
    }

    public String getTrade_product_url() {
        return trade_product_url;
    }

    public void setTrade_product_url(String trade_product_url) {
        this.trade_product_url = trade_product_url;
    }

    private String trade_product_url;//协议
    Bundle args;

    private enum BaseInfoEnum {

        PERSON_INFO,
        WORK_INFO,
        APPLY_INFO,
        OTHER_INFO,
        CALLRECORDS_INFO,
        FACERECOGNITION_INFO,
        PEDSTRIANLETTER_INFO,
        SIXCHOOSEONE_INFO,
        TAOBAO_INFO,
        JINGDONG_INFO,
        CREDITCARDBILL_INFO,
        WEBVIEW_INFO,
        FUNDWEBVIEW_INFO,
    }


    //新增对象
    private CSupplementaryMaterialsRequest cSupplementaryMaterialsRequest = null;
    //修改对象
    private CModifyPersonMaterialsRequest cModifyPersonMaterialsRequest = null;

    public static interface IPhoneListener {
        void phone(String phone);
    }

    private IPhoneListener iPhoneListener;
    //dot控制
    private LeadingDotsSuccessViews leadingDotsSuccessViews;

    public Customer customer = new Customer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_activity_base_infosuccess);
       // setTitleLayout("工作信息", true, false);
        credit_tileleftImg = (ImageView) findViewById(R.id.credit_tileleftImg);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("工作信息");
        credit_tileleftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initData();
        if (getIntent().hasExtra("product_id")) {
            productId = getIntent().getStringExtra("product_id");
        }
        if (getIntent().hasExtra("user_level")) {
            user_level = getIntent().getStringExtra("user_level");
        }
        if (getIntent().hasExtra("user_info_level")) {
            user_info_level = getIntent().getStringExtra("user_info_level");
        }
        if (getIntent().hasExtra("trade_product_url")) {
            //瑞卡贷
            trade_product_url = getIntent().getStringExtra("trade_product_url");
        }
        if (getIntent().hasExtra("service_authorize_url")) {
            //瑞易贷
            service_authorize_url = getIntent().getStringExtra("service_authorize_url");
        }
        setbottomMenu();
        leadingDotsSuccessViews = (LeadingDotsSuccessViews) findViewById(R.id.ldv_content);
        leadingDotsSuccessViews.setDotsNum(2);
        if ("H".equals(user_info_level.trim())) {
            leadingDotsSuccessViews.setDotsNum(3);
        } else if ("A".equals(user_info_level.trim())) {
            leadingDotsSuccessViews.setDotsNum(4);
        } else if ("C".equals(user_info_level.trim())) {
            leadingDotsSuccessViews.setDotsNum(4);
        } else if ("D".equals(user_info_level.trim())) {
            leadingDotsSuccessViews.setDotsNum(5);
        }

    }

    //获取用户填写的基本信息
    private void initData() {
        CActivateLineSearchRequest request = new CActivateLineSearchRequest();
        request.setVersion(RyxcreditConfig.getVersion());
        httpsPost(this, request, ReqAction.APPLICATION_CREATE_OR_CARD_PAYMENT_BOSS, CActivateLineSearchResponse.class, new ICallback<CActivateLineSearchResponse>() {
            @Override
            public void success(final CActivateLineSearchResponse cActivateLineResponse) {
                customer = cActivateLineResponse.getResult().getCustomer();
                province = cActivateLineResponse.getResult().getProvince();
                city =cActivateLineResponse.getResult().getCity();
                region =cActivateLineResponse.getResult().getRegion();
                changeFragmentPosition(BaseInfoEnum.WORK_INFO);
            }


            @Override
            public void failture(String tips) {
                changeFragmentPosition(BaseInfoEnum.WORK_INFO);
            }
        });

    }

    @Override
    public void setbottomMenu() {
        super.setbottomMenu();
    }

    private RYDBossFaceCollectFragment faceCollectFragment;
    private RYDBasePersonalInfoFragmentJHSuccessed personfragment;

    /**
     * 切换fragment
     *
     * @param baseinfo
     */
    private void changeFragmentPosition(BaseInfoEnum baseinfo) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(baseinfo.name());
        if (baseinfo == BaseInfoEnum.WORK_INFO) {
            if (fragment == null)
                fragment = new RYDBossBaseWorkInfoFragmentJHSuccess();
            leadingDotsSuccessViews.change(0);
            //setTitleLayout(getResources().getString(R.string.c_work_info), true, false);
            tv_title.setText("工作信息");
        } else if (baseinfo == BaseInfoEnum.PERSON_INFO) {
            if (fragment == null) {
                personfragment = new RYDBasePersonalInfoFragmentJHSuccessed();
                iPhoneListener = (IPhoneListener) personfragment;
                fragment = personfragment;
            }
            leadingDotsSuccessViews.change(1);
          //  setTitleLayout(getResources().getString(R.string.c_person_info), true, false);
            tv_title.setText("个人信息");
        }
        //六选一
        else if (baseinfo == BaseInfoEnum.SIXCHOOSEONE_INFO) {
   /*         if (fragment == null)
                fragment = new CenterBankCreditFragment();
            if ("A".equals(user_info_level.trim()) || "D".equals(user_info_level.trim())) {
                leadingDotsSuccessViews.change(2);
            }
            setTitleLayout(getResources().getString(R.string.c_pedestrianletter_info), true, false);*/
            if (fragment == null)
                fragment = new RYDSixChooseOneFragment();
            if ("A".equals(user_info_level.trim()) ) {
                leadingDotsSuccessViews.change(2);
                tv_title.setText("额度激活");
            }else if( "D".equals(user_info_level.trim())){
                leadingDotsSuccessViews.change(3);
                tv_title.setText("额度激活");
            }
            // setTitleLayout(getResources().getString(R.string.xjd_c_quota_activation), true, false);

        } else if (baseinfo == BaseInfoEnum.CALLRECORDS_INFO) {
            if (fragment == null)
                fragment = new RYDCallRecordsFragment();
            if ("C".equals(user_info_level.trim())) {
                leadingDotsSuccessViews.change(2);
                tv_title.setText("运营商信息");
            } else if ("D".equals(user_info_level.trim())) {
                leadingDotsSuccessViews.change(2);
                tv_title.setText("运营商信息");
            }
            //setTitleLayout(getResources().getString(R.string.c_callRecords_info), true, false);
            // tv_title.setText("运营商信息");
        } else if (baseinfo == BaseInfoEnum.FACERECOGNITION_INFO) {
            if (fragment == null) {
                faceCollectFragment = new RYDBossFaceCollectFragment();
                fragment = faceCollectFragment;
            }
            //基本信息+运营商数据
            if ("C".equals(user_info_level.trim())) {
                leadingDotsSuccessViews.change(3);
            }//基本信息
            else if ("H".equals(user_info_level.trim())) {
                leadingDotsSuccessViews.change(2);
            }//基本信息+人行征信
            else if ("A".equals(user_info_level.trim())) {
                leadingDotsSuccessViews.change(3);
            }//基本信息+人行征信+运营商数据
            else if ("D".equals(user_info_level.trim())) {
                leadingDotsSuccessViews.change(4);
            }
            //  setTitleLayout(getResources().getString(R.string.c_facerecognition_info), true, false);
            tv_title.setText("人脸识别");
        }//人行征信
        else if (baseinfo == BaseInfoEnum.PEDSTRIANLETTER_INFO) {
            if (fragment == null)
                fragment = new RYDBossCentBankFragment();
            if ("A".equals(user_info_level.trim()) ) {
                leadingDotsSuccessViews.change(2);
                tv_title.setText("人行征信报告");
            }else if("D".equals(user_info_level.trim())){
                leadingDotsSuccessViews.change(3);
                tv_title.setText("人行征信报告");
            }
            //setTitleLayout(getResources().getString(R.string.c_pedestrianletter_info), true, false);

        }//信用卡账单
        /*else if (baseinfo == BaseInfoEnum.CREDITCARDBILL_INFO) {
            if (fragment == null)
                fragment = new CreditCardBillFragment();
            if ("A".equals(user_info_level.trim()) ) {
                leadingDotsSuccessViews.change(2);
                tv_title.setText("信用卡账单");
            }else if("D".equals(user_info_level.trim())){
                leadingDotsSuccessViews.change(3);
                tv_title.setText("信用卡账单");
            }
            // setTitleLayout(getResources().getString(R.string.c_creditcardbill_info), true, false);

        }*///淘宝
        else if (baseinfo == BaseInfoEnum.TAOBAO_INFO) {
            if (fragment == null)
                fragment = new RYDTaoBaoFragment();
            if ("A".equals(user_info_level.trim())) {
                leadingDotsSuccessViews.change(2);
                tv_title.setText("淘宝认证");
            }else if("D".equals(user_info_level.trim())){
                leadingDotsSuccessViews.change(3);
                tv_title.setText("淘宝认证");
            }
            // setTitleLayout(getResources().getString(R.string.c_taobao_info), true, false);
            tv_title.setText("淘宝认证");
        }//京东
        else if (baseinfo == BaseInfoEnum.JINGDONG_INFO) {
            if (fragment == null)
                fragment = new RYDJingDongFragment();
            if ("A".equals(user_info_level.trim()) ) {
                leadingDotsSuccessViews.change(2);
                tv_title.setText("京东认证");
            }else if("D".equals(user_info_level.trim())){
                leadingDotsSuccessViews.change(3);
                tv_title.setText("京东认证");
            }
            //  setTitleLayout(getResources().getString(R.string.c_jingdong_info), true, false);
        }
        //webview
        else if (baseinfo == BaseInfoEnum.WEBVIEW_INFO) {
            if (fragment == null)
                fragment = new RYDWebViewFragment();
            if ("A".equals(user_info_level.trim()) ) {
                leadingDotsSuccessViews.change(2);
                tv_title.setText("社保信息认证");
            }else if("D".equals(user_info_level.trim())){
                leadingDotsSuccessViews.change(3);
                tv_title.setText("社保信息认证");
            }
            //  setTitleLayout(getResources().getString(R.string.c_jingdong_info), true, fale);

        }

        //fundwebview
        else if (baseinfo == BaseInfoEnum.FUNDWEBVIEW_INFO) {
            if (fragment == null)
                fragment = new RYDFundWebViewFragment();
            if ("A".equals(user_info_level.trim()) ) {
                leadingDotsSuccessViews.change(2);
                tv_title.setText("公积金信息认证");
            }else if("D".equals(user_info_level.trim())){
                leadingDotsSuccessViews.change(3);
                tv_title.setText("公积金信息认证");
            }
            //  setTitleLayout(getResources().getString(R.string.c_jingdong_info), true, false);

        }
       /* //人行征信
        else if (baseinfo == BaseInfoEnum.PEDSTRIANLETTER_INFO) {
            if (fragment == null)
                fragment = new RYDBossCentBankFragment();
            if ("A".equals(user_info_level.trim()) || "D".equals(user_info_level.trim())) {
                leadingDotsSuccessViews.change(2);
            }
            setTitleLayout(getResources().getString(R.string.c_pedestrianletter_info), true, false);
        } else if (baseinfo == BaseInfoEnum.CALLRECORDS_INFO) {
            if (fragment == null)
                fragment = new RYDBossCallRecordsFragment();
            if ("C".equals(user_info_level.trim())) {
                leadingDotsSuccessViews.change(2);
            } else if ("D".equals(user_info_level.trim())) {
                leadingDotsSuccessViews.change(3);
            }
            setTitleLayout(getResources().getString(R.string.c_callRecords_info), true, false);

        } else if (baseinfo == BaseInfoEnum.FACERECOGNITION_INFO) {
            if (fragment == null) {
                faceCollectFragment = new RYDBossFaceCollectFragment();
                fragment = faceCollectFragment;
            }
            //基本信息+运营商数据
            if ("C".equals(user_info_level.trim())) {
                leadingDotsSuccessViews.change(3);
            }//基本信息
            else if ("H".equals(user_info_level.trim())) {
                leadingDotsSuccessViews.change(2);
            }//基本信息+人行征信
            else if ("A".equals(user_info_level.trim())) {
                leadingDotsSuccessViews.change(3);
            }//基本信息+人行征信+运营商数据
            else if ("D".equals(user_info_level.trim())) {
                leadingDotsSuccessViews.change(4);
            }
            setTitleLayout(getResources().getString(R.string.c_facerecognition_info), true, false);
        }*/
        if (fragment != null) {
            //瑞易贷
            args = new Bundle();
            args.putSerializable("data", cSupplementaryMaterialsRequest);
            args.putSerializable("productId", productId);
            args.putSerializable("user_level", user_level);
            args.putString("xjd_url_address", trade_product_url);
            fragment.setArguments(args);
            fragmentTransaction.replace(R.id.c_fl_base_info, fragment, baseinfo.name());
            fragmentTransaction.addToBackStack(baseinfo.name());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void setPersonInfo(Object personInfo) {
        changeFragmentPosition(BaseInfoEnum.PERSON_INFO);
        submitSuppliementaryMaterials();
    }

    public void setFaceCollectInfo() {
        changeFragmentPosition(BaseInfoEnum.FACERECOGNITION_INFO);
    }

    public void setWorkInfo(Object workInfo) {
        cSupplementaryMaterialsRequest = (CSupplementaryMaterialsRequest) workInfo;
        submitSuppliementaryMaterials();
    }

    public void setCallRecordInfo(Object callRecordInfo) {
        cSupplementaryMaterialsRequest = (CSupplementaryMaterialsRequest) callRecordInfo;
        submitSuppliementaryMaterials();
    }

    public void setCallRecordInfo() {
        changeFragmentPosition(BaseInfoEnum.CALLRECORDS_INFO);
        submitSuppliementaryMaterials();
    }

    public void setPedestrianLetter() {
        changeFragmentPosition(BaseInfoEnum.SIXCHOOSEONE_INFO);
   //     submitSuppliementaryMaterials();
    }

    public void setSixChooseOne() {
        changeFragmentPosition(BaseInfoEnum.PEDSTRIANLETTER_INFO);
        submitSuppliementaryMaterials();
    }
    //切换信用卡
    public void setSixChooseOneCredit() {
        changeFragmentPosition(BaseInfoEnum.CREDITCARDBILL_INFO);
     //   submitSuppliementaryMaterials();
    }
    //切换淘宝
    public void setSixChooseOneTaoBao() {
        changeFragmentPosition(BaseInfoEnum.TAOBAO_INFO);
    //    submitSuppliementaryMaterials();
    }
    //切换京东
    public void setSixChooseOneJingDong() {
        changeFragmentPosition(BaseInfoEnum.JINGDONG_INFO);
      //  submitSuppliementaryMaterials();
    }
    /*    public void setFaceCollectInfo(Object faceCollectInfo) {
            changeFragmentPosition(BaseInfoEnum.FACERECOGNITION_INFO);
            submitSuppliementaryMaterials();
        }*/
    //切换webview
    public void setSixChooseOneWebview() {
        changeFragmentPosition(BaseInfoEnum.WEBVIEW_INFO);
     //   submitSuppliementaryMaterials();
    }
    //切换公积金webview
    public void setFundSixChooseOneWebview() {
        changeFragmentPosition(BaseInfoEnum.FUNDWEBVIEW_INFO);
     //   submitSuppliementaryMaterials();
    }

    public void setFaceCollectInfo(Object faceCollectInfo) {
        changeFragmentPosition(BaseInfoEnum.FACERECOGNITION_INFO);
        submitSuppliementaryMaterials();
    }

    @Override
    public void setApplyInfo(Object applyInfo) {
        changeFragmentPosition(BaseInfoEnum.APPLY_INFO);
    }

    @Override
    public void setOtherInfo(Object otherInfo) {
        cSupplementaryMaterialsRequest = (CSupplementaryMaterialsRequest) otherInfo;
        changeFragmentPosition(BaseInfoEnum.PERSON_INFO);
    }

    /**
     * 提交个人资料
     */
    private void submitSuppliementaryMaterials() {
        RyxcreditConfig.context = this;
        addPersonMaterial(cSupplementaryMaterialsRequest);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment_person = fragmentManager.findFragmentByTag(BaseInfoEnum.PERSON_INFO.name());
        Fragment fragment_work = fragmentManager.findFragmentByTag(BaseInfoEnum.WORK_INFO.name());
        Fragment fragment_face = fragmentManager.findFragmentByTag(BaseInfoEnum.FACERECOGNITION_INFO.name());
        Fragment fragment_contact = fragmentManager.findFragmentByTag(BaseInfoEnum.CALLRECORDS_INFO.name());
        Fragment fragment_sixchooseone = fragmentManager.findFragmentByTag(BaseInfoEnum.SIXCHOOSEONE_INFO.name());
        Fragment fragment_taobao = fragmentManager.findFragmentByTag(BaseInfoEnum.TAOBAO_INFO.name());
        Fragment fragment_jingdong = fragmentManager.findFragmentByTag(BaseInfoEnum.JINGDONG_INFO.name());
        Fragment fragment_creditcardbill = fragmentManager.findFragmentByTag(BaseInfoEnum.CREDITCARDBILL_INFO.name());
        Fragment fragment_webview = fragmentManager.findFragmentByTag(BaseInfoEnum.WEBVIEW_INFO.name());
        Fragment fragment_fundwebview = fragmentManager.findFragmentByTag(BaseInfoEnum.FUNDWEBVIEW_INFO.name());
        Fragment fragment_pedstrianletter = fragmentManager.findFragmentByTag(BaseInfoEnum.PEDSTRIANLETTER_INFO.name());
        if (null != fragment_work && fragment_work.isVisible()) {
            finish();
            return;
        }
        if (null != fragment_person && fragment_person.isVisible()) {
            leadingDotsSuccessViews.change(0);
            //setFragmentTitleLayout(getResources().getString(R.string.c_work_info), true, false);
            tv_title.setText("工作信息");
            super.onBackPressed();
        }
        if(null != fragment_contact&& fragment_contact.isVisible()){
            switch (user_info_level.trim()){
                case "C":
                    leadingDotsSuccessViews.change(1);
                    //        setFragmentTitleLayout(getResources().getString(R.string.c_person_info), true, false);
                    tv_title.setText("个人信息");
                    break;
                case "D":
                    leadingDotsSuccessViews.change(3);
                    // setFragmentTitleLayout(getResources().getString(R.string.c_pedestrianletter_info), true, false);
                    tv_title.setText("人行征信");
                    break;
            }
            super.onBackPressed();
        }
        if(null != fragment_sixchooseone&& fragment_sixchooseone.isVisible()){
            switch (user_info_level.trim()){
                case "A":
                    leadingDotsSuccessViews.change(1);
                    // setFragmentTitleLayout(getResources().getString(R.string.c_person_info), true, false);
                    tv_title.setText("个人信息");
                    break;
                case "D":
                    leadingDotsSuccessViews.change(1);
                    tv_title.setText("个人信息");
                    //  setFragmentTitleLayout(getResources().getString(R.string.c_person_info), true, false);
                    break;
            }
            super.onBackPressed();
        }

        if(null != fragment_pedstrianletter&&fragment_pedstrianletter .isVisible()){
            switch (user_info_level.trim()){
                case "A":
                    leadingDotsSuccessViews.change(2);
                    // setFragmentTitleLayout(getResources().getString(R.string.c_person_info), true, false);
                    tv_title.setText("人行征信");
                    break;
                case "D":
                    leadingDotsSuccessViews.change(3);
                    tv_title.setText("人行征信");
                    //  setFragmentTitleLayout(getResources().getString(R.string.c_person_info), true, false);
                    break;
            }
            super.onBackPressed();
        }
        if(null != fragment_taobao&&fragment_taobao .isVisible()){
            switch (user_info_level.trim()){
                case "A":
                    leadingDotsSuccessViews.change(2);
                    // setFragmentTitleLayout(getResources().getString(R.string.c_person_info), true, false);
                    tv_title.setText("人行征信");
                    break;
                case "D":
                    leadingDotsSuccessViews.change(3);
                    tv_title.setText("人行征信");
                    //  setFragmentTitleLayout(getResources().getString(R.string.c_person_info), true, false);
                    break;
            }
            super.onBackPressed();
        }

        if(null != fragment_jingdong&&fragment_jingdong .isVisible()){
            switch (user_info_level.trim()){
                case "A":
                    leadingDotsSuccessViews.change(2);
                    // setFragmentTitleLayout(getResources().getString(R.string.c_person_info), true, false);
                    tv_title.setText("人行征信");
                    break;
                case "D":
                    leadingDotsSuccessViews.change(3);
                    tv_title.setText("人行征信");
                    //  setFragmentTitleLayout(getResources().getString(R.string.c_person_info), true, false);
                    break;
            }
            super.onBackPressed();
        }
        if(null !=fragment_creditcardbill && fragment_creditcardbill.isVisible()){
            switch (user_info_level.trim()){
                case "A":
                    leadingDotsSuccessViews.change(2);
                    // setFragmentTitleLayout(getResources().getString(R.string.c_person_info), true, false);
                    tv_title.setText("额度激活");
                    break;
                case "D":
                    leadingDotsSuccessViews.change(3);
                    tv_title.setText("额度激活");
                    //  setFragmentTitleLayout(getResources().getString(R.string.c_person_info), true, false);
                    break;
            }
            super.onBackPressed();
        }
        if (null != fragment_face && fragment_face.isVisible()) {
            switch (user_info_level.trim()){
                case "H":
                    leadingDotsSuccessViews.change(1);
                    // setTitleLayout(getResources().getString(R.string.c_person_info), true, false);
                    tv_title.setText("个人信息");
                    break;
                case "A":
                    leadingDotsSuccessViews.change(2);
                    // setTitleLayout(getResources().getString(R.string.c_pedestrianletter_info), true, false);
                    tv_title.setText("人行征信");
                    break;
                case "D":
                    leadingDotsSuccessViews.change(2);
                    //   setTitleLayout(getResources().getString(R.string.c_callRecords_info), true, false);
                    tv_title.setText("运营商信息");
                    break;
                case "C":
                    leadingDotsSuccessViews.change(2);
                    // setTitleLayout(getResources().getString(R.string.c_callRecords_info), true, false);
                    tv_title.setText("运营商信息");
                    break;
            }
            super.onBackPressed();
        }
/*
* webview
* */
        if (null != fragment_webview && fragment_webview.isVisible()) {
            switch (user_info_level.trim()){
                case "H":
                    leadingDotsSuccessViews.change(1);
                    // setTitleLayout(getResources().getString(R.string.c_person_info), true, false);
                    tv_title.setText("个人信息");
                    break;
                case "A":
                    leadingDotsSuccessViews.change(2);
                    // setTitleLayout(getResources().getString(R.string.c_pedestrianletter_info), true, false);
                    tv_title.setText("人行征信报告");
                    break;
                case "D":
                    leadingDotsSuccessViews.change(2);
                    // setTitleLayout(getResources().getString(R.string.c_callRecords_info), true, false);
                    tv_title.setText("运营商信息");
                    break;
                case "C":
                    leadingDotsSuccessViews.change(2);
                    setTitleLayout(getResources().getString(R.string.c_callRecords_info), true, false);
                    tv_title.setText("运营商信息");
                    break;
            }
            super.onBackPressed();
        }
               /*
* 公积金webview
* */
        if (null !=fragment_fundwebview  &&fragment_fundwebview .isVisible()) {
            switch (user_info_level.trim()){
                case "H":
                    leadingDotsSuccessViews.change(1);
                    // setTitleLayout(getResources().getString(R.string.c_person_info), true, false);
                    tv_title.setText("个人信息");
                    break;
                case "A":
                    leadingDotsSuccessViews.change(2);
                    // setTitleLayout(getResources().getString(R.string.c_pedestrianletter_info), true, false);
                    tv_title.setText("人行征信报告");
                    break;
                case "D":
                    leadingDotsSuccessViews.change(2);
                    // setTitleLayout(getResources().getString(R.string.c_callRecords_info), true, false);
                    tv_title.setText("运营商信息");
                    break;
                case "C":
                    leadingDotsSuccessViews.change(2);
                    setTitleLayout(getResources().getString(R.string.c_callRecords_info), true, false);
                    tv_title.setText("运营商信息");
                    break;
            }
            super.onBackPressed();
        }
    }

    /**
     * 上传个人资料
     *
     * @param req
     */
    private void addPersonMaterial(CSupplementaryMaterialsRequest req) {
        httpsPost(this, req, ReqAction.APPLICATION_CREATE_OR_MODIFY_CUSTOMER, CSupplementaryMaterialsResponse.class, new ICallback<CSupplementaryMaterialsResponse>() {
            @Override
            public void success(CSupplementaryMaterialsResponse response) {
                customer.setContact_name(cSupplementaryMaterialsRequest.getContact_name());
                customer.setContact_phone_num(cSupplementaryMaterialsRequest.getCompany_phone_num());
                customer.setContact_relation(cSupplementaryMaterialsRequest.getContact_relation());
                customer.setMarital_status(cSupplementaryMaterialsRequest.getMarital_status());
                customer.setEducation_status(cSupplementaryMaterialsRequest.getEducation_status());
                customer.setCompany_address(cSupplementaryMaterialsRequest.getCompany_address());
                customer.setCompany_name(cSupplementaryMaterialsRequest.getCompany_name());
                customer.setCompany_phone_num(cSupplementaryMaterialsRequest.getCompany_phone_num());
                customer.setIndustry_type(cSupplementaryMaterialsRequest.getIndustry_type());
                customer.setMonthly_income(cSupplementaryMaterialsRequest.getMonthly_income());
                //不需要采集电商信息
                if ("H".equals(user_info_level.trim())) {
                    changeFragmentPosition(BaseInfoEnum.FACERECOGNITION_INFO);
                }
                //需要采集电商信息
                else if ("C".equals(user_info_level.trim())) {
                    changeFragmentPosition(BaseInfoEnum.CALLRECORDS_INFO);
                }

            }

            @Override
            public void failture(String tips) {
                CLogUtil.showToast(RKDBossBaseInfoSuccessActivity.this, tips);
                changeFragmentPosition(BaseInfoEnum.CALLRECORDS_INFO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment_face = fragmentManager.findFragmentByTag(BaseInfoEnum.FACERECOGNITION_INFO.name());
        Fragment fragment_person = fragmentManager.findFragmentByTag(BaseInfoEnum.PERSON_INFO.name());
        if (fragment_face != null && fragment_face.isVisible()) {
            faceCollectFragment.onActivityResult(requestCode, resultCode, data);
        } else if (null != fragment_person && fragment_person.isVisible()) {
            personfragment.onActivityResult(requestCode, resultCode, data);
        }
    }

}
