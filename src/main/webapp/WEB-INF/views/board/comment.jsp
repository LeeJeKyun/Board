<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    
<c:forEach var="comment" items="${commentList }" >
	<tr>
<%-- 		<td class="commentno">${comment.commentno }</td> --%>
		<td>아이디 : ${comment.userid }</td>
		<td>댓글 내용 : ${comment.content }</td>
		<td class="cmtBtnTd">
		<c:if test="${userid eq comment.userid }">
			<button class="cmtDelBtn" data-commentno="${comment.commentno }" onclick="del(${comment.commentno})">삭제</button>
		</c:if>
		</td>
	</tr>
</c:forEach>