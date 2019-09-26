package com.tensquare.qa.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("tensquare-base")
public interface LabelClient {

    @GetMapping("/label/{labelId}")
    public Result findById(@PathVariable("labelId") String labelId);

}
