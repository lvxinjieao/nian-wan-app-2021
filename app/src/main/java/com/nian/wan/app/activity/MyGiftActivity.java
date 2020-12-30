package com.nian.wan.app.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.IsCheckBean;
import com.nian.wan.app.bean.MyGiftBean;
import com.nian.wan.app.utils.Utils;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/4/26.
 */

public class MyGiftActivity extends BaseFragmentActivity {

    @BindView(R.id.tou)
    ImageView tou;

    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.listView_my_gift)
    ListView listViewMyGift;
    @BindView(R.id.springview)
    SpringView springview;
    @BindView(R.id.tv_lingqu)
    TextView tvLingqu;
    @BindView(R.id.layout_no_gift)
    LinearLayout layoutNoGift;
    private int limit = 1;
    private MyGiftListAdapter myGiftListAdapter;
    private MyGiftBean myGiftBean;
//    private List<MyGiftBean.MsgEntity> listDate;

    @Override
    public void initView() {
        setContentView(R.layout.activity_mygift);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this,tou);
        title.setText("我的礼包");
        myGiftBean = new MyGiftBean();
//        listDate = new ArrayList<>();
        initSpringViewStyle();
        initAndReflsh();
    }

    private void initSpringViewStyle() {
//        myGiftListAdapter = new MyGiftListAdapter(listDate,MyGiftActivity.this);
        listViewMyGift.setAdapter(myGiftListAdapter);
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setHeader(new SimpleHeader(this));
        springview.setFooter(new SimpleFooter(this));
    }



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


    private void initAndReflsh() {
        /*limit = 1;
        Map<String, String> map = new HashMap<>();
        map.put("p", limit + "");
        map.put("token", Utils.getLoginUser().token);
        HttpCom.POST(handler, HttpCom.MyGiftUrl, map, false);*/
    }

    private void onLoadMord() {
     /*   limit = ++limit;
        Map<String, String> map = new HashMap<>();
        map.put("p", limit + "");
        map.put("token", Utils.getLoginUser().token);
        HttpCom.POST(mhandler, HttpCom.MyGiftUrl, map, false);*/
    }


    @OnClick({R.id.back, R.id.tv_lingqu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_lingqu:
                IsCheckBean isCheckBean = new IsCheckBean();
                isCheckBean.i = 3;
                EventBus.getDefault().post(isCheckBean);
                finish();
                break;
        }
    }

    /**
     * 刚进来请求
     */
   /* Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            springview.onFinishFreshAndLoad();
            try {
                switch (msg.what) {
                    case 1:
                        myGiftBean = new Gson().fromJson(msg.obj.toString(), MyGiftBean.class);
                        LogUtils.e("我的礼包json", msg.obj.toString());
                        if (myGiftBean.getMsg() != null) {
                            listDate.clear();
                            listDate.addAll(myGiftBean.getMsg());
                            myGiftListAdapter.setList(listDate);
                            myGiftListAdapter.notifyDataSetChanged();
                        } else {
                            layoutNoGift.setVisibility(View.VISIBLE);
                            springview.setVisibility(View.GONE);
                        }
                        break;
                    case 2:
                        ToastUtil.showToast("网络异常");
                        break;
                }
            }catch (Exception e){
                layoutNoGift.setVisibility(View.VISIBLE);
                springview.setVisibility(View.GONE);
                Log.e("我的礼包Handler崩了~~~",e.toString());
            }
        }
    };*/

    /**
     * 加载更多
     */
   /* Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            springview.onFinishFreshAndLoad();
            switch (msg.what) {
                case 1:
                    LogUtils.e("我的礼包加载更多json",msg.obj.toString());
                    break;
                case 2:

                    break;
            }
        }
    };*/
}
