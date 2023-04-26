<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    
<c:forEach var="comment" items="${commentList }" >
	<tr>
		<td>아이디 : ${comment.userid }</td>
		<td>댓글 내용 : ${comment.content }</td>
		<td>
		<c:if test="${userid eq comment.userid }">
			<button id="cmtDelBtn">삭제</button>
		</c:if>
		</td>
	</tr>
</c:forEach>