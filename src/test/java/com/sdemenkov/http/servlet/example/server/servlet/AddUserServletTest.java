package com.sdemenkov.http.servlet.example.server.servlet;

import com.sdemenkov.http.servlet.example.server.entity.User;
import com.sdemenkov.http.servlet.example.server.service.UserService;
import com.sdemenkov.http.servlet.example.server.templater.PageGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddUserServletTest {
    private final AddUserServlet addUserServlet = new AddUserServlet();
    private final Map<String, String[]> parameterMap = new HashMap<>();

    @Mock
    private PageGenerator pageGenerator;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

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
        when(pageGenerator.getPage(eq("userAdd.ftl"), anyMapOf(String.class, Object.class))).thenReturn(expectedBody);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(servletResponse.getWriter()).thenReturn(writer);
        addUserServlet.setPageGenerator(pageGenerator);

        addUserServlet.doGet(servletRequest, servletResponse);
        writer.flush();
        assertEquals(expectedBody, stringWriter.toString().trim());

    }

    @Test
    public void doPost() throws ServletException, IOException {
        when(servletRequest.getParameterMap()).thenReturn(parameterMap);
        addUserServlet.setUserService(userService);
        addUserServlet.setPageGenerator(pageGenerator);
        addUserServlet.doPost(servletRequest, servletResponse);
    }


    @Test
    public void createUserFromParameterMap() {
        User expectedUser = new User();
        expectedUser.setFirstName("Sergey");
        expectedUser.setLastName("Demenkov");
        expectedUser.setGender("God");
        expectedUser.setAge(33);
        when(servletRequest.getParameter("firstName")).thenReturn("Sergey");
        when(servletRequest.getParameter("lastName")).thenReturn("Demenkov");
        when(servletRequest.getParameter("gender")).thenReturn("God");
        when(servletRequest.getParameter("age")).thenReturn("33");
        User actualUser = addUserServlet.createUserFromParameterMap(servletRequest);
        assertEquals(expectedUser, actualUser);
    }
}