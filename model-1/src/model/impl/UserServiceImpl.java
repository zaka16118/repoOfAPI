package model.impl;

import model.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public void show(String name) {
        System.out.println("userImpl is showing");
    }
}
