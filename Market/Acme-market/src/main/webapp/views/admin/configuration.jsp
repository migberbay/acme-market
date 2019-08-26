<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- PRUEBA FORM -->

<security:authorize access="hasRole('ADMIN')">
	<form:form action="system/admin/configuration.do" modelAttribute="configuration">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<acme:textbox path="systemName" code="admin.systemName"/>
	<acme:textbox path="banner" code="admin.banner"/> 
	<acme:textbox path="welcomeTextEnglish" code="admin.welcomeTextEnglish"/>
	<acme:textbox path="welcomeTextSpanish" code="admin.welcomeTextSpanish"/>
	<acme:textbox path="defaultPhoneCode" code="admin.defaultPhoneCode"/>
	
	<acme:submit name="save" code="admin.save"/>
	<acme:cancel url="" code="admin.cancel"/>			

</form:form>

</security:authorize>