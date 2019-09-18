package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * 查询全部内容
     */
    public List<Spit> findAll(){

        return spitDao.findAll();
    }

    /**
     * 根据ID查询单条记录
     */
    public Spit findById(String id){
        Optional<Spit> spit = spitDao.findById(id);
        return spit.get();
    }

    /**
     * 添加一条记录
     */
    public void save(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setPublishtime(new Date());
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        //如果该回复有上级ID的话  回复数加一
        if (spit.getParentid() != null && !"".equals(spit.getParentid())){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.get_id()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    /**
     * 修改一条记录
     */

    public  void update(Spit spit){
        spitDao.save(spit);
    }

    /**
     * 删除单条记录
     */
    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    /**
     * 根据上级ID查询数据
     *
     */
    public Page<Spit> findByParentId(String id, Pageable pageable){

        Page<Spit> spits = spitDao.findByParentid(id,pageable);
        return spits;
    }

    /**
     * 吐槽点赞
     * @param spitId
     */
    public void thumbup(String spitId) {
        //1.查询
      //  Spit spit = spitDao.findById(spitId).get();
        //2.判断 加一
    //    spit.setThumbup((spit.getThumbup() == null ? 0 : spit.getThumbup())+1);
        //3.更新
    //    spitDao.save(spit);

        Query query = new Query();
       query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }
}
