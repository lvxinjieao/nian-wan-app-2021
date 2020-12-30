package com.nian.wan.app.activity;

/**
 * 绑定手机号
 */

public class BangdingPhoneActivity extends BaseFragmentActivity {
    @Override
    public void initView() {

    }
   /* @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_yanzheng)
    EditText etYanzheng;
    @BindView(R.id.getyzm)
    TextView getyzm;
    @BindView(R.id.miao)
    TextView miao;
    @BindView(R.id.zhong)
    RelativeLayout zhong;
    @BindView(R.id.tijiao)
    TextView tijiao;
    private boolean niu = true;
    private TimeCount time;

    @Override
    public void initView() {
        setContentView(R.layout.activity_bangphone);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this,tou);
        title.setText("绑定手机");
    }

    @OnClick({R.id.back, R.id.tijiao,R.id.getyzm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tijiao:
                UpData();
                break;
            case R.id.getyzm:
                GetYzm();
                break;
        }
    }

    *//**
     * 获取验证码
     *//*
    private void GetYzm() {
        String phone = etPhone.getText().toString();
        if(phone.equals("")){
            ToastUtil.showToast("请输入手机号");
            return;
        }
        if(!RegexUtils.isMobileExact(phone)){
            ToastUtil.showToast("请输入正确的手机号码");
            return;
        }
        if(niu){
            time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
            time.start();// 开始计时
            HashMap<String, String> map = new HashMap<>();
            map.put("phone",phone);
            HttpCom.POST_2(mhandler,HttpCom.getPhoneCode,map,false);
        }
    }

    *//**
     * 提交
     *//*
    private void UpData() {
        String phone = etPhone.getText().toString();
        String yanzheng = etYanzheng.getText().toString();
        if(!RegexUtils.isMobileExact(phone)){
            ToastUtil.showToast("请输入正确的手机号码");
            return;
        }
        if(yanzheng.equals("")){
            ToastUtil.showToast("请输入验证码");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token",Utils.getLoginUser().token);
        map.put("phone",phone);
        map.put("vcode",yanzheng);
        HttpCom.POST_2(handler,HttpCom.BangPhone,map,false);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    boolean b = HttpUtils.DNSBangding(msg.obj.toString());
                    if(b){
                        startActivity(new Intent(BangdingPhoneActivity.this,ReplacePhoneActivity.class));
                        finish();
                    }
                    break;
                case 2:

                    break;
            }
        }
    };

    *//**
     * 获取验证码
     *//*
    Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    boolean b = HttpUtils.DNSGetYZM(msg.obj.toString());
                    if(!b){
                        niu = true;
                        getyzm.setVisibility(View.VISIBLE);
                        zhong.setVisibility(View.GONE);
                    }
                    break;
                case 2:
                    ToastUtil.showToast("网络异常");
                    break;
            }
        }
    };



    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onTick(long millisUntilFinished) {
            // 计时过程显示
            niu = false;
            miao.setText(millisUntilFinished / 1000 + "");
            getyzm.setVisibility(View.GONE);
            zhong.setVisibility(View.VISIBLE);
        }
        @Override
        public void onFinish() {// 计时完毕时触发
            niu = true;
            getyzm.setVisibility(View.VISIBLE);
            zhong.setVisibility(View.GONE);
        }
    }*/
}
