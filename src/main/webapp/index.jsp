<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/om_styles.css">
</head>
<body>
<div class="login-container">
    <div class="login-content">
        <h1>Please Login or Register</h1>
        <p class="error">${error_login}</p>
        <div class="login-form">
            <form action="${pageContext.request.contextPath}/ServletUser?action=login" method="post">
                <input type="email" name="email" placeholder="e-mail" required>
                <input type="password" name="password" placeholder="password" required>
                <input type="submit" class="btn btn-login" value="login">
            </form>
        </div>
        <div class="register-link">
            <a href="${pageContext.request.contextPath}/register.jsp">Register &bkarow;</a>
        </div>
    </div>
</div>
</body>
</html>
