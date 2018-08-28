package ru.davidlevi.jsp.web.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.davidlevi.jsp.web.beans.Book;

/**
 * Сервлет для отображения картинки
 *
 * @author david
 */
public class ShowImage extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg"); // контент
        OutputStream out = response.getOutputStream(); // исходящий поток
        try {
            // Получить индекс картинки из параметра запроса
            int index = Integer.valueOf(request.getParameter("index"));
            // Обратимся к списку отобранных книг через глобальный атрибут currentBookList
            ArrayList<Book> list = (ArrayList<Book>) request.getSession(false).getAttribute("currentBookList");
            // Возьмем из списка книгу по индексу
            Book book = list.get(index);
            // Установим размер исходящего контента
            response.setContentLength(book.getImage().length);
            // Получим байтовый поток из поля image класса Book и запишем в OutputStream
            out.write(book.getImage());
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
