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
package me.luzhuo.lib_tencent.wechat.share_message;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

public class WebMessage extends ShareMessage {
    private WXMediaMessage msg;

    public WebMessage(Context context, String webUrl, String webTitle, String webDesc, int resid){
        this(webUrl, webTitle, webDesc, BitmapFactory.decodeResource(context.getResources(), resid));
    }

    public WebMessage(String webUrl, String webTitle, String webDesc, Bitmap videoThumb){
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = webUrl;

        msg = new WXMediaMessage(webpage);
        msg.title = webTitle;
        msg.description = webDesc;

        msg.setThumbImage(thumbnail.getImageThumbnail(videoThumb, THUMB_SIZE, THUMB_SIZE));
    }

    @Override
    public WXMediaMessage getWXMediaMessage() {
        return msg;
    }
}
