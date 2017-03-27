<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 25.03.2017
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Add/edit user</title>
</head>
<body>
<form method="post" action="meals" name="frmAddMeal">
    <c:set var="cleanedDateTime" value="${fn:replace(meal.dateTime, 'T', ' ')}" />
    Meal ID: <input type="text" readonly="readonly" name="mealId" value="<c:out value="${not empty nextId ? nextId : meal.id}"/>"/><br/>
    Date Time: <input type="text" name="dateTime" value="<c:out value="${cleanedDateTime}"/>"/><br/>
    Description: <input type="text" name="description" value="<c:out value="${meal.description}"/>"/><br/>
    Calories: <input type="text" name="calories" value="<c:out value="${meal.calories}"/>"/><br/>
    <input type="submit" value="Submit" />
</form>
</body>
</html>
