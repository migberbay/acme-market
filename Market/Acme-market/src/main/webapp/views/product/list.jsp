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


	<display:table name="products" id="row" requestURI="product/list.do" pagesize="5">
		<display:column titleKey="product.provider">
			<a href = "actor/show.do=actorId${row.provider.id}"><jstl:out value="${row.provider.userAccount.username}"></jstl:out></a>
		</display:column>
		<display:column property="name" titleKey="product.name" />
		<display:column property="price" titleKey="product.price" />
		<display:column property="stock" titleKey="product.stock" />
	
	</display:table>

