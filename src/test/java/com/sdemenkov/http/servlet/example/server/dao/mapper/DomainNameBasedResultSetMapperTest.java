package com.sdemenkov.http.servlet.example.server.dao.mapper;

import com.sdemenkov.http.servlet.example.server.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DomainNameBasedResultSetMapperTest {
    @Mock
    private ResultSet resultSet;

    @Mock
    private ResultSetMetaData resultSetMetaData;

    @Test
    public void map() throws SQLException {
        User expectedUser = new User();
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        expectedUser.setFirstName("Sergey");
        expectedUser.setLastName("Demenkov");
        expectedUser.setGender("God");
        expectedUser.setAge(33);
        expectedUser.setRegisteredAt(localDateTime);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getObject("firstName")).thenReturn(expectedUser.getFirstName());
        when(resultSet.getObject("lastName")).thenReturn(expectedUser.getLastName());
        when(resultSet.getObject("gender")).thenReturn(expectedUser.getGender());
        when(resultSet.getObject("age")).thenReturn(expectedUser.getAge());
        when(resultSet.getObject("registeredAt")).thenReturn(timestamp);
        when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        when(resultSetMetaData.getColumnCount()).thenReturn(5);
        when(resultSetMetaData.getColumnName(1)).thenReturn("firstName");
        when(resultSetMetaData.getColumnName(2)).thenReturn("lastName");
        when(resultSetMetaData.getColumnName(3)).thenReturn("gender");
        when(resultSetMetaData.getColumnName(4)).thenReturn("age");
        when(resultSetMetaData.getColumnName(5)).thenReturn("registeredAt");
        DomainNameBasedResultSetMapper mapper = new TimestampToLocalDateTimeConvertResultSetMapper(
                new CachedFieldsResultSetMapper(
                        new DomainNameBasedResultSetMapper()));
        List<User> userList = mapper.map(resultSet, User.class);
        assertEquals(1, userList.size());
        User actualUser = userList.get(0);
        assertEquals(expectedUser, actualUser);

    }

    @Test
    public void mapWrong() throws SQLException {
        User expectedUser = new User();
        expectedUser.setFirstName("Sergey");
        expectedUser.setLastName(null);
        expectedUser.setGender("God");
        expectedUser.setAge(33);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getObject("firstName")).thenReturn(expectedUser.getFirstName());
        when(resultSet.getObject("gender")).thenReturn(expectedUser.getGender());
        when(resultSet.getObject("age")).thenReturn(expectedUser.getAge());
        when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        when(resultSetMetaData.getColumnCount()).thenReturn(3);
        when(resultSetMetaData.getColumnName(1)).thenReturn("firstName");
        when(resultSetMetaData.getColumnName(2)).thenReturn("gender");
        when(resultSetMetaData.getColumnName(3)).thenReturn("age");

        DomainNameBasedResultSetMapper mapper = new TimestampToLocalDateTimeConvertResultSetMapper(
                new CachedFieldsResultSetMapper(
                        new DomainNameBasedResultSetMapper()));
        List<User> userList = mapper.map(resultSet, User.class);
        assertEquals(1, userList.size());
        User actualUser = userList.get(0);
        assertEquals(expectedUser, actualUser);

    }
}