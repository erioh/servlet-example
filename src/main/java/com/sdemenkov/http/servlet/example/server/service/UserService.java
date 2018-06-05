package com.sdemenkov.http.servlet.example.server.service;

import com.sdemenkov.http.servlet.example.server.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    int save(User user);
}
