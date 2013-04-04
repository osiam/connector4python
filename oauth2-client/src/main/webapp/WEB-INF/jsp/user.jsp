<%--
  Created by IntelliJ IDEA.
  User: jtodea
  Date: 22.03.13
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<t:generic_page_template>
    <h2>Created/Updated User:</h2>

    <p>User: ${userResponse}</p>

    <p>Location Header: ${LocationHeader}</p>
</t:generic_page_template>