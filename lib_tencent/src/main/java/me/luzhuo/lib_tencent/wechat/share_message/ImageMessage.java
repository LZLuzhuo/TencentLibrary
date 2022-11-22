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

import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

import java.io.File;

import me.luzhuo.lib_core.ui.toast.ToastManager;

public class ImageMessage extends ShareMessage {
    private WXMediaMessage msg;

    /**
     * 分享本地图片
     * @param imageFilePath 本地图片路径
     */
    public ImageMessage(Context context, String imageFilePath){
        File file = new File(imageFilePath);
        if (!file.exists()) {
            ToastManager.show(context, "分享的文件不存在!");
            return;
        }

        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(imageFilePath);

        msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        msg.setThumbImage(thumbnail.getImageThumbnail(imageFilePath, THUMB_SIZE, THUMB_SIZE));
    }

    public ImageMessage(Context context, int resId){
        this(BitmapFactory.decodeResource(context.getResources(), resId));
    }

    public ImageMessage(Bitmap bitmap){
        msg = new WXMediaMessage();
        msg.mediaObject = new WXImageObject(bitmap);

        msg.setThumbImage(thumbnail.getImageThumbnail(bitmap, THUMB_SIZE, THUMB_SIZE));
    }

    @Override
    public WXMediaMessage getWXMediaMessage() {
        return msg;
    }
}
