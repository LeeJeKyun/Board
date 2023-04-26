<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/header.jsp" />

<script type="text/javascript">
$(function() {
	$("#result").on("click", ".btnRecommend", function() {
	 	$.ajax({
		type: "GET"	//요청 메소드
		, url: "/board/recommend"	//요청 URL
		, data: {	//요청 파라미터
			boardno: $("#boardno").html()
		}
		, dataType: "html"		//응답 데이터 형식
		, success: function( res ) {
			console.log("AJAX 성공")
// 			console.log(res);
			$("#result").html(res);
			
		}
		, error: function() {
			console.log("AJAX 실패")
		}	
		})
		
	})
})
</script>
<style type="text/css">
table {
	border: 1px solid #ccc;
}

th {
	border: 1px solid #bbb;
	background-color: #ddd;
}
#buttons {
 	text-align: center; 
}
td {
 	border: 1px solid #aaa; 
}
#content {
	width: 900px;
	height: 600px;
}
</style>


<h1>게시글 상세 내용 보기</h1>
<h1>${recommended }</h1>
<hr>

<table>

<tr>
	<th>글번호 : <span id="boardno">${board.boardno }</span></th>
	<th>제목 : ${board.title }</th>
	<th>아이디 : ${board.userid }(닉네임위치)</th>
	<th>조회수 : ${board.hit }</th>
</tr>

<tr>
	<td colspan="4" id="content"><div style="width: 890px; height: 600px;">${board.content }</div></td>
</tr>
<tr>
	<td colspan="4" style="text-align: center;">
		<a href="../upload/${boardFile.storedname }" download="${boardFile.originname }" >${boardFile.originname }</a>
	</td> 
</tr>

<tr>
	<td colspan="2" id="buttons">
		<a href="<%=request.getContextPath()%>/board/list"><button type="button">목록</button></a>
		<c:if test="${userid eq board.userid }">
		<a href="<%=request.getContextPath()%>/board/update?boardno=${board.boardno }"><button>수정</button></a>
		<a href="<%=request.getContextPath()%>/board/delete?boardno=${board.boardno }"><button>삭제</button></a>
		</c:if>
	</td>
	<td>
	<span id="result">
		추천수 : [현재추천수]=${recommendCnt } <c:if test="${recommended eq false }"><button class="btnRecommend">추천 취소</button></c:if>
			<c:if test="${recommended eq true }"><button class="btnRecommend">추천</button></c:if>
	</span>
	</td>
	<td>작성일 : ${board.writeDate }</td>
</tr>

</table>

<c:import url="../layout/footer.jsp" />
