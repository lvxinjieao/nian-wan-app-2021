package com.nian.wan.app.http;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.nian.wan.app.utils.Utils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class HttpConstant {

    public static String AppId = "wx6e661d30573350e7";
    public static String FilePath = "nianwan";
    public static String qunkey;                                                                //关于我们接口返回此QQ群Key信息

    public static String DownServer = "com.nian.wan.app.download.DownloadManager";                  //下载服务
    public static String shanchu = "class com.nian.wan.app.guild.activity.four.DownloadActivity";   //下载管理显示删除功能
    public static String guize = "活动规则解释权归 亦度科技 所有";
    public static String fenxiang = "邀请您的好友下载游戏并进行现金充值，您将会获得充值支付金额5%的平台币邀请奖励，每位好友给您的奖励100平台币封顶，邀请人数不限";


    public static String Common = "https://www.nianwan.cn/app.php/";

//    public static String Common = "http://nianwan.cn/app.php/";

    public static String userPhoneRegister = Common + "User/user_phone_register";               //手机号注册
    public static String Game = Common + "Game/recommend_game";
    //全部游戏
    public static String forgetPassword = Common + "User/forget_password";                      //找回密码
    public static String giftList = Common + "GameGift/game_gift_listAll";                      //所有游戏礼包列表
    public static String gameGiftList = Common + "GameGift/game_gift_list";                     //当前游戏礼包列表
    public static String Type = Common + "Game/get_game_lists_by_type";                         //分类详情
    public static String LoginUrl = Common + "User/user_login";                                 //普通注册
    public static String BangBiUrl = Common + "Game/get_user_bind_coin";                        //获取绑币
    public static String MyShoucangUrl = Common + "User/get_my_collect";                        //我的收藏
    public static String DeleteShoucangUrl = Common + "User/del_collect";                       //删除收藏
    public static String StartGame = Common + "Game/open_game";                                 //开始游戏

    public static final String GetdownURL = Common + "Down/down_file";                          //获取下载链接
    public static final String TabDown = Common + "Game/downRecord";                            //记录=下载下载的游戏
    public static String UpPassUrl = Common + "User/forget_password_pwd";                       //忘记密码提交密码
    public static String feedback = Common + "User/feedback";                                   //意见反馈
    public static String getPhoneCode = Common + "User/send_sms";                               //获取手机验证码
    public static String DeleteZu = Common + "User/quitfoot";                                   //删除足迹
    public static String MyZuJi = Common + "User/my_game";                                      //我的足迹
    public static String userRegister = Common + "User/user_register";                          //用户注册
    public static final String DownList = Common + "Game/downRecordLists";                      //下载游戏的记录列表
    public static final String DownDel = Common + "Game/downRecordDel";                         //删除下载记录
    public static final String GameDetUrl = Common + "Game/get_game_detail";                    //手游详情页面
    public static final String DiscountURL = Common + "Game/get_game_recharge_discount";        //获取折扣充值信息
    public static final String DCGameURL = Common + "Game/get_user_recharge_game";              //获取可代充的游戏列表

    //服务器地址
    public static String ServerUrl = Common;
    public static String GetShare = ServerUrl + "Action/share";                                 //获取文章分享所需数据*
    //帐号注册*
    public static String API_USER_REGISTER = ServerUrl + "User/user_register";
    //手机号注册*
    public static String API_USER_REGISTER_MOBILE = ServerUrl + "User/user_phone_register";
    //登录*
    public static String API_USER_LOGIN = ServerUrl + "User/user_login";
    //第三方登录*
    public static String API_USER_THIRD_LOGIN = ServerUrl + "User/user_third_login";
    //游客登录*
    public static String API_VISITOR_LOGIN = ServerUrl + "User/ykregister";

    /*首页start*/
    //首页轮播图*
    public static String API_HOME_BANNER = ServerUrl + "Adv/get_slider";
    //最近在玩*
    public static String API_HOME_RECENTLY_PALY = ServerUrl + "Game/userLatelyPlay";
    //首页游戏*
    public static String API_HOME_GAME = ServerUrl + "Game/gameRecList";
    //首页小程序*
    public static String API_SMALLGAME_LIST = ServerUrl + "SmallGame/getLists";
    //首页活动*
    public static String API_HOME_ACTIVITIES = ServerUrl + "Article/get_article_lists";
    //开服*
    public static String API_HOME_ALREADY_OPEN_SERVER = ServerUrl + "Game/server";
    //开服通知*
    public static String API_HOME_ALREADY_OPEN_SERVER_PUSH = ServerUrl + "Game/setServerNotice";
    /*首页end*/

    /*礼包start*/
    //礼包列表*
    public static String API_GIFT_LISTS = ServerUrl + "Game/gameGiftLists";
    //礼包详情*
    public static String API_GIFT_DETAIL = ServerUrl + "Game/giftDetail";
    //领取礼包*
    public static String API_GIFT_GET = ServerUrl + "Game/get_novice";
    /*礼包end*/

    /*游戏start*/
    //游戏分类
    public static String API_GAME_SUBJECT = ServerUrl + "Game/gameGroup";
    //游戏分类游戏列表
    public static String API_GAME_SUBJECT_LIST = ServerUrl + "Game/gameGroupList";
    //游戏详情（游戏介绍之前）
    public static String API_GAME_SUBJECT_DETAIL = ServerUrl + "Game/gameDetail";
    //闪屏页
    public static String API_SPLASH = ServerUrl + "Center/get_app_icon";
    //游戏详情（游戏礼包）
    public static String API_GAME_SUBJECT_GAME_GIFT = ServerUrl + "Game/gameGift";
    //游戏详情（活动）
    public static String API_GAME_SUBJECT_GAME_ACTIVITIES_DETAIL = ServerUrl + "Game/gameActiveDoc";
    //大家都在玩/猜你喜欢
    public static String API_GAME_SUBJECT_GUESS_YOU_PALY = ServerUrl + "Game/gsULike";
    //收藏/取消收藏游戏
    public static String API_GAME_COLLECT = ServerUrl + "Action/change_collect_status";
    /*游戏end*/

    /*商城start*/
    //商品列表
    public static String API_SHOP_GOODS_LIST = ServerUrl + "PointShop/mall";
    //礼包详情
    public static String API_SHOP_GIFT_DETAIL = ServerUrl + "PointShop/mall_detail";
    //签到
    public static String API_SHOP_SIGN = ServerUrl + "PointShop/sign_info";
    //日常任务
    public static String API_SHOP_TASK = ServerUrl + "PointShop/mall_integral";
    //积分记录
    public static String API_SHOP_INTEGRAL = ServerUrl + "PointShop/mall_record";
    //50条商城轮播数据
    public static String API_SHOP_BINNER = ServerUrl + "PointShop/mall_play";
    //商品兑换
    public static String API_SHOP_EXCHANGE = ServerUrl + "PointShop/mall_buy";
    //获取分享相关信息
    public static String API_SHOP_SHARE = ServerUrl + "Action/share";
    /*商城end*/

    //支付宝支付
    public static String API_PAY_ALIPAY = ServerUrl + "Pay/alipay_pay";
    //微信支付
    public static String API_PAY_WECHAT = ServerUrl + "Pay/wx_pay";

    /*个人中心start 60*/
    //是否绑定手机号、积分、是否签到
    public static String API_PERSONAL_CENTER_USER = ServerUrl + "Center/user";
    //实名认证开关
    public static String API_PERSONAL_CNTER_REAL_NAME_SWITCH = ServerUrl + "Center/auth_status";
    //实名信息认证
    public static String API_PERSONAL_CENTER_USER_AUTH_DATA = ServerUrl + "Center/user_auth_data";
    //进行实名认证
    public static String API_PERSONAL_CENTER_USER_AUTH = ServerUrl + "Center/user_auth";
    //修改密码
    public static String API_PERSONAL_CENTER_CHANGE_PWD = ServerUrl + "Center/user_password";
    //获取用户收货地址
    public static String API_PERSONAL_ADDRESS_USER = ServerUrl + "Center/user_address";
    //添加用户收获地址
    public static String API_PERSONAL_ADDRESS_ADD = ServerUrl + "Center/user_address_add";
    //编辑用户收获地址
    public static String API_PERSONAL_ADDRESS_EDIT = ServerUrl + "Center/user_address_edit";
    //删除用户收获地址
    public static String API_PERSONAL_ADDRESS_DEL = ServerUrl + "Center/user_address_del";


    //用户-绑定手机（第一次绑定会获得积分 70）
    public static String API_PERSONAL_USER_BIND_PHONE = ServerUrl + "Center/user_bind_phone";
    //用户-解绑手机
    public static String API_PERSONAL_USER_UNBIND_PHONE = ServerUrl + "Center/user_unbind_phone";
    //发送验证码
    public static String API_PERSONAL_USER_SEND_SMS = ServerUrl + "User/send_sms";
    //验证验证码
    public static String CHECK_CODE = ServerUrl + "Center/check_code_return";
    //联系我们
    public static String API_PERSONAL_USER_ABOUT_US = ServerUrl + "Center/user_contact";
    //商城规则
    public static String Business_City_Rules = ServerUrl + "PointShop/mall_rule";
    //用户-余额（点数 平台币 绑币数据）
    public static String API_PERSONAL_USER_BALANCE = ServerUrl + "Center/user_balance";
    //我的礼包
    public static String API_PERSONAL_MY_GIFT = ServerUrl + "Center/user_gift";
    //我的收藏、足迹
    public static String API_PERSONAL_COLLECTION_FOOTPRINT = ServerUrl + "Center/user_collection";
    //群体取消收藏
    public static String API_PERSONAL_CANCEL_COLLECTION = ServerUrl + "Action/cancel_collect";
    //删除足迹
    public static String API_PERSONAL_DELETE_FOOT = ServerUrl + "Action/delete_foot";
    //热门礼包
    public static String API_PERSONAL_HOT_GIFT = ServerUrl + "Center/user_no_gift";
    //单个热门游戏
    public static String API_PERSONAL_HOT_GAME = ServerUrl + "Game/hot_game";
    //用户-获取用户的消息
    public static String API_PERSONAL_USER_MESSAGE = ServerUrl + "Center/user_message";
    //直接签到
    public static String API_PERSONAL_USER_SIGN = ServerUrl + "PointShop/sign_in";
    //热门游戏和推荐游戏 三条
    public static String API_PERSONAL_USER_RECOMMEND_HOT = ServerUrl + "Game/game_recommend_hot";
    //首页活动红点是否显示
    public static String API_HOME_IS_SHOW_RED_POINT = ServerUrl + "Article/show_red_point";
    //记录用户查看活动（取消红点）
    public static String API_HOME_CANCEL_RED_POINT = ServerUrl + "Article/set_red_point";
    //支付方式
    public static String API_PERSONAL_PAY_WAYS = ServerUrl + "Pay/payWay";

    /*个人中心end*/
    //搜索
    public static String API_GLOBAL_SEARCH = ServerUrl + "Action/search";
    //验证token是否过期
    public static String API_TOKEN_IS_VALID = ServerUrl + "User/auth_token";


    public static final String YaoURL = ServerUrl + "Share/get_user_invite_info";               //获取用户邀请奖励
    public static final String ShareURL = ServerUrl + "Share/get_share_url";                    //获取分享链接
    public static final String MyYaoqingURL = ServerUrl + "Share/get_my_invite_record";         //我的邀请记录
    public static final String RQCode = ServerUrl + "Center/appLogo";                           //首页二维码
    public static final String GameRacking = ServerUrl + "Game/get_game_rank_lists";            //游戏排行榜
    public static final String ZhekouGame = ServerUrl + "Game/get_discount_game_lists";         //折扣游戏
    public static final String DuiHuanPTB = ServerUrl + "PointShop/point_convert_coin";         //兑换平台币
    public static final String PayUrl = ServerUrl + "Pay/recharge";                             //支付宝支付 !!!(jar文件内，无法改动)
    public static long Time = 5000;                                                             //多少秒后执行

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Post网络请求
     */
    public static void POST(final Handler handler, final String url, Map<String, String> map, final boolean isEncrypt) {

        RequestParams params = new RequestParams(url);
        params.setMultipart(true);

        Log.e("---------【请求URL】", url);
        if (map != null) {
            String lvxinmin = "";
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                String abc = key + "=" + value + "&";
                lvxinmin += abc;
                params.addBodyParameter(key, value, "multipart/form-data");
            }
            Log.e("---------【请求参数】", lvxinmin.toString());
        }


        x.http().post(params, new Callback.CacheCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(String json) {

                Message msg = new Message();
                msg.what = 1;

                if (isEncrypt) {
                    //String result = new String(Base64.decode(json, Base64.DEFAULT), "utf-8");
                    msg.obj = json;
                    handler.sendMessage(msg);
                } else {
                    if (json != null && json.startsWith("\ufeff")) {
                        json = json.substring(1);
                    }
                    msg.obj = json;
                    handler.sendMessage(msg);
                }

            }

            @Override
            public boolean onCache(String json) {
                Message msg = new Message();
                msg.what = 1;
                try {
                    String result = new String(Base64.decode(json, Base64.DEFAULT), "utf-8");
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }


    /**
     * Post网络请求
     *
     * @param url
     * @return
     */
    public static Callback.Cancelable POST(final Handler handler, String url, Map<String, String> map, final boolean sendToken, String filePath, String tag) {
        RequestParams params = new RequestParams(url);
        params.setMultipart(true);

        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue(), "multipart/form-data");
            }
        }

        if (sendToken) {
            if (null != Utils.getPersistentUserInfo()) {
                params.addBodyParameter("token", Utils.getPersistentUserInfo().token, "multipart/form-data");
            }
        }


        Callback.Cancelable post = x.http().post(params, new Callback.CacheCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                try {
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = arg0.toString();
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(String json) {
                Message msg = new Message();
                msg.what = 1;
                try {
                    msg.obj = json;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean onCache(String json) {
                Message msg = new Message();
                msg.what = 3;
                try {
                    String result = new String(Base64.decode(json, Base64.DEFAULT), "utf-8");
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        return post;
    }


    @NonNull
    public static <T> HttpResult<T> getData(String json, java.lang.reflect.Type type, String tag) {
        try {
            Gson gson = new Gson();
            HttpResult<T> listHttpResult = gson.fromJson(json, type);
            return listHttpResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}











