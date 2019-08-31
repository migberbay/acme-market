<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- PUBLIC DATA -->
	<div>		
		<b><spring:message code="actor.username"/></b>: <jstl:out value="${actor.userAccount.username}"/> <br/>	
		<b><spring:message code="actor.name"/></b>: <jstl:out value="${actor.name}"/> <br/>
		<b><spring:message code="actor.middleName"/></b>: <jstl:out value="${actor.middleName}"/> <br/>
		<b><spring:message code="actor.surname"/></b>: <jstl:out value="${actor.surname}"/> <br/>
		<b><spring:message code="actor.photo"/></b>: <jstl:out value="${actor.photo}"/> <br/>				 				 
		<b><spring:message code="actor.email"/></b>: <jstl:out value="${actor.email}"/> <br/>			 				 
		<b><spring:message code="actor.phone"/></b>: <jstl:out value="${actor.phone}"/> <br/>
		<b><spring:message code="actor.address"/></b>: <jstl:out value="${actor.address}"/> <br/>
		<jstl:if test="${actorIsMarket}">
			<b><spring:message code="actor.VATNumber"/></b> <jstl:out value="${actor.VATNumber}"/> <br/>
			<b><spring:message code="actor.CompanyName"/></b>: <jstl:out value="${actor.companyName}"/> <br/>
		</jstl:if>
		<jstl:if test="${logged}">
			<b><a href="actor/editPersonal.do"><spring:message code="actor.edit" /></a> Personal Data</b> <br/>
		</jstl:if>
		<br>
	
	<jstl:if test="${actorIsDeliveryBoy}">
	COMMENTS:<br>
		<display:table name="actor.comments" id="row" requestURI="actor/show.do" pagesize="5">
		<display:column titleKey="actor.customer">
			<a href="actor/show.do?actorId=${row.customer.id}"><jstl:out value="${row.customer.userAccount.username}"/></a><br/>	
		</display:column>
		
		<display:column property="text" titleKey="comment.text"/>
		<display:column property="score" titleKey="comment.score"/>

	</display:table>
	</jstl:if>

<!-- PRIVATE DATA -->		
	<jstl:if test="${logged}">
		
		<jstl:if test="${actorIsProvider}">
			<jstl:choose>
			<jstl:when test="${curricula == null}">
				<a href="curricula/provider/create.do">Create a curricula</a>
			</jstl:when>
			<jstl:otherwise>
				<a href="curricula/provider/show.do">Show curricula</a>
			</jstl:otherwise>
			</jstl:choose>
		</jstl:if>
		
		
		<jstl:if test="${actorIsCustomer}">
			<h3>Credit Card:</h3>
			<b><spring:message code="actor.holder"/></b>: <jstl:out value="${actor.creditCard.holder}"/> <br/>
			<b><spring:message code="actor.make"/></b>: <jstl:out value="${actor.creditCard.make}"/> <br/>
			<b><spring:message code="actor.number"/></b>: <jstl:out value="${actor.creditCard.number}"/> <br/>
			<b><spring:message code="actor.CVV"/></b>: <jstl:out value="${actor.creditCard.CVV}"/> <br/>
			<b><spring:message code="actor.expiration"/></b>: <jstl:out value="${actor.creditCard.expirationDate.getMonth()+1}"/> / <jstl:out value="${actor.creditCard.expirationDate.getYear()+1900}"/> <br/>
  					
  			<b><a href="actor/editCreditCard.do"><spring:message code="actor.edit" /></a> CreditCard Data</b> <br/>
		</jstl:if>
	</jstl:if>
	</div>
	<br/>
		
	<jstl:if test="${logged}">
			<br>
			<a href="actor/delete.do" onclick="confirmLeave();">delete ALL data</a><br>
			<a href="actor/generateData.do"> <spring:message code="actor.generate" /> </a> <br>
	</jstl:if>
		
		<input type="button" name="back"
		value="<spring:message code="actor.show.back" />"
		onclick="javascript: window.location.replace('')" />
	<br/>
	
<script>
	function confirmLeave() {
	if(!confirm("Are you sure about this? this operation is not reversible and will delete all your data...")) return;
	}
</script>