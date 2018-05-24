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
	<form:form action="professionalRecord/teacher/edit.do" modelAttribute="professionalRecord" id="form">
	
		<form:hidden path="id" />
		
		<form:hidden path="version" />
		
		
		<fieldset>
		<legend> <spring:message code="pr.professionalRecord" />: </legend>
		
		<form:label path="companyName">
			<spring:message code="pr.companyName" />:
		</form:label>
		<form:input path="companyName"  />
		<form:errors cssClass="error" path="companyName" />
		<br />
		<br />
		
		<form:label path="startDate">
			<spring:message code="pr.startDate" />:
		</form:label>
		<form:input path="startDate" placeholder="dd/MM/yyyy" />
		<form:errors cssClass="error" path="startDate" />
		<br />
		<br />
		
		<form:label path="endDate">
			<spring:message code="pr.endDate" />:
		</form:label>
		<form:input path="endDate"  placeholder="dd/MM/yyyy"/>
		<form:errors cssClass="error" path="endDate" />
		<br />
		<br />
		
		<form:label path="role">
			<spring:message code="pr.role" />:
		</form:label>
		<form:input path="role"  />
		<form:errors cssClass="error" path="role" />
		<br />
		<br />
		
		
		<form:label path="linkAttachment">
			<spring:message code="pr.linkAttachment" />:
		</form:label>
		<form:input path="linkAttachment"  />
		<form:errors cssClass="error" path="linkAttachment" />
		<br />
		<br />
		
		
		<%-- Lista din�mica para los comments --%>
		
		<fieldset style="width: 40%;">

	
	<legend> <form:label path="comments"> <spring:message code="pr.comments" />: </form:label> </legend>



		<div id="list1">
		
		<table class="displayStyle">
			<tr>
			<th>  <spring:message code="pr.comment" /> :  </th>
			<th> </th>
			 </tr>
			
			<jstl:choose> 
			<jstl:when test="${empty professionalRecord.comments}">
			<tr class="list-item">
			
			
			
			<td>	<form:textarea path="comments[0]" /> </td>
			
			<td>	<a href="#" onclick="event.preventDefault();"
					class="list-remove"> <spring:message code="pr.comment.remove" /> </a> </td>
			</tr>
			
			</jstl:when>
			<jstl:otherwise>
			<jstl:forEach items="${professionalRecord.comments}" var="comment" varStatus="i" begin="0">
   			 <tr class="list-item">
			<td> 	<form:textarea path="comments[${i.index}]" /> </td>
			
     		<td>	<a href="#" onclick="event.preventDefault();"
					class="list-remove"> <spring:message code="pr.comment.remove" /> </a> </td>
	    </tr>
            <br />
        </jstl:forEach>
			</jstl:otherwise>
			</jstl:choose>
			
		</table>
		<a href="#" onclick="event.preventDefault();" class="list-add"><spring:message code="pr.comment.add" /></a>
		</div>
		<br />
		<form:errors cssClass="error" path="comments" />

</fieldset>
		
	
	<br>
	<br>
	
	
	<input type="submit" name="save" id="save" value="<spring:message code="pr.save" />" />&nbsp; 
		<jstl:if test="${professionalRecord.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="pr.delete" />"
				onclick="return confirm('<spring:message code="pr.confirm.delete" />')" />&nbsp;
		</jstl:if>
		<jstl:if test="${curriculum.id!=0}">
		<input type="button" name="cancel" value="<spring:message code="pr.cancel" />"
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
<h3><spring:message code="pr.nopermisiontobehere" /></h3>
</jstl:if>






