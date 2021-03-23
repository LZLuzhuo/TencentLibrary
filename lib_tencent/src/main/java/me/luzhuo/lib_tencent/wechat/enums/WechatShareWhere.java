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
package me.luzhuo.lib_tencent.wechat.enums;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

public enum WechatShareWhere {
    Friend(SendMessageToWX.Req.WXSceneSession, "微信好友"), CircleOfFriends(SendMessageToWX.Req.WXSceneTimeline, "朋友圈"), Collect(SendMessageToWX.Req.WXSceneFavorite, "微信收藏");

    private int scene;
    private String description;
    private WechatShareWhere(int scene,  String description){
        this.scene = scene;
        this.description = description;
    }

    public int value(){
        return this.scene;
    }
}
