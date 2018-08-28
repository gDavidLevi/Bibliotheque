<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Онлайн библиотека :: Вход</title>
        <link href="css/style_index.css" rel="stylesheet" type="text/css">
    </head>

    <%--
    Директивы <%@...%> - служебная инфа, импорт классов из др. проектов
    Скрипты:
        декларации <%!...%> - объявление переменной <%! int count; %>
        скриптлеты <%...%> - java-код
        выражения <%=...%> - вывод результата
    --%>
    
    <body>
        <%  // Получим новую сессию
            // Если пришел запрос с параметром session=0 (со страницы book.jsp) или без него, то...
            if (request.getParameter("session") != null && request.getParameter("session").equals("0")) {
                session.invalidate(); // ...анулировать сессию и...
                request.getSession(true); // ...запросить новую сессию с сервера.
            }%>

        <div class="main">
            <div class="header">
                <p class="title"><span class="text"><img src="images/lib.png" width="76" height="77" hspace="10" vspace="10" align="middle"></span></p>
                <p class="title">Онлайн библиотека</p> 
                <p class="text">Технологии: HTML, CSS, Java EE (Glassfish, сервлеты, параметры и атрибуты, cookies, Java Server Pages).</p>
            </div>

            <div class="authorization">
                <p class="title">Для входа введите своё имя</p>
                <!-- Упрощенная форма авторизации. Вводим имя пользователя -->
                <form class="username" name="username" action="pages/main.jsp" method="POST">
                    Имя: <input type="text" name="username" value="" size="20" />
                    <input type="submit" value="Войти" />
                </form>
            </div>

            <div class="footer">
                Учащийся: Давид Леви, 2018 г.
                <p class="text">По всем вопросам обращайтесь по адресу <a href="mailto:gdavidlevy@gmail.com">gdavidlevy@gmail.com</a></p> 
            </div>
        </div>
    </body>
</html> 