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

<form:form action="package/manager/edit.do" modelAttribute="package1">

	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<security:authorize access="hasRole('MANAGER')">
	
	<acme:textbox code="package.title" path="title"/>	<br/>
	<acme:textarea code="package.description" path="description"/>	<br/>
	<acme:textbox code="package.startDate" path="startDate" placeholder="dd/MM/yyyy HH:mm"/><br/>
	<acme:textbox code="package.endDate" path="endDate" placeholder="dd/MM/yyyy HH:mm"/><br/>
	<acme:textbox code="package.price" path="price"/><br/>
	<acme:textbox code="package.photo" path="photo"/><br/>

	<jstl:if test="${package1.id!=0}">
		<acme:checkbox code="package.isFinal" path="isFinal"/><br/>
	</jstl:if>

	<acme:submit name="save" code="package.save"/>
	<acme:cancel url="package/manager/list.do" code="package.cancel"/>
	<br />	
	
	</security:authorize>

</form:form>