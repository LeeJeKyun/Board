<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="margin:0 auto; width: 240px;">
	<%-- 이전 페이징 리스트로 이동 --%>
<%-- 	<li class="page-item"><a class="page-link" href="./list?curPage=${paging.curPage - paging.pageCount }">&laquo;</a></li> --%>
<%-- 	<li class="page-item"><a class="page-link" href="./list?curPage=${paging.endPage - paging.pageCount }">&laquo;</a></li> --%>
	<c:if test="${paging.startPage ne 1 }">
	<a class="page-link" href="./list?curPage=${paging.startPage - paging.pageCount }">
		<button>
			&laquo;
		</button>
	</a>
	</c:if>
	<c:if test="${paging.startPage eq 1 }">
	<a class="page-link">
		<button>
			&laquo;
		</button>
	</a>
	</c:if>



	<c:forEach var="i" begin="${paging.startPage }" end="${paging.endPage }">
	
		<c:if test="${paging.curPage eq i }">
			<a class="page-link" href="./list?curPage=${i }">
				<button style="color: red;">${i }</button>
			</a>
		</c:if>
		
		<c:if test="${paging.curPage ne i }">
			<a class="page-link" href="./list?curPage=${i }">
				<button>${i }</button>
			</a>
		</c:if>
		
	</c:forEach>

	<%-- 다음 페이징 리스트로 이동 --%>
	<c:if test="${paging.endPage ne paging.totalPage }">
	<a class="page-link" href="./list?curPage=${paging.startPage + paging.pageCount }">
		<button>
			&raquo;
		</button>
	</a>
	</c:if>
	<c:if test="${paging.endPage eq paging.totalPage }">
	<a class="page-link">
		<button>
			&raquo;
		</button>
	</a>
	</c:if>
</div>

</div>