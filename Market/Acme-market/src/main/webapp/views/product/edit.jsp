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

<form:form action="product/provider/edit.do" modelAttribute="product">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<acme:textbox code="product.name" path="name"/>	<br/>
	<acme:textbox code="product.stock" path="stock"/>	<br/>
	<acme:textbox code="product.price" path="price"/><br/>
	

	<acme:submit name="save" code="product.save"/>
	<acme:cancel url="product/provider/list.do" code="product.cancel"/>
	<br />	
	

</form:form>