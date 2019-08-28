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

Your Cart:<input type="button" onclick="location.href='purchase/customer/finalizePurchase.do?purchaseId=${purchase.id}';" value="Order!" /> <br>
Total: <jstl:out value="${purchase.totalPrice}"/><br>
<jstl:forEach items="${purchase.products}" var="x">
	name: <jstl:out value="${x.name}"/> ,
	price: <jstl:out value="${x.price}"/>
	<input type="button" onclick="location.href='purchase/customer/removeProduct.do?purchaseId=${purchase.id}&productId=${x.id}';" value="Remove" /> <br>
</jstl:forEach>
<br>

Avaliable products:<br>
<jstl:forEach items="${products}" var="x">
	 name: <jstl:out value="${x.name}"/> ,
	 price: <jstl:out value="${x.price}"/> ,
	 stock: <jstl:out value="${x.stock}"/> 
	 <input type="button" onclick="location.href='purchase/customer/addProduct.do?purchaseId=${purchase.id}&productId=${x.id}';" value="Add" /> <br> 
</jstl:forEach>


