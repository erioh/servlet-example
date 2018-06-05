package com.sdemenkov.http.servlet.example.server.servlet;

import com.sdemenkov.http.servlet.example.server.service.UserService;
import com.sdemenkov.http.servlet.example.server.templater.PageGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsersServletTest {

    private final UsersServlet usersServlet = new UsersServlet();

    private final Map<String, String[]> parameterMap = new HashMap<>();

    @Mock
    private PageGenerator pageGenerator;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletResponse servletResponse;

    @Mock
    private HttpServletRequest servletRequest;

    @Before
    public void setUp() {
        String[] firstName = {"Sergey"};
        String[] lastName = {"Demenkov"};
        String[] gender = {"God"};
        String[] age = {"33"};
        parameterMap.put("firstName", firstName);
        parameterMap.put("lastName", lastName);
        parameterMap.put("gender", gender);
        parameterMap.put("age", age);
    }
    @Test
    public void doGet() throws IOException {
        String expectedBody = "expected body";
        when(pageGenerator.getPage(eq("users.ftl"), anyMapOf(String.class, Object.class))).thenReturn(expectedBody);
        when(userService.findAll()).thenReturn(new ArrayList<>());
        when(servletRequest.getRequestURI()).thenReturn("/users");
        usersServlet.setPageGenerator(pageGenerator);
        usersServlet.setUserService(userService);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(servletResponse.getWriter()).thenReturn(writer);

        usersServlet.doGet(servletRequest, servletResponse);

        writer.flush();
        assertEquals(expectedBody, stringWriter.toString().trim());
    }

}