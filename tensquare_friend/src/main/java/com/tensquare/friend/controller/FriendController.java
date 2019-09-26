package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {


    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserClient userClient;

    /**
     * 添加好友
     * @param friendid
     * @param type
     * @return
     */
    @PutMapping("/like/{friendid}/{type}")
    public Result likeAndNoLike(@PathVariable("friendid") String friendid,@PathVariable("type") String type){
        //获取登录人
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims==null){

            return new Result(false,StatusCode.LOGINERROR,"无权访问!");
        }
        //获取当前登录人的ID
        String userId = claims.getId();

        //判断type
        if (StringUtils.isNotEmpty(type)){
            if (type.equals("1")){
                //添加喜欢的人
                int flag = friendService.like(userId,friendid);
                if (flag == 0){
                    return new Result(false,StatusCode.ERROR,"您已添加过该好友了!");
                }
                if (flag == 1){
                    //更新粉丝数与关注数
                    userClient.updateFanscountAndFollowcount(friendid,userId,1);
                    return new Result(true,StatusCode.OK,"添加成功!");
                }
            }else if (type.equals("2")){
                //添加不喜欢的人
                int flag = friendService.noLike(userId,friendid);
                if (flag == 0){
                    return new Result(false,StatusCode.ERROR,"您已添加过该好友了!");
                }
                if (flag == 1){
                    return new Result(true,StatusCode.OK,"添加成功!");
                }
            }
            return new Result(false,StatusCode.ERROR,"参数格式不正确");
        }else{
            return new Result(false, StatusCode.ERROR,"请选择喜欢与不喜欢");
        }
    }

    /**
     * 删除好友
     */
    @DeleteMapping("{friendid}")
    public Result deleteFriend(@PathVariable String friendid){
        //获取登录人
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims==null){

            return new Result(false,StatusCode.LOGINERROR,"无权访问!");
        }
        //获取当前登录人的ID
        String userId = claims.getId();
        friendService.deleteFriend(userId,friendid);
        userClient.updateFanscountAndFollowcount(friendid,userId,-1);
        return new Result(true,StatusCode.OK,"删除成功!");
    }
}
