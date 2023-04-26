package com.yupi.yuapicommon.service;


import com.yupi.yuapicommon.model.entity.User;

/**
 * 用户服务
 *
 * @author yupi
 */
public interface InnerUserService {

    //1.数据库中查询是否给用户分配了秘钥（ak\sk）返回类型 用户
    User getInvokeUser(String accessKey);

}
