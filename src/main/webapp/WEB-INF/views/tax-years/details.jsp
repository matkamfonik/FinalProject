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
<h1>Rok ${taxYear.year}</h1>
<p>Bilans za poprzedni rok:  ${previousYear.balance}</p>
<p>Bilans VAT za poprzedni rok:  ${previousYear.vatBalance}</p>
<table>
    <thead>
    <tr>
        <td></td>
    </tr>
    </thead>
</table>
<a href="/view/businesses/${businessId}">Wróć</a>
</body>
</html>
