<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<security:authorize access="hasRole('DELIVERYBOY')">
<%-- 	<jstl:forEach items="${principal.purchases}" var="x">
		<jstl:if test="${row.status == 'ASSIGNED' && row.deliveryBoy.id == principal.id}">
			
		</jstl:if>
		<jstl:if test="${row.status == 'IN_TRANSIT' && row.deliveryBoy.id == principal.id}">
			
		</jstl:if>
	</jstl:forEach> --%>
	
	<h3>MY PURCHASES:</h3>
	
	<display:table name="principal.purchases" id="row" requestURI="${requestURI}" pagesize="5">

		 <display:column titleKey="purchase.action">
			<jstl:if test="${row.status == 'ASSIGNED'}">
				<a href="purchase/deliveryBoy/setInTransit.do?purchaseId=${row.id}">I'm in transit!</a>
			</jstl:if>
			<jstl:if test="${row.status == 'IN_TRANSIT'}">
				<a href="purchase/deliveryBoy/setDelivered.do?purchaseId=${row.id}">Delivery complete!</a>
			</jstl:if>
		</display:column>
		<display:column property="status" titleKey="purchase.status"/>
		<display:column property="market.companyName" titleKey="purchase.market"/>
		<display:column property="ticker" titleKey="purchase.ticker"/>
	</display:table>	

	</security:authorize>


	<display:table name="purchases" id="row" requestURI="${requestURI}" pagesize="5">

		 <display:column titleKey="purchase.action">
		 <security:authorize access="hasRole('CUSTOMER')">
		 	<a href="purchase/customer/show.do?purchaseId=${row.id}">Show</a><br>
			<jstl:if test="${row.isFinal == false}">
				<a href="purchase/customer/edit.do?purchaseId=${row.id}">Edit</a><br>
				<a href="purchase/customer/delete.do?purchaseId=${row.id}">Delete</a>
			</jstl:if>
		 </security:authorize>
		 
		 <security:authorize access="hasRole('DELIVERYBOY')">
			<jstl:if test="${row.isFinal && row.status == 'PENDING'}">
				<a href="purchase/deliveryBoy/assign.do?purchaseId=${row.id}">Assign Purchase!</a>
			</jstl:if>
		</security:authorize>
			
		</display:column>
		<display:column property="status" titleKey="purchase.status"/>
		<display:column property="market.companyName" titleKey="purchase.market"/>
		
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column titleKey="purchase.products">
			<jstl:forEach items="${row.products}" var ="x">
				<jstl:out value="${x.name}"/>     <jstl:out value="${x.price}"/><br>
			</jstl:forEach>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('DELIVERYBOY')">
		<display:column property="ticker" titleKey="purchase.ticker"/>
	</security:authorize>
</display:table>
	
	

