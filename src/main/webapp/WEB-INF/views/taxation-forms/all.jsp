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
        <td>Stawka PIT[%]</td>
        <td>Stawka PIT po przekroczeniu progu podatkowego[%]</td>
        <td>Kwota wolna od podatku[zł]</td>
        <td>Stawka składki zdrowotnej[%]</td>
        <td>Składka zdrowotna uwzględniana w kosztach?</td>
        <td>Próg podatkowy[zł]</td>
        <td>Koszty obniżają dochód</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="taxationForm" items="${taxationForms}">
        <tr>
            <td>${taxationForm.name}</td>
            <td>${taxationForm.pitRate}</td>
            <c:choose>
                <c:when test="${not empty taxationForm.secondPitRate}">
                    <td> ${taxationForm.secondPitRate}</td>
                </c:when>
                <c:otherwise>
                    <td>n/d</td>
                </c:otherwise>
            </c:choose>
            <td>${taxationForm.taxFreeAllowance}</td>
            <td>${taxationForm.healthCareContributionRate}</td>
            <td>${taxationForm.healthContributionAsCost}</td>
            <c:choose>
                <c:when test="${taxationForm.taxThreshold>0}">
                    <td> ${taxationForm.taxThreshold}</td>
                </c:when>
                <c:otherwise>
                    <td>n/d</td>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${taxationForm.costRate>0}">
                    <td> Tak</td>
                </c:when>
                <c:otherwise>
                    <td>Nie</td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="/view/taxation-forms">Dodaj</a>
</body>
</html>
