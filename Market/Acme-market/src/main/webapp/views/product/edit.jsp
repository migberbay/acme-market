<%--
 * edit.jsp
 *
 * Copyright (C) 2015 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${packetTooBig}">
<div class = "error">
	The Stock you're trying to add is too little for the packet size, please increase it to at least <jstl:out value="${form.packetSize}"></jstl:out>
</div>
</jstl:if>
<form:form action="product/provider/edit.do" modelAttribute="form">
	
		<form:hidden path="creating"/>
		
	<jstl:if test="${stock}">
		<form:hidden path="name"/>
		<form:hidden path="price"/>
		<form:hidden path="packetSize"/>
	</jstl:if>
	<jstl:if test="${stock == false}">
		<acme:textbox code="product.name" path="name"/>	<br/>
		<acme:textbox code="product.price" path="price"/><br/>
		<acme:textbox code="product.packetSize" path="packetSize"/>	<br/>
	</jstl:if>
	<acme:textbox code="product.stock" path="totalStock"/>	<br/>
	

	<acme:submit name="save" code="product.save"/>
	<acme:cancel url="product/provider/list.do" code="product.cancel"/>
	<br />	
	

</form:form>