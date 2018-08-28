/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.davidlevi.jsp.web.listeners;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

/**
 * Web application lifecycle listener.
 *
 * @author Tim
 */
public class ContextListener implements ServletContextListener {

    private HashMap<String, HttpSession> sessionMap = new HashMap<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("sessionMap", sessionMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("sessionMap");
        sessionMap = null;
    }
}
