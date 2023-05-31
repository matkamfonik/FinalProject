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
<form:form method="post" modelAttribute="costPosition">

    <label for="name">Nazwa</label><br>
    <form:input path="name"/><br>
    <form:errors path="name" cssClass="error"/><br>

    <label for="costTypeId">Typ kosztu</label><br>
    <form:select itemValue="id" itemLabel="name" path="costTypeId" items="${costTypes}"/><br>
    <form:errors path="costTypeId" cssClass="error"/><br>

    <label for="netto">Kwota Netto</label><br>
    <form:input path="netto"/><br>
    <form:errors path="netto" cssClass="error"/><br>

    <label for="vatRate">Stawka VAT[%]</label><br>
    <form:input path="vatRate"/><br>
    <form:errors path="vatRate" cssClass="error"/><br>

    <input type="submit" value="Dodaj">
</form:form>
<form:form action="health-insurance" method="get">
    <input type="submit" value="Oblicz składkę zdrowotną">
</form:form>

</body>
</html>
