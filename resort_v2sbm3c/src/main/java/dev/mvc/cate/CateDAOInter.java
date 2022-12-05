package dev.mvc.cate;

import java.util.ArrayList;

// Spring framework에서 제공하는 기능
// application.properties를 읽어서 DBMS 연결/해제 자동 구현
// try ~ catch 자동 구현
// cateVO 값을 SQL에 자동 전달 및 실행
// return 자동 구현
public interface CateDAOInter {
  /**
   * 등록
   * <xmp><insert id="create" parameterType="dev.mvc.cate.CateVO"></xmp>
   * @param cateVO
   * @return 등록한 레코드 개수
   */
  public int create(CateVO cateVO);
  
  /**
   * 전체 목록
   * <xmp><select id="list_all" resultType="dev.mvc.cate.CateVO"></xmp>
   * @return 레코드 전체 목록
   */
  public ArrayList<CateVO> list_all();
  
  /**
   * 조회
   * <xmp><select id="read" resultType="dev.mvc.cate.CateVO" parameterType="int"></xmp>
   * @param cateno
   * @return
   */
  public CateVO read(int cateno);
  
  /**
   * 수정
   * <xmp><update id="update" parameterType="dev.mvc.cate.CateVO"></xmp>
   * @param cateVO
   * @return 수정된 레코드 갯수
   */
  public int update(CateVO cateVO);
  
  /**
   * 삭제
   * @param cateno
   * @return 삭제된 레코드 수
   */
  public int delete(int cateno);
  
  /**
   * 출력 순서 올림(상향, 10 등 -> 1 등), seqno: 10 -> 1
   * @param cateno
   * @return 변경된 레코드 수
   */
  public int update_seqno_up(int cateno);
  
  /**
   * 출력 순서 내림(상향, 1 등 -> 10 등), seqno: 1 -> 10
   * @param cateno
   * @return 변경된 레코드 수
   */
  public int update_seqno_down(int cateno);
  
  /**
   * 출력 모드 Y로 변경
   * @param cateno
   * @return 변경된 레코드 수
   */
  public int update_visible_y(int cateno);
  
  /**
   * 출력 모드 N로 변경
   * @param cateno
   * @return 변경된 레코드 수
   */
  public int update_visible_n(int cateno);

  /**
   * visible이 「Y」인 카테고리 출력하기
   * @return 레코드 전체 목록
   */
  public ArrayList<CateVO> list_all_y();
  
  /**
   * 글 수 증가
   * @param cateno
   * @return 처리된 레코드 수
   */
  public int update_cnt_add(int cateno);

  /**
   * 글 수 감소
   * @param cateno
   * @return 처리된 레코드 수
   */
  public int update_cnt_sub(int cateno);
  
  
}


