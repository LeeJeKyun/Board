<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>회원가입 폼</h1>
<hr>

<form action="/member/join" method="post">

<table>

<tr>
	<td>아이디 </td>
	<td><input type="text" name="userid"></td>
</tr>

<tr>
	<td>비밀번호 </td>
	<td><input type="password" name="userpw"></td>
</tr>
<tr>
	<td>닉네임 </td>
	<td><input type="text" name="usernick"></td>
</tr>
<tr>
	<td><button>회원가입</button></td>
</tr>

</table>

</form>
</body>
</html>