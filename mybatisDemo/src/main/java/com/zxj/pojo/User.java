package com.zxj.pojo;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class User {

    private Integer id;

    private String username;

    private String password;

    private Integer age;

    private Date  createtime;

    private Date  updatetime;
}
