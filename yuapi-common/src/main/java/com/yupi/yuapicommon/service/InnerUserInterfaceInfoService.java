package com.yupi.yuapicommon.service;




/**
 *
 */
public interface InnerUserInterfaceInfoService  {


    /**
     * 调用接口次数统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
