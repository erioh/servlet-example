package com.sdemenkov.http.servlet.example.server.servlet;

import com.sdemenkov.http.servlet.example.server.entity.User;
import com.sdemenkov.http.servlet.example.server.service.UserService;
import com.sdemenkov.http.servlet.example.server.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class AddUserServlet extends HttpServlet {
    private UserService userService;
    private PageGenerator pageGenerator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String responseContent = pageGenerator.getPage("userAdd.ftl", new HashMap<>());
        resp.getWriter().println(responseContent);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = createUserFromParameterMap(req);
            userService.save(user);
            resp.sendRedirect("/users");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (FileNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setPageGenerator(PageGenerator pageGenerator) {
        this.pageGenerator = pageGenerator;
    }

    public User createUserFromParameterMap(HttpServletRequest req) {
        try {
            String firstNames = req.getParameter("firstName");
            String lastNames = req.getParameter("lastName");
            String ages = req.getParameter("age");
            String genders = req.getParameter("gender");
            User user = new User();
            user.setFirstName(firstNames);
            user.setLastName(lastNames);
            user.setAge(Integer.parseInt(ages));
            user.setGender(genders);
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
