package com.nian.wan.app.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.nian.wan.app.bean.ShareInfo;
import com.mc.developmentkit.utils.ToastUtil;

import java.util.HashMap;

import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by LeBron on 2016/7/13.
 * 分享给社交平台
 */
public class Shares {

    private static final int MSG_TOAST = 1;
    private static final int MSG_ACTION_CCALLBACK = 2;
    private static final int MSG_CANCEL_NOTIFY = 3;

    public static int TYPE = 0;

    /**
     * 空间分享
     */
    public static void QZone(Context context, ShareInfo shareBean) {
        ShareSDK.initSDK(context);
        QZone.ShareParams sp = new QZone.ShareParams();
        sp.setTitleUrl(shareBean.shareUrl);
        sp.setTitle(shareBean.title);
        sp.setText(shareBean.text);
        sp.setImageUrl(shareBean.logoUrl);
        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
        qzone.setPlatformActionListener(platformActionListener); // 璁剧疆鍒嗕韩浜嬩欢鍥炶皟
        qzone.share(sp);
    }

    /**
     * qq好友分享
     */
    public static void QQ(Context context, ShareInfo shareBean) {
        ShareSDK.initSDK(context);
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setTitle(shareBean.title);
        sp.setText(shareBean.text);
        sp.setTitleUrl(shareBean.shareUrl);
        sp.setImageUrl(shareBean.logoUrl);
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(platformActionListener);
        qq.share(sp);
    }

    /**
     * 微信分享
     */
    public static void Wechat(Context context, ShareInfo shareBean) {
        ShareSDK.initSDK(context);
        Wechat.ShareParams ws = new Wechat.ShareParams();
        ws.setTitle(shareBean.title);
        ws.setText(shareBean.text);
        ws.setImageUrl(shareBean.logoUrl);
        ws.setUrl(shareBean.shareUrl);
        ws.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
        weixin.setPlatformActionListener(platformActionListener);
        weixin.share(ws);
    }

    /**
     * 微信朋友圈分享
     */
    public static void WechatMoments(Context context, ShareInfo shareBean) {
        ShareSDK.initSDK(context);
        WechatMoments.ShareParams ws1 = new WechatMoments.ShareParams();
        ws1.setTitle(shareBean.title);
        ws1.setUrl(shareBean.shareUrl);
        ws1.setImageUrl(shareBean.logoUrl);
        ws1.setShareType(Platform.SHARE_WEBPAGE);
        Platform pengyouquan = ShareSDK.getPlatform(WechatMoments.NAME);
        pengyouquan.setPlatformActionListener(platformActionListener);
        pengyouquan.share(ws1);
    }


    /**
     * 新浪微博分享
     */
    public static void SinaWeibo(Context context, ShareInfo shareBean) {
        ShareSDK.initSDK(context);
        SinaWeibo.ShareParams shareParams = new SinaWeibo.ShareParams();
        shareParams.setTitle(shareBean.title);
        shareParams.setUrl(shareBean.shareUrl);
        shareParams.setImageUrl(shareBean.logoUrl);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        Platform pengyouquan = ShareSDK.getPlatform(SinaWeibo.NAME);
        pengyouquan.setPlatformActionListener(platformActionListener);
        pengyouquan.share(shareParams);
    }


    /**
     * 腾讯微博分享
     */
    public static void TencentWeibo(Context context, ShareInfo shareBean) {
        ShareSDK.initSDK(context);
        TencentWeibo.ShareParams shareParams = new TencentWeibo.ShareParams();
        shareParams.setTitle(shareBean.title);
        shareParams.setUrl(shareBean.shareUrl);
        shareParams.setImageUrl(shareBean.logoUrl);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        Platform pengyouquan = ShareSDK.getPlatform(TencentWeibo.NAME);
        pengyouquan.setPlatformActionListener(platformActionListener);
        pengyouquan.share(shareParams);
    }


    /**
     * Facebook分享
     */
    public static void Facebook(Context context, ShareInfo shareBean) {
        ShareSDK.initSDK(context);
        Facebook.ShareParams shareParams = new Facebook.ShareParams();
        shareParams.setTitle(shareBean.title);
        shareParams.setUrl(shareBean.shareUrl);
        shareParams.setImageUrl(shareBean.logoUrl);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        Platform pengyouquan = ShareSDK.getPlatform(Facebook.NAME);
        pengyouquan.setPlatformActionListener(platformActionListener);
        pengyouquan.share(shareParams);
    }


    /**
     * Twitter分享
     */
    public static void Twitter(Context context, ShareInfo shareBean) {
        ShareSDK.initSDK(context);
        Twitter.ShareParams shareParams = new Twitter.ShareParams();
        shareParams.setTitle(shareBean.title);
        shareParams.setUrl(shareBean.shareUrl);
        shareParams.setImageUrl(shareBean.logoUrl);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        Platform pengyouquan = ShareSDK.getPlatform(Twitter.NAME);
        pengyouquan.setPlatformActionListener(platformActionListener);
        pengyouquan.share(shareParams);
    }

    /**
     * 分享的回调
     */
    static PlatformActionListener platformActionListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            // 成功
            Log.e("分享成功","分享成功");
            Message msg = new Message();
            msg.what = MSG_TOAST;
            sHandler.sendMessage(msg);
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            // 失敗
            //打印错误信息,print the error msg
            throwable.printStackTrace();
            //错误监听,handle the error msg
            Log.e("分享失败","分享失败");
            Message msg = new Message();
            msg.what = MSG_ACTION_CCALLBACK;
            sHandler.sendMessage(msg);
            Log.e("分享失败原因",throwable.toString());
        }

        @Override
        public void onCancel(Platform platform, int i) {
            // 取消
            Log.e("分享取消","分享取消");
            Message msg = new Message();
            msg.what = MSG_CANCEL_NOTIFY;
            sHandler.sendMessage(msg);
        }
    };


    /**
     * 分享回调Handler
     */
    static Handler sHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
//                    HttpCom.POST(handler,HttpCom.ShareJifenUrl,map,false);
                    break;
                case 2:
                    Log.e("分享失败", "失败");
                    ToastUtil.showToast("分享失败");
                    break;
                case 3:
                    ToastUtil.showToast("分享取消");
                    Log.e("分享取消", "取消");
                    break;
            }
        }
    };

    /**
     * 分享结果返回服务器
     */
   static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("分享json",msg.obj.toString());
                    break;
                case 2:
                    Log.e("分享获取积分出错",msg.obj.toString());
                    break;
            }
        }
    };
}
