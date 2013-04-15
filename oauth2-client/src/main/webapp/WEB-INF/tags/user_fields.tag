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

<%@tag description="A lot of scim fields ..." pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input id="hidden_access_token" type="hidden" name="access_token" value="${access_token}" />
<p><label>Schema (comma separated):<br><input id="schema" name="schema" type="text" value="urn:scim:schemas:core:1.0"></label></p>
<p><label>Username:<input id="user_name" name="user_name" type="text"></label></p>
<p><label>Firstname:<input id="firstname" name="firstname" type="text" value="Arthur"></label></p>
<p><label>Lastname:<input id="lastname" name="lastname" type="text" value="Dent"></label></p>
<p><label>Displayname:<input id="displayname" name="displayname" type="text" value="Doesn't like to speak to birds anymore ..."></label></p>
<p><label>Nickname:<input id="nickname" name="nickname" type="text" value="Arthi"></label></p>
<p><label>Profileurl:<input id="profileurl" name="profileurl" type="text" value="http://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy"></label></p>
<p><label>Title:<input id="title" name="title" type="text" ></label></p>
<p><label>Usertype:<input id="usertype" name="usertype" type="text" value="A hapless Englishman"></label></p>
<p><label>Preferred Language:<input id="preferredlanguage" name="preferredlanguage" type="text" value="Vogon"></label></p>
<p><label>Locale:<input id="locale" name="locale" type="text" value="vo"></label></p>
<p><label>Timezone:<input id="timezone" name="timezone" type="text" value="UTC -42 +23 / 5"></label></p>
<%--<p><label>Active:<input id="active" name="timezone" type="text"></label></p>--%>
<p><label>Password:<input id="password" name="password" type="text" value="echo 'Bob is not God but I believe in him anyway' | sha512sum"></label></p>

<%-- TODO attributes to be sent ... --%>
<p><a href="/oauth2-client/createMultiValueAttribute?access_token=${access_token}&used_for=email">add email</a> </p>
<p><a href="/oauth2-client/createMultiValueAttribute?access_token=${access_token}&used_for=phone">add phonenumber</a> </p>
<p><a href="/oauth2-client/createMultiValueAttribute?access_token=${access_token}&used_for=im">add im</a> </p>
<p><a href="/oauth2-client/createMultiValueAttribute?access_token=${access_token}&used_for=email">add photos</a> </p>
<p><a href="/oauth2-client/createMultiValueAttribute?access_token=${access_token}&used_for=address">add addresses</a> </p>
<%--<p><a href="/oauth2-client/createMultiValueAttribute?access_token=${access_token}&used_for=groups">add groups</a> </p>--%>
<p><a href="/oauth2-client/createMultiValueAttribute?access_token=${access_token}&used_for=entitlement">add entitlements</a> </p>
<p><a href="/oauth2-client/createMultiValueAttribute?access_token=${access_token}&used_for=role">add roles</a> </p>
<p><a href="/oauth2-client/createMultiValueAttribute?access_token=${access_token}&used_for=x509">add x509</a> </p>
<p><a href="#">add any drink</a> </p>