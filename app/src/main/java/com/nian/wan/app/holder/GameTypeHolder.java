package com.nian.wan.app.holder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.ShapeImageView;

import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/15.
 */

public class GameTypeHolder extends BaseHolder<GameInfo> {
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_fanli)
    TextView tvFanli;
    @BindView(R.id.img_icon)
    ShapeImageView imgIcon;
    @BindView(R.id.tv_game_name)
    TextView tvGameName;
    @BindView(R.id.rl_game_description)
    RelativeLayout rlGameDescription;
    @BindView(R.id.tv_package)
    TextView tvPackage;
    @BindView(R.id.down_progressbar)
    RoundCornerProgressBar downProgressbar;
    @BindView(R.id.down_spend)
    TextView downSpend;
    @BindView(R.id.down_hint)
    TextView downHint;
    @BindView(R.id.down_layout)
    RelativeLayout downLayout;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.img_line)
    ImageView imgLine;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;

    private Activity activity;
    private GameInfo gameInfo;
    private boolean isH5Game;

    @SuppressLint("SetTextI18n")
    @Override
    protected void refreshView(GameInfo gameInfo, int position, Activity activity) {
        this.activity = activity;
        this.gameInfo = gameInfo;


        if (isH5Game){
            tvStart.setText("开始");
            tvHint.setText(gameInfo.type);
            tvHint.setText("人在玩");
        }else {
            tvStart.setText("下载");
            if (!TextUtils.isEmpty(gameInfo.size)){
                tvHint.setText(gameInfo.size);
            }else {
                tvHint.setText("0M");
            }
            tvHint.setText("下载");
        }
        Glide.with(x.app()).load(gameInfo.iconurl).error(R.drawable.default_picture).into(imgIcon);
        String jieQu = Utils.getJieQu(gameInfo.name);
        if (jieQu.length()>=12){
            tvGameName.setText(jieQu.substring(0,12) + "...");
        }else {
            tvGameName.setText(jieQu);
        }
        tvMsg.setText(gameInfo.features);
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isH5Game){
                    StartGame();
                }else {
                    //下载
                }
            }
        });
    }


    public void setH5Game(boolean h5Game) {
        isH5Game = h5Game;
    }


    @Override
    protected View initView() {
        View inflate = LinearLayout.inflate(x.app(), R.layout.gameholder_home_item, null);
        ButterKnife.bind(this, inflate);
        inflate.setTag(this);
        return inflate;
    }


    //开始游戏网络请求
    private void StartGame() {
    }

}
