package com.tensquare.spit.controller;

        import com.tensquare.spit.pojo.Spit;
        import com.tensquare.spit.service.SpitService;
        import entity.PageResult;
        import entity.Result;
        import entity.StatusCode;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.PageRequest;
        import org.springframework.data.domain.Pageable;
        import org.springframework.data.redis.core.RedisTemplate;
        import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 查询全部
     * @return
     */
    @GetMapping
    public Result findAll(){

        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    /**
     * 查询单条
     * @return
     */
    @GetMapping("{spitId}")
    public Result findById(@PathVariable("spitId") String spitId){

        return new Result(true, StatusCode.OK,"查询成功",spitService.findById(spitId));
    }

    /**
     * 添加
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 修改
     * @return
     */
    @PutMapping("{spitId}")
    public Result update(@PathVariable("spitId")String spitId , @RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     * @return
     */
    @DeleteMapping("{spitId}")
    public Result deleteById(String spitId){
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    /**
     * 根据上级ID查询数据
     */
    @GetMapping("/comment/{parentid}/{page}/{size}")
    public Result findByParentId(@PathVariable("parentid") String parentid,@PathVariable("page")Integer page,@PathVariable("size")Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Spit> spitPage = spitService.findByParentId(parentid, pageable);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(spitPage.getTotalElements(),spitPage.getContent()));
    }


    /**
     * 吐槽点赞 + 不能重复点赞
     */
    @PutMapping("/thumbup/{spitId}")
    public Result thumbup(@PathVariable("spitId")String spitId){
        String userId = "111";
        if(redisTemplate.opsForValue().get("thumbup_"+userId)!= null){
            return new Result(false,StatusCode.REPERROR,"不能重复点赞");
        }
        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+userId,"");
        return new Result(true,StatusCode.OK,"点赞成功!");
    }
}
