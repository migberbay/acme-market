<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


	<display:table name="products" id="row" requestURI="product/provider/list.do" pagesize="5">
		<security:authorize access="hasRole('PROVIDER')">
			<display:column titleKey="product.options">
				<a href="product/provider/show.do?productId=${row.id}"><spring:message code="product.show"/></a><br/>
				<security:authorize access="hasRole('PROVIDER')">
				<jstl:if test="${row.department==null}">
					<a href="product/provider/edit.do?productId=${row.id}"><spring:message code="product.edit"/></a><br/>
					<a href="product/provider/delete.do?productId=${row.id}"><spring:message code="product.delete"/></a><br/>
				</jstl:if>		
				</security:authorize>
				<security:authorize access="hasRole('MARKET')">
					<a href="request/market/create.do?productId=${row.id}"><spring:message code="product.request"/></a><br/>		
				</security:authorize>
			</display:column>
		</security:authorize>
		
		<display:column property="name" titleKey="product.name"/>
		<display:column property="price" titleKey="product.price"/>
		<display:column property="stock" titleKey="product.stock"/>
	</display:table>
	<security:authorize access="hasRole('PROVIDER')">
	<div>
	<a href="product/provider/create.do"> <spring:message code="product.create" /> </a>
	</div>
	</security:authorize>

