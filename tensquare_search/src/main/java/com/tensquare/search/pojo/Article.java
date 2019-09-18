package com.tensquare.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
@Document(indexName = "tensquare_article",type = "article")
@Data
public class Article implements Serializable {

    /**
     * 是否索引 就是看改域能否被搜索到
     * 是否分词  就表示在搜索的时候是整体匹配还是单词匹配
     * 是否存储  就是表示在页面上能否看到
     */

    @Id
    private String id;//ID

    @Field(index= true,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String title;//标题

    @Field(index= true,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String content;//文章正文

    private String state;//审核状态
}
