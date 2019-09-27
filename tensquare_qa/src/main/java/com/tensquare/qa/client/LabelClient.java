package com.tensquare.qa.client;

import com.tensquare.qa.client.impl.LabelClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "tensquare-base",fallback = LabelClientImpl.class)
public interface LabelClient {

    @GetMapping("/label/{labelId}")
    public Result findById(@PathVariable("labelId") String labelId);

}
