package dev.mvc.cart;

/*
cartno                        NUMBER(10) NOT NULL PRIMARY KEY,
contentsno                 NUMBER(10) NULL ,
memberno                 NUMBER(10) NOT NULL,
cnt                            NUMBER(10) DEFAULT 0 NOT NULL,
rdate                          DATE NOT NULL,
  
SELECT t.cartno, c.contentsno, c.title, c.thumb1, c.price, c.dc, c.saleprice, c.point, t.memberno, t.cnt, t.rdate 
FROM contents c, cart t
WHERE c.contentsno = t.contentsno
ORDER BY cartno ASC;
 */
public class CartVO {
    /** 쇼핑 카트 번호 */
    private int cartno;
    /** 컨텐츠 번호 */
    private int contentsno;
    /** 제목 */
    private String title = "";
    /** 메인 이미지 preview */
    private String thumb1 = "";
    /** 정가 */
    private int price;
    /** 할인률 */
    private int dc;
    /** 판매가 */
    private int saleprice;
    /** 포인트 */
    private int point;
    /** 회원 번호 */
    private int memberno;
    /** 수량 */
    private int cnt;
    /** 금액 = 판매가 x 수량 */
    private int tot;
    /** 등록일 */
    private String rdate;

    public int getCartno() {
        return cartno;
    }

    public void setCartno(int cartno) {
        this.cartno = cartno;
    }

    public int getContentsno() {
        return contentsno;
    }

    public void setContentsno(int contentsno) {
        this.contentsno = contentsno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb1() {
        return thumb1;
    }

    public void setThumb1(String thumb1) {
        this.thumb1 = thumb1;
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

    public int getMemberno() {
        return memberno;
    }

    public void setMemberno(int memberno) {
        this.memberno = memberno;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public int getTot() {
        return tot;
    }

    public void setTot(int tot) {
        this.tot = tot;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

}