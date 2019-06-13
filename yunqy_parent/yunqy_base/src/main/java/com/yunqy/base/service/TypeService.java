package com.yunqy.base.service;

import com.yunqy.base.dao.TypeDao;
import com.yunqy.base.pojo.Type;
import com.yunqy.base.pojo.User;
import entity.PageResult;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 类型服务层
 */
@Service
public class TypeService {

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private DateWorker dateWorker;

    @Autowired
    private IdWorker idWorker;

    //查询所有用户
    public List findAll() {
        return typeDao.findAll();
    }

    //根据Id查询用户
    public Type findById(String id) {
        return typeDao.findById(id).get();
    }

    //添加用户
    public void add(Type type){
        //生产系统时间
        type.setCreatetime(dateWorker.createDate());
        type.setUpdatetime(dateWorker.createDate());
        type.setStat("1");
        //添加id
        type.setId(idWorker.nextId()+"");
        typeDao.save(type);
    }

    //修改用户
    public void update(Type type) {
        Type oldType = findById(type.getId());
        type.setCreatetime(oldType.getCreatetime());
        type.setUpdatetime(dateWorker.createDate());
        typeDao.save(type);
    }

    //根据id删除用户
    public void deleteById(String id){
        //根据id查询类型对象，把状态码设置为0
        Type type = findById(id);
        type.setStat("0");
        type.setUpdatetime(dateWorker.createDate());
        typeDao.save(type);
    }

    //封装条件的函数
 /*   private Specification createSpecification (Map searchMap) {
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
    }*/

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    public Page<Type> findSearch(int page,int size) {
        //Specification specification = createSpecification(searchMap);
        PageRequest pageRequest = PageRequest.of(page-1,size);
        //return typeDao.findAll(pageRequest);
        return typeDao.findListByStat("1",pageRequest);
    }
}
