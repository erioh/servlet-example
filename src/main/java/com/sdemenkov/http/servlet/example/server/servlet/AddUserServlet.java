package com.sdemenkov.http.servlet.example.server.servlet;

import com.sdemenkov.http.servlet.example.server.entity.User;
import com.sdemenkov.http.servlet.example.server.exception.InternalServerErrorRuntimeExpection;
import com.sdemenkov.http.servlet.example.server.service.UserService;
import com.sdemenkov.http.servlet.example.server.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddUserServlet extends HttpServlet {
    private UserService userService;
    private PageGenerator pageGenerator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String templateName = getTemplateName(requestURI);
        String responseContent = pageGenerator.getPage(templateName, new HashMap<>());

        resp.getWriter().println(responseContent);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Map<String, String[]> inputParameterMap = req.getParameterMap();
            User user = createUserFromParameterMap(inputParameterMap);
            userService.save(user);
            List<User> userList = userService.findAll();
            Map<String, Object> parametersMap = new HashMap<>();
            parametersMap.put("userList", userList);
            resp.sendRedirect("/users");
        } catch (InternalServerErrorRuntimeExpection e){
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

    public String getTemplateName(String requestURI){
        return requestURI.substring(1)+".ftl";
    }

    public User createUserFromParameterMap(Map<String, String[]> parameterMap){
        try {
            String[] firstNames = parameterMap.get("firstName");
            String[] lastNames = parameterMap.get("lastName");
            String[] ages = parameterMap.get("age");
            String[] genders = parameterMap.get("gender");
            User user = new User();
            user.setFirstName(firstNames[0]);
            user.setLastName(lastNames[0]);
            user.setAge(Integer.parseInt(ages[0]));
            user.setGender(genders[0]);
            return user;
        } catch (Exception e) {
            throw new InternalServerErrorRuntimeExpection(e);
        }
    }
}
