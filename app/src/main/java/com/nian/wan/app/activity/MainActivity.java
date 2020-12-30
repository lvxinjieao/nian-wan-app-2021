package com.nian.wan.app.activity;

import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.nian.wan.app.R;
import com.nian.wan.app.adapter.MainPagerAdapter;
import com.nian.wan.app.fragment.PersonalCenter;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.BottomTabView;
import com.nian.wan.app.view.CollectionViewPager;
import com.nian.wan.app.view.UpDateDialog;

import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.head)
    ImageView head;

    @BindView(R.id.content)
    CollectionViewPager content;

    @BindView(R.id.tabs)
    BottomTabView tabs;

    private Long time;

    private MainPagerAdapter viewPagerAdapter;
    private MainActivityBroadcast mainActivityBroadcast;

    public static final String MESSAGE_RECEIVED_ACTION = x.app().getPackageName() + ".MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static boolean isForeground = false; //极光推送相关标识

    public void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Utils.initActionBarPosition(this, head);

        regsiterHomeBrodcast();

        if (Utils.getPersistentUserInfo() != null) {//不为空时，判断session
            checkToken();
        }

        viewPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        content.setAdapter(viewPagerAdapter);
        //预加载4页防止用户快速滑动导致的卡顿
        content.setOffscreenPageLimit(4);
        //禁止ViewPager滑动
        content.setPagingEnabled(false);
        //初始化底部导航
        final List<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();
        tabItemViews.add(new BottomTabView.TabItemView(this, "首页", R.color.gray, R.color.green, R.drawable.tab_home_unselect, R.drawable.tab_home_select, false));
        tabItemViews.add(new BottomTabView.TabItemView(this, "游戏", R.color.gray, R.color.green, R.drawable.tab_game_unselect, R.drawable.tab_game_select, false));
        tabItemViews.add(new BottomTabView.TabItemView(this, "榜单", R.color.gray, R.color.green, R.drawable.tab_rack_unselect, R.drawable.tab_rack_select, false));
        tabItemViews.add(new BottomTabView.TabItemView(this, "礼包", R.color.gray, R.color.green, R.drawable.tab_packages_unselect, R.drawable.tab_packages_select, false));
        tabItemViews.add(new BottomTabView.TabItemView(this, "商城", R.color.gray, R.color.green, R.drawable.tab_store_unselect, R.drawable.tab_store_select, true));
        tabItemViews.add(new BottomTabView.TabItemView(this, "我的", R.color.gray, R.color.green, R.drawable.tab_user_unselect, R.drawable.tab_user_select, false));
        tabs.setTabItemViews(tabItemViews);
        tabs.setUpWithViewPager(content);


        try {
            //检测到有更新
            String string_version = Utils.getPersistentAboutUsData().getAPP_VERSION();
            Integer service_version = Integer.parseInt(string_version);
            Integer local_version = Utils.getVersionCode(MainActivity.this);

            if (service_version > local_version) {
                UpDateDialog dialog = new UpDateDialog(MainActivity.this, R.style.MyDialogStyle);
                dialog.setCancelable(false);
                dialog.show();
            }

        } catch (Exception e) {
            Utils.TS("检测更新出现异常");
            Log.e("检测更新出现异常", e.toString());
        }
    }

    /**
     * 检查token是否过期
     */
    private void checkToken() {

        Type type = new TypeToken<HttpResult<String>>() {
        }.getType();

        new HttpUtils<String>(type, HttpConstant.API_TOKEN_IS_VALID, null, "首页检查token是否过期", true) {

            @Override
            protected void _onSuccess(String bean, String msg) {
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);
                //如果Token已过期
                if (1032 == code) {

                    //删除已持久化用户信息
                    Utils.deletePersistentUserInfo();

                    //发送注销广播
                    Intent intent = new Intent("com.yinu.login");
                    intent.putExtra("login_status", DialogLoginActivity.EVENT_LOGIN_EXCIT);
                    LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);

                    //弹出登录框
                    DialogLoginActivity loginActivity = new DialogLoginActivity(MainActivity.this, "LGOIN");
                    FragmentTransaction transaction = MainActivity.this.getFragmentManager().beginTransaction();
                    transaction.add(loginActivity, "WoDe");
                    transaction.show(loginActivity);
                    transaction.commitAllowingStateLoss();
                }

            }

            @Override
            protected void _onError() {

            }
        };
    }


    private void regsiterHomeBrodcast() {
        mainActivityBroadcast = new MainActivityBroadcast();
        IntentFilter intentFilter = new IntentFilter("com.yinu.change.viewpage.index");
        LocalBroadcastManager.getInstance(this).registerReceiver(mainActivityBroadcast, intentFilter);
    }

    private class MainActivityBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("change_status", -1)) {
                //个人中心余额跳转到商城
                case PersonalCenter.ACTION_GO_EXCHANGE:
                    content.setCurrentItem(3);
                    break;
                //个人中心我的收藏跳转到热门游戏
                case PersonalCenter.ACTION_GO_HOT_GAME:
                    content.setCurrentItem(1);
                    break;
                //个人中心我的收藏跳转到推荐H5游戏
                case PersonalCenter.ACTION_GO_RECOMMEND_H5_GAME:
                    content.setCurrentItem(1);
                    break;
                //个人中心我的收藏跳转到推荐手游游戏
                case PersonalCenter.ACTION_GO_RECOMMEND_SY_GAME:
                    content.setCurrentItem(1);
                    break;
                //个人中心我的足迹跳转到热门手游游戏
                case PersonalCenter.ACTION_GO_HOT_SY_GAME:
                    content.setCurrentItem(1);
                    break;
                //个人中心我的足迹跳转到新上架手游游戏
                case PersonalCenter.ACTION_GO_NEW_SY_GAME:
                    content.setCurrentItem(1);
                    break;
                //个人中心我的礼包跳转到推荐礼包
                case PersonalCenter.ACTION_GO_RECOMMEND_GIFT:
                    content.setCurrentItem(2);
                    break;
                //未读消息
                case PersonalCenter.ACTION_NEED_SHOW_RED_POINT:
                    tabs.getChildAt(3).findViewById(R.id.iv_tab_red_point).setVisibility(View.VISIBLE);
                    break;
                //未读消息
                case PersonalCenter.ACTION_NEED_SHOW_RED_POINT2:
                    tabs.getChildAt(4).findViewById(R.id.iv_tab_red_point).setVisibility(View.VISIBLE);
                    break;
                //已读消息
                case PersonalCenter.ACTION_UN_SHOW_RED_POINT2:
                    tabs.getChildAt(4).findViewById(R.id.iv_tab_red_point).setVisibility(View.INVISIBLE);
                    break;
                //注销
                case DialogLoginActivity.EVENT_LOGIN_EXCIT:
                    tabs.getChildAt(4).findViewById(R.id.iv_tab_red_point).setVisibility(View.VISIBLE);
                    tabs.getChildAt(3).findViewById(R.id.iv_tab_red_point).setVisibility(View.VISIBLE);
                    break;
                //签到
                case DialogLoginActivity.ACTION_ALREADY_SIGN:
                    tabs.getChildAt(3).findViewById(R.id.iv_tab_red_point).setVisibility(View.INVISIBLE);
                    break;
            }
        }

    }


    @Override
    public void onBackPressed() {
//        if ((System.currentTimeMillis() - time) > 2000) {
//            Utils.TS("再按一次退出程序！");
//            time = System.currentTimeMillis();
//        } else {
//            if (ServiceUtils.isServiceRunning(this, "timer")) {
//                ServiceUtils.stopService(this, TimeService.class);
//            }
//            if (ServiceUtils.isServiceRunning(this, "timer")) {
//                ServiceUtils.stopService(this, DownloadManager.class);
//            }
//            finish();
//            System.exit(0);
//        }
    }


}
