<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


	<display:table name="products" id="row" requestURI="${requestURI}" pagesize="5">
			<display:column titleKey="product.options">
				<security:authorize access="hasRole('PROVIDER')">
					<a href="product/provider/show.do?productId=${row.id}"><spring:message code="product.show"/></a><br/>
				<jstl:if test="${row.department==null}">
					<a href="product/provider/edit.do?productId=${row.id}"><spring:message code="product.edit"/></a><br/>
					<a href="product/provider/delete.do?productId=${row.id}"><spring:message code="product.delete"/></a><br/>
				</jstl:if>		
				</security:authorize>
				<security:authorize access="hasRole('MARKET')">
					<a href="request/market/create.do?productId=${row.id}"><spring:message code="product.request"/></a><br/>		
				</security:authorize>
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
	<security:authorize access="hasRole('PROVIDER')">
	<div>
	<a href="product/provider/create.do"> <spring:message code="product.create" /> </a>
	</div>
	</security:authorize>

