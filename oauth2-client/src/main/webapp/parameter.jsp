<html>
<head>
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
</body>
</html>