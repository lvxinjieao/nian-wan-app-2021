package com.nian.wan.app.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.gson.Gson;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;
import com.nian.wan.app.R;
import com.nian.wan.app.adapter.GiftDetListAdapter;
import com.nian.wan.app.bean.GameGiftBeam;
import com.nian.wan.app.bean.ShouCangBean;
import com.nian.wan.app.bean.StartGameBean;
import com.nian.wan.app.bean.UserInfo;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogGoLogin;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 礼包详情
 */
public class GiftDetActivity extends BaseFragmentActivity {
    @BindView(R.id.tv_play)
    TextView tvPlay;
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.icon_gift)
    ImageView iconGift;
    @BindView(R.id.shoucang)
    ImageView shoucang;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.ren)
    TextView ren;
    @BindView(R.id.geshu)
    TextView geshu;
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.springview)
    SpringView springview;
    @BindView(R.id.tvhint)
    TextView tvhint;

    private GiftDetListAdapter adapter;
    private GameGiftBeam gameGiftBeam;
    private List<GameGiftBeam.MsgEntity.GiftEntity> listDate;
    private int game_id;
    private int limit = 1;
    private StartGameBean startGameBean;
    private ShouCangBean shouCangBean;
    private String gameURL;

    @Override
    public void initView() {
        setContentView(R.layout.activity_giftdet);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this,tou);
        startGameBean = new StartGameBean();
        gameGiftBeam = new GameGiftBeam();
        shouCangBean = new ShouCangBean();
        listDate = new ArrayList<>();
        title.setText("游戏礼包");
        game_id = getIntent().getIntExtra("game_id",-1);
        initAndReflsh();
        adapter = new GiftDetListAdapter(GiftDetActivity.this);
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setHeader(new SimpleHeader(this));
        springview.setFooter(new SimpleFooter(this));
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GiftDetActivity.this,GiftDetTweActivity.class);
                intent.putExtra("gift_id",gameGiftBeam.getMsg().getGift().get(position).getGift_id());
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        initAndReflsh();
    }


    //SpringView相关设置
    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            initAndReflsh();
        }

        @Override
        public void onLoadmore() {
            onLoadMord();
        }
    };

    //下拉刷新相关请求
    private void initAndReflsh() {
        UserInfo userInfo = Utils.getLoginUser();
        if (userInfo!=null) {
            limit = 1;
            Map<String, String> map = new HashMap<>();
            map.put("game_id", String.valueOf(game_id));
            map.put("p", limit + "");
            map.put("token", userInfo.token);
            HttpConstant.POST(handler, HttpConstant.gameGiftList, map, false);
        }else {
            new DialogGoLogin(this,R.style.MyDialogStyle,"暂时无法玩游戏哦~T_T~").show();
        }
    }

    //上拉加载相关请求
    private void onLoadMord() {
        UserInfo userInfo = Utils.getLoginUser();
        if (userInfo!=null){
            limit = limit+1;
            Map<String, String> map = new HashMap<>();
            map.put("p", limit + "");
            map.put("game_id",String.valueOf(game_id));
            map.put("token", userInfo.token);
            HttpConstant.POST(mHandler, HttpConstant.gameGiftList, map, false);
        }else {
            new DialogGoLogin(this,R.style.MyDialogStyle,"暂时无法玩游戏哦~T_T~").show();
        }
    }


    //按钮点击事件
    @OnClick({R.id.tv_play, R.id.back,R.id.shoucang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_play:
                StartGame();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.shoucang:
//                if (gameGiftBeam.getMsg().getGame().getIs_collect() == 0){
//                    ShouCang();
//                    initAndReflsh();
//                }else {
//                    QuXiaoShouCang();
//                    initAndReflsh();
//                }
                break;
        }
    }




    /**
     * 刚进来的请求
     */
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (springview!=null){
                springview.onFinishFreshAndLoad();
            }
            switch (msg.what){
                case 1:
                    gameGiftBeam = new Gson().fromJson(msg.obj.toString(),GameGiftBeam.class);

                    if (gameGiftBeam.getStatus() == 1){
                        if(gameGiftBeam.getMsg().getGame()!=null){
                            Glide.with(x.app()).load(gameGiftBeam.getMsg().getGame().getIcon()).transform(new RoundedCorners(6)).into(iconGift);
                            name.setText(gameGiftBeam.getMsg().getGame().getGame_name());
                            ren.setText(String.valueOf(gameGiftBeam.getMsg().getGame().getPlay_num())+"人在玩");
                            geshu.setText(gameGiftBeam.getMsg().getGame().getGift_count());
                            type.setText(gameGiftBeam.getMsg().getGame().getGame_type_name());
                            if (gameGiftBeam.getMsg().getGame().getIs_collect()==1){
                                shoucang.setImageResource(R.drawable.collection_select);
                                tvhint.setText("已收藏");
                            }else {
                                shoucang.setImageResource(R.drawable.collection_unselect);
                                tvhint.setText("收藏");
                            }
                        }

                        if (gameGiftBeam.getMsg().getGift()!=null){
                            listDate.clear();
                            listDate.addAll(gameGiftBeam.getMsg().getGift());
                            adapter.setList(listDate);
                            adapter.notifyDataSetChanged();
                        }else {
                            ToastUtil.showToast("该游戏下暂时还没有礼包哦~");
                        }
                    }else {
                        ToastUtil.showToast("更多礼包正在路上~");
                    }
                    break;
                case 2:
                    ToastUtil.showToast("网络缓慢");
                    break;
            }
        }
    };



    /**
     * 上拉加载
     */
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            springview.onFinishFreshAndLoad();
            switch (msg.what) {
                case 1:
                    break;
                case 2:
                    springview.setVisibility(View.GONE);
                    //ToastUtil.showToast("网络异常");
                    break;
                default:
                    break;
            }
        }
    };



    //开始游戏网络请求
    private void StartGame(){
        UserInfo userInfo = Utils.getLoginUser();
        if (userInfo!=null){
            Map<String,String> map = new HashMap<>();
            map.put("game_id",String.valueOf(game_id));
            map.put("token",userInfo.token);
            HttpConstant.POST(GameHandler, HttpConstant.StartGame,map,false);
        }else {
            new DialogGoLogin(this,R.style.MyDialogStyle,"登录后可开始游戏~").show();
        }
    }


    /**
     * 开始游戏
     */
    Handler GameHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.e("开始游戏传来的数据",msg.obj.toString());
                    startGameBean = new Gson().fromJson(msg.obj.toString(),StartGameBean.class);
                    if (startGameBean.getStatus()==1){
                        if (startGameBean.getMsg().getUrl()!=null){
                            Intent intent =new Intent(GiftDetActivity.this, WebActivity.class);
                            gameURL = startGameBean.getMsg().getUrl();
                            intent.putExtra("url",gameURL);
                            startActivity(intent);
                        }
                    }else {
                        ToastUtil.showToast("游戏链接为空");
                    }
                    break;
                case 2:
                    ToastUtil.showToast("请求失败~");
                    break;
                default:
                    break;
            }
        }
    };


//    //收藏游戏网络请求
//    private void ShouCang(){
//        UserInfo userInfo = Utils.getLoginUser();
//        if (userInfo!=null){
//            Map<String,String> map = new HashMap<>();
//            map.put("game_id",String.valueOf(game_id));
//            map.put("token",userInfo.token);
//            HttpCom.POST(scHander,HttpCom.ShouCang,map,false);
//        }else {
//            new DialogGoLogin(this,R.style.MyDialogStyleBottom,"登陆后可收藏游戏~").show();
//        }
//    }
//
//    Handler scHander = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 1:
//                    shouCangBean = new Gson().fromJson(msg.obj.toString(),ShouCangBean.class);
//                    Log.e("收藏返回的数据",msg.obj.toString());
//                    if (shouCangBean.getStatus()==1){
//                        new DialogCollection(GiftDetActivity.this,R.style.MyDialogStyleBottom,gameURL).show();
//                        shoucang.setImageResource(R.drawable.home_game_btn_collection_pre);
//                        tvhint.setText("已收藏");
//                        initAndReflsh();
//                    }else {
//                        ToastUtil.showToast(shouCangBean.getReturn_code());
//                    }
//                    break;
//                case 2:
//                    ToastUtil.showToast("请求失败");
//                    break;
//            }
//        }
//    };
//
//
//    /**
//     * 取消收藏
//     */
//    private void QuXiaoShouCang(){
//        UserInfo userInfo = Utils.getLoginUser();
//        if (userInfo!=null){
//            HashMap<String, String> map = new HashMap<>();
//            map.put("token",userInfo.token);
//            map.put("ids", game_id+"");
//            HttpCom.POST(qxhandler, HttpCom.DeleteShoucangUrl, map, false);
//        }
//    }
//
//
//    /**
//     * 取消收藏
//     */
//    Handler qxhandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 1:
//                    Log.e("取消收藏返回的数据",msg.obj.toString());
//                    quXiaoShouCangBean = new Gson().fromJson(msg.obj.toString(),QuXiaoShouCangBean.class);
//                    if (quXiaoShouCangBean.getStatus()==1){
//                        ToastUtil.showToast("取消收藏成功~");
//                        shoucang.setImageResource(R.drawable.home_game_btn_collection_nor);
//                        tvhint.setText("收藏");
//                        initAndReflsh();
//                    }else {
//                        ToastUtil.showToast(quXiaoShouCangBean.getReturn_code());
//                    }
//                    break;
//                case 2:
//                    ToastUtil.showToast("网络异常");
//                    break;
//            }
//        }
//    };

}
