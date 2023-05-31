<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Miesiąc ${taxMonth.number}</h1>
<c:set var="i" value="0"/>
<c:set var="j" value="0"/>
<table>
    <thead>
    <tr>
        <th>Koszty</th>
    </tr>
    <tr>
        <td>Lp.</td>
        <td>Nazwa</td>
        <td>Typ</td>
        <td>Kwota Netto</td>
        <td>Kwota VAT</td>
        <td>Kwota Brutto</td>
        <td>Uznany VAT</td>
        <td>Uznane koszty</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${costPositions}" var="costPosition">
        <tr>
            <td>${i = 1+i}</td>
            <td>${costPosition.name}</td>
            <td>${costPosition.costTypeName}</td>
            <td>${costPosition.netto}</td>
            <td>${costPosition.vat}</td>
            <td>${costPosition.brutto}</td>
            <td>${costPosition.vatDeducted}</td>
            <td>${costPosition.costIncluded}</td>
            <td>
                <a href="/view/businesses/${businessId}/tax-years/${taxYearId}/tax-months/${taxMonth.id}/cost-positions/${costPosition.id}/">Edytuj</a>
            </td>
            <td>
                <a href="/view/businesses/${businessId}/tax-years/${taxYearId}/tax-months/${taxMonth.id}/cost-positions/${costPosition.id}/delete">Usuń</a>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td>
            <a href="/view/businesses/${businessId}/tax-years/${taxYearId}/tax-months/${taxMonth.id}/cost-positions/">Dodaj</a>
        </td>
    </tr>
    </tbody>
</table>
<table>
    <thead>
    <tr>
        <th>Przychody</th>
    </tr>
    <tr>
        <td>Lp.</td>
        <td>Nazwa</td>
        <td>Kwota Netto</td>
        <td>Kwota VAT</td>
        <td>Kwota Brutto</td>
        <td>Faktura</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${revenuePositions}" var="revenuePosition">
        <tr>
            <td>${j = 1+j}</td>
            <td>${revenuePosition.name}</td>
            <td>${revenuePosition.netto}</td>
            <td>${revenuePosition.vat}</td>
            <td>${revenuePosition.brutto}</td>
            <td>Dodaj</td>
                <%-- todo dodać fakturę --%>
            <td>
                <a href="/view/businesses/${businessId}/tax-years/${taxYearId}/tax-months/${taxMonth.id}/revenue-positions/${revenuePosition.id}">Edytuj</a>
            </td>
            <td>
                <a href="/view/businesses/${businessId}/tax-years/${taxYearId}/tax-months/${taxMonth.id}/revenue-positions/${revenuePosition.id}/delete">Usuń</a>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td>
            <a href="/view/businesses/${businessId}/tax-years/${taxYearId}/tax-months/${taxMonth.id}/revenue-positions">Dodaj</a>
        </td>
    </tr>
    </tbody>
</table>
<table>
    <thead>
    <tr>
        <th>
            Bilans
        </th>
    </tr>
    <tr>
        <td>Przychód</td>
        <td>Koszty</td>
        <td>Dochód</td>
        <td>Skladki ZUS</td>
        <td>VAT</td>
        <td>PIT</td>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${taxMonth.revenue}</td>
        <td>${taxMonth.costs}</td>
        <td>${taxMonth.income}</td>
        <td>${taxMonth.socialInsurance}</td>
        <td>${taxMonth.vatValue}</td>
        <td>${taxMonth.pitValue}</td>
    </tr>
    </tbody>
</table>

<a href="/view/businesses/${businessId}/tax-years/${taxYearId}">Wróć</a>
</body>
</html>
