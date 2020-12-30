package com.nian.wan.app.bean;

/**
 * @Author: XiaYuShi
 * @Description: 我的礼包头部实体类
 * @Date: Created in 2017/10/19 16:58
 * @Modified By:
 * @Modified Date:
 */
public class GiftHeadBean extends BaseMulDataBean {

    private String gameName;
    private String gameImage;
    private int gameid;
    private int sdk_version;

    public GiftHeadBean(String gameName, String gameImage,int gameid, int type,int sdk_version) {
        this.gameName = gameName;
        this.gameImage = gameImage;
        this.gameid = gameid;
        this.type=type;
        this.sdk_version = sdk_version;
    }

    public int getSdkVersion() {
        return sdk_version;
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


}
