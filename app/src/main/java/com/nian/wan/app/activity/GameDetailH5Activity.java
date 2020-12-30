package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.adapter.AllPlayAdapter;
import com.nian.wan.app.adapter.GameDetGongLueRecyclerViewAdapter;
import com.nian.wan.app.adapter.GameDetailGiftRecyclerViewAdapter;
import com.nian.wan.app.adapter.GassAdapter;
import com.nian.wan.app.adapter.HomeGameActivitiesRecyclerViewAdapter;
import com.nian.wan.app.bean.GameDetailBean;
import com.nian.wan.app.bean.GassBean;
import com.nian.wan.app.bean.GetGiftBean;
import com.nian.wan.app.bean.HomeGameActivitiesBean;
import com.nian.wan.app.bean.IntoGameGiftBean;
import com.nian.wan.app.bean.LingQuGiftBean;
import com.nian.wan.app.bean.ShouCangBean;
import com.nian.wan.app.bean.StartGameBean;
import com.nian.wan.app.bean.UserInfo;
import com.nian.wan.app.fragment.PersonalCenter;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.PromoteUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogCollectedSuccess;
import com.nian.wan.app.view.DialogGetGiftFailed;
import com.nian.wan.app.view.DialogGetGiftSuccess;
import com.nian.wan.app.view.DialogGoLogin;
import com.nian.wan.app.view.DialogWeChatOfficialAccounts;
import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.MCUtils;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.FilletImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Description:H5游戏详情
 */
public class GameDetailH5Activity extends BaseFragmentActivity {

    private static final String TAG = "GameDetailH5Activity";
    @BindView(R.id.tou)
    ImageView tou;

    @BindView(R.id.back)
    RelativeLayout back;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.xingxing)
    ImageView xingxing;

    @BindView(R.id.tv_shoucang)
    TextView tvShoucang;

    @BindView(R.id.collection)
    RelativeLayout collection;

    @BindView(R.id.nameww)
    TextView nameww;

    @BindView(R.id.leixing)
    TextView leixing;

    @BindView(R.id.zaiwan)
    TextView zaiwan;

    @BindView(R.id.shoucang)
    TextView shoucang;

    @BindView(R.id.yijuhua)
    TextView yijuhua;

    @BindView(R.id.gameInfo_ImagesLayout)
    LinearLayout gameInfoImagesLayout;

    @BindView(R.id.gameInfo_HorizontalScrollView)
    HorizontalScrollView gameInfoHorizontalScrollView;

    @BindView(R.id.tv_jieshao)
    TextView tvJieshao;

    @BindView(R.id.btn_more)
    LinearLayout btnMore;

    @BindView(R.id.gass_more)
    LinearLayout gassMore;

//    @BindView(R.id.yougass_like)
//    GridView yougassLike;

    @BindView(R.id.bl)
    ImageView bl;

    @BindView(R.id.allplay_more)
    LinearLayout allplayMore;

    @BindView(R.id.all_play)
    GridView allPlay;

    @BindView(R.id.btn_toPlay)
    TextView btnToPlay;

    @BindView(R.id.tvhint)
    TextView libao1;

    @BindView(R.id.rc_home_game_activities_list)
    RecyclerView rcHomeGameActivitiesList;
    @BindView(R.id.rl_activities_title)
    RelativeLayout rlActivitiesTitle;
    @BindView(R.id.rc_game_detail_gift_list)
    RecyclerView rcGameDetailGiftList;
    @BindView(R.id.ll_game_detail_content)
    LinearLayout llGameDatailContent;
    @BindView(R.id.ll_game_detail_gift_content)
    LinearLayout llGameDetailGiftContent;
    @BindView(R.id.ll_game_detail_activities_content)
    LinearLayout llGameDetailActivitiesContent;
    @BindView(R.id.ll_game_detail_all_people)
    LinearLayout llGameDetailAllPeople;
    @BindView(R.id.tv_fanli)
    TextView tvFanli;

    @BindView(R.id.ll_game_detail_gonglue_content)
    LinearLayout llGameDetailGongLueContent;
    @BindView(R.id.rc_home_game_gonglue_list)
    RecyclerView rcHomeGameGongLueList;

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


    @Override
    public void initView() {
        setContentView(R.layout.activity_gamedet_h5);
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
                    intent.setClass(GameDetailH5Activity.this, GameDetailH5Activity.class);
                } else {
                    intent.setClass(GameDetailH5Activity.this, GameDetailActivity.class);
                }
                startActivity(intent);
            }
        });
        lingqubean = new LingQuGiftBean();
        gameDetailBean = new GameDetailBean();
        startGameBean = new StartGameBean();
        shouCangBean = new ShouCangBean();
        gassBean = new GassBean();

        XiangQing();
        PeoplePlay();
        getGift();
        getPublish();
        getActivities();
    }


    @OnClick({R.id.back, R.id.collection, R.id.allplay_more, R.id.btn_toPlay, R.id.btn_more})
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
                recommednGameIntent.putExtra("change_status", PersonalCenter.ACTION_GO_HOT_GAME);
                LocalBroadcastManager.getInstance(this).sendBroadcast(recommednGameIntent);
                finish();
                break;
            case R.id.btn_toPlay:
                if (null != Utils.getPersistentUserInfo()) {
                    StartGame();
                } else {
                    new DialogGoLogin(this, R.style.MyDialogStyle, "暂时无法玩游戏哦~T_T~").show();
                }
                break;
            case R.id.btn_more:
                UserInfo userInfo = Utils.getLoginUser();
                if (userInfo != null) {
                    Intent itGiftDet = new Intent(GameDetailH5Activity.this, GiftDetActivity.class);
                    itGiftDet.putExtra("game_id", game_id);
                    startActivity(itGiftDet);
                } else {
                    DialogLoginActivity loginActivity = new DialogLoginActivity(GameDetailH5Activity.this, "LGOIN");
                    FragmentTransaction transaction = GameDetailH5Activity.this.getFragmentManager().beginTransaction();
                    transaction.add(loginActivity, "WoDe");
                    transaction.show(loginActivity);
                    transaction.commitAllowingStateLoss();
                }
                break;
        }
    }


    /**
     * 游戏详情
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
                if (!TextUtils.isEmpty(gameDetailBean.getIcon())) {
                    Utils.Fill(icon, gameDetailBean.getIcon());
                }
                title.setText(gameDetailBean.getGame_name());
                if (gameDetailBean.getGame_name().length() > 12) {
                    String s = gameDetailBean.getGame_name().substring(0, 12);
                    nameww.setText(s + "...");
                } else {
                    nameww.setText(gameDetailBean.getGame_name());
                }
                leixing.setText("(" + gameDetailBean.getGame_type_name() + ")" + "");
                zaiwan.setText(gameDetailBean.getPlay_num() + "");
                shoucang.setText(String.valueOf(gameDetailBean.getCollect_num()));
                yijuhua.setText(gameDetailBean.getFeatures() + "");

                if ("1".equals(gameDetailBean.getCollect_status())) {
                    xingxing.setImageResource(R.drawable.collection_select);
                    tvShoucang.setTextColor(getResources().getColor(R.color.font_yellow1));
                    tvShoucang.setText("已收藏");
                } else {
                    xingxing.setImageResource(R.drawable.collection_unselect);
                    tvShoucang.setTextColor(getResources().getColor(R.color.gray2));
                    tvShoucang.setText("收藏");
                }
                tvJieshao.setText(gameDetailBean.getIntroduction());
                initHorizontalSorollView(gameDetailBean);
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
        Map<String, String> getGiftParams = new HashMap<>();
        getGiftParams.put("game_id", String.valueOf(game_id));
        getGiftParams.put("sdk_version", "1");
        if (null != Utils.getPersistentUserInfo()) {
            getGiftParams.put(" token", Utils.getPersistentUserInfo().token);
        }
        Type type = new TypeToken<HttpResult<List<IntoGameGiftBean>>>() {
        }.getType();
        new HttpUtils<List<IntoGameGiftBean>>(type, HttpConstant.API_GAME_SUBJECT_GAME_GIFT, getGiftParams, "H5游戏礼包返回的数据", true) {
            @Override
            protected void _onSuccess(List<IntoGameGiftBean> list, String msg) {
                if (list.size() > 0) {
                    llGameDatailContent.setVisibility(View.VISIBLE);
                    llGameDetailGiftContent.setVisibility(View.VISIBLE);
                    intoGameGift.addAll(list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GameDetailH5Activity.this);
                    rcGameDetailGiftList.setLayoutManager(linearLayoutManager);
                    gameDetailGiftRecyclerViewAdapter = new GameDetailGiftRecyclerViewAdapter(intoGameGift, GameDetailH5Activity.this);
                    rcGameDetailGiftList.setAdapter(gameDetailGiftRecyclerViewAdapter);
                } else {
                    llGameDetailGiftContent.setVisibility(View.GONE);
                    llGameDatailContent.setVisibility(View.GONE);
                }
            }

            @Override
            protected void _onError(String message, int code) {
                llGameDetailGiftContent.setVisibility(View.GONE);
                llGameDatailContent.setVisibility(View.GONE);
            }

            @Override
            protected void _onError() {
                llGameDetailGiftContent.setVisibility(View.GONE);
                llGameDatailContent.setVisibility(View.GONE);
            }
        };

    }

    /**
     * 领取礼包
     *
     * @param packageIndex
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
                    new DialogGetGiftSuccess(GameDetailH5Activity.this, R.style.MyDialogStyle, code).show();
                }

                @Override
                protected void _onError(String message, int code) {
                    if (code == 1117) {
                        DialogWeChatOfficialAccounts dialog = new DialogWeChatOfficialAccounts(GameDetailH5Activity.this, R.style.MyDialogStyle, message);
                        dialog.show();
                    } else {
                        new DialogGetGiftFailed(GameDetailH5Activity.this, R.style.MyDialogStyle, retryGetGiftHandler, message).show();
                    }
                }

                @Override
                protected void _onError() {
                    new DialogGetGiftFailed(GameDetailH5Activity.this, R.style.MyDialogStyle, retryGetGiftHandler, "网络缓慢").show();
                }
            };
        } else {
            new DialogGoLogin(GameDetailH5Activity.this, R.style.MyDialogStyle, "暂时无法领取礼包哦 ~T_T~").show();
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
     * 开始游戏
     */
    private void StartGame() {
    }


    /**
     * 获取活动
     */
    private void getActivities() {
        Map<String, String> activitiesParams = new HashMap<>();
        activitiesParams.put("game_id", String.valueOf(game_id));
        activitiesParams.put("is_gl", String.valueOf(0));

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
                    LinearLayoutManager layoutManager = new LinearLayoutManager(GameDetailH5Activity.this);
                    gameActivitiesRecyclerViewAdapter = new HomeGameActivitiesRecyclerViewAdapter(list, GameDetailH5Activity.this);
                    rcHomeGameActivitiesList.setLayoutManager(layoutManager);
                    rcHomeGameActivitiesList.addItemDecoration(new DividerItemDecoration(GameDetailH5Activity.this, DividerItemDecoration.VERTICAL));
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
                    LinearLayoutManager layoutManager = new LinearLayoutManager(GameDetailH5Activity.this);
                    gameDetGongLueRecyclerViewAdapter = new GameDetGongLueRecyclerViewAdapter(listData, GameDetailH5Activity.this);
                    rcHomeGameGongLueList.setLayoutManager(layoutManager);
                    rcHomeGameGongLueList.addItemDecoration(new DividerItemDecoration(GameDetailH5Activity.this, DividerItemDecoration.VERTICAL));
                    rcHomeGameGongLueList.setAdapter(gameDetGongLueRecyclerViewAdapter);
                }
            }

            @Override
            protected void _onError(String message, int code) {
                llGameDetailGongLueContent.setVisibility(View.GONE);
            }

            @Override
            protected void _onError() {
                llGameDetailGongLueContent.setVisibility(View.GONE);
                //ToastUtil.showToast("游戏详情网络异常");
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
                llGameDetailAllPeople.setVisibility(View.GONE);
            }

            @Override
            protected void _onError() {
                llGameDetailAllPeople.setVisibility(View.GONE);
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
            new DialogGoLogin(GameDetailH5Activity.this, R.style.MyDialogStyle, "暂时无法收藏心爱的游戏 ~T_T~").show();
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
     *
     * @param map
     */
    private void gameCollect(Map<String, String> map) {

        Type type = new TypeToken<HttpResult<String>>() {
        }.getType();
        new HttpUtils<String>(type, HttpConstant.API_GAME_COLLECT, map, "收藏/取消收藏游戏返回的数据", true) {
            @Override
            protected void _onSuccess(String s, String msg) {
                if (isCollected) {
                    new DialogCollectedSuccess(GameDetailH5Activity.this, R.style.MyDialogStyle, true, null).show();
                    xingxing.setImageResource(R.drawable.collection_select);
                    tvShoucang.setText("已收藏");
                } else {
                    new DialogCollectedSuccess(GameDetailH5Activity.this, R.style.MyDialogStyle, false, null).show();
                    xingxing.setImageResource(R.drawable.collection_unselect);
                    tvShoucang.setText("收藏");
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
     *
     * @param gameDetailBean
     */
    private boolean isFirst = true;

    private void initHorizontalSorollView(GameDetailBean gameDetailBean) {
        final ArrayList<String> imgs_urls = (ArrayList<String>) gameDetailBean.getScreenshots();
        try {
            if (isFirst) {
                for (int i = 0; i < gameDetailBean.getScreenshots().size(); i++) {
                    LinearLayout.LayoutParams params = new LinearLayout
                            .LayoutParams(-0x1, -0x1);
                    FilletImageView img = new FilletImageView(this);
                    img.setImageDrawable(this.getResources().getDrawable(R.drawable.default_picture));
                    params.height = Utils.dipToPx(this, 200.0f);
                    params.width = Utils.dipToPx(this, 130.0f);
                    if (i != 0) {
                        params.leftMargin = Utils.dipToPx(this, 8.0f);
                    }
                    img.setLayoutParams(params);
                    img.setTag(Integer.valueOf(i));
                    img.setScaleType(ImageView.ScaleType.FIT_XY);
                    final String imgs = gameDetailBean.getScreenshots().get(i);
                    if (gameDetailBean.getScreenshots().size() <= 0 ||
                            TextUtils.isEmpty(imgs)) {
                        gameInfoImagesLayout.addView(img);
                    } else {
                        if (!TextUtils.isEmpty(imgs)) {
                            MCUtils.fillImage(img, imgs);
                            gameInfoImagesLayout.addView(img);
                        }
                    }
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (imgs_urls.size() >= 1) {
                                Intent intent = new Intent();
                                intent.putStringArrayListExtra("imgs_urls", imgs_urls);
                                intent.setClass(GameDetailH5Activity.this, ScreenActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
                isFirst = false;
            }
        } catch (Exception e) {
            Log.e("空指针了", e.toString());
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
//                    ToastUtil.showToast("登陆失败");
                    break;
                case DialogLoginActivity.EVENT_LOGIN_EXCIT:
                    break;
            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        regsiterPersonalBroadcast();
    }
}
