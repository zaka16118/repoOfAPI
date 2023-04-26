package com.yupi.yuapicommon.service;


import com.yupi.yuapicommon.model.entity.InterfaceInfo;

/**
 *
 */
public interface InnerInterfaceInfoService {

     //2.  数据库中查询模拟接口是否存在（请求路径、请求方法  ）返回 接口信息
     InterfaceInfo getInterfaceInfo(String path,String method);

}
