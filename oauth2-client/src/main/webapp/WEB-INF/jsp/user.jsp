<%--
  Created by IntelliJ IDEA.
  User: jtodea
  Date: 22.03.13
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Oauth2 Client</title>
</head>
<body>
    <h2>Created User:</h2>
    <p>User: <%= request.getAttribute("userResponse") %></p>
    <p>Location Header: <%= request.getAttribute("LocationHeader") %></p>
</body>
</html>