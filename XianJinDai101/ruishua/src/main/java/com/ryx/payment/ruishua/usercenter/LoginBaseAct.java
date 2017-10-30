package com.ryx.payment.ruishua.usercenter;

import android.content.Intent;
import android.text.TextUtils;

import com.ryx.payment.ruishua.RyxAppconfig;
import com.ryx.payment.ruishua.RyxAppdata;
import com.ryx.payment.ruishua.activity.BaseActivity;
import com.ryx.payment.ruishua.activity.MainFragmentActivity_;
import com.ryx.payment.ruishua.activity.QtpayAppData;
import com.ryx.payment.ruishua.bean.MsgInfo;
import com.ryx.payment.ruishua.bean.Param;
import com.ryx.payment.ruishua.bean.RyxPayResult;
import com.ryx.payment.ruishua.net.XmlCallback;
import com.ryx.payment.ruishua.utils.DataUtil;
import com.ryx.payment.ruishua.utils.DateUtil;
import com.ryx.payment.ruishua.utils.GesturesPaswdUtil;
import com.ryx.payment.ruishua.utils.JsonUtil;
import com.ryx.payment.ruishua.utils.LogUtil;
import com.ryx.payment.ruishua.utils.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/19.
 */

public class LoginBaseAct extends BaseActivity {
    protected ArrayList<String> userList = new ArrayList<String>();//账号列表
    protected ArrayList<MsgInfo> noticeList = new ArrayList<MsgInfo>();//通知列表
    protected ArrayList<MsgInfo> noticeTempOldList = new ArrayList<MsgInfo>();
    protected ArrayList<MsgInfo> noticeTempNewList = new ArrayList<MsgInfo>();
    protected boolean isTokenIntent;
    protected String toastmsg;
    protected int unreadNoticeNumber = 0;//通知数量
    protected int unreadPersonalNoticeNumber = 0;//个人消息数量
    private String misLastLoginInfo = "";
    private int QUICKPAY_OPEN=106;//开通快捷支付跳转code
    int quickPaymentSwitch = -1;//是否需要去请求快捷支付接口

    //瑞刷登录解析
    public void doRSAnalyze(String accountNumber, RyxPayResult qtpayResult) {
//        getAppCustomerInfo(qtpayResult);
        saveUserInfo(accountNumber, qtpayResult, null);
        quickPaymentSwitch = qtpayResult.getQuickPaymentSwitch();
    }

    //保存账号
    protected void saveAccount(String account) {
        if (!TextUtils.isEmpty(account)) {
            userList.remove(account);
            userList.add(account);
            if (userList.size() > 5) {
                userList.remove(0);
            }
        }
    }

    //保存用户信息
    protected void saveUserInfo(String account, RyxPayResult qtpayResult, CompleteResultListen completeResultListen) {
        //是否打开商户认证功能1打开，0不打开
        try {
            RyxAppdata.getInstance(LoginBaseAct.this).setbeanStatusDesc(qtpayResult.getBean_status_desc());
            RyxAppdata.getInstance(LoginBaseAct.this).setkjzfTouchPayTag(qtpayResult.getKjzf_touch_pay());
            RyxAppdata.getInstance(LoginBaseAct.this).setIsOpenMerchantFlag(Integer.parseInt(qtpayResult.getMerchant_status()));
        } catch (Exception e) {

        }
        String verifiedSwitch = qtpayResult.getVerifiedSwitch();
        if ("1".equals(verifiedSwitch)) {
//            VerifiedSwitch（1：活体采集认证，0：原先实名认证通道）
            RyxAppdata.getInstance(LoginBaseAct.this).setAuthprocessswitch(true);
        } else {
            RyxAppdata.getInstance(LoginBaseAct.this).setAuthprocessswitch(false);
        }

        RyxAppdata.getInstance(LoginBaseAct.this).setToken(qtpayResult.getToken());
        RyxAppdata.getInstance(LoginBaseAct.this).setMobileNo(
                qtpayResult.getMobileNo());
        RyxAppdata.getInstance(LoginBaseAct.this)
                .setPhone(qtpayResult.getMobileNo());
        RyxAppdata.getInstance(LoginBaseAct.this).setCustomerName(
                "" + qtpayResult.getUserName());
        RyxAppdata.getInstance(LoginBaseAct.this).setRealName(
                "" + qtpayResult.getRealName());
        RyxAppdata.getInstance(LoginBaseAct.this).setLogin(true);

        RyxAppdata.getInstance(LoginBaseAct.this).setAuthenFlag(
                qtpayResult.getAuthenFlag());
        RyxAppdata.getInstance(LoginBaseAct.this).setCustomerId(
                qtpayResult.getCustomerId());
        RyxAppdata.getInstance(LoginBaseAct.this).setCertPid(
                qtpayResult.getCertPid());
        RyxAppdata.getInstance(LoginBaseAct.this).setCertType(
                qtpayResult.getCertType());
        RyxAppdata.getInstance(LoginBaseAct.this).setUserType(
                qtpayResult.getUserType());
        RyxAppdata.getInstance(LoginBaseAct.this).setEmail(
                "" + qtpayResult.getEmail());
        RyxAppdata.getInstance(LoginBaseAct.this).setTransLogNo(
                qtpayResult.getTransLogNo());
        RyxAppdata.getInstance(LoginBaseAct.this).setTagDesc(qtpayResult.getTagDesc());
        String data = qtpayResult.getData();

        //手势密码错误次数改为0
        String user_id = RyxAppdata.getInstance(LoginBaseAct.this).getCustomerId();
        GesturesPaswdUtil spUserid = new GesturesPaswdUtil(getApplicationContext(), user_id);
        spUserid.saveSharedPreferences("errorcount", 0);

        try {
            JSONObject noticeObj = new JSONObject(data);
            String rsfee = noticeObj.getString("rsfee");
            String rsRate = noticeObj.getString("rsRate");
            String productId = noticeObj.getString("productId");
            String merchantId = noticeObj.getString("merchantId");
            RyxAppdata.getInstance(LoginBaseAct.this).setRsRate(rsRate);
            RyxAppdata.getInstance(LoginBaseAct.this).setRsfee(rsfee);
            RyxAppdata.getInstance(LoginBaseAct.this).setProductId(productId);
            RyxAppdata.getInstance(LoginBaseAct.this).setMerchantId(merchantId);
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
        if (completeResultListen != null) {
            completeResultListen.compleResultok();
        }
        // 先读取本地的，再去读取最新的消息
        getNoticeList();
        getNoticeJsonData(account, qtpayResult.getLaslogininfo());
    }

    /**
     * 读取本地已经存储的消息
     */
    private void getNoticeList() {
        ObjectInputStream in = null;
        try {
            InputStream is = openFileInput("notice_"
                    + RyxAppdata.getInstance(LoginBaseAct.this).getCustomerId()
                    + ".obj");
            in = new ObjectInputStream(is);
            if (in != null) {
                noticeTempOldList = (ArrayList<MsgInfo>) in.readObject();
            }
        } catch (Exception e) {
            if (noticeTempOldList == null) {
                noticeTempOldList = new ArrayList<MsgInfo>();
            }
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        noticeTempOldList = DataUtil.removeDuplicate(noticeTempOldList);
        int len = noticeTempOldList.size();
        for (int i = 0; i < len; i++) {
            String activeTime = noticeTempOldList.get(i).getActiveTime();
            if (TextUtils.isEmpty(activeTime)) {
                continue;
            }
            if (activeTime.length() > 8)
                activeTime = activeTime.substring(0, 8);
            Date date = DateUtil.parseDate(activeTime, "yyyyMMdd");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
            String str = formatter.format(curDate);
            Date tempCurDate = DateUtil.parseDate(str, "yyyyMMdd");
            boolean isActive = compareDate(date, tempCurDate);
            if (!isActive) {
                noticeTempOldList.remove(i);
                len = len - 1;
            }
        }
    }

    private boolean compareDate(Date activedate, Date curdate) {
        if (DateUtil.getYear(activedate) < DateUtil.getYear(curdate)) {
            return false;
        } else if (DateUtil.getYear(activedate) == DateUtil.getYear(curdate)) {
            if (DateUtil.getMonth(activedate) < DateUtil.getMonth(curdate)) {
                return false;
            } else if (DateUtil.getMonth(activedate) == DateUtil
                    .getMonth(curdate)) {
                if (DateUtil.getDay(activedate) < DateUtil
                        .getDay(curdate)) {
                    return false;
                } else {
                    return true;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    //获取通知内容
    public void getNoticeJsonData(final String account, final String laslogininfo) {
        misLastLoginInfo = laslogininfo;
        qtpayApplication.setValue("GetPublicNotice.Req");
        Param qtpayNoticeCode = new Param("noticeCode", "0000");
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(qtpayNoticeCode);
        qtpayParameterList.add(new Param("noticeType", "2"));
        qtpayParameterList.add(new Param("readFlag", "2"));
        qtpayParameterList.add(new Param("offset", "1"));
        httpsPost(false, true, "GetPublicNotice", new XmlCallback() {
            @Override
            public void onTradeSuccess(RyxPayResult payResult) {
                analyzeNotices(payResult.getData());
                saveNoticeList();
                saveAccount(account);
                checkQuickPay();
            }


            @Override
            public void onOtherState() {

            }

            @Override
            public void onTradeFailed() {

            }
        });
    }

    /**
     * 判断用户是否需要开通快捷支付,quickPaymentSwitch： 0是去请求  1是不用请求；
     */
    public void checkQuickPay() {
        LogUtil.showLog("quickPaymentSwitch----",quickPaymentSwitch+"----");
        if (quickPaymentSwitch==0) {
            qtpayApplication.setValue("SupportQuickPayment.Req");
            qtpayAttributeList.add(qtpayApplication);
            httpsPost(false, true, "SupportQuickPayment", new XmlCallback() {
                @Override
                public void onTradeSuccess(RyxPayResult payResult) {
                    LogUtil.showLog("SupportQuickPayment----", payResult.getData() + "------"+payResult.toString());
                    analyzeQuickResult(payResult.getData());
                }

                @Override
                public void onOtherState() {
                    jumpPage();
                }

                @Override
                public void onTradeFailed() {
                    jumpPage();
                }
            });
        }else{
           //如果不需要请求开通快捷支付页面，则执行页面跳转
            jumpPage();
        }
    }

    //解析开通快捷支付返回的内容:
    // "resultcode":"是否展示（0:不展示，1:展示）
    private void analyzeQuickResult(String data){
        if (!TextUtils.isEmpty(data)) {
            try {
                JSONObject resultData = new JSONObject(data);
                if(resultData.has("resultcode")){
                    String resultcode = resultData.getString("resultcode");
                    LogUtil.showLog("resultcode---",resultcode+"---");
                    if("1".equals(resultcode)){
                        openQuickPay(data);
                    }else{
                        //如果不展示开通快捷支付页面，则执行页面跳转
                        jumpPage();
                    }
                }else{
                    //如果不展示开通快捷支付页面，则执行页面跳转
                    jumpPage();
                }
            }catch(JSONException ex){
                ex.printStackTrace();
                jumpPage();
            }
        }else{
            //如果不展示开通快捷支付页面，则执行页面跳转
            jumpPage();
        }
    }

    /**
     * 如果用户需要开通跳转开通快捷支付页面，跳转开通快捷支付页面
     */
    public void openQuickPay(String data) {
        Intent intent = new Intent(LoginBaseAct.this, QuickPayOpenActivity_.class);
        intent.putExtra("resultData",data);
        startActivityForResult(intent,QUICKPAY_OPEN);
    }

    /**
     * 如果用户需要开通快捷支付，则跳转快捷支付页面之后，再返回主页面；
     * 否则直接回主页面
     */
    public void jumpPage() {
        if (isTokenIntent) {
            //如果是token失效跳转到的登录，则跳转到瑞刷主页面
            Intent intent = new Intent(LoginBaseAct.this, MainFragmentActivity_.class);
            intent.putExtra("LoginFlag", true);
            setLaslogininfo(intent, misLastLoginInfo);
            startActivity(intent);
        } else {
            Intent intent = new Intent();
            setLaslogininfo(intent, misLastLoginInfo);
            LogUtil.showLog("Login===showSnakeMsg==" + misLastLoginInfo);
            setResult(RyxAppconfig.LOGINACTFINISH, intent);
        }
        LogUtil.showLog("isTokenIntent==" + isTokenIntent);
        RyxAppconfig.resetTime(LoginBaseAct.this);
        finish();
    }


    /**
     * 登录回显上次登录信息
     *
     * @param intent
     */
    public void setLaslogininfo(Intent intent, String msg) {

        StringBuffer stringBuffer = new StringBuffer();
        try {
            JSONObject msgObj = new JSONObject(msg);
            String create_date = msgObj.getString("create_date");
            String create_time = msgObj.getString("create_time");
            String phone_brand = msgObj.getString("phone_brand");
            String area = msgObj.getString("area");
            stringBuffer.append("上次登录时间:" + DateUtil.StrToDateStr(create_date + create_time, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss") + "\n");
            stringBuffer.append("手机:" + phone_brand + "\n");
            if (!TextUtils.isEmpty(area)) {
                stringBuffer.append("地址:" + area);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(stringBuffer.toString())) {
            intent.putExtra("loginMessage", stringBuffer.toString());
        }
    }

    //解析通知内容
    private void analyzeNotices(String noticeData) {
        if (noticeData != null) {
            try {
                JSONObject noticeObj = new JSONObject(noticeData);
                if (!noticeObj.has("result")) {
                    return;
                }
                if (noticeObj.getJSONObject("result") == null) {
                    return;
                }
                if (!noticeObj.getJSONObject("result").has("message")) {
                    return;
                }
                toastmsg = (String) noticeObj.getJSONObject("result").getString(
                        "message");
                if (!noticeObj.getJSONObject("result").has("resultCode")) {
                    return;
                }
                if (RyxAppconfig.QTNET_SUCCESS.equals(noticeObj.getJSONObject(
                        "result").getString("resultCode"))) {
                    if (!noticeObj.has("resultBean"))
                        return;
                    JSONArray noticeArray = noticeObj.getJSONArray("resultBean");
                    MsgInfo msgInfo = null;
                    int len = noticeArray.length();
                    for (int i = 0; i < len; i++) {
                        msgInfo = new MsgInfo();
                        String noticeCode = JsonUtil.getValueFromJSONObject(
                                noticeArray.getJSONObject(i), "noticeCode");
                        msgInfo.setNoticeCode(noticeCode);
                        msgInfo.setTitle(JsonUtil.getValueFromJSONObject(
                                noticeArray.getJSONObject(i), "title"));
                        msgInfo.setContent(JsonUtil.getValueFromJSONObject(
                                noticeArray.getJSONObject(i), "noticeContent"));
                        msgInfo.setTime(JsonUtil.getValueFromJSONObject(
                                noticeArray.getJSONObject(i), "effectTime"));
                        String noticeType = JsonUtil.getValueFromJSONObject(
                                noticeArray.getJSONObject(i), "noticeType");
                        msgInfo.setNoticeType(noticeType);
                        msgInfo.setActiveTime(JsonUtil.getValueFromJSONObject(
                                noticeArray.getJSONObject(i), "activeTime"));
                        msgInfo.setPopup(JsonUtil.getValueFromJSONObject(
                                noticeArray.getJSONObject(i), "popup"));
                        String readFlag = "";
                        if ("0".equals(noticeType)) {
                            if (!hasNotice(noticeCode)) {
                                msgInfo.setReaded(false);
                                noticeTempNewList.add(msgInfo);
                            }
                        } else if ("1".equals(noticeType)) {
                            readFlag = JsonUtil.getValueFromJSONObject(
                                    noticeArray.getJSONObject(i), "readFlag");
                            msgInfo.setReadFlag(readFlag);
                        }
                        // 个人消息，则根据readFlag判断消息状态
                        if (("1".equals(noticeType) && "0".equals(readFlag))) {
                            unreadPersonalNoticeNumber = unreadPersonalNoticeNumber + 1;
                        }
                    }
                    noticeList.addAll(noticeTempNewList);
                    noticeList.addAll(noticeTempOldList);
                    // 1、公共消息，则根据本地存储的消息状态判断消息是否已读；
                    // 2、历史公共消息，根据isRead判断是否已读；
                    // 3、新获取到的公共消息，根据isRead判断是否已读；
                    int noticelen = noticeList.size();
                    for (int i = 0; i < noticelen; i++) {
                        String noticeType = noticeList.get(i).getNoticeType();
                        boolean isRead = getReadState(noticeList.get(i)
                                .getNoticeCode());
                        if (("0".equals(noticeType) && !isRead)
                                || (TextUtils.isEmpty(noticeType) && !isRead)) {
                            unreadNoticeNumber = unreadNoticeNumber + 1;
                        }
                    }
                    LogUtil.showLog("NoticeNum---login", unreadPersonalNoticeNumber + "---" + unreadNoticeNumber);
                    PreferenceUtil.getInstance(LoginBaseAct.this).saveInt(
                            "unreadNoticeNumber_"
                                    + QtpayAppData.getInstance(LoginBaseAct.this)
                                    .getMobileNo(), unreadNoticeNumber);
                    PreferenceUtil.getInstance(LoginBaseAct.this).saveInt(
                            "unreadNoticePersonNumber_"
                                    + QtpayAppData.getInstance(LoginBaseAct.this)
                                    .getMobileNo(),
                            unreadPersonalNoticeNumber);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                noticeData = null;
            }
        }
    }


    private boolean hasNotice(String noticeCode) {
        int len = noticeTempOldList.size();
        for (int i = 0; i < len; i++) {
            if (noticeCode.equals(noticeTempOldList.get(i).getNoticeCode())) {
                return true;
            }
        }
        return false;
    }

    private boolean getReadState(String noticeCode) {
        int len = noticeTempOldList.size();
        for (int i = 0; i < len; i++) {
            if (noticeCode.equals(noticeTempOldList.get(i).getNoticeCode())) {
                return noticeTempOldList.get(i).isReaded();
            }
        }
        return false;
    }

    /**
     * 保存消息到本地
     */
    public void saveNoticeList() {
        ObjectOutputStream out = null;
        try {
            noticeList = DataUtil.removeDuplicate(noticeList);
            FileOutputStream os = openFileOutput("notice_"
                    + RyxAppdata.getInstance(LoginBaseAct.this).getCustomerId()
                    + ".obj", MODE_PRIVATE);
            out = new ObjectOutputStream(os);
            out.writeObject(noticeList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void saveAccount() {
        ObjectOutputStream out = null;
        try {
            FileOutputStream os = openFileOutput("account.obj",
                    MODE_PRIVATE);
            out = new ObjectOutputStream(os);
            out.writeObject(userList);
        } catch (Exception e) {

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(RyxAppconfig.LOGINACTFINISH==resultCode){
            setResult(resultCode,intent);
            finish();
        }else if(requestCode==QUICKPAY_OPEN){
            jumpPage();
        }

    }
}
