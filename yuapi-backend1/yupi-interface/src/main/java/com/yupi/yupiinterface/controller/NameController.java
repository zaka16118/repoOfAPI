package com.yupi.yupiinterface.controller;



import com.yupi.yuapiclientsdk.model.User;
import com.yupi.yuapiclientsdk.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 名称API
 * @author zxj
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/get")
    public String getNameByGet(String name,HttpServletRequest request){
        String getValues = request.getHeader("yupi");
        System.out.println(getValues);
        return "GET 你的名字是"+name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name){
        return "POST 你的名字是"+ name;
    }

    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest request){
//        String accessKey = request.getHeader("accessKey");
//        String nonce = request.getHeader("nonce");
//        String timestamp = request.getHeader("timestamp");
//        String sign = request.getHeader("sign");
//        String body = request.getHeader("body");
//        //todo 实际情况应该拾是去数据库查是否已分配给用户
//        if (!accessKey.equals("zxj")){
//            throw new RuntimeException("无权限");
//        }
//        //todo 实际应该是在redis缓存中查找该随机数
//        if (Long.parseLong(nonce)>10000){
//            throw new RuntimeException("无权限");
//        }
//        //todo 时间不能超过五分钟
//        //if (timestamp)
//        //todo secretkey是服务端分发给用户的，所以实际上secretkey从数据库中根据每个用户查出的
//        String serverSign = SignUtils.genSign(body, "abcdefgh");
//        if (!sign.equals(serverSign)){
//            throw new RuntimeException("无权限");
//        }
        String result = "POST 你的名字是"+ user.getUsername();
        //接口调用成功后
        return result;

    }
}
