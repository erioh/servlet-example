package com.sdemenkov.http.servlet.example.server.templater;

import com.sdemenkov.http.servlet.example.server.exception.NotFoundRuntimeException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;


public class PageGenerator {
    private String pathToTemplates ;

    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            cfg.setClassForTemplateLoading(this.getClass(), pathToTemplates);
            Template template = cfg.getTemplate(filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            throw new NotFoundRuntimeException(e);
        }
        return stream.toString();
    }

    private PageGenerator() {
        cfg = new Configuration();
    }

    public void setPathToTemplates(String pathToTemplates) {
        this.pathToTemplates = pathToTemplates;
    }
}