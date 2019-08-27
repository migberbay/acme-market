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
	   		<b><spring:message code="product.name"   /></b>: <jstl:out value="${product.name}"   /><br/>
	   		<b><spring:message code="product.stock"   /></b>: <jstl:out value="${product.stock}"   /> <br/>  
	   		<b><spring:message code="product.price"   /></b>: <jstl:out value="${product.price}"   /> <br/>    		 
	   		<b><spring:message code="product.provider"   /></b>: <jstl:out value="${product.provider.userAccount.username}"/> <br/>    		 
		    <b><spring:message code="product.department" /></b>: <jstl:out value="${product.department.title}" /> <br/>
		    <b><spring:message code="product.market" /></b>: <jstl:out value="${market.companyName}" /> <br/>
		</div>
		<br/>
		
		<acme:cancel url="${uri}" code="product.back"/>
		
