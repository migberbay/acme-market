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
				<th><spring:message code="admin.requestsPerManager"/></th>
				<th><spring:message code="admin.pendingRequestsPerManager"/></th>
				<th><spring:message code="admin.requestsPerCustomer"/></th>
				<th><spring:message code="admin.pendingRequestsPerCustomer"/></th>
				<th><spring:message code="admin.filesPerContract"/></th>
				<th><spring:message code="admin.billboardFilesPerContract"/></th>
				<th><spring:message code="admin.radioFilesPerContract"/></th>
				<th><spring:message code="admin.tvFilesPerContract"/></th>
				<th><spring:message code="admin.socialNetworkFilesPerContract"/></th>
				<th><spring:message code="admin.infoFilesPerContract"/></th>
			</tr>
			<tr>
				<td><spring:message code="admin.average"/></td>
				<td><jstl:out value="${avgRequestsPerManager}"/></td>
				<td><jstl:out value="${avgPendingRequestsPerManager}"/></td>
				<td><jstl:out value="${avgRequestsPerCustomer}"/></td>
				<td><jstl:out value="${avgPendingRequestsPerCustomer}"/></td>
				<td><jstl:out value="${avgFilesPerContract}"/></td>
				<td><jstl:out value="${avgBillboardFilesPerContract}"/></td>
				<td><jstl:out value="${avgRadioFilesPerContract}"/></td>
				<td><jstl:out value="${avgTVFilesPerContract}"/></td>
				<td><jstl:out value="${avgSocialNetworkFilesPerContract}"/></td>
				<td><jstl:out value="${avgInfoFilesPerContract}"/></td>
			</tr>
			<tr>
				<td><spring:message code="admin.minimum"/></td>
				<td><jstl:out value="${minRequestsPerManager}"/></td>
				<td><jstl:out value="${minPendingRequestsPerManager}"/></td>
				<td><jstl:out value="${minRequestsPerCustomer}"/></td>
				<td><jstl:out value="${minPendingRequestsPerCustomer}"/></td>
				<td><jstl:out value="${minFilesPerContract}"/></td>
				<td><jstl:out value="${minBillboardFilesPerContract}"/></td>
				<td><jstl:out value="${minRadioFilesPerContract}"/></td>
				<td><jstl:out value="${minTVFilesPerContract}"/></td>
				<td><jstl:out value="${minSocialNetworkFilesPerContract}"/></td>
				<td><jstl:out value="${minInfoFilesPerContract}"/></td>
			</tr>	
			<tr>
				<td><spring:message code="admin.maximum"/></td>
				<td><jstl:out value="${maxRequestsPerManager}"/></td>
				<td><jstl:out value="${maxPendingRequestsPerManager}"/></td>
				<td><jstl:out value="${maxRequestsPerCustomer}"/></td>
				<td><jstl:out value="${maxPendingRequestsPerCustomer}"/></td>
				<td><jstl:out value="${maxFilesPerContract}"/></td>
				<td><jstl:out value="${maxBillboardFilesPerContract}"/></td>
				<td><jstl:out value="${maxRadioFilesPerContract}"/></td>
				<td><jstl:out value="${maxTVFilesPerContract}"/></td>
				<td><jstl:out value="${maxSocialNetworkFilesPerContract}"/></td>
				<td><jstl:out value="${maxInfoFilesPerContract}"/></td>
			</tr>
			<tr>
				<td><spring:message code="admin.stdv"/></td>
				<td><jstl:out value="${stdevRequestsPerManager}"/></td>
				<td><jstl:out value="${stdevPendingRequestsPerManager}"/></td>
				<td><jstl:out value="${stdevRequestsPerCustomer}"/></td>
				<td><jstl:out value="${stdevPendingRequestsPerCustomer}"/></td>
				<td><jstl:out value="${stdevFilesPerContract}"/></td>
				<td><jstl:out value="${stdevBillboardFilesPerContract}"/></td>
				<td><jstl:out value="${stdevRadioFilesPerContract}"/></td>
				<td><jstl:out value="${stdevTVFilesPerContract}"/></td>
				<td><jstl:out value="${stdevSocialNetworkFilesPerContract}"/></td>
				<td><jstl:out value="${stdevInfoFilesPerContract}"/></td>
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

