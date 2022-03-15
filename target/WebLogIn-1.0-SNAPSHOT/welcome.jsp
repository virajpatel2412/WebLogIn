<%-- 
    Document   : welcome
    Created on : 23 Feb, 2022, 8:38:54 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome</title>
    </head>
    <body>
        <%
//            Prevent to revisit back Page in Browser
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            
//            Check user has login or not 
            if (((String)session.getAttribute("isValid") != "yes")) {
                response.sendRedirect("index.html");
            }
        %>
        
        Welcome.....

        <a href="logout"><button>
            LogOut
            </button></a>
        <script>
           
            </script>
        
    </body>
</html>
