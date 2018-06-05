package com.sdemenkov.http.servlet.example.server.dao;

public class UserDaoSql {
    public final static String FIND_ALL_SQL = "select * from new_user";
    public static final String INSERT_NEW_USER_SQL = "insert into new_user (firstName, lastName, age, gender) values(?,?,?,?)";
    public static final String DELETE_USER_SQL = "delete from new_user where id = ?";
}
