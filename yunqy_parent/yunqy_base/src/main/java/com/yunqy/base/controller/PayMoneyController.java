package com.yunqy.base.controller;

import com.yunqy.base.pojo.PayInfo;
import com.yunqy.base.pojo.PayMoney;
import com.yunqy.base.pojo.Type;
import com.yunqy.base.service.PayMoneyService;
import com.yunqy.base.service.TypeService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("paymoney")
public class PayMoneyController {

    @Autowired
    private PayMoneyService payMoneyService;

    @Autowired
    private TypeService typeService;

    /**
     * 查询所有
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true,StatusCode.OK,"查询成功！",payMoneyService.findAll());
    }

    /**
     * 根据id查询缴费情况
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true,StatusCode.OK,"查询成功！",payMoneyService.findById(id));
    }

    /**
     * 添加缴费单
     * @param payMoney
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody PayMoney payMoney){
        payMoneyService.add(payMoney);
        return new Result(true,StatusCode.OK,"添加成功！");
    }

    /**
     * 修改缴费单
     * @param payMoney
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@RequestBody PayMoney payMoney,@PathVariable String id){
        payMoney.setId(id);
        payMoneyService.update(payMoney);
        return new Result(true,StatusCode.OK,"修改成功！");
    }

    /**
     * 根据id删除缴费单
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        payMoneyService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功！");
    }

    /**
     * 查询自定义列的缴费信息
     * @return
     */
    @RequestMapping(value = "/payinfo",method = RequestMethod.GET)
    public Result findAllMy(){
        return new Result(true,StatusCode.OK,"查询成功！",payMoneyService.findAllMy());
    }

    /**
     * 条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap){
        if (searchMap.get("typeid") !=null && !"".equals((String)searchMap.get("typeid"))){
            System.out.println("缴费类型的id是： "+searchMap.get("typeid"));
        }
        return new Result(true,StatusCode.OK,"查询成功！",payMoneyService.findSearch(searchMap));
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result findSearchByStat( @PathVariable int page,@PathVariable int size) {
        Page<PayInfo> typePage = payMoneyService.findSearchByStat(page, size);
        PageResult<PayInfo> typePageResult = new PageResult<>(typePage.getTotalElements(),typePage.getContent());

        return  new Result(true,StatusCode.OK,"查询成功",typePageResult);
    }

    /**
     * 查询总钱数
     * @return
     */
    @RequestMapping(value = "/sum",method = RequestMethod.GET)
    public Result sumPayMoney(){
        return  new Result(true,StatusCode.OK,"查询成功",payMoneyService.sumPayMoney());
    }

    /**
     * 根据缴费类型查询总钱数
     * @param typeid
     * @return
     */
    @RequestMapping(value = "/sum/{typeid}",method = RequestMethod.GET)
    public Result sumPayMoneyBytypeId(@PathVariable String typeid) {
        return new Result(true,StatusCode.OK,"查询成功！",payMoneyService.sumPayMoneyBytypeId(typeid));
    }
}
