<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Este list lo usan solo los providers (para mostrar sus productos) y los markets para pedir productos sin asignar. -->

	<display:table name="products" id="row" requestURI="${requestURI}" pagesize="5">
			<display:column titleKey="product.options">
			
			<a href="product/show.do?productId=${row.getKey().id}"><spring:message code="product.show"/></a><br/>
				<security:authorize access="hasRole('PROVIDER')">
				<jstl:if test="${row.getKey().department==null}">
					<a href="product/provider/edit.do?productId=${row.getKey().id}"><spring:message code="product.edit"/></a><br/>
					<!-- edits all the unassigned ones -->
					<a href="product/provider/delete.do?productId=${row.getKey().id}"><spring:message code="product.delete"/></a><br/>
					<!-- deletes all the unassigned ones -->
				</jstl:if>		
				<a href="product/provider/addStock.do?productId=${row.getKey().id}"><spring:message code="product.addStock"/></a><br/>
				</security:authorize>
				
				<security:authorize access="hasRole('MARKET')">
					<a href="request/market/create.do?productId=${row.getKey().id}"><spring:message code="product.request"/></a><br/>		
				</security:authorize>
			</display:column>
		
		<display:column titleKey="product.name">
			<jstl:out value="${row.getKey().name}"/> x <jstl:out value="${row.getKey().stock}"/>
		</display:column>
		<display:column titleKey="product.stock">
			<jstl:out value="${row.getValue()}"/>
		</display:column>
		<display:column titleKey="product.price">
			<jstl:out value="${row.getKey().price}"/>
		</display:column>
		
		<jstl:forEach items="${departments}" var="x">
			<jstl:if test="${row.getKey().department.id==x.id}">
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

