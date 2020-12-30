package com.nian.wan.app.bean;


import java.io.Serializable;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/23
 * @Description: 首页商城商品实体类
 * @Modify By:
 * @ModifyDate:
 */
public class HomeStoreGoodsBean implements Serializable{


    /**
     * id : 19
     * good_name : [怪兽必须死]黄金礼包
     * price : 1
     * number : 19
     * good_info : 星魂*1000
     符文碎片*1000
     金币*18888
     中级强化石*3
     羽翼精华*5
     3等物攻石*1
     * status : 1
     * create_time : 1516959831
     * cover : http://testh55.vlcms.com/Uploads/Picture/2018-01-30/5a6fde1a05b8e.png
     * detail_cover : 427
     * good_type : 2
     * good_usage : 有效时间：2017.09.01~2017.09.30
     适用区服：狂野飙车8976服
     可用账户：首次注册账户
     兑换成功后请重新登陆游戏，礼包将在游戏内以邮件形式发放。兑换成功后请重新登陆游戏，礼包将在游戏内以邮件形式发放。兑换成功后请重新登陆游戏，礼包将在游戏内以邮件形式发放。兑换成功后请重新登陆游戏，礼包将在游戏内以邮件形式发放。
     * short_name : [GSBXS]HJLB
     */

    private String id;
    private String good_name;
    private String price;
    private String number;
    private String good_info;
    private String status;
    private String create_time;
    private String cover;
    private String detail_cover;
    private String good_type;
    private String good_usage;
    private String short_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGood_info() {
        return good_info;
    }

    public void setGood_info(String good_info) {
        this.good_info = good_info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDetail_cover() {
        return detail_cover;
    }

    public void setDetail_cover(String detail_cover) {
        this.detail_cover = detail_cover;
    }

    public String getGood_type() {
        return good_type;
    }

    public void setGood_type(String good_type) {
        this.good_type = good_type;
    }

    public String getGood_usage() {
        return good_usage;
    }

    public void setGood_usage(String good_usage) {
        this.good_usage = good_usage;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }
}
