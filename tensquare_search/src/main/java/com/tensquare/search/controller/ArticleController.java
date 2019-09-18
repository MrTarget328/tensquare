package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleSearchService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {


    @Autowired
    private ArticleSearchService articleSearchService;
    @PostMapping
    public Result save(@RequestBody Article article) {
        articleSearchService.add(article);
        return new Result(true, StatusCode.OK, "操作成功");
    }

    @GetMapping("/{key}/{page}/{size}")
    public Result findByPage(@PathVariable("key") String key,@PathVariable("page") Integer page,@PathVariable("size") Integer size) {
        Page<Article> articles = articleSearchService.findByPage(key,page,size);
        System.err.println(articles);
        return new Result(true, StatusCode.OK, "操作成功",new PageResult<Article>(articles.getTotalElements(),articles.getContent()));
    }

}
