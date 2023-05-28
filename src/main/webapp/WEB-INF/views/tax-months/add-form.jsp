<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Nowy Miesiąc</h1>
<form:form method="post" modelAttribute="taxMonth">
    <label for="number">Miesiąc</label><br>
    <form:select path="number"><br>
    <form:option value="1" label="Styczeń"/>
    <form:option value="2" label="Luty"/>
    <form:option value="3" label="Marzec"/>
    <form:option value="4" label="Kwiecień"/>
    <form:option value="5" label="Maj"/>
    <form:option value="6" label="Czerwiec"/>
    <form:option value="7" label="Lipiec"/>
    <form:option value="8" label="Sierpień"/>
    <form:option value="9" label="Wrzesień"/>
    <form:option value="10" label="Październik"/>
    <form:option value="11" label="Listopad"/>
    <form:option value="12" label="Grudzień"/>
    </form:select>
    <form:errors path="number" cssClass="error"/><br>
    <input type="submit" value="Dodaj">
</form:form>
</body>
</html>
