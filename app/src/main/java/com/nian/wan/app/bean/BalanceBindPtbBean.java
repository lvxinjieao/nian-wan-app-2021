package com.nian.wan.app.bean;

/**
 * @Description: 余额-绑定平台币实体类
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/2 11:57
 * @Modified By:
 * @Modified Date:
 */
public class BalanceBindPtbBean {

    //图片Url
    private String gamePicUrl;
    //名称
    private String gameName;
    //绑定的数额
    private String bindNumber;

    public BalanceBindPtbBean(String gamePicUrl, String gameName, String bindNumber) {
        this.gamePicUrl = gamePicUrl;
        this.gameName = gameName;
        this.bindNumber = bindNumber;
    }

    public BalanceBindPtbBean() {
    }

    public String getGamePicUrl() {
        return gamePicUrl;
    }

    public void setGamePicUrl(String gamePicUrl) {
        this.gamePicUrl = gamePicUrl;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getBindNumber() {
        return bindNumber;
    }

    public void setBindNumber(String bindNumber) {
        this.bindNumber = bindNumber;
    }

    @Override
    public String toString() {
        return "BalanceBindPtbBean{" +
                "gamePicUrl='" + gamePicUrl + '\'' +
                ", gameName='" + gameName + '\'' +
                ", bindNumber='" + bindNumber + '\'' +
                '}';
    }
}
