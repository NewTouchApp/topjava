<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meal</title>
</head>
<body>
<h3>
    <a href="index.html">Home</a>
    <a href="addMeal.jsp">Add new meal</a>
</h3>
<hr>
<h2 align="center">Meals</h2>

<table align="center">
    <tr>
        <th>Time</th>
        <th>Description</th>
        <th>Calories</th>
       <%-- <th>Excess</th>--%>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="color:  ${meal.excess ? 'red' : 'green'}">
            <%--<td><fmt:formatDate value="${meal.dateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>--%>
            <td>
                <fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parseDate"/>
                <fmt:formatDate value="${parseDate}" pattern="yyyy-MM.dd HH:mm"/>
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <%--<td>${meal.excess}</td>--%>
            <td><a href="editMeal?action=edit&id=${meal.id}">Edit</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>