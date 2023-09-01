<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Order</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/om_styles.css">
</head>
<body>
<div class="index-container">
    <jsp:include page="left_side.jsp"/>
    <div class="index-container-right">
        <jsp:include page="header.jsp"/>

        <main class="my-orders-container">
            <div class="ordered-meals">
                <c:if test="${orders != null}">
                    <table class="orders-table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Meal Name</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Subtotal</th>
                                <th>Edit</th>
                                <th>Delete</th>
                                <th>Comment</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="order" items="${orders}" varStatus="index">
                                <tr>
                                    <td>${index.count}</td>
                                    <td>${order.mealName}</td>
                                    <td>$ ${order.price}</td>
                                    <td>${order.quantity}</td>
                                    <td>$ ${order.totalPrice}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/ServletOrder?action=edit&id=${order.orderId}">
                                            <svg class="edit" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><!--! Font Awesome Pro 6.2.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2022 Fonticons, Inc. -->
                                                <path d="M471.6 21.7c-21.9-21.9-57.3-21.9-79.2 0L362.3 51.7l97.9 97.9 30.1-30.1c21.9-21.9 21.9-57.3 0-79.2L471.6 21.7zm-299.2 220c-6.1 6.1-10.8 13.6-13.5 21.9l-29.6 88.8c-2.9 8.6-.6 18.1 5.8 24.6s15.9 8.7 24.6 5.8l88.8-29.6c8.2-2.8 15.7-7.4 21.9-13.5L437.7 172.3 339.7 74.3 172.4 241.7zM96 64C43 64 0 107 0 160V416c0 53 43 96 96 96H352c53 0 96-43 96-96V320c0-17.7-14.3-32-32-32s-32 14.3-32 32v96c0 17.7-14.3 32-32 32H96c-17.7 0-32-14.3-32-32V160c0-17.7 14.3-32 32-32h96c17.7 0 32-14.3 32-32s-14.3-32-32-32H96z"/>
                                            </svg>
                                        </a>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/ServletOrder?action=delete&id=${order.orderId}">
                                            <svg class="delete" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--! Font Awesome Pro 6.2.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2022 Fonticons, Inc. -->
                                                <path d="M135.2 17.7L128 32H32C14.3 32 0 46.3 0 64S14.3 96 32 96H416c17.7 0 32-14.3 32-32s-14.3-32-32-32H320l-7.2-14.3C307.4 6.8 296.3 0 284.2 0H163.8c-12.1 0-23.2 6.8-28.6 17.7zM416 128H32L53.2 467c1.6 25.3 22.6 45 47.9 45H346.9c25.3 0 46.3-19.7 47.9-45L416 128z"/>
                                            </svg>
                                        </a>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/ServletOrder?action=comment&id=${order.orderId}">
                                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><!--! Font Awesome Pro 6.2.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2022 Fonticons, Inc. -->
                                                <path d="M512 240c0 114.9-114.6 208-256 208c-37.1 0-72.3-6.4-104.1-17.9c-11.9 8.7-31.3 20.6-54.3 30.6C73.6 471.1 44.7 480 16 480c-6.5 0-12.3-3.9-14.8-9.9c-2.5-6-1.1-12.8 3.4-17.4l0 0 0 0 0 0 0 0 .3-.3c.3-.3 .7-.7 1.3-1.4c1.1-1.2 2.8-3.1 4.9-5.7c4.1-5 9.6-12.4 15.2-21.6c10-16.6 19.5-38.4 21.4-62.9C17.7 326.8 0 285.1 0 240C0 125.1 114.6 32 256 32s256 93.1 256 208z"/>
                                            </svg>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th>Total:</th>
                                <th id="total">$ ${total}</th>
                            </tr>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </main>
    </div>
</div>
</body>
</html>
