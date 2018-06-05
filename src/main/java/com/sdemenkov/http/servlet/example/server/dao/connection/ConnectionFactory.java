package com.sdemenkov.http.servlet.example.server.dao.connection;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection create();
}
