package com.nian.wan.app.bean;

/**
 * @Description: 我的礼包-热门礼包实体类
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/6 10:51
 * @Modified By:
 * @Modified Date:
 */
public class MyGiftHotGiftBean {

    //热门礼包id
    private String giftId;
    //热门礼包图片
    private String giftPic;
    //热门礼包名称
    private String giftName;
    //游戏名称
    private String gameName;
    //热门礼包码
    private String giftCode;
    //热门礼包总数量
    private String giftNumber;
    //热门礼包当前剩余数量
    private String giftCurrentNumber;
    //热门礼包有效时间
    private String giftValidityTime;
    //结束时间
    private String giftEndTime;
    //礼包游戏类型 H5/手游
    private int GameType;

    public int getGameType() {
        return GameType;
    }

    public void setGameType(int gameType) {
        GameType = gameType;
    }

    public String getGiftPic() {
        return giftPic;
    }

    public void setGiftPic(String giftPic) {
        this.giftPic = giftPic;
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

    public String getGiftNumber() {
        return giftNumber;
    }

    public void setGiftNumber(String giftNumber) {
        this.giftNumber = giftNumber;
    }

    public String getGiftCurrentNumber() {
        return giftCurrentNumber;
    }

    public void setGiftCurrentNumber(String giftCurrentNumber) {
        this.giftCurrentNumber = giftCurrentNumber;
    }

    public String getGiftValidityTime() {
        return giftValidityTime;
    }

    public void setGiftValidityTime(String giftValidityTime) {
        this.giftValidityTime = giftValidityTime;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGiftEndTime() {
        return giftEndTime;
    }

    public void setGiftEndTime(String giftEndTime) {
        this.giftEndTime = giftEndTime;
    }

    @Override
    public String toString() {
        return "MyGiftHotGiftBean{" +
                "giftId='" + giftId + '\'' +
                ", giftPic='" + giftPic + '\'' +
                ", giftName='" + giftName + '\'' +
                ", gameName='" + gameName + '\'' +
                ", giftCode='" + giftCode + '\'' +
                ", giftNumber='" + giftNumber + '\'' +
                ", giftCurrentNumber='" + giftCurrentNumber + '\'' +
                ", giftValidityTime='" + giftValidityTime + '\'' +
                ", giftEndTime='" + giftEndTime + '\'' +
                '}';
    }
}
