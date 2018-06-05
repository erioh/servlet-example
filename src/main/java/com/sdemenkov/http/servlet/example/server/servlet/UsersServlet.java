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
import java.util.List;
import java.util.Map;

public class UsersServlet extends HttpServlet {
    private UserService userService;
    private PageGenerator pageGenerator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> userList = userService.findAll();
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("userList", userList);
        String responseContent = pageGenerator.getPage("users.ftl", parameterMap);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(responseContent);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setPageGenerator(PageGenerator pageGenerator) {
        this.pageGenerator = pageGenerator;
    }

}
