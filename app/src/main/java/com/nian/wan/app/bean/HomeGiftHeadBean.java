package com.nian.wan.app.bean;

/**
 * @Author: XiaYuShi
 * @Description: 我的礼包头部实体类
 * @Date: Created in 2017/10/19 16:58
 * @Modified By:
 * @Modified Date:
 */
public class HomeGiftHeadBean extends BaseMulDataBean {

    private String gameName;
    private String gameImage;

    public HomeGiftHeadBean(String gameName, String gameImage, int type) {
        this.gameName = gameName;
        this.gameImage = gameImage;
        this.type=type;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameImage() {
        return gameImage;
    }

    public void setGameImage(String gameImage) {
        this.gameImage = gameImage;
    }

}
