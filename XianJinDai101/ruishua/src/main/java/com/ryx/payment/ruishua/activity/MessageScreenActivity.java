package com.ryx.payment.ruishua.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;

import com.ryx.payment.ruishua.R;
import com.ryx.payment.ruishua.bean.MsgInfo;
import com.ryx.payment.ruishua.bean.Param;
import com.ryx.payment.ruishua.bean.RyxPayResult;
import com.ryx.payment.ruishua.fragment.MessagePersonalFragment;
import com.ryx.payment.ruishua.fragment.MessagePubFragment;
import com.ryx.payment.ruishua.listener.FragmentListener;
import com.ryx.payment.ruishua.net.XmlCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_message_screen)
public class MessageScreenActivity extends BaseActivity implements FragmentListener {

    @ViewById
    TabLayout commonTabLayout;
    Param qtpayNoticeCode;
    ArrayList<MsgInfo> noticeList = new ArrayList<MsgInfo>();
    private  final  int  WILL_HAS_UPDATE=21;
    private int offset = 1;

    MessagePersonalFragment personalFragment;
    MessagePubFragment pubFragment;

    private String jsonData;
    private int unreadNoticeNumber = 0;//未读公共消息
    private  int unreadPersonNoticeNumber = 0;//未读个人消息
    private String tag="pubNotice";

    @AfterViews
    public void initViews() {
        setTitleLayout("我的消息", true, false);
        initQtPatParams();
        pubFragment=  MessagePubFragment.newInstance();
        personalFragment= MessagePersonalFragment.newInstance();
        commonTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    topubFrag();
                }else {
                    topersonalFrg();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        topubFrag();
    }

    /**
     * 公共消息
     */
    private void topubFrag(){
        tag="pubNotice";
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.tabfragemetcomtent,pubFragment,"pubNotice");
        fragmentTransaction.commit();
    }

    /**
     * 个人消息
     */
    private void topersonalFrg(){
        tag="personalNotice";
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.tabfragemetcomtent,personalFragment,"personalNotice");
        fragmentTransaction.commit();
    }

    /**
     * 初始化网络请求参数
     *
     * @author
     */
    @Override
    public void initQtPatParams() {
        super.initQtPatParams();
        qtpayNoticeCode = new Param("noticeCode");
    }

    //查询个人的已读和未读消息
    @Override
    public void doDataRequest(Object data) {
        qtpayApplication = new Param("application", "GetPublicNotice.Req");
        qtpayNoticeCode = new Param("noticeCode");
        qtpayNoticeCode.setValue("0000");
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(qtpayNoticeCode);
        qtpayParameterList.add(new Param("noticeType","2"));
        qtpayParameterList.add(new Param("readFlag","2"));
        qtpayParameterList.add(new Param("offset",offset+""));
        httpsPost("GetPublicNotice", new XmlCallback() {
            @Override
            public void onTradeSuccess(RyxPayResult payResult) {
                if("personalNotice".equals(tag)){
                    personalFragment.send(0x111,payResult);
                }else if("pubNotice".equals(tag)){
                    pubFragment.send(0x111,payResult);
                }
            }

            @Override
            public void onTradeFailed() {
                super.onTradeFailed();
                if("personalNotice".equals(tag)){
                    personalFragment.send(0x222,null);
                }else if("pubNotice".equals(tag)){
                    pubFragment.send(0x222,null);
                }
            }

            @Override
            public void onOtherState() {
                super.onOtherState();
                if("personalNotice".equals(tag)){
                    personalFragment.send(0x222,null);
                }else if("pubNotice".equals(tag)){
                    pubFragment.send(0x222,null);
                }
            }
        });
    }

    @Override
    public void doDataRequest(String type, Object data) {

    }

    //判断公共消息的读取状态
    private boolean getReadState(String noticeCode) {
        int len = noticeList.size();
        for (int i = 0; i < len; i++) {
            if (noticeCode.equals(noticeList.get(i).getNoticeCode())) {
                return noticeList.get(i).isReaded();
            }
        }
        return false;
    }

    @Click(R.id.tileleftImg)
    public void cancelPage() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        saveNoticeList();
    }
}
