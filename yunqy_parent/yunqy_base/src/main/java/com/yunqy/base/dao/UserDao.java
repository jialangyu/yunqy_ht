package com.yunqy.base.dao;

import com.yunqy.base.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 用户dao层
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User> {

    @Query("select u from User u where stat=?1 order by createtime desc")
    public Page<User> findListByStat(String stat, Pageable pageable);

    /**
     * 根据电话号查看用户，用户的状态是1（可用用户）或者9（超级管理员）
     * @param telno
     * @return
     */
    @Query("select u from User u where telno=?1 and stat='1'")
    public User findUserByTelno(String telno);

    /**
     * 超级管理员登录
     * @param username
     * @return
     */
    @Query("select u from User u where username=?1 and stat='9'")
    public User findAdmin(String username);
}
