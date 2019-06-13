package com.yunqy.base.service;

import com.yunqy.base.dao.PayMoneyDao;
import com.yunqy.base.dao.TypeDao;
import com.yunqy.base.dao.UserDao;
import com.yunqy.base.pojo.PayInfo;
import com.yunqy.base.pojo.PayMoney;
import com.yunqy.base.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utils.DateWorker;
import utils.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;

@Service
public class PayMoneyService {

    @Autowired
    private PayMoneyDao payMoneyDao;

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DateWorker dateWorker;

    @Autowired
    private IdWorker idWorker;

    //查询所有
    public List findAll() {
        return payMoneyDao.findAll();
    }

    //根据id查询缴费记录
    public PayMoney findById(String id) {
        return payMoneyDao.findById(id).get();
    }

    //添加缴费
    public void add(PayMoney payMoney) {
        //生产系统时间
        payMoney.setCreatetime(dateWorker.createDate());
        payMoney.setUpdatetime(dateWorker.createDate());
        payMoney.setStat("1");
        //添加id
        payMoney.setId(idWorker.nextId()+"");
        payMoneyDao.save(payMoney);
    }

    //修改缴费
    public void update(PayMoney payMoney) {
        PayMoney oldPayMoney = findById(payMoney.getId());
        payMoney.setCreatetime(oldPayMoney.getCreatetime());
        //修改修改时间
        payMoney.setUpdatetime(dateWorker.createDate());
        payMoneyDao.save(payMoney);
    }

    //根据id删除缴费
    public void deleteById(String id){
        PayMoney payMoney = findById(id);
        payMoney.setStat("0");
        payMoney.setUpdatetime(dateWorker.createDate());
        payMoneyDao.save(payMoney);
    }

    //根据要求返回指定的信息
    public List findAllMy(){
        ArrayList<PayInfo> payInfoList = new ArrayList<>();
        List<PayMoney> payMoneyList = payMoneyDao.findAll();
        for (int i = 0; i <payMoneyList.size() ; i++) {
            /*PayMoney payMoney = payMoneyList.get(i);
            //封装对象
            PayInfo payInfo = new PayInfo();
            payInfo.setId(payMoney.getId());
            payInfo.setTypeName(typeDao.findById(payMoney.getTypeid()).get().getTypename());
            payInfo.setPayusername(userDao.findById(payMoney.getPayuserid()).get().getUsername());
            payInfo.setPaycount(payMoney.getPaycount());
            payInfo.setCreatetime(payMoney.getCreatetime());
            payInfo.setRemark(payMoney.getRemark());
            //添加到list集合
            payInfoList.add(payInfo);*/
        }
        return payInfoList;
    }

    //封装条件的函数
    private Specification createSpecification (Map searchMap) {
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();

                //封装条件
                if (searchMap.get("typeid") !=null && !"".equals((String)searchMap.get("typeid"))){
                    predicateList.add(cb.equal(root.get("typeid").as(String.class),searchMap.get("typeid")));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

        return specification;
    }

    //根据条件查询
    public List findSearch(Map searchMap){
        Specification specification = createSpecification(searchMap);
        return payMoneyDao.findAll(specification);
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    public Page<PayInfo> findSearchByStat(int page, int size) {
        //Specification specification = createSpecification(searchMap);
        PageRequest pageRequest = PageRequest.of(page-1,size);
        return payMoneyDao.findListByStat("1", pageRequest);
    }

    /**
     * 查询总钱数
     * @return
     */
    public BigDecimal sumPayMoney(){
        return payMoneyDao.sumPayMoney();
    }

    /**
     * 根据缴费类型查询总钱数
     * @return
     */
    public BigDecimal sumPayMoneyBytypeId(String typeid){
        return payMoneyDao.sumPayMoneyBytypeId(typeid);
    }

}
