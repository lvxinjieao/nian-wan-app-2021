package com.nian.wan.app.bean;

import java.io.Serializable;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/21
 * @Description: 首页礼包子元素Bean
 * @Modify By:
 * @ModifyDate:
 */
public class HomeGiftItemBean implements Serializable {

    private String groupId;
    private String giftId;
    private String giftName;
    private String giftCode;
    private String giftDescription;
    private String giftIsGet;

    public HomeGiftItemBean() {
    }

    public HomeGiftItemBean(String groupId, String giftId, String giftName, String giftCode,
                            String giftDescription, String giftIsGet) {
        this.groupId = groupId;
        this.giftId = giftId;
        this.giftName = giftName;
        this.giftCode = giftCode;
        this.giftDescription = giftDescription;
        this.giftIsGet = giftIsGet;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftCode() {
        return giftCode;
    }

    public void setGiftCode(String giftCode) {
        this.giftCode = giftCode;
    }

    public String getGiftDescription() {
        return giftDescription;
    }

    public void setGiftDescription(String giftDescription) {
        this.giftDescription = giftDescription;
    }

    public String getGiftIsGet() {
        return giftIsGet;
    }

    public void setGiftIsGet(String giftIsGet) {
        this.giftIsGet = giftIsGet;
    }

    @Override
    public String toString() {
        return "HomeGiftItemBean{" +
                "groupId='" + groupId + '\'' +
                ", giftId='" + giftId + '\'' +
                ", giftName='" + giftName + '\'' +
                ", giftCode='" + giftCode + '\'' +
                ", giftDescription='" + giftDescription + '\'' +
                ", giftIsGet='" + giftIsGet + '\'' +
                '}';
    }
}
