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

<security:authorize access="hasRole('ADMIN')">

		<spring:message code="admin.datatable"/>
	<table style="width:'100%' border='0' align='center' ">
			<tr>
				<th><spring:message code="admin.type"/></th>
				<th><spring:message code="admin.requestsPerMarket"/></th>
				<th><spring:message code="admin.requestsPerProvider"/></th>
				<th><spring:message code="admin.purchasesPerCustomer"/></th>
				<th><spring:message code="admin.deliveredPurchasesPerDeliveryBoy"/></th>

			</tr>
			<tr>
				<td><spring:message code="admin.average"/></td>
				<td><jstl:out value="${avgRequestsPerMarket}"/></td>
				<td><jstl:out value="${avgRequestsPerProvider}"/></td>
				<td><jstl:out value="${avgPurchasesPerCustomer}"/></td>
				<td><jstl:out value="${avgDeliveredPurchasesPerDeliveryBoy}"/></td>
			</tr>
			<tr>
				<td><spring:message code="admin.minimum"/></td>
				<td><jstl:out value="${minRequestsPerMarket}"/></td>
				<td><jstl:out value="${minRequestsPerProvider}"/></td>
				<td><jstl:out value="${minPurchasesPerCustomer}"/></td>
				<td><jstl:out value="${minDeliveredPurchasesPerDeliveryBoy}"/></td>
			</tr>	
			<tr>
				<td><spring:message code="admin.maximum"/></td>
				<td><jstl:out value="${maxRequestsPerMarket}"/></td>
				<td><jstl:out value="${maxRequestsPerProvider}"/></td>
				<td><jstl:out value="${maxPurchasesPerCustomer}"/></td>
				<td><jstl:out value="${maxDeliveredPurchasesPerDeliveryBoy}"/></td>
			</tr>
			<tr>
				<td><spring:message code="admin.stdv"/></td>
				<td><jstl:out value="${stdevRequestsPerMarket}"/></td>
				<td><jstl:out value="${stdevRequestsPerProvider}"/></td>
				<td><jstl:out value="${stdevPurchasesPerCustomer}"/></td>
				<td><jstl:out value="${stdevDeliveredPurchasesPerDeliveryBoy}"/></td>
			</tr>
	</table>

	<b><spring:message code="admin.topPendingManagers"/></b>
	<jstl:if test="${empty top10Managers}"><spring:message code="admin.empty"/></jstl:if>	
	<table style="width:'100%' border='0' align='center' ">
		<jstl:forEach var="i" items="${top10Managers}">
		<tr>
			<td><jstl:out value="${i.name}"/> <jstl:out value="${i.surname}"/> (<a href="actor/show.do?actorId=${i.id}"><jstl:out value="${i.userAccount.username}"/></a>)</td>
		</tr>			
		</jstl:forEach>		
	</table> 
</security:authorize>

