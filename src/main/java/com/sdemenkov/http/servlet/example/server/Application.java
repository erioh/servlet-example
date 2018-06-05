package com.sdemenkov.http.servlet.example.server;

import com.sdemenkov.http.servlet.example.server.dao.connection.ConnectionFactory;
import com.sdemenkov.http.servlet.example.server.dao.connection.impl.jdbc.MySqlConnectionFactory;
import com.sdemenkov.http.servlet.example.server.dao.jdbc.JdbcUserDao;
import com.sdemenkov.http.servlet.example.server.dao.mapper.CachedFieldsResultSetMapper;
import com.sdemenkov.http.servlet.example.server.dao.mapper.DomainNameBasedResultSetMapper;
import com.sdemenkov.http.servlet.example.server.dao.mapper.TimestampToLocalDateTimeConvertResultSetMapper;
import com.sdemenkov.http.servlet.example.server.exception.NotFoundRuntimeException;
import com.sdemenkov.http.servlet.example.server.property.PropertiesFactory;
import com.sdemenkov.http.servlet.example.server.service.impl.UserServiceImpl;
import com.sdemenkov.http.servlet.example.server.servlet.AddUserServlet;
import com.sdemenkov.http.servlet.example.server.servlet.DeleteUserServlet;
import com.sdemenkov.http.servlet.example.server.servlet.EditUserServlet;
import com.sdemenkov.http.servlet.example.server.servlet.UsersServlet;
import com.sdemenkov.http.servlet.example.server.templater.PageGenerator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

    public Properties getDbProperties(){
        URL dbProperties = this.getClass().getClassLoader().getResource(this.pathToDbProperties);
        if (dbProperties == null) {
            throw new NotFoundRuntimeException("properties file is not found");
        }
        PropertiesFactory databasePropertiesFactory = new PropertiesFactory();
        databasePropertiesFactory.setPath(dbProperties.getPath());
        return databasePropertiesFactory.create();
    }

    public DomainNameBasedResultSetMapper getDomainNameBasedResultSetMapper(){
        return new TimestampToLocalDateTimeConvertResultSetMapper(
                new CachedFieldsResultSetMapper(
                        new DomainNameBasedResultSetMapper()));
    }

    public void configuration() {

        JdbcUserDao userDao = new JdbcUserDao();
        DomainNameBasedResultSetMapper mapper = getDomainNameBasedResultSetMapper();
        userDao.setResultSetMapper(mapper);
        ConnectionFactory connectionFactory = new MySqlConnectionFactory(getDbProperties());
        userDao.setConnectionFactory(connectionFactory);

        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao);

        UsersServlet usersServlet = new UsersServlet();
        AddUserServlet addUserServlet = new AddUserServlet();
        DeleteUserServlet deleteUserServlet = new DeleteUserServlet();
        EditUserServlet editUserServlet = new EditUserServlet();

        usersServlet.setUserService(userService);
        addUserServlet.setUserService(userService);
        deleteUserServlet.setUserService(userService);
        editUserServlet.setUserService(userService);

        PageGenerator pageGenerator = PageGenerator.instance();
        pageGenerator.setPathToTemplates(this.pathToTemplates);

        usersServlet.setPageGenerator(pageGenerator);
        addUserServlet.setPageGenerator(pageGenerator);
        editUserServlet.setPageGenerator(pageGenerator);

        urlToServletMap.put("/users", usersServlet);
        urlToServletMap.put("/user/add", addUserServlet);
        urlToServletMap.putIfAbsent("/user/delete", deleteUserServlet);
        urlToServletMap.putIfAbsent("/user/edit", editUserServlet);
    }

    public void setPathToDbProperties(String pathToDbProperties) {
        this.pathToDbProperties = pathToDbProperties;
    }
}
