package com.nian.wan.app.activity;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.FeedBackDialog;
import com.mc.developmentkit.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 意见反馈Activity
 * Created by Administrator on 2017/4/26.
 */

public class FeedbackActivity extends BaseFragmentActivity {
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.zhuti)
    EditText zhuti;
    @BindView(R.id.shengyu)
    TextView shengyu;
    @BindView(R.id.con)
    EditText con;
    @BindView(R.id.lianxi)
    EditText lianxi;
    @BindView(R.id.tijiao)
    TextView tijiao;
    @BindView(R.id.weitijiao)
    LinearLayout weitijiao;
    @BindView(R.id.backzhong)
    TextView backzhong;
    @BindView(R.id.tijiao_ok)
    LinearLayout tijiaoOk;

    @Override
    public void initView() {
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        title.setText("意见反馈");

        con.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    shengyu.setText("还可以输入"+String.valueOf(300-s.length())+"字");
                }else{
                    shengyu.setText("还可以输入300字");
                }
            }
        });
    }

    @OnClick({R.id.back, R.id.tijiao,R.id.backzhong})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tijiao:
                UpData();
                break;
            case R.id.backzhong:
                finish();
                break;
        }
    }

    /**
     * 提交
     */
    private void UpData() {
        String zhu = zhuti.getText().toString();
        String neirong = con.getText().toString();
        String lian = lianxi.getText().toString();
        if (zhu.equals("")) {
            ToastUtil.showToast("请输入主题");
            return;
        }
        if (neirong.equals("")) {
            ToastUtil.showToast("请输入内容");
            return;
        }
        if (lian.equals("")) {
            ToastUtil.showToast("请输入联系方式");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token",Utils.getLoginUser().token);
        map.put("title",zhu);
        map.put("content",neirong);
        map.put("contact",lian);
        HttpConstant.POST(handler, HttpConstant.feedback,map,false);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("意见反馈json", msg.obj.toString());
                    try {
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        int status = jsonObject.getInt("status");
                        if(status==1){
                            weitijiao.setVisibility(View.GONE);
                            tijiaoOk.setVisibility(View.VISIBLE);
                        }else{
                            FeedBackDialog feedBackDialog = new FeedBackDialog(FeedbackActivity.this, R.style.MyDialogStyle);
                            feedBackDialog.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("解析意见反馈失败", e.toString());
                    }
                    break;
                case 2:
                    FeedBackDialog feedBackDialog = new FeedBackDialog(FeedbackActivity.this, R.style.MyDialogStyle);
                    feedBackDialog.show();
                    break;
            }
        }
    };
}
