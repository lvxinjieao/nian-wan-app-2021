package com.nian.wan.app.bean;

/**
 * @Author: XiaYuShi
 * @Description: 我的礼包底部实体类
 * @Date: Created in 2017/10/19 16:58
 * @Modified By:
 * @Modified Date:
 */
public class GiftFootBean extends BaseMulDataBean {

    private String title;


    public GiftFootBean(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
