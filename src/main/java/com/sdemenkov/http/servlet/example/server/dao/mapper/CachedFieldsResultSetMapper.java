package com.sdemenkov.http.servlet.example.server.dao.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachedFieldsResultSetMapper extends DomainNameBasedResultSetMapper {
    private DomainNameBasedResultSetMapper resultSetMapper;
    private Map<Class, Field[]> classMap = new ConcurrentHashMap<>();

    public CachedFieldsResultSetMapper(DomainNameBasedResultSetMapper resultSetMapper) {
        this.resultSetMapper = resultSetMapper;
    }

    @Override
    protected <T> void fill(T instance, Field field, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        resultSetMapper.fill(instance, field, value);
    }

    @Override
    protected String getSettersMethodName(String fieldName) {
        return resultSetMapper.getSettersMethodName(fieldName);
    }

    @Override
    protected boolean hasColumn(ResultSetMetaData metaData, String columnName) throws SQLException {
        return resultSetMapper.hasColumn(metaData, columnName);
    }

    @Override
    protected Field[] getFieldsFromClass(Class clazz) {
        Field[] declaredFields = classMap.get(clazz);
        if (declaredFields == null) {
            declaredFields = resultSetMapper.getFieldsFromClass(clazz);
            classMap.putIfAbsent(clazz, declaredFields);
        }
        return declaredFields;
    }
}
