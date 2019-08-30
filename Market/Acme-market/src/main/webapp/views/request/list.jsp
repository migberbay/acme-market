<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('MARKET')">
	<display:table name="requests" id="row" requestURI="request/market/list.do" pagesize="5">		
		<display:column titleKey="request.options">
			<jstl:if test="${row.status == 'PENDING'}">
				<a href="request/market/edit.do?requestId=${row.id}"><spring:message code="request.edit"/></a><br/>
				<a href="request/market/delete.do?requestId=${row.id}"><spring:message code="request.delete"/></a><br/>
			</jstl:if>			
		</display:column>		
		<display:column property="status" titleKey="request.status"  />
		<display:column property="rejectReason" titleKey="request.rejectReason"  />
		<display:column titleKey="request.department">
			<jstl:out value="${department.title}"/>
		</display:column>
		<display:column property="market.companyName" titleKey="request.market"  />
		<display:column property="provider.userAccount.username" titleKey="request.provider"  />
	</display:table>
</security:authorize>

<security:authorize access="hasRole('PROVIDER')">
	<display:table name="requests" id="row" requestURI="request/provider/list.do" pagesize="5">		
		<display:column titleKey="request.options">
			<jstl:if test="${row.status == 'PENDING'}">
				<a href="request/provider/edit.do?requestId=${row.id}"><spring:message code="request.decide"/></a><br/>
			</jstl:if>			
		</display:column>		
		<display:column property="status" titleKey="request.status"  />
		<display:column property="rejectReason" titleKey="request.rejectReason"  />
		<display:column titleKey="request.department">
			<jstl:out value="${department.title}"/>
		</display:column>
		<display:column property="market.companyName" titleKey="request.market"  />
		<display:column property="provider.userAccount.username" titleKey="request.provider"  />
	</display:table>
</security:authorize>
	

