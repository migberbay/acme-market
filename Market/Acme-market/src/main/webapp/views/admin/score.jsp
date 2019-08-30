<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${showProduct}">

<display:table name="productByScore" id="row" pagesize="5" requestURI="/system/admin/computeProducts.do">
	<display:column titleKey="actor.name"><jstl:out value="${row.getKey().name}"/></display:column>
	<display:column titleKey="actor.provider"><a href="actor/show.do?actorId=${row.getKey().provider.id}"></a><jstl:out value="${row.getKey().provider.userAccount.username}"/></display:column>
	<display:column titleKey="comment.score"><jstl:out value="${row.getValue()}"/></display:column>
</display:table>

</jstl:if>



<jstl:if test="${showDelivery}">

<display:table name="deliveryBoysByScore" id="row" pagesize="5" requestURI="system/admin/computeDeliveryBoys.do">
	<display:column titleKey="actor.name"><jstl:out value="${row.getKey().name}"/></display:column>
	<display:column titleKey="actor.deliveryBoy"><a href="actor/show.do?actorId=${row.getKey().id}"></a><jstl:out value="${row.getKey().userAccount.username}"/></display:column>
	<display:column titleKey="comment.score"><jstl:out value="${row.getValue()}"/></display:column>
</display:table>


</jstl:if>


