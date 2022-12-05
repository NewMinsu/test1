package dev.mvc.admin;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import dev.mvc.member.MemberVO;

public interface AdminProcInter {
  /**
   * 관리자 로그인 처리
   * @param map
   * @return
   */
  public int login(HashMap<String, Object> map);
  
  /**
   * id로 관리자 정보 조회
   * @param id
   * @return
   */
  public AdminVO readById(String id);
  
  /**
   * 로그인된 관리자인지 검사합니다.
   * @param session
   * @return true: 관리자
   */
  public boolean isAdmin(HttpSession session);   
   
  
}
