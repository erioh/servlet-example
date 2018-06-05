package com.sdemenkov.http.servlet.example.server.dao.mapper;

import com.sdemenkov.http.servlet.example.server.exception.InternalServerErrorRuntimeExpection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetMapper {
    public <T> List<T> map(ResultSet resultSet, Class<T> clazz) {
        try {
            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                T newInstance = clazz.newInstance();
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    String fieldName = declaredField.getName();
                    Class<?> fieldType = declaredField.getType();
                    if (fieldType.getName().equals("java.lang.String")) {
                        Method method = clazz.getMethod(getSettersMethodName(fieldName), String.class);
                        String value = resultSet.getString(fieldName);
                        method.invoke(newInstance, value);
                    } else if (fieldType.getName().equals("java.lang.Integer")) {
                        int value = resultSet.getInt(fieldName);
                        Method method = clazz.getMethod(getSettersMethodName(fieldName), Integer.class);
                        method.invoke(newInstance, value);
                    } else {
                        throw new InternalServerErrorRuntimeExpection("Unsupported datatype: " + fieldType.getName());
                    }
                }
                list.add(newInstance);
            }
            return list;
        } catch (SQLException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            throw new InternalServerErrorRuntimeExpection(e);
        }
    }

    public String getSettersMethodName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

}
