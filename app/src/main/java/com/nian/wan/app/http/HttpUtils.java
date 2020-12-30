package com.nian.wan.app.http;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import com.mc.developmentkit.bean.LinkAgeInfo;
import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.bean.DataBean;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.bean.MyYaoqingBean;
import com.nian.wan.app.bean.ThirdLoginBean;
import com.nian.wan.app.bean.UserInfo;
import com.nian.wan.app.bean.UserLoginBean;
import com.nian.wan.app.db.UserInfoDB;
import com.nian.wan.app.utils.DatabaseOpenHelper;
import com.nian.wan.app.utils.LogUtils;
import com.nian.wan.app.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 描述：网络请求工具类
 */
public abstract class HttpUtils<T> {

    public String tag = "";
    private Type type;
    private boolean need_token = false;
    private Callback.Cancelable post;


    /**
     * 网络请求
     *
     * @param type       json数据解析成什么样子
     * @param url        网络请求链接
     * @param map        请求参数
     * @param tag        打印Log标签
     * @param isUseToken 是否显示加载弹窗
     */
    public HttpUtils(Type type, String url, Map<String, String> map, String tag, boolean isUseToken, String filePath) {
        this.tag = tag;
        this.type = type;
        post = HttpConstant.POST(handler, url, map, isUseToken, filePath, tag);
    }

    public HttpUtils(Type type, String url, Map<String, String> map, String tag, boolean isUseToken) {
        this.tag = tag;
        this.type = type;
        post = HttpConstant.POST(handler, url, map, isUseToken, null, tag);
    }


    public HttpUtils(Type type, String url, Map<String, String> map) {
        this.type = type;
        post = HttpConstant.POST(handler, url, map, false, null, null);
    }

    /**
     * 停止网络请求
     */
    public void cancel() {
        if (post != null) {
            post.cancel();
        }
    }

    /**
     * 网络请求返回结果
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        String aNull = msg.obj.toString().replace("null", "\"\"");
                        LogUtils.w(tag, aNull);
                        JSONObject jsonObject = new JSONObject(aNull);
                        int code = jsonObject.getInt("code");
                        String msg1 = jsonObject.getString("msg");

                        if (code == 200) {
                            HttpResult<T> data = HttpConstant.getData(aNull, type, tag);
                            if (data != null) {
                                _onSuccess(data.getData(), msg1);
                            } else {
                                LogUtils.e("HttpResult解析失败", "HttpResult解析失败");
                                _onError();
                            }
                        } else {
                            if (code == 1117) {
                                HttpResult<T> data1 = HttpConstant.getData(aNull, type, tag);
                                _onError((String) data1.getData(), code);
                            } else {
                                _onError(msg1, code);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        LogUtils.e("http错误", "" + e.toString());
                        _onError();
                    }
                    break;
                case 2:
                    LogUtils.e(tag + "网络缓慢", msg.obj.toString());
                    _onError();
                    break;
            }
        }
    };

    protected abstract void _onSuccess(T t, String msg);             //请求成功

    protected abstract void _onError(String message, int code);      //后台返回码  出错

    protected abstract void _onError();                             //xutils  网络请求报错   或解析报错


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Post网络请求
     *
     * @param url
     * @return
     */
    public static void POST(final Handler handler, String url, String body, final boolean aa) {

        RequestParams params = new RequestParams(url);
        if (aa) {
            String encodeToString = Base64.encodeToString(body.toString().getBytes(), Base64.DEFAULT);
            params.setBodyContent(encodeToString);
        } else {
            params.setBodyContent(body);
        }
        params.setCacheMaxAge(1000 * 60);
        final Message msg = new Message();
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                Log.e("POST错误信息", arg0.toString());
                msg.what = 2;
                handler.sendMessage(msg);
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(String json) {
                msg.what = 1;
                try {
                    if (aa) {
                        String result = new String(Base64.decode(json, Base64.DEFAULT), "utf-8");
                        msg.obj = result;
                        handler.sendMessage(msg);
                    } else {
                        msg.obj = json;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    Log.e("POST+json成功回调出错：", e.toString());
                }
            }

            @Override
            public boolean onCache(String json) {
                msg.what = 1;
                try {
                    String result = new String(Base64.decode(json, Base64.DEFAULT), "utf-8");
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e("POST+json缓存回调出错：", e.toString());
                }
                return true;
            }
        });
    }


    /**
     * 获取下载链接
     *
     * @param string
     * @return
     */
    @SuppressLint("HandlerLeak")
    public static DataBean DNSdownUrl(String string) {
        Log.e("获取下载链接JSON", string);
        try {
            JSONObject jsonObject = new JSONObject(string);
            int int1 = jsonObject.getInt("status");
            if (int1 == 200) {
                JSONObject data = jsonObject.getJSONObject("data");
                final DataBean dataBean = new DataBean();
                dataBean.id = data.getInt("game_id");
                dataBean.url = data.getString("url");
                dataBean.record_id = Utils.record_id;
                return dataBean;
            } else {
                String string2 = jsonObject.getString("return_code");
                Utils.TS(string2);
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("解析获取下载错误", e.toString());
            return null;
        }
    }

    /**
     * 首页-热门
     */
    public static ArrayList<GameInfo> homeGame(String json) {
        ArrayList<GameInfo> appInfos = new ArrayList<>();
        try {
            JSONObject jsonObject2 = new JSONObject(json);
            int status = jsonObject2.getInt("code");
            if (status == 200) {
                JSONArray msg = jsonObject2.getJSONArray("data");
                for (int i = 0; i < msg.length(); i++) {
                    JSONObject jsonObject1 = msg.getJSONObject(i);
                    GameInfo appInfo = new GameInfo();
                    appInfo.iconurl = jsonObject1.getString("icon");
                    appInfo.name = jsonObject1.getString("game_name");
                    appInfo.id = jsonObject1.getInt("id");
                    appInfo.features = jsonObject1.getString("features");
                    appInfo.type = jsonObject1.getString("game_type_name");
                    appInfo.playNum = jsonObject1.getString("play_num");
                    appInfo.GameUrl = jsonObject1.getString("play_url");
                    appInfo.fanli = jsonObject1.getString("ratio");
                    appInfo.gift = jsonObject1.getInt("gift_id");
                    appInfo.size = jsonObject1.getString("game_size");
                    appInfo.canDownload = jsonObject1.getInt("xia_status");
                    appInfo.is_high_level_game = jsonObject1.getString("is_high_level_game");
                    appInfo.bg_img = jsonObject1.getString("bg_img");
                    appInfos.add(appInfo);
                }
                return appInfos;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("首页-热门-游戏列表json异常：", e.toString());
            return null;
        }
    }


    /**
     * 游戏详情
     *
     * @param s
     */
    public static GameInfo DNSGameDel(String s) {
        Log.e("游戏详情页面json", s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                JSONObject data = jsonObject.getJSONObject("data");
                GameInfo appInfo = new GameInfo();
                appInfo.name = data.getString("game_name");
                appInfo.iconurl = data.getString("icon");
                appInfo.playNum = data.getString("play_num");
                appInfo.id = data.getInt("id");
                appInfo.Collection = data.getInt("is_collect");
                appInfo.size = data.getString("game_size");
                appInfo.introduce = data.getString("introduction");
                appInfo.features = data.getString("features");
                appInfo.fanli = data.getString("ratio");
                appInfo.discount = data.getString("discount");
                appInfo.canDownload = data.getInt("xia_status");
                appInfo.rating = (float) data.getInt("game_score");
                JSONArray screenshot = data.getJSONArray("screenshot");
                for (int i = 0; i < screenshot.length(); i++) {
                    String string = screenshot.getString(i);
                    appInfo.jietu.add(string);
                }
                return appInfo;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("解析游戏详情页面异常", e.toString());
            return null;
        }
    }


    /**
     * 第三方登录获取用户信息
     *
     * @param json
     * @return
     */
    public static ThirdLoginBean thirdPartyLoginGetInfo(String type, String json) {
        Log.e("第三方登录用户信息", json);
        ThirdLoginBean userInfo = new ThirdLoginBean();
        try {
            JSONObject jsonObject = new JSONObject(json);
            userInfo.icon = jsonObject.getString("icon");
            userInfo.nickname = jsonObject.getString("nickname");
            userInfo.token = jsonObject.getString("token");
            //QQ用户：userID
            if (type.equals("QQ")) {
                userInfo.id = jsonObject.getString("userID");
                //微信用户：unionid
            } else if (type.equals("Wechat")) {
                userInfo.id = jsonObject.getString("unionid");
            }
            return userInfo;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("获取第三方用户信息异常：", e.toString());
            return null;
        }
    }

    /**
     * 判断是否成功
     */
    public static boolean DNSJudge(String type, String json) {
        Log.e(type, json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("status");
            String msg = jsonObject.getString("return_msg");
            if (status == 1) {
                return true;
            } else {
                Utils.TS(msg);
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 推荐/热门游戏~
     *
     * @param json
     * @return
     */
    public static ArrayList<GameInfo> DNSJinp1(String json) {
        Log.e("推荐游戏json", json);
        ArrayList<GameInfo> appInfos = new ArrayList<>();
        try {
            JSONObject jsonObject2 = new JSONObject(json);
            int status = jsonObject2.getInt("code");
            if (status == 200) {
                JSONArray msg = jsonObject2.getJSONArray("data");
                for (int i = 0; i < msg.length(); i++) {
                    JSONObject jsonObject1 = msg.getJSONObject(i);
                    GameInfo appInfo = new GameInfo();
                    appInfo.iconurl = jsonObject1.getString("icon");
                    appInfo.name = jsonObject1.getString("game_name");
                    appInfo.id = jsonObject1.getInt("id");
                    appInfo.features = jsonObject1.getString("features");
                    appInfo.size = jsonObject1.getString("game_size");
                    appInfo.type = jsonObject1.getString("game_type_name");
                    appInfo.playNum = jsonObject1.getString("play_num");
                    appInfo.GameUrl = jsonObject1.getString("play_url");
                    appInfo.fanli = jsonObject1.getString("ratio");
                    appInfo.gift = jsonObject1.getInt("gift_id");
                    appInfo.canDownload = jsonObject1.getInt("xia_status");
                    appInfo.size = jsonObject1.getString("game_size");
                    appInfos.add(appInfo);
                }
                return appInfos;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("推荐游戏json异常：", e.toString());
            return null;
        }
    }


    /**
     * 搜索页面推荐热门游戏~
     *
     * @param json
     * @return
     */
    public static ArrayList<GameInfo> SearchHotGame(String json) {
        Log.e("搜索页面推荐热门游戏", json);
        ArrayList<GameInfo> appInfos = new ArrayList<>();
        try {
            JSONObject jsonObject2 = new JSONObject(json);
            int status = jsonObject2.getInt("code");
            if (status == 200) {
                JSONArray msg = jsonObject2.getJSONArray("data");
                for (int i = 0; i < msg.length(); i++) {
                    JSONObject jsonObject1 = msg.getJSONObject(i);
                    GameInfo appInfo = new GameInfo();
                    appInfo.iconurl = jsonObject1.getString("icon");
                    appInfo.name = jsonObject1.getString("game_name");
                    appInfo.id = jsonObject1.getInt("id");
                    appInfo.features = jsonObject1.getString("features");
                    appInfo.type = jsonObject1.getString("game_type_name");
                    appInfo.playNum = jsonObject1.getString("play_num");
                    appInfo.GameUrl = jsonObject1.getString("play_url");
                    appInfo.gift = jsonObject1.getInt("gift_id");
                    appInfo.canDownload = jsonObject1.getInt("xia_status");
                    appInfo.sdk_version = jsonObject1.getInt("sdk_version");
                    appInfos.add(appInfo);
                }
                return appInfos;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("搜索页面推荐热门游戏异常：", e.toString());
            return null;
        }
    }


    /**
     * 获取验证码
     *
     * @param s
     */
    public static boolean DNSGetYZM(String s) {
        LogUtils.e("获取验证码json", s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            int status = jsonObject.getInt("status");
            String msg = jsonObject.getString("msg");
            ToastUtil.showToast(msg);
            if (status == 1) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("解析获取验证码出错", e.toString());
            return false;
        }
    }

    /**
     * 解绑手机号
     *
     * @param s
     */
    public static boolean DNSJiebang(String s) {
        LogUtils.e("解绑手机号json", s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                return true;
            } else {
                String msg = jsonObject.getString("msg");
                ToastUtil.showToast(msg);
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("解析解绑手机号出错", e.toString());
            return false;
        }
    }

    /**
     * 手机注册
     *
     * @param s
     */
    public static boolean DNSRegister(String s) {
        Log.e("手机注册json", s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                JSONObject data = jsonObject.getJSONObject("msg");
                UserInfo userInfo = new UserInfo();
                userInfo.id = 1;
                userInfo.nickname = data.getString("account");
                userInfo.token = data.getString("token");
                DbManager db = DatabaseOpenHelper.getInstance();
                db.saveOrUpdate(userInfo);
                return true;
            } else {
                String msg = jsonObject.getString("msg");
                ToastUtil.showToast(msg);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("手机注册出错", e.toString());
            return false;
        }
    }

    /**
     * 用户登录
     *
     * @param s
     * @return
     */
    public static boolean DNSUserLogin(String s) {
        Log.e("用户登录json", s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                JSONObject data = jsonObject.getJSONObject("msg");
                UserInfo userInfo = new UserInfo();
               /* userInfo.id = 1;
                userInfo.nickname = data.getString("account");*/
                userInfo.token = data.getString("token");
                DbManager db = DatabaseOpenHelper.getInstance();
                db.saveOrUpdate(userInfo);
                return true;
            } else {
                String msg = jsonObject.getString("msg");
                ToastUtil.showToast(msg);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("用户登录出错", e.toString());
            return false;
        }
    }


    /**
     * 获取用户信息
     *
     * @param s
     */
    public static boolean DNSUserMsg(String s) {
        Log.e("用户信息json", s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            int anInt = jsonObject.getInt("status");
            if (anInt == 1) {
                JSONObject msg = jsonObject.getJSONObject("msg");
                UserInfo loginUser = Utils.getLoginUser();
                loginUser.Ptb = msg.getString("balance");
                loginUser.phone = msg.getString("phone");
                DbManager db = DatabaseOpenHelper.getInstance();
                db.saveOrUpdate(loginUser);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("用户信息出错", e.toString());
            return false;
        }
    }


    /**
     * 获取可代充游戏列表
     *
     * @param s
     */
    public static List<LinkAgeInfo> DNSGameCanPay(String s) {
        Log.e("获取可代充游戏列表json", s);
        List<LinkAgeInfo> carouselFigures = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            int status = jsonObject.getInt("code");
            if (status == 200) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    LinkAgeInfo linkAgeInfo = new LinkAgeInfo();
                    linkAgeInfo.id = jsonObject1.getInt("id");
                    String game_name = jsonObject1.getString("game_name");
                    linkAgeInfo.name = Utils.getJieQu(game_name);
                    carouselFigures.add(linkAgeInfo);
                }
                return carouselFigures;
            } else {
                String msg = jsonObject.getString("msg");
                Log.e("获取代充游戏返回码", msg);
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("获取可代充游戏列表json出错", e.toString());
            return null;
        }
    }


    /**
     * 我的邀请
     *
     * @param s
     */
    public static List<MyYaoqingBean> DNSMyYaoqing(String s) {
        Log.e("我的邀请json", s);
        List<MyYaoqingBean> informationBeen = new ArrayList<>();
        try {
            JSONObject jsonObject1 = new JSONObject(s);
            int status = jsonObject1.getInt("code");
            if (status == 200) {
                JSONArray data = jsonObject1.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject = data.getJSONObject(i);
                    MyYaoqingBean in = new MyYaoqingBean();
                    in.account = jsonObject.getString("user_account");
                    in.time = jsonObject.getString("create_time");
                    in.qian = jsonObject.getString("award_coin");
                    informationBeen.add(in);
                }
                return informationBeen;
            } else {
                String string = jsonObject1.getString("msg");
                Log.e("我的邀请", string);
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("解析我的邀请异常", e.toString());
            return null;
        }
    }


    /**
     * ============================================================================================
     * 以下方法适用于5.0
     */

    /**
     * 持久化用户信息
     */
    public static void persistentUserInfo(UserLoginBean userLoginBean) {
        try {
            UserInfoDB userInfoDB = new UserInfoDB();
            userInfoDB.id = 1;
            userInfoDB.uid = userLoginBean.getUser_id();
            userInfoDB.account = userLoginBean.getAccount();
            userInfoDB.token = userLoginBean.getToken();
            userInfoDB.portrait = userLoginBean.getHead_icon();
            DbManager db = DatabaseOpenHelper.getInstance();
            db.saveOrUpdate(userInfoDB);
            LogUtils.e("存进去了", "存进去了");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
