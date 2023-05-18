<%--
  Created by IntelliJ IDEA.
  User: matka
  Date: 14.05.2023
  Time: 13:03
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
<table>
    <thead>
    <tr>
        <td>Nazwa</td>
        <td>Adres</td>
        <td>NIP</td>
        <td>REGON</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="client" items="${clients}">
        <tr>
            <td>${client.name}</td>
            <td>ul.${client.street}
                ${client.number}
                <c:if test="${not empty client.apartmentNumber}">
                    /${client.apartmentNumber}
                </c:if>
                , ${client.postalCode} ${client.city}
            </td>
            <td>${client.nip}</td>
            <td>${client.regon}</td>
        </tr>
    </c:forEach>
    <tr>
        <td>
            <a href="/view/clients">Dodaj</a>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>
