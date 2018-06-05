package com.sdemenkov.http.servlet.example.server.dao.connection;

import java.sql.Connection;
import java.util.Properties;

public interface ConnectionFactory {
    Connection create();
}
