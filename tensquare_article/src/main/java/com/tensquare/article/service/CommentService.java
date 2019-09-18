package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.List;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 添加数据
     * @param comment
     */
    public void addComment(Comment comment){
        comment.set_id(idWorker.nextId()+"");
        commentDao.save(comment);
    }

    /**
     * 根据Articleid查询数据
     */
    public List<Comment> findByArticleid(String id){
        return commentDao.findByArticleid(id);
    }

    /**
     * 根据Articleid删除数据
     */
    public void deleteByArticleid(String id){
        commentDao.deleteByArticleid(id);
    }
}
