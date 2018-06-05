package com.sdemenkov.http.servlet.example.server.dao;

import com.sdemenkov.http.servlet.example.server.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    int save(User user);

    void delete(int id);
}
