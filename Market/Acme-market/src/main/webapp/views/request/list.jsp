<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">
	<display:table name="requests" id="row" requestURI="request/customer/list.do" pagesize="5">		
		<display:column titleKey="request.options" class="${row.status}">
			<jstl:if test="${row.status == 'APPROVED' && row.contract.isFinal==true}">
				<a href="contract/show.do?contractId=${row.contract.id}"><spring:message code="request.contract"/></a><br/>
			</jstl:if>			
		</display:column>		
		<display:column property="status" titleKey="request.status" class="${row.status}"/>
		<display:column property="customerComments" titleKey="request.customerComments" class="${row.status}"/>
		<display:column property="managerComments" titleKey="request.managerComments" class="${row.status}"/>
		<display:column titleKey="request.package" class="${row.status}">
			<a href="package/show.do?packageId=${row.package1.id}"><jstl:out value="${row.package1.title}"/></a>
		</display:column>
	</display:table>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
	<display:table name="requests" id="row" requestURI="request/manager/list.do" pagesize="5">
		<display:column titleKey="request.options" class="${clase}">
			<jstl:if test="${row.status == 'PENDING'}">
				<a href="request/manager/edit.do?requestId=${row.id}"><spring:message code="request.decide"/></a><br/>
			</jstl:if>
			<jstl:if test="${row.status == 'APPROVED'}">
				<a href="contract/show.do?contractId=${row.contract.id}"><spring:message code="request.contract"/></a><br/>
			</jstl:if>		
		</display:column>	
		<display:column property="status" titleKey="request.status" class="${clase}"/>
		<display:column property="customerComments" titleKey="request.customerComments" class="${clase}"/>
		<display:column property="managerComments" titleKey="request.managerComments" class="${clase}"/>
		<display:column titleKey="request.package">
			<a href="package/show.do?packageId=${row.package1.id}"><jstl:out value="${row.package1.title}"/></a>
		</display:column>
		<display:column titleKey="request.customer">
			<a href="actor/show.do?actorId=${row.customer.id}"><jstl:out value="${row.customer.userAccount.username}"/></a>
		</display:column>
	</display:table>
</security:authorize>
	

