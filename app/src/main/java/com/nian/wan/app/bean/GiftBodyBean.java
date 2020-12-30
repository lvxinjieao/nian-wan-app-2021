package com.nian.wan.app.bean;

/**
 * @Author: XiaYuShi
 * @Description: 我的礼包身体实体类
 * @Date: Created in 2017/10/19 16:58
 * @Modified By:
 * @Modified Date:
 */
public class GiftBodyBean extends BaseMulDataBean {

    private String giftId;
    private String giftName;
    private String giftCode;
    private String giftDescription;
//    private boolean isShowLine = true;

    public GiftBodyBean(String giftId, String giftName, String giftCode, String giftDescription,
                        int type) {
        this.giftName = giftName;
        this.giftCode = giftCode;
        this.giftDescription = giftDescription;
        this.type = type;
        this.giftId = giftId;
//        this.isShowLine = isShowLine;
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

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }
    /*    public boolean isShowLine() {
        return isShowLine;
    }

    public void setShowLine(boolean showLine) {
        isShowLine = showLine;
    }*/
}

