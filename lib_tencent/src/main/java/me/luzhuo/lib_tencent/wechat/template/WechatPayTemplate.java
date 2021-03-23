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
package me.luzhuo.lib_tencent.wechat.template;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import me.luzhuo.lib_tencent.wechat.WechatManager;

/**
 * Description: Callback interface for WeChat pay
 * WXPayEntryActivity
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/5/25 18:41
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class WechatPayTemplate extends Activity implements IWXAPIEventHandler {
    private WechatManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = WechatManager.getInstance(this);
        manager.getApi().handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        manager.getApi().handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) { }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.errCode == 0) {
            manager.callPaySuccess("支付成功");
        } else {
            manager.callPayError("支付失败");
        }
        finish();
    }
}
