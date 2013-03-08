<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>Oauth2 Client Error</title>
</head>
<body>
<h1>Error: <%= request.getParameter("error") %>
</h1>

<p>Error Description: <%= request.getParameter("error_description") %>
</p>

<form action="authcode" method="get">
    <input type="submit" value="Try again"/>
</form>
</body>
</html>