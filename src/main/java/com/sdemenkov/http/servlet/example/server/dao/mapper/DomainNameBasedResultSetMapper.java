package com.sdemenkov.http.servlet.example.server.dao.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DomainNameBasedResultSetMapper {

    public final  <T> List<T> map(ResultSet resultSet, Class<T> clazz) {
        try {
            List<T> list = new ArrayList<>();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()) {
                T newInstance = clazz.newInstance();
                Field[] declaredFields = getFieldsFromClass(clazz);
                for (Field declaredField : declaredFields) {
                    String fieldName = declaredField.getName();
                    if (hasColumn(resultSetMetaData, fieldName)) {
                        Object value = resultSet.getObject(fieldName);
                        fill(newInstance, declaredField, value);
                    }
                }
                list.add(newInstance);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected <T> void fill(T instance, Field field, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> fieldType = field.getType();
        Method method = instance.getClass().getMethod(getSettersMethodName(field.getName()), fieldType);
        method.invoke(instance, value);

    }

    protected String getSettersMethodName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    protected boolean hasColumn(ResultSetMetaData metaData, String columnName) throws SQLException {
        int columnsCount = metaData.getColumnCount();
        for (int x = 1; x <= columnsCount; x++) {
            if (columnName.equals(metaData.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }

    protected Field[] getFieldsFromClass(Class clazz) {
        return clazz.getDeclaredFields();
    }

}
