<%--
 * 
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jstl:choose>
<jstl:when test="${forum.course.isClosed == false}"> 

<spring:message code="forum.questions" var="questions"/> 
<h3> <jstl:out value="${questions}"> </jstl:out> </h3>


<jstl:choose>
<jstl:when test="${not empty forum.questions}"> 

<display:table name="questions" id="row" requestURI="forum/display.do" class="displaytag">

	<security:authorize access="hasRole('ADMIN')">
		<spring:message code="question.confirm" var="confirmQuestion"  />
		<display:column>
				<a href="question/admin/delete.do?questionId=${row.id}" onclick="return confirm('${confirmQuestion}')"><spring:message code ="question.delete" /></a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('STUDENT')">
		<spring:message code="question.confirm" var="confirmQuestion"  />
		<display:column>
				<a href="question/student/delete.do?questionId=${row.id}" onclick="return confirm('${confirmQuestion}')"><spring:message code ="question.delete" /></a>
		</display:column>
	</security:authorize>
	
	<spring:message code="question.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" >
	<jstl:choose>
				<jstl:when test="${not empty row.answers}">
					<a href="question/display.do?articleId=${row.id}">
						<jstl:out value="${row.title}"/>
					</a>
				</jstl:when>
			<jstl:otherwise>
			<jstl:out value="${row.title}"/>
			</jstl:otherwise>
			</jstl:choose>
	</display:column>
	
	<spring:message code="question.pictureError" var="pictureError" />
	
	<spring:message code="question.question" var="questionHeader" />
	<display:column property="question" title="${questionHeader}" sortable="true" />
	<spring:message code="question.format" var="format" />
	<spring:message code="question.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" format="${format}" sortable="true" />
	<spring:message code="question.photoURL" var="photoURLHeader" />
	<display:column title="${photoURLHeader}" sortable="false"> <img src="${row.photoURL}" alt="${pictureError}"  width="200" height="200"> </display:column>
	<spring:message code="question.isAnswered" var="isAnsweredHeader" />
	<display:column property="isAnswered" title="${isAnsweredHeader}" sortable="true" />
	<spring:message code="question.student" var="studentHeader" />
	<display:column title="${studentHeader}"  > 
		<a href="student/display.do?studentId=${row.student.id}">
			<jstl:out value="${row.student.name} ${row.student.surname}"/>
		</a>
	</display:column>
	<display:column>
		<a href="question/display.do?questionId=${row.id}"> <spring:message
			code="question.display" />
		</a>
	</display:column>

</display:table>

</jstl:when>
<jstl:otherwise>

<spring:message code="forum.questions.empty" />

</jstl:otherwise>
</jstl:choose>

<security:authorize access="hasRole('STUDENT')">
<a href="question/student/create.do?forumId=${forum.id}"> <spring:message code="forum.question.create"/></a>
</security:authorize>	



</jstl:when>
<jstl:otherwise>

<spring:message code="forum.permission" />

</jstl:otherwise>
</jstl:choose>

<spring:message code="datatables.locale.lang" var="tableLang"/>
<spring:message code="datatables.moment.format" var="tableFormatMoment"/>
<script>
$(document).ready( function () {	
	
	
    $('#row').dataTable( {
    	"language": {
        	"url": '${tableLang}'
    	},
		"lengthMenu": [ 5, 10, 25, 50, 100 ]
    } );
} );
</script>
