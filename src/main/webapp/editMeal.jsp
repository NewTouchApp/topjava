<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Edit Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit Meals</h2>

<form action="meals" method="get">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="${meal.id}">
    <input type="datetime-local" name="datetime" value="${meal.dateTime}"/>
    <input type="text" name="description" value="${meal.description}"/>
    <input type="number" name="calories" value="${meal.calories}"/>
    <button type="submit">Save</button>
    <button onclick="window.history.back()">Canceled</button>
</body>
</html>