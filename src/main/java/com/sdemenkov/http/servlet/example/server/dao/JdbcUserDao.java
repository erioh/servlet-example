package com.sdemenkov.http.servlet.example.server.dao;

import com.sdemenkov.http.servlet.example.server.dao.mapper.ResultSetMapper;
import com.sdemenkov.http.servlet.example.server.entity.User;

import java.sql.*;
import java.util.List;

import static com.sdemenkov.http.servlet.example.server.dao.UserDaoSql.*;

public class JdbcUserDao implements UserDao {
    private com.sdemenkov.http.servlet.example.server.dao.connection.ConnectionFactory connectionFactory;
    private ResultSetMapper resultSetMapper;

    @Override
    public List<User> findAll() {
        try (Connection connection = connectionFactory.create();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL);
            return resultSetMapper.map(resultSet, User.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectionFactory.create();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(int id) {
        try (Connection connection = connectionFactory.create();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<User> userList = resultSetMapper.map(resultSet, User.class);
            if (userList.size() == 0) {
                throw new RuntimeException("No user with id =" + id + " found");
            }
            return userList.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = connectionFactory.create();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SQL)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getGender());
            statement.setInt(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setConnectionFactory(com.sdemenkov.http.servlet.example.server.dao.connection.ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void setResultSetMapper(ResultSetMapper resultSetMapper) {
        this.resultSetMapper = resultSetMapper;
    }
}
