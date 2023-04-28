<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/header.jsp" />

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
<script type="text/javascript">
$(function() {
	$("#goBack").on("click", function() {
		location.href="/board/list";
	})
	
	$("#search").focus();
})

function listDelete(){
 	$.ajax({
		type: "GET"	//요청 메소드
		, url: "/board/listDelete"	//요청 URL
		, data: {	//요청 파라미터
			boardno: $(".boardno").val()
		}
		, dataType: "html"		//응답 데이터 형식
		, success: function( res ) {
			console.log("AJAX 성공")
			
		}
		, error: function() {
			console.log("AJAX 실패")
		}	
	})
}
function selectCancel() {
	$(".checkes").removeAttr("checked")
	console.log('selectCancel')
}
function init() {
	$("#search").val("")
}

</script>


<h1><span id="goBack" style="cursor: pointer;">게시글 목록</span></h1>
<hr>

<div>


<form action="/board/listDelete" method="get">
<table style="margin: 0 auto;">
	<tr>
		<th>선택하기</th>
		<th>게시글 번호</th>
		<th style="width: 300px;">제목</th>
		<th>작성자</th>
		<th>조회수</th>
		<th>추천수</th>
		<th>작성일자</th>
	</tr>
	<c:forEach var="map" items="${list }" >
		<tr>
			<td><c:if test='${userid eq map.get("b").getUserid() }'><input type="checkbox" class="checkes" name='boardno' value='${map.get("b").getBoardno() }'></c:if></td>
			<td>${map.get("b").getBoardno()} </td>
			<td><a href="<%=request.getContextPath()%>/board/view?boardno=${map.get('b').getBoardno() }">${map.get('b').getTitle() }</a></td>
			<td>${map.get("b").getUserid() } </td>
			<td>${map.get("b").getHit() }</td>
			<td>${map.get("c") }</td>
			<td>${map.get("b").getWriteDate() }</td>
		</tr>
	</c:forEach>
	<tr>
		<td style="border: none; text-align: center;">
			<button type="button" onclick="selectCancel()" >선택취소</button>
		</td>
		<td style="border: none; text-align: center;">
			<button>삭제</button>
		</td>
		<td colspan="5" style="border: none;"></td>
	</tr>
</table>
</form>

<br>

<div style="width: 45%; margin: 0 auto; position: relative;">
	<form action="/board/list" method="get" style="position: absolute; right: 0px;">
	<input type="text" id="search" name="search" value="${paging.search }">
	<button type="submit">검색</button>
	<button type="button" id="initBtn" onclick="init()">초기화</button>
	<a href="./write" ><button type="button">글쓰기</button></a>
	</form>
</div>

<br>
<br>

<c:import url="../layout/paging.jsp" />

<c:import url="../layout/footer.jsp" />
