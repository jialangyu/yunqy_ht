package com.yunqy.base.controller;

import com.yunqy.base.pojo.Type;
import com.yunqy.base.pojo.User;
import com.yunqy.base.service.UserService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;
    /**
     * 查询所有用户
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return  new Result(true,StatusCode.OK,"查询成功！",userService.findAll());
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return  new Result(true,StatusCode.OK,"查询成功！",userService.findById(id));
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody User user) {
        //通过解析token里的数据，判断用户是否是admin用户，是的admin才添加用户的权限
        Claims claims =(Claims) request.getAttribute("admin_claims");
        if (claims == null){
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }
        userService.add(user);
        return  new Result(true,StatusCode.OK,"添加成功！");
    }

    /**
     * 根据id修改用户
     * @param user
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@RequestBody User user,@PathVariable String id) {
        //通过解析token里的数据，判断用户是否是admin用户，是的admin才有修改所有用户的权限
        Claims claimsadmin =(Claims) request.getAttribute("admin_claims");
        Claims claimsuser =(Claims) request.getAttribute("user_claims");
        if (claimsadmin != null ){
            user.setId(id);
            userService.update(user);
            return  new Result(true,StatusCode.OK,"修改成功！");
        }else {
            //普通用户只能修改自己的信息
            if ( claimsuser != null && claimsuser.getId().equals(id)){
                user.setId(id);
                userService.update(user);
                return  new Result(true,StatusCode.OK,"修改成功！");
            }else{
                return new Result(false,StatusCode.ACCESSERROR,"权限不足");
            }
        }
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id) {
        //通过解析token里的数据，判断用户是否是admin用户，是的admin才有删除权限
        Claims claims =(Claims) request.getAttribute("admin_claims");
        if (claims == null){
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }
        userService.deleteById(id);
        return  new Result(true,StatusCode.OK,"删除成功！");
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result findSearch( @PathVariable int page,@PathVariable int size) {
        Page<User> typePage = userService.findSearch(page, size);
        PageResult<User> typePageResult = new PageResult<>(typePage.getTotalElements(),typePage.getContent());
        return  new Result(true,StatusCode.OK,"查询成功",typePageResult);
    }

    /**
     * 用户登录
     * @param loginMap
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody Map<String,String> loginMap) {
        //1.获取用户登录的账号和密码
        String telno = loginMap.get("telno");
        String password = loginMap.get("password");
        User user =null;
        //判断是不是超级管理员
        if (telno.equals("admin")){
            //超级管理员调用的方法
           user = userService.findAdmin(telno,password);
            if (user != null) {
                //admin用户添加角色是admin
                String token = jwtUtil.createJWT(user.getId(), user.getTelno(), "admin");
                Map map = new HashMap();
                map.put("token",token);
                map.put("username",user.getUsername());
                map.put("id",user.getId());
                return  new Result(true,StatusCode.OK,"登录成功",map);
            }
        }else{
            //普通用户调用的方法
            user = userService.findUserByTelno(telno, password);
            if (user != null) {
                //普通用户添加角色是user
                String token = jwtUtil.createJWT(user.getId(), user.getTelno(), "user");
                Map map = new HashMap();
                map.put("token",token);
                map.put("username",user.getUsername());
                map.put("id",user.getId());
                return  new Result(true,StatusCode.OK,"登录成功",map);
            }
        }
        return new Result(true,StatusCode.LOGINERROR,"用户名或密码错误");
    }
}
