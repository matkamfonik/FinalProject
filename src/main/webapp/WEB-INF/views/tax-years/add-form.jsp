<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Nowy Rok</h1>
<form:form method="post" modelAttribute="taxYear">

    <label for="year">Rok</label><br>
    <form:input path="year"/><br>
    <form:errors path="year" cssClass="error"/><br>
    <input type="submit" value="Dodaj">
</form:form>
</body>
</html>
