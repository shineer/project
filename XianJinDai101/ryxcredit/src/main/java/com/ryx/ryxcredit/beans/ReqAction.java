package com.ryx.ryxcredit.beans;

/**
 * Author：lijing on 16/6/22 14:37
 * Mail：lijing1-jn@ruiyinxin.com
 * Description：
 */
public enum ReqAction {
    APPLICATION_INCREASE_AMOUNT("申请提升额度", ""),
    APPLICATIOIN_FIND_VIP("是否是VIP查询", "baseData/findVip"),
    APPLICATIOIN_FIND_CUSTOMER("签署协议查询", "baseData/findCustomer"),
    APPLICATION_PRODUCT_DETAIL("产品详情","product/detail"),
    APPLICATION_CARD_REPAYMENT("获取代扣卡列表","card/repayment/find"),
    APPLICATION_CARD_PAYMENT("获取代付卡列表","card/payment/find"),
    APPLICATION_CREATE_OR_MODIFY_CUSTOMER("修改或修改用户信息","baseData/customerInfo/createOrModify"),
    APPLICATION_CREATE_OR_MODIFY_CUSTOMER_BOSS("老板修改或修改用户信息","baseData/boss/saveOrModify"),
    APPLICATION_ACTIVATION_LIMIT("客户激活信息查询","baseData/activationData"),
    APPLICATION_CREATE_OR_CARD_PAYMENT_BOSS("获取老板资料管理页面","baseData/bossData"),
    APPLICATION_CREATE_OR_CARD_PAYMENT("添加或修改客户银行卡信息","card/payment/createOrModify"),
    APPLICATION_LOAN_USER_LEVEL("额度激活","loan/aid/sas/customer/userLevel"),
    APPLICATION_LOAN_APPLY_LIMIT("客户类别资料级别","loan/applyLimit"),
    APPLICATION_MESSAGE_CHALLENGECODE_SEND("发送确认借款验证码","message/challengeCode/send"),
    APPLICATION_BORROW_RECORDS("借款记录","loan/find"),
    APPLICATION_BORROW_RECORD_DETAIL("借款详情","loan/detail"),
    APPLICATION_LOAN_APPLY("确认借款","loan/apply"),
    APPLICATION_REPAYMENT_CALCULATE("还款试算","repayment/calculate"),
    APPLICATION_LOAN_REPAY("确认还款","loan/repay"),
    APPLICATION_AGREEMENT_AGREE("同意协议","agreement/agree"),
    APPLICATION_LOAN_CALCULATE("产品试算","loan/calculate"),
    APPLICATION_WITHHELD_CARD_CERTIFICATE("代扣卡认证","card/repayment/prepare"),
    APPLICATION_CHANGE_DKCARD("更换代扣卡","loan/replacing"),
    APPLICATION_SEARCH_WHITE_PAGE("白名单查询","baseData/findWhite"),
    APPLICATION_ADDRESSBOOK_CREATE("通讯录上传","baseData/addressBook/create"),
    APPLICATION_SEARCH_BLACK_PAGE("黑名单查询","baseData/findBlack"),
    APPLICATION_FINDAPPOINTMENT("获取预约贷款路径","baseData/findAppointment"),
    APPLICATION_ROUTE("请求路由接口","baseData/route"),
    MOBILE_CERATE("手机通话创建","mobile/cerate"),
    MOBILE_SEARCH("手机通话查询","mobile/search"),
    MOBILE_SUBMIT("手机验证","mobile/submit/info"),
    FACE_IDENTIFY("身份检测","baseData/face"),
    CUSTOMER_USERLEVEL("用户等级查询","sas/customer/userLevel"),
    USER_LEVEL("客户级别","sas/customer/classify"),
    CASH_LIMIT("额度激活","loan/cashLimit"),
    LOAN_LIST("额度激活","loan/list"),
    LOAN_REPAYMENTSPLAN("还款计划","loan/repaymentsplan"),
    LIMIT_STATUS("提高额度","loan/limitStatus"),
    CARD_PAYMENT_LIST("还款银行卡列表查询","card/list"),
    RKD_CARD_PAYMENT_LIST("瑞卡贷收款银行卡列表查询","card/payment/list"),
    LOAN_REPAYMENTS("还款记录","loan/repayments"),
    LOAN_BCREDITSTATUS("人行征信状态","loan/bCreditStatus"),
    FIND_TIME("查询版本更新时间","baseData/findtime"),
    FIND_KIND("查询版本号","baseData/findkinds"),
    FIND_DATA("第三个接口","baseData/finddata"),
    FIND_BASEDATA("查询资料管理是老板还是员工","baseData/findBaseData"),
    PRICE_INFO("查询用户贷款状态","loan/priceInfo"),
    CREDIT_EMAILBILL("电子账单登陆接口","credit/emailBill"),
    CREDIT_EMAILBILLSTATUS("电子账单状态接口","credit/emailBillStatus"),
    TAOBAO_CREATTASK("淘宝登陆接口","taobao/createTask"),
    TAOBAO_SEARCHTASK("淘宝状态接口","taobao/searchTask"),
    TAOBAO_UPDATATASK("淘宝更新接口","taobao/updateTask"),
    CREDIT_JDSUBMIT("京东登陆接口","credit/jdSubmit"),
    CREDIT_JDSTATUS("京东状态接口","credit/jdStatus"),
    CREDIT_JDTELCODE("京东手机验证码接口","credit/jdTelCode"),
    API_SOCIALSECURITY("社保接口","api/socialSecurity/"),
    API_SOCIALSECURITY_FUND("公积金接口","api/socialSecurity/fund"),
    LOAN_BANKCREDIT_VERIFICATION("央行征信认证","loan/bankCredit");
    private String content;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    private ReqAction(String name, String content) {
        this.name = name;
        this.content = content;
    }

}
