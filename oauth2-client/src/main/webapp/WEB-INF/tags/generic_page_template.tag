<%@tag description="Generic Page template" pageEncoding="UTF-8" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <style>
        .menuUl {
            float: left;
            width: 100%;
            padding: 0;
            margin: 0;
            list-style-type: none;
        }

        .menuLink {
            float: left;
            width: 20em;
            text-decoration: none;
            color: white;
            background-color: black;
            padding: 0.2em 0.6em;
            border-right: 1px solid white;
        }

        .menuLink:hover {
            background-color: #a9a9a9;
        }

        .menuli {
            display: inline;
        }
    </style>

</head>
<body>
<div class="menu" id="page_header">
    <ul class="menuUl">
        <li class="menuli"><a class="menuLink" href="/oauth2-client/authcode">Get Access Token</a></li>
        <li class="menuli"><a class="menuLink" href="/oauth2-client/crud/user/get?access_token=${access_token}">GET USER</a></li>
        <li class="menuli"><a class="menuLink" href="/oauth2-client/crud/user/put?access_token=${access_token}">PUT USER</a></li>
        <li class="menuli"><a class="menuLink" href="/oauth2-client/crud/user/put?access_token=${access_token}">POST USER</a></li>
        <li class="menuli"><a class="menuLink" href="/oauth2-client/crud/user/patch?access_token=${access_token}">PATCH USER</a></li>
    </ul>
</div>
<div id="body">
    <jsp:doBody/>
</div>
<div id="page_footer">
    <jsp:invoke fragment="footer"/>
</div>
</body>
</html>