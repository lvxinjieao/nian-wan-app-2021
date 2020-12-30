package com.nian.wan.app.bean;

/**
 * 描述：收货地址bean
 * 作者：钮家齐
 * 时间: 2018-02-04 14:15
 */
public class AddressBean {

    /**
     * consignee : kdjdkdkd
     * consignee_phone : 18012007909
     * consignee_address : 江苏省常州市天宁区
     * detailed_address : jdidjdkldkd
     */

    private String consignee;
    private String consignee_phone;
    private String consignee_address;
    private String detailed_address;

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsignee_phone() {
        return consignee_phone;
    }

    public void setConsignee_phone(String consignee_phone) {
        this.consignee_phone = consignee_phone;
    }

    public String getConsignee_address() {
        return consignee_address;
    }

    public void setConsignee_address(String consignee_address) {
        this.consignee_address = consignee_address;
    }

    public String getDetailed_address() {
        return detailed_address;
    }

    public void setDetailed_address(String detailed_address) {
        this.detailed_address = detailed_address;
    }
}
