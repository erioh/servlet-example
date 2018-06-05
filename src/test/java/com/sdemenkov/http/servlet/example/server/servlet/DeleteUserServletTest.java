package com.sdemenkov.http.servlet.example.server.servlet;

import com.sdemenkov.http.servlet.example.server.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteUserServletTest {
    @Mock
    private UserService userService;

    @Mock
    private HttpServletResponse servletResponse;

    @Mock
    private HttpServletRequest servletRequest;

    @Test
    public void doPost() throws IOException {

        DeleteUserServlet deleteUserServlet = new DeleteUserServlet();
        when(servletRequest.getParameter("id")).thenReturn("1");
        deleteUserServlet.setUserService(userService);
        deleteUserServlet.doPost(servletRequest, servletResponse);
    }
}