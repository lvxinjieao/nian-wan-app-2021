package com.nian.wan.app.bean;

/**
 * Created by Administrator on 2017/5/22.
 */

public class GetLogoBean {

    /**
     * msg : {"copyright":"© 徐州梦创信息科技有限公司 版权所有 2016 vlcms.com","kefu_phone":"0516-80258920","app_version":"2.0","agree":"http://newh5.vlcms.com/app.php?s=/Article/agreement.html","qq_hao":"97471547","network":"http://www.vlcms.com/","app_name":"溪谷软件H5游戏盒子","qq_qun":"454978038","business_hezuo":"863387758","icp":"","company_name":"123456","logo":"http://newh5.vlcms.com/Uploads/Picture/2017-04-25/58feed89918ff.png","app_version_name":"123456","start_img":"http://newh5.vlcms.com/Uploads/Picture/2017-05-22/5922911a875fd.jpg","group_key":""}
     * return_code : 1
     * status : 1
     */
    private MsgEntity msg;
    private int return_code;
    private int status;

    public void setMsg(MsgEntity msg) {
        this.msg = msg;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MsgEntity getMsg() {
        return msg;
    }

    public int getReturn_code() {
        return return_code;
    }

    public int getStatus() {
        return status;
    }

    public class MsgEntity {
        /**
         * copyright : © 徐州梦创信息科技有限公司 版权所有 2016 vlcms.com
         * kefu_phone : 0516-80258920
         * app_version : 2.0
         * agree : http://newh5.vlcms.com/app.php?s=/Article/agreement.html
         * qq_hao : 97471547
         * network : http://www.vlcms.com/
         * app_name : 溪谷软件H5游戏盒子
         * qq_qun : 454978038
         * business_hezuo : 863387758
         * icp :
         * company_name : 123456
         * logo : http://newh5.vlcms.com/Uploads/Picture/2017-04-25/58feed89918ff.png
         * app_version_name : 123456
         * start_img : http://newh5.vlcms.com/Uploads/Picture/2017-05-22/5922911a875fd.jpg
         * group_key :
         */
        private String copyright;
        private String kefu_phone;
        private String app_version;
        private String agree;
        private String qq_hao;
        private String network;
        private String app_name;
        private String qq_qun;
        private String business_hezuo;
        private String icp;
        private String company_name;
        private String logo;
        private String app_version_name;
        private String start_img;
        private String group_key;

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public void setKefu_phone(String kefu_phone) {
            this.kefu_phone = kefu_phone;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }

        public void setAgree(String agree) {
            this.agree = agree;
        }

        public void setQq_hao(String qq_hao) {
            this.qq_hao = qq_hao;
        }

        public void setNetwork(String network) {
            this.network = network;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public void setQq_qun(String qq_qun) {
            this.qq_qun = qq_qun;
        }

        public void setBusiness_hezuo(String business_hezuo) {
            this.business_hezuo = business_hezuo;
        }

        public void setIcp(String icp) {
            this.icp = icp;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public void setApp_version_name(String app_version_name) {
            this.app_version_name = app_version_name;
        }

        public void setStart_img(String start_img) {
            this.start_img = start_img;
        }

        public void setGroup_key(String group_key) {
            this.group_key = group_key;
        }

        public String getCopyright() {
            return copyright;
        }

        public String getKefu_phone() {
            return kefu_phone;
        }

        public String getApp_version() {
            return app_version;
        }

        public String getAgree() {
            return agree;
        }

        public String getQq_hao() {
            return qq_hao;
        }

        public String getNetwork() {
            return network;
        }

        public String getApp_name() {
            return app_name;
        }

        public String getQq_qun() {
            return qq_qun;
        }

        public String getBusiness_hezuo() {
            return business_hezuo;
        }

        public String getIcp() {
            return icp;
        }

        public String getCompany_name() {
            return company_name;
        }

        public String getLogo() {
            return logo;
        }

        public String getApp_version_name() {
            return app_version_name;
        }

        public String getStart_img() {
            return start_img;
        }

        public String getGroup_key() {
            return group_key;
        }
    }
}
