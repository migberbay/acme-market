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


	<display:table name="markets" id="row" requestURI="market/list.do" pagesize="5">
	
	<jstl:if test="${isCustomer}">
		<display:column titleKey="purchase.action">
			<jstl:if test="${!customer.markets.contains(row)}">
				<br/><a href="customer/market/join.do?marketId=${row.id}"><spring:message code="market.join"/></a>
			</jstl:if>
			<jstl:if test="${customer.markets.contains(row)}">
				<a href = "purchase/customer/create.do?marketId=${row.id}"><spring:message code="market.purchase.open"/></a>
				<br/><a href = "customer/market/leave.do?marketId=${row.id}"><spring:message code="market.leave"/></a>
			</jstl:if>
		</display:column>
	</jstl:if>
		
		<display:column titleKey="market.products">
			<a href = "department/list.do?marketId=${row.id}"><spring:message code="market.department.list"/></a>
		</display:column>
		<display:column property="companyName" titleKey="market.companyName" />
	
	</display:table>

