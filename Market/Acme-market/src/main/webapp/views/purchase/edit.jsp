<%--
 * edit.jsp
 *
 * Copyright (C) 2015 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<div id="mydiv">
  <div id="mydivheader">Click here to move</div>
 	Avaliable products:<br>
	<jstl:forEach items="${products}" var="x">
		<spring:message code="purchase.product.name"/>: <jstl:out value="${x.name}"/> ,
		<spring:message code="purchase.product.price"/>: <jstl:out value="${x.price}"/> ,
		<spring:message code="purchase.product.stock"/>: <jstl:out value="${x.stock}"/> 
	 	<input type="button" onclick="location.href='purchase/customer/addProduct.do?purchaseId=${purchase.id}&productId=${x.id}';" value="Add" /> <br> 
	</jstl:forEach>
</div>


  <spring:message code="purchase.Cart"/>:<input type="button" onclick="location.href='purchase/customer/finalizePurchase.do?purchaseId=${purchase.id}';" value="Order!" /> <br>
	<spring:message code="purchase.total"/>: <jstl:out value="${purchase.totalPrice}"/><br>
	<jstl:forEach items="${purchase.products}" var="x">
		<spring:message code="purchase.product.name"/>: <jstl:out value="${x.name}"/> ,
		<spring:message code="purchase.product.price"/>: <jstl:out value="${x.price}"/>
		<input type="button" onclick="location.href='purchase/customer/removeProduct.do?purchaseId=${purchase.id}&productId=${x.id}';" value="Remove" /> <br>
	</jstl:forEach>
	<br>



<script>
//Make the DIV element draggable:
dragElement(document.getElementById("mydiv"));

function dragElement(elmnt) {
  var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
  if (document.getElementById(elmnt.id + "header")) {
    // if present, the header is where you move the DIV from:
    document.getElementById(elmnt.id + "header").onmousedown = dragMouseDown;
  } else {
    // otherwise, move the DIV from anywhere inside the DIV: 
    elmnt.onmousedown = dragMouseDown;
  }

  function dragMouseDown(e) {
    e = e || window.event;
    e.preventDefault();
    // get the mouse cursor position at startup:
    pos3 = e.clientX;
    pos4 = e.clientY;
    document.onmouseup = closeDragElement;
    // call a function whenever the cursor moves:
    document.onmousemove = elementDrag;
  }

  function elementDrag(e) {
    e = e || window.event;
    e.preventDefault();
    // calculate the new cursor position:
    pos1 = pos3 - e.clientX;
    pos2 = pos4 - e.clientY;
    pos3 = e.clientX;
    pos4 = e.clientY;
    // set the element's new position:
    elmnt.style.top = (elmnt.offsetTop - pos2) + "px";
    elmnt.style.left = (elmnt.offsetLeft - pos1) + "px";
  }

  function closeDragElement() {
    // stop moving when mouse button is released:
    document.onmouseup = null;
    document.onmousemove = null;
  }
}

</script>








