package com.nian.wan.app.bean;


/**
 * @Author: XiaYuShi
 * @Date: 2018/1/12
 * @Description: 根据后台设置显示支付方式
 * @Modify By:
 * @ModifyDate:
 */
public class DynamicShowPayWays {


    /**
     * jft : 1
     * weixin : 1
     * alipay : 1
     * goldpig : 1
     */

    private String jft;
    private int weixin;
    private String alipay;
    private String goldpig;

    public String getJft() {
        return jft;
    }

    public void setJft(String jft) {
        this.jft = jft;
    }

    public int getWeixin() {
        return weixin;
    }

    public void setWeixin(int weixin) {
        this.weixin = weixin;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getGoldpig() {
        return goldpig;
    }

    public void setGoldpig(String goldpig) {
        this.goldpig = goldpig;
    }
}
