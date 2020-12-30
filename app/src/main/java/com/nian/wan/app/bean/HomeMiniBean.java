package com.nian.wan.app.bean;

/**
 * 描述：首页小程序列表Bean
 * 作者：闫冰
 * 时间: 2018-06-07 10:21
 */
public class HomeMiniBean {

    /**
     * id : 5
     * game_name : 猎魔传说
     * scan_num : 0
     * icon : http://testh55.vlcms.com/Uploads/Picture/2018-04-03/5ac36fa8555eb.jpg
     * qrcodeurl : https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGC7zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyc3NmRmNxYnBjZmwxMjlzX05yY2sAAgQJ4RhbAwSAOyYA
     */

    private String id;
    private String game_name;
    private String scan_num;
    private String icon;
    private String qrcodeurl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getScan_num() {
        return scan_num;
    }

    public void setScan_num(String scan_num) {
        this.scan_num = scan_num;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getQrcodeurl() {
        return qrcodeurl;
    }

    public void setQrcodeurl(String qrcodeurl) {
        this.qrcodeurl = qrcodeurl;
    }
}
