package com.nian.wan.app.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 更换手机号
 * Created by Administrator on 2017/4/26.
 */

public class ReplacePhoneActivity extends BaseFragmentActivity {
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.phone)
    TextView phone;
//    @BindView(R.id.genghuan)
    TextView genghuan;

    @Override
    public void initView() {
        setContentView(R.layout.activity_replacephone);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this,tou);
        title.setText("绑定手机号");
        phone.setText(Utils.getLoginUser().phone);
    }

/*    @OnClick({R.id.back, R.id.genghuan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.genghuan:
                HashMap<String, String> map = new HashMap<>();
                map.put("token",Utils.getLoginUser().token);
                HttpCom.POST_2(mhandler,HttpCom.GetUserMsg,map,false);
                break;
        }
    }*/


    /**
     * 更新用户信息
     */
    Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    boolean b = HttpUtils.DNSUserMsg(msg.obj.toString());
                    if(b&&!Utils.getLoginUser().phone.equals("")){
                        startActivity(new Intent(ReplacePhoneActivity.this,VerificationPhoneActivity.class));
                        finish();
                    }else{
                        finish();
                    }
                    break;
                case 2:

                    break;
                default:
                    break;
            }
        }
    };
}
