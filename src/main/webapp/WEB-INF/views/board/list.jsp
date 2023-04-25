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

</style>
</head>
<body>

<h1>게시글 목록</h1>
<hr>

<a href="./write"><button>글쓰기</button></a>

<table>
	<tr>
		<th>게시글 번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>조회수</th>
		<th>추천수</th>
		<th>작성일자</th>
	</tr>
	
	<c:forEach var="map" items="${list }" >
		<tr>
			<td>${map.get("b").getBoardno()} </td>
			<td><a href="<%=request.getContextPath()%>/board/view?boardno=${map.get('b').getBoardno() }">${map.get('b').getTitle() }</a></td>
			<td>${map.get("b").getUserid() }</td>
			<td>${map.get("b").getHit() }</td>
			<td>${map.get("c") }</td>
			<td>${map.get("b").getWriteDate() }</td>
		</tr>
	</c:forEach>
	
</table>

</body>
</html>