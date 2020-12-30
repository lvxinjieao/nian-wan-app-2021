package com.nian.wan.app.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 关于我们
 */
@Table(name = "AboutUsData")
public class AboutUsData implements Serializable {

    @Column(name = "id", isId = true, autoGen = false)
    public int id;

    @Column(name = "PC_SET_SERVER_QQ")
    private String PC_SET_SERVER_QQ;

    @Column(name = "PC_COMMUNICATION_GROUP")
    private String PC_COMMUNICATION_GROUP;

    @Column(name = "group_key")
    private String group_key;

    @Column(name = "PC_SET_SERVER_TEL")
    private String PC_SET_SERVER_TEL;

    @Column(name = "APP_COOPERATION")
    private String APP_COOPERATION;

    @Column(name = "APP_COPYRIGHT")
    private String APP_COPYRIGHT;

    @Column(name = "APP_COPYRIGHT_EN")
    private String APP_COPYRIGHT_EN;

    @Column(name = "PC_OFFICIAL_SITE")
    private String PC_OFFICIAL_SITE;

    @Column(name = "USER_AGREEMENT")
    private String USER_AGREEMENT;

    @Column(name = "APP_FILE_SIZE")
    private String APP_FILE_SIZE;

    public String getAPP_NAME() {
        return APP_NAME;
    }

    public void setAPP_NAME(String APP_NAME) {
        this.APP_NAME = APP_NAME;
    }

    @Column(name = "APP_NAME")
    private String APP_NAME;

    @Column(name = "APP_VERSION_NAME")
    private String APP_VERSION_NAME;

    @Column(name = "APP_VERSION")
    private String APP_VERSION;

    @Column(name = "APP_DOWNLOAD")
    private String APP_DOWNLOAD;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPC_SET_SERVER_QQ() {
        return PC_SET_SERVER_QQ;
    }

    public void setPC_SET_SERVER_QQ(String PC_SET_SERVER_QQ) {
        this.PC_SET_SERVER_QQ = PC_SET_SERVER_QQ;
    }

    public String getPC_COMMUNICATION_GROUP() {
        return PC_COMMUNICATION_GROUP;
    }

    public void setPC_COMMUNICATION_GROUP(String PC_COMMUNICATION_GROUP) {
        this.PC_COMMUNICATION_GROUP = PC_COMMUNICATION_GROUP;
    }

    public String getGroup_key() {
        return group_key;
    }

    public void setGroup_key(String group_key) {
        this.group_key = group_key;
    }

    public String getPC_SET_SERVER_TEL() {
        return PC_SET_SERVER_TEL;
    }

    public void setPC_SET_SERVER_TEL(String PC_SET_SERVER_TEL) {
        this.PC_SET_SERVER_TEL = PC_SET_SERVER_TEL;
    }

    public String getAPP_COOPERATION() {
        return APP_COOPERATION;
    }

    public void setAPP_COOPERATION(String APP_COOPERATION) {
        this.APP_COOPERATION = APP_COOPERATION;
    }

    public String getAPP_COPYRIGHT() {
        return APP_COPYRIGHT;
    }

    public void setAPP_COPYRIGHT(String APP_COPYRIGHT) {
        this.APP_COPYRIGHT = APP_COPYRIGHT;
    }

    public String getAPP_COPYRIGHT_EN() {
        return APP_COPYRIGHT_EN;
    }

    public void setAPP_COPYRIGHT_EN(String APP_COPYRIGHT_EN) {
        this.APP_COPYRIGHT_EN = APP_COPYRIGHT_EN;
    }

    public String getPC_OFFICIAL_SITE() {
        return PC_OFFICIAL_SITE;
    }

    public void setPC_OFFICIAL_SITE(String PC_OFFICIAL_SITE) {
        this.PC_OFFICIAL_SITE = PC_OFFICIAL_SITE;
    }

    public String getUSER_AGREEMENT() {
        return USER_AGREEMENT;
    }

    public void setUSER_AGREEMENT(String USER_AGREEMENT) {
        this.USER_AGREEMENT = USER_AGREEMENT;
    }

    public String getAPP_FILE_SIZE() {
        return APP_FILE_SIZE;
    }

    public void setAPP_FILE_SIZE(String APP_FILE_SIZE) {
        this.APP_FILE_SIZE = APP_FILE_SIZE;
    }

    public String getAPP_VERSION_NAME() {
        return APP_VERSION_NAME;
    }

    public void setAPP_VERSION_NAME(String APP_VERSION_NAME) {
        this.APP_VERSION_NAME = APP_VERSION_NAME;
    }

    public String getAPP_VERSION() {
        return APP_VERSION;
    }

    public void setAPP_VERSION(String APP_VERSION) {
        this.APP_VERSION = APP_VERSION;
    }

    public String getAPP_DOWNLOAD() {
        return APP_DOWNLOAD;
    }

    public void setAPP_DOWNLOAD(String APP_DOWNLOAD) {
        this.APP_DOWNLOAD = APP_DOWNLOAD;
    }

}

