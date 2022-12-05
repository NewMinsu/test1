package dev.mvc.admin;

/*
    adminno    NUMBER(10)    NOT NULL,
    id         VARCHAR(20)   NOT NULL UNIQUE, -- 아이디, 중복 안됨, 레코드를 구분 
    passwd     VARCHAR(15)   NOT NULL, -- 패스워드, 영숫자 조합
    mname      VARCHAR(20)   NOT NULL, -- 성명, 한글 10자 저장 가능
    mdate      DATE          NOT NULL, -- 가입일    
    grade      NUMBER(2)     NOT NULL, -- 등급(1~10: 관리자, 11~20: 회원, 비회원: 30~39, 정지 회원: 40~49, 탈퇴 회원: 99)    
 
 */
public class AdminVO {
  /** 관리자 번호 */
  private int adminno;
  /** 아이디 */
  private String id = "";
  /** 패스워드 */
  private String passwd = "";
  /** 회원 성명 */
  private String mname = "";
  /** 가입일 */
  private String mdate = "";
  /** 등급 */
  private int grade = 1;
  
  public int getAdminno() {
    return adminno;
  }
  public void setAdminno(int adminno) {
    this.adminno = adminno;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getPasswd() {
    return passwd;
  }
  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }
  public String getMname() {
    return mname;
  }
  public void setMname(String mname) {
    this.mname = mname;
  }
  public String getMdate() {
    return mdate;
  }
  public void setMdate(String mdate) {
    this.mdate = mdate;
  }
  public int getGrade() {
    return grade;
  }
  public void setGrade(int grade) {
    this.grade = grade;
  }
  
  
}
