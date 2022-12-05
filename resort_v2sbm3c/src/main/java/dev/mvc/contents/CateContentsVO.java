package dev.mvc.contents;

import org.springframework.web.multipart.MultipartFile;

/*
SELECT c.name,
       t.contentsno, t.adminno, t.cateno, t.title, t.content, t.recom, t.cnt, t.replycnt, t.word, t.rdate,
       t.file1, t.file1saved, t.thumb1, t.size1, t.price, t.dc, t.saleprice, t.point, t.salecnt, t.map, t.youtube
FROM cate c, contents t
WHERE c.cateno = t.cateno
ORDER BY t.contentsno ASC;   
 */
public class CateContentsVO {
    /** 카테고리 이름 */
    private String name;
    /** 컨텐츠 번호 */
    private int contentsno;
    /** 관리자 번호 */
    private int adminno;
    /** 카테고리 번호 */
    private int cateno;
    /** 제목 */
    private String title = "";
    /** 내용 */
    private String content = "";
    /** 추천수 */
    private int recom;
    /** 조회수 */
    private int cnt = 0;
    /** 댓글수 */
    private int replycnt = 0;
    /** 패스워드 */
    private String passwd = "";
    /** 검색어 */
    private String word = "";
    /** 등록 날짜 */
    private String rdate = "";

    /** 메인 이미지 */
    private String file1 = "";
    /** 실제 저장된 메인 이미지 */
    private String file1saved = "";
    /** 메인 이미지 preview */
    private String thumb1 = "";
    /** 메인 이미지 크기 */
    private long size1;

    /** 정가 */
    private int price;
    /** 할인률 */
    private int dc;
    /** 판매가 */
    private int saleprice;
    /** 포인트 */
    private int point;
    /** 재고 수량 */
    private int salecnt;
    
    /** 지도 */
    private String map;

    /** Youtube */
    private String youtube;
    
    /**
     이미지 파일
     <input type='file' class="form-control" name='file1MF' id='file1MF' 
                value='' placeholder="파일 선택">
     */
    private MultipartFile file1MF;
    
    /** 메인 이미지 크기 단위, 파일 크기 */
    private String size1_label = "";

    public CateContentsVO() { // 기본 생성자
        
    }

    public int getContentsno() {
        return contentsno;
    }

    public void setContentsno(int contentsno) {
        this.contentsno = contentsno;
    }

    public int getAdminno() {
        return adminno;
    }

    public void setAdminno(int adminno) {
        this.adminno = adminno;
    }

    public int getCateno() {
        return cateno;
    }

    public void setCateno(int cateno) {
        this.cateno = cateno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRecom() {
        return recom;
    }

    public void setRecom(int recom) {
        this.recom = recom;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public int getReplycnt() {
        return replycnt;
    }

    public void setReplycnt(int replycnt) {
        this.replycnt = replycnt;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getFile1() {
        return file1;
    }

    public void setFile1(String file1) {
        this.file1 = file1;
    }

    public String getFile1saved() {
        return file1saved;
    }

    public void setFile1saved(String file1saved) {
        this.file1saved = file1saved;
    }

    public String getThumb1() {
        return thumb1;
    }

    public void setThumb1(String thumb1) {
        this.thumb1 = thumb1;
    }

    public long getSize1() {
        return size1;
    }

    public void setSize1(long size1) {
        this.size1 = size1;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDc() {
        return dc;
    }

    public void setDc(int dc) {
        this.dc = dc;
    }

    public int getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(int saleprice) {
        this.saleprice = saleprice;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getSalecnt() {
        return salecnt;
    }

    public void setSalecnt(int salecnt) {
        this.salecnt = salecnt;
    }

    public MultipartFile getFile1MF() {
        return file1MF;
    }

    public void setFile1MF(MultipartFile file1mf) {
        file1MF = file1mf;
    }

    public String getSize1_label() {
        return size1_label;
    }

    public void setSize1_label(String size1_label) {
        this.size1_label = size1_label;
    }

    public String getMap() {
      return map;
    }

    public void setMap(String map) {
      this.map = map;
    }

    public String getYoutube() {
      return youtube;
    }

    public void setYoutube(String youtube) {
      this.youtube = youtube;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
    
    
    
}
