<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>
<%-- /static/css/style.css --%> 
<link href="/css/style.css" rel="Stylesheet" type="text/css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

</head> 
<body>
<c:import url="/menu/top.do" />

<DIV class='title_line'>알림</DIV>

 <c:set var="code" value="${param.code }" />
<c:set var="cnt" value="${param.cnt }" />
<c:set var="cateno" value="${param.cateno }" />

<DIV class='message'>
  <fieldset class='fieldset_basic'>
    <UL>
      <c:choose>
        <c:when test="${code == 'passwd_fail'}">
          <LI class='li_none'>
            <span class="span_fail">패스워드가 일치하지 않습니다.</span>
          </LI> 
        </c:when>
        
        <c:when test="${code == 'product_success'}"> <%-- Java if --%>
          <LI class='li_none'>
            <span class="span_success">상품 정보를 등록했습니다.</span>
          </LI>                   
          <LI class='li_none'>                                                   
            <button type='button' 
                         onclick="location.href='./product_update.do?cateno=${cateno}&contentsno=${contentsno }'"
                         class="btn btn-primary">관련 상품 정보 재등록</button>
          </LI>
        </c:when>
        
        <c:when test="${code == 'product_fail'}"> <%-- Java if --%>
          <LI class='li_none'>
            <span class="span_fail">상품 정보 등록에 실패했습니다.</span>
          </LI>                                                                      
        </c:when>

        <c:when test="${code == 'create_success'}"> <%-- Java if --%>
          <LI class='li_none'>
            <span class="span_success">새로운 컨텐츠를 등록했습니다.</span>
          </LI> 
        </c:when>
        
        <c:when test="${code == 'create_fail'}"> <%-- Java if --%>
          <LI class='li_none'>
            <span class="span_fail">새로운 컨텐츠 등록에 실패했습니다.</span>
          </LI>                                                                      
        </c:when>
        
        <c:when test="${code == 'update_fail'}"> <%-- Java if --%>
          <LI class='li_none'>
            <span class="span_fail">컨텐츠 수정에 실패했습니다.</span>
          </LI>                                                                      
        </c:when>
        
        <c:when test="${code == 'delete_success'}"> <%-- Java if --%>
          <LI class='li_none'>
            <span class="span_success">컨텐츠 삭제에 성공했습니다.</span>
          </LI>                                                                      
        </c:when>        
        
        <c:when test="${code == 'delete_fail'}"> <%-- Java if --%>
          <LI class='li_none'>
            <span class="span_fail">컨텐츠 삭제에 실패했습니다.</span>
          </LI>                                                                      
        </c:when> 
        
        <c:otherwise>
          <LI class='li_none_left'>
            <span class="span_fail">알 수 없는 에러로 작업에 실패했습니다.</span>
          </LI>
          <LI class='li_none_left'>
            <span class="span_fail">다시 시도해주세요.</span>
          </LI>
        </c:otherwise>
      </c:choose>
      <LI class='li_none'>
        <br>
        <c:choose>
            <c:when test="${cnt == 0 }">
                <button type='button' onclick="history.back()" class="btn btn-primary">다시 시도</button>    
            </c:when>
        </c:choose>
        
        <button type='button' onclick="location.href='./create.do?cateno=${cateno}'" class="btn btn-primary">새로운 컨텐츠 등록</button>
        <button type='button' onclick="location.href='./list_by_cateno_search_paging.do?cateno=${cateno}'" class="btn btn-primary">목록</button>
      </LI>
    </UL>
  </fieldset>

</DIV>

<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>

</html>


