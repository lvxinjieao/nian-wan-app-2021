package com.nian.wan.app.holder;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.bean.GameGiftBeam;
import com.nian.wan.app.bean.LingQuGiftBean;
import com.nian.wan.app.bean.StartGameBean;
import com.mc.developmentkit.utils.MCUtils;

import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 游戏礼包Holder
 */
public class GiftDetHolder extends BaseHolder<GameGiftBeam.MsgEntity.GiftEntity> {
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.shuliang)
    TextView shuliang;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.con)
    TextView con;
    private GameGiftBeam.MsgEntity.GiftEntity gamegiftbean;
    private LingQuGiftBean lingqubean;
    private String token;
    private Context mContext;
    private StartGameBean startGameBean;
    private String url;
    private String endTime;

    @Override
    protected void refreshView(GameGiftBeam.MsgEntity.GiftEntity s, int position, Activity activity) {
        this.mContext = activity;
        this.gamegiftbean = s;

        startGameBean = new StartGameBean();
        lingqubean = new LingQuGiftBean();

        if (gamegiftbean.getEnd_time() .equals("0")){
            endTime = "永久";
        }else {
            endTime = MCUtils.getDataYMD(gamegiftbean.getEnd_time());
        }
        name.setText(gamegiftbean.getGiftbag_name());
        shuliang.setText(String.valueOf(gamegiftbean.getWei())+"/"+String.valueOf(gamegiftbean.getAll()));
        time.setText("有效期："+ MCUtils.getDataYMD(gamegiftbean.getStart_time())+" 至 "+endTime);
        con.setText("礼包内容："+gamegiftbean.getDesribe());
//        progressbar .setProgress((int) (((double)gamegiftbean.getWei()/gamegiftbean.getAll())*100)); //设置progressBar的进度条为当前礼包剩余数/礼包总数


    }


    @Override
    protected View initView() {
        try {
            View inflate = LinearLayout.inflate(x.app(), R.layout.holder_gift_det, null);
            ButterKnife.bind(this, inflate);
            inflate.setTag(this);

            return inflate;

        }catch (Exception e){
            Log.e("GiftDetHolder",e.toString());
            return null;
        }
    }

}
