package dev.mvc.member;

import org.springframework.web.multipart.MultipartFile;

public class MemberVO {
    /*
     * memberno INT NOT NULL AUTO_INCREMENT, -- 회원 번호, 레코드를 구분하는 컬럼 id VARCHAR(20)
     * NOT NULL UNIQUE, -- 아이디, 중복 안됨, 레코드를 구분 password VARCHAR(20) NOT NULL, -- 패스워드,
     * 영숫자 조합 mname VARCHAR(30) NOT NULL, -- 성명, 한글 10자 저장 가능 tel VARCHAR(14) NOT
     * NULL, -- 전화번호 zipcode VARCHAR(5) NULL, -- 우편번호, 12345 address1 VARCHAR(80)
     * NULL, -- 주소 1 address2 VARCHAR(50) NULL, -- 주소 2 mdate DATETIME NOT NULL, --
     * 가입일 grade NUMBER(2) NOT NULL, -- 등급(1 ~ 10: 관리자, 11~20: 회원, 비회원: 30~39, 정지
     * 회원: 40~49) PRIMARY KEY (mno) -- 한번 등록된 값은 중복 안됨
     */

    /** 회원 번호 */
    private int memberno;
    /** 아이디 */
    private String id = "";
    /** 패스워드 */
    private String password = "";
    /** 회원 성명 */
    private String mname = "";
    /** 전화 번호 */
    private String phonenum = "";
    /** 집 번호 */
    private String homenum = "";
    /** 주소 */
    private String address = "";
    /** 닉네임 */
    private String nickname = "";
    /** 가입일 */
    private String mdate = "";
    /** 등급 */
    private int grade = 0;

    /** 등록된 패스워드 */
    private String old_password = "";
    /** id 저장 여부 */
    private String id_save = "";
    /** password 저장 여부 */
    private String password_save = "";
    /** 이동할 주소 저장 */
    private String url_address = "";
    
    public int getMemberno() {
        return memberno;
    }
    public void setMemberno(int memberno) {
        this.memberno = memberno;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getMname() {
        return mname;
    }
    public void setMname(String mname) {
        this.mname = mname;
    }
    public String getPhonenum() {
        return phonenum;
    }
    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
    public String getHomenum() {
        return homenum;
    }
    public void setHomenum(String homenum) {
        this.homenum = homenum;
    }
    public String getAddress() {
        return address;
    }
    public void setAddres1(String address) {
        this.address = address;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
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
    public String getOld_password() {
        return old_password;
    }
    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }
    public String getId_save() {
        return id_save;
    }
    public void setId_save(String id_save) {
        this.id_save = id_save;
    }
    public String getpassword_save() {
        return password_save;
    }
    public void setpassword_save(String password_save) {
        this.password_save = password_save;
    }
    public String getUrl_address() {
        return url_address;
    }
    public void setUrl_address(String url_address) {
        this.url_address = url_address;
    }

    
    
}
