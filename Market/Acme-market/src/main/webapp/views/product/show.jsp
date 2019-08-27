<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
	    
	    <div>
	   		<b><spring:message code="package.ticker"   /></b>: <jstl:out value="${package1.ticker}"   /><br/>
	   		<b><spring:message code="package.title"   /></b>: <jstl:out value="${package1.title}"   /> <br/>    		 
	   		<b><spring:message code="package.description"   /></b>: <jstl:out value="${package1.description}"/> <br/>    		 
			<spring:message code="package.moment.format" var="formatMoment"/>
		    <b><spring:message code="package.startDate"/></b>: <fmt:formatDate value="${package1.startDate}" type="date" pattern="${formatMoment}"/> <br/> 
		    <b><spring:message code="package.endDate"/></b>: <fmt:formatDate value="${package1.endDate}" type="date" pattern="${formatMoment}"/> <br/> 
		    <b><spring:message code="package.price" /></b>: <jstl:out value="${package1.price}" /> <br/>
		    <b><spring:message code="package.photo" /></b>: <a href="<jstl:out value="${package1.photo}" />"><jstl:out value="${package1.photo}" /></a> <br/>
		</div>
		<br/>
		
		<acme:cancel url="${uri}" code="package.back"/>
		
