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
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;

/**
 * 1. 将网络图片作为封面, 未实现
 */
public class MusicMessage extends ShareMessage {
    private WXMediaMessage msg;

    public MusicMessage(Context context, String musicUrl, String musicWeb, String musicTitle, String musicDesc, int resId){
        this(musicUrl, musicWeb, musicTitle, musicDesc, BitmapFactory.decodeResource(context.getResources(), resId));
    }

    /**
     * 分享音乐
     * @param musicUrl 音乐地址
     * @param musicWeb 音乐打开的详情地址
     * @param musicTitle 音乐标题
     * @param musicDesc 音乐简介
     * @param musicThumb 音乐封面
     */
    public MusicMessage(String musicUrl, String musicWeb, String musicTitle, String musicDesc, Bitmap musicThumb){
        WXMusicObject music = new WXMusicObject();
        music.musicDataUrl = musicUrl;
        music.musicUrl = musicWeb;

        msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = musicTitle;
        msg.description = musicDesc;

        msg.setThumbImage(thumbnail.getImageThumbnail(musicThumb, THUMB_SIZE, THUMB_SIZE));
    }

    @Override
    public WXMediaMessage getWXMediaMessage() {
        return msg;
    }
}
