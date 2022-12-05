package dev.mvc.admin;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.member.MemberVO;

@Component("dev.mvc.admin.AdminProc") // Autowired annotation에 사용하는 이름
public class AdminProc implements AdminProcInter {
  @Autowired
  private AdminDAOInter adminDAO; // 스프링이 자동 구현한 DAO 객체 자동 할당

  @Override
  public int login(HashMap<String, Object> map) {
    int cnt = this.adminDAO.login(map);
    return cnt;
  }

  @Override
  public AdminVO readById(String id) {
    AdminVO adminVO = this.adminDAO.readById(id);
    return adminVO;
  }
  
  @Override
  public boolean isAdmin(HttpSession session) {
    boolean admin_sw = false; // 로그인하지 않은 것으로 초기화
    int admin_grade = 99;
    
    // System.out.println("-> grade: " + session.getAttribute("admin_grade"));
    if (session != null) {
      String admin_id = (String)session.getAttribute("admin_id");
      if (session.getAttribute("admin_grade") != null) {
        admin_grade = (int)session.getAttribute("admin_grade");
      }
      
      if (admin_id != null && admin_grade <= 10){ // 관리자
        admin_sw = true;  // 로그인 한 경우
      }
    }
    
    return admin_sw;
  }  
  
}

