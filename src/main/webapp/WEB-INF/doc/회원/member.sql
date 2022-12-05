DROP TABLE MEMBER;
DROP TABLE MEMBER CASCADE CONSTRAINTS;

CREATE TABLE MEMBER (
  memberno NUMBER(10) NOT NULL, 
  id         VARCHAR(20)   NOT NULL UNIQUE, 
  password   VARCHAR(20)   NOT NULL, 
  mname      VARCHAR(10)   NOT NULL, 
  phonenum   VARCHAR(15)   NOT NULL,
  homenum    VARCHAR(15)        NULL, 
  address    VARCHAR(80)       NULL, 
  nickname   VARCHAR(50)       NULL, 
  mdate      DATE             NOT NULL, 
  grade    NUMBER(2)     NOT NULL, 
  PRIMARY KEY (memberno)           
);

COMMENT ON TABLE MEMBER is '회원';
COMMENT ON COLUMN MEMBER.MEMBERNO is '회원번호';
COMMENT ON COLUMN MEMBER.ID is '아이디';
COMMENT ON COLUMN MEMBER.PASSWORD is '비밀번호';
COMMENT ON COLUMN MEMBER.MNAME is '성명';
COMMENT ON COLUMN MEMBER.PHONENUM is '전화번호';
COMMENT ON COLUMN MEMBER.HOMENUM is '우편번호';
COMMENT ON COLUMN MEMBER.ADDRESS is '주소';
COMMENT ON COLUMN MEMBER.NICKNAME is '닉네임';
COMMENT ON COLUMN MEMBER.MDATE is '가입일';
COMMENT ON COLUMN MEMBER.GRADE is '등급';

DROP SEQUENCE MEMBER_seq;

CREATE SEQUENCE MEMBER_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
 
 
1. 등록
 
1) id 중복 확인(null 일시 count 안됨)
SELECT COUNT(id)
FROM MEMBER
WHERE id='user1';

SELECT COUNT(id) as cnt
FROM MEMBER
WHERE id='user1';

-- 회원 계정
INSERT INTO member(memberno, id, password, mname, phonenum, homenum,address, nickname, mdate, grade)
VALUES (member_seq.nextval, 'user1', '1234', 'juwon', '000-0000-0000', '00-0000-0000',
             '서울시', '주원', sysdate, 1);

COMMIT;

 
2. 목록
- 검색을 하지 않는 경우, 전체 목록 출력
 
SELECT memberno, id, password, mname, phonenum, homenum, address, nickname, mdate, grade
FROM member
ORDER BY grade ASC, id ASC;
     
     
3. 조회
 
SELECT memberno, id, password, mname, phonenum, homenum, address, nickname, mdate, grade
FROM member
WHERE memberno = 1;

SELECT memberno, id, password, mname, phonenum, homenum, address, nickname, mdate, grade
FROM member
WHERE id = 'user1';
 
    
4. 수정
UPDATE member 

SET memberno = 2, id = '', password='', mname='', phonenum = 000-0000-0000, homenum= 00-0000-0000, address='', nickname='', mdate=0000-00-00, grade=0
WHERE memberno=1;


UPDATE member SET password='123123' 
WHERE memberno=2;

commit;
 
5. 삭제
1) 모두 삭제
DELETE FROM member;
 
2) 특정 회원 삭제
DELETE FROM MEMBER
WHERE memberno=1;
commit;
 