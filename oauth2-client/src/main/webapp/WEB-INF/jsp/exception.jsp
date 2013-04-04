<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isErrorPage="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<t:generic_page_template>
    <p>${exception.message} </p>
</t:generic_page_template>