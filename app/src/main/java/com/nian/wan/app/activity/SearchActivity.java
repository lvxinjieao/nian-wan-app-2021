package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.reflect.TypeToken;
import com.nian.wan.app.R;
import com.nian.wan.app.adapter.SearchActivitiesRecyclerViewAdapter;
import com.nian.wan.app.adapter.SearchGameRecyclerViewAdapter;
import com.nian.wan.app.adapter.SearchGiftRecyclerViewAdapter;
import com.nian.wan.app.adapter.SearchHotRecyclerViewAdapter;
import com.nian.wan.app.adapter.SearchMiniProgramRecyclerViewAdapter;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.bean.GlobalSearchBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.PromoteUtils;
import com.nian.wan.app.utils.Utils;

import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

;

/**
 * @Description: 全局搜索
 */
public class SearchActivity extends BaseFragmentActivity {

    @BindView(R.id.tou)
    ImageView tou;

    @BindView(R.id.edt_search_search_text)
    EditText edtSearchSearchText;

    @BindView(R.id.ll_search_input)
    LinearLayout llSearchInput;

    @BindView(R.id.tv_search_cancel)
    TextView tvSearchCancel;

    @BindView(R.id.ll_unsearch_content)
    LinearLayout llUnsearchContent;

    @BindView(R.id.ll_search_none)
    LinearLayout llSearchNone;

    @BindView(R.id.rc_search_game)
    RecyclerView rcSearchGame;


    @BindView(R.id.rl_search_more_game)
    RelativeLayout rlSearchMoreGame;

    @BindView(R.id.ll_search_game_content)
    LinearLayout llSearchGameContent;

    @BindView(R.id.rc_search_gift)
    RecyclerView rcSearchGift;

    @BindView(R.id.rl_search_more_gift)
    RelativeLayout rlSearchMoreGift;

    @BindView(R.id.ll_search_gift_content)
    LinearLayout llSearchGiftContent;

    @BindView(R.id.rc_search_activities)
    RecyclerView rcSearchActivities;

    @BindView(R.id.rl_search_more_activities)
    RelativeLayout rlSearchMoreActivities;

    @BindView(R.id.ll_search_activities_content)
    LinearLayout llSearchActivitiesContent;

    @BindView(R.id.ll_search_has_data_content)
    LinearLayout llSearchHasDataContent;

    @BindView(R.id.tv_search_change_collection)
    TextView tvSearchChangeCollection;

    @BindView(R.id.rc_search_hot)
    RecyclerView rcSearchHot;

    //    @BindView(R.id.tl_search_cloud_tag)
    //    TagFlowLayout tlSearchTag;

    @BindView(R.id.flexboxLayout)
    FlexboxLayout flexboxLayout;

    @BindView(R.id.v_search_line_first)
    View vSearchLineFirst;

    @BindView(R.id.v_search_line_second)
    View vSearchLineSecond;

    @BindView(R.id.v_search_line_third)
    View vSearchLineThird;

    @BindView(R.id.rc_search_mini)
    RecyclerView rcSearchMini;

    @BindView(R.id.rl_search_more_mini)
    RelativeLayout rlSearchMoreMini;

    @BindView(R.id.ll_search_mini_content)
    LinearLayout llSearchMiniContent;


    private GlobalSearchBean globalSearchBean;

    private SearchGameRecyclerViewAdapter gameRecyclerViewAdapter;

    private SearchGiftRecyclerViewAdapter giftRecyclerViewAdapter;

    private SearchActivitiesRecyclerViewAdapter activitiesRecyclerViewAdapter;

    private SearchHotRecyclerViewAdapter searchHotRecyclerViewAdapter;

    private SearchMiniProgramRecyclerViewAdapter miniProgramRecyclerViewAdapter;

    private ArrayList<GameInfo> searchHotGame;

    private int pageNumber = 0;

    private ArrayList<GameInfo> appInf;

    private List<GameInfo> listGames;


    @Override
    public void initView() {
        setContentView(R.layout.search_collection);
        ButterKnife.bind(this);

        Utils.initActionBarPosition(this, tou);

        Aria.download(this).register();
        listGames = new ArrayList<>();
        justNowInto(false);

        edtSearchSearchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 && edtSearchSearchText.isFocused()) {
                    llSearchHasDataContent.setVisibility(View.VISIBLE);
                    llUnsearchContent.setVisibility(View.GONE);
                    //搜索
                    globalSearch(edtSearchSearchText.getText().toString());
                    edtSearchSearchText.setSelection(edtSearchSearchText.getText().toString().length());
                }
                if (editable.length() <= 0) {
                    llSearchHasDataContent.setVisibility(View.GONE);
                    llUnsearchContent.setVisibility(View.VISIBLE);
                    llSearchNone.setVisibility(View.GONE);
                }
            }

        });
    }


    @OnClick({R.id.tv_search_cancel, R.id.rl_search_more_game, R.id.rl_search_more_gift,R.id.rl_search_more_mini,
            R.id.rl_search_more_activities, R.id.tv_search_change_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search_cancel:
                this.finish();
                break;
            //更多游戏
            case R.id.rl_search_more_game:
                rlSearchMoreGame.setVisibility(View.GONE);
                gameRecyclerViewAdapter.setXian(false);
                break;
            //更多礼包
            case R.id.rl_search_more_gift:
                rlSearchMoreGift.setVisibility(View.GONE);
                giftRecyclerViewAdapter.setXian(false);
                break;
            //更多活动
            case R.id.rl_search_more_activities:
                rlSearchMoreActivities.setVisibility(View.GONE);
                activitiesRecyclerViewAdapter.setXian(false);
                break;
            //更多小程序
            case R.id.rl_search_more_mini:
                rlSearchMoreMini.setVisibility(View.GONE);
                miniProgramRecyclerViewAdapter.getMore(true);
                break;
            //热门推荐、换一换
            case R.id.tv_search_change_collection:
                ++pageNumber;
//                flexboxLayout.removeAllViews();
                justNowInto(true);
                break;
        }
    }


    /**
     * 初始化SearchActivity加载的数据
     */
    public void justNowInto(boolean isHuanyihuan) {
        searchHotGame = new ArrayList<>();
        Map<String, String> justNowIntoParams1 = new HashMap<>();
        justNowIntoParams1.put("type", "1");
        justNowIntoParams1.put("p", String.valueOf(pageNumber));
        justNowIntoParams1.put("sdk_version", "1");
        HttpConstant.POST(justNowIntoHandler, HttpConstant.API_PERSONAL_HOT_GAME, justNowIntoParams1, true);      //游戏

        if (!isHuanyihuan){  //点击换一换时不执行切 搜索热词
            Map<String, String> justNowIntoParams = new HashMap<>();
            justNowIntoParams.put("rec_status", "2");
            justNowIntoParams.put("p", "1");
            justNowIntoParams.put("limit", "9");
            justNowIntoParams.put("sdk_version", "3");
            HttpConstant.POST(justNowIntoHandler1, HttpConstant.API_HOME_GAME, justNowIntoParams, true);
        }
    }


    @SuppressLint("HandlerLeak")
    Handler justNowIntoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        ArrayList<GameInfo> appInf = HttpUtils.SearchHotGame(msg.obj.toString());
                        if (appInf != null && appInf.size() > 0) {
                            searchHotGame.addAll(appInf);
                            searchHotRecyclerViewAdapter = new SearchHotRecyclerViewAdapter(SearchActivity.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
                            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            rcSearchHot.setLayoutManager(linearLayoutManager);
                            rcSearchHot.setAdapter(searchHotRecyclerViewAdapter);
                            searchHotRecyclerViewAdapter.setSearchHotGame(searchHotGame);
                        }
                    } catch (Exception e) {
                        Log.e("搜索页面热门推荐异常",e.toString());
                    }
                    break;
                default:
                    break;
            }
        }
    };


    @SuppressLint("HandlerLeak")
    Handler justNowIntoHandler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Log.e("大家都在搜索", msg.obj.toString());
                    try {
                        appInf = HttpUtils.DNSJinp1(msg.obj.toString());
                        if (appInf != null && appInf.size() != 0) {
                            setCloudTag();
                        }
                    } catch (Exception e) {
                        Log.e("大家都在搜索异常", e.toString());
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 设置搜索热词
     */
    public void setCloudTag() {
        if (appInf.size() > 0) {
            for (int i = 0; i < appInf.size(); i++) {
                final TextView textView = new TextView(x.app());
                final String name = appInf.get(i).name;
                textView.setText(name);
                textView.setBackgroundResource(R.drawable.zhi_jiao_gray);
                textView.setTextColor(x.app().getResources().getColor(R.color.zi_hui));
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(Utils.dip2px(6));
                int padding = Utils.dpToPixel(this, 5);
                int paddingLeftAndRight = Utils.dpToPixel(this, 15);
                ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
                FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 15, 15, 0);
                textView.setLayoutParams(layoutParams);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { edtSearchSearchText.setText(name);
                    }
                });
                flexboxLayout.addView(textView);
            }
            appInf.clear();
        }

    }


    /**
     * 全局搜索
     *
     * @param params
     */
    public void globalSearch(String params) {
        Map<String, String> searchParams = new HashMap<>();
        Type type = new TypeToken<HttpResult<GlobalSearchBean>>() {}.getType();

        if (!TextUtils.isEmpty(params)) {
            searchParams.put("keyword", params.trim());
        }
            searchParams.put("sdk_version", "1");

        new HttpUtils<GlobalSearchBean>(type, HttpConstant.API_GLOBAL_SEARCH, searchParams, "全局搜索", true) {

            @Override
            protected void _onSuccess(GlobalSearchBean bean, String msg) {
                globalSearchBean = bean;

                //如果返回数据 游戏 数量不为0,填充适配器、更新UI
                if (0 != globalSearchBean.getGame().size()) {
                    llSearchGameContent.setVisibility(View.VISIBLE);
                    llSearchNone.setVisibility(View.GONE);
                    listGames.clear();
                    
                    for (int i=0;i<globalSearchBean.getGame().size();i++){
                        GameInfo gameInfo = new GameInfo();
                        gameInfo.iconurl = globalSearchBean.getGame().get(i).getIcon();//icon
                        gameInfo.bg_img = globalSearchBean.getGame().get(i).getCover();//背景图片
                        gameInfo.name = globalSearchBean.getGame().get(i).getGame_name();//游戏名
                        gameInfo.id = Integer.valueOf(globalSearchBean.getGame().get(i).getId());//游戏id
                        gameInfo.features = globalSearchBean.getGame().get(i).getFeatures();//列别
                        gameInfo.type = globalSearchBean.getGame().get(i).getGame_type_name();
                        gameInfo.playNum = String.valueOf(globalSearchBean.getGame().get(i).getPlay_num());//人数
                        gameInfo.GameUrl = globalSearchBean.getGame().get(i).getPlay_url();//h5游戏地址
                        gameInfo.fanli = globalSearchBean.getGame().get(i).getRatio();//返利
                        gameInfo.sdk_version = globalSearchBean.getGame().get(i).getSdk_version();//版本
                        gameInfo.canDownload = globalSearchBean.getGame().get(i).getXia_status();//下载
                        gameInfo.size = globalSearchBean.getGame().get(i).getGame_size();//游戏大小
                        listGames.add(gameInfo);
                    }

                    gameRecyclerViewAdapter = new SearchGameRecyclerViewAdapter(listGames, SearchActivity.this);



                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
                    rcSearchGame.setLayoutManager(linearLayoutManager);
                    rcSearchGame.setAdapter(gameRecyclerViewAdapter);
                    if (globalSearchBean.getGame().size() <= 3) {
                        rlSearchMoreGame.setVisibility(View.GONE);
                    } else {
                        rlSearchMoreGame.setVisibility(View.VISIBLE);
                    }

                } else {
                    llSearchGameContent.setVisibility(View.GONE);
                }

                //如果返回数据 礼包 数量不为0,填充适配器、更新
                if (0 != globalSearchBean.getGift().size()) {
                    llSearchGiftContent.setVisibility(View.VISIBLE);
                    llSearchNone.setVisibility(View.GONE);
                    giftRecyclerViewAdapter = new SearchGiftRecyclerViewAdapter(globalSearchBean, SearchActivity.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
                    rcSearchGift.setLayoutManager(linearLayoutManager);
                    rcSearchGift.setAdapter(giftRecyclerViewAdapter);
                    if (globalSearchBean.getGift().size() <= 3) {
                        rlSearchMoreGift.setVisibility(View.GONE);
                    } else {
                        rlSearchMoreGift.setVisibility(View.VISIBLE);
                    }
                } else {
                    llSearchGiftContent.setVisibility(View.GONE);
                }

                //如果返回数据 活动 数量不为0,填充适配器、更新
                if (0 != globalSearchBean.getArticle().size()) {
                    llSearchActivitiesContent.setVisibility(View.VISIBLE);
                    llSearchNone.setVisibility(View.GONE);
                    activitiesRecyclerViewAdapter = new SearchActivitiesRecyclerViewAdapter(globalSearchBean, SearchActivity.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
                    rcSearchActivities.setLayoutManager(linearLayoutManager);
                    rcSearchActivities.setAdapter(activitiesRecyclerViewAdapter);
                    if (globalSearchBean.getArticle().size() <= 3) {
                        rlSearchMoreActivities.setVisibility(View.GONE);
                    } else {
                        rlSearchMoreActivities.setVisibility(View.VISIBLE);
                    }
                } else {
                    llSearchActivitiesContent.setVisibility(View.GONE);
                }

                //如果返回数据 小程序 数量不为0,填充适配器、更新
                if (new PromoteUtils().getPromoteId().equals("0") && 0 != globalSearchBean.getSamllgame().size()) {
                    llSearchMiniContent.setVisibility(View.VISIBLE);
                    llSearchNone.setVisibility(View.GONE);
                    miniProgramRecyclerViewAdapter = new SearchMiniProgramRecyclerViewAdapter(globalSearchBean,SearchActivity.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
                    rcSearchMini.setLayoutManager(linearLayoutManager);
                    rcSearchMini.setAdapter(miniProgramRecyclerViewAdapter);
                    miniProgramRecyclerViewAdapter.notifyDataSetChanged();
                    if (globalSearchBean.getSamllgame().size() <= 3) {
                        rlSearchMoreMini.setVisibility(View.GONE);
                    } else {
                        rlSearchMoreMini.setVisibility(View.VISIBLE);
                    }
                } else {
                    llSearchMiniContent.setVisibility(View.GONE);
                }

                //如果搜索返回数据数量
                if (0 == globalSearchBean.getArticle().size() &&
                        0 == globalSearchBean.getGift().size() &&
                        0 == globalSearchBean.getGame().size() &&
                        0 == globalSearchBean.getSamllgame().size()) {
                    llSearchNone.setVisibility(View.VISIBLE);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        if(listGames.size()!=0){
            gameRecyclerViewAdapter.ConfirmationState();
        }
    }

    @Download.onWait void taskWait(DownloadTask task){
        gameRecyclerViewAdapter.taskWait(task,task.getKey());
    }

    @Download.onTaskStop void taskStop(DownloadTask task) {
        gameRecyclerViewAdapter.taskStop(task,task.getKey());
    }
    @Download.onTaskRunning void taskRuning(DownloadTask task) {
        gameRecyclerViewAdapter.taskRuning(task,task.getKey());
    }

    @Download.onTaskCancel void taskCancel(DownloadTask task) {
        gameRecyclerViewAdapter.taskCancel(task,task.getKey());
    }

    @Download.onTaskFail void taskFail(DownloadTask task) {
        gameRecyclerViewAdapter.taskFail(task,task.getKey());
    }

    @Download.onTaskComplete void taskComplete(DownloadTask task) {
        gameRecyclerViewAdapter.taskComplete(task, task.getKey());
    }


}
