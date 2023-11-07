-- 시퀀스 생성
DROP SEQUENCE SEQ_CATEGORY_CODE;
DROP SEQUENCE SEQ_MENU_CODE;
DROP SEQUENCE SEQ_ORDER_CODE;
DROP SEQUENCE SEQ_PAYMENT_CODE;

CREATE SEQUENCE SEQ_CATEGORY_CODE;
CREATE SEQUENCE SEQ_MENU_CODE;
CREATE SEQUENCE SEQ_ORDER_CODE;
CREATE SEQUENCE SEQ_PAYMENT_CODE;

-- 테이블 생성
DROP TABLE tbl_category CASCADE CONSTRAINTS;
DROP TABLE tbl_menu CASCADE CONSTRAINTS;
DROP TABLE tbl_order CASCADE CONSTRAINTS;
DROP TABLE tbl_order_menu CASCADE CONSTRAINTS;
DROP TABLE tbl_payment CASCADE CONSTRAINTS;
DROP TABLE tbl_payment_order;

-- category 테이블 생성
CREATE TABLE tbl_category
(
    category_code    NUMBER NOT NULL,
    category_name    VARCHAR2(30) NOT NULL,
    ref_category_code    NUMBER
);

COMMENT ON COLUMN tbl_category.category_code IS '카테고리코드';
COMMENT ON COLUMN tbl_category.category_name IS '카테고리명';
COMMENT ON COLUMN tbl_category.ref_category_code IS '상위카테고리코드';
COMMENT ON TABLE tbl_category IS '카테고리';

CREATE UNIQUE INDEX index_category_code ON tbl_category
( category_code );

ALTER TABLE tbl_category
 ADD CONSTRAINT pk_category_code PRIMARY KEY ( category_code )
 USING INDEX index_category_code;

ALTER TABLE tbl_category
 ADD CONSTRAINT fk_ref_category_code FOREIGN KEY ( ref_category_code )
 REFERENCES tbl_category ( category_code);

CREATE TABLE tbl_menu
(
    menu_code    NUMBER NOT NULL,
    menu_name    VARCHAR2(30) NOT NULL,
    menu_price    NUMBER NOT NULL,
    category_code    NUMBER NOT NULL,
    orderable_status    CHAR(1) NOT NULL
);

COMMENT ON COLUMN tbl_menu.menu_code IS '메뉴코드';
COMMENT ON COLUMN tbl_menu.menu_name IS '메뉴명';
COMMENT ON COLUMN tbl_menu.menu_price IS '메뉴가격';
COMMENT ON COLUMN tbl_menu.category_code IS '카테고리코드';
COMMENT ON COLUMN tbl_menu.orderable_status IS '주문가능상태';
COMMENT ON TABLE tbl_menu IS '메뉴';

CREATE UNIQUE INDEX index_menu_code ON tbl_menu
( menu_code );

ALTER TABLE tbl_menu
 ADD CONSTRAINT pk_menu_code PRIMARY KEY ( menu_code )
 USING INDEX index_menu_code;

ALTER TABLE tbl_menu
 ADD CONSTRAINT fk_category_code FOREIGN KEY ( category_code )
 REFERENCES tbl_category ( category_code );

CREATE TABLE tbl_order
(
    order_code    NUMBER NOT NULL,
    order_date    VARCHAR2(8) NOT NULL,
    order_time    VARCHAR2(8) NOT NULL,
    total_order_price    NUMBER NOT NULL
);

COMMENT ON COLUMN tbl_order.order_code IS '주문코드';
COMMENT ON COLUMN tbl_order.order_date IS '주문일자';
COMMENT ON COLUMN tbl_order.order_time IS '주문시간';
COMMENT ON COLUMN tbl_order.total_order_price IS '총주문금액';
COMMENT ON TABLE tbl_order IS '주문';

CREATE UNIQUE INDEX index_order_code ON tbl_order
( order_code );

ALTER TABLE tbl_order
 ADD CONSTRAINT pk_order_code PRIMARY KEY ( order_code )
 USING INDEX index_order_code;

CREATE TABLE tbl_order_menu
(
    order_code NUMBER NOT NULL,
    menu_code    NUMBER NOT NULL,
    order_amount    NUMBER NOT NULL
);

COMMENT ON COLUMN tbl_order_menu.order_code IS '주문코드';
COMMENT ON COLUMN tbl_order_menu.menu_code IS '메뉴코드';
COMMENT ON COLUMN tbl_order_menu.order_amount IS '주문수량';
COMMENT ON TABLE tbl_order_menu IS '주문별메뉴';
CREATE UNIQUE INDEX index_comp_order_menu_code ON tbl_order_menu
( order_code,menu_code );

ALTER TABLE tbl_order_menu
 ADD CONSTRAINT pk_comp_order_menu_code PRIMARY KEY ( order_code, menu_code )
 USING INDEX index_comp_order_menu_code;

ALTER TABLE tbl_order_menu
 ADD CONSTRAINT fk_order_menu_order_code FOREIGN KEY ( order_code )
 REFERENCES tbl_order ( order_code );

ALTER TABLE tbl_order_menu
 ADD CONSTRAINT fk_order_menu_menu_code FOREIGN KEY ( menu_code )
 REFERENCES tbl_menu ( menu_code );
 
CREATE TABLE tbl_payment
(
    payment_code    NUMBER NOT NULL,
    payment_date    VARCHAR2(8) NOT NULL,
    payment_time    VARCHAR2(8) NOT NULL,
    payment_price    NUMBER NOT NULL,
    payment_type    VARCHAR2(6) NOT NULL
);

COMMENT ON COLUMN tbl_payment.payment_code IS '결제코드';
COMMENT ON COLUMN tbl_payment.payment_date IS '결제일';
COMMENT ON COLUMN tbl_payment.payment_time IS '결제시간';
COMMENT ON COLUMN tbl_payment.payment_price IS '결제금액';
COMMENT ON COLUMN tbl_payment.payment_type IS '결제구분';
COMMENT ON TABLE tbl_payment IS '결제';

CREATE UNIQUE INDEX index_payment_code ON tbl_payment
( payment_code );

ALTER TABLE tbl_payment
 ADD CONSTRAINT pk_payment_code PRIMARY KEY ( payment_code)
 USING INDEX index_payment_code;

CREATE TABLE tbl_payment_order
(
    order_code    NUMBER NOT NULL,
    payment_code    NUMBER NOT NULL
);

COMMENT ON COLUMN tbl_payment_order.order_code IS '주문코드';
COMMENT ON COLUMN tbl_payment_order.payment_code IS '결제코드';
COMMENT ON TABLE tbl_payment_order IS '결제별주문';

CREATE UNIQUE INDEX index_comp_payment_order_code ON tbl_payment_order
( payment_code,order_code );

ALTER TABLE tbl_payment_order
 ADD CONSTRAINT pk_comp_payment_order_code PRIMARY KEY ( payment_code,order_code )
 USING INDEX index_comp_payment_order_code;

INSERT INTO TBL_CATEGORY VALUES (SEQ_CATEGORY_CODE.NEXTVAL, '식사', null);
INSERT INTO TBL_CATEGORY VALUES (SEQ_CATEGORY_CODE.NEXTVAL, '음료', null);
INSERT INTO TBL_CATEGORY VALUES (SEQ_CATEGORY_CODE.NEXTVAL, '디저트', null);
INSERT INTO TBL_CATEGORY VALUES (SEQ_CATEGORY_CODE.NEXTVAL, '한식', 1);
INSERT INTO TBL_CATEGORY VALUES (SEQ_CATEGORY_CODE.NEXTVAL, '중식', 1);

INSERT INTO TBL_CATEGORY VALUES (SEQ_CATEGORY_CODE.NEXTVAL, '일식', 1);
INSERT INTO TBL_CATEGORY VALUES (SEQ_CATEGORY_CODE.NEXTVAL, '퓨전', 1);
INSERT INTO TBL_CATEGORY VALUES (SEQ_CATEGORY_CODE.NEXTVAL, '커피', 2);
INSERT INTO TBL_CATEGORY VALUES (SEQ_CATEGORY_CODE.NEXTVAL, '쥬스', 2);
INSERT INTO TBL_CATEGORY VALUES (SEQ_CATEGORY_CODE.NEXTVAL, '기타', 2);

INSERT INTO TBL_CATEGORY VALUES (SEQ_CATEGORY_CODE.NEXTVAL, '동양', 3);
INSERT INTO TBL_CATEGORY VALUES (SEQ_CATEGORY_CODE.NEXTVAL, '서양', 3);

INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '열무김치라떼', 4500, 8, 'Y');
INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '우럭스무디', 5000, 10, 'Y');
INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '생갈치쉐이크', 6000, 10, 'Y');
INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '갈릭미역파르페', 7000, 10, 'Y');
INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '앙버터김치찜', 13000, 4, 'Y');

INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '생마늘샐러드', 12000, 4, 'Y');
INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '민트미역국', 15000, 4, 'Y');
INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '한우딸기국밥', 20000, 4, 'Y');
INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '홍어마카롱', 9000, 12, 'Y');
INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '코다리마늘빵', 7000, 12, 'Y');

INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '정어리빙수', 10000, 10, 'Y');
INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '날치알스크류바', 2000, 10, 'Y');
INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '직화구이젤라또', 8000, 12, 'Y');
INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '과메기커틀릿', 13000, 6, 'Y');
INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '죽방멸치튀김우동', 11000, 6, 'Y');

INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '흑마늘아메리카노', 9000, 8, 'Y');
INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '아이스가리비관자육수', 6000, 10, 'Y');
INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '붕어빵초밥', 35000, 6, 'Y');
INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '까나리코코넛쥬스', 9000, 9, 'Y');
INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '마라깐쇼한라봉', 22000, 5, 'Y');

INSERT INTO TBL_MENU VALUES (SEQ_MENU_CODE.NEXTVAL, '돌미나리백설기', 5000, 11, 'Y');

COMMIT;




       
