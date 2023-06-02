<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <thead>
        <td>Klient</td>
    </thead>
    <tbody>
    <c:forEach var="client" items="${clients}">
        <tr>
            <td><a href="/view/clients/${client.id}">${client.name}</a></td>
        </tr>
    </c:forEach>
    <tr>
        <td>
            <a href="/view/clients">Dodaj</a>
        </td>
    </tr>
    </tbody>
</table>
<a href="/view">Wróć</a>
</body>
</html>
