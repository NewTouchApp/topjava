<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Add Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add Meals</h2>

<form action="meals" method="get">
    <input type="hidden" name="action" value="add">
    <input type="datetime-local" name="datetime" value="Дата" onclick="this.value=''" />
    <input type="text" name="description" value="Описание" onclick="this.value='desc'"/>
    <input type="number" name="calories" value="Калории" onclick="this.value='500'"/>
    <input type="submit" value="create/add">
 </form>


</body>
</html>