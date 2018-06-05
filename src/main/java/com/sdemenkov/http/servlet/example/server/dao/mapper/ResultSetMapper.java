package com.sdemenkov.http.servlet.example.server.dao.mapper;

import com.sdemenkov.http.servlet.example.server.exception.InternalServerErrorRuntimeExpection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
                    if (hasColumn(resultSet, fieldName)) {
                        Class<?> fieldType = declaredField.getType();
                        Method method = clazz.getMethod(getSettersMethodName(fieldName), fieldType);
                        Object value = resultSet.getObject(fieldName);
                        method.invoke(newInstance, value);
                    }
                }
                list.add(newInstance);
            }
            return list;
        } catch (Exception e) {
            throw new InternalServerErrorRuntimeExpection(e);
        }
    }

    public String getSettersMethodName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public boolean hasColumn(ResultSet resultSet, String columnName) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnsCount = metaData.getColumnCount();
        for (int x = 1; x <= columnsCount; x++) {
            if (columnName.equals(metaData.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }

}
