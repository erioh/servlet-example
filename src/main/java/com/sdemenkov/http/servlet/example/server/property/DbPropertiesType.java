package com.sdemenkov.http.servlet.example.server.property;

public enum  DbPropertiesType {
    DB_USERNAME("mysql.db.username"), DB_PASSWORD("mysql.db.password"), DB_URL("mysql.db.url");
    private String name;

    DbPropertiesType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
