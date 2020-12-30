package com.nian.wan.app.bean;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/10
 * @Description: 最近在玩实体类
 * @Modify By:
 * @ModifyDate:
 */
public class HomeSlideRecentBean {

    private String gameId;
    private String gamePic;
    private String gameName;

    public HomeSlideRecentBean() {
    }

    public HomeSlideRecentBean(String gameId, String gamePic, String gameName) {

        this.gameId = gameId;
        this.gamePic = gamePic;
        this.gameName = gameName;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGamePic() {
        return gamePic;
    }

    public void setGamePic(String gamePic) {
        this.gamePic = gamePic;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public String toString() {
        return "HomeSlideRecentBean{" +
                "gameId='" + gameId + '\'' +
                ", gamePic='" + gamePic + '\'' +
                ", gameName='" + gameName + '\'' +
                '}';
    }
}
