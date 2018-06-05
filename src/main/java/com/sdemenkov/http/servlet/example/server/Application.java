package com.sdemenkov.http.servlet.example.server;

import com.sdemenkov.http.servlet.example.server.dao.UserDaoJdbc;
import com.sdemenkov.http.servlet.example.server.dao.connection.ConnectionFactory;
import com.sdemenkov.http.servlet.example.server.dao.mapper.ResultSetMapper;
import com.sdemenkov.http.servlet.example.server.exception.NotFoundRuntimeException;
import com.sdemenkov.http.servlet.example.server.property.PropertiesFactory;
import com.sdemenkov.http.servlet.example.server.service.UserServiceImpl;
import com.sdemenkov.http.servlet.example.server.servlet.AddUserServlet;
import com.sdemenkov.http.servlet.example.server.servlet.UsersServlet;
import com.sdemenkov.http.servlet.example.server.templater.PageGenerator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Application {
    private int port;
    private String pathToDbProperties;

    private Map<String, HttpServlet> urlToServletMap = new HashMap<>();
    private String pathToTemplates;

    public void start() throws Exception {
        ServletContextHandler contextHandler = new ServletContextHandler();
        urlToServletMap.forEach((url, servlet) -> contextHandler.addServlet(new ServletHolder(servlet), url));
        Server server = new Server(port);
        server.setHandler(contextHandler);
        server.start();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUrlToServletMap(Map<String, HttpServlet> urlToServletMap) {
        this.urlToServletMap = urlToServletMap;
    }

    public static void main(String[] args) throws Exception {
        Application application = new Application();
        application.setPort(8080);
        application.setPathToDbProperties("database.properties");
        application.setPathToTemplates("/templates/");
        application.configuration();
        application.start();
    }

    private void setPathToTemplates(String pathToTemplates) {
        this.pathToTemplates = pathToTemplates;
    }

    public void configuration(){
        Map<String, HttpServlet> urlToServletMap = new HashMap<>();
        UsersServlet usersServlet = new UsersServlet();
        AddUserServlet addUserServlet = new AddUserServlet();
        UserServiceImpl userService = new UserServiceImpl();
        UserDaoJdbc userDao = new UserDaoJdbc();
        ConnectionFactory connectionFactory = new ConnectionFactory();
        PropertiesFactory databasePropertiesFactory = new PropertiesFactory();
        ResultSetMapper mapper = new ResultSetMapper();
        PageGenerator pageGenerator = PageGenerator.instance();
        URL dbProperties = this.getClass().getClassLoader().getResource(this.pathToDbProperties);
        if (dbProperties == null) {
            throw new NotFoundRuntimeException("properties file is not found");
        }
        databasePropertiesFactory.setPath(dbProperties.getPath());

        connectionFactory.setDbProperties(databasePropertiesFactory.create());
        userDao.setResultSetMapper(mapper);
        userDao.setConnectionFactory(connectionFactory);
        userService.setUserDao(userDao);
        usersServlet.setUserService(userService);
        usersServlet.setPageGenerator(pageGenerator);
        addUserServlet.setUserService(userService);
        addUserServlet.setPageGenerator(pageGenerator);
        pageGenerator.setPathToTemplates(this.pathToTemplates);

        urlToServletMap.put("/users", usersServlet);
        urlToServletMap.put("/userAdd", addUserServlet);
        this.setUrlToServletMap(urlToServletMap);
    }

    public void setPathToDbProperties(String pathToDbProperties) {
        this.pathToDbProperties = pathToDbProperties;
    }
}