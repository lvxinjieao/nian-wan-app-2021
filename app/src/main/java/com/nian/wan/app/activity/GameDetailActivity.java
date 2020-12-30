package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.FilletImageView;
import com.nian.wan.app.R;
import com.nian.wan.app.adapter.AllPlayAdapter;
import com.nian.wan.app.adapter.GameDetGongLueRecyclerViewAdapter;
import com.nian.wan.app.adapter.GameDetailGiftRecyclerViewAdapter;
import com.nian.wan.app.adapter.GassAdapter;
import com.nian.wan.app.adapter.HomeGameActivitiesRecyclerViewAdapter;
import com.nian.wan.app.bean.DataBean;
import com.nian.wan.app.bean.DownDataBean;
import com.nian.wan.app.bean.GameDetailBean;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.bean.GassBean;
import com.nian.wan.app.bean.GetGiftBean;
import com.nian.wan.app.bean.HomeGameActivitiesBean;
import com.nian.wan.app.bean.IntoGameGiftBean;
import com.nian.wan.app.bean.LingQuGiftBean;
import com.nian.wan.app.bean.ShouCangBean;
import com.nian.wan.app.bean.StartGameBean;
import com.nian.wan.app.bean.UserInfo;
import com.nian.wan.app.download.DownManager;
import com.nian.wan.app.fragment.PersonalCenter;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.DatabaseOpenHelper;
import com.nian.wan.app.utils.PromoteUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogCollectedSuccess;
import com.nian.wan.app.view.DialogGetGiftFailed;
import com.nian.wan.app.view.DialogGetGiftSuccess;
import com.nian.wan.app.view.DialogGoLogin;
import com.nian.wan.app.view.DialogWeChatOfficialAccounts;

import org.xutils.ex.DbException;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Description:手游游戏详情
 */
public class GameDetailActivity extends BaseFragmentActivity {

    private static final String TAG = "GameDetailActivity";

    @BindView(R.id.tou)
    ImageView tou;

    @BindView(R.id.back)
    RelativeLayout back;

    @BindView(R.id.title)
    TextView title;


    @BindView(R.id.game_detail_icon)
    ImageView game_detail_icon;


    @BindView(R.id.game_detail_collection)
    RelativeLayout game_detail_collection;

    @BindView(R.id.game_detail_collection_icon)
    ImageView game_detail_collection_icon;

    @BindView(R.id.game_detail_collection_tv)
    TextView game_detail_collection_tv;


    @BindView(R.id.game_name)
    TextView game_name;

    @BindView(R.id.game_type)
    TextView game_type;

    @BindView(R.id.game_size)
    TextView game_size;

    @BindView(R.id.game_player)
    TextView game_player;

    @BindView(R.id.game_desc)
    TextView game_desc;


    @BindView(R.id.game_detai_splash_LinearLayout)
    LinearLayout game_detai_splash_linearLayout;


    @BindView(R.id.game_detail_introduction)
    TextView game_detail_introduction;


    @BindView(R.id.btn_more)
    LinearLayout btn_more;

    @BindView(R.id.tvhint)
    TextView tvhint;

    @BindView(R.id.rc_game_detail_gift_list)
    RecyclerView rcGameDetailGiftList;

    @BindView(R.id.btn_libao)
    RelativeLayout btnLibao;

    @BindView(R.id.ll_game_detail_content)
    LinearLayout llGameDetailContent;

    @BindView(R.id.ll_game_detail_gift_content)
    LinearLayout llGameDetailGiftContent;

    @BindView(R.id.bn)
    ImageView bn;

    @BindView(R.id.gass_more)
    LinearLayout gassMore;

    @BindView(R.id.rl_activities_title)
    RelativeLayout rlActivitiesTitle;

    @BindView(R.id.rc_home_game_activities_list)
    RecyclerView rcHomeGameActivitiesList;

    @BindView(R.id.ll_game_detail_activities_content)
    LinearLayout llGameDetailActivitiesContent;

    @BindView(R.id.bl)
    ImageView bl;

    @BindView(R.id.allplay_more)
    LinearLayout allplayMore;

    @BindView(R.id.all_play)
    GridView allPlay;

    @BindView(R.id.ll_game_detail_all_people)
    LinearLayout llGameDetailAllPeople;

    @BindView(R.id.btn_start)
    TextView btnStart;

    @BindView(R.id.tv_fanli)
    TextView tvFanli;

    @BindView(R.id.ll_game_detail_gonglue_content)
    LinearLayout llGameDetailGongLueContent;
    @BindView(R.id.rc_home_game_gonglue_list)
    RecyclerView rcHomeGameGongLueList;


    @BindView(R.id.game_download)
    RelativeLayout game_download;

    private ArrayList<GassBean> listGass;
    private ArrayList<GassBean> listAllPlay;
    private ArrayList<IntoGameGiftBean> intoGameGift;
    private GassAdapter gassAdapter;
    private AllPlayAdapter allPlayAdapter;
    private HomeGameActivitiesRecyclerViewAdapter gameActivitiesRecyclerViewAdapter;
    private GameDetailGiftRecyclerViewAdapter gameDetailGiftRecyclerViewAdapter;
    private GameDetGongLueRecyclerViewAdapter gameDetGongLueRecyclerViewAdapter;
    private LingQuGiftBean lingqubean;
    private GassBean gassBean;
    private GameDetailBean gameDetailBean;
    private StartGameBean startGameBean;
    private HomeGameActivitiesBean gameActivitiesBean;
    private ShouCangBean shouCangBean;
    private int game_id;
    private String token;
    private String gameURL;
    private StoreBroadcast storeBroadcast;
    private GetGiftBean getGiftBean;
    private int giftPos;
    private DownDataBean down;
    private int percent = -2;
    private boolean suo = true;


    @Override
    public void initView() {
        setContentView(R.layout.activity_game_detail);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        title.setText("游戏详情");
        game_id = getIntent().getIntExtra("game_id", -1);
        listGass = new ArrayList<>();
        intoGameGift = new ArrayList<>();

        gassAdapter = new GassAdapter(this);
        listAllPlay = new ArrayList<>();
        allPlayAdapter = new AllPlayAdapter(this);
        allPlay.setAdapter(allPlayAdapter);

        rcGameDetailGiftList.setNestedScrollingEnabled(false);
        rcHomeGameActivitiesList.setNestedScrollingEnabled(false);
        rcHomeGameGongLueList.setNestedScrollingEnabled(false);

        allPlay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("game_id", Integer.valueOf(listAllPlay.get(position).getGame_id()));
                if (listAllPlay.get(position).getSdk_version().equals("3")) {
                    intent.setClass(GameDetailActivity.this, GameDetailH5Activity.class);
                } else {
                    intent.setClass(GameDetailActivity.this, GameDetailActivity.class);
                }
                startActivity(intent);
            }
        });
        lingqubean = new LingQuGiftBean();
        gameDetailBean = new GameDetailBean();
        startGameBean = new StartGameBean();
        shouCangBean = new ShouCangBean();
        gassBean = new GassBean();
        //这一步是判断  数据库有没有这个游戏的下载记录 (根据当前游戏ID查询)
        down = DownManager.getInstance().getDownBean(game_id);
        if (down != null) {
            //如果有记录  判断当前下载状态
            percent = Aria.download(this).load(down.DownUrl).getTaskState();
            //percent的下载状态里没有  安装完成打开状态  需要自行判断
            if (down.packageName != null && Utils.isInstall(x.app(), down.packageName)) {
                //根据包名判断如果该游戏已经安装过    下载状态改为已安装 =8
                percent = 8;
            }

            if (percent == 3) {
                percent = -2;
            }
        }
        XiangQing();
        PeoplePlay();
        getGift();
        getActivities();
        getPublish();
        Aria.download(this).register();
        Status(percent);
    }


    @OnClick({R.id.back, R.id.game_detail_collection, R.id.allplay_more, R.id.btn_more, R.id.game_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.collection:
                if ("0".equals(gameDetailBean.getCollect_status())) {
                    ShouCang();
                    XiangQing();
                } else {
                    QuXiaoShouCang();
                    XiangQing();
                }
                break;
            case R.id.allplay_more:
                Intent recommednGameIntent = new Intent("com.yinu.change.viewpage.index");
                recommednGameIntent.putExtra("change_status", PersonalCenter.ACTION_GO_HOT_SY_GAME);
                LocalBroadcastManager.getInstance(this).sendBroadcast(recommednGameIntent);
                finish();
                break;
            case R.id.btn_more:
                UserInfo userInfo = Utils.getLoginUser();
                if (userInfo != null) {
                    Intent itGiftDet = new Intent(GameDetailActivity.this, GiftDetActivity.class);
                    itGiftDet.putExtra("game_id", game_id);
                    startActivity(itGiftDet);
                } else {
                    DialogLoginActivity loginActivity = new DialogLoginActivity(GameDetailActivity.this, "LGOIN");
                    FragmentTransaction transaction = GameDetailActivity.this.getFragmentManager().beginTransaction();
                    transaction.add(loginActivity, "WoDe");
                    transaction.show(loginActivity);
                    transaction.commitAllowingStateLoss();
                }
                break;
            case R.id.game_download:
                if (Utils.getLoginUser() != null) {
                    Intent intent = new Intent(this, MyGameActivity.class);
                    intent.putExtra("target", 3);
                    startActivity(intent);
                } else {
                    DialogLoginActivity loginActivity = new DialogLoginActivity(GameDetailActivity.this, "LGOIN");
                    FragmentTransaction transaction = GameDetailActivity.this.getFragmentManager().beginTransaction();
                    transaction.add(loginActivity, "WoDe");
                    transaction.show(loginActivity);
                    transaction.commitAllowingStateLoss();
                }
                break;
        }
    }


    /**
     * 获取游戏详情
     */
    public void XiangQing() {
        HashMap<String, String> map = new HashMap<>();
        map.put("game_id", game_id + "");
        if (Utils.getLoginUser() != null) {
            map.put("token", Utils.getLoginUser().token);
        }
        getGameDetail(map);
    }

    /**
     * 游戏详情（游戏介绍之前）
     *
     * @param map
     */
    private void getGameDetail(Map<String, String> map) {
        Type type = new TypeToken<HttpResult<GameDetailBean>>() {
        }.getType();

        new HttpUtils<GameDetailBean>(type, HttpConstant.API_GAME_SUBJECT_DETAIL, map, "游戏详情返回的数据", true) {
            @Override
            protected void _onSuccess(GameDetailBean bean, String msg) {
                gameDetailBean = bean;
                if (!TextUtils.isEmpty(bean.getRatio()) && !bean.getRatio().equals("0.00")) {
                    tvFanli.setText("充值返利" + bean.getRatio().replace(".00", "") + "%");
                } else {
                    tvFanli.setText("充值返利0%");
                }

                if (!TextUtils.isEmpty(bean.getGame_size())) {
                    game_size.setText(bean.getGame_size());
                } else {
                    game_size.setText("0M");
                }

                if (!TextUtils.isEmpty(gameDetailBean.getIcon())) {
                    Glide.with(GameDetailActivity.this).load(gameDetailBean.getIcon()).error(R.drawable.default_picture).transform(new RoundedCorners(6)).into(game_detail_icon);
                }

                title.setText(gameDetailBean.getGame_name());
                if (gameDetailBean.getGame_name().length() > 7) {
                    String s = gameDetailBean.getGame_name().substring(0, 7);
                    game_name.setText(s + "...");
                } else {
                    game_name.setText(gameDetailBean.getGame_name());
                }

                game_type.setText("(" + gameDetailBean.getGame_type_name() + ")" + "");
                game_player.setText(gameDetailBean.getPlay_num() + "");
                game_desc.setText(gameDetailBean.getFeatures() + "");

                if ("1".equals(gameDetailBean.getCollect_status())) {
                    game_detail_collection_icon.setImageResource(R.drawable.collection_select);
                    game_detail_collection_tv.setTextColor(getResources().getColor(R.color.font_yellow1));
                    game_detail_collection_tv.setText("已收藏");
                } else {
                    game_detail_collection_icon.setImageResource(R.drawable.collection_unselect);
                    game_detail_collection_tv.setTextColor(getResources().getColor(R.color.icon_gray));
                    game_detail_collection_tv.setText("收藏");
                }

                game_detail_introduction.setText(gameDetailBean.getIntroduction());
                initHorizontalSorollView(gameDetailBean);

                //给GameInf赋值，下载用到
                GameInfo gameInfo = new GameInfo();
                gameInfo.iconurl = bean.getIcon();
                gameInfo.name = bean.getGame_name();
                gameInfo.id = Integer.valueOf(bean.getId());
                gameInfo.features = bean.getFeatures();
                gameInfo.type = bean.getGame_type_name();
                gameInfo.playNum = bean.getPlay_num();
                gameInfo.GameUrl = bean.getPlay_url();
                gameInfo.fanli = bean.getRatio();
                gameInfo.canDownload = bean.getXia_status();
                gameInfo.sdk_version = Integer.valueOf(bean.getSdk_version());
                gameInfo.discount = bean.getDiscount();
                gameInfo.canDownload = bean.getXia_status();

                if (gameInfo.canDownload == 0) {
                    btnStart.setBackgroundResource(R.color.search_zi);
                    btnStart.setEnabled(false);
                } else {
                    btnStart.setBackgroundResource(R.color.zhuse_lan);
                    btnStart.setEnabled(true);
                    btnStart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (gameDetailBean != null) {
                                if (Utils.getLoginUser() != null) {
                                    xiazai();
                                } else {
                                    new DialogGoLogin(GameDetailActivity.this, R.style.MyDialogStyle, "暂时无法玩游戏哦~T_T~").show();
                                }
                            }
                        }
                    });
                }
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);
            }

            @Override
            protected void _onError() {
            }
        };
    }


    /**
     * 获取礼包
     */
    private void getGift() {
        intoGameGift.clear();
        Map<String, String> getGiftParams = new HashMap<>();
        getGiftParams.put("game_id", String.valueOf(game_id));
        getGiftParams.put("sdk_version", "1");
        if (null != Utils.getPersistentUserInfo()) {
            getGiftParams.put(" token", Utils.getPersistentUserInfo().token);
        }
        Type type = new TypeToken<HttpResult<List<IntoGameGiftBean>>>() {
        }.getType();
        new HttpUtils<List<IntoGameGiftBean>>(type, HttpConstant.API_GAME_SUBJECT_GAME_GIFT, getGiftParams, "手游游戏礼包返回的数据", true) {
            @Override
            protected void _onSuccess(List<IntoGameGiftBean> list, String msg) {
                if (list.size() > 0) {
                    llGameDetailContent.setVisibility(View.VISIBLE);
                    llGameDetailGiftContent.setVisibility(View.VISIBLE);
                    intoGameGift.addAll(list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GameDetailActivity.this);
                    rcGameDetailGiftList.setLayoutManager(linearLayoutManager);
                    gameDetailGiftRecyclerViewAdapter = new GameDetailGiftRecyclerViewAdapter(intoGameGift, GameDetailActivity.this);
                    rcGameDetailGiftList.setAdapter(gameDetailGiftRecyclerViewAdapter);
                } else {
                    llGameDetailGiftContent.setVisibility(View.GONE);
                    llGameDetailContent.setVisibility(View.GONE);
                }
            }

            @Override
            protected void _onError(String message, int code) {
                llGameDetailGiftContent.setVisibility(View.GONE);
                llGameDetailContent.setVisibility(View.GONE);
            }

            @Override
            protected void _onError() {
                llGameDetailGiftContent.setVisibility(View.GONE);
                llGameDetailContent.setVisibility(View.GONE);
            }
        };

    }

    /**
     * 领取礼包
     */
    private void getGiftRequest(int packageIndex) {
        if (null != Utils.getPersistentUserInfo()) {
            Map<String, String> giftParams = new HashMap<>();
            giftParams.put("gift_id", intoGameGift.get(packageIndex).getGift_id());
            giftParams.put("promote_id", new PromoteUtils().getPromoteId());
            Type type = new TypeToken<HttpResult<String>>() {
            }.getType();
            new HttpUtils<String>(type, HttpConstant.API_GIFT_GET, giftParams, "领取礼包返回数据", true) {
                @Override
                protected void _onSuccess(String code, String msg) {
                    XiangQing();
                    new DialogGetGiftSuccess(GameDetailActivity.this, R.style.MyDialogStyle, code).show();
                }

                @Override
                protected void _onError(String message, int code) {
                    if (code == 1117) {
                        DialogWeChatOfficialAccounts dialog = new DialogWeChatOfficialAccounts(GameDetailActivity.this, R.style.MyDialogStyle, message);
                        dialog.show();
                    } else {
                        new DialogGetGiftFailed(GameDetailActivity.this, R.style.MyDialogStyle, retryGetGiftHandler, message).show();
                    }
                }

                @Override
                protected void _onError() {
                    new DialogGetGiftFailed(GameDetailActivity.this, R.style.MyDialogStyle, retryGetGiftHandler, "网络缓慢").show();
                }
            };
        } else {
            new DialogGoLogin(GameDetailActivity.this, R.style.MyDialogStyle, "暂时无法领取礼包哦 ~T_T~").show();
        }
    }


    @SuppressLint("HandlerLeak")
    Handler retryGetGiftHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    getGiftRequest(giftPos);
                    break;
            }
        }
    };


    /**
     * 获取活动
     */
    private void getActivities() {
        Map<String, String> activitiesParams = new HashMap<>();
        activitiesParams.put("game_id", String.valueOf(game_id));

        Type type = new TypeToken<HttpResult<List<HomeGameActivitiesBean>>>() {
        }.getType();
        new HttpUtils<List<HomeGameActivitiesBean>>(type, HttpConstant.API_GAME_SUBJECT_GAME_ACTIVITIES_DETAIL, activitiesParams, "游戏详情（活动）", true) {
            @Override
            protected void _onSuccess(List<HomeGameActivitiesBean> list, String msg) {
                if (list.size() <= 0) {
                    llGameDetailActivitiesContent.setVisibility(View.GONE);
                    rcHomeGameActivitiesList.setVisibility(View.GONE);
                    rlActivitiesTitle.setVisibility(View.GONE);
                } else {
                    llGameDetailActivitiesContent.setVisibility(View.VISIBLE);
                    rcHomeGameActivitiesList.setVisibility(View.VISIBLE);
                    rlActivitiesTitle.setVisibility(View.VISIBLE);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(GameDetailActivity.this);
                    gameActivitiesRecyclerViewAdapter = new HomeGameActivitiesRecyclerViewAdapter(list, GameDetailActivity.this);
                    rcHomeGameActivitiesList.setLayoutManager(layoutManager);
                    rcHomeGameActivitiesList.addItemDecoration(new DividerItemDecoration(GameDetailActivity.this, DividerItemDecoration.VERTICAL));
                    rcHomeGameActivitiesList.setAdapter(gameActivitiesRecyclerViewAdapter);
                }
            }

            @Override
            protected void _onError(String message, int code) {
                Log.e(TAG, "_onError: " + message);
                llGameDetailActivitiesContent.setVisibility(View.GONE);
            }

            @Override
            protected void _onError() {
                llGameDetailActivitiesContent.setVisibility(View.GONE);
            }
        };
    }


    /**
     * 获取攻略
     */
    private void getPublish() {
        Map<String, String> hotGameParams = new HashMap<>();
        hotGameParams.put("game_id", String.valueOf(game_id));
        hotGameParams.put("is_gl", String.valueOf(1));
        Type type = new TypeToken<HttpResult<List<HomeGameActivitiesBean>>>() {
        }.getType();
        new HttpUtils<List<HomeGameActivitiesBean>>(type, HttpConstant.API_GAME_SUBJECT_GAME_ACTIVITIES_DETAIL, hotGameParams, "游戏详情获取攻略", false) {
            @Override
            protected void _onSuccess(List<HomeGameActivitiesBean> listData, String msg) {
                if (listData.size() <= 0) {
                    llGameDetailGongLueContent.setVisibility(View.GONE);
                } else {
                    llGameDetailGongLueContent.setVisibility(View.VISIBLE);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(GameDetailActivity.this);
                    gameDetGongLueRecyclerViewAdapter = new GameDetGongLueRecyclerViewAdapter(listData, GameDetailActivity.this);
                    rcHomeGameGongLueList.setLayoutManager(layoutManager);
                    rcHomeGameGongLueList.addItemDecoration(new DividerItemDecoration(GameDetailActivity.this, DividerItemDecoration.VERTICAL));
                    rcHomeGameGongLueList.setAdapter(gameDetGongLueRecyclerViewAdapter);
                }
            }

            @Override
            protected void _onError(String message, int code) {
                llGameDetailGongLueContent.setVisibility(View.GONE);
            }

            @Override
            protected void _onError() {
                ToastUtil.showToast("游戏详情网络异常");
                llGameDetailGongLueContent.setVisibility(View.GONE);
            }
        };

    }


    /**
     * 大家都在玩
     */
    private void PeoplePlay() {

        Type type = new TypeToken<HttpResult<List<GassBean>>>() {
        }.getType();
        new HttpUtils<List<GassBean>>(type, HttpConstant.API_GAME_SUBJECT_GUESS_YOU_PALY, null, "大家都在玩返回的数据", true) {
            @Override
            protected void _onSuccess(List<GassBean> list, String msg) {
                if (list.size() > 0) {
                    llGameDetailAllPeople.setVisibility(View.VISIBLE);
                    listAllPlay.clear();
                    listAllPlay.addAll(list);
                    allPlayAdapter.setList(listAllPlay);
                    Utils.setListViewHeightBasedOnChildren(allPlay, 4);
                } else {
                    llGameDetailAllPeople.setVisibility(View.GONE);
                    ToastUtil.showToast(" 大家都在玩数据为空");
                }
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);
            }

            @Override
            protected void _onError() {

            }
        };
    }


    private boolean isCollected;//用来标记是否收藏

    /**
     * 收藏游戏网络请求
     */
    private void ShouCang() {
        UserInfo userInfo = Utils.getLoginUser();
        if (userInfo != null) {
            Map<String, String> map = new HashMap<>();
            map.put("game_id", String.valueOf(game_id));
            map.put("token", userInfo.token);
            map.put("status", 1 + "");
            isCollected = true;
            gameCollect(map);
        } else {
            new DialogGoLogin(GameDetailActivity.this, R.style.MyDialogStyle, "暂时无法收藏心爱的游戏 ~T_T~").show();

        }
    }


    /**
     * 取消收藏
     */
    private void QuXiaoShouCang() {
        UserInfo userInfo = Utils.getLoginUser();
        if (userInfo != null) {
            HashMap<String, String> map = new HashMap<>();
            map.put("token", userInfo.token);
            map.put("game_id", String.valueOf(game_id));
            map.put("status", -1 + "");
            isCollected = false;
            gameCollect(map);
        }
    }

    /**
     * 收藏/取消收藏游戏
     */
    private void gameCollect(Map<String, String> map) {

        Type type = new TypeToken<HttpResult<String>>() {
        }.getType();
        new HttpUtils<String>(type, HttpConstant.API_GAME_COLLECT, map, "收藏/取消收藏游戏返回的数据", true) {
            @Override
            protected void _onSuccess(String s, String msg) {
                if (isCollected) {
                    new DialogCollectedSuccess(GameDetailActivity.this, R.style.MyDialogStyle, true, null).show();
                    game_detail_collection_icon.setImageResource(R.drawable.collection_select);
                    game_detail_collection_tv.setText("已收藏");
                } else {
                    new DialogCollectedSuccess(GameDetailActivity.this, R.style.MyDialogStyle, false, null).show();
                    game_detail_collection_icon.setImageResource(R.drawable.collection_unselect);
                    game_detail_collection_tv.setText("收藏");
                }
                XiangQing();

            }

            @Override
            protected void _onError(String message, int code) {

            }

            @Override
            protected void _onError() {

            }
        };
    }


    /**
     * 横向滑动的游戏截图
     */
    private boolean isFirst = true;

    private void initHorizontalSorollView(GameDetailBean gameDetailBean) {
        final ArrayList<String> imgs_urls = (ArrayList<String>) gameDetailBean.getScreenshots();
        try {
            if (isFirst) {
                for (int i = 0; i < gameDetailBean.getScreenshots().size(); i++) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-0x1, -0x1);
                    FilletImageView imageView = new FilletImageView(this);
                    imageView.setImageDrawable(this.getResources().getDrawable(R.drawable.default_picture));
                    layoutParams.height = Utils.dipToPx(this, 220.0f);
                    layoutParams.width = Utils.dipToPx(this, 125.0f);
                    if (i != 0) {
                        layoutParams.leftMargin = Utils.dipToPx(this, 8.0f);
                    }

                    imageView.setLayoutParams(layoutParams);
                    //imageView.setTag(Integer.valueOf(i));
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    String img_url = gameDetailBean.getScreenshots().get(i);

                    if (gameDetailBean.getScreenshots().size() <= 0 || TextUtils.isEmpty(img_url)) {
                        game_detai_splash_linearLayout.addView(imageView);
                    } else {
                        if (!TextUtils.isEmpty(img_url)) {
                            game_detai_splash_linearLayout.addView(imageView);
                            Glide.with(GameDetailActivity.this).load(img_url).into(imageView);
                        }
                    }

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (imgs_urls.size() >= 1) {
                                Intent intent = new Intent();
                                intent.putStringArrayListExtra("imgs_urls", imgs_urls);
                                intent.setClass(GameDetailActivity.this, ScreenActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
                isFirst = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 登录监听
     */
    private void regsiterPersonalBroadcast() {
        storeBroadcast = new StoreBroadcast();
        IntentFilter intentFilter = new IntentFilter("com.yinu.login");
        LocalBroadcastManager.getInstance(this).registerReceiver(storeBroadcast, intentFilter);
    }

    private class StoreBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("login_status", -1)) {
                case DialogLoginActivity.EVENT_LOGIN_SUCCESS:
                    XiangQing();
                    PeoplePlay();
                    getGift();
                    getActivities();
                    break;
                case DialogLoginActivity.EVENT_LOGIN_FILED:
                    break;
                case DialogLoginActivity.EVENT_LOGIN_EXCIT:
                    break;
            }
        }

    }


    /**
     * 下载按钮的执行动作
     */
    public void xiazai() {
        down = DownManager.getInstance().getDownBean(Integer.parseInt(gameDetailBean.id));
        Log.e(TAG, "xiazai: " + percent);
        switch (percent) {
            //未下载
            case -2:
                if (suo) {
                    suo = false;
                    Utils.getDownLoadUrl(handler, game_id);
                    btnStart.setText("等待");
                }
                break;
            //下载中
            case 4:
                Aria.download(this).load(down.DownUrl).stop();
                break;
            //暂停中
            case 2:
                DownManager.getInstance().down(down);
                break;
            //下载成功
            case 1:
                DownManager.getInstance().install(down);
                break;
            //安装成功
            case 8:
                DownDataBean open = DownManager.getInstance().open(down);
                Status(open.btnStatus);
                break;
            //下载失败
            case 0:
                DownManager.getInstance().down(down);
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            btnStart.setText("下载游戏");
        }
    };

    /**
     * 获得下载链接
     */
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //解析网络请求 获取游戏链接
                    DataBean dataBean = HttpUtils.DNSdownUrl(msg.obj.toString());
                    if (dataBean != null && !dataBean.url.equals("")) {
                        DownDataBean downDataBean = new DownDataBean();
                        downDataBean.id = game_id;
                        downDataBean.DownUrl = dataBean.url;
                        DownManager.getInstance().down(downDataBean);

                        DownManager.getInstance().copy2(gameDetailBean, dataBean.record_id);
                        mHandler.sendEmptyMessageDelayed(1, HttpConstant.Time);
                    } else {
                        Utils.TS("游戏链接为空");
                        btnStart.setText("下载游戏");
                    }
                    suo = true;
                    break;
                case 2:
                    Utils.TS("获取下载链接失败");
                    btnStart.setText("下载游戏");
                    suo = true;
                    break;
            }
        }
    };

    /**
     * 刷新状态
     *
     * @param btnStatus
     */
    public void Status(int btnStatus) {
        switch (btnStatus) {
            //未下载状态
            case -2:
                btnStart.setText("下载游戏");
                break;
            //等待状态
            case 3:
                btnStart.setText("等待");
                break;
            //下载完成未安装状态
            case 1:
                Log.e("下载状态", "----下载完成未安装状态");
                percent = 1;
                btnStart.setText("安装");
                break;
            //已安装打开状态
            case 8:
                Log.e("下载状态", "----已安装打开状态");
                percent = 8;
                btnStart.setText("打开");
                break;
            //下载失败状态
            case 0:
                btnStart.setText("重试");
                break;
            //下载暂停状态
            case 2:
                btnStart.setText("暂停中，点击可继续下载");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //判断是否安装
        if (percent == 1 || percent == 8) {
            DownDataBean downDataBean = DownManager.getInstance().RealState(game_id);
            if (downDataBean != null) {
                Status(downDataBean.btnStatus);
            }
        }
        regsiterPersonalBroadcast();
    }


    @Download.onWait
    void taskWait(DownloadTask task) {
        if (down == null) {
            down = DownManager.getInstance().getDownBean(game_id);
        }
        if (down != null && task.getKey().equals(down.DownUrl)) {
            percent = 3;
            btnStart.setText("等待");
        }
    }

    @Download.onTaskStop
    void taskStop(DownloadTask task) {
        if (down == null) {
            down = DownManager.getInstance().getDownBean(game_id);
        }
        if (down != null && task.getKey().equals(down.DownUrl)) {
            mHandler.removeMessages(1);
            percent = 2;
            btnStart.setText("暂停中，点击可继续下载");
        }
    }

    @Download.onTaskRunning
    void taskRuning(DownloadTask task) {
        Log.e(TAG, "taskRuning: xia  在中");
        if (down == null) {
            down = DownManager.getInstance().getDownBean(game_id);
        }
        if (down != null && task.getKey().equals(down.DownUrl)) {
            mHandler.removeMessages(1);
            percent = 4;
            btnStart.setText("已下载" + task.getPercent() + "%");
        }
    }

    @Download.onTaskCancel
    void taskCancel(DownloadTask task) {
        if (down == null) {
            down = DownManager.getInstance().getDownBean(game_id);
        }
        if (down != null && task.getKey().equals(down.DownUrl)) {
            mHandler.removeMessages(1);
            btnStart.setText("下载游戏");
        }
    }

    @Download.onTaskFail
    void taskFail(DownloadTask task) {
        if (down == null) {
            down = DownManager.getInstance().getDownBean(game_id);
        }
        if (down != null && task.getKey().equals(down.DownUrl)) {
            mHandler.removeMessages(1);
            percent = 0;
            btnStart.setText("重试");
            ToastUtil.showToast("下载链接有误");
            DownManager.getInstance().setState(game_id);

            try {
                Aria.download(this).load(down.DownUrl).cancel();//删除下载数据记录
                DatabaseOpenHelper.getInstance().deleteById(DownDataBean.class, down.id);//删除数据库记录
                DownManager.getInstance().Delete(down.id);//删除本地下载文件    如果有则删除
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        DownDataBean downDataBean = DownManager.getInstance().RealState(game_id);
        if (downDataBean != null) {
            mHandler.removeMessages(1);
            Status(downDataBean.btnStatus);
        }
    }
}
