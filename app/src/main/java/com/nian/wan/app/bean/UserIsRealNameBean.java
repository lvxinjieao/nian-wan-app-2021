package com.nian.wan.app.bean;


/**
 * @Author: XiaYuShi
 * @Date: 2017/12/14
 * @Description: 用户是否实名认证
 * @Modify By:
 * @ModifyDate:
 */
public class UserIsRealNameBean {


    /**
     * id : 15
     * real_name :
     * idcard :
     * age_status : 0
     */

    private String id;
    private String real_name;
    private String idcard;
    private String age_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getAge_status() {
        return age_status;
    }

    public void setAge_status(String age_status) {
        this.age_status = age_status;
    }
}
