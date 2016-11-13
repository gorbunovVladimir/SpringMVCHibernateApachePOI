<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html; charset=Windows-1251" %>
<%@ page session="false" %>
<html>
<head>
	<title>Hospitalization Page</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
</head>
<body>



<h2>��������� excel-���� � ������� �������</h2>
<form:form method="post" action="save"
           modelAttribute="uploadForm" enctype="multipart/form-data">
           ���������� �������� ���� ��� �������� : <input type="file" name="file" />
           <input type="submit" value="���������" />
           <span><form:errors path="file" cssClass="error" />
           </span>
</form:form>

<c:if test="${!empty fileName}">
${fileName} ��������
</c:if>

<h3>���������� ����</h3>
<div class="row">
    <form method="get" action="/SpringMVCHibernateApachePOI/humans">
        <div class="small-3 columns">
            <input type="number" id ="numb" name="countOfBed" >
        </div>

        <div class="small-5 columns end">
            <button id="button-id" type="submit">��������� ���� ��������������</button>
        </div>

        <div>
            ${countOfBed}
        </div>
    </form>
</div>

<br>
<h3>������������������� ��������</h3>
<c:if test="${!empty listForHospitalization}">
	<table class="tg">
		<tr>
			<th width="80">Id � �� ��������</th>
			<th width="120">��� ��������</th>
			<th width="90">���� ������ ��������������</th>
			<th width="90">���� ��������� ��������������</th>
			<th width="80">����� ���������� �����</th>
		</tr>
		<c:forEach items="${listForHospitalization}" var="human">
			<tr>
				<td>${human.id}</td>
				<td>${human.name}</td>
				<td>${human.bdate}</td>
                <td>${human.edate}</td>
                <td>${human.bedNumber}</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<br>
<h3>�������� ��� ������� ����� ��������������� ���� ��������������</h3>
<c:if test="${!empty listForNotHospitalization}">
    <table class="tg">
        <tr>
            <th width="80">Id � �� ��������</th>
            <th width="120">��� ��������</th>
            <th width="90">���� ������ ��������������</th>
            <th width="90">���� ��������� ��������������</th>
        </tr>
        <c:forEach items="${listForNotHospitalization}" var="human">
            <tr>
                <td>${human.id}</td>
                <td>${human.name}</td>
                <td>${human.bdate}</td>
                <td>${human.edate}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:url var="removeAll" value="/human/removeAll" ></c:url>
<form:form action="${removeAll}" >
    <table >
        <tr>
            <td width="120"></td>
        </tr>
        <tr>
            <td><input type="submit" name="submit" value="Remove all records"></td>
        </tr>
    </table>
</form:form>
</body>
</html>
