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
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;

public class VideoMessage extends ShareMessage {
    private WXMediaMessage msg;

    public VideoMessage(Context context, String videoUrl, String videoTitle, String videoDesc, int resId){
        this(videoUrl, videoTitle, videoDesc, BitmapFactory.decodeResource(context.getResources(), resId));
    }

    /**
     * 视频分享
     * @param videoUrl 视频地址 或 视频web页
     * @param videoTitle 视频标题
     * @param videoDesc 视频描述
     * @param videoThumb 视频封面
     */
    public VideoMessage(String videoUrl, String videoTitle, String videoDesc, Bitmap videoThumb){
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = videoUrl;

        msg = new WXMediaMessage(video);

        msg.title = videoTitle;
        msg.description = videoDesc;

        msg.setThumbImage(thumbnail.getImageThumbnail(videoThumb, THUMB_SIZE, THUMB_SIZE));
    }

    @Override
    public WXMediaMessage getWXMediaMessage() {
        return msg;
    }

}
