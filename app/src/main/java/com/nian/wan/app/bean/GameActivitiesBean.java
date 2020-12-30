package com.nian.wan.app.bean;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/14
 * @Description: 活动实体类
 * @Modify By:
 * @ModifyDate:
 */
public class GameActivitiesBean {

    //游戏图片
    private String gamePic;
    //游戏标题
    private String gameTitle;
    //游戏描述
    private String gameDescription;
    //活动是否进行中
    private String isUnderWay;

    public GameActivitiesBean() {
    }

    public GameActivitiesBean(String gamePic, String gameTitle, String gameDescription,
                              String isUnderWay) {
        this.gamePic = gamePic;
        this.gameTitle = gameTitle;
        this.gameDescription = gameDescription;
        this.isUnderWay = isUnderWay;
    }

    public String getGamePic() {
        return gamePic;
    }

    public void setGamePic(String gamePic) {
        this.gamePic = gamePic;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public String getIsUnderWay() {
        return isUnderWay;
    }

    public void setIsUnderWay(String isUnderWay) {
        this.isUnderWay = isUnderWay;
    }

    @Override
    public String toString() {
        return "GameActivitiesBean{" +
                "gamePic='" + gamePic + '\'' +
                ", gameTitle='" + gameTitle + '\'' +
                ", gameDescription='" + gameDescription + '\'' +
                ", isUnderWay='" + isUnderWay + '\'' +
                '}';
    }
}
