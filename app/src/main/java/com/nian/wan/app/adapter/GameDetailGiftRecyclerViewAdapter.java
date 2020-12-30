package com.nian.wan.app.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.GetGiftBean;
import com.nian.wan.app.bean.IntoGameGiftBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.PromoteUtils;
import com.nian.wan.app.utils.TimeUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogGetGiftFailed;
import com.nian.wan.app.view.DialogGetGiftSuccess;
import com.nian.wan.app.view.DialogGiftDefeated;
import com.nian.wan.app.view.DialogGoLogin;
import com.nian.wan.app.view.DialogWeChatOfficialAccounts;
import com.google.gson.reflect.TypeToken;
import android.widget.LinearLayout;;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/10
 * @Description: 游戏详情礼包RecyclerView适配器
 * @Modify By:
 * @ModifyDate:
 */
public class GameDetailGiftRecyclerViewAdapter extends RecyclerView
        .Adapter<GameDetailGiftRecyclerViewAdapter.ViewHolder> {


    private ArrayList<IntoGameGiftBean> intoGameGift;
    private WeakReference<Context> mWeakReference;
    private int giftPos;
    private GetGiftBean getGiftBean;
    private ViewHolder viewHolder;
    private Activity activity;

    public GameDetailGiftRecyclerViewAdapter(ArrayList<IntoGameGiftBean> intoGameGift, Activity context) {
        this.intoGameGift = intoGameGift;
        this.activity = context;
        mWeakReference = new WeakReference(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_detail_gift_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvGiftName.setText("[" + Utils.getJieQu(intoGameGift.get(position).getGame_name()) + "]" + intoGameGift.get(position).getGiftbag_name());
        holder.tvGameDetailNumberPrecentMoney.setText(intoGameGift.get(position).getNovice_surplus() + "");

        if (intoGameGift.get(position).getEnd_time().equals("0")){
            holder.tvGameDetailGiftValid.setText("有效期：" + TimeUtils.timesThree(intoGameGift.get(position).getStart_time()) + "~" +"永久");
        }else {
            holder.tvGameDetailGiftValid.setText("有效期：" + TimeUtils.timesThree(intoGameGift.get(position).getStart_time()) + "~" +
                    TimeUtils.timesThree(intoGameGift.get(position).getEnd_time()));
        }
        if(intoGameGift.get(position).getReceived()==1){
            holder.tvLingqu.setText("已领取");
        }else{
            holder.tvLingqu.setText("领取");
        }
        holder.rlGameDetailGetGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giftPos = position;
                viewHolder = holder;
                getGiftRequest(position);
            }
        });
    }


    /**
     * 领取礼包
     *
     * @param packageIndex
     */
    private void getGiftRequest(int packageIndex) {
        if (null != Utils.getPersistentUserInfo()) {
            if(intoGameGift.get(packageIndex).getReceived()==1){
                new DialogGiftDefeated(mWeakReference.get(), R.style.MyDialogStyle).show();
            }else{
                Map<String, String> giftParams = new HashMap<>();
                giftParams.put("gift_id", intoGameGift.get(packageIndex).getGift_id());
                giftParams.put("promote_id", new PromoteUtils().getPromoteId());
                Type type = new TypeToken<HttpResult<String>>() {
                }.getType();
                new HttpUtils<String>(type, HttpConstant.API_GIFT_GET, giftParams, "领取礼包返回数据", true) {
                    @Override
                    protected void _onSuccess(String code, String msg) {
                        new DialogGetGiftSuccess(mWeakReference.get(), R.style.MyDialogStyle, code).show();
                        viewHolder.tvLingqu.setText("已领取");
                    }

                    @Override
                    protected void _onError(String message, int code) {
                        if (code == 1117){
                            DialogWeChatOfficialAccounts dialog = new DialogWeChatOfficialAccounts(activity,R.style.MyDialogStyle,message);
                            dialog.show();
                        }else {
                            new DialogGetGiftFailed(activity, R.style.MyDialogStyle, retryGetGiftHandler,message).show();
                        }
                    }

                    @Override
                    protected void _onError() {
                        new DialogGetGiftFailed(activity, R.style.MyDialogStyle, retryGetGiftHandler,"网络异常").show();
                    }
                };


            }
        } else {
            new DialogGoLogin((Activity) mWeakReference.get(), R.style.MyDialogStyle,
                    "暂时无法领取礼包哦 ~T_T~").show();
        }
    }


    @SuppressLint("HandlerLeak")
    Handler retryGetGiftHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    getGiftRequest(giftPos);
                    break;
            }
        }
    };

    @Override
    public int getItemCount() {
        return intoGameGift.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ss)
        ImageView ss;
        @BindView(R.id.tv_gift_name)
        TextView tvGiftName;
        @BindView(R.id.tv_game_detail_number_how_money_one)
        TextView tvGameDetailNumberHowMoneyOne;
        @BindView(R.id.tv_game_detail_number_precent_money)
        TextView tvGameDetailNumberPrecentMoney;
        @BindView(R.id.tv_game_detail_gift_valid)
        TextView tvGameDetailGiftValid;
        @BindView(R.id.btn_libao1)
        RelativeLayout btnLibao1;
        @BindView(R.id.rl_game_detail_get_gift)
        RelativeLayout rlGameDetailGetGift;
        @BindView(R.id.rl1)
        LinearLayout rl1;
        @BindView(R.id.tv_lingqu)
        TextView tvLingqu;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
