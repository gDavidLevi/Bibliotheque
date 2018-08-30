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
 * Слушатель жизненного цикла web-приложения в разрезе контекста
 * <p>
 * Добавлено в web.xml:
 * <listener>
 * <description>ServletContextListener</description>
 * <listener-class>ru.davidlevi.jsp.web.listeners.ContextListener</listener-class>
 * </listener>
 *
 * @author david
 */
public class ContextListener implements ServletContextListener {

    // Карта сессии
    private HashMap<String, HttpSession> sessionMap = new HashMap<>();

    /**
     * Слушатель контекста, что произошла Инициализация
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Установим атрибут карты сессии
        sce.getServletContext().setAttribute("sessionMap", sessionMap);
    }

    /**
     * Слушатель контекста, что произошло Уничтожение
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Удалим атрибут сессии
        sce.getServletContext().removeAttribute("sessionMap");
        // Занулим карту сессии
        sessionMap = null;
    }
}
