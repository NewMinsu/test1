<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contentsno" value="${contentsVO.contentsno }" />
<c:set var="cateno" value="${contentsVO.cateno }" />
<c:set var="title" value="${contentsVO.title }" />        
<c:set var="price" value="${contentsVO.price }" />
<c:set var="dc" value="${contentsVO.dc }" />
<c:set var="saleprice" value="${contentsVO.saleprice }" />
<c:set var="point" value="${contentsVO.point }" />
<c:set var="salecnt" value="${contentsVO.salecnt }" />
<c:set var="file1" value="${contentsVO.file1 }" />
<c:set var="file1saved" value="${contentsVO.file1saved }" />
<c:set var="thumb1" value="${contentsVO.thumb1 }" />
<c:set var="size1" value="${contentsVO.size1 }" />
<c:set var="content" value="${contentsVO.content }" />
<c:set var="word" value="${contentsVO.word }" />
<c:set var="size1_label" value="${contentsVO.size1_label }" />
<c:set var="map" value="${contentsVO.map }" />
<c:set var="youtube" value="${contentsVO.youtube }" />
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
</head> 
 
<body>
<c:import url="/menu/top.do" />
 
<DIV class='title_line'><A href="./list_by_cateno_search_paging.do?cateno=${cateno }" class='title_link'>한국 프리미어리그 선수</A></DIV>

<DIV class='content_body'>
  <ASIDE class="aside_right">
    <A href="javascript:location.reload();">새로고침</A>


    <%-- <c:if test="${sessionScope.admin_id != null }"> --%>
      <span class='menu_divide' >│</span>
      <A href="./create.do?cateno=${cateVO.cateno }">등록</A>
      <span class='menu_divide' >│</span>
	    <A href="./update_text.do?contentsno=${contentsno}&now_page=${param.now_page}">글 수정</A>
	    <span class='menu_divide' >│</span>
	    <A href="./update_file.do?contentsno=${contentsno}&now_page=${param.now_page}">파일 수정</A>
	    <span class='menu_divide' >│</span>
	    <A href="./youtube.do?cateno=${cateno }&contentsno=${contentsno}">Youtube</A>
	    <span class='menu_divide' >│</span>
	    <A href="./delete.do?contentsno=${contentsno}&now_page=${param.now_page}&cateno=${cateno}">삭제</A>  
<%--     </c:if> --%>
    
  </ASIDE> 
  
  <%-- 검색 --%>
  <DIV style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_cateno_search_paging.do'>
      <input type='hidden' name='cateno' value='${param.cateno }'>
      <c:choose>
        <c:when test="${param.word != '' }"> <%-- 검색하는 경우 --%>
          <input type='text' name='word' id='word' value='${param.word }' style='width: 20%;'>
        </c:when>
        <c:otherwise> <%-- 검색하지 않는 경우 --%>
          <input type='text' name='word' id='word' value='' style='width: 20%;'>
        </c:otherwise>
      </c:choose>
      <button type='submit'>검색</button>
      <c:if test="${param.word.length() > 0 }">
        <button type='button' 
                     onclick="location.href='./list_by_cateno_search_paging.do?cateno=${cateVO.cateno}&word='">검색 취소</button>  
      </c:if>    
    </form>
  </DIV>
  
  <DIV class='menu_line'></DIV>

  <fieldset class="fieldset_basic">
    <ul>
      <li class="li_none">
        <DIV style="width: 50%; float: left; margin-right: 10px;">
            <c:choose>
              <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}">
                <%-- /static/contents/storage/ --%>
                <A href="/contents/storage/${file1saved }"><IMG src="/contents/storage/${file1saved }" style="width: 100%;"></A> 
              </c:when>
              <c:otherwise> <!-- 기본 이미지 출력 -->
                <IMG src="/contents/images/none2.png" style="width: 50%;"> 
              </c:otherwise>
            </c:choose>
        </DIV>
        <DIV style="width: 47.5%; height: 260px; float: left; margin-right: 10px; margin-bottom: 30px;">
          <span style="font-size: 1.5em; font-weight: bold;">${title}</span><br><br>
          <span style="font-size: 1.3em; font-weight: bold;">프로 클럽 : ${point}</span><br><br>
          <span style="font-size: 1.3em; font-weight: bold;">국적 : ${price}</span><br><br>
          <span style="color: #000000; font-size: 1.3em;">나이 : ${dc}</span><br>

          <form>

          </form>
        </DIV> 

        <DIV>${content }</DIV>
      </li>

      <c:if test="${youtube.trim().length() > 0 }">
	      <li class="li_none" style="clear: both; padding-top: 15px; padding-bottom: 15px;">
				  <DIV style="text-align: center;">
				    ${youtube }
				  </DIV>
	      </li>
      </c:if>
            
      <c:if test="${map.trim().length() > 0 }">
	      <li class="li_none" style="clear: both; padding-top: 15px; padding-bottom: 15px;">
				  <DIV style='width:640px; height: 380px; margin: 0px auto;'>
				    ${map }
				  </DIV>
	      </li>
      </c:if>
      
      <li class="li_none">
        <DIV style='text-decoration: none;'>
          검색어(키워드): ${word }
        </DIV>
      </li>
      <li class="li_none">
        <DIV>
          <c:if test="${file1.trim().length() > 0 }">
            첨부 파일: <A href='/download?dir=/contents/storage&filename=${file1saved}&downname=${file1}'>${file1}</A> (${size1_label}) 
            <A href='/download?dir=/contents/storage&filename=${file1saved}&downname=${file1}'><IMG SRC="/contents/images/download.png"></A>
          </c:if>
        </DIV>
      </li>   
    </ul>
  </fieldset>

</DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

