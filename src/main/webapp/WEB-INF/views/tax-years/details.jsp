<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:choose>
<c:when test="${previousYear.balance < 0}">
    <c:set var="loss" value="${previousYear.balance}"/>
</c:when>
    <c:otherwise>
        <c:set var="loss" value="0"/>
    </c:otherwise>
</c:choose>
<c:set var="i" value="0"/>
<c:set var="pitBalance" value="${(-taxationForm.taxFreeAllowance+loss)*taxationForm.pitRate/100}"/>
<c:choose>
<c:when test="${previousYear.vatBalance < 0}">
    <c:set var="vatBalance" value="${previousYear.vatBalance}"/>
</c:when>
    <c:otherwise>
    <c:set var="vatBalance" value="0"/>
    </c:otherwise>
</c:choose>
<h1>Rok ${taxYear.year}</h1>
<p>Bilans za poprzedni rok: ${previousYear.balance}</p>
<p>Bilans VAT za poprzedni rok: ${previousYear.vatBalance}</p>
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
            <td>${taxMonth.number}</td>
                <%-- todo zmienić na wyświtlanie nazw miesięcy --%>
            <td>${taxMonth.income}</td>
            <td>${taxMonth.socialInsurance}</td>
            <td>
                <c:choose>
                    <c:when test="${pitBalance > 0}">
                        <c:set var="pitBalance" value="0"/>
                    </c:when>
                </c:choose>
                    ${taxMonth.pitValue + pitBalance}
                <c:set var="pitBalance" value="${taxMonth.pitValue + pitBalance}"/>
            </td>
            <td>
                <c:choose>
                    <c:when test="${vatBalance > 0}">
                        <c:set var="vatBalance" value="0"/>
                    </c:when>
                </c:choose>
                    ${taxMonth.vatValue + vatBalance}
                <c:set var="vatBalance" value="${taxMonth.vatValue + vatBalance}"/>
            </td>
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
            <td>
                <a href="/view/businesses/${businessId}/tax-years/${taxYear.id}/tax-months/${taxMonth.id}">Szczegóły</a>
            </td>
            <td>
                <a href="/view/businesses/${businessId}/tax-years/${taxYear.id}/tax-months/${taxMonth.id}/delete">Usuń</a>
            </td>
        </tr>
        <p hidden>
            <c:choose>
                <c:when test="${i<taxMonth.number}">
                    ${i=taxMonth.number}
                </c:when>
            </c:choose></p>
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
<p>Bilans roczny: ${taxYear.balance}</p>
<p>Bilans VAT: ${taxYear.vatBalance}</p>
<a href="/view/businesses/${businessId}">Wróć</a>
</body>
</html>
