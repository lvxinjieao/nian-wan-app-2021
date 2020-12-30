package com.nian.wan.app.bean;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/21
 * @Description: 首页礼包组元素
 * @Modify By:
 * @ModifyDate:
 */
public class HomeGiftGroupBean {


    private String id;
    private String gameName;
    private String gamePic;
    private String giftNumber;

    public HomeGiftGroupBean() {

    }

    public HomeGiftGroupBean(String id, String gameName, String gamePic, String giftNumber) {
        this.id = id;
        this.gameName = gameName;
        this.gamePic = gamePic;
        this.giftNumber = giftNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGamePic() {
        return gamePic;
    }

    public void setGamePic(String gamePic) {
        this.gamePic = gamePic;
    }

    public String getGiftNumber() {
        return giftNumber;
    }

    public void setGiftNumber(String giftNumber) {
        this.giftNumber = giftNumber;
    }

    @Override
    public String toString() {
        return "HomeGiftGroupBean{" +
                "id='" + id + '\'' +
                ", gameName='" + gameName + '\'' +
                ", gamePic='" + gamePic + '\'' +
                ", giftNumber='" + giftNumber + '\'' +
                '}';
    }
}
