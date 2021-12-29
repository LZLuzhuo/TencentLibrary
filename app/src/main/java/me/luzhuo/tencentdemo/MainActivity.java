package me.luzhuo.tencentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import me.luzhuo.lib_compiler.annotations.WechatPay;
import me.luzhuo.lib_core.ui.toast.ToastManager;
import me.luzhuo.lib_okhttp.OKHttpManager;
import me.luzhuo.lib_okhttp.callback.IBitmapCallback;
import me.luzhuo.lib_tencent.wechat.IWechatCallback;
import me.luzhuo.lib_tencent.wechat.WechatManager;
import me.luzhuo.lib_tencent.wechat.enums.WechatShareWhere;
import me.luzhuo.lib_tencent.wechat.share_message.ImageMessage;
import me.luzhuo.lib_tencent.wechat.share_message.MiniProgramObject;
import me.luzhuo.lib_tencent.wechat.share_message.MusicMessage;
import me.luzhuo.lib_tencent.wechat.share_message.TextMessage;
import me.luzhuo.lib_tencent.wechat.share_message.VideoMessage;
import me.luzhuo.lib_tencent.wechat.share_message.WebMessage;
import me.luzhuo.lib_tencent.wechat.template.WechatPayTemplate;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 集合了 微信登录 和 微信支付 的案例
 */
@WechatPay(applicationId = BuildConfig.APPLICATION_ID, entryTemplete = WechatPayTemplate.class)
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void wechatLogin(View view) {
        WechatManager wechat = WechatManager.getInstance(this);
        wechat.setOnLoginCallback(new IWechatCallback() {
            @Override
            public void onSuccess(String code) {
                Log.e(TAG, "" + code);
            }

            @Override
            public void onError(String err) {
                Log.e(TAG, "" + err);
            }
        });
        wechat.login();
    }

    public void wechatPay(View view) {
        WechatManager wechat = WechatManager.getInstance(this);
        wechat.setOnPayCallback(new IWechatCallback() {
            @Override
            public void onSuccess(String code) {
                Log.e(TAG, "支付成功");
            }

            @Override
            public void onError(String err) {
                Log.e(TAG, "支付失败");
            }
        });
        /*
        *"package": "Sign=WXPay",
		"appid": "wxb546013460427555",
		"sign": "10CD04AFF0D9E416B70A9E23CE2224E7B54CFB989B283DDFFBC95F2EC5EDF19F",
		"partnerid": "1557796771",
		"prepayid": "wx291044150658698cb3928bbc1215428700",
		"noncestr": "ifmlAbnqlu9YdpAn",
		"timestamp": "1590720255"
        * */
        wechat.pay("1557796771", "wx291044150658698cb3928bbc1215428700", "ifmlAbnqlu9YdpAn", "1590720255", "10CD04AFF0D9E416B70A9E23CE2224E7B54CFB989B283DDFFBC95F2EC5EDF19F");
    }

    public void wechatShare(View view) throws NoSuchAlgorithmException, KeyManagementException {
        final WechatManager wechat = WechatManager.getInstance(this);

        // 分享文本内容
        // wechat.share(new TextMessage("这是文本分享"), WechatShareWhere.Friend);
        // 分享图片
        // wechat.share(new ImageMessage(this, R.mipmap.image), WechatShareWhere.Friend);
        // wechat.share(new ImageMessage(this, Environment.getExternalStorageDirectory() + File.separator + "shareimage.jpg" /*内部存储卡*/), WechatShareWhere.Friend);
        // 分享音乐
        // wechat.share(new MusicMessage(this, "http://staff2.ustc.edu.cn/~wdw/softdown/index.asp/0042515_05.ANDY.mp3", "http://www.qq.com", "音乐标题啊", "音乐描述啊", R.mipmap.image), WechatShareWhere.Friend);
        // 分享视频
        // wechat.share(new VideoMessage(this, "https://vdept.bdstatic.com/5846443970506b655764664834687537/35506d4e50576843/e610799b39f3c277bd7e6a2126a1211ce016cf0ceb260b5f5557d606d020bca4c53e7fe1de9a15cbc4e94bf5d6f5fda3f4101f828384ede0865741a81d17e5ae.mp4?auth_key=1594552495-0-0-d893d9d8b6118bdc4e94a62d855ebcc6", "这是标题哟", "这是描述哟", R.mipmap.image), WechatShareWhere.Collect);
        // 分享Web页
         wechat.share(new WebMessage(this, "http://www.qq.com", "这是标题哟", "这是描述哟", R.mipmap.image), WechatShareWhere.Friend);

        // 以网路图片作为封面
        /*Request request = new Request.Builder().url("http://b-ssl.duitang.com/uploads/item/201608/29/20160829203548_YHJjV.thumb.700_0.jpeg").build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "图片下载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                wechat.share(new MusicMessage("http://staff2.ustc.edu.cn/~wdw/softdown/index.asp/0042515_05.ANDY.mp3", "http://www.qq.com", "音乐标题啊", "音乐描述啊", bitmap), WechatShareWhere.Friend);
            }
        });*/

        /*OKHttpManager okHttpManager = new OKHttpManager();
        okHttpManager.getBitmap("http://b-ssl.duitang.com/uploads/item/201608/29/20160829203548_YHJjV.thumb.700_0.jpeg", new IBitmapCallback() {
            @Override
            public void onSuccess(int i, Bitmap bitmap) {
                wechat.share(new MusicMessage("http://staff2.ustc.edu.cn/~wdw/softdown/index.asp/0042515_05.ANDY.mp3", "http://www.qq.com", "音乐标题啊", "音乐描述啊", bitmap), WechatShareWhere.Friend);
            }

            @Override
            public void onError(int i, String s) {
                ToastManager.show(MainActivity.this, "下载图片失败");
            }
        });*/
    }

    /**
     * 分享到小程序
     */
    public void wechatShareMiniProgramObject(View view) {
        final String miniId = "gh_6c8b13ed7269";

        final WechatManager wechat = WechatManager.getInstance(this);
        // wechat.share(new MiniProgramObject(this, "http://www.qq.com", WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE, miniId, "", "这是标题哟", "这是描述哟", R.mipmap.image), WechatShareWhere.Friend);
        wechat.share(new MiniProgramObject(this, miniId, "这是标题哟", "这是描述哟", R.mipmap.image), WechatShareWhere.Friend);
    }
}
