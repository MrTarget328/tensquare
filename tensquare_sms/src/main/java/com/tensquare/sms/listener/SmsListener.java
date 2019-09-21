package com.tensquare.sms.listener;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @RabbitHandler
    public void sendSms(Map<String,String> map){
   /*     System.err.println(map.get("checkCode"));
        System.err.println(map.get("moblie"));*/

        try {
            smsUtil.sendSms(
                    map.get("moblie"),
                    map.get("template_code"),
                    map.get("sign_name"),
                    map.get("checkCode")
            );
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
