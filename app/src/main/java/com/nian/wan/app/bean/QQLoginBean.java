package com.nian.wan.app.bean;


/**
 * @Author: XiaYuShi
 * @Date: 2017/12/22
 * @Description:
 * @Modify By:
 * @ModifyDate:
 */
public class QQLoginBean {


    /**
     * userID : C2E323DDD1F3013FB9315C939619932F
     * pay_token : 7E74FF95B93355C43542DD2D7AC45716
     * icon : http://q.qlogo.cn/qqapp/1106058967/C2E323DDD1F3013FB9315C939619932F/40
     * secretType : 0
     * pf : desktop_m_qq-10000144-android-2002-
     * iconQzone : http://qzapp.qlogo.cn/qzapp/1106058967/C2E323DDD1F3013FB9315C939619932F/100
     * token : E462C43A68130F7957678ED7C31EB799
     * nickname : 该隐
     * expiresTime : 1513932139214
     * expiresIn : 7776000
     * secret :
     * gender : 1
     * pfkey : 166594e563d2f5993b4eae3105e5a334
     */

    private String userID;
    private String pay_token;
    private String icon;
    private String secretType;
    private String pf;
    private String iconQzone;
    private String token;
    private String nickname;
    private long expiresTime;
    private int expiresIn;
    private String secret;
    private String gender;
    private String pfkey;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPay_token() {
        return pay_token;
    }

    public void setPay_token(String pay_token) {
        this.pay_token = pay_token;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSecretType() {
        return secretType;
    }

    public void setSecretType(String secretType) {
        this.secretType = secretType;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getIconQzone() {
        return iconQzone;
    }

    public void setIconQzone(String iconQzone) {
        this.iconQzone = iconQzone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPfkey() {
        return pfkey;
    }

    public void setPfkey(String pfkey) {
        this.pfkey = pfkey;
    }
}
