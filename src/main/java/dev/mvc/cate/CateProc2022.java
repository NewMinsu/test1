package dev.mvc.cate;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.cate.CateProc2022")
public class CateProc2022 implements CateProcInter {
  // CateDAOInter interface 만 존재하고 구현 class는 존재하지 않음.
  // interface는 객체를 만들 수 없고 할당만 받을 수 있음.
  
  @Autowired
  private CateDAOInter cateDAO;
  
  public CateProc2022() {
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public CateVO read(int cateno) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int update(CateVO cateVO) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int delete(int cateno) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int update_seqno_up(int cateno) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int update_seqno_down(int cateno) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int update_visible_y(int cateno) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int update_visible_n(int cateno) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public ArrayList<CateVO> list_all_y() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int update_cnt_add(int cateno) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int update_cnt_sub(int cateno) {
    // TODO Auto-generated method stub
    return 0;
  }

}
