package ru.davidlevi.jsp.web.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * База данных
 *
 * @author david
 */
public class Database {

    private static Connection conn;
    private static InitialContext ic;
    private static DataSource ds;

    /**
     * Метод возващает подключение к БД
     *
     * @return Connection
     */
    public static Connection getConnection() {
        try {
            ic = new InitialContext(); 
            /*
            Метод lookup() принимает параметр из "JDBC Resources" из Glassfish.
            Сам ресурс "jdbc/Library" привязан к пулу "MySQLPool", который 
            имеет параметры подключения к MySQL-серверу:
             URL=jdbc:mysql://localhost:3306/library?characterEncoding=utf8
             user=root
             password=******** 
            */
            ds = (DataSource) ic.lookup("jdbc/Library"); 
            conn = ds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}
