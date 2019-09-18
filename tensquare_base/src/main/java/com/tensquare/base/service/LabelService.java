package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LabelService {


    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;
    /**
     * 查询全部
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /**
     * 根据Id查询
     */
    public Label findById(String labelId){
        Label label = labelDao.findById(labelId).get();
        return label;
    }

    /**
     * 添加
     */
    public void save(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

   /**
     * 修改
     */
    public void update(Label label){
        labelDao.save(label);
    }

    /**
     * 删除
     */
    public void delete(String labelId){
        labelDao.deleteById(labelId);
    }
    public List<Label> findSearch(Label label) {
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList();
                if (label.getLabelname()!=null && StringUtils.isNotEmpty(label.getLabelname())){
                    Predicate labelName = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(labelName);
                }

                if (label.getState()!=null && StringUtils.isNotEmpty(label.getState())){
                    Predicate state = cb.like(root.get("state").as(String.class), "%" + label.getState() + "%");
                    list.add(state);
                }
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        });
    }

    public Page<Label>  findSearchByPage(Label label, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1,size);
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList();
                if (label.getLabelname()!=null && StringUtils.isNotEmpty(label.getLabelname())){
                    Predicate labelName = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(labelName);
                }
                if (label.getState()!=null && StringUtils.isNotEmpty(label.getState())){
                    Predicate state = cb.like(root.get("state").as(String.class), "%" + label.getState() + "%");
                    list.add(state);
                }
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        },pageable);
    }
}
