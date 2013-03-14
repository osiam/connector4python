<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--
  ~ Copyright 2013
  ~     tarent AG
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~   www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Auth-Server</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/style.css"/>" />
</head>

<body>

	<h1>Auth-Server</h1>

	<div id="content">
		<c:if test="${not empty param.authentication_error}">
			<h1>Woops!</h1>

			<p class="error">Your login attempt was not successful.</p>
		</c:if>
		<c:if test="${not empty param.authorization_error}">
			<h1>Woops!</h1>

			<p class="error">You are not permitted to access that resource.</p>
		</c:if>

		<h2>Login</h2>

		<p>We've got 1 user: marissa. Go ahead and log in. Marissa's password is "koala".</p>
		<form id="loginForm" name="loginForm"
			action="<c:url value="/login.do"/>" method="post">
			<p>
				<label>Username: <input type='text' name='j_username'
					value="marissa" /></label>
			</p>
			<p>
				<label>Password: <input type='text' name='j_password'
					value="koala" /></label>
			</p>

			<p>
				<input name="login" value="Login" type="submit"/>
			</p>
		</form>
	</div>

</body>
</html>
