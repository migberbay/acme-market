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

<security:authorize access="hasRole('CUSTOMER')">

<form:form action="request/customer/edit.do" modelAttribute="request1">
	
	<acme:textbox code="request.customerComments" path="customerComments"/>	<br/>

	<acme:submit name="save" code="request.save"/>
	<acme:cancel url="request/customer/list.do" code="request.cancel"/>
	<br />	

</form:form>

</security:authorize>

<security:authorize access="hasRole('MANAGER')">

<form:form action="request/manager/edit.do" name="manForm"  onsubmit="return confirmReject()" modelAttribute="request1">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<form:label path="status">
		<spring:message code="request.status" />
	</form:label>	
	<form:select path="status" name="status">
		<form:option value="0" label="----" />		
		<form:options items="${items}" />
	</form:select>
	<form:errors path="status" cssClass="error" />
	
	<acme:textbox code="request.managerComments" path="managerComments" id="comments"/>	<br/>

	<acme:submit name="save" code="request.save"/>
	<acme:cancel url="request/manager/list.do" code="request.cancel"/>
	<br />	
	
</form:form>

</security:authorize>
<script>
	function confirmReject() {
		var what =  document.forms["manForm"]["status"];  
		var comments =  document.getElementById("comments");
			
		if(what.value=="REJECTED" && comments.value==""){
			if(!confirm("Reject request without any comments?")){return false;}
		}
	}
</script>

