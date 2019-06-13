package com.yunqy.base.dao;

import com.yunqy.base.pojo.PayInfo;
import com.yunqy.base.pojo.PayMoney;
import com.yunqy.base.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

/**
 * 缴费模块的dao层
 */
public interface PayMoneyDao extends JpaRepository<PayMoney,String>,JpaSpecificationExecutor<PayMoney> {


    @Query("select new com.yunqy.base.pojo.PayInfo(p.id ,t.typename ,u.username,p.paycount,p.createtime,p.remark ) from PayMoney p inner join Type t on p.typeid=t.id inner join User u on p.payuserid=u.id where p.stat=?1 order by p.createtime desc")
    public Page<PayInfo> findListByStat(String stat, Pageable pageable);

    @Query("select sum(paycount) from PayMoney p where p.stat='1'")
    public BigDecimal sumPayMoney();

    @Query("select sum(paycount) from PayMoney p where p.stat='1' and typeid =?1")
    public BigDecimal sumPayMoneyBytypeId(String typeid);
}

