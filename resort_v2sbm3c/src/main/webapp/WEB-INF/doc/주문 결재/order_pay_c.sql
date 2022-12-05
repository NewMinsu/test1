/**********************************/
/* Table Name: 주문_결재 */
/**********************************/
DROP TABLE order_pay CASCADE CONSTRAINTS;

CREATE TABLE order_pay(
    order_payno                     NUMBER(10)     NOT NULL    PRIMARY KEY,
    memberno                       NUMBER(10)     NULL ,
    rname                             VARCHAR2(30)     NOT NULL,
    rtel                                 VARCHAR2(14)     NOT NULL,
    rzipcode                          VARCHAR2(5)    NULL ,
    raddress1                         VARCHAR2(80)     NOT NULL,
    raddress2                         VARCHAR2(50)     NOT NULL,
    paytype                           NUMBER(1)    DEFAULT 0     NOT NULL,
    amount                            NUMBER(10)     DEFAULT 0     NOT NULL,
    rdate                              DATE     NOT NULL,
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);

COMMENT ON TABLE order_pay is '주문_결재';
COMMENT ON COLUMN order_pay.order_payno is '주문 번호';
COMMENT ON COLUMN order_pay.MEMBERNO is '회원 번호';
COMMENT ON COLUMN order_pay.rname is '수취인성명';
COMMENT ON COLUMN order_pay.rtel is '수취인 전화번호';
COMMENT ON COLUMN order_pay.rzipcode is '수취인 우편번호';
COMMENT ON COLUMN order_pay.raddress1 is '수취인 주소1';
COMMENT ON COLUMN order_pay.raddress2 is '수취인 주소2';
COMMENT ON COLUMN order_pay.paytype is '결재 종류';
COMMENT ON COLUMN order_pay.amount is '결재금액';
COMMENT ON COLUMN order_pay.rdate is '주문날짜';


DROP SEQUENCE order_pay_seq;
CREATE SEQUENCE order_pay_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999999
  CACHE 2                     -- 2번은 메모리에서만 계산
  NOCYCLE;                   -- 다시 1부터 생성되는 것을 방지

-- 등록  
-- 결재 종류(paytype):  1: 신용 카드, 2: 모바일, 3: 포인트, 4: 계좌 이체, 5: 직접 입금  
INSERT INTO order_pay(order_payno, memberno, rname, rtel, rzipcode,
                                 raddress1, raddress2, paytype, amount, rdate)
VALUES (order_pay_seq.nextval, 3, '홍길순', '111-2222-3333', '12345',
             '서울시 종로구', '관철동', 1, 32000, sysdate);
INSERT INTO order_pay(order_payno, memberno, rname, rtel, rzipcode,
                                 raddress1, raddress2, paytype, amount, rdate)
VALUES (order_pay_seq.nextval, 3, '아로미', '111-2222-3333', '12345',
             '서울시 종로구', '관철동', 1, 15000, sysdate);
INSERT INTO order_pay(order_payno, memberno, rname, rtel, rzipcode,
                                 raddress1, raddress2, paytype, amount, rdate)
VALUES (order_pay_seq.nextval, 3, '왕눈이', '111-2222-3333', '12345',
             '서울시 종로구', '관철동', 1, 63000, sysdate);
commit; 

-- 전체 목록
SELECT order_payno, memberno, rname, rtel, rzipcode, raddress1, raddress2, paytype, amount, rdate
FROM order_pay
ORDER BY order_payno DESC;

--회원별 목록
SELECT order_payno, memberno, rname, rtel, rzipcode, raddress1, raddress2, paytype, amount, rdate
FROM order_pay
WHERE memberno=3
ORDER BY order_payno DESC;

-- 수정: 개발 안함.

-- 삭제
DELETE FROM order_pay
WHERE order_payno=1;

commit;

