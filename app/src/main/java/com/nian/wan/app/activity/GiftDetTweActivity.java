package com.nian.wan.app.activity;

import com.nian.wan.app.activity.BaseFragmentActivity;

/**
 * 单个礼包详情
 * Created by Administrator on 2017/5/2.
 */

public class GiftDetTweActivity extends BaseFragmentActivity {
    @Override
    public void initView() {

    }
   /* @BindView(R.id.tv_Start_Game)
    TextView tvPlay;
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.lingqu)
    TextView lingqu;
    @BindView(R.id.icon_gift)
    MyImageView iconGift;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.progressbar)
    RoundCornerProgressBar progressbar;
    @BindView(R.id.shuliang)
    TextView shuliang;
    @BindView(R.id.jiezhi_time)
    TextView jiezhiTime;
    @BindView(R.id.gift_con)
    TextView giftCon;
    @BindView(R.id.gift_time)
    TextView giftTime;
    @BindView(R.id.gift_method)
    TextView giftMethod;

    private String giftid;
    private String gameid;
    private GiftDetBean giftDetBean;
    private LingQuGiftBean lingqubean;
    private StartGameBean startGameBean;
    private String endTime;
    private String url;

    @Override
    public void initView() {
        setContentView(R.layout.activity_gift_dettwe);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this,tou);
        title.setText("礼包详情");
        lingqubean = new LingQuGiftBean();
        startGameBean = new StartGameBean();
        giftid = getIntent().getStringExtra("gift_id");
        getGiftDet();
    }

    @OnClick({R.id.tv_Start_Game, R.id.back,R.id.lingqu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_Start_Game:
                StartGame();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.lingqu:
                LingQu();
                break;
        }
    }

    private void getGiftDet(){
        Map<String,String> map = new HashMap<>();
        map.put("gift_id",giftid);
        HttpCom.POST_2(handler, HttpCom.giftInfo,map,false);
    }

    *//**
     * 刚进来的请求
     *//*
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    giftDetBean = new Gson().fromJson(msg.obj.toString(), GiftDetBean.class);
                    Log.e("礼包详情", msg.obj.toString());
                    if (giftDetBean.getMsg()!=null){
                        gameid = giftDetBean.getMsg().getGame_id();
                        Utils.Fill(iconGift,giftDetBean.getMsg().getIcon());
                        name.setText(giftDetBean.getMsg().getGame_name());
                        progressbar .setProgress((int) (((double)giftDetBean.getMsg().getWnovice()/giftDetBean.getMsg().getAllcount_novice())*100)); //设置progressBar的进度条为当前礼包剩余数/礼包总数
                        if (giftDetBean.getMsg().getEnd_time() .equals("0")){
                            endTime = "永久";
                        }else {
                            endTime = MCUtils.getDataYMD(giftDetBean.getMsg().getEnd_time());
                        }
                        jiezhiTime.setText("截至时间："+endTime);
                        giftCon.setText(giftDetBean.getMsg().getDesribe());
                        giftTime.setText(MCUtils.getDataYMD(giftDetBean.getMsg().getStart_time())+" 至 "+endTime);
                        giftMethod.setText(giftDetBean.getMsg().getDigest());
                        shuliang.setText(String.valueOf(giftDetBean.getMsg().getWnovice())+"/"+String.valueOf(giftDetBean.getMsg().getAllcount_novice()));
                    }else {
                        ToastUtil.showToast("礼包内容请求为空");
                    }
                    break;
                case 2:
                    ToastUtil.showToast("网络异常");
                    break;
            }
        }
    };


    //领取礼包网络请求
    private void LingQu(){
        UserInfo loginUser = Utils.getLoginUser();
        if(loginUser!=null){
            HashMap<String, String> map = new HashMap<>();
            map.put("gift_id",giftid);
            map.put("token",loginUser.token);
            map.put("game_id",gameid);
            HttpCom.POST(mhandler,HttpCom.ReceiveGiftUrl,map,false);
        }else {
            new DialogGoLogin(this,R.style.MyDialogStyleBottom,"登陆后可领取游戏礼包~").show();
        }
    }

    *//**
     * 领取礼包
     *//*
    Handler mhandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    lingqubean = new Gson().fromJson(msg.obj.toString(),LingQuGiftBean.class);
                    Log.e("领取礼包返回的数据",msg.obj.toString());
                    if(lingqubean.getStatus()==1){
                        if (lingqubean.getMsg().getNovice()!=null){
                            String jihuoma = lingqubean.getMsg().getNovice();
                            GetUrl();
                            new DialogGiftCheng(GiftDetTweActivity.this,R.style.MyDialogStyleBottom,jihuoma,url).show();
                            getGiftDet();
                        }
                    }else {
                        ToastUtil.showToast(lingqubean.getReturn_code()+"");
                    }

                    break;
                case 2:
                    new DialogGiftDefeated(GiftDetTweActivity.this,R.style.MyDialogStyleBottom);
                    break;
            }
        }
    };


    //开始游戏网络请求
    private void StartGame(){
        UserInfo loginUser = Utils.getLoginUser();
        if (loginUser!=null){
            Map<String,String> map = new HashMap<>();
            map.put("game_id",gameid);
            map.put("token",loginUser.token);
            HttpCom.POST(GameHandler,HttpCom.StartGame,map,false);
        }else {
            new DialogGoLogin(this,R.style.MyDialogStyleBottom,"登录后可开始游戏~").show();
        }

    }


    *//**
     * 开始游戏
     *//*
    Handler GameHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.e("开始游戏传来的数据",msg.obj.toString());
                    startGameBean = new Gson().fromJson(msg.obj.toString(),StartGameBean.class);
                    if (startGameBean.getStatus()==1){
                        if (startGameBean.getMsg().getUrl()!=null){
                            Intent intent =new Intent(GiftDetTweActivity.this, com.xigu.activity.LoadH5GameActivity.class);
                            intent.putExtra("url",startGameBean.getMsg().getUrl());
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


    //获得游戏URL
    private void GetUrl(){
        UserInfo loginUser = Utils.getLoginUser();
        if (loginUser!=null){
            Map<String,String> map = new HashMap<>();
            map.put("game_id",gameid);
            map.put("token",loginUser.token);
            HttpCom.POST(URLHandler,HttpCom.StartGame,map,false);
        }else {
            new DialogGoLogin(GiftDetTweActivity.this,R.style.MyDialogStyleBottom,"请先登录~");
        }

    }


    *//**
     * 获得游戏URL
     *//*
    Handler URLHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.e("获得游戏URL传来的数据",msg.obj.toString());
                    startGameBean = new Gson().fromJson(msg.obj.toString(),StartGameBean.class);
                    if (startGameBean.getStatus()==1){
                        if (startGameBean.getMsg().getUrl()!=null){
                            url = startGameBean.getMsg().getUrl();
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
    };*/



}
