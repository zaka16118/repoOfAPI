package com.yupi.yupiinterface;

import com.yupi.yuapiclientsdk.client.YuApiClient;
import com.yupi.yuapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class YupiInterfaceApplicationTests {

    @Resource
    private YuApiClient yuApiClient;

    @Test
    void contextLoads() {
        String yupi = yuApiClient.getNameByGet("yupi");
        User user = new User();
        user.setUsername("zxj");
        String usernameByPost = yuApiClient.getUsernameByPost(user);
        System.out.println(yupi);
        System.out.println(usernameByPost);
    }

}
