package com.sdemenkov.http.servlet.example.server.dao.connection.impl.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.sdemenkov.http.servlet.example.server.dao.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static com.sdemenkov.http.servlet.example.server.property.DbPropertiesType.*;

public class MySqlConnectionFactory implements ConnectionFactory {
    private MysqlDataSource dataSource = new MysqlDataSource();


    public MySqlConnectionFactory(Properties dbProperties) {
        String user = dbProperties.getProperty(DB_USERNAME.getName());
        String password = dbProperties.getProperty(DB_PASSWORD.getName());
        String url = dbProperties.getProperty(DB_URL.getName());
        dataSource.setUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
    }

    @Override
    public Connection create() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
