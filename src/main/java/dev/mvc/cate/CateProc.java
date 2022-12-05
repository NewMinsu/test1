package dev.mvc.cate;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import dev.mvc.contents.ContentsVO;

@Component("dev.mvc.cate.CateProc")
public class CateProc implements CateProcInter {
  // CateDAOInter interface 만 존재하고 구현 class는 존재하지 않음.
  // interface는 객체를 만들 수 없고 할당만 받을 수 있음.
  
  @Autowired
  private CateDAOInter cateDAO;
  
  public CateProc() {
    // System.out.println("-> CateProc created.");
    // System.out.println("-> CateProc: " + (cateDAO == null));
  }
  
  @Override
  public int create(CateVO cateVO) {
    int cnt = this.cateDAO.create(cateVO); // MyBATIS가 처리한 레코드 갯수가 return됨
    
    // System.out.println("-> CateProc create: " + (cateDAO == null));
    return cnt;
  }

  @Override
  public ArrayList<CateVO> list_all() {
    ArrayList<CateVO> list = this.cateDAO.list_all();
    
    return list;
  }

  @Override
  public CateVO read(int cateno) {
    CateVO cateVO = this.cateDAO.read(cateno);
    return cateVO;
  }

  @Override
  public int update(CateVO cateVO) {
    int cnt = this.cateDAO.update(cateVO);
    return cnt;
  }

  @Override
  public int delete(int cateno) {
    int cnt = this.cateDAO.delete(cateno);
    return cnt;
  }

  @Override
  public int update_seqno_up(int cateno) {
    int cnt = this.cateDAO.update_seqno_up(cateno);
    
    return cnt;
  }

  @Override
  public int update_seqno_down(int cateno) {
    int cnt = this.cateDAO.update_seqno_down(cateno);
    
    return cnt;
  }

  @Override
  public int update_visible_y(int cateno) {
    int cnt = this.cateDAO.update_visible_y(cateno);
    return cnt;
  }

  @Override
  public int update_visible_n(int cateno) {
    int cnt = this.cateDAO.update_visible_n(cateno);
    return cnt;
  }

  @Override
  public ArrayList<CateVO> list_all_y() {
    ArrayList<CateVO> list = this.cateDAO.list_all_y();
    return list;
  }

  @Override
  public int update_cnt_add(int cateno) {
    int cnt = this.cateDAO.update_cnt_add(cateno);
    return cnt;
  }

  @Override
  public int update_cnt_sub(int cateno) {
    int cnt = this.cateDAO.update_cnt_sub(cateno);
    return cnt;
  }


}

