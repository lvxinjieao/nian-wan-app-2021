package com.nian.wan.app.fragment.fragment_racking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.bumptech.glide.Glide;
import com.mc.developmentkit.utils.DbUtils;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SpringView;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.GameDetailActivity;
import com.nian.wan.app.adapter.RackingAdapter;
import com.nian.wan.app.bean.DataBean;
import com.nian.wan.app.bean.DownDataBean;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.download.DownManager;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogGoLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

;

/**
 * 描述：手游排行榜-子Fragment
 */
@SuppressLint("ValidFragment")
public class RackingChildFragment extends Fragment {

    @BindView(R.id.tv_top2_gamename)
    TextView tvTop2Gamename;

    @BindView(R.id.btn_top2_down)
    TextView btnTop2Down;

    @BindView(R.id.huangguan_2)
    ImageView huangguan2;

    @BindView(R.id.img_top2_gamaicon)
    ImageView imgTop2Gamaicon;

    @BindView(R.id.rl_ranking_top2)
    RelativeLayout rlRankingTop2;

    @BindView(R.id.tv_top1_gamename)
    TextView tvTop1Gamename;

    @BindView(R.id.btn_top1_down)
    TextView btnTop1Down;

    @BindView(R.id.huangguan_1)
    ImageView huangguan1;

    @BindView(R.id.img_top1_gamaicon)
    ImageView imgTop1Gamaicon;

    @BindView(R.id.rl_ranking_top1)
    RelativeLayout rlRankingTop1;

    @BindView(R.id.tv_top3_gamename)
    TextView tvTop3Gamename;

    @BindView(R.id.btn_top3_down)
    TextView btnTop3Down;

    @BindView(R.id.huangguan_3)
    ImageView huangguan3;

    @BindView(R.id.img_top3_gamaicon)
    ImageView imgTop3Gamaicon;

    @BindView(R.id.rl_ranking_top3)
    RelativeLayout rlRankingTop3;

    @BindView(R.id.list_ranking)
    ListView listRanking;

    @BindView(R.id.springView)
    SpringView springview;

    @BindView(R.id.ll_racking_top)
    LinearLayout llRackingTop;

    @BindView(R.id.error_layout)
    LinearLayout errorLayout;

    @BindView(R.id.btn_top2_detail)
    TextView btnTop2Detail;

    @BindView(R.id.btn_top1_detail)
    TextView btnTop1Detail;

    @BindView(R.id.btn_top3_detail)
    TextView btnTop3Detail;

    private int rackType;  //用于判断排行榜类别： 1推荐榜 2热门榜 3最新榜 4下载榜
    private RackingAdapter rackingAdapter;
    private List<GameInfo> listData = new ArrayList<>();
    private int p = 1;
    private int suo = -1;    //默认可以点击下载按钮
    private DbManager db;
    private DownDataBean down;


    @SuppressLint("ValidFragment")
    public RackingChildFragment(int type) {
        this.rackType = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_racking_child_shouyou, null);
        ButterKnife.bind(this, view);

        rackingAdapter = new RackingAdapter(getActivity(), false);
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setFooter(new SimpleFooter(getActivity()));
        listRanking.setFocusable(false);
//        springview.setHeader(new SimpleHeader(getActivity()));
        Aria.download(this).register();
        getRacking();
        return view;
    }

    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            p = 1;
            listData.clear();
            getRacking();
        }

        @Override
        public void onLoadmore() {
            p = p + 1;
            onLoadMore();
        }

    };


    /**
     * 获取排行数据
     */
    private void getRacking() {
        Map<String, String> map = new HashMap<>();
        map.put("recommend", rackType + "");
        map.put("sdk_version", "1");
        map.put("p", p + "");
        if (null != Utils.getPersistentUserInfo()) {
            map.put("token", Utils.getPersistentUserInfo().token);
        }
        HttpConstant.POST(handler, HttpConstant.GameRacking, map, false);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ProcessingData(msg);
        }
    };

    /**
     * 处理网络请求数据
     *
     * @param msg
     */
    private void ProcessingData(Message msg) {
        if (springview != null) {
            springview.onFinishFreshAndLoad();
        }
        try {
            Log.e("手游排行类型：" + rackType, "，返回数据" + msg.obj.toString());
            JSONObject jsonObject = new JSONObject(msg.obj.toString());
            int code = jsonObject.getInt("code");
            JSONArray jsonData = jsonObject.getJSONArray("data");
            if (jsonData != null && jsonData.length() > 0) {
                if (code == 200) {
                    llRackingTop.setVisibility(View.VISIBLE);
                    springview.setVisibility(View.VISIBLE);
                    errorLayout.setVisibility(View.GONE);

                    for (int i = 0; i < jsonData.length(); i++) {
                        JSONObject jsonObject1 = jsonData.getJSONObject(i);
                        GameInfo appInfo = new GameInfo();
                        appInfo.iconurl = jsonObject1.getString("icon");
                        appInfo.name = jsonObject1.getString("game_name");
                        appInfo.id = jsonObject1.getInt("id");
                        appInfo.features = jsonObject1.getString("features");
                        appInfo.type = jsonObject1.getString("game_type_name");
                        appInfo.playNum = jsonObject1.getString("play_num");
                        appInfo.GameUrl = jsonObject1.getString("play_url");
                        appInfo.fanli = jsonObject1.getString("ratio");
                        appInfo.size = jsonObject1.getString("game_size");
                        appInfo.canDownload = jsonObject1.getInt("xia_status");
                        listData.add(appInfo);
                    }

                    switch (listData.size()) {
                        case 1:
                            rlRankingTop1.setVisibility(View.VISIBLE);
                            rlRankingTop2.setVisibility(View.INVISIBLE);
                            rlRankingTop3.setVisibility(View.INVISIBLE);
                            Glide.with(x.app()).load(listData.get(0).iconurl).error(R.drawable.default_picture).into(imgTop1Gamaicon);
                            tvTop1Gamename.setText(listData.get(0).name);
                            if (listData.get(0).canDownload == 0) {
                                btnTop1Down.setBackgroundResource(R.drawable.home_ranking_top_down_not_bg);
                                btnTop1Down.setEnabled(false);
                            }
                            Complete(0);
                            break;
                        case 2:
                            rlRankingTop1.setVisibility(View.VISIBLE);
                            rlRankingTop2.setVisibility(View.VISIBLE);
                            rlRankingTop3.setVisibility(View.INVISIBLE);
                            Glide.with(x.app()).load(listData.get(0).iconurl).error(R.drawable.default_picture).into(imgTop1Gamaicon);
                            tvTop1Gamename.setText(listData.get(0).name);
                            Glide.with(x.app()).load(listData.get(1).iconurl).error(R.drawable.default_picture).into(imgTop2Gamaicon);
                            tvTop2Gamename.setText(listData.get(1).name);
                            if (listData.get(0).canDownload == 0) {
                                btnTop1Down.setBackgroundResource(R.drawable.home_ranking_top_down_not_bg);
                                btnTop1Down.setEnabled(false);
                            }
                            if (listData.get(1).canDownload == 0) {
                                btnTop2Down.setBackgroundResource(R.drawable.home_ranking_top_down_not_bg);
                                btnTop2Down.setEnabled(false);
                            }
                            Complete(0);
                            Complete(1);
                            break;
                        case 3:
                            rlRankingTop1.setVisibility(View.VISIBLE);
                            rlRankingTop2.setVisibility(View.VISIBLE);
                            rlRankingTop3.setVisibility(View.VISIBLE);
                            Glide.with(x.app()).load(listData.get(0).iconurl).error(R.drawable.default_picture).into(imgTop1Gamaicon);
                            tvTop1Gamename.setText(listData.get(0).name);
                            Glide.with(x.app()).load(listData.get(1).iconurl).error(R.drawable.default_picture).into(imgTop2Gamaicon);
                            tvTop2Gamename.setText(listData.get(1).name);
                            Glide.with(x.app()).load(listData.get(2).iconurl).error(R.drawable.default_picture).into(imgTop3Gamaicon);
                            tvTop3Gamename.setText(listData.get(2).name);
                            if (listData.get(0).canDownload == 0) {
                                btnTop1Down.setBackgroundResource(R.drawable.home_ranking_top_down_not_bg);
                                btnTop1Down.setEnabled(false);
                            }
                            if (listData.get(1).canDownload == 0) {
                                btnTop2Down.setBackgroundResource(R.drawable.home_ranking_top_down_not_bg);
                                btnTop2Down.setEnabled(false);
                            }
                            if (listData.get(2).canDownload == 0) {
                                btnTop3Down.setBackgroundResource(R.drawable.home_ranking_top_down_not_bg);
                                btnTop3Down.setEnabled(false);
                            }
                            Complete(0);
                            Complete(1);
                            Complete(2);
                            break;
                        default:
                            rlRankingTop1.setVisibility(View.VISIBLE);
                            rlRankingTop2.setVisibility(View.VISIBLE);
                            rlRankingTop3.setVisibility(View.VISIBLE);
                            Glide.with(x.app()).load(listData.get(0).iconurl).error(R.drawable.default_picture).into(imgTop1Gamaicon);
                            tvTop1Gamename.setText(listData.get(0).name);
                            Glide.with(x.app()).load(listData.get(1).iconurl).error(R.drawable.default_picture).into(imgTop2Gamaicon);
                            tvTop2Gamename.setText(listData.get(1).name);
                            Glide.with(x.app()).load(listData.get(2).iconurl).error(R.drawable.default_picture).into(imgTop3Gamaicon);
                            tvTop3Gamename.setText(listData.get(2).name);
                            if (listData.get(0).canDownload == 0) {
                                btnTop1Down.setBackgroundResource(R.drawable.home_ranking_top_down_not_bg);
                                btnTop1Down.setEnabled(false);
                            }
                            if (listData.get(1).canDownload == 0) {
                                btnTop2Down.setBackgroundResource(R.drawable.home_ranking_top_down_not_bg);
                                btnTop2Down.setEnabled(false);
                            }
                            if (listData.get(2).canDownload == 0) {
                                btnTop3Down.setBackgroundResource(R.drawable.home_ranking_top_down_not_bg);
                                btnTop3Down.setEnabled(false);
                            }

                            if (listData.size() > 3) {
                                List<GameInfo> listItemData = new ArrayList<>();
                                for (int i = 3; i < listData.size(); i++) {
                                    listItemData.add(listData.get(i));
                                }
                                rackingAdapter.setListData(listItemData);
                                listRanking.setAdapter(rackingAdapter);
                                Utils.setListView(listRanking);

                                Complete(0);
                                Complete(1);
                                Complete(2);
                                rackingAdapter.ConfirmationState();
                            }
                            break;
                    }

                } else {
                    ToastUtil.showToast(jsonObject.getString("msg"));
                }

            } else {
                llRackingTop.setVisibility(View.GONE);
                springview.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
            }

        } catch (JSONException e) {
            Log.e("手游排行异常", e.toString());
        }
    }


    /**
     * 处理加载更多数据
     *
     * @param msg
     */
    private void ProcessingData2(Message msg) {
        if (springview != null) {
            springview.onFinishFreshAndLoad();
        }
        try {
            Log.e("手游排行类型：" + rackType, "，加载更多" + msg.obj.toString());
            JSONObject jsonObject = new JSONObject(msg.obj.toString());
            int code = jsonObject.getInt("code");
            JSONArray jsonData = jsonObject.getJSONArray("data");

            if (code == 200) {
                if (jsonData != null && jsonData.length() > 0) {
                    ArrayList<GameInfo> gameInfos = new ArrayList<>();
                    for (int i = 0; i < jsonData.length(); i++) {
                        JSONObject jsonObject1 = jsonData.getJSONObject(i);
                        GameInfo appInfo = new GameInfo();
                        appInfo.iconurl = jsonObject1.getString("icon");
                        appInfo.name = jsonObject1.getString("game_name");
                        appInfo.id = jsonObject1.getInt("id");
                        appInfo.features = jsonObject1.getString("features");
                        appInfo.type = jsonObject1.getString("game_type_name");
                        appInfo.playNum = jsonObject1.getString("play_num");
                        appInfo.GameUrl = jsonObject1.getString("play_url");
                        appInfo.fanli = jsonObject1.getString("ratio");
                        appInfo.canDownload = jsonObject1.getInt("xia_status");
                        gameInfos.add(appInfo);
                    }
                    rackingAdapter.setListData(gameInfos);
                }
            } else {
                ToastUtil.showToast(jsonObject.getString("msg"));
            }

        } catch (JSONException e) {
            Log.e("手游排行加载更多异常", e.toString());
        }
    }

    /**
     * 加载更多数据
     */
    private void onLoadMore() {
        Map<String, String> map = new HashMap<>();
        map.put("recommend", rackType + "");
        map.put("sdk_version", "1");
        map.put("p", p + "");
        if (null != Utils.getPersistentUserInfo()) {
            map.put("token", Utils.getPersistentUserInfo().token);
        }
        HttpConstant.POST(mhandler, HttpConstant.GameRacking, map, false);
    }

    @SuppressLint("HandlerLeak")
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ProcessingData2(msg);
        }
    };


    @OnClick({R.id.btn_top2_down, R.id.btn_top1_down, R.id.btn_top3_down, R.id.btn_top2_detail, R.id.btn_top1_detail, R.id.btn_top3_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_top1_down:
                xiazai(0);
                break;
            case R.id.btn_top2_down:
                xiazai(1);
                break;
            case R.id.btn_top3_down:
                xiazai(2);
                break;
            case R.id.btn_top1_detail:
                jumpGameDetail(0);
                break;
            case R.id.btn_top2_detail:
                jumpGameDetail(1);
                break;
            case R.id.btn_top3_detail:
                jumpGameDetail(2);
                break;
        }
    }


    @Download.onWait
    void taskWait(DownloadTask task) {
        rackingAdapter.taskWait(task, task.getKey());
        int pos = getPos(task.getKey());
        taskWait2(pos);
    }

    @Download.onTaskStop
    void taskStop(DownloadTask task) {
        rackingAdapter.taskStop(task, task.getKey());
        int pos = getPos(task.getKey());
        Puse(pos);
    }

    @Download.onTaskRunning
    void taskRuning(DownloadTask task) {
        rackingAdapter.taskRuning(task, task.getKey());
        int pos = getPos(task.getKey());
        Runing(pos, task.getPercent());
    }

    @Download.onTaskCancel
    void taskCancel(DownloadTask task) {
        rackingAdapter.taskCancel(task, task.getKey());
        int pos = getPos(task.getKey());
        NoDown(pos);
    }

    @Download.onTaskFail
    void taskFail(DownloadTask task) {
        rackingAdapter.taskFail(task, task.getKey());
        int pos = getPos(task.getKey());
        Fail(pos);
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        rackingAdapter.taskComplete(task, task.getKey());
        int pos = getPos(task.getKey());
        Complete(pos);
    }

    /**
     * 等待
     *
     * @param pos
     */
    public void taskWait2(int pos) {
        switch (pos) {
            case 0:
                btnTop1Down.setText("等待");
                break;
            case 1:
                btnTop2Down.setText("等待");
                break;
            case 2:
                btnTop3Down.setText("等待");
                break;
        }
    }

    /**
     * 失败
     *
     * @param pos
     */
    public void Fail(int pos) {
        try {
            if (down != null) {
                Aria.download(this).load(down.DownUrl).cancel();//删除下载数据记录
                DbUtils.getDB().deleteById(DownDataBean.class, down.id);//删除数据库记录
                DownManager.getInstance().Delete(down.id);//删除本地下载文件    如果有则删除
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        ToastUtil.showToast("下载链接有误");
        switch (pos) {
            case 0:
                btnTop1Down.setText("重试");
                break;
            case 1:
                btnTop2Down.setText("重试");
                break;
            case 2:
                btnTop3Down.setText("重试");
                break;
        }
    }

    /**
     * 未下载
     *
     * @param pos
     */
    public void NoDown(int pos) {
        switch (pos) {
            case 0:
                btnTop1Down.setText("下载");
                break;
            case 1:
                btnTop2Down.setText("下载");
                break;
            case 2:
                btnTop3Down.setText("下载");
                break;
        }
    }

    /**
     * 暂停
     *
     * @param pos
     */
    public void Puse(int pos) {
        switch (pos) {
            case 0:
                btnTop1Down.setText("继续");
                break;
            case 1:
                btnTop2Down.setText("继续");
                break;
            case 2:
                btnTop3Down.setText("继续");
                break;
        }
    }

    /**
     * 下载完成
     *
     * @param pos
     */
    public void Complete(int pos) {
        if (pos != -1) {
            DownDataBean downDataBean = DownManager.getInstance().RealState(listData.get(pos).id);
            if (downDataBean == null) {
                return;
            }
            int taskState = -2;
            //如果有记录  判断当前下载状态
            taskState = Aria.download(this).load(downDataBean.DownUrl).getTaskState();
            //percent的下载状态里没有  安装完成打开状态  需要自行判断
            if (downDataBean.packageName != null && Utils.isInstall(x.app(), downDataBean.packageName)) {
                //根据包名判断如果该游戏已经安装过    下载状态改为已安装 =8
                taskState = 8;
            }
            switch (pos) {
                case 0:
                    if (taskState == 1 || taskState == 8) {
                        if (downDataBean.btnStatus == 8) {
                            btnTop1Down.setText("打开");
                        }
                        if (downDataBean.btnStatus == 1) {
                            btnTop1Down.setText("安装");
                        }
                        if (downDataBean.btnStatus == -2) {
                            btnTop1Down.setText("下载");
                        }
                    }
                    break;
                case 1:
                    if (taskState == 1 || taskState == 8) {
                        if (downDataBean.btnStatus == 8) {
                            btnTop2Down.setText("打开");
                        }
                        if (downDataBean.btnStatus == 1) {
                            btnTop2Down.setText("安装");
                        }
                        if (downDataBean.btnStatus == -2) {
                            btnTop2Down.setText("下载");
                        }
                    }
                    break;
                case 2:
                    if (taskState == 1 || taskState == 8) {
                        if (downDataBean.btnStatus == 8) {
                            btnTop3Down.setText("打开");
                        }
                        if (downDataBean.btnStatus == 1) {
                            btnTop3Down.setText("安装");
                        }
                        if (downDataBean.btnStatus == -2) {
                            btnTop3Down.setText("下载");
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 下载中
     *
     * @param pos
     */
    public void Runing(int pos, int jindu) {
        switch (pos) {
            case 0:
                btnTop1Down.setText(jindu + "%");
                break;
            case 1:
                btnTop2Down.setText(jindu + "%");
                break;
            case 2:
                btnTop3Down.setText(jindu + "%");
                break;
        }
    }

    /**
     * 根据下载url判断当前应当刷新的位置
     *
     * @param key
     * @return -1  代表下载的不是头部3个游戏
     */
    public int getPos(String key) {
        try {
            if (db == null) {
                db = DbUtils.getDB();
            }
            DownDataBean downUrl = db.selector(DownDataBean.class).where("DownUrl", "=", key).findFirst();
            if (downUrl != null) {
                for (int i = 0; i < listData.size(); i++) {
                    GameInfo gameInfo = listData.get(i);
                    if (gameInfo.id == downUrl.id) {
                        return i;
                    }
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 下载按钮的执行动作
     */
    public void xiazai(int pos) {
        if (Utils.getLoginUser() != null) {
            GameInfo gameInfo = listData.get(pos);
            down = DownManager.getInstance().getDownBean(gameInfo.id);
            int percent = -2;
            if (down != null) {
                //如果有记录  判断当前下载状态
                percent = Aria.download(this).load(down.DownUrl).getTaskState();
                //percent的下载状态里没有  安装完成打开状态  需要自行判断
                if (down.packageName != null && Utils.isInstall(x.app(), down.packageName)) {
                    //根据包名判断如果该游戏已经安装过    下载状态改为已安装 =8
                    percent = 8;
                }
            }
            switch (percent) {
                //未下载
                case -2:
                    if (suo == -1 || suo != pos) {
                        suo = pos;
                        Utils.getDownLoadUrl(handler2, gameInfo.id);
                        taskWait2(pos);
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
                    Complete(pos);
                    break;
                //下载失败
                case 0:
                    DownManager.getInstance().down(down);
                    break;
            }
        } else {
            new DialogGoLogin(getActivity(), R.style.MyDialogStyle, "登录后可开始游戏~").show();
        }
    }

    /**
     * 获得下载链接
     */
    @SuppressLint("HandlerLeak")
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //解析网络请求 获取游戏链接
                    DataBean dataBean = HttpUtils.DNSdownUrl(msg.obj.toString());
                    if (dataBean != null && !dataBean.url.equals("")) {
                        for (int i = 0; i < listData.size(); i++) {
                            GameInfo gameInfo = listData.get(i);
                            if (gameInfo.id == dataBean.id) {
                                DownDataBean downDataBean = new DownDataBean();
                                downDataBean.id = gameInfo.id;
                                downDataBean.DownUrl = dataBean.url;
                                DownManager.getInstance().down(downDataBean);

                                gameInfo.record_id = dataBean.record_id;
                                DownManager.getInstance().copy(gameInfo);
                            }
                        }
                    } else {
                        Utils.TS("游戏链接为空");
                    }
                    suo = -1;
                    break;
                case 2:
                    Utils.TS("获取下载链接失败");
                    suo = -1;
                    break;
            }
        }
    };


    /**
     * 跳转到详情页
     *
     * @param pos
     */
    private void jumpGameDetail(int pos) {
        Intent intent = new Intent(getActivity(), GameDetailActivity.class);
        intent.putExtra("game_id", listData.get(pos).id);
        getActivity().startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listData.size() != 0) {
            if (listData.size() == 1) {
                Complete(0);
            }
            if (listData.size() == 2) {
                Complete(0);
                Complete(1);
            }
            if (listData.size() == 3) {
                Complete(0);
                Complete(1);
                Complete(2);
            }
            if (listData.size() > 3) {
                Complete(0);
                Complete(1);
                Complete(2);
                rackingAdapter.ConfirmationState();
            }
        }
    }


}




