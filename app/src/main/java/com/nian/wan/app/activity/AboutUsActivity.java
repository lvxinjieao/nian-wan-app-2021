package com.nian.wan.app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.PhoneUtils;
import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.bean.AboutUsData;
import com.nian.wan.app.utils.DatabaseOpenHelper;
import com.nian.wan.app.utils.Utils;

import org.xutils.DbManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 联系我们
 */
public class AboutUsActivity extends FragmentActivity {

    @BindView(R.id.tou)
    ImageView tou;

    @BindView(R.id.back)
    RelativeLayout back;

    @BindView(R.id.title)
    TextView title;



    @BindView(R.id.app_icon)
    ImageView app_icon;

    @BindView(R.id.app_name)
    TextView appName;

    @BindView(R.id.tv_current_version)
    TextView tv_current_version;

    @BindView(R.id.tv_least_version)
    TextView tv_least_version;

    @BindView(R.id.tv_qq)
    TextView tvQq;

    @BindView(R.id.layout_qq)
    RelativeLayout layoutQq;

    @BindView(R.id.tv_qun)
    TextView tvQun;

    @BindView(R.id.layout_qun)
    RelativeLayout layoutQun;

    @BindView(R.id.layout_phone)
    RelativeLayout layoutPhone;

    @BindView(R.id.tv_hezuo)
    TextView tvHezuo;

    @BindView(R.id.layout_hezuo)
    RelativeLayout layoutHezuo;

    @BindView(R.id.tv_wangzhi)
    TextView tvWangzhi;

    @BindView(R.id.layout_wangzhan)
    RelativeLayout layoutWangzhan;

    @BindView(R.id.layout_xieyi)
    RelativeLayout layoutXieyi;

    @BindView(R.id.gongsi)
    TextView gongsi;

    @BindView(R.id.dizi)
    TextView dizi;

    @BindView(R.id.tv_dianhua)
    TextView tvDianhua;

    private AboutUsData aboutUsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        title.setText("联系我们");
        tv_current_version.setText("当前版本：" + Utils.getVersionName(this));
        initdata();
    }

    private void initdata() {
        DbManager db = DatabaseOpenHelper.getInstance();
        try {
            aboutUsData = db.findById(AboutUsData.class, 2);
            appName.setText(aboutUsData.getAPP_NAME());
            tvQq.setText("客服QQ：" + aboutUsData.getPC_SET_SERVER_QQ());
            tvQun.setText("官方玩家群：" + aboutUsData.getPC_COMMUNICATION_GROUP());
            tvDianhua.setText("客服电话：" + aboutUsData.getPC_SET_SERVER_TEL());
            tvHezuo.setText("商务合作：" + aboutUsData.getAPP_COOPERATION());
            tvWangzhi.setText("官方网址：" + aboutUsData.getPC_OFFICIAL_SITE());
            if (Utils.getVersionCode(this) < Integer.valueOf(aboutUsData.getAPP_VERSION()))
                tv_least_version.setText("最新版本：" + aboutUsData.getAPP_VERSION_NAME());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.back, R.id.layout_qq, R.id.layout_qun, R.id.layout_phone, R.id.layout_hezuo, R.id.layout_wangzhan, R.id.layout_xieyi})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.back:
                this.finish();
                break;

            case R.id.layout_qq:
                if (checkApkExist(this, "com.tencent.mobileqq")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + aboutUsData.getPC_SET_SERVER_QQ() + "&version=1")));
                } else {
                    ToastUtil.showToast("本机未安装QQ应用");
                }
                break;

            case R.id.layout_qun:
                joinQQGroup(aboutUsData.getPC_COMMUNICATION_GROUP());
                break;

            case R.id.layout_phone:
                PhoneUtils.dial(aboutUsData.getPC_SET_SERVER_TEL());
                break;

            case R.id.layout_hezuo:
                if (checkApkExist(this, "com.tencent.mobileqq")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + aboutUsData.getAPP_COOPERATION() + "&version=1")));
                } else {
                    ToastUtil.showToast("本机未安装QQ应用");
                }
                break;

            case R.id.layout_wangzhan:
                Intent intent = new Intent();
                intent.setClass(this, SignWebActivity.class);
                intent.putExtra("url", aboutUsData.getPC_OFFICIAL_SITE());
                intent.putExtra("name", "官网");
                startActivity(intent);
                break;

            case R.id.layout_xieyi:
                Intent intent1 = new Intent();
                intent1.setClass(this, SignWebActivity.class);
                intent1.putExtra("url", aboutUsData.getUSER_AGREEMENT());
                intent1.putExtra("name", "用户协议");
                startActivity(intent1);
                break;
        }
    }


    /****************
     * 发起添加群流程。群号：牛擦协会(125638122) 的 key 为： HD4wt8XxGOIXKUHrvs0wK-d_PPMywLHI
     * 调用 joinQQGroup(HD4wt8XxGOIXKUHrvs0wK-d_PPMywLHI) 即可发起手Q客户端申请加群 牛擦协会(125638122)
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            ToastUtil.showToast("未安装手Q或安装的版本不支持");
            return false;
        }
    }


    /**
     * 一键唤起QQ聊天界面
     *
     * @param context
     * @param packageName
     */
    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
