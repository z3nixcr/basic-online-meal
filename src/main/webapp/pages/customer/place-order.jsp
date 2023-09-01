<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Place Order</title>
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
                    <img src="${pageContext.request.contextPath}/img/${this_meal.mealId}.jpg" alt="image ${this_meal.mealId}">
                </div>
                <div class="order-form">
                    <form action="${pageContext.request.contextPath}/ServletMealController?action=order&id=${this_meal.mealId}" method="post">
                        <label>Name:
                            <input type="text" name="name" value="${this_meal.name}" readonly>
                        </label>
                        <label>Ingredients:
                            <input type="text" name="ingredients" value="${this_meal.ingredients}" readonly>
                        </label>
                        <label>Price:
                            <input type="text" class="price" name="price" value="$ ${this_meal.price}" readonly>
                        </label>
                        <label>Quantity:
                            <input type="number" name="quantity" min="1" value="1">
                        </label>
                        <label>
                            <input type="submit" value="Place Order">
                        </label>
                    </form>
                </div>
            </div>
        </main>
    </div>
</div>
</body>
</html>



