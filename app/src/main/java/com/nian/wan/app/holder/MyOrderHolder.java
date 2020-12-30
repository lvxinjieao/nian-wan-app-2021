package com.nian.wan.app.holder;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.bean.MyOrderBean;
import com.nian.wan.app.utils.TimeUtils;
import com.nian.wan.app.view.ShapeImageView;
import com.bumptech.glide.Glide;
import com.mc.developmentkit.utils.ToastUtil;
import android.widget.LinearLayout;;
import android.widget.RelativeLayout;

import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：
 * 作者：苏杭
 * 时间: 2018-05-23 16:52
 */

public class MyOrderHolder extends BaseHolder<MyOrderBean> {
    @BindView(R.id.img_icon)
    ShapeImageView imgIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_my_order_code)
    TextView tvMyOrderCode;
    @BindView(R.id.tv_my_order_copy)
    TextView tvMyOrderCopy;
    @BindView(R.id.layout_msg)
    LinearLayout layoutMsg;

    @Override
    protected void refreshView(final MyOrderBean bean, int position, Activity activity) {
        Glide.with(x.app()).load(bean.getCover()).error(R.drawable.default_picture).into(imgIcon);
        tvTitle.setText(bean.getGood_name());
        tvTime.setText(TimeUtils.timedate(bean.getCreate_time()));

        //1实物  2虚拟 6兑换的积分
        switch (Integer.valueOf(bean.getGood_type())){
            case 1:
                tvMyOrderCopy.setVisibility(View.GONE);
                tvMyOrderCode.setText("收货地址："+bean.getGoodmark());
                break;
            case 2:
                tvMyOrderCode.setText("激活码："+bean.getGoodmark());
                tvMyOrderCopy.setVisibility(View.VISIBLE);
                break;
            case 6:
                Glide.with(x.app()).load(R.mipmap.mall_record_pingtaibi).into(imgIcon);
                tvMyOrderCode.setText("兑换数量："+bean.getNumber());
                tvMyOrderCopy.setVisibility(View.GONE);
                break;
        }

        tvMyOrderCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                ClipData mClipData = ClipData.newPlainText("Label", bean.getGoodmark());
                cm.setPrimaryClip(mClipData);
                ToastUtil.showToast("已复制");
            }
        });
    }

    @Override
    protected View initView() {
        View inflate = LayoutInflater.from(x.app()).inflate(R.layout.holder_my_order, null);
        ButterKnife.bind(this, inflate);
        inflate.setTag(this);
        return inflate;
    }
}
