package com.yunqy.base.dao;

import com.yunqy.base.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TypeDao extends JpaRepository<Type,String>,JpaSpecificationExecutor<Type> {


    @Query("select t from Type t where stat=?1 order by createtime desc")
    public Page<Type> findListByStat(String stat, Pageable pageable);

}
