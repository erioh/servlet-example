package com.sdemenkov.http.servlet.example.server.servlet;

import com.sdemenkov.http.servlet.example.server.entity.User;
import com.sdemenkov.http.servlet.example.server.service.UserService;
import com.sdemenkov.http.servlet.example.server.templater.PageGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EditUserServletTest {

    private final EditUserServlet editUserServlet = new EditUserServlet();

    @Mock
    private PageGenerator pageGenerator;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletResponse servletResponse;

    @Mock
    private HttpServletRequest servletRequest;

    @Test
    public void doGet() throws IOException {
        String expected = "result";
        User user = new User();
        when(servletRequest.getParameter("id")).thenReturn("1");
        when(userService.findById(1)).thenReturn(user);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(servletResponse.getWriter()).thenReturn(printWriter);
        when(pageGenerator.getPage(eq("editUser.ftl"), anyMapOf(String.class, Object.class))).thenReturn(expected);
        editUserServlet.setPageGenerator(pageGenerator);
        editUserServlet.setUserService(userService);
        editUserServlet.doGet(servletRequest, servletResponse);
        assertEquals(expected, stringWriter.toString().trim());
    }

    @Test
    public void doPost() throws IOException {
        when(servletRequest.getParameter("firstName")).thenReturn("Sergey");
        when(servletRequest.getParameter("lastName")).thenReturn("Demenkov");
        when(servletRequest.getParameter("gender")).thenReturn("God");
        when(servletRequest.getParameter("age")).thenReturn("33");
        when(servletRequest.getParameter("id")).thenReturn("33");
        editUserServlet.setUserService(userService);
        editUserServlet.doPost(servletRequest, servletResponse);
    }
}