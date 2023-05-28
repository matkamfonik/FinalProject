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
<c:set var="i" value="0"/>
<h1>Rok ${taxYear.year}</h1>
<p>Bilans za poprzedni rok:  ${previousYear.balance}</p>
<p>Bilans VAT za poprzedni rok:  ${previousYear.vatBalance}</p>
<table>
    <thead>
    <tr>
        <td>Miesiąc</td>
        <td>Dochód</td>
        <td>ZUS<br> do zapłaty</td>
        <td>PIT<br> do zapłaty</td>
        <td>VAT<br> do zapłaty</td>
        <td>Aktualny</td>
        <td></td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${taxMonths}" var="taxMonth">
        <tr>
            <td>${taxMonth.number}</td>             <%-- todo zmienić na wyświtlanie nazw miesięcy --%>
            <td>${taxMonth.socialInsurance}</td>
            <td>${taxMonth.pitValue}</td>
            <td>${taxMonth.vatValue}</td>
            <td>
                <c:choose>
                    <c:when test="${taxMonth.upToDate}">
                        Tak
                    </c:when>
                    <c:otherwise>
                        <a href="/view/businesses/${businessId}/tax-years/${taxYear.id}/tax-months/${taxMonth.id}/patch">Nie</a>
                    </c:otherwise>
                </c:choose>
            </td>
            <td><a href="/view/businesses/${businessId}/tax-years/${taxYear.id}/tax-months/${taxMonth.id}">Szczegóły</a></td>
        </tr>
        ${i=taxMonth.number}
    </c:forEach>
    <c:if test="${i<12}">
    <tr>
        <td>
            <a href="/view/businesses/${businessId}/tax-years/${taxYear.id}/tax-months">Dodaj</a>
        </td>
    </tr>
    </c:if>
    </tbody>
</table>
<p>Bilans roczny:  ${taxYear.balance}</p>
<p>Bilans VAT:  ${taxYear.vatBalance}</p>
<a href="/view/businesses/${businessId}">Wróć</a>
</body>
</html>
