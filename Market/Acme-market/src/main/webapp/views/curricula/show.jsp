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

<security:authorize access="hasRole('PROVIDER')">
	<jstl:if test="${newcurricula}">
		<a href="curricula/provider/create.do"><spring:message code="curricula.create"/></a>	
	</jstl:if>
	<jstl:if test="${!newcurricula}">
			<h3><spring:message code="curricula.personalRecord"/></h3>
		<b><spring:message code="curricula.personalRecord.fullName"/>:</b> <jstl:out value="${personal.fullName}"/><br>
		<b><spring:message code="curricula.personalRecord.photo"/>:</b> <jstl:out value="${personal.photo }"/><br>
		<b><spring:message code="curricula.personalRecord.email"/>:</b> <jstl:out value="${personal.email }"/><br>
		<b><spring:message code="curricula.personalRecord.phone"/>:</b> <jstl:out value="${personal.phone }"/><br>
		<b><spring:message code="curricula.personalRecord.linkedInProfile"/>:</b> <jstl:out value="${personal.linkedInUrl}"/><br>
		<a href="personalRecord/provider/edit.do?personalRecordId=${personal.id}"><spring:message code="curricula.edit"/></a>
		
		
		<h3><spring:message code="curricula.educationRecords"/></h3>
		<display:table name="educations" id="row" requestURI="curricula/provider/show.do" pagesize="5">
			<display:column titleKey="curricula.options">
				<a href="educationRecord/provider/edit.do?educationRecordId=${row.id}"><spring:message code="curricula.edit"/></a><br/>
				<a href="educationRecord/provider/delete.do?educationRecordId=${row.id}"><spring:message code="curricula.delete"/></a><br/>
			</display:column>		
		<display:column titleKey="curricula.educationRecord.diplomaTitle" property="diplomaTitle"/>
		<spring:message code="curricula.moment.format" var="formatMoment"/>
		<display:column titleKey="curricula.startDate" property="startDate" format="{0,date,${formatMoment} }"/>
		<display:column titleKey="curricula.endDate" property="endDate" format="{0,date,${formatMoment} }"/>	
		<display:column titleKey="curricula.educationRecord.institution" property="institution"/>
		<display:column titleKey="curricula.attachment" property="attachment"/>
		<display:column titleKey="curricula.comments" property="comments"/>
	</display:table>
			<a href="educationRecord/provider/create.do"><spring:message code="curricula.create"/></a>
	
		
		<h3><spring:message code="curricula.professionalRecords"/></h3>
		<display:table name="professionals" id="row" requestURI="curricula/provider/show.do" pagesize="5">
			<display:column titleKey="curricula.options">
				<a href="professionalRecord/provider/edit.do?professionalRecordId=${row.id}"><spring:message code="curricula.edit"/></a><br/>
				<a href="professionalRecord/provider/delete.do?professionalRecordId=${row.id}"><spring:message code="curricula.delete"/></a><br/>
			</display:column>		
		<display:column titleKey="curricula.professionalRecord.companyName" property="companyName"/>
		<spring:message code="curricula.moment.format" var="formatMoment"/>
		<display:column titleKey="curricula.startDate" property="startDate" format="{0,date,${formatMoment} }"/>
		<display:column titleKey="curricula.endDate" property="endDate" format="{0,date,${formatMoment} }"/>	
		<display:column titleKey="curricula.professionalRecord.role" property="role"/>
		<display:column titleKey="curricula.attachment" property="attachment"/>
		<display:column titleKey="curricula.comments" property="comments"/>
	</display:table>
	<a href="professionalRecord/provider/create.do"><spring:message code="curricula.create"/></a>
	
	</jstl:if>
</security:authorize>