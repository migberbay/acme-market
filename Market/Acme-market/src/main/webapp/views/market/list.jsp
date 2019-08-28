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
			<a href = "purchase/customer/create.do?marketId=${row.id}">Open a Purchase</a>
		</display:column>
	</jstl:if>
		
		<display:column titleKey="market.products">
			<a href = "product/list.do?marketId=${row.id}">Show</a>
		</display:column>
		<display:column property="companyName" titleKey="market.companyName" />
	
	</display:table>

