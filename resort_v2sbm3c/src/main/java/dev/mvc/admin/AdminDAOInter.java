package dev.mvc.admin;

import java.util.HashMap;

import dev.mvc.member.MemberVO;

public interface AdminDAOInter {
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
  
}
