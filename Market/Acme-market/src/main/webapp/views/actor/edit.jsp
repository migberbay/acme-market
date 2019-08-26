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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="actor/edit.do" modelAttribute="form">

	<jstl:if test="${showPersonal}">
	
	<acme:textbox code="actor.name" path="name"/>
	<acme:textbox code="actor.middleName" path="middleName"/>
	<acme:textbox code="actor.surname" path="surname"/>
	<acme:textbox code="actor.photo" path="photo"/>
	<acme:textbox code="actor.email" path="email"/>
	<acme:textbox code="actor.phone" path="phone"/>
	<acme:textbox code="actor.address" path="address"/>
	
	<jstl:choose>
		<jstl:when test="${isMarket}">
			<acme:textbox code="actor.VATNumber" path="VATNumber"/>
			<acme:textbox code="actor.CompanyName" path="CompanyName"/>
		</jstl:when>
		<jstl:otherwise>
			<input type="hidden" name="VATNumber" value="ESX12341234"/>
			<input type="hidden" name="CompanyName" value="DummyName">
		</jstl:otherwise>
	</jstl:choose>

	
	
	<acme:submit name="savePersonal" code="actor.save"/>
	
	</jstl:if>
	
	
	<jstl:if test="${showCredit}">
	
	<spring:message code="actor.card"/>:<br/>
	<acme:textbox code="actor.holder" path="holder"/> <br />
	
	<form:label path="make">
		<spring:message code="actor.make" />
	</form:label>	
	<form:select path="make" >
		<form:option value="MASTER CARD" label="MASTER CARD" />
		<form:option value="VISA" label="VISA" />
		<form:option value="AMERICAN EXPRESS" label="AMERICAN EXPRESS" />	
		<form:option value="DINERS CLUB" label="DINERS CLUB" />	
	</form:select>
	<form:errors path="make" cssClass="error" /><br>
	
	<acme:textbox code="actor.number" path="number"/><br/>
	<acme:textbox code="actor.CVV" path="CVV"/>	<br/>
	
	<form:label path="expirationMonth">
		<spring:message code="actor.expirationMonth" />
	</form:label>
	<form:select path="expirationMonth" items="${months}"/>
	<form:errors path="expirationMonth" cssClass="error" />
	
	<br/>
	
	<form:label path="expirationYear">
		<spring:message code="actor.expirationYear" />
	</form:label>
	<form:select path="expirationYear" items="${years}"/>
	<form:errors path="expirationYear" cssClass="error" />
	
	<br/>
	
	<jstl:if test="${isExpired == true}">
		<div class="error">this is and expired date</div>
	</jstl:if><br />
	
	<acme:submit name="saveCredit" code="actor.save"/>
	</jstl:if>
	
	<acme:cancel url="actor/show.do" code="actor.cancel"/>
	
</form:form>
