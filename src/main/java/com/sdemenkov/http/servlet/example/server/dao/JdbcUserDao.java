package com.sdemenkov.http.servlet.example.server.dao;

import com.sdemenkov.http.servlet.example.server.dao.connection.ConnectionFactory;
import com.sdemenkov.http.servlet.example.server.dao.mapper.ResultSetMapper;
import com.sdemenkov.http.servlet.example.server.entity.User;
import com.sdemenkov.http.servlet.example.server.exception.InternalServerErrorRuntimeExpection;

import java.sql.*;
import java.util.List;

import static com.sdemenkov.http.servlet.example.server.dao.UserDaoSql.*;

public class JdbcUserDao implements UserDao {
    private ConnectionFactory connectionFactory;
    private ResultSetMapper resultSetMapper;

    @Override
    public List<User> findAll() {
        try (Connection connection = connectionFactory.create();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL);
            return resultSetMapper.map(resultSet, User.class);
        } catch (SQLException e) {
            throw new InternalServerErrorRuntimeExpection(e);
        }
    }

    @Override
    public int save(User user) {
        try (Connection connection = connectionFactory.create();
             PreparedStatement statement = connection.prepareStatement(INSERT_NEW_USER_SQL)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getGender());
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServerErrorRuntimeExpection(e);
        }
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void setResultSetMapper(ResultSetMapper resultSetMapper) {
        this.resultSetMapper = resultSetMapper;
    }
}
