<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 24.03.2017
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Meals list</title>
</head>
<body>
<table>
    <tr bgcolor="#f5f5f5">
        <td width="50"><strong>Id</strong></td>
        <td width="150"><strong>Date&Time</strong></td>
        <td width="200"><strong>Description</strong></td>
        <td width="70"><strong>Calories</strong></td>
        <td width="50"><strong>Edit</strong></td>
        <td width="50"><strong>Delete</strong></td>
    </tr>
<c:forEach items="${meals}" var="item">
    <c:set var="cleanedDateTime" value="${fn:replace(item.dateTime, 'T', ' ')}" />
    <c:if test="${item.exceed == true}"><tr style="color: red"></c:if>
    <c:if test="${item.exceed == false}"><tr style="color: green"></c:if>
    <td width="50">${item.id}</td>
    <td width="150">${cleanedDateTime}</td>
    <td width="200">${item.description}</td>
    <td width="70">${item.calories}</td>
    <td width="50"><a href="meals?action=edit&mealId=<c:out value="${item.id}"/>">Edit</a></td>
    <td width="50"><a href="meals?action=delete&mealId=<c:out value="${item.id}"/>">Delete</a></td>
    </tr>
    <c:set var="lastId" value="${item.id}"/>
</c:forEach>
</table>
<p><a href="meals?action=insert">Add meal</a></p>
</body>
</html>
