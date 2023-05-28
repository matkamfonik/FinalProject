<%--
  Created by IntelliJ IDEA.
  User: matka
  Date: 14.05.2023
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Dodaj pozycję kosztu</h1>
<form:form method="post" modelAttribute="revenuePosition">

    <label for="name">Nazwa</label><br>
    <form:input path="name"/><br>
    <form:errors path="name" cssClass="error"/><br>

    <label for="netto">Kwota Netto</label><br>
    <form:input path="netto"/><br>
    <form:errors path="netto" cssClass="error"/><br>

    <label for="vatRate">Stawka VAT[%]</label><br>
    <form:input path="vatRate"/><br>
    <form:errors path="vatRate" cssClass="error"/><br>

    <input type="submit" value="Dodaj">
</form:form>
</body>
</html>
