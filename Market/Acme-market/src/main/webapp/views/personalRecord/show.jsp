<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
	    
	    <div>		 
			<spring:message code="contract.moment.format" var="formatMoment"/>
		    <b><spring:message code="contract.customerMoment"/></b>: 
		    <jstl:if test="${contract.customerMoment==null}">PENDING<br/></jstl:if>
		     <jstl:if test="${contract.customerMoment!=null}">
		     	<fmt:formatDate value="${contract.customerMoment}" type="date" pattern="${formatMoment}"/> <br/> 
		     </jstl:if>
		      <b><spring:message code="contract.managerMoment"/></b>: 
		     <jstl:if test="${contract.managerMoment==null}">PENDING<br/></jstl:if>
		     <jstl:if test="${contract.managerMoment!=null}">
		     	<fmt:formatDate value="${contract.managerMoment}" type="date" pattern="${formatMoment}"/> <br/> 
		     </jstl:if>
		     
		    <b><spring:message code="contract.text" /></b>: <br/><jstl:out value="${contract.text}" /> <br/>
		    <b><spring:message code="contract.hash" /></b>: <jstl:out value="${contract.hash}" /> <br/>
		</div>
		<br/>
		
	<h3><spring:message code="file.billboardFiles"/></h3>
	<display:table name="contract.billboardFiles" id="row" requestURI="contract/manager/show.do" pagesize="5">		

		<jstl:if test="${logged and row.contract.isFinal==false}">
		<display:column titleKey="file.options">
			<a href="billboardFile/manager/edit.do?billboardFileId=${row.id}"><spring:message code="file.edit"/></a><br/>
			<a href="billboardFile/manager/delete.do?billboardFileId=${row.id}"><spring:message code="file.delete"/></a><br/>
		</display:column>	
		</jstl:if>	
		<display:column titleKey="file.location" property="location"/>
		<display:column titleKey="file.image">
			<a href="${row.image}"><jstl:out value="${row.image}" /></a>
		</display:column>
	</display:table>
	<jstl:if test="${logged and contract.isFinal==false}">
		<a href="billboardFile/manager/create.do?contractId=${contract.id}"><spring:message code="file.billboard.create"/></a>
	</jstl:if>
	
	<h3><spring:message code="file.radioFiles"/></h3>
	<display:table name="contract.radioFiles" id="row" requestURI="contract/manager/show.do" pagesize="5">		
		<jstl:if test="${logged and row.contract.isFinal==false}">
		<display:column titleKey="file.options">
			<a href="radioFile/manager/edit.do?radioFileId=${row.id}"><spring:message code="file.edit"/></a><br/>
			<a href="radioFile/manager/delete.do?radioFileId=${row.id}"><spring:message code="file.delete"/></a><br/>
		</display:column>	
		</jstl:if>	
		<display:column titleKey="file.sound" property="sound"/>
		<display:column titleKey="file.broadcaster" property="broadcaster"/>
		<display:column titleKey="file.schedule" property="schedule"/>
	</display:table>
	<jstl:if test="${logged and contract.isFinal==false}">
		<a href="radioFile/manager/create.do?contractId=${contract.id}"><spring:message code="file.radio.create"/></a>
	</jstl:if>
	
	<h3><spring:message code="file.tvFiles"/></h3>
	<display:table name="contract.tvFiles" id="row" requestURI="contract/manager/show.do" pagesize="5">		
		<jstl:if test="${logged and row.contract.isFinal==false}">
		<display:column titleKey="file.options">
			<a href="tvFile/manager/edit.do?tvFileId=${row.id}"><spring:message code="file.edit"/></a><br/>
			<a href="tvFile/manager/delete.do?tvFileId=${row.id}"><spring:message code="file.delete"/></a><br/>
		</display:column>	
		</jstl:if>	
		<display:column titleKey="file.video" property="video"/>
		<display:column titleKey="file.broadcaster" property="broadcaster"/>
		<display:column titleKey="file.schedule" property="schedule"/>
	</display:table>
	<jstl:if test="${logged and contract.isFinal==false}">
		<a href="tvFile/manager/create.do?contractId=${contract.id}"><spring:message code="file.tv.create"/></a>
	</jstl:if>
	
	<h3><spring:message code="file.socialNetworkFiles"/></h3>
	<display:table name="contract.socialNetworkFiles" id="row" requestURI="contract/manager/show.do" pagesize="5">		
		<jstl:if test="${logged and row.contract.isFinal==false}">
		<display:column titleKey="file.options">
			<a href="socialNetworkFile/manager/edit.do?socialNetworkFileId=${row.id}"><spring:message code="file.edit"/></a><br/>
			<a href="socialNetworkFile/manager/delete.do?socialNetworkFileId=${row.id}"><spring:message code="file.delete"/></a><br/>
		</display:column>	
		</jstl:if>	
		<display:column titleKey="file.banner">
			<a href="${row.banner}"><jstl:out value="${row.banner}" /></a>
		</display:column>
		<display:column titleKey="file.target">
			<a href="${row.target}"><jstl:out value="${row.target}" /></a>
		</display:column>
	</display:table>
		<jstl:if test="${logged and contract.isFinal==false}">
		<a href="socialNetworkFile/manager/create.do?contractId=${contract.id}"><spring:message code="file.social.create"/></a>
	</jstl:if>
		
	<h3><spring:message code="file.infoFiles"/></h3>
	<display:table name="contract.infoFiles" id="row" requestURI="contract/manager/show.do" pagesize="5">		
		<jstl:if test="${logged and row.contract.isFinal==false}">
		<display:column titleKey="file.options">
			<a href="infoFile/manager/edit.do?infoFileId=${row.id}"><spring:message code="file.edit"/></a><br/>
			<a href="infoFile/manager/delete.do?infoFileId=${row.id}"><spring:message code="file.delete"/></a><br/>
		</display:column>	
		</jstl:if>	
		<display:column titleKey="file.title" property="title"/>
		<display:column titleKey="file.text" property="text"/>
	</display:table>
	<jstl:if test="${logged and contract.isFinal==false}">
		<a href="infoFile/manager/create.do?contractId=${contract.id}"><spring:message code="file.info.create"/></a>
	</jstl:if>
	<br/>
	<acme:cancel url="${uri}" code="contract.back"/>
		
