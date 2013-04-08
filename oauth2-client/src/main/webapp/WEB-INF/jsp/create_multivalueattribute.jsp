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
<t:generic_page_template>
    <h2>Add a multi value attribute for ${used_for}</h2>
    <form method="post">
        <input id="hidden_used_for" type="hidden" name="used_for" value="${used_for}" />
        <input id="hidden_access_token" type="hidden" name="access_token" value="${access_token}" />
        <p><label>Value (used everywhere): <input id="value" name="value" type="text" value="${value}"></label></p>
        <p><label>Type (used in photo, phone, im): <input id="type" name="schema" type="text" value="${type}"></label></p>
        <p><label>Primary (used in email): <input id="primary" name="primary" type="checkbox" value="${primary}"></label></p>
        <p><label>Display (will be used in groups (not implemented, yet): <input id="display" name="display" type="text" value="*weird gesticulation* there's no such field."></label></p>
        <p><label>Delete: <input id="delete" name="delete" type="checkbox" value="false"></label></p>
        <input type="submit" formaction="/oauth2-client/addListAttribute" value="Update Resource" />
</t:generic_page_template>