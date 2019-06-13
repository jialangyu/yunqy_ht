package com.yunqy.base.controller;

import com.yunqy.base.pojo.Type;
import com.yunqy.base.service.TypeService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    /**
     * 查询所有类型
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return  new Result(true,StatusCode.OK,"查询成功！",typeService.findAll());
    }

    /**
     * 根据id查询类型
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return  new Result(true,StatusCode.OK,"查询成功！",typeService.findById(id));
    }

    /**
     * 添加类型
     * @param type
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Type type) {
        typeService.add(type);
        return  new Result(true,StatusCode.OK,"添加成功！");
    }

    /**
     * 根据id修改类型
     * @param type
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@RequestBody Type type,@PathVariable String id) {
        type.setId(id);
        typeService.update(type);
        return  new Result(true,StatusCode.OK,"修改成功！");
    }

    /**
     * 根据id删除类型
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
        typeService.deleteById(id);
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
        Page<Type> typePage = typeService.findSearch(page, size);
        PageResult<Type> typePageResult = new PageResult<>(typePage.getTotalElements(),typePage.getContent());
        return  new Result(true,StatusCode.OK,"查询成功",typePageResult);
    }

}
