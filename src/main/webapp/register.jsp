<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/om_styles.css">
</head>
<body>
<div class="login-container">
    <div class="register-content">
        <h1>Register into the System</h1>
        <p class="error">${error_register}</p>
        <div class="register-form">
            <form action="${pageContext.request.contextPath}/ServletUser?action=register" method="post">
                <input type="text" name="name" placeholder="YOUR NAME" required>
                <input type="email" name="email" placeholder="YOUR E-MAIL" required>
                <input type="password" name="password" placeholder="YOUR PASSWORD" required>
                <input type="text" name="address" placeholder="YOUR ADDRESS" required>
                <input type="submit" class="btn btn-register" value="REGISTER">
            </form>
        </div>
        <div class="register-link">
            <a href="${pageContext.request.contextPath}/index.jsp">&DoubleLeftArrow; Back</a>
        </div>
    </div>
</div>
</body>
</html>

