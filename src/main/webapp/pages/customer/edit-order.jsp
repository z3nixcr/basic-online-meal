<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Order</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/om_styles.css">
</head>
<body>
<div class="index-container">
    <jsp:include page="left_side.jsp"/>
    <div class="index-container-right">
        <jsp:include page="header.jsp"/>

        <main class="order-container">
            <div class="order-content">
                <div class="order-content-img">
                    <img src="${pageContext.request.contextPath}/img/${order.mealId}.jpg" alt="image ${order.mealId}">
                </div>
                <div class="order-form">
                    <form action="${pageContext.request.contextPath}/ServletOrder?action=edit&id=${order.orderId}" method="post">
                        <label>Name:
                            <input type="text" name="name" value="${order.mealName}" readonly>
                        </label>
                        <label>Price:
                            <input type="text" class="price" name="price" value="$ ${order.price}" readonly>
                        </label>
                        <label>Quantity:
                            <input type="number" name="quantity" min="1" value="${order.quantity}">
                        </label>
                        <label>
                            <input type="submit" value="Submit Changes">
                        </label>
                    </form>
                </div>
            </div>
        </main>
    </div>
</div>
</body>
</html>



