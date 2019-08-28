<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


	<display:table name="departments" id="row" requestURI="${requestURI}" pagesize="5">
		<security:authorize access="hasRole('MARKET')">
			<display:column titleKey="department.options">
				<a href="department/market/show.do?departmentId=${row.id}"><spring:message code="department.show"/></a><br/>
				<jstl:if test="${deps.contains(row)}">
					<a href="department/market/edit.do?departmentId=${row.id}"><spring:message code="department.edit"/></a><br/>
					<a href="department/market/delete.do?departmentId=${row.id}"><spring:message code="department.delete"/></a><br/>
				</jstl:if>		
			</display:column>
		</security:authorize>
		
		<display:column property="title" titleKey="department.name"/>
		<display:column property="discount" titleKey="department.price"/>

	</display:table>
	
	<a href="department/market/create.do"> <spring:message code="department.create" /> </a>

