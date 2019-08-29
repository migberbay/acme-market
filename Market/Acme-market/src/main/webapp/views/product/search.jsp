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

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="product/search.do" modelAttribute="stringFinderForm">
		
	<acme:textbox code="product.search.keyword" path="keyword"/>

	<acme:submit name="save" code="product.search"/>
	<acme:submit name="clear" code="product.search.clear" />

</form:form>
<br/>
	
	<display:table name="products" id="row" requestURI="${requestURI}" pagesize="5">
			<display:column titleKey="product.options">
				<a href="product/show.do?productId=${row.id}"><spring:message code="product.show"/></a><br/>
			</display:column>
		<display:column property="name" titleKey="product.name"/>
		<display:column property="price" titleKey="product.price"/>
		<display:column property="stock" titleKey="product.stock"/>
		<jstl:forEach items="${departments}" var="x">
			<jstl:if test="${row.department.id==x.id}">
				<display:column titleKey="product.market">
					<a href="actor/show.do?actorId=${x.market.id}"><jstl:out value="${x.market.companyName}" /></a>
				</display:column>
			</jstl:if>
		</jstl:forEach>
	</display:table>