/**********************************/
/* Table Name: 관리자 */
/**********************************/
DROP TABLE admin CASCADE CONSTRAINTS;
DROP TABLE admin;

CREATE TABLE admin(
    adminno    NUMBER(10)    NOT NULL,
    id         VARCHAR(20)   NOT NULL UNIQUE, -- 아이디, 중복 안됨, 레코드를 구분 
    passwd     VARCHAR(15)   NOT NULL, -- 패스워드, 영숫자 조합
    mname      VARCHAR(20)   NOT NULL, -- 성명, 한글 10자 저장 가능
    mdate      DATE          NOT NULL, -- 가입일    
    grade      NUMBER(2)     NOT NULL, -- 등급(1~10: 관리자, 11~20: 회원, 비회원: 30~39, 정지 회원: 40~49, 탈퇴 회원: 99)    
    PRIMARY KEY (adminno)              -- 한번 등록된 값은 중복 안됨
);

COMMENT ON TABLE admin is '관리자';
COMMENT ON COLUMN admin.adminno is '관리자 번호';
COMMENT ON COLUMN admin.id is '아이디';
COMMENT ON COLUMN admin.PASSWD is '패스워드';
COMMENT ON COLUMN admin.MNAME is '성명';
COMMENT ON COLUMN admin.MDATE is '가입일';
COMMENT ON COLUMN admin.GRADE is '등급';

DROP SEQUENCE admin_seq;

CREATE SEQUENCE admin_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지

INSERT INTO admin(adminno, id, passwd, mname, mdate, grade)
VALUES(admin_seq.nextval, 'admin1', '1234', '관리자1', sysdate, 1);

INSERT INTO admin(adminno, id, passwd, mname, mdate, grade)
VALUES(admin_seq.nextval, 'admin2', '1234', '관리자2', sysdate, 1);

INSERT INTO admin(adminno, id, passwd, mname, mdate, grade)
VALUES(admin_seq.nextval, 'admin3', '1234', '관리자3', sysdate, 1);

commit;

SELECT adminno, id, passwd, mname, mdate, grade FROM admin ORDER BY adminno ASC;
   ADMINNO ID                   PASSWD          MNAME                MDATE                    GRADE
---------- -------------------- --------------- -------------------- ------------------- ----------
         1 admin1               1234            관리자1              2022-10-06 11:47:56          1
         2 admin2               1234            관리자2              2022-10-06 11:47:56          1
         3 admin3               1234            관리자3              2022-10-06 11:47:56          1
         
SELECT adminno, id, passwd, mname, mdate, grade 
FROM admin
WHERE adminno=1;
   ADMINNO ID                   PASSWD          MNAME                MDATE                    GRADE
---------- -------------------- --------------- -------------------- ------------------- ----------
         1 admin1               1234            관리자1              2022-10-06 11:47:56          1

SELECT adminno, id, passwd, mname, mdate, grade 
FROM admin
WHERE id='admin1';
   ADMINNO ID                   PASSWD          MNAME                MDATE                    GRADE
---------- -------------------- --------------- -------------------- ------------------- ----------
         1 admin1               1234            관리자1              2022-10-06 11:47:56          1

UPDATE admin
SET passwd='1234', mname='관리자1', mdate=sysdate, grade=1
WHERE ADMINNO=1;

COMMIT;
         
DELETE FROM admin WHERE adminno=1;
-- ORA-02292: integrity constraint (KD.SYS_C007226) violated - child record found
-- 자식 테이블에서 adminno: 1을 이용하고있기 때문에 삭제 못함. 

-- 로그인
SELECT COUNT(*) as cnt
FROM admin
WHERE id='admin1' AND passwd='1234';
  
  
  