package ru.davidlevi.jsp.web.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ru.davidlevi.jsp.web.beans.Book;

/**
 * Сервлент для отображения pdf-контеста
 *
 * @author david
 */
public class PdfContent extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf"); // контент
        OutputStream out = response.getOutputStream(); // исходящий поток
        try {
            // Получить индекс картинки из параметра запроса
            int index = Integer.valueOf(request.getParameter("index"));

//            // Получаем сессию из карты сессии (все сессии внутри web-контейнера)
//            HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
//            // Необходимая сессия
//            HttpSession session = (HttpSession) sessionMap.get(request.getParameter("session_id"));
//            // Обратимся к списку отобранных книг через глобальный атрибут currentBookList
//            ArrayList<Book> list = (ArrayList<Book>) session.getAttribute("currentBookList");

            /**/
            ArrayList<Book> list = (ArrayList<Book>) request
                    // getSession(false) - если сессии нет, то она не будет создаваться потому, что нам не нужно несколько разных сессий.
                    .getSession(false)
                    .getAttribute("currentBookList");
            // Возьмем из списка книгу по индексу
            Book book = list.get(index);
            // Метод наполняет книгу содержимым ИМЕННО тогда, когда к ней обратились!
            book.fillPdfContent();
            // Установим размер исходящего контента
            response.setContentLength(book.getContent().length);
            // Получим байтовый поток из поля content класса Book и запишем в OutputStream
            out.write(book.getContent());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
