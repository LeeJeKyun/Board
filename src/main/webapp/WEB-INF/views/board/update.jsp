
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
textarea {
	resize: none;
	width: 900px;
	height: 600px;
}
td {
 	border: 1px solid #aaa; 
}
th {
	border: 1px solid #bbb;
	background-color: #ddd;
}
table {
	border: 1px solid #ccc;
}
#boardno{
	background-color: #ddd;
	border: none;
	font-weight: bold;
	width: 40px;
	font-size: 16px;
	color: black;
}

</style>
</head>
<body>

<h1>게시글 수정하기</h1>
<hr>

<form action="/board/update" method="post" enctype="multipart/form-data">
<table>

<tr>
	<th>
		글번호 : <input type="text" name="boardno" value="${board.boardno }" id="boardno" readonly="readonly">
	</th>
	<th>제목 : ${board.title }</th>
	<th>아이디 : ${board.userid }(NINKNAME)</th>
	<th>조회수 : ${board.hit }</th>
</tr>

<tr>
	<td colspan="4"><textarea name="content">${board.content }</textarea></td>
</tr>
<tr>
	<td colspan = "4" style="text-align: center;">
		<input type="file" name="file">
	</td>
</tr>
<tr>
	<td colspan="2">
		<a href="<%=request.getContextPath()%>/board/list"><button type="button">목록</button></a>
		<button type="submit">수정완료</button>
	</td>
	<td>추천수 : </td>
	<td>작성일 : ${board.writeDate }</td>
</tr>

</table>
</form>

</body>
</html>