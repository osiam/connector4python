<%--
  ~ Copyright (C) 2013 tarent AG
  ~
  ~ Permission is hereby granted, free of charge, to any person obtaining
  ~ a copy of this software and associated documentation files (the
  ~ "Software"), to deal in the Software without restriction, including
  ~ without limitation the rights to use, copy, modify, merge, publish,
  ~ distribute, sublicense, and/or sell copies of the Software, and to
  ~ permit persons to whom the Software is furnished to do so, subject to
  ~ the following conditions:
  ~
  ~ The above copyright notice and this permission notice shall be
  ~ included in all copies or substantial portions of the Software.
  ~
  ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
  ~ EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
  ~ MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
  ~ IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
  ~ CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
  ~ TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
  ~ SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
  --%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:generic_page_template>
    <h2>Get User Resource:</h2>

    <h3>To get delete user click on the buttons with the weird names; Curiously enough, the only thing that went through the mind of the bowl of petunias as it fell was: 'Oh no, not again'</h3>

    <form method="delete">
        <input id="hidden_access" type="hidden" name="access_token" value="${access_token} "/>
        <c:forEach var="u" items="${userIds}">
            <p>
                <input type="submit" formaction="/oauth2-client/resource/delete/${u}" name="username" value="delete ${u}"/>
            </p>
        </c:forEach>
        <h4>Invalid uuids:</h4>
        <c:forEach var="u" items="${invalidUserIds}">
            <p>
                <input type="submit" formaction="/oauth2-client/resource/delete/${u}" name="username" value="delete invalid ${u}"/>
            </p>
        </c:forEach>


    </form>
</t:generic_page_template>
