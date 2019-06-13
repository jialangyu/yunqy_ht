package com.yunqy.base.pojo;

import java.math.BigDecimal;

public class PayInfo {

    private String id;//ID
    private String typename;//类型名称
    private String payusername;//缴费人姓名
    private BigDecimal paycount; //缴费的钱数
    private String createtime;//创建时间
    private String remark;//备注

    public PayInfo(String id, String typename, String payusername, BigDecimal paycount, String createtime, String remark) {
        this.id = id;
        this.typename = typename;
        this.payusername = payusername;
        this.paycount = paycount;
        this.createtime = createtime;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getPayusername() {
        return payusername;
    }

    public void setPayusername(String payusername) {
        this.payusername = payusername;
    }

    public BigDecimal getPaycount() {
        return paycount;
    }

    public void setPaycount(BigDecimal paycount) {
        this.paycount = paycount;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
