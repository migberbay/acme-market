<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">

	<display:table name="contracts" id="row" requestURI="contract/customer/list.do" pagesize="5">		
	
		<jstl:if test="${row.customerMoment==null || row.managerMoment==null}">
			<jstl:set var="clase" value="background-color: red;"/>
		</jstl:if>
		<jstl:if test="${row.customerMoment!=null && row.managerMoment!=null}">
			<jstl:set var="clase" value="background-color: green;"/>
		</jstl:if>
		<display:column titleKey="contract.options" style="${clase}">
			<jstl:if test="${row.customerMoment==null && row.managerMoment!=null}">
				<a href="contract/customer/sign.do?contractId=${row.id}"><spring:message code="contract.sign"/></a><br/>
			</jstl:if>	
			<a href="contract/show.do?contractId=${row.id}"><spring:message code="contract.show"/></a><br/>		
		</display:column>		
		<spring:message code="contract.moment.format" var="formatMoment"/>
		<display:column titleKey="contract.customerMoment" property="customerMoment" format="{0,date,${formatMoment} }" style="${clase}"/>
		<display:column titleKey="contract.managerMoment" property="managerMoment" format="{0,date,${formatMoment} }" style="${clase}"/>
		<display:column titleKey="contract.isFinal" style="${clase}">
				<jstl:if test="${row.isFinal}"><spring:message code="contract.final"/></jstl:if>
				<jstl:if test="${!row.isFinal}"><spring:message code="contract.draft"/></jstl:if>
		</display:column>
	</display:table>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">

	<display:table name="contracts" id="row" requestURI="contract/manager/list.do" pagesize="5">		

		<display:column titleKey="contract.options">
			<a href="contract/show.do?contractId=${row.id}"><spring:message code="contract.show"/></a><br/>	
			<jstl:if test="${row.isFinal==false}">
				<a href="contract/manager/edit.do?contractId=${row.id}"><spring:message code="contract.edit"/></a><br/>
			</jstl:if>	
		</display:column>		
		<spring:message code="contract.moment.format" var="formatMoment"/>
		<display:column titleKey="contract.customerMoment" property="customerMoment" format="{0,date,${formatMoment} }" style="${clase}"/>
		<display:column titleKey="contract.managerMoment" property="managerMoment" format="{0,date,${formatMoment} }" style="${clase}"/>
		<display:column titleKey="contract.isFinal" style="${clase}">
				<jstl:if test="${row.isFinal}"><spring:message code="contract.final"/></jstl:if>
				<jstl:if test="${!row.isFinal}"><spring:message code="contract.draft"/></jstl:if>
		</display:column>
	</display:table>
</security:authorize>


