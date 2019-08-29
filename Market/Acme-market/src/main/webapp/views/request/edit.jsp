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

<security:authorize access="hasRole('MARKET')">
<form:form action="request/market/edit.do" modelAttribute="request">
	
	<acme:select path="departmentId" code="request.department" items="${deps}" itemLabel="title"/>
	<acme:textbox code="request.quantity" path="quantity"/>	<br/>

	<acme:submit name="save" code="request.save"/>
	<acme:cancel url="request/market/list.do" code="request.cancel"/>
	<br />	

</form:form>
</security:authorize>

<security:authorize access="hasRole('PROVIDER')">
<form:form action="request/provider/edit.do" modelAttribute="request">
	
	<form:label path="status">
		<spring:message code="request.status" />
	</form:label>	
	<form:select path="status" name="status" onchange="initPage(this);">
		<form:option value="0" label="----" />		
		<form:options items="${items}" />
	</form:select>
	<form:errors path="status" cssClass="error" /><br/>

	<div style="display:none" id="rejected">
	<acme:textbox code="request.rejectReason" path="rejectReason" />
	</div>
	<acme:submit name="save" code="request.save"/>
	<acme:cancel url="request/provider/list.do" code="request.cancel"/>
	<br />	

</form:form>
</security:authorize>

<script>
function initPage(select) {
	var selectIndex=select.selectedIndex;
	var selectValue=select.options[selectIndex].text;
	if(selectValue=="REJECTED"){
		document.getElementById('rejected').style.display = 'block';
	}else{
		document.getElementById('rejected').style.display = 'none';
	}
}
</script>



