package ru.davidlevi.jsp.web.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.davidlevi.jsp.web.db.Database;
import ru.davidlevi.jsp.web.enums.SearchType;

/**
 * Список книг
 *
 * @author david
 */
public class BookList {

    private ArrayList<Book> bookList = new ArrayList<>();

    private ArrayList<Book> getBooks(String sql) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = Database.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setName(rs.getString("name"));
                book.setGenre(rs.getString("genre"));
                book.setIsbn(rs.getString("isbn"));
                book.setAuthor(rs.getString("author"));
                book.setPageCount(rs.getInt("page_count"));
                book.setPublishDate(rs.getInt("publish_year"));
                book.setPublisher(rs.getString("publisher"));
                book.setImage(rs.getBytes("image"));
                /*
                Код "book.setContent(rs.getBytes("content"));" перенесен в метод 
                fillPdfContent() класса Book, чтобы снизить нагрузку на список.
                См. сервлет PdfContent.
                
                Метод fillPdfContent() вызывается именно при обращении к контенту 
                (при открытии книги).
                 */
                bookList.add(book);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bookList;
    }

    /**
     * Все книги в БД
     *
     * @return ArrayList<Book>
     */
    public ArrayList<Book> getAllBooks() {
        return getBooks("select b.id,b.name,b.isbn,b.page_count,b.publish_year, p.name as publisher, "
                + "a.fio as author, g.name as genre, b.image from book b inner join author a on b.author_id=a.id "
                + "inner join genre g on b.genre_id=g.id inner join publisher p on b.publisher_id=p.id order by b.name");
    }

    /**
     * Список книг по жанру
     *
     * @param id long
     * @return ArrayList<Book>
     */
    public ArrayList<Book> getBooksByGenre(long id) {
        if (id == 0) {
            return getAllBooks();
        } else {
            return getBooks("select b.id,b.name,b.isbn,b.page_count,b.publish_year, p.name as publisher, a.fio as author, g.name as genre, b.image from book b "
                    + "inner join author a on b.author_id=a.id "
                    + "inner join genre g on b.genre_id=g.id "
                    + "inner join publisher p on b.publisher_id=p.id "
                    + "where genre_id=" + id + " order by b.name "
                    + ""); // limit 0,5
        }
    }

    /**
     * Список книг начинающихся на букву
     *
     * @param letter String
     * @return ArrayList<Book>
     */
    public ArrayList<Book> getBooksByLetter(String letter) {
        return getBooks("select b.id,b.name,b.isbn,b.page_count,b.publish_year, p.name as publisher, a.fio as author, g.name as genre, b.image from book b "
                + "inner join author a on b.author_id=a.id "
                + "inner join genre g on b.genre_id=g.id "
                + "inner join publisher p on b.publisher_id=p.id where substr(b.name,1,1)='" + letter + "' order by b.name "
                + ""); // limit 0,5
    }

    /**
     * Список книг по строке поиска
     *
     * @param searchStr String
     * @param type SearchType.AUTHOR или SearchType.TITLE
     * @return ArrayList<Book>
     */
    public ArrayList<Book> getBooksBySearch(String searchStr, SearchType type) {
        StringBuilder sql = new StringBuilder("select b.id,b.name,b.isbn,b.page_count,b.publish_year, p.name as publisher, a.fio as author, g.name as genre, b.image from book b "
                + "inner join author a on b.author_id=a.id "
                + "inner join genre g on b.genre_id=g.id "
                + "inner join publisher p on b.publisher_id=p.id ");
        if (type == SearchType.AUTHOR) {
            sql.append("where lower(a.fio) like '%" + searchStr.toLowerCase() + "%' ");
        } else if (type == SearchType.TITLE) {
            sql.append("where lower(b.name) like '%" + searchStr.toLowerCase() + "%' ");
        }
        sql.append("order by b.name ");
        sql.append(""); // limit 0,5
        return getBooks(sql.toString());
    }
}
