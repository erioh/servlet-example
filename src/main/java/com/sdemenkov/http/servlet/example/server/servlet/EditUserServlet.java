package com.sdemenkov.http.servlet.example.server.servlet;

import com.sdemenkov.http.servlet.example.server.entity.User;
import com.sdemenkov.http.servlet.example.server.service.UserService;
import com.sdemenkov.http.servlet.example.server.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditUserServlet extends HttpServlet {
    UserService userService;
    private PageGenerator pageGenerator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);
        User user = userService.findById(id);
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("user", user);
        String responseContent = pageGenerator.getPage("editUser.ftl", parameterMap);
        resp.getWriter().println(responseContent);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User userFromRequest = getUserFromRequest(req);
        userService.update(userFromRequest);
        resp.sendRedirect("/users");

    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setPageGenerator(PageGenerator pageGenerator) {
        this.pageGenerator = pageGenerator;
    }

    public User getUserFromRequest(HttpServletRequest req) {
        String idString = req.getParameter("id");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String gender = req.getParameter("gender");
        String ageString = req.getParameter("age");
        int id = Integer.parseInt(idString);
        int age = Integer.parseInt(ageString);
        User user = new User();
        user.setId(id);
        user.setAge(age);
        user.setGender(gender);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        return user;
    }
}
