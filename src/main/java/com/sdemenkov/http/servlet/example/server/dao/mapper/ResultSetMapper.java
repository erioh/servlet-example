package com.sdemenkov.http.servlet.example.server.dao.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResultSetMapper {
    private Map<Class, Field[]> classMap = new ConcurrentHashMap<>();

    public <T> List<T> map(ResultSet resultSet, Class<T> clazz) {
        try {
            List<T> list = new ArrayList<>();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()) {
                T newInstance = clazz.newInstance();
                Field[] declaredFields = getFieldsFromClass(clazz);
                for (Field declaredField : declaredFields) {
                    String fieldName = declaredField.getName();
                    if (hasColumn(resultSetMetaData, fieldName)) {
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
            throw new RuntimeException(e);
        }
    }

    public String getSettersMethodName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public boolean hasColumn(ResultSetMetaData metaData, String columnName) throws SQLException {
        int columnsCount = metaData.getColumnCount();
        for (int x = 1; x <= columnsCount; x++) {
            if (columnName.equals(metaData.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }

    private Field[] getFieldsFromClass(Class clazz) {
        Field[] declaredFields = classMap.get(clazz);
        if (declaredFields == null) {
            declaredFields = clazz.getDeclaredFields();
            classMap.putIfAbsent(clazz, declaredFields);
        }
        return declaredFields;
    }

}
