<%--
  Created by IntelliJ IDEA.
  User: matka
  Date: 14.05.2023
  Time: 14:34
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
Nowa forma opodatkowania

<form:form method="post" modelAttribute="taxationForm">

    <label for="name">Nazwa</label><br>
    <form:input path="name"/><br>
    <form:errors path="name" cssClass="error"/><br>

    <label for="pitRate">Stawka PIT[%]</label><br>
    <form:input path="pitRate"/><br>
    <form:errors path="pitRate" cssClass="error"/><br>

    <label for="secondPitRate">Stawka PIT po przekroczeniu progu podatkowego(jeżeli nie dotyczy wpisz 0)[%]</label><br>
    <form:input path="secondPitRate"/><br>
    <form:errors path="secondPitRate" cssClass="error"/><br>

    <label for="taxFreeAllowance">Kwota wolna od podatku</label><br>
    <form:input path="taxFreeAllowance"/><br>
    <form:errors path="taxFreeAllowance" cssClass="error"/><br>

    <label for="healthCareContributionRate">Stawka składki zdrowotnej(jeżeli nie dotyczy wpisz 0)</label><br>
    <form:input path="healthCareContributionRate"/><br>
    <form:errors path="healthCareContributionRate" cssClass="error"/><br>

    <fieldset>
        <lagend for="healthContributionAsCost">Czy składka zdrowotna jest uwzględniana w kosztach uzyskania przychodu?</lagend><br>
        <label for="healthContributionAsCostTrue">Tak</label>
        <form:radiobutton id="healthContributionAsCostTrue" path="healthContributionAsCost" value="true"/><br>
        <label for="healthContributionAsCostFalse">Nie</label>
        <form:radiobutton id="healthContributionAsCostFalse" path="healthContributionAsCost" value="false"/><br>
        <form:errors path="healthContributionAsCost" cssClass="error"/>
    </fieldset><br>

    <label for="taxThreshold">Próg podatkowy</label><br>
    <form:input path="taxThreshold"/><br>
    <form:errors path="taxThreshold" cssClass="error"/><br>

    <fieldset>
        <lagend for="costRate">Czy koszty obniżają wartość dochodu?</lagend><br>
        <label for="costRateTrue">Tak</label>
        <form:radiobutton id="costRateTrue" path="costRate" value="1"/><br>
        <label for="costRateFalse">Nie</label>
        <form:radiobutton id="costRateFalse" path="costRate" value="0"/><br>
        <form:errors path="costRate" cssClass="error"/>
    </fieldset><br>

    <input type="submit" value="Dodaj">
</form:form>
</body>
</html>
