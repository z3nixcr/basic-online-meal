<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Edit Feedback</title>
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
          <form action="${pageContext.request.contextPath}/ServletFeedback?id=${feedback.feedBackId}" method="post">
            <label>Name:
              <input type="text" name="name" value="${feedback.mealName}" readonly>
            </label>
            <label>Rating (from 1 to 10):
              <input type="number" name="rate" min="1" max="10" value="${feedback.rate}" required>
            </label>
            <label>Leave your comment bellow:
              <textarea name="comment" id="comment" cols="30" rows="10">${feedback.message}</textarea>
            </label>
            <label>
              <input type="submit" value="Send Feedback">
            </label>
          </form>
        </div>
      </div>
    </main>
  </div>
</div>
</body>
</html>



