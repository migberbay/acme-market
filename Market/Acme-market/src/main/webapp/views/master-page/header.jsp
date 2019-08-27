<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<a href="">
<div class="page-header" style="background: url(${banner}) center no-repeat; background-size: cover">
</div>
</a>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.system" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="system/admin/configuration.do"><spring:message code="master.page.configuration" /></a></li>
					<li><a href="admin/dashboard.do"><spring:message code="master.page.dashboard" /></a></li>
					<li><a href="message/createBroadcast.do"><spring:message code="master.page.broadcast" /></a></li>				
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/register.do?type=ADMIN"><spring:message code="master.page.register.admin" /></a></li>
					<li><a href="actor/register.do?type=MARKET"><spring:message code="master.page.register.market" /></a></li>	
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/action-1.do"><spring:message code="master.page.customer.action.1" /></a></li>
					<li><a href="customer/action-2.do"><spring:message code="master.page.customer.action.2" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('PROVIDER')">
			<li><a class="fNiv" href="curricula/provider/show.do"><spring:message code="master.page.curricula" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.product" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="product/provider/list.do"><spring:message code="master.page.product.list" /></a></li>
					<li><a href="product/provider/create.do"><spring:message code="master.page.product.create" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="market/list.do"><spring:message code="master.page.market.list" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/register.do?type=CUSTOMER"><spring:message code="master.page.register.customer" /></a></li>
					<li><a href="actor/register.do?type=PROVIDER"><spring:message code="master.page.register.provider" /></a></li>	
					<li><a href="actor/register.do?type=DELIVERYBOY"><spring:message code="master.page.register.deliveryBoy" /></a></li>				
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="box/list.do"><spring:message code="master.page.mailbox" /></a></li>
					<li><a href="actor/show.do"><spring:message code="master.page.profile" /></a></li>		
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>	
					
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

