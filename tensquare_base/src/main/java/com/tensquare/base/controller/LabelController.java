package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;


    /**
     * 查询全部
     * @return
     */
    @GetMapping
    public Result findAll(){
        List<Label> labelList = labelService.findAll();

        return new Result(true, StatusCode.OK,"查询成功!",labelList);
    }
    /**
     * 根据Id查询
     * @return
     */
    @GetMapping("/{labelId}")
    public Result findById(@PathVariable("labelId") String labelId){
        Label label = labelService.findById(labelId);
        System.err.println(333333+"****************");
        return new Result(true, StatusCode.OK,"查询成功!",label);
    }

    /**
     * 添加
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Label label){
        labelService.save(label);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 修改
     * @return
     */
    @PutMapping("/{labelId}")
    public Result update(@RequestBody Label label,@PathVariable("labelId") String labelId){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @return
     */
    @DeleteMapping("/{labelId}")
    public Result delete(@PathVariable("labelId") String labelId){
        labelService.delete(labelId);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 多条件查询
     * @return
     */
    @PostMapping("/search")
    public Result search(@RequestBody Label label){
        List<Label> labelList = labelService.findSearch(label);
        return new Result(true,StatusCode.OK,"查询成功",labelList);
    }

    /**
     * 分页
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result search(@PathVariable("page") Integer page,@PathVariable("size") Integer size,@RequestBody Label label){

        Page<Label> labelList = labelService.findSearchByPage(label,page,size);
        PageResult<Label> labelPageResult = new PageResult<>(labelList.getTotalElements(), labelList.getContent());
        return new Result(true,StatusCode.OK,"查询成功",labelPageResult);
    }

}
