<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">

<script type="text/javascript">
  $(function() {
	  // $('#panel_update').hide();
	});

  // 수정
  function read_update_ajax(cateno) {
	  $('#panel_create').hide();
	  $('#panel_update').show();
	  
    // alert(cateno);

    var params = "";
    params = 'cateno=' + cateno; // 공백이 값으로 있으면 안됨.
    
     // params = $('#frm').serialize(); // 직렬화, 폼의 데이터를 키와 값의 구조로 조합
    // alert('params: ' + params);
    // return;
    $.ajax({
      url: '/cate/read_ajax_json.do',
      type: 'get',  // get, post
      cache: false, // 응답 결과 임시 저장 취소
      async: true,  // true: 비동기 통신
      dataType: 'json', // 응답 형식: json, html, xml...
      data: params,      // 데이터
      success: function(rdata) { // 응답이 온경우
        let frm = $('#frm_update');

        // frm폼의 id가 cateno인 태그에 rdata json 객체의 cateno 변수의 값을 저장 
        $('#cateno', frm).val(rdata.cateno);
        $('#cnt', frm).val(rdata.cnt);
        // frm폼의 id가 name인 태그에 rdata json 객체의 name 변수의 값을 저장 
        $('#name', frm).val(rdata.name);
        // frm폼의 id가 seqno인 태그에 rdata json 객체의 seqno 변수의 값을 저장 
        $('#seqno', frm).val(rdata.seqno);

        $('#panel_update_animation').hide(); // 태그 감추기
      },
      error: function(request, status, error) { // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우
        console.log(error);
      }
    });
  
    $('#panel_update_animation').css('text-align', 'center'); // style 접근
    $('#panel_update_animation').html("<img src='/cate/images/ani01.gif' style='width: 3%;'>");
    $('#panel_update_animation').show(); // 숨겨진 태그의 출력
    	  
	}

  // 삭제
  function read_delete_ajax(cateno) {
    $('#panel_delete_msg').html('삭제 정보를 읽어오는 중입니다.'); // 메시지 출력
    $('#frm_delete_name').html('카테고리: '); // 카테고리 이름 출력
    
	  $('#panel_create').hide();
	  $('#panel_update').hide();
	  $('#panel_delete').show();
	  	  
    // alert(cateno);

    var params = "";
    params = 'cateno=' + cateno; // 공백이 값으로 있으면 안됨.
    
     // params = $('#frm').serialize(); // 직렬화, 폼의 데이터를 키와 값의 구조로 조합
    // alert('params: ' + params);
    // return;
    $.ajax({
      url: '/cate/read_ajax_json_fk.do',
      type: 'get',  // get, post
      cache: false, // 응답 결과 임시 저장 취소
      async: true,  // true: 비동기 통신
      dataType: 'json', // 응답 형식: json, html, xml...
      data: params,      // 데이터
      success: function(rdata) { // 응답이 온경우
        let frm = $('#frm_delete');

        // <div id="panel_delete_msg" class="msg_warning">카테고리를 삭제하면 복구 할 수 없습니다.</div>
        let msg ="";
        if (rdata.count_by_cateno == 0) {
        	msg = "카테고리를 삭제하면 복구 할 수 없습니다.";
          $('#btn_submit', frm).show(); // 삭제 버튼 출력
        } else {
          msg = "「" + rdata.name + "」에 관련된 자료가 " + rdata. count_by_cateno + "건이 발견되었습니다.<br>";
          msg += "관련자료를 모두 삭제해야 현재 카테고리 「" + rdata.name + "」 를 삭제 할 수 있습니다.";
          $('#btn_submit', frm).hide(); // 삭제 버튼 감추기 
        }

        $('#panel_delete_msg').html(msg); // 메시지 출력
        
        // frm폼의 id가 cateno인 태그에 rdata json 객체의 cateno 변수의 값을 저장 
        $('#cateno', frm).val(rdata.cateno);
        $('#frm_delete_name').html('카테고리: ' + rdata.name);

        $('#panel_delete_animation').hide(); // 태그 감추기
      },
      error: function(request, status, error) { // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우
        console.log(error);
      }
    });
  
    $('#panel_delete_animation').css('text-align', 'center'); // style 접근
    $('#panel_delete_animation').html("<img src='/cate/images/ani01.gif' style='width: 3%;'>");
    $('#panel_delete_animation').show(); // 숨겨진 태그의 출력
    	  
	}

  
  // 수정 취소, 삭제 취소
	function cancel() {
	  $('#panel_create').show();
	  $('#panel_update').hide();
	  $('#panel_delete').hide();
  }
</script>
    
</head> 
 
<body>
<c:import url="/menu/top.do" />
 
<DIV class='title_line'>전체 카테고리</DIV>

<DIV class='content_body'>
  <%-- 등록 --%>
  <DIV id='panel_create' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <FORM name='frm_create' id='frm_create' method='POST' action='./create.do'>
      <label>카테고리 이름</label>
      <input type='text' name='name' id='name' value='' required="required" style='width: 25%;' autofocus="autofocus">
      
      <label>출력 순서</label>
      <input type='number' name='seqno' id="seqno" value='1' required="required" min="1" max="1000" step="1" style='width: 5%;'>
  
      <button type="submit" id='submit'>등록</button>
      <button type="button" onclick="cancel();">취소</button>
    </FORM>
  </DIV>
  
  <%-- 수정 --%>
  <DIV id='panel_update' style='display: none; padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <FORM name='frm_update' id='frm_update' method='POST' action='./read_update.do'>
      <input type="hidden" name="cateno" id="cateno" value="${cateVO.cateno}">
      <input type="hidden" name="cnt" id="cnt" value="${cateVO.cnt }">
      
      <label>카테고리 이름</label>
      <input type='text' name='name' id="name" value='' required="required" style='width: 25%;' autofocus="autofocus">

      <label>출력 순서</label>
      <input type='number' name='seqno' id="seqno" value='' required="required" min="1" max="1000" step="1" style='width: 5%;'>
  
      <button type="submit" id='submit'>수정</button>
      <button type="button" onclick="cancel();">취소</button>
      
      <span id='panel_update_animation' style='display: none;'></span>
    </FORM>
  </DIV>

  <%-- 삭제 --%>
  <DIV id='panel_delete' style='display: none; padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <div id="panel_delete_msg" class="msg_warning">삭제 정보를 읽어오는 중입니다.</div>
    <FORM name='frm_delete' id='frm_delete' method='POST' action='./read_delete.do'>
      <!-- 삭제할 글 번호 -->
      <input type='hidden' name='cateno' id='cateno' value="">
      
      <span id="frm_delete_name"></span> <%-- 삭제할 카테고리 이름 --%>
       
      <button type="submit" id='btn_submit'>삭제</button>
      <button type="button" onclick="cancel()">취소</button>
      
      <span id='panel_delete_animation' style='display: none;'></span>
    </FORM>
  </DIV>
  
  <TABLE class='table table-hover'>
    <colgroup>
      <col style='width: 10%;'/>
      <col style='width: 35%;'/>
      <col style='width: 10%;'/>    
      <col style='width: 15%;'/>
      <col style='width: 15%;'/>
      <col style='width: 15%;'/>
    </colgroup>
   
    <thead>  
    <TR>
      <TH class="th_bs">순서</TH>
      <TH class="th_bs">카테고리 이름</TH>
      <TH class="th_bs">자료수</TH>
      <TH class="th_bs">등록일</TH>
      <TH class="th_bs">수정일</TH>
      <TH class="th_bs">기타</TH>
    </TR>
    </thead>
    
    <tbody>
    <c:forEach var="cateVO" items="${list }">
      <c:set var="cateno" value="${cateVO.cateno }" />
      <c:set var="name" value="${cateVO.name }" />
      <c:set var="cnt" value="${cateVO.cnt }" />
      <c:set var="rdate" value="${cateVO.rdate.substring(0, 16) }" />
      <c:set var="udate" value="${cateVO.udate.substring(0, 16) }" />
      <c:set var="seqno" value="${cateVO.seqno }" />
      <c:set var="visible" value="${cateVO.visible }" />

      <TR>
        <TD class="td_bs">${seqno}</TD>
        <TD class="td_bs_left"><A href="/contents/list_by_cateno_search_paging.do?cateno=${cateno }">${name }</A></TD>
        <TD class="td_bs">${cnt }</TD>
        <TD class="td_bs">${rdate}</TD>
        <TD class="td_bs">${udate }</TD>
        <TD class="td_bs">
          <A href="../contents/create.do?cateno=${cateno}" title="${name } 등록"><IMG src="/cate/images/append.png" class="icon"></A>
          <A href="javascript: read_update_ajax(${cateno })" title="${name } 수정"><IMG src="/cate/images/update.png" class="icon"></A>
          <A href="javascript: read_delete_ajax(${cateno})" title="${name } 삭제"><IMG src="/cate/images/delete.png" class="icon"></A>
          <A href="./update_seqno_up.do?cateno=${cateno}" title="출력 순서 올림"><IMG src="/cate/images/up.png" class="icon"></A>
          <A href="./update_seqno_down.do?cateno=${cateno}" title="출력 순서 내림"><IMG src="/cate/images/down.png" class="icon"></A>
          
          <c:choose>
            <c:when test="${visible == 'Y' }">
              <A href="./update_visible_n.do?cateno=${cateno }" title="출력 모드 N로 변경"><IMG src="/cate/images/show.png" class="icon"></A>
            </c:when>
            <c:when test="${visible == 'N' }">
              <A href="./update_visible_y.do?cateno=${cateno }" title="출력 모드 Y로 변경"><IMG src="/cate/images/hide.png" class="icon"></A>            </c:when>
          </c:choose>
          
        </TD>   
      </TR> 
      
    </c:forEach>
    </tbody>
   
  </TABLE>
</DIV>

 
<jsp:include page="../menu/bottom.jsp" />
</body>
 
</html>
 