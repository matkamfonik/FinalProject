<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Nowa Firma</h1>
<form:form method="post" modelAttribute="business">

    <label for="name">Nazwa</label><br>
    <form:input path="name"/><br>
    <form:errors path="name" cssClass="error"/><br>

    <label for="city">Miasto</label><br>
    <form:input path="city"/><br>
    <form:errors path="city" cssClass="error"/><br>

    <label for="postalCode">Kod pocztowy (XX-XXX)</label><br>
    <form:input path="postalCode"/><br>
    <form:errors path="postalCode" cssClass="error"/><br>

    <label for="street">Ulica</label><br>
    <form:input path="street"/><br>
    <form:errors path="street" cssClass="error"/><br>

    <label for="number">Numer</label><br>
    <form:input path="number"/><br>
    <form:errors path="number" cssClass="error"/><br>

    <label for="apartmentNumber">Numer mieszkania</label><br>
    <form:input path="apartmentNumber"/><br>
    <form:errors path="apartmentNumber" cssClass="error"/><br>

    <label for="nip">NIP</label><br>
    <form:input path="nip"/><br>
    <form:errors path="nip" cssClass="error"/><br>

    <label for="regon">REGON</label><br>
    <form:input path="regon"/><br>
    <form:errors path="regon" cssClass="error"/><br>

    <label for="taxationFormId">Forma opodatkowania</label><br>
    <form:select itemValue="id" itemLabel="name" path="taxationFormId" items="${taxationForms}"/><br>
    <form:errors path="taxationFormId" cssClass="error"/><br>


    <input type="submit" value="Dodaj">
</form:form>
</body>
</html>
