<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
table {
	border: 1px solid #ccc;
}

th {
	border: 1px solid #bbb;
	background-color: #ddd;
}
td:nth-child(1) {
	text-align: center;
}
td {
 	border: 1px solid #aaa; 
}
textarea {
	resize: none;
	width: 900px;
	height: 600px;
}

</style>
</head>
<body>
<h1>글쓰기</h1>
<hr>

<form action="<%=request.getContextPath() %>/board/write" method="post" enctype="multipart/form-data">
<table>

	<tr>
		<td>제목</td>
		<td><input type="text" name="title"></td>
	</tr>
	<tr>
		<td>작성자</td>
		<td>${userid }</td>
	</tr>
	<tr>
		<td colspan="2"><textarea name="content"></textarea></td>
	</tr>
	<tr>
		<td colspan="2" ><input type="file" name="upfile"></td>
	</tr>
</table>
<br>
<button id="#btnWrite">작성</button>
</form>

</body>
</html>