package com.zxj.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try{
            String resource ="mybatis-config.xml";
            //从配置文件读取，转换为stream流
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //从sqlsessionfactory中获取sqlsession实例 开启事务自动提交
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession(true);
    }
}
