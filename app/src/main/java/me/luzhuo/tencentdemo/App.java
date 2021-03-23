package me.luzhuo.tencentdemo;

import android.app.Application;

import me.luzhuo.lib_compiler.annotations.WechatLogin;
import me.luzhuo.lib_tencent.wechat.WechatManager;
import me.luzhuo.lib_tencent.wechat.template.WechatLoginTemplate;

/**
 * Description:
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/5/29 10:03
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
@WechatLogin(applicationId = BuildConfig.APPLICATION_ID, entryTemplete = WechatLoginTemplate.class)
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        WechatManager.init("wxb546013460427555");
    }
}
