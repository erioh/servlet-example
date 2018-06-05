package com.sdemenkov.http.servlet.example.server.dao.mapper;

import com.sdemenkov.http.servlet.example.server.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResultSetMapperTest {
    @Mock
    private ResultSet resultSet;

    @Test
    public void map() throws SQLException {
        User expectedUser = new User();
        expectedUser.setFirstName("Sergey");
        expectedUser.setLastName("Demenkov");
        expectedUser.setGender("God");
        expectedUser.setAge(33);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("firstName")).thenReturn(expectedUser.getFirstName());
        when(resultSet.getString("lastName")).thenReturn(expectedUser.getLastName());
        when(resultSet.getString("gender")).thenReturn(expectedUser.getGender());
        when(resultSet.getInt("age")).thenReturn(expectedUser.getAge());
        ResultSetMapper mapper = new ResultSetMapper();
        List<User> userList = mapper.map(resultSet, User.class);
        assertEquals(1, userList.size());
        User actualUser = userList.get(0);
        assertEquals(expectedUser, actualUser);

    }
}