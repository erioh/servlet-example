package com.sdemenkov.http.servlet.example.server.dao.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TimestampToLocalDateTimeConvertResultSetMapper extends DomainNameBasedResultSetMapper {
    private final DomainNameBasedResultSetMapper resultSetMapper;

    public TimestampToLocalDateTimeConvertResultSetMapper(DomainNameBasedResultSetMapper resultSetMapper) {
        this.resultSetMapper = resultSetMapper;
    }

    @Override
    protected <T> void fill(T instance, Field field, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        TimestampToLocalDateTime annotation = field.getAnnotation(TimestampToLocalDateTime.class);
        if (annotation != null) {
            Timestamp timestamp = (Timestamp) value;
            value = timestamp.toLocalDateTime();
        }
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
        return resultSetMapper.getFieldsFromClass(clazz);
    }
}
