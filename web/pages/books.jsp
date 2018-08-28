<%@page import="java.util.ArrayList"%>
<%@page import="ru.davidlevi.jsp.web.enums.SearchType"%>
<%@page import="ru.davidlevi.jsp.web.beans.Book"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<!--Фрагмент header.jspf автоматически вставляется в это место. См. web.xml тег <include-prelude>-->

<!--include означается, что фрагмент встраивается как код без компиляции -->
<%@include file="../WEB-INF/jspf/left_menu.jspf" %>

<!--include означается, что фрагмент встраивается как код без компиляции -->
<%@include file="../WEB-INF/jspf/letters.jspf" %>

<!--Бин "Список книг". Область обзора - данная страница-->
<jsp:useBean id="bookList" class="ru.davidlevi.jsp.web.beans.BookList" scope="page"/>

<div class="book_list"> 
    <%  // Этот кусок кода обрабатывает параметры переданные на эту страницу

        ArrayList<Book> list = null;
        // Если параметр жанр не пустой, то получим значение и получим список книг по жанру
        if (request.getParameter("genre_id") != null) {
            long genreId = Long.valueOf(request.getParameter("genre_id"));
            list = bookList.getBooksByGenre(genreId);
        } else // иначе если параметр буква не пустой, то получим значение, установим как атрибут сессии и получим список книг на букву
        if (request.getParameter("letter") != null) {
            String letter = request.getParameter("letter");
            session.setAttribute("letter", letter);
            list = bookList.getBooksByLetter(letter);
        } else // иначе если поисковая строка не пустая, то получим список книг по поисковой строке
        if (request.getParameter("search_string") != null) {
            String searchStr = request.getParameter("search_string");
            SearchType type = SearchType.TITLE;
            if (request.getParameter("search_option").equals("Автор")) {
                type = SearchType.AUTHOR;
            }
            if (searchStr != null && !searchStr.trim().equals("")) {
                list = bookList.getBooksBySearch(searchStr, type);
            }
        } else // Если атрибут сессии теуший список книг не пустой, то...
        if (session.getAttribute("currentBookList") != null) {
            //.. получим список из атрибута.
            list = (ArrayList<Book>) session.getAttribute("currentBookList");
        } else {
            // иначе получим список всех книг.
            list = bookList.getAllBooks();
        }
    %>

    <h5 style="text-align: left; margin-top:20px;">Найдено книг: <%=list.size()%> </h5>

    <%  // Из атрибута сессии берем список книг
        session.setAttribute("currentBookList", list);
        String pathContext = request.getContextPath(); // строка "/Bibliotheque10"
        
        // Цикл отображения списка книг
        for (Book book : list) {
            int index = list.indexOf(book);
    %>
    <div class="book_info">
        <div class="book_title">
            <p> <a href="<%=pathContext%>/PdfContent?index=<%=index%>" target="_blank"><%=book.getName()%></a></p>
        </div>
        <div class="book_image">
            <!-- Метод getContextPath() возвращает строку "/Bibliotheque10"-->
            <a href="<%=pathContext%>/PdfContent?index=<%=index%>" target="_blank">
                <img src="<%=pathContext%>/ShowImage?index=<%=index%>" height="250" width="190" alt="Обложка"/>
            </a>
        </div>
        <div class="book_details">
            <br><strong>ISBN:</strong> <%=book.getIsbn()%>
            <br><strong>Издательство:</strong> <%=book.getPublisher()%>
            <br><strong>Количество страниц:</strong> <%=book.getPageCount()%>
            <br><strong>Год издания:</strong> <%=book.getPublishDate()%>
            <br><strong>Автор:</strong> <%=book.getAuthor()%>
            <p style="margin:10px;">
                <a href="<%=pathContext%>/PdfContent?index=<%=index%>" target="_blank">Читать</a>
            </p>
        </div>
    </div> 
    <%  }%> 
</div>

<!--Фрагмент footer.jspf автоматически вставляется в это место. См. web.xml тег <include-coda>-->