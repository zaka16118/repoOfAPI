package com.zxj.dao;

import com.zxj.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import com.zxj.utils.MybatisUtils;

import java.util.List;

public class UserDaoTest {

    SqlSession sqlSession;

    @Test
    public void test(){
        try {
            sqlSession = MybatisUtils.getSqlSession();
            UserDao mapper = sqlSession.getMapper(UserDao.class);
            List<User> userList = mapper.getUserList();

            System.out.println("所有用户：");
            for (User user:userList){
                System.out.println(user);
            }
            //System.out.println("id为2用户:");
            //查找指定用户
//            User userGetById = mapper.findUserById(10);
//            System.out.println(userGetById);
//            System.out.println("添加用户");
//            User user = new User();
//            user.setAge(16);
//            user.setPassword("jkhg123");
//            user.setUsername("zgg");
            //int result = mapper.insertUser(user);
            //修改用户-- id:2
//            userGetById.setUsername("udpate name");
//            userGetById.setPassword("udpate password");
//            mapper.updateUserInfo(userGetById);
            //删除指定用户
            int i = mapper.deleteUserById(22);
            System.out.println("delete result: "+i);
            userList = mapper.getUserList();

            System.out.println("所有用户：");
            for (User user:userList){
                System.out.println(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
    }
}
