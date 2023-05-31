<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>STRONA GŁÓWNA</h1>
Witaj <a href="view/users/${user.id}">${user.username}</a> <br>
<c:forEach items="${businesses}" var="business">
    Firma: <a href="view/businesses/${business.id}">${business.name}</a>
    <a href="view/businesses/${business.id}/delete">Usuń</a> <br>

</c:forEach>
<a href="view/businesses">Dodaj firmę</a>
<br>
<br>
<a href="/view/clients/all">Klienci</a>
<h3>Hello</h3>
</body>
</html>
