<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="dev.mvc.cate.CateVO" %>

<DIV class='container_main'> 
    <!-- 헤더 start -->
    <div class="header">
        <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
            <a class="navbar-brand" href="/">BAKERY</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle Navigation">
                <span class="navbar-toggler-icon"></span>
            </button>    
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <ul class="navbar-nav mr-auto">
							      <%
							      ArrayList<CateVO> list = (ArrayList<CateVO>)request.getAttribute("list");
							      for (int index=0; index < list.size(); index++) {
							        CateVO cateVO = list.get(index);
							      %>
							        <li class="nav-item">
                        <a class="nav-link" href="/contents/list_by_cateno_search_paging.do?cateno=<%=cateVO.getCateno() %>"><%=cateVO.getName()%></a>
                      </li>
							      <%
							      }      
							      %>
     
                                 
                    <li class="nav-item">
                        <c:choose>
                            <c:when test="${sessionScope.id == null}">
                                <a class="nav-link" href="/member/login.do">로그인</a>
                            </c:when>
                            <c:otherwise>
                                <a class="nav-link" href='/member/logout.do'>${sessionScope.id } 로그아웃</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    
                    <li class="nav-item dropdown"> <%-- 회원 서브 메뉴 --%>
                        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">회원</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="/member/create.do">회원 가입</a>
                            <a class="dropdown-item" href="#">아이디 찾기</a>
                            <a class="dropdown-item" href="#">비밀번호 찾기</a>
														<c:choose>
                            	<c:when test="${sessionScope.id != null}">
		                            <a class="dropdown-item" href="/member/read.do">가입 정보</a>
		                            <a class="dropdown-item" href="/member/passwd_update.do">비밀번호 변경</a>
		                            <a class="dropdown-item" href="/member/read.do">회원 정보 수정</a>
		                            <a class="dropdown-item" href="#">회원 탈퇴</a>
		                          </c:when>
		                        </c:choose>
                        </div>
                    </li>
                    
                    <c:choose>
                        <c:when test="${sessionScope.grade <= 10}"> <%-- 로그인 한 경우 --%>                    
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">회원 관리자</a>
                                <div class="dropdown-menu">
                                    <a class="dropdown-item" href="/cate/list_all.do">카테고리</a>
                                    <a class="dropdown-item" href="/member/list.do">회원 목록</a>
                                </div>
                            </li>
                        </c:when>
                    </c:choose>        

	                  <li class="nav-item">
	                      <c:choose>
	                          <c:when test="${sessionScope.admin_id == null}">
	                              <a class="nav-link" href="/admin/login.do">관리자 로그인</a>
	                          </c:when>
	                          <c:otherwise>
	                              <a class="nav-link" href='/admin/logout.do'>관리자 ${sessionScope.id } 로그아웃</a>
	                          </c:otherwise>
	                      </c:choose>
	                  </li> 
	                  
	                  <c:choose>
	                      <c:when test="${sessionScope.admin_grade <= 10}"> <%-- 관리자로 로그인 한 경우 --%>                    
	                          <li class="nav-item dropdown">
	                              <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">관리자</a>
	                              <div class="dropdown-menu">
	                                  <a class="dropdown-item" href="/cate/list_all.do">카테고리</a>
	                                  <a class="dropdown-item" href="/member/list.do">회원 목록</a>
	                              </div>
	                          </li>
	                      </c:when>
	                  </c:choose>        
                              
                </ul>
            </div>    
        </nav>

    </div>
    <!-- 헤더 end -->
    
    <%-- 내용 --%> 
    <DIV class='content'>
      <div style='clear: both; height: 50px;'></div>
  
  
  
  
  