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




<spring:message code="curriculum.propietaryOf" var="propietary"/>
<h3> <jstl:out value="${propietary} ${curriculum.teacher.userAccount.username}"> </jstl:out> </h3>

<%-- Botones para a�adir Records al curriculum --%>
<jstl:if test="${curriculum.teacher.id == principal.id }">
	<table class="displayStyle">
		<tr>
			<th colspan="4"><spring:message code="curriculum.buttonsCreationRecords" /></th>
		</tr>
		<tr>
			<td>
				<input type="button" name="addPR"
				value="<spring:message code="curriculum.addPR" />"
				onclick="redirect: location.href = 'professionalRecord/teacher/create.do';" />
			</td>
			
			<td>
				<input type="button" name="addER"
				value="<spring:message code="curriculum.addER" />"
				onclick="redirect: location.href = 'educationRecord/teacher/create.do';" />
			</td>
			
			<td>
				<input type="button" name="addMR"
				value="<spring:message code="curriculum.addMR" />"
				onclick="redirect: location.href = 'miscellaneousRecord/teacher/create.do';" />
			</td>
		</tr>
		
	</table>
</jstl:if>


<table class="displayStyle">


<tr>
<th> <jstl:out value="${propietary} ${curriculum.teacher.userAccount.username}"> </jstl:out>  </th>
<th>   </th>
</tr>

<tr>
<td> <strong> <spring:message code="curriculum.propietary" /> : </strong> </td>
<td> <a href="actor/display.do?actorId=${curriculum.teacher.id}"><spring:message code="curriculum.display.teacher" /></a> </td>
</tr>

<tr>
<td> <strong> <spring:message code="curriculum.ticker" /> : </strong> </td>
<td> <jstl:out value="${ curriculum.ticker}"></jstl:out> </td>
</tr>

<tr>
<td> <strong> <spring:message code="curriculum.personalRecord" /> : </strong> </td>
<td>
	<table class="displayStyle">
	<tr>
	<th><strong> <spring:message code="curriculum.personalRecord" /></strong></th>
	<th></th>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.personalRecord.name" /> : </strong></td>
	<td><jstl:out value="${curriculum.personalRecord.name}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.personalRecord.surname" /> : </strong></td>
	<td><jstl:out value="${curriculum.personalRecord.surname}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.personalRecord.linkPhoto" /> : </strong></td>
	<td><img src="${curriculum.personalRecord.linkPhoto}" alt = "${curriculum.personalRecord.linkPhoto}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.personalRecord.email" /> : </strong></td>
	<td><jstl:out value="${curriculum.personalRecord.email}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.personalRecord.phone" /> : </strong></td>
	<td><jstl:out value="${curriculum.personalRecord.phone}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.personalRecord.linkedInProfile" /> : </strong></td>
	<td><a href="${curriculum.personalRecord.linkedInProfile }"></a>${curriculum.personalRecord.linkedInProfile }</td>
	</tr>
	</table>
</td>
</tr>

<jstl:if test="${ not empty curriculum.professionalRecords}" >
<jstl:set var="countPR" value="1"/>
<tr>
<td> <strong> <spring:message code="curriculum.professionalRecords" /> : </strong> </td>
<td>
	<jstl:forEach var="PR" items="${curriculum.professionalRecords}">
	<table class="displayStyle">
	<tr>
	<th><strong> <spring:message code="curriculum.professionalRecord" /><jstl:out value="${countPR}"/></strong></th>
	<th></th>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecord.companyName" />: </strong></td>
	<td><jstl:out value="${PR.companyName}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecord.startDate" />: </strong></td>
	<td><fmt:formatDate value="${PR.startDate}" pattern="dd/MM/yyyy" /></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecord.endDate" />: </strong></td>
	<td><fmt:formatDate value="${PR.endDate}" pattern="dd/MM/yyyy" /></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecord.role" />: </strong></td>
	<td><jstl:out value="${PR.role}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecord.linkAttachment" />: </strong></td>
	<td><a href="${PR.linkAttachment}"><jstl:out value="${PR.linkAttachment}"/></a></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecord.comments" />: </strong></td>
	<td>
		<ul>
		<jstl:forEach var="comment" items="${PR.comments}">
			<li><jstl:out value="${comment }"/></li>
		</jstl:forEach>
		</ul>
	</td>
	</tr>
	<jstl:set var="countPR" value="${countPR+1}"/>
	
	<jstl:if test="${curriculum.teacher == principal}" >
	<tr>
		<td colspan="2">
			<br>
			<input type="button" name="edit"
			value="<spring:message code="curriculum.editPR" />"
			onclick="redirect: location.href = 'professionalRecord/teacher/edit.do?professionalRecordId=${PR.id}';" />
		</td>
	</tr>
	</jstl:if>
	
	</table>
	</jstl:forEach>
</td>
</tr>
</jstl:if>

<%-- Hacemos lo mismo para  EducationRecord --%>
<jstl:if test="${ not empty curriculum.educationRecord}" >
<jstl:set var="countER" value="1"/>
<tr>
<td> <strong> <spring:message code="curriculum.educationRecords" /> : </strong> </td>
<td>
	<jstl:forEach var="ER" items="${curriculum.educationRecord}">
	<table class="displayStyle">
	<tr>
	<th><strong> <spring:message code="curriculum.educationRecord" /><jstl:out value="${countER}"/></strong></th>
	<th></th>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.educationRecord.diplomaTitle" />: </strong></td>
	<td><jstl:out value="${ER.diplomaTitle}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.educationRecord.startDate" />: </strong></td>
	<td><fmt:formatDate value="${ER.startDate}" pattern="dd/MM/yyyy" /></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.educationRecord.endDate" />: </strong></td>
	<td><fmt:formatDate value="${ER.endDate}" pattern="dd/MM/yyyy" /></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.educationRecord.institutionName" />: </strong></td>
	<td><jstl:out value="${ER.institutionName}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.educationRecord.linkAttachment" />: </strong></td>
	<td><a href="${ER.linkAttachment}"><jstl:out value="${ER.linkAttachment}"/></a></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.educationRecord.comments" />: </strong></td>
	<td>
		<ul>
		<jstl:forEach var="comment" items="${ ER.comments}">
			<li><jstl:out value="${comment }"/></li>
		</jstl:forEach>
		</ul>
	</td>
	</tr>
	<jstl:set var="countER" value="${countER+1}"/>
	<jstl:if test="${curriculum.teacher == principal}" >
	<tr>
		<td colspan="2">
			<br>
			<input type="button" name="edit"
			value="<spring:message code="curriculum.editER" />"
			onclick="redirect: location.href = 'educationRecord/teacher/edit.do?educationRecordId=${ER.id}';" />
		</td>
	</tr>
	</jstl:if>
	
	</table>
	</jstl:forEach>
</td>
</tr>

</jstl:if>

<%-- Igual para MiscellaneousRecord --%>
<jstl:if test="${ not empty curriculum.miscellaneousRecord}" >
<jstl:set var="countMR" value="1"/>
<tr>
<td> <strong> <spring:message code="curriculum.miscellaneousRecords" /> : </strong> </td>
<td>
	<jstl:forEach var="MR" items="${curriculum.miscellaneousRecord}">
	<table class="displayStyle">
	<tr>
	<th><strong> <spring:message code="curriculum.miscellaneousRecord" /><jstl:out value="${countMR}"/></strong></th>
	<th></th>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.miscellaneousRecord.title" />: </strong></td>
	<td><jstl:out value="${MR.title}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.miscellaneousRecord.linkAttachment" />: </strong></td>
	<td><a href="${MR.linkAttachment }"><jstl:out value="${MR.linkAttachment}"/></a></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.miscellaneousRecord.comments" />: </strong></td>
	<td>
		<ul>
		<jstl:forEach var="comment" items="${MR.comments}">
			<li><jstl:out value="${comment }"/></li>
		</jstl:forEach>
		</ul>
	</td>
	</tr>
	<jstl:set var="countMR" value="${countMR+1}"/>
	
	<jstl:if test="${curriculum.teacher == principal}" >
	<tr>
		<td colspan="2">
			<br>
			<input type="button" name="edit"
			value="<spring:message code="curriculum.editMR" />"
			onclick="redirect: location.href = 'miscellaneousRecord/teacher/edit.do?miscellaneousRecordId=${MR.id}';" />
		</td>
	</tr>
	</jstl:if>
	
	</table>
	</jstl:forEach>
</td>
</tr>

</jstl:if>

</table>

<div>
<jstl:if test="${curriculum.teacher == principal}" >
	<jstl:if test="${curriculum.id!=0}">
		<input type="button" name="edit"
		value="<spring:message code="curriculum.edit" />"
		onclick="redirect: location.href = 'curriculum/teacher/edit.do?curriculumId=${curriculum.id}';" />
	</jstl:if>
</jstl:if>
</div>



