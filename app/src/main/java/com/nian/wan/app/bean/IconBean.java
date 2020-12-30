package com.nian.wan.app.bean;

public class IconBean {

    private String logo;
    private String start_icon;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStart_icon() {
        return start_icon;
    }

    public void setStart_icon(String start_icon) {
        this.start_icon = start_icon;
    }

    public IconBean(String logo, String start_icon){
        this.logo=logo;
        this.start_icon=start_icon;
    }
}
