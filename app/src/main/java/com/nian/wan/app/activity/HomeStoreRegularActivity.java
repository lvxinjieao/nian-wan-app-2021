package com.nian.wan.app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.adapter.GuiZeAdapter;
import com.nian.wan.app.bean.AboutUsData;
import com.nian.wan.app.bean.RulesBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.PromoteUtils;
import com.nian.wan.app.utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;
import android.widget.RelativeLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Author: XiaYuShi
 * @Date: 2017/11/24
 * @Description: 首页积分规则
 * @Modify By:
 * @ModifyDate:
 */
public class HomeStoreRegularActivity extends BaseFragmentActivity {

    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.img_home_store_score_mission_back)
    ImageView imgHomeStoreScoreMissionBack;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_home_store_score_mission_custom)
    RelativeLayout imgHomeStoreScoreMissionCustom;
    @BindView(R.id.lv_guize)
    ListView lvGuize;

    @Override
    public void initView() {
        setContentView(R.layout.activity_home_store_score_help);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        Type type = new TypeToken<HttpResult<ArrayList<RulesBean>>>() {
        }.getType();
        new HttpUtils<ArrayList<RulesBean>>(type, HttpConstant.Business_City_Rules, null, "商城规则", false) {

            @Override
            protected void _onSuccess(ArrayList<RulesBean> bean, String msg) {
                GuiZeAdapter guiZeAdapter = new GuiZeAdapter(bean);
                lvGuize.setAdapter(guiZeAdapter);
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
     * 获取客服QQ
     */
    private void getQQ() {
        Type type = new TypeToken<HttpResult<AboutUsData>>() {
        }.getType();
        Map<String,String> map = new HashMap();
        map.put(" promote_id", new PromoteUtils().getPromoteId());
        map.put(" type", "1");
        new HttpUtils<AboutUsData>(type, HttpConstant.API_PERSONAL_USER_ABOUT_US, map, "获取客服QQ", false) {

            @Override
            protected void _onSuccess(AboutUsData s, String msg) {
                kefuQQ = s.getPC_SET_SERVER_QQ();
                if (checkApkExist(HomeStoreRegularActivity.this, "com.tencent.mobileqq")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + kefuQQ + "&version=1")));
                } else {
                    ToastUtil.showToast("本机未安装QQ应用");
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

    private String kefuQQ;

    /**
     * 一键唤起QQ聊天界面
     *
     * @param context
     * @param packageName
     * @return
     */
    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @OnClick({R.id.img_home_store_score_mission_custom,R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_home_store_score_mission_custom:
                getQQ();
                break;
            case R.id.back:
                this.finish();
                break;
        }
    }
}
