/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.davidlevi.jsp.web.listeners;

import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Слушатель жизненного цикла web-приложения в разрезе сессии
 * <p>
 * Добавлено в web.xml:
 * <listener>
 * <description>HttpSessionListener</description>
 * <listener-class>ru.davidlevi.jsp.web.listeners.SessionListener</listener-class>
 * </listener>
 *
 * @author david
 */
public class SessionListener implements HttpSessionListener {

    /**
     * Сессия была создана
     *
     * @param se
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // Получаем сессию
        HttpSession session = se.getSession();
        // Получаем контекст сервлета
        ServletContext context = session.getServletContext();
        // Из контекста получаем значение атрибута sessionMap
        HashMap sessionMap = (HashMap) context.getAttribute("sessionMap");
        // С карту сессии помещаем в ключ id-сессии значение сессии
        sessionMap.put(session.getId(), session);
    }

    /**
     * Сессия была уничтожена
     *
     * @param se
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // Получаем сессию
        HttpSession session = se.getSession();
        // Получаем контекст сервлета
        ServletContext context = session.getServletContext();
        // Из контекста получаем значение атрибута sessionMap
        HashMap sessionMap = (HashMap) context.getAttribute("sessionMap");
        // Из карты сессии удаляем по ключу id-сессии
        sessionMap.remove(session.getId());
    }
}
