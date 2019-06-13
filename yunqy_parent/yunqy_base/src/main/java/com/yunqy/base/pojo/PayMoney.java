package com.yunqy.base.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="tb_paymoney")
public class PayMoney implements Serializable {

    @Id
    private String id;//ID
    private String typeid;//类型ID
    private String payuserid;//缴费人id
    private BigDecimal paycount; //缴费的钱数
    private String createtime;//创建时间
    private String updatetime;//更新时间
    private String remark;//备注
    private String stat;//状态，1表示可用，0表示不可用

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getPayuserid() {
        return payuserid;
    }

    public void setPayuserid(String payuserid) {
        this.payuserid = payuserid;
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

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
