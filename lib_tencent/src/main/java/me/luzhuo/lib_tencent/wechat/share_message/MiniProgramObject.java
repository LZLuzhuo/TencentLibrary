/* Copyright 2021 Luzhuo. All rights reserved.
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
package me.luzhuo.lib_tencent.wechat.share_message;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;

import static com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;

public class MiniProgramObject extends ShareMessage {
    private WXMediaMessage msg;

    public MiniProgramObject(Context context, String miniId, String webTitle, String webDesc, int resid){
        this("http://", MINIPTOGRAM_TYPE_RELEASE, miniId, "", webTitle, webDesc, BitmapFactory.decodeResource(context.getResources(), resid));
    }

    public MiniProgramObject(String miniId, String webTitle, String webDesc, Bitmap thumb){
        this("http://", MINIPTOGRAM_TYPE_RELEASE, miniId, "", webTitle, webDesc, thumb);
    }

    public MiniProgramObject(Context context, String webUrl, int type, String miniId, String path, String webTitle, String webDesc, int resid){
        this(webUrl, type, miniId, path, webTitle, webDesc, BitmapFactory.decodeResource(context.getResources(), resid));
    }

    /**
     * 分享到微信小程序
     * App 与 小程序 属于同一个微信开放平台账号
     * 仅支持分享到会话, 不支持分享到朋友圈
     * @param webUrl 兼容低版本的网络链接, 若客户端低于6.5.6或iPad客户端接收, 小程序类型分享将自动转成网页类型分享
     * @param type 正式版:0(MINIPTOGRAM_TYPE_RELEASE) / 测试版:1(MINIPROGRAM_TYPE_TEST) / 体验版:2(MINIPROGRAM_TYPE_PREVIEW)
     * @param miniId 小程序原始Id, 如( gh_d5d3de25a329 )
     * @param path 小程序页面路径, 不填则打开首页, 如( /pages/media )( /pages/media?foo=bar )
     * @param webTitle 标题
     * @param webDesc 注释
     * @param thumb 封面
     */
    public MiniProgramObject(String webUrl, int type, String miniId, String path, String webTitle, String webDesc, Bitmap thumb){
        WXMiniProgramObject mini = new WXMiniProgramObject();
        mini.webpageUrl = webUrl;
        mini.miniprogramType = type;
        mini.userName = miniId;
        mini.path = path;

        msg = new WXMediaMessage(mini);
        msg.title = webTitle;
        msg.description = webDesc;
        msg.setThumbImage(thumbnail.getImageThumbnail(thumb, THUMB_SIZE, THUMB_SIZE));
    }

    @Override
    public WXMediaMessage getWXMediaMessage() {
        return msg;
    }
}
