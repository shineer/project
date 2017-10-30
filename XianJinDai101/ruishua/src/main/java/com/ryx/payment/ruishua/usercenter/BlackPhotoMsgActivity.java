package com.ryx.payment.ruishua.usercenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.rey.material.widget.CheckBox;
import com.ryx.payment.ruishua.R;
import com.ryx.payment.ruishua.RyxAppconfig;
import com.ryx.payment.ruishua.RyxAppdata;
import com.ryx.payment.ruishua.activity.BaseActivity;
import com.ryx.payment.ruishua.activity.HtmlMessageActivity_;
import com.ryx.payment.ruishua.bean.Param;
import com.ryx.payment.ruishua.bean.RyxPayResult;
import com.ryx.payment.ruishua.inteface.PermissionResult;
import com.ryx.payment.ruishua.net.XmlCallback;
import com.ryx.payment.ruishua.utils.BitmapUntils;
import com.ryx.payment.ruishua.utils.LogUtil;
import com.ryx.payment.ruishua.utils.PhoneinfoUtils;
import com.ryx.payment.ruishua.utils.PreferenceUtil;
import com.ryx.payment.ruishua.utils.UriUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@EActivity(R.layout.activity_black_photo_msg)
public class BlackPhotoMsgActivity extends BaseActivity {
    private String pname,pid,phoneno,smscode,md5Value,localtime,localDate,locallogo,primarykey;
    final int TAKE_PHOTO = 1,TAKE_SIGN=12;
    private String imgTempName = "";
    private Bitmap myBitmap;
    private String signPicAscii;
    @ViewById(R.id.agreeCb)
    CheckBox agreeCb;
    @ViewById(R.id.iv_sign)
    ImageView iv_sign;
    @ViewById(R.id.agreement_tv)
    TextView agreement_tv;
    @ViewById(R.id.iv_touxiang)
    ImageView iv_touxiang;
    @Click(R.id.tohandsignll)
    public void tohandsignll(){
        Intent intent=new Intent(BlackPhotoMsgActivity.this,BlackCheckSignActivity_.class);
        startActivityForResult(intent, TAKE_SIGN);

    }
    @Click(R.id.iv_touxiang)
    public void iv_touxiangClick(){
        takePic();
    }

    @AfterViews
    public void initView(){
        setTitleLayout("补充资料",true,false);
        initdata();
    }
    @Click(R.id.agreement_tv)
    public void agreementtvClick(){
        Intent intent=new Intent(BlackPhotoMsgActivity.this, HtmlMessageActivity_.class);
        Bundle bundle=new Bundle();
        bundle.putString("title","支付协议");
        bundle.putString("urlkey",RyxAppconfig.Notes_BlackMSG);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    /**
     * 拍照
     */
    public void takePic() {
        String   waring = MessageFormat.format(getResources().getString(R.string.camerawritefilewaringmsg), RyxAppdata.getInstance(this).getCurrentBranchName());
//        if (RyxAppconfig.BRANCH.equals("01")) {
//            waring = MessageFormat.format(getResources().getString(R.string.camerawritefilewaringmsg), getResources().getString(R.string.app_name));
//        }else if (RyxAppconfig.BRANCH.equals("02")) {
//            waring = MessageFormat.format(getResources().getString(R.string.camerawritefilewaringmsg), getResources().getString(R.string.app_name_ryx));
//        }
        requesDevicePermission(waring, 0x00011, new PermissionResult() {

                    @Override
                    public void requestSuccess() {
                        String status = Environment.getExternalStorageState();
                        if (status.equals(Environment.MEDIA_MOUNTED)) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddhhmmss");
                            imgTempName = "/temp_" + format2.format(new Date()) + ".jpg";
                            PreferenceUtil.getInstance(getApplicationContext()).saveString(
                                    "imgTempName", imgTempName);
                            File f = new File(Environment.getExternalStorageDirectory(),
                                    imgTempName);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, UriUtils.fromFile(f,BlackPhotoMsgActivity.this));
                            intent.putExtra("scale", true);
                            startActivityForResult(intent, TAKE_PHOTO);
                        } else {
                            LogUtil.showToast(BlackPhotoMsgActivity.this, "请检查SD卡是否正常!");
                        }
                    }

                    @Override
                    public void requestFailed() {

                    }
                }, Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        LogUtil.showLog("requestCode=="+requestCode+",resultCode="+resultCode);
        // 检查数据来源；
        switch (requestCode) {
            case TAKE_PHOTO: // 相机返回
            {
                if (resultCode == RESULT_OK) {
                    LogUtil.showLog("相机拍照完毕===========================");
                    showPicFromCamera(null);
                }
                break;
            }
            case TAKE_SIGN:
                if (resultCode == RESULT_OK) {
                    Bundle bundle=data.getExtras();
//			     picSign=bundle.getString("picSign");
                    signPicAscii=bundle.getString("signPicAscii");
                    LogUtil.showLog("接收signPicAscii==="+signPicAscii);
                    byte[] signPicAsciiByte=  BitmapUntils.hexStringToBytes(signPicAscii);
                    if(signPicAsciiByte!=null){
                        Bitmap bitmap=   BitmapFactory.decodeByteArray(signPicAsciiByte, 0, signPicAsciiByte.length);
                        iv_sign.setImageBitmap(bitmap);
                    }
                }
                break;
//		case PICK_PHOTO: // 相册返回
//		{
//			if (data != null) {
//				startPhotoZoom(data.getData());
//			}
//			break;
//		}
//		case CROP_PHOTO: // 剪裁以后再显示
//			break;
//		}
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void showPicFromCamera(String filePath) {

        if(TextUtils.isEmpty(filePath))
            imgTempName = Environment.getExternalStorageDirectory() +
                    PreferenceUtil.getInstance(getApplicationContext()).getString("imgTempName", "");
        else
            imgTempName = filePath;
//=============================图片BitmapFactory加载=========================================
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = false;
//
//        File dF = new File(imgTempName);
//        long len = dF.length();
//        LogUtil.showLog("len = " + Long.toString(len));
//        if (len > 100 * 1024) {
//            int sample = (int) (len / (100 * 1024));
//            options.inSampleSize = sample>10?10:sample;
//            LogUtil.showLog("sample = " + Integer.toString(sample));
//        } else {
//            options.inSampleSize = 1;
//        }
////			iv_check3.setVisibility(View.VISIBLE);
//        if (myBitmap != null && !myBitmap.isRecycled()){
//            myBitmap.recycle();
//        }
//        myBitmap = null;
////			System.gc();
//
//        if(options.inSampleSize>1)
//            options.inSampleSize =options.inSampleSize /2;
//       try {
//           myBitmap = BitmapFactory.decodeFile(imgTempName, options);
//       }catch (Exception e){
//           e.printStackTrace();
//       }
//=============================图片BitmapFactory加载=========================================
        Glide.with(BlackPhotoMsgActivity.this)
                .load(imgTempName)
                .asBitmap()
                .fitCenter()
                .into(new SimpleTarget(PhoneinfoUtils.getWindowsWidth(BlackPhotoMsgActivity.this), PhoneinfoUtils.getWindowsHight(BlackPhotoMsgActivity.this)) {

                    @Override
                    public void onResourceReady(Object resource, GlideAnimation glideAnimation) {
                        myBitmap=(Bitmap) resource;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(myBitmap!=null){
                                    iv_touxiang.setImageBitmap(myBitmap);
                                }else{
                                    LogUtil.showToast(BlackPhotoMsgActivity.this, "拍照失败请重新拍照!");
                                }
                            }
                        });

                    }
                });

//			String[] name = imgTempName.split("/");
//		String 	imgProfile = name[name.length -1];
//			imgProfile = "IMOB_"+imgProfile.substring(1, imgProfile.length());
//			imgProfile = QtpayAppConfig.imageCachePath + imgProfile;

//			PreferenceUtil.getInstance(getApplicationContext()).saveString(QtpayAppConfig.IMAG_PROFILE, imgProfile);
//			AsyncImageLoader.savePic(myBitmap, imgProfile);
    }
    /**
     * 参数检查
     * @return
     */
    public boolean checkInput(){
        if(!agreeCb.isChecked()){
            LogUtil.showToast(BlackPhotoMsgActivity.this, "请先同意支付协议!");
            return false;
        }
        return true;
    }
    /**
     * 初始化数据
     */
    private void initdata() {
        super.initQtPatParams();
        qtpayApplication = new Param("application");
        Bundle bundle=	getIntent().getExtras();
        pname=bundle.getString("pname");
        pid=bundle.getString("pid");
        phoneno=bundle.getString("phoneno");
        smscode=bundle.getString("smscode");
        md5Value=bundle.getString("md5Value");

        localtime=bundle.getString("localtime");
        localDate=bundle.getString("localDate");
        locallogo=bundle.getString("locallogo");
        primarykey=bundle.getString("msgid","");
    }
    @Click(R.id.bt_next)
    public void btnNextClick(){
        if(checkInput()){
            if(myBitmap==null){
                LogUtil.showToast(BlackPhotoMsgActivity.this, "请上传手持银行卡照片!");
                return;
            }
            if(TextUtils.isEmpty(signPicAscii)){
                LogUtil.showToast(BlackPhotoMsgActivity.this, "请先签名后再上传!");
                return;
            }
            showLoading();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    byte[] bitmapBytes = BitmapUntils.getContentbyCameraPix(myBitmap);
                    Message msg = new Message();
                    msg.what=0x001;
                    Bundle bundle=new Bundle();
//	                    bundle.putString("handpic", CryptoUtils.getInstance().EncodeDigest(bitmapBytes));
                    bundle.putString("handpic",  BitmapUntils
                            .changeBytesToHexString(bitmapBytes));
                    msg.setData(bundle);
                    mhandler.sendMessage(msg);
                }
            }).start();
        }
    }
    private Handler mhandler = new Handler(){

        @SuppressLint("NewApi")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 0x001:

                    Bundle dataBundle=	msg.getData();
                    String handpicData= dataBundle.getString("handpic","");
                    cancleLoading();
//                    LogUtil.printInfo("submitData==="+"pid="+pid+",pname="+pname+",phoneno="+phoneno+",smscode="+
//                            smscode+",handpicData="+handpicData+",signPicAscii="+signPicAscii+",md5Value="+md5Value);
                    //====================以下仅供测试===================================
//				//资料上传成功
//				LOG.showToast(BlackPhotoMsgActivity.this, "资料上传成功,请耐心等待审核.");
//				setResult(QtpayAppConfig.CLOSE_ALL);
//				finish();
                    //====================以上仅供测试===================================

//                    LogUtil.printInfo("handpicData==="+handpicData);
//                    LogUtil.printInfo("signpic==="+signPicAscii);

                    qtpayApplication.setValue("UpdateRhdfInfo.Req");
                    qtpayAttributeList.add(qtpayApplication);
                    qtpayParameterList.add(new Param("pid", pid));
                    qtpayParameterList.add(new Param("pname", pname));
                    qtpayParameterList.add(new Param("pphoneno", phoneno));
                    qtpayParameterList.add(new Param("smsCode", smscode));
                    qtpayParameterList.add(new Param("md5Value", md5Value));
                    qtpayParameterList.add(new Param("lcoaldate", localDate));
                    qtpayParameterList.add(new Param("localtime", localtime));
                    qtpayParameterList.add(new Param("locallogno", locallogo));
                    qtpayParameterList.add(new Param("rhdfReviewId", primarykey));
                    qtpayParameterList.add(new Param("handpic", handpicData));
                    qtpayParameterList.add(new Param("signpic", signPicAscii));

                    httpsPost("UpdateRhdfInfoTag", new XmlCallback() {
                        @Override
                        public void onTradeSuccess(RyxPayResult payResult) {
                            String result=payResult.getData();
                            try {
                                JSONObject jsonObj = new JSONObject(result);
                                if ("0000".equals(jsonObj.getString("code"))) {
                                    //资料上传成功
                                    LogUtil.showToast(BlackPhotoMsgActivity.this, "资料上传成功,请耐心等待审核.");
                                    setResult(RyxAppconfig.CLOSE_ALL);
                                }else{
                                    LogUtil.showToast(BlackPhotoMsgActivity.this, jsonObj.getString("msg"));
                                    if("5001".equals(jsonObj.getString("code"))||"5002".equals(jsonObj.getString("code"))){
                                        setResult(RyxAppconfig.CLOSE_ALL);
                                    }
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            finish();
                        }
                    });


                    break;
            }
        }

    };

}
