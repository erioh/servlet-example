package com.sdemenkov.http.servlet.example.server.property;

import com.sdemenkov.http.servlet.example.server.exception.NotFoundRuntimeException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFactory {
    private String path;
    public Properties create(){
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(path));
            return properties;

        } catch (IOException e) {
            throw new NotFoundRuntimeException(e);
        }
    }

    public void setPath(String path) {
        this.path = path;
    }
}
