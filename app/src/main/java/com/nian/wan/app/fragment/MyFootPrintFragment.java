package com.nian.wan.app.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SpringView;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.GameDetailActivity;
import com.nian.wan.app.activity.GameDetailH5Activity;
import com.nian.wan.app.activity.MyGameActivity;
import com.nian.wan.app.activity.broadcatInterface.IMyFootprintCheckBoxCheckAll;
import com.nian.wan.app.activity.broadcatInterface.IMyFootprintCheckBoxDelete;
import com.nian.wan.app.activity.broadcatInterface.IMyFootprintCheckBoxInteface;
import com.nian.wan.app.activity.broadcatInterface.IMyFootprintCheckBoxUnCheckAll;
import com.nian.wan.app.activity.broadcatInterface.IMyFootprintHideAllCheckBox;
import com.nian.wan.app.activity.broadcatInterface.IMyFootprintShowAllCheckBox;
import com.nian.wan.app.adapter.MyCollectionRecommendRecyclerAdapter;
import com.nian.wan.app.adapter.MyFootprintExpandableListviewAdapter;
import com.nian.wan.app.adapter.MyGameHotGameAdapter;
import com.nian.wan.app.bean.EvenBean;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.bean.MyCollectionBean;
import com.nian.wan.app.bean.MyCollectionHotGameBean;
import com.nian.wan.app.bean.MyFootprintGroupBean;
import com.nian.wan.app.bean.MyFootprintItemBean;
import com.nian.wan.app.bean.SingleHotGameBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogMyCollectionConfirmDelete;

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

/**
 * @Description: 个人中心-我的足迹
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/6 14:22
 * @Modified By:
 * @Modified Date:
 */
public class MyFootPrintFragment extends Fragment implements IMyFootprintCheckBoxInteface {

    //我的足迹热门游戏
    @BindView(R.id.rc_my_footprint_hot_game)
    ListView mRcMyFootprintHotGame;
    //我的足迹列表
    @BindView(R.id.ev_my_footprint_list)
    ExpandableListView mEvMyFootprintList;
    //没有足迹布局
    @BindView(R.id.rl_my_footprint_none)
    RelativeLayout mRlMyFootprintNone;
    //我的足迹热门游戏布局
    @BindView(R.id.ll_my_footprint_hot_game)
    LinearLayout mLlMyFootprintHotGame;
    @BindView(R.id.img_my_footprint_hot_game_more)
    LinearLayout imgMyFootprintHotGameMore;
    @BindView(R.id.springView)
    SpringView springView;

    private MyGameHotGameAdapter hotGameAdapter;
    private ArrayList<GameInfo> hotGameInfos = new ArrayList<>();

    //热门游戏适配器
    private MyCollectionRecommendRecyclerAdapter myCollectionRecommendRecyclerAdapter;
    //热门游戏数据源
    private List<MyCollectionHotGameBean> mHotGameBeen;
    //我的足迹适配器
    MyFootprintExpandableListviewAdapter myFootprintExpandableListviewAdapter;
    //我的足迹组集合
    private List<MyFootprintGroupBean> mMyFootprintGroupBean = new ArrayList<>();
    //我的足迹子集合
    private Map<String, List<MyFootprintItemBean>> mMyFootprintItemBean = new HashMap<>();
    //我的足迹组集合
    private List<MyFootprintGroupBean> mMyFootprintGroupBean1 = new ArrayList<>();
    //我的足迹子集合
    private Map<String, List<MyFootprintItemBean>> mMyFootprintItemBean1 = new HashMap<>();
    private Handler mMyFootprintFragmentHandler;


    private MyCollectionBean myCollectionBean;
    private ArrayList<SingleHotGameBean> singleHotGameBean;
    private int limit = 1;
    public static final int NEED_REFRESH = 0x01;

    @SuppressLint("HandlerLeak")
    Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NEED_REFRESH:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mine_footprint, null);
        ButterKnife.bind(this, view);
        Aria.download(this).register();
        hotGameAdapter = new MyGameHotGameAdapter(getActivity());
        mRcMyFootprintHotGame.setAdapter(hotGameAdapter);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadmore() {
                limit = limit + 1;
                loadMoreMyFootPrint();
            }
        });
        springView.setFooter(new SimpleFooter(x.app()));
        return view;
    }

    @SuppressLint("ValidFragment")
    public MyFootPrintFragment(Handler handler) {
        this.mMyFootprintFragmentHandler = handler;
    }


    /**
     * 设置我的足迹数据
     */
    private void formatData() {
        for (int i = 0; i < myCollectionBean.getFoot().size(); i++) {
            mMyFootprintGroupBean.add(new MyFootprintGroupBean(String.valueOf(i), myCollectionBean.getFoot().get(i).getData()));
            List<MyCollectionBean.FootBean.GameDataBean> game_data = myCollectionBean.getFoot().get(i).getGame_data();
            List<MyFootprintItemBean> footprintItemBeanList = new ArrayList<>();
            for (int j = 0; j < game_data.size(); j++) {
                MyFootprintItemBean footprintItemBean = new MyFootprintItemBean();
                footprintItemBean.setId(String.valueOf(i));
                footprintItemBean.setGameId(game_data.get(j).getId());
                footprintItemBean.setGameName(game_data.get(j).getGame_name());
                footprintItemBean.setGamePic(game_data.get(j).getIcon());
                footprintItemBean.setGameBid(game_data.get(j).getBid());
                footprintItemBeanList.add(footprintItemBean);
                mMyFootprintItemBean.put(String.valueOf(i), footprintItemBeanList);
            }
        }
        mMyFootprintGroupBean1.addAll(mMyFootprintGroupBean);
        mMyFootprintItemBean1.putAll(mMyFootprintItemBean);
        if (myFootprintExpandableListviewAdapter == null) {
            myFootprintExpandableListviewAdapter = new MyFootprintExpandableListviewAdapter(getContext());
            myFootprintExpandableListviewAdapter.setmMyFootprintCheckBoxInteface(this);
            mEvMyFootprintList.setAdapter(myFootprintExpandableListviewAdapter);
        }
        myFootprintExpandableListviewAdapter.setData(mMyFootprintGroupBean1, mMyFootprintItemBean1);
        registerCheckBoxListener();
        for (int i = 0; i < myFootprintExpandableListviewAdapter.getGroupCount(); i++) {
            MyFootprintGroupBean group = (MyFootprintGroupBean) mEvMyFootprintList.getExpandableListAdapter().getGroup(i);
            if (mMyFootprintItemBean1.get(group.getId()).size() <= 5) {
                mEvMyFootprintList.expandGroup(i);
            }
        }
    }


    /**
     * 获取热门游戏
     */
    public void getHotGame() {
        Map<String, String> hotGameParams = new HashMap<>();
        hotGameParams.put("rec_status", "2");
        hotGameParams.put("sdk_version", "1");
        Type type = new TypeToken<HttpResult<ArrayList<SingleHotGameBean>>>() {
        }.getType();
        new HttpUtils<ArrayList<SingleHotGameBean>>(type, HttpConstant.API_PERSONAL_USER_RECOMMEND_HOT, hotGameParams,
                "获取热门游戏", true) {

            @Override
            protected void _onSuccess(ArrayList<SingleHotGameBean> bean, String msg) {
                singleHotGameBean = bean;
                if (singleHotGameBean.size() < 3) {
                    mLlMyFootprintHotGame.setVisibility(View.GONE);
                } else {
                    formatHotGameData();
                }
            }

            @Override
            protected void _onError(String message, int code) {
                mLlMyFootprintHotGame.setVisibility(View.GONE);
            }

            @Override
            protected void _onError() {
                mLlMyFootprintHotGame.setVisibility(View.GONE);
            }
        };
    }

    private void formatHotGameData() {
        for (int i = 0; i < singleHotGameBean.size(); i++) {
            GameInfo gameInfo = new GameInfo();
            gameInfo.sdk_version = Integer.valueOf(singleHotGameBean.get(i).getSdk_version());
            gameInfo.id = Integer.valueOf(singleHotGameBean.get(i).getId());
            gameInfo.name = singleHotGameBean.get(i).getGame_name();
            gameInfo.iconurl = singleHotGameBean.get(i).getIcon();
            gameInfo.features = singleHotGameBean.get(i).getFeatures();
            gameInfo.playNum = singleHotGameBean.get(i).getPlay_num();
            gameInfo.type = singleHotGameBean.get(i).getGame_type_name();
            gameInfo.GameUrl = singleHotGameBean.get(i).getPlay_url();
            gameInfo.canDownload = singleHotGameBean.get(i).getXia_status();
            gameInfo.size = singleHotGameBean.get(i).getGame_size();
            hotGameInfos.add(gameInfo);
        }
        hotGameAdapter.setList(hotGameInfos);
        mRcMyFootprintHotGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (hotGameAdapter.getList().get(i).sdk_version == 3) {
                    Intent intent = new Intent(getActivity(), GameDetailH5Activity.class);
                    intent.putExtra("game_id", hotGameAdapter.getList().get(i).id);
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), GameDetailActivity.class);
                    intent.putExtra("game_id", hotGameAdapter.getList().get(i).id);
                    getActivity().startActivity(intent);
                }
            }
        });
    }


    /**
     * 获取我的足迹
     */
    public void getMyFootPrint() {
        if (springView != null) {
            springView.onFinishFreshAndLoad();
        }
        Type type = new TypeToken<HttpResult<MyCollectionBean>>() {
        }.getType();
        Map<String, String> getScoreParams = new HashMap<>();
        getScoreParams.put("p2", String.valueOf(limit));
        new HttpUtils<MyCollectionBean>(type, HttpConstant.API_PERSONAL_COLLECTION_FOOTPRINT, getScoreParams, "获取我的足迹", true) {

            @Override
            protected void _onSuccess(MyCollectionBean bean, String msg) {
                myCollectionBean = bean;
                if (myCollectionBean.getFoot().size() <= 0 && mMyFootprintGroupBean1.size() == 0) {
                    getHotGame();
                    springView.setVisibility(View.GONE);
                    mRlMyFootprintNone.setVisibility(View.VISIBLE);
                    mLlMyFootprintHotGame.setVisibility(View.VISIBLE);
                    Message message = new Message();
                    message.what = MyGameActivity.ACTION_FOOT_NO_DATA;
                    mMyFootprintFragmentHandler.sendMessage(message);
                } else {
                    mLlMyFootprintHotGame.setVisibility(View.GONE);
                    mRlMyFootprintNone.setVisibility(View.GONE);
                    springView.setVisibility(View.VISIBLE);

                    mMyFootprintGroupBean1.clear();
                    mMyFootprintGroupBean.clear();
                    mMyFootprintItemBean1.clear();
                    mMyFootprintItemBean.clear();
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
     * 获取更多我的足迹
     */
    public void loadMoreMyFootPrint() {
        if (springView != null) {
            springView.onFinishFreshAndLoad();
        }
        Type type = new TypeToken<HttpResult<MyCollectionBean>>() {
        }.getType();
        Map<String, String> getScoreParams = new HashMap<>();
        getScoreParams.put("p2", String.valueOf(limit));
        new HttpUtils<MyCollectionBean>(type, HttpConstant.API_PERSONAL_COLLECTION_FOOTPRINT, getScoreParams,
                "更多我的足迹", true) {

            @Override
            protected void _onSuccess(MyCollectionBean bean, String msg) {
                myCollectionBean = bean;

                if (myCollectionBean.getFoot().size() <= 0) {
                    ToastUtil.showToast("已经到底了~");
                } else {
                    formatData();
                }

//                if (myCollectionBean.getFoot().size() <= 0 && mMyFootprintGroupBean1.size() == 0) {
//                    getHotGame();
//                    mRlMyFootprintNone.setVisibility(View.VISIBLE);
//                    mLlMyFootprintHotGame.setVisibility(View.VISIBLE);
//                    Message message = new Message();
//                    message.what = MyGameActivity.ACTION_FOOT_NO_DATA;
//                    mMyFootprintFragmentHandler.sendMessage(message);
//                } else {
//                    formatData();
//                }

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
     * 注册接口
     */
    public void registerCheckBoxListener() {
        //注册显示所有CheckBox接口
        MyGameActivity.setmIMyFootprintShowAllCheckBox(new IMyFootprintShowAllCheckBox() {
            @Override
            public void showAllCheckBox() {
                myFootprintExpandableListviewAdapter.isShowAllCheckBox(true);
            }
        });
        //注册隐藏所有CheckBox接口
        MyGameActivity.setmIMyFootprintHideAllCheckBox(new IMyFootprintHideAllCheckBox() {
            @Override
            public void hideAllCheckBox() {
                myFootprintExpandableListviewAdapter.isShowAllCheckBox(false);
            }
        });
        //注册删除选中元素接口
        MyGameActivity.setmIMyFootprintCheckBoxDelete(new IMyFootprintCheckBoxDelete() {
            @Override
            public void doDelete() {
                @SuppressLint("HandlerLeak") Handler deleteHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case MyGameActivity.ACTION_DELETE_CONFIRM:
                                doDeleteChecked();
                                break;
                        }
                    }
                };
                //待删除的组元素列表
                List<MyFootprintGroupBean> toDeleteFootprintGroupBeen = new ArrayList<>();
                List<MyFootprintItemBean> toDeleteFootprintItemBeen = new ArrayList<>();
                //待删除的子元素列表
                List<MyFootprintItemBean> myFootprintItemBeenList = null;
                for (int i = 0; i < mMyFootprintGroupBean.size(); i++) {
                    MyFootprintGroupBean myFootprintGroupBean = mMyFootprintGroupBean.get(i);
                    if (myFootprintGroupBean.isChoosed()) {
                        toDeleteFootprintGroupBeen.add(myFootprintGroupBean);
                    }
                    //待删除的子元素列表
                    myFootprintItemBeenList = mMyFootprintItemBean.get(myFootprintGroupBean.getId());
                    for (int j = 0; j < myFootprintItemBeenList.size(); j++) {
                        if (myFootprintItemBeenList.get(j).isChoosed()) {
                            toDeleteFootprintItemBeen.add(myFootprintItemBeenList.get(j));
                        }
                    }
                }
                if (toDeleteFootprintItemBeen.size() <= 0) {
                    ToastUtil.showToast("请选择需要操作的数据");
                    return;
                }
                new DialogMyCollectionConfirmDelete(getActivity(), R.style.MyDialogStyle, deleteHandler).show();
            }
        });
        //注册全选接口
        MyGameActivity.setmIMyFootprintCheckBoxCheckAll(new IMyFootprintCheckBoxCheckAll() {
            @Override
            public void checkAll() {
                doCheckAll(true);
            }
        });
        //注册反选接口
        MyGameActivity.setmIMyFootprintCheckBoxUnCheckAll(new IMyFootprintCheckBoxUnCheckAll() {
            @Override
            public void unCheckAll() {
                doCheckAll(false);
            }
        });
    }


    /**
     * 组元素选中
     *
     * @param groupPosition 组元素位置
     * @param isChecked     组元素选中与否
     */
    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        MyFootprintGroupBean myFootprintGroupBean = mMyFootprintGroupBean1.get(groupPosition);
        List<MyFootprintItemBean> footprintItemBeanList = mMyFootprintItemBean1
                .get(myFootprintGroupBean.getId());
        for (int i = 0; i < footprintItemBeanList.size(); i++) {
            footprintItemBeanList.get(i).setChoosed(isChecked);
        }
        Message message = Message.obtain();
        if (isCheckAll()) {
            message.what = MyGameActivity.ACTION_FOOT_CHECKED_CHECKALL_CHECKBOX;
        } else {
            message.what = MyGameActivity.ACTION_FOOT_UNCHECK_CHECKALL_CHECKBOX;
        }
        mMyFootprintFragmentHandler.sendMessage(message);
        myFootprintExpandableListviewAdapter.notifyDataSetChanged();
    }


    /**
     * 子元素选中
     *
     * @param groupPosition 组元素位置
     * @param childPosition 子元素位置
     * @param isChecked     子元素选中与否
     */
    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        // 判断该组下面的所有子元素是否是同一种状态
        boolean allChildSameState = true;
        MyFootprintGroupBean myFootprintGroupBean = mMyFootprintGroupBean1.get(groupPosition);
        List<MyFootprintItemBean> footprintItemBeanList = mMyFootprintItemBean1
                .get(myFootprintGroupBean.getId());
        for (int i = 0; i < footprintItemBeanList.size(); i++) {
            //不全选中
            if (footprintItemBeanList.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        if (allChildSameState) {
            // 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
            myFootprintGroupBean.setChoosed(isChecked);
        } else {
            myFootprintGroupBean.setChoosed(false);
        }
        Message message = Message.obtain();
        if (isCheckAll()) {
            message.what = MyGameActivity.ACTION_FOOT_CHECKED_CHECKALL_CHECKBOX;
        } else {
            message.what = MyGameActivity.ACTION_FOOT_UNCHECK_CHECKALL_CHECKBOX;
        }
        mMyFootprintFragmentHandler.sendMessage(message);
        myFootprintExpandableListviewAdapter.notifyDataSetChanged();
    }

    /**
     * 全选或者反选
     *
     * @param ischeckAll
     */
    public void doCheckAll(boolean ischeckAll) {
        for (int i = 0; i < mMyFootprintGroupBean1.size(); i++) {
            mMyFootprintGroupBean1.get(i).setChoosed(ischeckAll);
            MyFootprintGroupBean myFootprintGroupBean = mMyFootprintGroupBean1.get(i);
            List<MyFootprintItemBean> footprintItemBeanList = mMyFootprintItemBean1.get(myFootprintGroupBean.getId());
            for (int j = 0; j < footprintItemBeanList.size(); j++) {
                footprintItemBeanList.get(j).setChoosed(ischeckAll);
            }
        }
        myFootprintExpandableListviewAdapter.notifyDataSetChanged();
    }

    /**
     * 删除选中的元素
     */
    public void doDeleteChecked() {
        //待删除的元素的id
        StringBuffer dynamicIdArray = new StringBuffer();
        //待删除的组元素列表
        List<MyFootprintGroupBean> toDeleteFootprintGroupBeen = new ArrayList<>();
        //待删除的子元素列表
        List<MyFootprintItemBean> toDeleteFootprintItemBeen = new ArrayList<>();


        List<MyFootprintItemBean> myFootprintItemBeenList = null;
        for (int i = 0; i < mMyFootprintGroupBean1.size(); i++) {
            MyFootprintGroupBean myFootprintGroupBean = mMyFootprintGroupBean1.get(i);
            if (myFootprintGroupBean.isChoosed()) {
                toDeleteFootprintGroupBeen.add(myFootprintGroupBean);
            }
            //待删除的子元素列表
            myFootprintItemBeenList = mMyFootprintItemBean1.get(myFootprintGroupBean.getId());
            for (int j = 0; j < myFootprintItemBeenList.size(); j++) {
                if (myFootprintItemBeenList.get(j).isChoosed()) {
                    toDeleteFootprintItemBeen.add(myFootprintItemBeenList.get(j));
                    dynamicIdArray.append(myFootprintItemBeenList.get(j).getGameBid() + ",");
                }
            }
            myFootprintItemBeenList.removeAll(toDeleteFootprintItemBeen);
        }
        mMyFootprintGroupBean1.removeAll(toDeleteFootprintGroupBeen);
        if (mMyFootprintGroupBean1.size() == 0) {
            myFootprintExpandableListviewAdapter.isShowAllCheckBox(false);
        }
        myFootprintExpandableListviewAdapter.notifyDataSetChanged();
        deteFoot(new String[]{dynamicIdArray.toString()});
    }


    /**
     * 删除足迹
     *
     * @param gameId 要删除足迹的id
     */
    public void deteFoot(String[] gameId) {
        if (0 == gameId.length) {
            return;
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < gameId.length; i++) {
                stringBuffer.append(gameId[i] + ",");
            }
            Map<String, String> cancelCollectionParams = new HashMap<>();
            cancelCollectionParams.put("ids", stringBuffer.toString());
            Type type = new TypeToken<HttpResult<String>>() {
            }.getType();
            new HttpUtils<String>(type, HttpConstant.API_PERSONAL_DELETE_FOOT, cancelCollectionParams, "删除足迹", true) {

                @Override
                protected void _onSuccess(String bean, String msg) {
                    if (mMyFootprintGroupBean1.size() == 0) {
                        getHotGame();
                        EvenBean evenBean = new EvenBean();
                        evenBean.b = 5;
                        EventBus.getDefault().post(evenBean);
                        mRlMyFootprintNone.setVisibility(View.VISIBLE);
                        mLlMyFootprintHotGame.setVisibility(View.VISIBLE);
                    }

                    Message message = new Message();
                    message.what = MyGameActivity.ACTION_DELETE_CONFIRM;
                    mMyFootprintFragmentHandler.sendMessage(message);
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
    }


    /**
     * 所有组是否都已经选中
     *
     * @return
     */
    private boolean isCheckAll() {
        for (MyFootprintGroupBean myFootprintGroupBean : mMyFootprintGroupBean) {
            if (!myFootprintGroupBean.isChoosed()) {
                return false;
            }
        }
        return true;
    }


    @OnClick(R.id.img_my_footprint_hot_game_more)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_my_footprint_hot_game_more:
                this.getActivity().finish();
                Intent intent = new Intent("com.yinu.change.viewpage.index");
                intent.putExtra("change_status", PersonalCenter.ACTION_GO_HOT_SY_GAME);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                break;
        }
    }


    @Download.onWait
    void taskWait(DownloadTask task) {
        hotGameAdapter.taskWait(task, task.getKey());
    }

    @Download.onTaskStop
    void taskStop(DownloadTask task) {
        hotGameAdapter.taskStop(task, task.getKey());
    }

    @Download.onTaskRunning
    void taskRuning(DownloadTask task) {
        hotGameAdapter.taskRuning(task, task.getKey());
    }

    @Download.onTaskCancel
    void taskCancel(DownloadTask task) {
        hotGameAdapter.taskCancel(task, task.getKey());
    }

    @Download.onTaskFail
    void taskFail(DownloadTask task) {
        hotGameAdapter.taskFail(task, task.getKey());
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        hotGameAdapter.taskComplete(task, task.getKey());
    }


    @Override
    public void onResume() {
        super.onResume();
        getMyFootPrint();
        //判断是否安装
        if (hotGameInfos.size() > 0) {
            hotGameAdapter.ConfirmationState();
        }
    }
}
