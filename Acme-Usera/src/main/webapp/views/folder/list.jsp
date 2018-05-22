<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div class="messages-table">
	<div class="menu-folders">
	<h3 class="titles"> <spring:message code="folder.listFolders" /></h3>
	<display:table class="displaytag" 
		name="folders" requestURI="folder/actor/list.do" id="folder">
		
		<spring:message code="folder.folder.name" var="name" />
		<display:column >
			<a href="folder/actor/list.do?folderId=${folder.id}"><jstl:out value = "${folder.name}"/></a>
		</display:column>
	
	</display:table>
	</div>
	
	<%-- La lista de mensajes de la carpeta seleccionada: --%>
	
	<div class="messages-list">
	<h3 class="titles"> <spring:message code="folder.messages" /> ${currentFolder.name}</h3>
	
	
	
	
	<jstl:if test="${not empty messages}">
	<display:table name="messages" id="row"
		requestURI="messages/actor/list.do" pagesize="5"
		class="displaytag">
				<spring:message code="folder.message.subject" var="subject"/>
				<display:column title="subject">
					<jstl:out value = "${row.subject}"/>
				</display:column>
				<spring:message code="folder.message.body" var="body"/>
				<display:column title="body">
					<jstl:out value = "${row.body}"/>
				</display:column>
				<spring:message code="folder.message.moment" var="moment"/>
				<display:column title="moment">
					<jstl:out value = "${row.moment}"/>
				</display:column>
				<spring:message code="folder.message.sender" var="sender"/>
				<display:column title="sender">
					<jstl:out value = "${row.sender.userAccount.username}"/>
				</display:column>
				<spring:message code="folder.editFolder" var="edit"/>
				<display:column>
				
					<a href="message/actor/edit.do?messageId=${row.id}"><jstl:out value="${edit}"/></a>
				</display:column>
	</display:table>
	</jstl:if>
	
	<a href="message/actor/create.do"><spring:message code="folder.writeMessage" /></a>
	<br/>
	
	<security:authorize access="hasRole('ADMIN')">
	<a href="message/admin/create.do"><spring:message code="folder.broadCastMessage" /></a>
	<br>
	</security:authorize>
	</div>
</div>

<spring:message code="datatables.locale.lang" var="tableLang"/>
<spring:message code="datatables.moment.format" var="tableFormatMoment"/>
<script>
$(document).ready( function () {	
	$.fn.dataTable.moment('${tableFormatMoment}');
	
    $('#row').dataTable( {
    	"language": {
        	"url": '${tableLang}'
    	},
		"lengthMenu": [ 5, 10, 25, 50, 100 ]
    } );
} );
</script>