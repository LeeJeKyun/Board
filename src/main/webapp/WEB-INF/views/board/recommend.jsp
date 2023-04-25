<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

추천수 : [현재추천수]=${recommendCnt } <c:if test="${isRecommended eq true }"><button class="btnRecommend">추천 취소</button></c:if>
			<c:if test="${isRecommended eq false }"><button class="btnRecommend">추천</button></c:if>