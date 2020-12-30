package com.nian.wan.app.bean;

/**
 * @Description: 首页Banner
 */
public class HomeBannerBean {

    /**
     * title : appbanner1
     * data : http://testh55.vlcms.com/Uploads/Picture/2017-10-18/59e6afd24a9a8.png
     * url :
     */
    private String title;
    private String data;
    private String url;
    private String slider_type;
    private String target;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSlider_type() {
        return slider_type;
    }

    public void setSlider_type(String slider_type) {
        this.slider_type = slider_type;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
