package dev.mvc.contents;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.cate.CateProcInter;
import dev.mvc.cate.CateVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class ContentsCont {
  @Autowired
  @Qualifier("dev.mvc.cate.CateProc") 
  private CateProcInter cateProc;
  
  @Autowired
  @Qualifier("dev.mvc.contents.ContentsProc") 
  private ContentsProcInter contentsProc;
  
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc = null;
  
  public ContentsCont () {
    System.out.println("-> ContentsCont created.");
  }
  
  /**
   * 새로고침 방지, POST -> POST 정보 삭제 -> GET -> msg.jsp
   * @return
   */
  @RequestMapping(value="/contents/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  
  
  // 등록 폼
  // http://localhost:9091/contents/create.do?cateno=1
  @RequestMapping(value="/contents/create.do", method = RequestMethod.GET)
  public ModelAndView create(int cateno) {
//  public ModelAndView create(HttpServletRequest request,  int cateno) {
    ModelAndView mav = new ModelAndView();

    CateVO cateVO = this.cateProc.read(cateno);
    mav.addObject("cateVO", cateVO);
//    request.setAttribute("cateVO", cateVO);
    
    // 커뮤니티(공지사항, 게시판, 자료실, 갤러리,  Q/A...)글 등록 
    // mav.setViewName("/contents/create"); // /webapp/WEB-INF/views/contents/create.jsp
    
    // 쇼핑몰의 상품 정보 등록
    mav.setViewName("/contents/create_product"); // /webapp/WEB-INF/views/contents/create_product.jsp
    
    return mav;
  }
  
  /**
   * 등록 처리 http://localhost:9091/contents/create.do
   * 
   * @return
   */
  @RequestMapping(value = "/contents/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpSession session, HttpServletRequest request, ContentsVO contentsVO) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session)) {
      int adminno = (int)session.getAttribute("adminno");
      contentsVO.setAdminno(adminno);
      
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 시작
      // ------------------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image
      String file1saved = "";   // 저장된 파일명, image
      String thumb1 = "";     // preview image

      // 기준 경로 확인
      String user_dir = System.getProperty("user.dir"); // 시스템 제공
      // System.out.println("-> User dir: " + user_dir);
      //  --> User dir: C:\kd\ws_java\resort_v1sbm3c
      
      // 파일 접근임으로 절대 경로 지정, static 폴더 지정
      // 완성된 경로 C:/kd/ws_java/resort_v1sbm3c/src/main/resources/static/contents/storage
      String upDir =  user_dir + "/src/main/resources/static/contents/storage/"; // 절대 경로
      // System.out.println("-> upDir: " + upDir);
      
      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = contentsVO.getFile1MF();
      
      file1 = Tool.getFname(mf.getOriginalFilename()); // 원본 순수 파일명 산출
      System.out.println("-> file1: " + file1);
      
      long size1 = mf.getSize();  // 파일 크기
      
      if (size1 > 0) { // 파일 크기 체크
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        file1saved = Upload.saveFileSpring(mf, upDir); 
        
        if (Tool.isImage(file1saved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
          thumb1 = Tool.preview(upDir, file1saved, 200, 150); 
        }
        
      }    
      
      contentsVO.setFile1(file1);   // 순수 원본 파일명
      contentsVO.setFile1saved(file1saved); // 저장된 파일명(파일명 중복 처리)
      contentsVO.setThumb1(thumb1);      // 원본이미지 축소판
      contentsVO.setSize1(size1);  // 파일 크기
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 종료
      // ------------------------------------------------------------------------------
      
      // Call By Reference: 메모리 공유, Hashcode 전달
      int cnt = this.contentsProc.create(contentsVO); 
      
      // ------------------------------------------------------------------------------
      // PK의 return
      // ------------------------------------------------------------------------------
      // System.out.println("--> contentsno: " + contentsVO.getContentsno());
      // mav.addObject("contentsno", contentsVO.getContentsno()); // redirect parameter 적용
      // ------------------------------------------------------------------------------
      
      if (cnt == 1) {
          mav.addObject("code", "create_success");
          // cateProc.increaseCnt(contentsVO.getCateno()); // 글수 증가
      } else {
          mav.addObject("code", "create_fail");
      }
      mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
      
      // System.out.println("--> cateno: " + contentsVO.getCateno());
      // redirect시에 hidden tag로 보낸것들이 전달이 안됨으로 request에 다시 저장
      mav.addObject("cateno", contentsVO.getCateno()); // redirect parameter 적용

      // mav.setViewName("/contents/msg"); // POST -> msg.jsp -> top.do 파일이 호출이안됨
      
      mav.addObject("url", "/contents/msg"); // msg.jsp, redirect parameter 적용
      mav.setViewName("redirect:/contents/msg.do"); // GET
    } else {
      mav.addObject("url", "/admin/login_need"); // login_need.jsp, redirect parameter 적용
      mav.setViewName("redirect:/contents/msg.do"); // GET
    }
    

    return mav; // forward
  }
  
//  /**
//   * 모든 레코드 목록, http://localhost:9091/contents/list_by_cateno_search_paging.do?cateno=1
//   * @return
//   */
//  @RequestMapping(value="/contents/list_by_cateno_search_paging.do", method=RequestMethod.GET)
//  public ModelAndView list_by_cateno(int cateno) {
//    ModelAndView mav = new ModelAndView();
//    
//    CateVO cateVO = this.cateProc.read(cateno);
//    mav.addObject("cateVO", cateVO);
//    
//    ArrayList<ContentsVO> list = this.contentsProc.list_by_cateno(cateno);
//    mav.addObject("list", list);
//    // request.setAttribute("list", list);
//    
//    // System.out.println("-> list size: " + list.size());
//    
//    mav.setViewName("/contents/list_by_cateno"); // /webapp/WEB-INF/views/contents/list_by_cateno.jsp
//    
//    return mav;
//  }
  
  /**
   * 조회, http://localhost:9091/contents/read.do?cateno=12&contentsno=1
   * @return
   */
  @RequestMapping(value="/contents/read.do", method=RequestMethod.GET)
  public ModelAndView read(int cateno, int contentsno) {
    
    ModelAndView mav = new ModelAndView();
    
    CateVO cateVO = this.cateProc.read(cateno);
    mav.addObject("cateVO", cateVO);
    
    ContentsVO contentsVO = this.contentsProc.read(contentsno);
    
    long size1 = contentsVO.getSize1();
    contentsVO.setSize1_label(Tool.unit(size1)); // 93848 -> 92 KB
        
    mav.addObject("contentsVO", contentsVO);
    
    mav.setViewName("/contents/read"); // /webapp/WEB-INF/views/contents/read.jsp
    
    return mav;
  }
  
  // MAP 등록/수정/삭제 폼
  // http://localhost:9091/contents/map.do?cateno=15&contentsno=6
  @RequestMapping(value="/contents/map.do", method = RequestMethod.GET)
  public ModelAndView map(int cateno, int contentsno) {
    ModelAndView mav = new ModelAndView();

    CateVO cateVO = this.cateProc.read(cateno);
    mav.addObject("cateVO", cateVO);
    
    ContentsVO contentsVO = this.contentsProc.read(contentsno);
    mav.addObject("contentsVO", contentsVO);
    
    mav.setViewName("/contents/map"); // /webapp/WEB-INF/views/contents/map.jsp
    
    return mav;
  }
  
  // MAP 등록/수정/삭제 처리
  @RequestMapping(value="/contents/map.do", method = RequestMethod.POST)
  public ModelAndView map_update(int cateno, int contentsno, String map) {
    // System.out.println("-> contentsno: " + contentsno);
    
    ModelAndView mav = new ModelAndView();

    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("contentsno", contentsno);
    hashMap.put("map", map);
    
    this.contentsProc.map(hashMap);
    
    mav.setViewName("redirect:/contents/read.do?cateno=" + cateno + "&contentsno=" + contentsno); 
    // /webapp/WEB-INF/views/contents/read.jsp
    
    return mav;
  }
  
  // Youtube 등록/수정/삭제 폼
  // http://localhost:9091/contents/youtube.do?cateno=15&contentsno=6
  @RequestMapping(value="/contents/youtube.do", method = RequestMethod.GET)
  public ModelAndView youtube(int cateno, int contentsno) {
    ModelAndView mav = new ModelAndView();

    CateVO cateVO = this.cateProc.read(cateno);
    mav.addObject("cateVO", cateVO);
    
    ContentsVO contentsVO = this.contentsProc.read(contentsno);
    mav.addObject("contentsVO", contentsVO);
    
    mav.setViewName("/contents/youtube"); // /webapp/WEB-INF/views/contents/youtube.jsp
    
    return mav;
  }
  
  // Youtube 등록/수정/삭제 처리
  @RequestMapping(value="/contents/youtube.do", method = RequestMethod.POST)
  public ModelAndView youtube_update(int cateno, int contentsno, String youtube) {
    ModelAndView mav = new ModelAndView();

    
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("contentsno", contentsno);
    hashMap.put("youtube", youtube);
    
    this.contentsProc.youtube(hashMap);
    
    mav.setViewName("redirect:/contents/read.do?cateno=" + cateno + "&contentsno=" + contentsno); 
    // /webapp/WEB-INF/views/contents/read.jsp
    
    return mav;
  }

//  /**
//   * 검색 레코드 목록, http://localhost:9091/contents/list_by_cateno_search_paging.do?cateno=1
//   * @return
//   */
//  @RequestMapping(value="/contents/list_by_cateno_search_paging.do", method=RequestMethod.GET)
//  public ModelAndView list_by_cateno_search(int cateno, String word) {
//    ModelAndView mav = new ModelAndView();
//    
//    CateVO cateVO = this.cateProc.read(cateno);
//    mav.addObject("cateVO", cateVO);
//    
//    HashMap<String, Object> hashMap = new HashMap<String, Object>();
//    hashMap.put("cateno", cateno);
//    hashMap.put("word", word);
//    
//    ArrayList<ContentsVO> list = this.contentsProc.list_by_cateno_search(hashMap);
//    mav.addObject("list", list);
//    
//    mav.setViewName("/contents/list_by_cateno_search"); // /webapp/WEB-INF/views/contents/list_by_cateno_search.jsp
//    
//    return mav;
//  }

  /**
   * cate + contents INNER JOIN 목록, http://localhost:9091/contents/list_all.do
   * @return
   */
  @RequestMapping(value="/contents/list_all.do", method=RequestMethod.GET)
  public ModelAndView list_all() {
    ModelAndView mav = new ModelAndView();
    
    ArrayList<CateContentsVO> list = this.contentsProc.list_all();
    mav.addObject("list", list);
    
    mav.setViewName("/contents/list_all"); // /webapp/WEB-INF/views/contents/list_all.jsp
    
    return mav;
  }
  
//  /**
//   * 목록 + 검색 + 페이징 지원
//   * http://localhost:9090/contents/list_by_cateno_search_paging.do?cateno=1&word=스위스&now_page=1
//   * 
//   * @param cateno
//   * @param word
//   * @param now_page
//   * @return
//   */
//  @RequestMapping(value = "/contents/list_by_cateno_search_paging.do", method = RequestMethod.GET)
//  public ModelAndView list_by_cateno_search_paging(@RequestParam(value = "cateno", defaultValue = "1") int cateno,
//                                                                     @RequestParam(value = "word", defaultValue = "") String word,
//                                                                     @RequestParam(value = "now_page", defaultValue = "1") int now_page) {
//    System.out.println("--> now_page: " + now_page);
//
//    ModelAndView mav = new ModelAndView();
//
//    // 숫자와 문자열 타입을 저장해야함으로 Obejct 사용
//    HashMap<String, Object> map = new HashMap<String, Object>();
//    map.put("cateno", cateno); // #{cateno}
//    map.put("word", word); // #{word}
//    map.put("now_page", now_page); // 페이지에 출력할 레코드의 범위를 산출하기위해 사용
//
//    // 검색 목록
//    ArrayList<ContentsVO> list = contentsProc.list_by_cateno_search_paging(map);
//    mav.addObject("list", list);
//
//    // 검색된 레코드 갯수
//    int search_count = contentsProc.search_count(map);
//    mav.addObject("search_count", search_count);
//
//    CateVO cateVO = cateProc.read(cateno);
//    mav.addObject("cateVO", cateVO);
//
//    /*
//     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
//     * 18 19 20 [다음]
//     * @param cateno 카테고리번호
//     * @param search_count 검색(전체) 레코드수
//     * @param now_page 현재 페이지
//     * @param word 검색어
//     * @return 페이징용으로 생성된 HTML/CSS tag 문자열
//     */
//    String paging = contentsProc.pagingBox(cateno, search_count, now_page, word);
//    mav.addObject("paging", paging);
//
//    // mav.addObject("now_page", now_page);
//    
//    mav.setViewName("/contents/list_by_cateno_search_paging");  // /contents/list_by_cateno_search_paging.jsp
//
//    return mav;
//  }
  
  /**
   * 목록 + 검색 + 페이징 지원 + Cookie
   * http://localhost:9090/contents/list_by_cateno_search_paging.do?cateno=1&word=스위스&now_page=1
   * 
   * @param cateno
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/contents/list_by_cateno_search_paging.do", method = RequestMethod.GET)
  public ModelAndView list_by_cateno_search_paging_cookie(
                                                                  HttpServletRequest request,
                                                                  @RequestParam(value = "cateno", defaultValue = "1") int cateno,
                                                                  @RequestParam(value = "word", defaultValue = "") String word,
                                                                  @RequestParam(value = "now_page", defaultValue = "1") int now_page) {
    // System.out.println("--> now_page: " + now_page);

    ModelAndView mav = new ModelAndView();

    // 숫자와 문자열 타입을 저장해야함으로 Obejct 사용
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("cateno", cateno); // #{cateno}
    map.put("word", word); // #{word}
    map.put("now_page", now_page); // 페이지에 출력할 레코드의 범위를 산출하기위해 사용

    // 검색 목록
    ArrayList<ContentsVO> list = contentsProc.list_by_cateno_search_paging(map);
    mav.addObject("list", list);

    // 검색된 레코드 갯수
    int search_count = contentsProc.search_count(map);
    mav.addObject("search_count", search_count);

    CateVO cateVO = cateProc.read(cateno);
    mav.addObject("cateVO", cateVO);

    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param cateno 카테고리번호
     * @param search_count 검색(전체) 레코드수
     * @param now_page 현재 페이지
     * @param word 검색어
     * @return 페이징용으로 생성된 HTML/CSS tag 문자열
     */
    String paging = contentsProc.pagingBox(cateno, search_count, now_page, word);
    mav.addObject("paging", paging);

    // mav.addObject("now_page", now_page);
    
    // 로그인 Cookie 지원
    // mav.setViewName("/contents/list_by_cateno_search_paging_cookie");  // /contents/list_by_cateno_search_paging_cookie.jsp ★
    
    // 로그인 Cookie + 쇼핑카트
    mav.setViewName("/contents/list_by_cateno_search_paging_cookie_cart");  // /contents/list_by_cateno_search_paging_cookie_cart.jsp ★
    
    // -------------------------------------------------------------------------------
    // 쇼핑 카트 장바구니에 상품 등록전 로그인 폼 출력 관련 쿠기  
    // -------------------------------------------------------------------------------
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_id = ""; // id 저장
    String ck_id_save = ""; // id 저장 여부를 체크
    String ck_passwd = ""; // passwd 저장
    String ck_passwd_save = ""; // passwd 저장 여부를 체크

    if (cookies != null) {  // Cookie 변수가 있다면
      for (int i=0; i < cookies.length; i++){
        cookie = cookies[i]; // 쿠키 객체 추출
        
        if (cookie.getName().equals("ck_id")){
          ck_id = cookie.getValue();                                 // Cookie에 저장된 id
        }else if(cookie.getName().equals("ck_id_save")){
          ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
        }else if (cookie.getName().equals("ck_passwd")){
          ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
        }else if(cookie.getName().equals("ck_passwd_save")){
          ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
        }
      }
    }
    
    System.out.println("-> ck_id: " + ck_id);
    
    mav.addObject("ck_id", ck_id); 
    mav.addObject("ck_id_save", ck_id_save);
    mav.addObject("ck_passwd", ck_passwd);
    mav.addObject("ck_passwd_save", ck_passwd_save);
    // -------------------------------------------------------------------------------
    

    return mav;
  }
  
  /**
   * Grid 형태의 화면 구성 http://localhost:9091/contents/list_by_cateno_grid.do
   * 
   * @return
   */
  @RequestMapping(value = "/contents/list_by_cateno_grid.do", method = RequestMethod.GET)
  public ModelAndView list_by_cateno_grid(int cateno) {
    ModelAndView mav = new ModelAndView();
    
    CateVO cateVO = this.cateProc.read(cateno);
    mav.addObject("cateVO", cateVO);
    
    ArrayList<ContentsVO> list = this.contentsProc.list_by_cateno(cateno);
    mav.addObject("list", list);

    // 테이블 이미지 기반, /webapp/WEB-INF/views/contents/list_by_cateno_grid.jsp
    mav.setViewName("/contents/list_by_cateno_grid");

    return mav; // forward
  }
  
  /**
   * 수정 폼
   * http://localhost:9091/contents/update_text.do?contentsno=1
   * 
   * @return
   */
  @RequestMapping(value = "/contents/update_text.do", method = RequestMethod.GET)
  public ModelAndView update_text(int contentsno) {
    ModelAndView mav = new ModelAndView();
    
    ContentsVO contentsVO = this.contentsProc.read(contentsno);
    mav.addObject("contentsVO", contentsVO);
    
    CateVO cateVO = this.cateProc.read(contentsVO.getCateno());
    mav.addObject("cateVO", cateVO);
    
    mav.setViewName("/contents/update_text"); // /WEB-INF/views/contents/update_text.jsp
    // String content = "장소:\n인원:\n준비물:\n비용:\n기타:\n";
    // mav.addObject("content", content);

    return mav; // forward
  }
  
  /**
   * 수정 처리
   * http://localhost:9091/contents/update_text.do?contentsno=1
   * 
   * @return
   */
  @RequestMapping(value = "/contents/update_text.do", method = RequestMethod.POST)
  public ModelAndView update_text(HttpSession session, ContentsVO contentsVO) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session)) {
      int cnt = this.contentsProc.update_text(contentsVO);
      
      // URL에 파라미터의 전송
      // mav.setViewName("redirect:/contents/read.do?contentsno=" + contentsVO.getContentsno() + "&cateno=" + contentsVO.getCateno());             

      // mav 객체 이용
      mav.addObject("contentsno", contentsVO.getContentsno());
      mav.addObject("cateno", contentsVO.getCateno());
      mav.setViewName("redirect:/contents/read.do");      
    } else {
      mav.addObject("url", "/admin/login_need"); // login_need.jsp, redirect parameter 적용
      mav.setViewName("redirect:/contents/msg.do"); // GET
    }
        
    return mav; // forward
  }
  
  /**
   * 파일 수정 폼
   * http://localhost:9091/contents/update_file.do?contentsno=1
   * 
   * @return
   */
  @RequestMapping(value = "/contents/update_file.do", method = RequestMethod.GET)
  public ModelAndView update_file(int contentsno) {
    ModelAndView mav = new ModelAndView();
    
    ContentsVO contentsVO = this.contentsProc.read(contentsno);
    mav.addObject("contentsVO", contentsVO);
    
    CateVO cateVO = this.cateProc.read(contentsVO.getCateno());
    mav.addObject("cateVO", cateVO);
    
    mav.setViewName("/contents/update_file"); // /WEB-INF/views/contents/update_file.jsp

    return mav; // forward
  }
  
  /**
   * 파일 수정 처리 http://localhost:9091/contents/update_file.do
   * 
   * @return
   */
  @RequestMapping(value = "/contents/update_file.do", method = RequestMethod.POST)
  public ModelAndView update_file(HttpSession session, ContentsVO contentsVO) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session)) {
      // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
      ContentsVO contentsVO_old = contentsProc.read(contentsVO.getContentsno());
      
      int cnt = 0;

      // -------------------------------------------------------------------
      // 파일 삭제 코드 시작
      // -------------------------------------------------------------------
      String file1saved = contentsVO_old.getFile1saved();  // 실제 저장된 파일명
      String thumb1 = contentsVO_old.getThumb1();       // 실제 저장된 preview 이미지 파일명
      long size1 = 0;
      boolean sw = false;
          
      // 완성된 경로 C:/kd/ws_java/resort_v1sbm3c/src/main/resources/static/contents/storage/
      String upDir =  System.getProperty("user.dir") + "/src/main/resources/static/contents/storage/"; // 절대 경로

      sw = Tool.deleteFile(upDir, file1saved);  // 실제 저장된 파일삭제
      sw = Tool.deleteFile(upDir, thumb1);     // preview 이미지 삭제
      // -------------------------------------------------------------------
      // 파일 삭제 종료 시작
      // -------------------------------------------------------------------
          
      // -------------------------------------------------------------------
      // 파일 전송 코드 시작
      // -------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image

      // 완성된 경로 C:/kd/ws_java/resort_v1sbm3c/src/main/resources/static/contents/storage/
      // String upDir =  System.getProperty("user.dir") + "/src/main/resources/static/contents/storage/"; // 절대 경로
          
      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = contentsVO.getFile1MF();
          
      file1 = mf.getOriginalFilename(); // 원본 파일명
      size1 = mf.getSize();  // 파일 크기
          
      if (size1 > 0) { // 파일 크기 체크
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        file1saved = Upload.saveFileSpring(mf, upDir); 
        
        if (Tool.isImage(file1saved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 250, height: 200
          thumb1 = Tool.preview(upDir, file1saved, 250, 200); 
        }
        
      } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
        file1="";
        file1saved="";
        thumb1="";
        size1=0;
      }
          
      contentsVO.setFile1(file1);
      contentsVO.setFile1saved(file1saved);
      contentsVO.setThumb1(thumb1);
      contentsVO.setSize1(size1);
      // -------------------------------------------------------------------
      // 파일 전송 코드 종료
      // -------------------------------------------------------------------
          
      cnt = this.contentsProc.update_file(contentsVO); // Oracle 처리

      mav.addObject("contentsno", contentsVO.getContentsno());
      mav.addObject("cateno", contentsVO.getCateno());
      mav.setViewName("redirect:/contents/read.do"); // request -> param으로 접근 전환
                
    } else {
      mav.addObject("url", "/admin/login_need"); // login_need.jsp, redirect parameter 적용
      mav.setViewName("redirect:/contents/msg.do"); // GET
    }

    return mav; // forward
  }   

  /**
   * 삭제 폼
   * @param contentsno
   * @return
   */
  @RequestMapping(value="/contents/delete.do", method=RequestMethod.GET )
  public ModelAndView delete(int contentsno) { 
    ModelAndView mav = new  ModelAndView();
    
    // 삭제할 정보를 조회하여 확인
    ContentsVO contentsVO = this.contentsProc.read(contentsno);
    mav.addObject("contentsVO", contentsVO);
    
    CateVO cateVO = this.cateProc.read(contentsVO.getCateno());
    mav.addObject("cateVO", cateVO);
    
    mav.setViewName("/contents/delete");  // /webapp/WEB-INF/views/contents/delete.jsp
    
    return mav; 
  }
  
  /**
   * 삭제 처리 http://localhost:9091/contents/delete.do
   * 
   * @return
   */
  @RequestMapping(value = "/contents/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(int contentsno, String word,
                                        @RequestParam(value="now_page", defaultValue="1") int now_page) {
    ModelAndView mav = new ModelAndView();
    
    int cnt = 0;
    // -------------------------------------------------------------------
    // 파일 삭제 코드 시작
    // -------------------------------------------------------------------
    // 삭제할 파일 정보를 읽어옴.
    ContentsVO contentsVO = contentsProc.read(contentsno);
        
    String file1saved = contentsVO.getFile1saved();
    String thumb1 = contentsVO.getThumb1();
    long size1 = 0;
    boolean sw = false;
        
    // 완성된 경로 C:/kd/ws_java/resort_v1sbm3c/src/main/resources/static/contents/storage/
    String upDir =  System.getProperty("user.dir") + "/src/main/resources/static/contents/storage/"; // 절대 경로

    sw = Tool.deleteFile(upDir, file1saved);  // 실제 저장된 파일삭제
    sw = Tool.deleteFile(upDir, thumb1);     // preview 이미지 삭제
    // -------------------------------------------------------------------
    // 파일 삭제 종료 시작
    // -------------------------------------------------------------------
        
    cnt = this.contentsProc.delete(contentsno); // DBMS 삭제
        
    // -------------------------------------------------------------------------------------
    // 마지막 페이지의 마지막 레코드 삭제시의 페이지 번호 -1 처리
    // -------------------------------------------------------------------------------------    
    HashMap<String, Object> page_map = new HashMap<String, Object>();
    page_map.put("cateno", contentsVO.getCateno());
    page_map.put("word", word);
    // 마지막 페이지의 마지막 10번째 레코드를 삭제후
    // 하나의 페이지가 3개의 레코드로 구성되는 경우 현재 9개의 레코드가 남아 있으면
    // 페이지수를 4 -> 3으로 감소 시켜야함, 마지막 페이지의 마지막 레코드 삭제시 나머지는 0 발생
    if (contentsProc.search_count(page_map) % Contents.RECORD_PER_PAGE == 0) {
      now_page = now_page - 1;
      if (now_page < 1) {
        now_page = 1; // 시작 페이지
      }
    }
    // -------------------------------------------------------------------------------------

    mav.addObject("cateno", contentsVO.getCateno());
    mav.addObject("now_page", now_page);
    mav.setViewName("redirect:/contents/list_by_cateno_search_paging.do"); 
    
    return mav;
  }   
  
  
}


