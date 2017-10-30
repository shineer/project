package com.ryx.ryxcredit.ryd.utils;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.Contacts;
import com.github.tamir7.contacts.PhoneNumber;
import com.livedetect.base.CIdentifyRequest;
import com.livedetect.base.CIdentifyResponse;
import com.livedetect.utils.FileUtils;
import com.ryx.quickadapter.inter.NoDoubleClickListener;
import com.ryx.ryxcredit.R;
import com.ryx.ryxcredit.beans.ReqAction;
import com.ryx.ryxcredit.beans.bussiness.activateline.CActivateLimitRequest;
import com.ryx.ryxcredit.beans.bussiness.activateline.CActivateLimitResponse;
import com.ryx.ryxcredit.beans.bussiness.addressbook.ContactsRequest;
import com.ryx.ryxcredit.beans.bussiness.addressbook.ContactsResponse;
import com.ryx.ryxcredit.ryd.activity.RYDBaseInfoSuccesActivity;
import com.ryx.ryxcredit.services.ICallback;
import com.ryx.ryxcredit.utils.Base64Utils;
import com.ryx.ryxcredit.utils.CCommonDialog;
import com.ryx.ryxcredit.utils.CLogUtil;
import com.ryx.ryxcredit.utils.PermissionResult;
import java.util.ArrayList;
import java.util.List;

public class RYDFaceCollectSucUtil {
    private final String TAG = RYDFaceCollectSucUtil.class.getSimpleName();
    private ImageView mReturnBtn, success_img;
    private ImageView returnImg;
    private ImageView mAgainImg;
    private String productId;//产品编码
    private String user_level;

    //    private Button btn_server;
    private Button btn_idenfy;
    private String dirPicSave = FileUtils.getSdcardPath() + "/DCIM/";
    private Bitmap bitmap;
    private byte[] pic_result;
    public static final int IDENTIFY_CODE = 0X1004;
    public static final int BORROW_RESULT_CODE = 0X1008;
    private Context mcontext;
    private RYDBaseInfoSuccesActivity baseInfoSuccesActivity;


    private static RYDFaceCollectSucUtil instance;

    public static RYDFaceCollectSucUtil getInstance() {
        if (instance == null) {
            instance = new RYDFaceCollectSucUtil();
        }
        return instance;
    }

    public void initSucPage(Context context, Bundle result) {
        FileUtils.init(context);
        mcontext = context;
        baseInfoSuccesActivity = (RYDBaseInfoSuccesActivity) context;
        dirPicSave = FileUtils.getSdcardPath() + "/DCIM/";
        if(result.containsKey("productId"))
            productId = result.getString("productId");
        if(result.containsKey("user_level"))
            user_level = result.getString("user_level");
        initView();
        boolean check_pass = result.getBoolean("check_pass");
        if (check_pass) {
            pic_result = result.getByteArray("pic_result");
            if (pic_result != null) {
                bitmap = FileUtils.getBitmapByBytesAndScale(pic_result, 1);
                if (null != bitmap) {
                    success_img.setImageBitmap(bitmap);
                }
            } else {
                CLogUtil.showToast(mcontext, "识别失败！");
            }
        }

    }


    private void initView() {
        success_img = (ImageView) (baseInfoSuccesActivity).findViewById(R.id.success_img);
        btn_idenfy = (Button) (baseInfoSuccesActivity).findViewById(R.id.btn_idenfy);
        btn_idenfy.setText("激活");
        btn_idenfy.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                CIdentifyRequest request = new CIdentifyRequest();
                if (pic_result == null) {
                    return;
                }
                request.setImage_data(Base64Utils.encode(pic_result));
                (baseInfoSuccesActivity).httpsPost(mcontext, request, ReqAction.FACE_IDENTIFY, CIdentifyResponse.class, new ICallback<CIdentifyResponse>() {
                    @Override
                    public void success(CIdentifyResponse cResponse) {
                        CIdentifyResponse.ResultBean result = cResponse.getResult();
                        if (result != null) {
                            String score = result.getScore();
                            String face_id = result.getFace_id();
                            doActive(face_id, score);
                        }
                    }

                    @Override
                    public void failture(String tips) {
                        CCommonDialog.showRepaymentError(mcontext, "提交失败", tips + "");
                    }
                });
            }
        });
    }


    /**
     * 手机访问联系人权限
     */
    public void checkContactPermission() {
        final String waring = baseInfoSuccesActivity.getResources().getString(R.string.contacts_waring_msg);
        baseInfoSuccesActivity.requesDevicePermission(waring, 0x0011, new PermissionResult() {
                    @Override
                    public void requestSuccess() {
                        CLogUtil.showLog("checkContactPermission---", "requestSuccess");
                        uploadContacts();
                    }

                    @Override
                    public void requestFailed() {
                        CLogUtil.showLog("checkContactPermission---", "requestFailed");
                        (baseInfoSuccesActivity).setResult(BORROW_RESULT_CODE);
                        (baseInfoSuccesActivity).finish();
                    }
                },
                Manifest.permission.READ_CONTACTS);
    }

    //上传通讯录
    private void uploadContacts() {
        List<Contact> contacts = Contacts.getQuery().find();
        ContactsRequest contactsRequest = new ContactsRequest();
        List<ContactsRequest.ContactsBean> list = new ArrayList<ContactsRequest.ContactsBean>();
        for (Contact c : contacts) {
            ContactsRequest.ContactsBean bean = new ContactsRequest.ContactsBean();
            bean.setName(c.getBestDisplayName());
            List<String> listString = new ArrayList<String>();
            for (PhoneNumber p : c.getPhoneNumbers()) {
                listString.add(p.getNormalizedNumber() + ",");
            }
            bean.setPhone_nums(listString);
            list.add(bean);
        }
        contactsRequest.setContacts(list);
        if (list == null || list.size() == 0) {
            (baseInfoSuccesActivity).setResult(BORROW_RESULT_CODE);
            (baseInfoSuccesActivity).finish();
            return;
        }
        baseInfoSuccesActivity.httpsPost(baseInfoSuccesActivity, contactsRequest, ReqAction.APPLICATION_ADDRESSBOOK_CREATE,
                ContactsResponse.class, new ICallback<ContactsResponse>() {
                    @Override
                    public void success(ContactsResponse contactsResponse) {
                        CLogUtil.showLog("checkContactPermission---", "uploadContacts--success");
                        (baseInfoSuccesActivity).setResult(BORROW_RESULT_CODE);
                        (baseInfoSuccesActivity).finish();
                    }

                    @Override
                    public void failture(String tips) {
                        CLogUtil.showLog("checkContactPermission---", "uploadContacts--failture");
                        (baseInfoSuccesActivity).setResult(BORROW_RESULT_CODE);
                        (baseInfoSuccesActivity).finish();
                    }
                });
    }

    private void doActive(String face_id, String score) {
        CActivateLimitRequest request = new CActivateLimitRequest();
        request.setFace_id(face_id);
        request.setScore(score);
        request.setProduct_id(productId);
        request.setCustomer_type(user_level);
        request.setFlag("A");
        baseInfoSuccesActivity.httpsPost(baseInfoSuccesActivity, request, ReqAction.CASH_LIMIT, CActivateLimitResponse.class, new ICallback<CActivateLimitResponse>() {
            @Override
            public void success(CActivateLimitResponse cActivateLimitResponse) {
                CCommonDialog.showRepaymentOK(mcontext, "提交成功", "您的申请已提交，我们会尽快处理！", new CCommonDialog.IMessage() {
                    @Override
                    public void callback() {
                        (baseInfoSuccesActivity).setResult(IDENTIFY_CODE);
                        (baseInfoSuccesActivity).finish();

                    }
                });
            }

            @Override
            public void failture(String tips) {
                CCommonDialog.showRepaymentError(mcontext, "提交失败", tips + "");
            }
        });
    }

}