/* Copyright 2020 Luzhuo. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.luzhuo.lib_tencent.wechat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import me.luzhuo.lib_tencent.wechat.IWechatCallback;
import me.luzhuo.lib_tencent.wechat.enums.WechatShareWhere;
import me.luzhuo.lib_tencent.wechat.share_message.ShareMessage;

/**
 * Description:
 *
 * Example:
 * <pre>
 *  @WechatLogin(applicationId = BuildConfig.APPLICATION_ID, entryTemplete = WechatLoginTemplate.class)
 *  @WechatPay(applicationId = BuildConfig.APPLICATION_ID, entryTemplete = WechatPayTemplate.class)
 * public class App extends Application {
 *
 *     @Override
 *     public void onCreate() {
 *         super.onCreate();
 *         WechatManager.init("wxb546013460427555");
 *     }
 * }
 *
 * public void wechatLogin(View view) {
 *     WechatManager wechat = WechatManager.getInstance(this);
 *     wechat.setOnLoginCallback(new IWechatCallback() {
 *         @Override
 *         public void onSuccess(String code) {
 *             Log.e(TAG, "" + code);
 *         }
 *
 *         @Override
 *         public void onError(String err) {
 *             Log.e(TAG, "" + err);
 *         }
 *     });
 *     wechat.login();
 * }
 *
 * public void wechatPay(View view) {
 *     WechatManager wechat = WechatManager.getInstance(this);
 *     wechat.setOnPayCallback(new IWechatCallback() {
 *         @Override
 *         public void onSuccess(String code) {
 *             Log.e(TAG, "支付成功");
 *         }
 *
 *         @Override
 *         public void onError(String err) {
 *             Log.e(TAG, "支付失败");
 *         }
 *     });
 *     wechat.pay("1557796771","wx291044150658698cb3928bbc1215428700","ifmlAbnqlu9YdpAn","1590720255","10CD04AFF0D9E416B70A9E23CE2224E7B54CFB989B283DDFFBC95F2EC5EDF19F");
 * }
 *
 * <activity
 *     android:name="${applicationId}.wxapi.WXEntryActivity"
 *     android:label="@string/app_name"
 *     android:theme="@android:style/Theme.Translucent.NoTitleBar"
 *     android:exported="true"
 *     android:taskAffinity="${applicationId}"
 *     android:launchMode="singleTask">
 * </activity>
 * <activity
 *     android:name="${applicationId}.wxapi.WXPayEntryActivity"
 *     android:label="@string/app_name"
 *     android:theme="@android:style/Theme.Translucent.NoTitleBar"
 *     android:exported="true"
 *     android:taskAffinity="${applicationId}"
 *     android:launchMode="singleTask">
 * </activity>
 * </pre>
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/5/23 21:17
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class WechatManager {
    private Context context;
    private IWXAPI api;
    private IWechatCallback loginCallback;
    private IWechatCallback payCallback;

    /**
     * Please first initialize this parameter.
     */
    private static String APP_ID;
    public static void init(String appId){
        APP_ID = appId;
    }

    private static WechatManager instance;
    public static WechatManager getInstance(Context context) {
        if(APP_ID == null || APP_ID.isEmpty())
            throw new IllegalArgumentException("Please first initialize the APP_ID.");

        if (instance == null){
            synchronized (WechatManager.class){
                if (instance == null){
                    instance = new WechatManager(context, APP_ID);
                }
            }
        }
        return instance;
    }

    private WechatManager(Context context, String APP_ID){
        this.context = context;

        api = WXAPIFactory.createWXAPI(context.getApplicationContext(), APP_ID, true);
        api.registerApp(APP_ID);
    }

    public IWXAPI getApi(){
        return api;
    }

    public void login(){
        if(!checkAppInstalled()) return;

        // send oauth request
        final SendAuth.Req req = new SendAuth.Req();
        /*
        Application authorization scope (default is sufficient)
        snsapi_userinfo: Get user personal information
        */
        req.scope = "snsapi_userinfo";
        /*
        Application verification (may not be added, or filled in randomly, it will be returned as it is after successful call)
        */
        req.state = "wechat_sdk_login";
        api.sendReq(req);
    }

    public void pay(String partnerid, String prepayid, String noncestr, String timestamp, String sign){
        if(!checkAppInstalled()) return;

        if(api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT){ }
        else{
            if(payCallback != null){
                payCallback.onError("当前版本的微信不支持微信支付");
            }else{
                Toast.makeText(context, "当前版本的微信不支持微信支付", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        PayReq req = new PayReq();
        req.appId = APP_ID;
        req.partnerId = partnerid;
        req.prepayId = prepayid;
        req.nonceStr = noncestr;
        req.timeStamp = timestamp;
        req.packageValue = "Sign=WXPay";
        req.sign = sign;
        req.extData = "app data";
        api.sendReq(req);
    }

    /**
     * Sharing is only successful, there is no failure, so there is no callback.
     */
    public void share(ShareMessage message, WechatShareWhere shareWhere) {
        if(!checkAppInstalled()) return;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        // Custom tag
        req.transaction = "weixin_share";
        req.message = message.getWXMediaMessage();
        // Where to share?
        req.scene = shareWhere.value();
        api.sendReq(req);
    }

    public void setOnLoginCallback(IWechatCallback callback) {
        this.loginCallback = callback;
    }

    public void setOnPayCallback(IWechatCallback callback) {
        this.payCallback = callback;
    }

    public void callLoginSuccess(String code){
        if(loginCallback != null) loginCallback.onSuccess(code);
    }
    public void callLoginError(String error){
        if(loginCallback != null) loginCallback.onError(error);
    }
    public void callPaySuccess(String message){
        if(payCallback != null) payCallback.onSuccess(message);
    }
    public void callPayError(String message){
        if(payCallback != null) payCallback.onError(message);
    }

    /**
     * Check if the WeChat client is installed.
     * @return Returns true if installed, false otherwise.
     */
    private boolean checkAppInstalled() {
        if (!api.isWXAppInstalled()) {
            if(loginCallback != null){
                loginCallback.onError("请先安装微信客户端");
            }else{
                Toast.makeText(context, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }
}
