<%--
 * 
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:if test="${permission}">
	<form:form action="educationRecord/teacher/edit.do" modelAttribute="educationRecord" id="form">
	
		<form:hidden path="id" />
		
		<form:hidden path="version" />
		
		
		<fieldset>
		<legend> <spring:message code="er.educationRecord" />: </legend>
		
		<form:label path="diplomaTitle">
			<spring:message code="er.diplomaTitle" />:
		</form:label>
		<form:input path="diplomaTitle"  />
		<form:errors cssClass="error" path="diplomaTitle" />
		<br />
		<br />
		
		<form:label path="startDate">
			<spring:message code="er.startDate" />:
		</form:label>
		<form:input path="startDate" placeholder="dd/MM/yyyy" />
		<form:errors cssClass="error" path="startDate" />
		<br />
		<br />
		
		<form:label path="endDate">
			<spring:message code="er.endDate" />:
		</form:label>
		<form:input path="endDate"  placeholder="dd/MM/yyyy"/>
		<form:errors cssClass="error" path="endDate" />
		<br />
		<br />
		
		<form:label path="institutionName">
			<spring:message code="er.institutionName" />:
		</form:label>
		<form:input path="institutionName"  />
		<form:errors cssClass="error" path="institutionName" />
		<br />
		<br />
		
		
		<form:label path="linkAttachment">
			<spring:message code="er.linkAttachment" />:
		</form:label>
		<form:input path="linkAttachment"  />
		<form:errors cssClass="error" path="linkAttachment" />
		<br />
		<br />
		
		
		<%-- Lista din�mica para los comments --%>
		
		<fieldset style="width: 40%;">

	
	<legend> <form:label path="comments"> <spring:message code="er.comments" />: </form:label> </legend>



		<div id="list1">
		
		<table class="displayStyle">
			<tr>
			<th>  <spring:message code="er.comment" /> :  </th>
			<th> </th>
			 </tr>
			
			<jstl:choose> 
			<jstl:when test="${empty educationRecord.comments}">
			<tr class="list-item">
			
			
			
			<td>	<form:textarea path="comments[0]" /> </td>
			
			<td>	<a href="#" onclick="event.preventDefault();"
					class="list-remove"> <spring:message code="er.comment.remove" /> </a> </td>
			</tr>
			
			</jstl:when>
			<jstl:otherwise>
			<jstl:forEach items="${educationRecord.comments}" var="comment" varStatus="i" begin="0">
   			 <tr class="list-item">
			<td> 	<form:textarea path="comments[${i.index}]" /> </td>
			
     		<td>	<a href="#" onclick="event.preventDefault();"
					class="list-remove"> <spring:message code="er.comment.remove" /> </a> </td>
	    </tr>
            <br />
        </jstl:forEach>
			</jstl:otherwise>
			</jstl:choose>
			
		</table>
		<a href="#" onclick="event.preventDefault();" class="list-add"><spring:message code="er.comment.add" /></a>
		</div>
		<br />
		<form:errors cssClass="error" path="comments" />

</fieldset>
		
	
	<br>
	<br>
	
	
	<input type="submit" name="save" id="save" value="<spring:message code="er.save" />" />&nbsp; 
		<jstl:if test="${educationRecord.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="er.delete" />"
				onclick="return confirm('<spring:message code="er.confirm.delete" />')" />&nbsp;
		</jstl:if>
		<jstl:if test="${curriculum.id!=0}">
		<input type="button" name="cancel" value="<spring:message code="er.cancel" />"
			onclick="javascript: relativeRedir('/curriculum/teacher/display.do');" />
		<br />
		</jstl:if>
		
	</fieldset>
	</form:form>
	<script>
    $(document).ready(function() {
        $("#list1").dynamiclist();
    });
</script>

</jstl:if>
<jstl:if test="${!permission}">
<h3><spring:message code="er.nopermisiontobehere" /></h3>
</jstl:if>






