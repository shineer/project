package com.ryx.ryxpay.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ryx.payment.ryxhttp.OkHttpUtils;
import com.ryx.payment.ryxhttp.callback.Callback;
import com.ryx.payment.ryxhttp.callback.FileCallBack;
import com.ryx.ryxpay.RyxAppconfig;

/**
 * Created by laomao on 16/4/19.
 * 网络请求封装类
 */
public class HttpUtil {

    private static HttpUtil instance = null;

    /**
     * 获取http请求实例
     *
     * @return
     */
    public static HttpUtil getInstance() {
        if (null == instance) {
            instance = new HttpUtil();
        }
        return instance;
    }

    private HttpUtil(){}
    /**
     * 判断网络是否链接
     *
     * @param context
     * @return
     */
    public static boolean checkNet(Context context) {// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

                }
            }
        } catch (Exception e) {

        }
//		Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		context.startActivity(intent);
        return false;
    }

    /**
     * 发送https网络请求
     * @param tag
     * @param queryString
     * @param callback
     */
    public void httpsPost(String tag,String queryString,Callback callback)
    {
        String host = RyxAppconfig.gethost();
        OkHttpUtils.postString().url(host).content(queryString).tag(tag).build().execute(callback);

    }

    /**
     * 文件下载网络请求
     * @param tag
     * @param queryString
     * @param callback
     */
    public void httpsFilePost(String hostUrl,String tag,String queryString,FileCallBack callback){
//        OkHttpUtils.postString().url(hostUrl).content(queryString).tag(tag).build().execute(callback);
        OkHttpUtils .get().url(hostUrl).build().execute(callback);
    }

    /**
     * 取消网络请求
     * @param tag
     */
    public void canelHttpsPost(String tag)
    {
        OkHttpUtils.getInstance().cancelTag(tag);
    }





}
