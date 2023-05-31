<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>${business.name}</h1>
Adres:<br>
ul.${business.street} ${business.number}
<c:if
        test="${not empty business.apartmentNumber}">/${business.apartmentNumber}</c:if><br>
${business.postalCode} ${business.city}<br>
NIP: ${business.nip}<br>
REGON: ${business.regon}<br>
Forma opodatkowania: <a href="/view/taxation-forms/${business.taxationFormId}">${business.taxationFormName}</a><br>

<table>
    <thead>
    <tr>
        <td>Rok podatkowy</td>
        <td>Bilans</td>
        <td>Bilans VAT</td>
        <td>Aktualny</td>
        <td></td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${taxYears}" var="taxYear">
        <tr>
            <td>${taxYear.year}</td>
            <td>${taxYear.balance}</td>
            <td>${taxYear.vatBalance}</td>
            <td>
                <c:choose>
                    <c:when test="${taxYear.upToDate}">
                        Tak
                    </c:when>
                    <c:otherwise>
                        <a href="/view/businesses/${business.id}/tax-years/${taxYear.id}/patch">Nie</a>
                    </c:otherwise>
                </c:choose>
            </td>
            <td><a href="/view/businesses/${business.id}/tax-years/${taxYear.id}">Szczegóły</a></td>
        </tr>
    </c:forEach>
    <tr>
        <td>
            <a href="/view/businesses/${business.id}/tax-years">Dodaj</a>
        </td>
    </tr>
    </tbody>
</table>
<a href="/view">Wróć</a>
</body>
</html>
