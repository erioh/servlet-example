package com.sdemenkov.http.servlet.example.server.service;

import com.sdemenkov.http.servlet.example.server.dao.UserDao;
import com.sdemenkov.http.servlet.example.server.entity.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public int save(User user) {
        return userDao.save(user);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}