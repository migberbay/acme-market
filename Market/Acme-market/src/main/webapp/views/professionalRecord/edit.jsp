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

<security:authorize access="hasRole('PROVIDER')">

<form:form action="professionalRecord/provider/edit.do" modelAttribute="professional">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<acme:textbox code="professional.companyName" path="companyName"/>	<br/>
	<acme:textbox code="professional.startDate" path="startDate"/><br/>
	<acme:textbox code="professional.endDate" path="endDate"/><br/>
	<acme:textbox code="professional.role" path="role"/><br/>
	<acme:textbox code="professional.attachment" path="attachment"/><br/>
	<acme:textarea code="professional.comments" path="comments"/><br/>

	<acme:submit name="save" code="professional.save"/>
	<acme:cancel url="curricula/provider/show.do" code="professional.cancel"/>
	<br />	

</form:form>

</security:authorize>

