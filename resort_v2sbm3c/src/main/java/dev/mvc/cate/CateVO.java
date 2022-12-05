package dev.mvc.cate;

/*
CREATE TABLE cate(
    cateno                            NUMBER(10)   NOT NULL    PRIMARY KEY,
    name                              VARCHAR2(50) NOT NULL,
    cnt                               NUMBER(7)  DEFAULT 0     NOT NULL,
    rdate                               DATE     NOT NULL,
    udate                             DATE     NULL,
    seqno                               NUMBER(10)   DEFAULT 0       NOT NULL,
    visible                             CHAR(1)      DEFAULT 'N'     NOT NULL -- Y, N
);
 */
public class CateVO {
  /** 카테고리 번호 */
  private int cateno;  
  /** 카테고리 이름 */
  private String name;
  /** 등록된 글 수 */
  private int cnt;
  /** 등록일 */
  private String rdate;
  /** 변경일 */
  private String udate;
  
  /** 출력 순서, 기본값 0 */
  private int seqno;
  
  /** 출력 모드, 기본값 null */
  private String visible;
  
  public int getCateno() {
    return cateno;
  }
  public void setCateno(int cateno) {
    this.cateno = cateno;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getRdate() {
    return rdate;
  }
  public void setRdate(String rdate) {
    this.rdate = rdate;
  }
  public int getCnt() {
    return cnt;
  }
  public void setCnt(int cnt) {
    this.cnt = cnt;
  }
  
  @Override
  public String toString() {
    return "CateVO [cateno=" + cateno + ", name=" + name + ", rdate=" + rdate + ", cnt=" + cnt + "]";
  }
  
  public String getUdate() {
    return udate;
  }
  public void setUdate(String udate) {
    this.udate = udate;
  }
  public int getSeqno() {
    return seqno;
  }
  public void setSeqno(int seqno) {
    this.seqno = seqno;
  }
  
  public String getVisible() {
    return visible;
  }
  public void setVisible(String visible) {
    this.visible = visible;
  }
  
  
  
}


