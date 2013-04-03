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
    <h2>Add/Update User Resource:</h2>
    <form method="post">
        <input id="hidden_access_token" type="hidden" name="access_token" value="<%= request.getParameter("access_token") %>" />
        <label>Schema (comma separated):<br><input id="schema" name="schema" type="text" value="urn:scim:schemas:core:1.0"></label><br>
        <label>Username:<br><input id="user_name" name="user_name" type="text"></label><br>
        <label>Firstname:<br><input id="firstname" name="firstname" type="text" value="Arthur"></label><br>
        <label>Lastname:<br><input id="lastname" name="lastname" type="text" value="Dent"></label><br>
        <label>Displayname:<br><input id="displayname" name="displayname" type="text" value="Doesn't like to speak to birds anymore ..."></label><br>
        <label>Nickname:<br><input id="nickname" name="nickname" type="text" value="Arthi"></label><br>
        <label>Profileurl:<br><input id="profileurl" name="profileurl" type="text" value="http://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy"></label><br>
        <label>Title:<br><input id="title" name="title" type="text" ></label><br>
        <label>Usertype:<br><input id="usertype" name="usertype" type="text" value="A hapless Englishman"></label><br>
        <label>Preferred Language:<br><input id="preferredlanguage" name="preferredlanguage" type="text" value="Vogon"></label><br>
        <label>Locale:<br><input id="locale" name="locale" type="text" value="vo"></label><br>
        <label>Timezone:<br><input id="timezone" name="timezone" type="text" value="UTC -42 +23 / 5"></label><br>
        <%--<label>Active:<br><input id="active" name="timezone" type="text"></label><br>--%>
        <label>Password:<br><input id="password" name="password" type="text" value="echo 'Bob is not God but I believe in him anyway' | sha512sum"></label><br>

        <%-- TODO move to own site due to more options than just strings ... --%>
        <label>Emails (comma separated):<br><input id="emails" name="emails" type="text"></label><br>
        <label>Phonenumbers (comma separated):<br><input id="phonenumbers" name="phonenumbers" type="text"></label><br>
        <label>IMS (comma separated):<br><input id="ims" name="ims" type="text"></label><br>
        <label>Photos (comma separated):<br><input id="photos" name="photos" type="text"></label><br>
        <label>Addresses (comma separated):<br><input id="addresses" name="addresses" type="text"></label><br>
        <label>Groups (comma separated, will be ignored on update):<br><input id="groups" name="groups" type="text"></label><br>
        <label>Entitlements (comma separated):<br><input id="entitlements" name="entitlements" type="text"></label><br>
        <label>Roles (comma separated):<br><input id="roles" name="roles" type="text"></label><br>
        <label>x509 certificates (comma separated):<br><input id="x509" name="x509" type="text"></label><br>
        <label>Any (comma separated):<br><input id="any" name="any" type="text"></label><br>

        <label>ID for Update:<br><input id="idForUpdate" name="idForUpdate" type="text"></label><br>
        <input type="submit" formaction="createResource" value="Add Resource" />
        <input type="submit" formaction="updateResource" value="Update Resource" />
    </form>
</body>
</html>