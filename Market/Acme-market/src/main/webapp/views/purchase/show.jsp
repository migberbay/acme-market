<%--
 * edit.jsp
 *
 * Copyright (C) 2015 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<spring:message code="purchase.total"/>: <jstl:out value="${purchase.totalPrice}"/><br>
<spring:message code="purchase.market"/>: <jstl:out value="${purchase.market.companyName }"/><br>
<spring:message code="purchase.ticker"/>: <jstl:out value="${purchase.ticker }"/><br>
<spring:message code="purchase.status"/>:	<jstl:out value="${purchase.status }"/><br>
<spring:message code="purchase.estimatedDate"/>: <jstl:out value="${purchase.estimatedDate }"/><br>

<spring:message code="purchase.DeliveryBoy"/>: <jstl:out value="${purchase.deliveryBoy.name}"/><br><br>

<spring:message code="purchase.Cart"/>:<br>
<jstl:forEach items="${purchase.products}" var="x">
	<spring:message code="purchase.product.name"/>: <jstl:out value="${x.name}"/> ,
	<spring:message code="purchase.product.price"/>: <jstl:out value="${x.price}"/> <br>
</jstl:forEach>
<br>



