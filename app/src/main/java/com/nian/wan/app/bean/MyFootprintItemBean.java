package com.nian.wan.app.bean;

/**
 * @Description: 我的足迹子元素
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/8 11:41
 * @Modified By:
 * @Modified Date:
 */
public class MyFootprintItemBean {


    private String id;
    private String gameId;
    private String gameName;
    private String gamePic;
    private String gameBid;
    private boolean isChoosed;


    public MyFootprintItemBean() {
    }

    public MyFootprintItemBean(String id, String gameId, String gameName, String gamePic,
                               String gameBid, boolean isChoosed) {
        this.id = id;
        this.gameId = gameId;
        this.gameName = gameName;
        this.gamePic = gamePic;
        this.isChoosed = isChoosed;
        this.gameBid = gameBid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public String getGameBid() {
        return gameBid;
    }

    public void setGameBid(String gameBid) {
        this.gameBid = gameBid;
    }

    @Override
    public String toString() {
        return "MyFootprintItemBean{" +
                "id='" + id + '\'' +
                ", gameId='" + gameId + '\'' +
                ", gameName='" + gameName + '\'' +
                ", gamePic='" + gamePic + '\'' +
                ", gameBid='" + gameBid + '\'' +
                ", isChoosed=" + isChoosed +
                '}';
    }


}
