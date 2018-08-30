<%@page import="java.util.Enumeration"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EmbedPDF Viewer</title>
    </head>

    <body>
        <div class="embedpdf_viewer">
            <!-- Подключаем класс EmbedPDF из файла EmbedPDF.jar 
             См. http://www.randelshofer.ch/embedpdf/index.html файл EmbedPDF-1.1.2.zip 
            -->
            <applet CODE="EmbedPDF.class" archive="<%=getServletContext().getContextPath()%>/jars/EmbedPDF.jar" WIDTH="850" HEIGHT="900">
                <!-- Передадим URL, который требуется отобразить.
                Метод getContextPath() возвращает строку "/Bibliotheque"
                Метод getParameter("index") возвращает значение параметра index
                Метод getSession().getId() возвращает идентификатов сессии для класса EmbedPDF
                
                Класс EmbedPDF получает два параметра: контент и значение параметра session_id текущего пользователя.
                session_id необходим классу EmbedPDF чтобы у аплета был доступ к web-контейнеру. Поэтому мы передаем этот 
                параметр вручную, а внутри аплета его считываем, см. PdfContent.
                -->

                <%
                    String pathContext = request.getContextPath(); // строка "/Bibliotheque10"
                    String index = request.getParameter("index");
                    String sessionId = request.getSession().getId();
                    System.out.printf("%s %s %s", pathContext, index, sessionId);
                %>

                <param name="pdf" value="<%=pathContext%>/PdfContent?index=<%=index%>&session_id=<%=sessionId%>"/> 

                <!-- Могут ли пользователи открыть документ PDF в новом окне? -->
                <param name="enableOpenWindow" value="true"/>

                <!-- Субпиксельное сглаживанием (может быть медленным и требуется больше памяти)? -->
                <param name="enableSubpixAA" value="true"/>

                <!-- Для повышения удобства использования и производительности апплета рекомендуется использовать следующие параметры: -->

                <!-- следует ли искать на сервере тексты, специфичные для языка. -->
                <param name="codebase_lookup" value="false"/>

                <!-- должен ли код апплета делиться с другими апплетами. -->
                <param name="classloader_cache" value="false"/>

                <!-- Обеспечивает ли сервер сжатую версию апплета .pack.gz.
                Объем памяти, который может использовать апплет (128 м - 128 мегабайт). -->
                <param name="java_arguments" value="-Djnlp.packEnabled=true -Xmx128m"/>

                <!-- экран заставки для отображения, в то время как апплет загружает. -->
                <param name="image" value="<%=getServletContext().getContextPath()%>/images/splash.gif"/>

                <!-- граница заставки. -->
                <param name="boxborder" value="false"/>

                <!-- следует ли центрировать экран заставки. -->
                <param name="centerimage" value="true"/>
            </applet>
        </div>
    </body>
</html>
