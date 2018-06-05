package com.sdemenkov.http.servlet.example.server.dao.connection;

import com.sdemenkov.http.servlet.example.server.exception.InternalServerErrorRuntimeExpection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static com.sdemenkov.http.servlet.example.server.property.DbPropertiesType.*;

public class ConnectionFactory {
    private Properties dbProperties;

    public Connection create() {
        try {
            String user = dbProperties.getProperty(DB_USERNAME.getName());
            String password = dbProperties.getProperty(DB_PASSWORD.getName());
            String url = dbProperties.getProperty(DB_URL.getName());
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new InternalServerErrorRuntimeExpection(e);
        }
    }

    public void setDbProperties(Properties dbProperties) {
        this.dbProperties = dbProperties;
    }
}
