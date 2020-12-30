package com.nian.wan.app.bean;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/11
 * @Description: 首页热门实体类
 * @Modify By:
 * @ModifyDate:
 */
public class HomeHotBean {

    ///游戏id
    private String id;
    //游戏图片
    private String gamePic;
    //是否有礼包
    private String isHasPackage;
    //游戏名字
    private String gameName;
    //游戏类型
    private String gameType;
    //游戏描述
    private String gameDescription;

    public HomeHotBean() {
    }

    public HomeHotBean(String id, String gamePic, String isHasPackage, String gameName,
                       String gameType, String gameDescription) {
        this.id = id;
        this.gamePic = gamePic;
        this.isHasPackage = isHasPackage;
        this.gameName = gameName;
        this.gameType = gameType;
        this.gameDescription = gameDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGamePic() {
        return gamePic;
    }

    public void setGamePic(String gamePic) {
        this.gamePic = gamePic;
    }

    public String getIsHasPackage() {
        return isHasPackage;
    }

    public void setIsHasPackage(String isHasPackage) {
        this.isHasPackage = isHasPackage;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    @Override
    public String toString() {
        return "HomeHotBean{" +
                "id='" + id + '\'' +
                ", gamePic='" + gamePic + '\'' +
                ", isHasPackage='" + isHasPackage + '\'' +
                ", gameName='" + gameName + '\'' +
                ", gameType='" + gameType + '\'' +
                ", gameDescription='" + gameDescription + '\'' +
                '}';
    }
}
