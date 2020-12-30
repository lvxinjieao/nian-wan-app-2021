package com.nian.wan.app.bean;

/**
 * @Description: 我的收藏-热门游戏实体类
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/7 9:38
 * @Modified By:
 * @Modified Date:
 */
public class MyCollectionHotGameBean {

    //游戏id
    private String gameId;
    //游戏名字
    private String gameName;
    //游戏图片url
    private String gamePic;
    //游戏类型
    private String gameType;
    //游戏收藏人数
    private String gameCollectedNumber;
    //游戏描述
    private String gameDescription;
    //游戏地址/下载地址
    private String gamePlayUrl;
    //是否已经收藏
    private String isCollected;
    private String play_num;

    private String gameSize; //游戏大小
    private String sdk_version; //游戏类型 3 H5游戏，1安卓手游

    public void setPlay_num(String play_num) {
        this.play_num = play_num;
    }

    public String getGameSize() {
        return gameSize;
    }

    public void setGameSize(String gameSize) {
        this.gameSize = gameSize;
    }

    public String getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
    }

    public String getPlay_num() {
        return play_num;
    }

    public void setplay_num(String play_num) {
        this.play_num = play_num;
    }
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
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

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getGameCollectedNumber() {
        return gameCollectedNumber;
    }

    public void setGameCollectedNumber(String gameCollectedNumber) {
        this.gameCollectedNumber = gameCollectedNumber;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public String getGamePlayUrl() {
        return gamePlayUrl;
    }

    public void setGamePlayUrl(String gamePlayUrl) {
        this.gamePlayUrl = gamePlayUrl;
    }

    public String getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(String isCollected) {
        this.isCollected = isCollected;
    }

    @Override
    public String toString() {
        return "MyCollectionHotGameBean{" +
                "gameId='" + gameId + '\'' +
                ", gameName='" + gameName + '\'' +
                ", gamePic='" + gamePic + '\'' +
                ", gameType='" + gameType + '\'' +
                ", gameCollectedNumber='" + gameCollectedNumber + '\'' +
                ", gameDescription='" + gameDescription + '\'' +
                ", gamePlayUrl='" + gamePlayUrl + '\'' +
                ", isCollected='" + isCollected + '\'' +
                '}';
    }
}
