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

		<h3><spring:message code="curricula.personalRecord"/></h3>
		<h4><a href="personalRecord/provider/edit.do?personalRecordId=${personalRecord.id}"><spring:message code="curricula.edit"/></a></h4>
		<spring:message code="curricula.personalRecord.fullName"/>: <jstl:out value="${personalRecord.fullName}"/><br>
		<spring:message code="curricula.personalRecord.photo"/>: <jstl:out value="${personalRecord.photo }"/><br>
		<spring:message code="curricula.personalRecord.email"/>: <jstl:out value="${personalRecord.email }"/><br>
		<spring:message code="curricula.personalRecord.phone"/>: <jstl:out value="${personalRecord.phone }"/><br>
		<spring:message code="curricula.personalRecord.linkedInProfile"/>: <jstl:out value="${personalRecord.linkedInUrl}"/><br>
		<hr>
		
		<h3><spring:message code="curricula.educationRecords"/></h3>
		<h4><a href="educationRecord/provider/create.do"><spring:message code="curricula.create"/></a></h4><hr>

		<display:table name="educationRecords" id="row" requestURI="curricula/provider/show.do" pagesize="5">
			<display:column class="${clase}">
			<a href="package/manager/show.do?packageId=${row.id}"><spring:message code="package.show"/></a><br/>
			<jstl:if test="${row.isFinal == false}">
				<a href="package/manager/edit.do?packageId=${row.id}"><spring:message code="package.edit"/></a><br/>
				<a href="package/manager/delete.do?packageId=${row.id}"><spring:message code="package.delete"/></a><br/>
			</jstl:if>		
			</display:column>		
		<display:column property="ticker" titleKey="package.ticker"/>
		<display:column property="title" titleKey="package.title"/>
		<spring:message code="package.moment.format" var="formatMoment"/>
		<display:column titleKey="package.startDate" property="startDate" format="{0,date,${formatMoment} }"/>
		<display:column titleKey="package.endDate" property="endDate" format="{0,date,${formatMoment} }"/>
		<display:column property="price" titleKey="package.price" class="${clase}"/>

	</display:table>
		
		<jstl:forEach var="i" items="${educationRecords }">
		<h4><a href="curricula/handyworker/editEducationRecord.do?educationRecordId=${i.id}"><spring:message code="curricula.editEducationRecord"/></a></h4>
		<h4><a href="curricula/handyworker/deleteEducationRecord.do?educationRecordId=${i.id}"><spring:message code="curricula.deleteEducationRecord"/></a></h4>
		<spring:message code="curricula.diplomaTitle"/>: <jstl:out value="${i.diplomaTitle }"/><br>
		<spring:message code="curricula.startDate"/>: <jstl:out value="${i.startDate }"/><br>
		<spring:message code="curricula.endDate"/>: <jstl:out value="${i.endDate }"/><br>
		<spring:message code="curricula.institution"/>: <jstl:out value="${i.institution }"/><br>
		<spring:message code="curricula.attachment"/>: <jstl:out value="${i.attachment }"/><br>
		<spring:message code="curricula.comments"/>: <jstl:out value="${i.comments }"/><br>
		<hr>
		</jstl:forEach>
		
		<h3><spring:message code="curricula.professionalRecords"/></h3>
		<h4><a href="curricula/handyworker/createProfessionalRecord.do"><spring:message code="curricula.createProfessionalRecord"/></a></h4><hr>
		<jstl:forEach var="i" items="${curricula.professionalRecords }">
		<h4><a href="curricula/handyworker/editProfessionalRecord.do?professionalRecordId=${i.id}"><spring:message code="curricula.editProfessionalRecord"/></a></h4>
		<h4><a href="curricula/handyworker/deleteProfessionalRecord.do?professionalRecordId=${i.id}"><spring:message code="curricula.deleteProfessionalRecord"/></a></h4>
		<spring:message code="curricula.companyName"/>: <jstl:out value="${i.companyName}"/><br>
		<spring:message code="curricula.startDate"/>: <jstl:out value="${i.startDate}"/><br>
		<spring:message code="curricula.endDate"/>: <jstl:out value="${i.endDate }"/><br>
		<spring:message code="curricula.role"/>: <jstl:out value="${i.role }"/><br>
		<spring:message code="curricula.attachment"/>: <jstl:out value="${i.attachment }"/><br>
		<spring:message code="curricula.comments"/>: <jstl:out value="${i.comments }"/><br>
		<hr>
		</jstl:forEach>
		
		<h3><spring:message code="curricula.miscellaneousRecords"/></h3>
		<h4><a href="curricula/handyworker/createMiscellaneousRecord.do"><spring:message code="curricula.createMiscellaneousRecord"/></a></h4>
		<jstl:forEach var="i" items="${curricula.miscellaneousRecords }">
		<h4><a href="curricula/handyworker/editMiscellaneousRecord.do?miscellaneousRecordId=${i.id}"><spring:message code="curricula.editMiscellaneousRecord"/></a></h4>
		<h4><a href="curricula/handyworker/deleteMiscellaneousRecord.do?miscellaneousRecordId=${i.id}"><spring:message code="curricula.deleteMiscellaneousRecord"/></a></h4>
		<spring:message code="curricula.title"/>: <jstl:out value="${i.title }"/><br>
		<spring:message code="curricula.attachment"/>: <jstl:out value="${i.attachment }"/><br>
		<spring:message code="curricula.comments"/>: <jstl:out value="${i.comments }"/><br>
		<hr>
		</jstl:forEach>
			
</security:authorize>