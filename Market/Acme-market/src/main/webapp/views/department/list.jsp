<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


	<display:table name="departments" id="row" requestURI="${requestURI}" pagesize="5">
		<display:column titleKey="department.options">
			<a href="product/list.do?departmentId=${row.id}"><spring:message code="department.product.list"/></a><br/>
			<security:authorize access="hasRole('MARKET')">
				<a href="department/market/show.do?departmentId=${row.id}"><spring:message code="department.show"/></a><br/>
				<a href="department/market/edit.do?departmentId=${row.id}"><spring:message code="department.edit"/></a><br/>
				<jstl:if test="${deps.contains(row)}">
					<a href="department/market/delete.do?departmentId=${row.id}"><spring:message code="department.delete"/></a><br/>
				</jstl:if>
			</security:authorize>		
		</display:column>
		
		<display:column property="title" titleKey="department.title"/>
		<display:column property="discount" titleKey="department.discount"/>

	</display:table>
	
	<a href="department/market/create.do"> <spring:message code="department.create" /> </a>

