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

<!-- 
	Recieves: 
		List<Box> boxes - los boxes de un actor.		
-->

<security:authorize access="isAuthenticated()">
	
	<a href="message/create.do"><spring:message code='m.create' /></a>
	
	<jstl:forEach items="${boxes}" var="x">
	<button class="collapsible"><jstl:out value="${x.name}"/></button>
	<div class="content">
	  <table border='1' style="width:100%">
		<jstl:forEach items="${x.messages}" var="y">
			<tr><td>
					<spring:message code='m.sender' /><jstl:out value="${y.sender.username}" /> <br>
					<spring:message code='m.subject' /><jstl:out value="${y.subject}" />
				</td></tr>
				
				<tr><td>
					<jstl:out value="${y.body}" />
				</td></tr>
				
				<tr><td>
					<a href="message/delete.do?messageId=${y.id}"><spring:message code='m.delete' /></a>
				</td></tr>
		</jstl:forEach>
	</table>
	</div>
	</jstl:forEach>
	
<script>
	var coll = document.getElementsByClassName("collapsible");
	var i;
	
	for (i = 0; i < coll.length; i++) {
	  coll[i].addEventListener("click", function() {
	    this.classList.toggle("active");
	    var content = this.nextElementSibling;
	    if (content.style.maxHeight){
	      content.style.maxHeight = null;
	    } else {
	      content.style.maxHeight = content.scrollHeight + "px";
	    } 
	  });
	}
</script>
	
</security:authorize>