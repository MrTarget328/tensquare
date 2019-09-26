package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("tensquare-user")
public interface UserClient {

    /**
     * 添加关注数和粉丝数
     */
    @PutMapping("/user/{friendId}/{userId}/{x}")
    public void updateFanscountAndFollowcount(@PathVariable("friendId") String friendId,
                                              @PathVariable("userId") String userId,
                                              @PathVariable("x") int x);
}
