package com.nian.wan.app.activity;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 修改密码
 * Created by Administrator on 2017/4/26.
 */

public class ModifyPasswordActivity extends BaseFragmentActivity {
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.cb_yuan)
    CheckBox cbYuan;
    @BindView(R.id.yuan_deleta)
    ImageView yuanDeleta;
    @BindView(R.id.cb_xin)
    CheckBox cbXin;
    @BindView(R.id.xin_deleta)
    ImageView xinDeleta;
    //    @BindView(R.id.cb_queren)
    CheckBox cbQueren;
//    @BindView(R.id.queren_deleta)
//    ImageView querenDeleta;
    @BindView(R.id.tijiao)
    TextView tijiao;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.error)
    LinearLayout error;
    /*  @BindView(R.id.ed_yuan)
      EditText edYuan;
      @BindView(R.id.et_xin)
      EditText etXin;*/
    @BindView(R.id.et_queren)
    EditText etQueren;

    @Override
    public void initView() {
        setContentView(R.layout.activity_modifypassword);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        title.setText("修改密码");
//        initdata();
    }

   /* private void initdata() {
        edYuan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    cbYuan.setVisibility(View.VISIBLE);
                    yuanDeleta.setVisibility(View.VISIBLE);
                }else{
                    cbYuan.setVisibility(View.GONE);
                    yuanDeleta.setVisibility(View.GONE);
                }
            }
        });
        etXin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    cbXin.setVisibility(View.VISIBLE);
                    xinDeleta.setVisibility(View.VISIBLE);
                }else{
                    cbXin.setVisibility(View.GONE);
                    xinDeleta.setVisibility(View.GONE);
                }
            }
        });
        etQueren.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    cbQueren.setVisibility(View.VISIBLE);
                    querenDeleta.setVisibility(View.VISIBLE);
                }else{
                    cbQueren.setVisibility(View.GONE);
                    querenDeleta.setVisibility(View.GONE);
                }
            }
        });
        cbYuan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edYuan.setInputType(InputType.TYPE_CLASS_TEXT);
                }else{
                    edYuan.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        cbXin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    etXin.setInputType(InputType.TYPE_CLASS_TEXT);
                }else{
                    etXin.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        cbQueren.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    etQueren.setInputType(InputType.TYPE_CLASS_TEXT);
                }else{
                    etQueren.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    @OnClick({R.id.back, R.id.yuan_deleta, R.id.xin_deleta, R.id.queren_deleta, R.id.tijiao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.yuan_deleta:
                edYuan.setText("");
                break;
            case R.id.xin_deleta:
                etXin.setText("");
                break;
            case R.id.queren_deleta:
                etQueren.setText("");
                break;
            case R.id.tijiao:
                UpData();
                break;
        }
    }

    private void UpData() {
        String yuan = edYuan.getText().toString();
        String xin = etXin.getText().toString();
        String queren = etQueren.getText().toString();
        if(yuan.equals("")){
            error.setVisibility(View.VISIBLE);
            tvError.setText("请输入原密码");
            return;
        }
        if(xin.equals("")){
            error.setVisibility(View.VISIBLE);
            tvError.setText("请输入新密码");
            return;
        }
        if(queren.equals("")){
            error.setVisibility(View.VISIBLE);
            tvError.setText("请再次输入新密码");
            return;
        }
        if(!xin.equals(queren)){
            error.setVisibility(View.VISIBLE);
            tvError.setText("两次密码不一致");
            return;
        }
        if(xin.length()<6){
            error.setVisibility(View.VISIBLE);
            tvError.setText("密码长度不得小于6位");
            return;
        }

        if(xin.length()>20){
            error.setVisibility(View.VISIBLE);
            tvError.setText("密码长度不得大于20位");
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("token",Utils.getLoginUser().token);
        map.put("password",xin);
        map.put("type","pwd");
        map.put("old_password",yuan);
        HttpCom.POST(handler,HttpCom.ChagePass,map,false);
    }


    *//**
     * 修改密码
     *//*
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    boolean a = HttpUtils.DNSpass(msg.obj.toString());
                    if(a){
                        try {
                            DbManager db = DbUtils.getDB();
                            db.delete(UserInfo.class);
                            startActivity(new Intent(ModifyPasswordActivity.this,LoginActivity.class));
                            SettingActivity.act.finish();
                            finish();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    error.setVisibility(View.VISIBLE);
                    tvError.setText("网络异常");
                    break;
            }
        }
    };*/
}
