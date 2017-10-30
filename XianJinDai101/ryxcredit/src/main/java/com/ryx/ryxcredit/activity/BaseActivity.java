package com.ryx.ryxcredit.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.kayvannj.permission_utils.Func;
import com.github.kayvannj.permission_utils.PermissionUtil;
import com.ryx.quickadapter.inter.NoDoubleClickListener;
import com.ryx.ryxcredit.BuildConfig;
import com.ryx.ryxcredit.R;
import com.ryx.ryxcredit.RyxcreditConfig;
import com.ryx.ryxcredit.beans.ReqAction;
import com.ryx.ryxcredit.services.ICallback;
import com.ryx.ryxcredit.services.UICallBack;
import com.ryx.ryxcredit.utils.CLogUtil;
import com.ryx.ryxcredit.utils.HttpUtil;
import com.ryx.ryxcredit.utils.PermissionResult;
import com.ryx.ryxcredit.utils.RyxCreditGesturesPaswdUtil;
import com.ryx.ryxcredit.widget.RyxCreditLoadDialog;
import com.umeng.analytics.MobclickAgent;

import static com.ryx.ryxcredit.RyxcreditConfig.context;


/**
 * Created by laomao on 16/6/16.
 */
public  class BaseActivity extends AppCompatActivity implements PermissionResult {
   // public abstract int getLayoutId();

    public ImageView rightImg;
    private PermissionUtil.PermissionRequestObject permissionRequestObject;
    private ImageView iv_systemmaintenanceclosebutton;
    private RelativeLayout rl_systemmaintenance;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
       // setContentView(getLayoutId());
        showMaintainDialog();
        context = this;
        CLogUtil.setLogdebug(BuildConfig.DEBUG);
        CLogUtil.showLog("currentAct:===="+this.getClass());
        RyxcreditConfig.resetTime(this);
    }

    public HttpUtil getHttpUtilInstance(Context context) {
        return HttpUtil.getInstance(context);
    }

    /**
     * 小贷请求发起
     *
     * @param context
     * @param t
     * @param reqAction
     * @param clazz
     * @param callback
     * @param <T>
     * @param <E>
     */
    public <T, E> void httpsPost(final Context context, T t, final ReqAction reqAction, final Class<E> clazz,
                                 final ICallback<E> callback) {
        if (HttpUtil.checkNet(context)) {
            RyxCreditLoadDialog.getInstance(context).show();
            HttpUtil.getInstance(context).httpsPost(t, reqAction, clazz, callback, new UICallBack() {
                @Override
                public void complete() {
                    RyxCreditLoadDialog.getInstance(context).dismiss();
                }
            });
        } else {
            CLogUtil.showToast(this, "请检查网络！");
        }
    }

    /**
     * 设置当前页面的标题
     *
     * @param title           标题
     * @param leftRightisShow 左侧右侧是否显示,
     * @author xucc
     */
    public void setTitleLayout(String title, boolean... leftRightisShow) {

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(title);
        ImageView leftImageView = (ImageView) findViewById(R.id.tileleftImg);
        ImageView rightImageView = (ImageView) findViewById(R.id.tilerightImg);
        if (leftRightisShow.length > 0) {
            //第一个代表左侧返回图标
            boolean leftIshow = leftRightisShow[0];
            if (leftIshow) {
                leftImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        backUpImgOnclickListen();
                    }
                });
                leftImageView.setVisibility(View.VISIBLE);
            } else {
                leftImageView.setVisibility(View.INVISIBLE);
            }
            //第二个代表右侧帮助图标
            boolean rightIshow = leftRightisShow[leftRightisShow.length - 1];
            if (rightIshow) {
                rightImageView.setVisibility(View.VISIBLE);
                rightImageView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(BaseActivity.this, ActivateLineActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                rightImageView.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 关闭按钮点击监听事件 '
     */
    protected void backUpImgOnclickListen() {
        BaseActivity.this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshToken();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RyxcreditConfig.resetTime(this);
        MobclickAgent.onPause(this);
    }

    public void setRightBtn() {
        rightImg = (ImageView) findViewById(R.id.tilerightImg);
        rightImg.setVisibility(View.VISIBLE);
    }

    /**
     * 底部菜单栏
     */
    public void setbottomMenu() {

        TextView tv_instruction = (TextView) findViewById(R.id.tv_instruction);
        TextView tv_problem = (TextView) findViewById(R.id.tv_problem);
        tv_instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, InstructionActivity.class);
                startActivity(intent);
            }
        });
        tv_problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, MiddleProblemActivity.class);
                startActivity(intent);
            }
        });
        TextView mServiceMobile = (TextView) findViewById(R.id.tv_service_mobile);
        mServiceMobile.setOnClickListener(new NoDoubleClickListener() {

            @Override
            protected void onNoDoubleClick(View view) {
                //跳转系统拨号
                Uri uri = Uri.parse("tel:" + getResources().getString(R.string.service_phone));
                Intent it = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(it);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if (permissionRequestObject != null && permissions != null && permissions.length != 0 && grantResults != null && grantResults.length != 0) {
            permissionRequestObject.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void requesDevicePermission(final String waring, int requestCode, final PermissionResult permissionResult, String... permissions) {
        if (permissionRequestObject != null) {
            permissionRequestObject = null;
        }
        permissionRequestObject = PermissionUtil.with(this).request(permissions).onAllGranted(
                new Func() {
                    @Override
                    protected void call() {
                        if (permissionResult != null)
                            permissionResult.requestSuccess();
                    }
                }).onAnyDenied(
                new Func() {
                    @Override
                    protected void call() {
                        if (permissionResult != null)
                            permissionResult.requestFailed();
                    }
                }).ask(requestCode);
//        }else{
//            if(permissionResult!=null)
//            permissionResult.requestSuccess();
//        }
    }

    // 显示缺失权限提示
    public void showMissingPermissionDialog(final String warning) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setTitle("温馨提示");
        builder.setMessage(warning);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        builder.setCancelable(false);
        builder.show();
    }


    @Override
    public void requestSuccess() {

    }

    @Override
    public void requestFailed() {

    }
    /**
     * 跳转手势密码界面刷新当前token
     */
    public void refreshToken(){
        if ((System.currentTimeMillis() - RyxcreditConfig.getExitTime(BaseActivity.this))<RyxcreditConfig.lockTime) {
            //5分钟
            return;
        }
        if(TextUtils.isEmpty(RyxcreditConfig.getAppuser())){
            return;
        }
        String user_id=  RyxcreditConfig.getCustomerId();
        if(!TextUtils.isEmpty(user_id)&&!"0000".equals(user_id)){
            //用户登录过，并且打开手势密码开关
            RyxCreditGesturesPaswdUtil spUserid=new RyxCreditGesturesPaswdUtil(this,user_id );
            int  switchFlag = spUserid.loadIntSharedPreference("switch");
            String    paswd=spUserid.loadStringSharedPreference("gesturepwd");
            final int   errorcount = spUserid.loadIntSharedPreference("errorcount");
            if(switchFlag==1&&!TextUtils.isEmpty(paswd)){
                try {
                    if(errorcount>=3){
                        RyxcreditConfig.clearParams();
                        //错误次数超过三次,则直接登录页
                        CLogUtil.showToast(BaseActivity.this, "手势密码绘制错误次数已超限,请账号验证!");
                        RyxcreditConfig.clearParams();
                        Intent intent = new Intent(BaseActivity.this,
                                Class.forName(getApplicationContext().getPackageName()+".usercenter.LoginActivity_"));
                        //清空activity栈
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        //添加token失效的标示
                        intent.putExtra("tokenIntent", true);
                        startActivity(intent);
                    }else{
                        RyxcreditConfig.clearParams();
                        Intent intent = new Intent(BaseActivity.this,
                                Class.forName(getApplicationContext().getPackageName() + ".usercenter.GesturePawdCheckActivity_"));
//                        Intent intent = new Intent(BaseActivity.this, GesturePawdCheckActivity_.class);
                        //清空activity栈
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        //添加token失效的标示
                        intent.putExtra("tokenIntent", true);
                        startActivityForResult(intent,0x777);
                    }
                }catch (Exception e){

                }

            }
        }
    }

     /*
    * 维护的时候的对话框
    * */
    public void showMaintainDialog(){
        final Dialog dialog = new Dialog(this);
        View contentView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null);
        dialog.setContentView(contentView);
        dialog.setCancelable(false);
        dialog.show();
        rl_systemmaintenance= (RelativeLayout) contentView.findViewById(R.id.rl_systemmaintenance);
        rl_systemmaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 实例化Intent
                try {
                    Intent  intent = new Intent(BaseActivity.this, Class.forName(getApplicationContext().getPackageName() + ".activity.MainFragmentActivity_"));
                     startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}