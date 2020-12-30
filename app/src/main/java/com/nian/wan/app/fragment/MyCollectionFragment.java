package com.nian.wan.app.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.MyGameActivity;
import com.nian.wan.app.activity.broadcatInterface.IMyCollectionCheckBoxCheckAll;
import com.nian.wan.app.activity.broadcatInterface.IMyCollectionCheckBoxUnCheckAll;
import com.nian.wan.app.activity.broadcatInterface.IMyCollectionDelete;
import com.nian.wan.app.activity.broadcatInterface.IMyCollectionHideAllCheckBox;
import com.nian.wan.app.activity.broadcatInterface.IMyCollectionIsShouldCheck;
import com.nian.wan.app.activity.broadcatInterface.IMyCollectionShowAllCheckBox;
import com.nian.wan.app.adapter.MyCollectionHotCollectedRecyclerAdapter;
import com.nian.wan.app.adapter.MyCollectionRecyclerViewAdapter;
import com.nian.wan.app.bean.EvenBean;
import com.nian.wan.app.bean.MyCollectionBean;
import com.nian.wan.app.bean.MyCollectionHotGameBean;
import com.nian.wan.app.bean.SingleHotGameBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
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
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * @Description: 个人中心-我的收藏-我的收藏
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/6 14:22
 * @Modified By:
 * @Modified Date:
 */
@SuppressLint("ValidFragment")
public class MyCollectionFragment extends Fragment {

    //没有收藏布局
    @BindView(R.id.rl_my_collection_none)
    RelativeLayout mRlMyCollectionNone;
    //热门游戏布局
    @BindView(R.id.ll_my_collection_hot_game)
    LinearLayout mMyCollectionHotGame;
    //热门游戏列表
    @BindView(R.id.rc_my_collection_hot_game)
    RecyclerView mRcMyCollectionHotGame;
    @BindView(R.id.img_my_collection_more_hot_game)
    LinearLayout imgMyCollectionMoreHotGame;
    @BindView(R.id.springView)
    SpringView springView;
    //热门推荐适配器
    private MyCollectionHotCollectedRecyclerAdapter mMyCollectionHotCollectedRecyclerAdapter;
    //热门游戏数据源
    private List<MyCollectionHotGameBean> mHotGameBeen;

    //我的收藏适配器
    private MyCollectionRecyclerViewAdapter mMyCollectionRecyclerViewAdapter;
    //我的收藏数据源
    private MyCollectionBean mMyCollectionBeen;
    //我的收藏列表
    @BindView(R.id.rc_my_collection_list)

    RecyclerView mRcMyCollectionList;
    private int pageNumber = 1;

    public static final int NEED_REFRESH = 0x01;
    private MyCollectionBean myCollectionBean;
    private List<MyCollectionBean.CollectBean> collectBeanList = new ArrayList<MyCollectionBean.CollectBean>();
    private ArrayList<SingleHotGameBean> singleHotGameBean;
    private Handler myCollectionHandler;
    @SuppressLint("HandlerLeak")
    private Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NEED_REFRESH:
                    getMyCollection();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mine_collection, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initView() {
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                collectBeanList.clear();
                getMyCollection();
            }

            @Override
            public void onLoadmore() {
                loadMoreCollection();
            }
        });
        springView.setHeader(new SimpleHeader(x.app()));
        springView.setFooter(new SimpleFooter(x.app()));
        mMyCollectionRecyclerViewAdapter = new MyCollectionRecyclerViewAdapter(collectBeanList, getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRcMyCollectionList.setLayoutManager(layoutManager);
        mRcMyCollectionList.setAdapter(mMyCollectionRecyclerViewAdapter);
        registerCheckBoxListener();
        getMyCollection();
    }

    @SuppressLint("ValidFragment")
    public MyCollectionFragment(Handler myCollectionHandler) {
        this.myCollectionHandler = myCollectionHandler;
    }


    /**
     * 获取推荐游戏
     */
    public void getRecommendGame() {
        Type type = new TypeToken<HttpResult<ArrayList<SingleHotGameBean>>>() {
        }.getType();
        Map<String, String> recommendGameParams = new HashMap<>();
        recommendGameParams.put("rec_status", "1");
        recommendGameParams.put("sdk_version", "1");
        new HttpUtils<ArrayList<SingleHotGameBean>>(type, HttpConstant.API_PERSONAL_USER_RECOMMEND_HOT, recommendGameParams,
                "获取推荐游戏", true) {

            @Override
            protected void _onSuccess(ArrayList<SingleHotGameBean> bean, String msg) {
                springView.onFinishFreshAndLoad();
                singleHotGameBean = bean;
                if (bean.size() < 3) {
                    mMyCollectionHotGame.setVisibility(View.GONE);
                } else {
                    mMyCollectionHotGame.setVisibility(View.VISIBLE);
                    formatHotGameData();
                }
            }

            @Override
            protected void _onError(String message, int code) {
                mMyCollectionHotGame.setVisibility(View.GONE);
            }

            @Override
            protected void _onError() {
                mMyCollectionHotGame.setVisibility(View.GONE);
            }
        };
    }


    private void formatHotGameData() {
        mHotGameBeen = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRcMyCollectionHotGame.setLayoutManager(layoutManager);
        for (int i = 0; i < singleHotGameBean.size(); i++) {
            MyCollectionHotGameBean hotGameBean = new MyCollectionHotGameBean();
            hotGameBean.setGameId(singleHotGameBean.get(i).getId());
            hotGameBean.setGameName(singleHotGameBean.get(i).getGame_name());
            hotGameBean.setGamePic(singleHotGameBean.get(i).getIcon());
            hotGameBean.setGameDescription(singleHotGameBean.get(i).getFeatures());
            hotGameBean.setGameCollectedNumber(String.valueOf(singleHotGameBean.get(i).getCollect_num()));
            hotGameBean.setplay_num(singleHotGameBean.get(i).getPlay_num() + "");
            hotGameBean.setGameType(singleHotGameBean.get(i).getGame_type_name());
            hotGameBean.setGamePlayUrl(singleHotGameBean.get(i).getPlay_url());
            hotGameBean.setIsCollected(String.valueOf(singleHotGameBean.get(i).getCollect_status()));
            hotGameBean.setSdk_version(singleHotGameBean.get(i).getSdk_version());
            mHotGameBeen.add(hotGameBean);
        }
        mMyCollectionHotCollectedRecyclerAdapter = new MyCollectionHotCollectedRecyclerAdapter(mHotGameBeen, getActivity(), refreshHandler);
        mRcMyCollectionHotGame.setAdapter(mMyCollectionHotCollectedRecyclerAdapter);
        mMyCollectionHotCollectedRecyclerAdapter.notifyDataSetChanged();
    }


    private void formatData() {

        mMyCollectionRecyclerViewAdapter.setiMyCollectionIsShouldCheck(new IMyCollectionIsShouldCheck() {
            @Override
            public void shouldCheck() {
                Message message = new Message();
                message.what = MyGameActivity.ACTION_COLLECTION_CHECKED_CHECKALL_CHECKBOX;
                myCollectionHandler.sendMessage(message);
            }

            @Override
            public void unShouldCheck() {
                Message message = new Message();
                message.what = MyGameActivity.ACTION_COLLECTION_UNCHECK_CHECKALL_CHECKBOX;
                myCollectionHandler.sendMessage(message);
            }
        });
        mMyCollectionRecyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * 获取我的收藏
     */
    private void getMyCollection() {
        if (springView != null) {
            springView.onFinishFreshAndLoad();
        }
        Type type = new TypeToken<HttpResult<MyCollectionBean>>() {
        }.getType();
        new HttpUtils<MyCollectionBean>(type, HttpConstant.API_PERSONAL_COLLECTION_FOOTPRINT, null, "获取我的收藏", true) {

            @Override
            protected void _onSuccess(MyCollectionBean bean, String msg) {
                myCollectionBean = bean;
                if (myCollectionBean.getCollect().size() <= 0) {
                    getRecommendGame();
                    springView.setVisibility(View.GONE);
                    mMyCollectionHotGame.setVisibility(View.GONE);
                    mRlMyCollectionNone.setVisibility(View.VISIBLE);
                    Message message = new Message();
                    message.what = MyGameActivity.ACTION_COLLECTION_NO_DATA;
                    myCollectionHandler.sendMessage(message);
                } else {
                    springView.setVisibility(View.VISIBLE);
                    mMyCollectionHotGame.setVisibility(View.GONE);
                    mRlMyCollectionNone.setVisibility(View.GONE);
                    collectBeanList.clear();
                    collectBeanList.addAll(myCollectionBean.getCollect());
                    formatData();
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


    private void loadMoreCollection() {
        if (springView != null) {
            springView.onFinishFreshAndLoad();
        }
        Map<String, String> loadMoreCollectionParams = new HashMap<>();
        loadMoreCollectionParams.put("p1", String.valueOf(++pageNumber));
        Type type = new TypeToken<HttpResult<MyCollectionBean>>() {
        }.getType();
        new HttpUtils<MyCollectionBean>(type, HttpConstant.API_PERSONAL_COLLECTION_FOOTPRINT, loadMoreCollectionParams,
                "获取我的收藏", true) {

            @Override
            protected void _onSuccess(MyCollectionBean bean, String msg) {
                myCollectionBean = bean;
                if (myCollectionBean.getCollect().size() <= 0) {
                    ToastUtil.showToast("已经到底了~");
                } else {
                    springView.setVisibility(View.VISIBLE);
                    mMyCollectionHotGame.setVisibility(View.GONE);
                    mRlMyCollectionNone.setVisibility(View.GONE);
                    collectBeanList.addAll(myCollectionBean.getCollect());
                    formatData();
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
     * 注册CheckBox监听
     */
    public void registerCheckBoxListener() {

        MyGameActivity.setmICollectionShowAllCheckBox(new IMyCollectionShowAllCheckBox() {
            @Override
            public void showAllCheckBox() {
                isShowAllCheckBox(true);
            }
        });
        MyGameActivity.setmICollectionHideAllCheckBox(new IMyCollectionHideAllCheckBox() {
            @Override
            public void hideAllCheckBox() {
                isShowAllCheckBox(false);
            }
        });
        MyGameActivity.setmICollectionCheckBoxCheckAll(new IMyCollectionCheckBoxCheckAll() {
            @Override
            public void checkAll() {
                isCheckAllCheckBox(true);
            }
        });
        MyGameActivity.setmICollectionCheckBoxUnCheckAll(new IMyCollectionCheckBoxUnCheckAll() {
            @Override
            public void unCheckAll() {
                isCheckAllCheckBox(false);
            }
        });
        MyGameActivity.setmICollectionMyCollectionDelete(new IMyCollectionDelete() {
            @Override
            public void doDelete() {
                deleteChecked();
            }
        });

    }

    /**
     * 是否显示全部CheckBox
     *
     * @param isShowAll
     */
    public void isShowAllCheckBox(boolean isShowAll) {
        mMyCollectionRecyclerViewAdapter.isShowAllcheckBox(isShowAll);

    }

    /**
     * 是否选中所有CheckBox
     *
     * @param isCheckAll
     */
    public void isCheckAllCheckBox(boolean isCheckAll) {
        mMyCollectionRecyclerViewAdapter.isCheckAllCheckBox(isCheckAll);
    }

    /**
     * 删除选中的条目
     */
    public void deleteChecked() {
        mMyCollectionRecyclerViewAdapter.deleteChecked();
//        getMyCollection();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void ShuaXin(EvenBean bean) {
        if (bean.b == 5) {
            getMyCollection();
        }
    }

    @OnClick(R.id.img_my_collection_more_hot_game)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_my_collection_more_hot_game:
                this.getActivity().finish();
                Intent intent = new Intent("com.yinu.change.viewpage.index");
                intent.putExtra("change_status", PersonalCenter.ACTION_GO_RECOMMEND_SY_GAME);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                break;
        }
    }

}
