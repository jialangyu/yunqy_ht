package com.yunqy.base.service;

import com.yunqy.base.dao.UserDao;
import com.yunqy.base.pojo.Type;
import com.yunqy.base.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.krb5.KrbCryptoException;
import utils.DateWorker;
import utils.IdWorker;
import utils.JwtUtil;

import java.util.List;
import java.util.Optional;

/**
 * 用户服务层
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DateWorker dateWorker;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private BCryptPasswordEncoder encode;


    //查询所有用户
    public List findAll() {
        return userDao.findAll();
    }

    //根据Id查询用户
    public User findById(String id) {
        User user = userDao.findById(id).get();
        user.setPassword("******");
        return user;
    }

    //添加用户
    public void add(User user){
        //生产系统时间
        user.setCreatetime(dateWorker.createDate());
        user.setUpdatetime(dateWorker.createDate());
        user.setStat("1");
        String newPassword = this.encode.encode(user.getPassword());
        user.setPassword(newPassword);
        //添加id
        user.setId(idWorker.nextId()+"");
        userDao.save(user);
    }

    //修改用户
    public void update(User user) {
        User oldUser = findById(user.getId());
        user.setCreatetime(oldUser.getCreatetime());
        user.setUpdatetime(dateWorker.createDate());
        //修改密码并加密
        String newPassword = this.encode.encode(user.getPassword());
        user.setPassword(newPassword);
        userDao.save(user);
    }

    //根据id删除用户
    public void deleteById(String id){
        User user = findById(id);
        user.setStat("0");
        user.setUpdatetime(dateWorker.createDate());
        userDao.save(user);
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    public Page<User> findSearch(int page, int size) {
        //Specification specification = createSpecification(searchMap);
        PageRequest pageRequest = PageRequest.of(page-1,size);
        //return typeDao.findAll(pageRequest);
        return userDao.findListByStat("1",pageRequest);
    }

    /**
     * 普通用户登录的密码验证
     * @param telno
     * @param password
     * @return
     */
    public User findUserByTelno(String telno,String password) {
        User user = userDao.findUserByTelno(telno);
        if (user!=null && encode.matches(password,user.getPassword())){
            return user;
        }else{
            return null;
        }
    }

    /**
     * 超级管理员登录
     * @param username
     * @param password
     * @return
     */
    public User findAdmin(String username,String password) {
        User user = userDao.findAdmin(username);
        if (user!=null && encode.matches(password,user.getPassword())){
            return user;
        }else{
            return null;
        }
    }
}
