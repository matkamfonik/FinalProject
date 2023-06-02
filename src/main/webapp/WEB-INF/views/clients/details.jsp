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
    <tr>
        <td>${client.name}</td>
        <td>${client.street}
            ${client.number}
            <c:if test="${not empty client.apartmentNumber}">
                /${client.apartmentNumber}
            </c:if>
            , ${client.postalCode} ${client.city}
        </td>
        <td>${client.nip}</td>
        <td>${client.regon}</td>
        <td>
            <a href="/view/clients/${client.id}/delete">Usuń</a>
        </td>
    </tr>
    </tbody>
</table>
<a href="/view/clients/all">Wróć</a>
</body>
</html>
