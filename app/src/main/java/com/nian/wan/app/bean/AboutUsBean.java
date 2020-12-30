package com.nian.wan.app.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 关于我们
 */

public class AboutUsBean {

    /**
     * status : 1
     * return_code : 1
     * msg : {"app_name":"溪谷软件H5游戏盒子","app_version":"2.0","app_version_name":null,"icp":"","copyright":"© 徐州梦创信息科技有限公司 版权所有 2016 vlcms.com","qq_qun":"454978038","qq_hao":"97471547","kefu_phone":"0516-80258920","business_hezuo":"863387758","network":"http://www.vlcms.com/","group_key":"","start_img":"http://newh5.vlcms.com/Uploads/Picture/2017-04-25/58fef2c975d88.jpg","agree":"http://newh5.vlcms.com/app.php?s=/User/agreement.html"}
     */

    private int status;
    private int return_code;
    private MsgBean msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getReturn_code() {
        return return_code;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    @Table(name = "AboutUsBean")
    public static class MsgBean {
        /**
         * app_name : 溪谷软件H5游戏盒子
         * app_version : 2.0
         * app_version_name : null
         * icp :
         * copyright : © 徐州梦创信息科技有限公司 版权所有 2016 vlcms.com
         * qq_qun : 454978038
         * qq_hao : 97471547
         * kefu_phone : 0516-80258920
         * business_hezuo : 863387758
         * network : http://www.vlcms.com/
         * group_key :
         * start_img : http://newh5.vlcms.com/Uploads/Picture/2017-04-25/58fef2c975d88.jpg
         * agree : http://newh5.vlcms.com/app.php?s=/User/agreement.html
         */

        @Column(name = "id", isId = true, autoGen = false)
        public int id;

        @Column(name = "app_name")
        private String app_name;

        @Column(name = "app_version")
        private String app_version;

        @Column(name = "app_version_name")
        private String app_version_name;

        private String icp;

        @Column(name = "copyright")
        private String copyright;

        @Column(name = "company_name")
        private String company_name;

        @Column(name = "qq_qun")
        private String qq_qun;

        @Column(name = "qq_hao")
        private String qq_hao;

        @Column(name = "kefu_phone")
        private String kefu_phone;

        @Column(name = "business_hezuo")
        private String business_hezuo;

        @Column(name = "network")
        private String network;

        @Column(name = "group_key")
        private String group_key;

        @Column(name = "start_img")
        private String start_img;

        @Column(name = "agree")
        private String agree;

        public String getApp_name() {
            return app_name;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getApp_version() {
            return app_version;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }

        public String getApp_version_name() {
            return app_version_name;
        }

        public void setApp_version_name(String app_version_name) {
            this.app_version_name = app_version_name;
        }

        public String getIcp() {
            return icp;
        }

        public void setIcp(String icp) {
            this.icp = icp;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public String getQq_qun() {
            return qq_qun;
        }

        public void setQq_qun(String qq_qun) {
            this.qq_qun = qq_qun;
        }

        public String getQq_hao() {
            return qq_hao;
        }

        public void setQq_hao(String qq_hao) {
            this.qq_hao = qq_hao;
        }

        public String getKefu_phone() {
            return kefu_phone;
        }

        public void setKefu_phone(String kefu_phone) {
            this.kefu_phone = kefu_phone;
        }

        public String getBusiness_hezuo() {
            return business_hezuo;
        }

        public void setBusiness_hezuo(String business_hezuo) {
            this.business_hezuo = business_hezuo;
        }

        public String getNetwork() {
            return network;
        }

        public void setNetwork(String network) {
            this.network = network;
        }

        public String getGroup_key() {
            return group_key;
        }

        public void setGroup_key(String group_key) {
            this.group_key = group_key;
        }

        public String getStart_img() {
            return start_img;
        }

        public void setStart_img(String start_img) {
            this.start_img = start_img;
        }

        public String getAgree() {
            return agree;
        }

        public void setAgree(String agree) {
            this.agree = agree;
        }
    }
}
