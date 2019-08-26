<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- Recieves:
		RegisterForm;
 -->


	
<form:form action="actor/register.do" modelAttribute="registerForm" id="myform">
	<form:hidden path="type"/>	
	
	<h3><spring:message code="actor.userAcc"/>:</h3>
	<acme:textbox code="actor.username" path="username"/>
	<acme:password code="actor.password" path="password" />
	<acme:password code="actor.password2" path="password2" />

	
	<jstl:if test="${passMatch == false}">
		<div class="error">passwords do not match</div>
	</jstl:if><br />

	
	<h3><spring:message code="actor.personalData"/>:</h3>
	<acme:textbox code="actor.name" path="name"/>
	<acme:textbox code="actor.middleName" path="middleName"/>
	<acme:textbox code="actor.surname" path="surname"/>
	<acme:textbox code="actor.photo" path="photo"/>
	<acme:textbox code="actor.email" path="email"/>
	<acme:textbox code="actor.phone" path="phone"/>
	<acme:textbox code="actor.address" path="address"/>
	
	<jstl:choose>
		<jstl:when test="${registerForm.type == 'MARKET'}">
			<acme:textbox code="actor.VATNumber" path="VATNumber"/>
			<acme:textbox code="actor.CompanyName" path="CompanyName"/>
		</jstl:when>
		<jstl:otherwise>
			<input type="hidden" name="VATNumber" value="ESX12341234"/>
			<input type="hidden" name="CompanyName" value="DummyName">
		</jstl:otherwise>
	</jstl:choose>
	
	<jstl:choose>
		<jstl:when test="${registerForm.type == 'CUSTOMER'}">
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
		</jstl:when>
		<jstl:otherwise>
			<input type="hidden" name="expirationMonth" value="10"/>
			<input type="hidden" name="expirationYear" value="2099">
			<input type="hidden" name="number" value="1234567812345678"/>
			<input type="hidden" name="CVV" value="123">
			<input type="hidden" name="holder" value="DummyName"/>
			<input type="hidden" name="make" value="VISA">			
		</jstl:otherwise>
	</jstl:choose>
	
	<spring:message code="actor.warning"/>
	<a href="welcome/TOS.do"><spring:message code="actor.TOS"/></a>
	
	<br/>
	
	
	<button type="submit" name="save" class="btn btn-primary" onclick="checkNumber();">
		<spring:message code="actor.submit" />
	</button>
	
	<acme:cancel url="/" code="actor.cancel"/>


</form:form>
<script>
function checkNumber() {
	var CC = document.getElementById("countryCode").value;
	var AC = document.getElementById("areaCode").value;
	var EmptyAC = AC == "";
	var EmptyCC = CC == "";
	if(EmptyCC && !EmptyAC){
		return confirm("Are you sure you want to input that phone number?");
	}
}
</script>
