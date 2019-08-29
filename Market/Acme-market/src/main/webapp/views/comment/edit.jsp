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

<form:form action="customer/comment/edit.do" modelAttribute="form">

	<form:hidden path="customer"/>
	<form:hidden path="deliveryBoy"/>
	<form:hidden path="product"/>
	
	<acme:textbox code="comment.text" path="text"/>	<br/>
	
	<form:label path="score">

	<spring:message code="comment.score" />
	</form:label>	
	<form:select path="score"  >
		<form:option value="1" label="1" />
		<form:option value="2" label="2" />
		<form:option value="3" label="3" />
		<form:option value="4" label="4" />
		<form:option value="5" label="5" />
	</form:select>
	<form:errors path="score" cssClass="error" /><br>

	<acme:submit name="save" code="department.save"/>
	<acme:cancel url="purchse/customer/list.do" code="department.cancel"/>
	<br />	
	

</form:form>

