<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


	<display:table name="purchases" id="row" requestURI="purchase/customer/list.do" pagesize="5">

		 <display:column titleKey="purchase.action">
			<a href="purchase/customer/show.do?purchaseId=${row.id}">Show</a><br>
			<jstl:if test="${row.isFinal == false}">
				<a href="purchase/customer/edit.do?purchaseId=${row.id}">Edit</a><br>
				<a href="purchase/customer/delete.do?purchaseId=${row.id}">Delete</a>
			</jstl:if>
		</display:column>
		<display:column property="status" titleKey="purchase.status"/>
		<display:column property="market.companyName" titleKey="purchase.market"/>
		<display:column titleKey="purchase.products">
			<jstl:forEach items="${row.products}" var ="x">
				<jstl:out value="${x.name}"/>     <jstl:out value="${x.price}"/><br>
			</jstl:forEach>
		</display:column>
	</display:table>
	<security:authorize access="hasRole('PROVIDER')">
	<div>
	<a href="product/provider/create.do"> <spring:message code="product.create" /> </a>
	</div>
	</security:authorize>

