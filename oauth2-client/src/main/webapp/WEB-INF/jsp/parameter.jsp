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
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Oauth2 Client</title>
</head>
<body>
    <h2>Parameters:</h2>
    <p>ClientId: <%= request.getAttribute("client_id") %></p>
    <p>Client secret: <%= request.getAttribute("client_secret") %></p>
    <p>Redirect Uri: <%= request.getAttribute("redirect_uri") %></p>
    <p>Auth code: <%= request.getAttribute("code") %></p>
    <p>Authorization-Server response:</p>
    <p><%= request.getAttribute("response") %></p>
    <h2>Get User Resource:</h2>
    <form method="post">
        <input id="hidden_access" type="hidden" name="access_token" value="<%= request.getAttribute("access_token") %>" />
        <label>Username:<br><input id="username" name="username" type="text"></label><br>
        <input type="submit" formaction="resource" value="Get Resource"/>
    </form>
    <a href="crud/user?access_token=<%= request.getAttribute("access_token") %>">moep!</a>
</body>
</html>