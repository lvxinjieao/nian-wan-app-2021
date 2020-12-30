package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.LoadDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：兑换平台币Activity
 */
public class ExchangePTBActivity extends BaseFragmentActivity {
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.tv_Proportion)
    TextView tvProportion;
    @BindView(R.id.et_num)
    EditText etNum;
    @BindView(R.id.tv_point)
    TextView tvPoint;
    @BindView(R.id.btn_duihuan)
    TextView btnDuihuan;

    private LoadDialog loadDialog;

    @Override
    public void initView() {
        setContentView(R.layout.activity_exchange_ptb);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this,tou);
        title.setVisibility(View.VISIBLE);
        title.setText("兑换平台币");
        etNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable.toString()) && editable.toString().length()>0){
                    tvPoint.setText(String.valueOf(Integer.valueOf(editable.toString()) * 100));
                }else {
                    tvPoint.setText("——");
                }
            }
        });
    }


    @OnClick({R.id.back, R.id.btn_duihuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_duihuan:
                if (!TextUtils.isEmpty(etNum.getText().toString()) && Integer.valueOf(etNum.getText().toString())!=0){
                    showDialog();
                    duihuanPTB();
                }else {
                    ToastUtil.showToast("请输入兑换数量");
                }
                break;
        }
    }


    /**
     * 兑换平台币
     */
    private void duihuanPTB(){
        Map<String,String> map = new HashMap<>();
        map.put("num",etNum.getText().toString());
        if (Utils.getPersistentUserInfo() != null){
            map.put("token",Utils.getPersistentUserInfo().token);
        }
        HttpConstant.POST(handler, HttpConstant.DuiHuanPTB,map,false);
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            dismissDialog();
            switch (msg.what){
                case 1:
                    try {
                        Log.e("兑换平台币",msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        int code = jsonObject.getInt("code");
                        if (code == 200){
                            finish();
                            ToastUtil.showToast("兑换成功");
                        }else {
                            ToastUtil.showToast(jsonObject.getString("msg"));
                        }
                    } catch (JSONException e) {
                        Log.e("兑换平台币数据异常",e.toString());
                        ToastUtil.showToast("数据异常");
                    }
                    break;
                case 2:
                    //ToastUtil.showToast("网络异常");
                    break;
            }
        }
    };


    private void showDialog(){
        if (loadDialog == null){
            loadDialog = new LoadDialog(ExchangePTBActivity.this,R.style.MyDialogStyle);
        }
        loadDialog.setCancelable(false);
        loadDialog.show();
    }

    private void dismissDialog(){
        if (loadDialog != null){
            loadDialog.dismiss();
        }
    }

}
