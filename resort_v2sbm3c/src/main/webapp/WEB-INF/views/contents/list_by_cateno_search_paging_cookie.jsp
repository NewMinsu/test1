<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
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

<script type="text/javascript">
  $(function() {
    // var contentsno = 0;
    // $('#btn_cart').on('click', function() { cart_ajax(contentsno)});
    // $('#btn_login').on('click', login_ajax);
    // $('#btn_loadDefault').on('click', loadDefault);
  });

  <%-- 로그인 기본값 --%>
  function loadDefault() {
	    $('#id').val('user1');
	    $('#passwd').val('1234');
  } 
  
  <%-- 로그인 --%>
  function login_ajax() {
    var params = "";
    params = $('#frm_login').serialize(); // 직렬화, 폼의 데이터를 키와 값의 구조로 조합
    // params += '&${ _csrf.parameterName }=${ _csrf.token }';
    // alert(params);
    // return;
    
    $.ajax(
      {
        url: '/member/login_ajax.do',
        type: 'post',  // get, post
        cache: false, // 응답 결과 임시 저장 취소
        async: true,  // true: 비동기 통신
        dataType: 'json', // 응답 형식: json, html, xml...
        data: params,      // 데이터
        success: function(rdata) { // 응답이 온경우
          var str = '';
          alert('-> login cnt: ' + rdata.cnt);  // 1: 로그인 성공
          
          if (rdata.cnt == 1) {
            // 쇼핑카트에 insert 처리 Ajax 호출
            $('#div_login').hide(); // 로그인폼 감추기
            alert('로그인 성공');
            $('#login_yn').val('Y');
            
          } else {
            alert('로그인에 실패했습니다.\n잠시후 다시 시도해주세요.');
            
          }
        },
        // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
        error: function(request, status, error) { // callback 함수
          console.log(error);
        }
      }
    );  //  $.ajax END

  }

  <%-- 쇼핑 카트에 상품 추가 --%>
  function cart_ajax(contentsno) {
    var f = $('#frm_login');
    $('#contentsno', f).val(contentsno);  // 쇼핑카트 등록시 사용할 상품 번호를 저장.
    
    // console.log('-> contentsno: ' + $('#contentsno', f).val()); 
    
    if ($('#login_yn') != 'Y') {  // 로그인이 안되어 있다면
      $('#div_login').show();   // 로그인 폼 
    } else {  // 로그인 한 경우
      alert('쇼핑카트에 insert 처리 Ajax 호출');
    }

  }
  
</script>

</head> 
 
<body>
<c:import url="/menu/top.do" />
 
<DIV class='title_line'>
  <A href="./list_by_cateno_search_paging.do?cateno=${cateVO.cateno }" class='title_link'>${cateVO.name }</A>
</DIV>

<DIV class='content_body'>
  <ASIDE class="aside_right">
    <A href="./create.do?cateno=${cateVO.cateno }">등록</A>
    <span class='menu_divide' >│</span>
    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>
    <A href="./list_by_cateno_search_paging.do?cateno=${cateVO.cateno }">기본 목록형</A>    
    <span class='menu_divide' >│</span>
    <A href="./list_by_cateno_grid.do?cateno=${cateVO.cateno }">갤러리형</A>
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
  
  <%-- ******************** Ajax 기반 로그인 폼 시작 ******************** --%>
  <DIV id='div_login' style='display: none;'>
    <div style='width: 30%; margin: 0px auto;'>
      <FORM name='frm_login' id='frm_login' method='POST'>
        <input type='hidden' name='contentsno' id='contentsno' value=''>
        <input type='hidden' name='login_yn' id='login_yn' value=''>
        
        <div class="form_input">
          <input type='text' class="form-control" name='id' id='id' 
                    value="${ck_id }" required="required" 
                    style='width: 100%;' placeholder="아이디" autofocus="autofocus">
          <Label>   
            <input type='checkbox' name='id_save' value='Y' ${ck_id_save == 'Y' ? "checked='checked'" : "" }> 저장
          </Label>    
        </div>   
     
        <div class="form_input">
          <input type='password' class="form-control" name='passwd' id='passwd' 
                    value='${ck_passwd }' required="required" style='width: 100%;' placeholder="패스워드">
          <Label>
            <input type='checkbox' name='passwd_save' value='Y' ${ck_passwd_save == 'Y' ? "checked='checked'" : "" }> 저장
          </Label>                    
        </div>   
      
      </FORM>
    </div>
   
    <div style='text-align: center; margin: 10px auto;'>
      <button type="button" id='btn_login' class="btn btn-info" onclick="login_ajax()">로그인</button>
      <button type='button' onclick="location.href='./create.do'" class="btn btn-info">회원가입</button>
      <button type='button' id='btn_loadDefault' class="btn btn-info" onclick="loadDefault()">테스트 계정</button>
      <button type='button' id='btn_cancel' class="btn btn-info" onclick="$('#div_login').hide();">취소</button>
    </div>
  
  </DIV>
  <%-- ******************** Ajax 기반 로그인 폼 종료 ******************** --%>
  
  <table class="table table-striped" style='width: 100%;'>
    <colgroup>
      <col style="width: 10%;"></col>
      <col style="width: 55%;"></col>
      <col style="width: 20%;"></col>
      <col style="width: 15%;"></col>
    </colgroup>
    <%-- table 컬럼 --%>
<!--     <thead>
      <tr>
        <th style='text-align: center;'>파일</th>
        <th style='text-align: center;'>상품명</th>
        <th style='text-align: center;'>정가, 할인률, 판매가, 포인트</th>
        <th style='text-align: center;'>기타</th>
      </tr>
    
    </thead> -->
    
    <%-- table 내용 --%>
    <tbody>
      <c:forEach var="contentsVO" items="${list }">
        <c:set var="cateno" value="${contentsVO.cateno }" />
        <c:set var="contentsno" value="${contentsVO.contentsno }" />
        <c:set var="file1" value="${contentsVO.file1 }" />
        <c:set var="size1" value="${contentsVO.size1 }" />
        <c:set var="thumb1" value="${contentsVO.thumb1 }" />

        
        <tr style="height: 132px;"> 
          <td style='vertical-align: middle; text-align: center;'>
            <c:choose>
              <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}"> <%-- 이미지인지 검사 --%>
                <%-- /static/contents/storage/ --%>
                <a href="./read.do?contentsno=${contentsno}&cateno=${cateno}&word=${param.word}&now_page=${param.now_page}"><IMG src="/contents/storage/${thumb1 }" style="width: 120px; height: 80px;"></a> 
              </c:when>
              <c:otherwise> <!-- 파일이 없거나 이미지가 아닌 경우 출력 -->
		
							  <c:choose>
							    <c:when test="${size1 > 0 }"> <!-- 파일명 출력 -->
							      <a href="./read.do?contentsno=${contentsno}&cateno=${cateno}&word=${param.word}&now_page=${param.now_page}">${file1 }</a>
							    </c:when>
							    <c:when test="${size1 == 0 }"> <!-- 기본 이미지 출력 -->
    							  <a href="./read.do?contentsno=${contentsno}&cateno=${cateno}&word=${param.word}&now_page=${param.now_page}"><IMG src="/contents/images/none1.png" style="width: 120px; height: 80px;"></a>
							    </c:when>
							  </c:choose>
                
              </c:otherwise>
            </c:choose>
          </td>  
          <td style='vertical-align: middle;'>
            <a href="./read.do?contentsno=${contentsno}&cateno=${cateno}&word=${param.word}&now_page=${param.now_page}"><strong>${contentsVO.title}</strong> 
            <c:choose>
              <c:when test="${contentsVO.content.length() > 160 }">
                  ${contentsVO.content.substring(0, 160)}.....
              </c:when>
              <c:when test="${contentsVO.content.length() <= 160 }">
                  ${contentsVO.content}
              </c:when>
            </c:choose>
            
            </a> 
          </td> 
          <td style='vertical-align: middle; text-align: center;'>
            <del><fmt:formatNumber value="${contentsVO.price}" pattern="#,###" /></del><br>
            <span style="color: #FF0000; font-size: 1.2em;">${contentsVO.dc} %</span>
            <strong><fmt:formatNumber value="${contentsVO.saleprice}" pattern="#,###" /></strong><br>
            <span style="font-size: 0.8em;">포인트: <fmt:formatNumber value="${contentsVO.point}" pattern="#,###" /></span>
            <br>
            <button type='button' id='btn_cart' class="btn btn-info btn-sm" style='margin-bottom: 2px;' onclick="cart_ajax(${contentsno })">장바 구니</button><br>
            <button type='button' id='btn_ordering' class="btn btn-info btn-sm" onclick="cart_ajax(${contentsno })">바로 구매</button>  
          </td>
          <td style='vertical-align: middle; text-align: center;'>
            <A href="/contents/map.do?cateno=${cateno }&contentsno=${contentsno}&word=${param.word }" title="지도"><IMG src="/contents/images/map.png" class="icon"></A>
            <A href="/contents/youtube.do?cateno=${cateno }&contentsno=${contentsno}&word=${param.word }" title="Youtube"><IMG src="/contents/images/youtube.png" class="icon"></A>
            <A href="/contents/update_text.do?cateno=${cateno }&contentsno=${contentsno}&word=${param.word }" title="글 수정"><IMG src="/contents/images/update.png" class="icon"></A>
            <A href="/contents/update_file.do?cateno=${cateno }&contentsno=${contentsno}&word=${param.word }" title="파일 수정"><IMG src="/contents/images/update_file.png" class="icon"></A>
            <A href="/contents/delete.do?cateno=${cateno }&contentsno=${contentsno}&word=${param.word }" title="삭제"><IMG src="/contents/images/delete.png" class="icon"></A>
          </td>
        </tr>
      </c:forEach>
      
    </tbody>
  </table>
  
  <!-- 페이지 목록 출력 부분 시작 -->
  <DIV class='bottom_menu'>${paging }</DIV> <%-- 페이지 리스트 --%>
  <!-- 페이지 목록 출력 부분 종료 -->
  
</DIV>

 
<jsp:include page="../menu/bottom.jsp" />
</body>
 
</html>

