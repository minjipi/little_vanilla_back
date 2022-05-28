
create table product (
    idx int auto_increment primary key,
    name varchar(50),
    brandIdx int,
    categoryIdx int,
    price int,
    salePrice int,
    deliveryType varchar(50),
    isTodayDeal varchar(1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP);

create table member (
    idx int auto_increment primary key,
    email varchar(50)  not null unique,
    nickname varchar(30),
    password varchar(200),
    point int  NOT NULL DEFAULT 0,
    grade VARCHAR(45) NOT NULL DEFAULT 'WELCOME',
    phoneNum varchar(11),
    gender varchar(1),
    birthday varchar(11),
    notification varchar(3),
    status tinyint default 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create table seller (
    idx int auto_increment primary key,
    email varchar(50)  not null unique,
    brandname varchar(30),
    password varchar(200),
    phoneNum varchar(11),
    gender varchar(1),
    birthday varchar(11),
    notification varchar(3),
    status tinyint default 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `emailcert`
(
    `token`            varchar(200) NOT NULL,
    `user_email`       varchar(200) NOT NULL,
    `expired`          tinyint(1) NOT NULL,
    `created_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `expired_at` timestamp    NOT NULL,
    PRIMARY KEY (`token`)
);

create table authority (
    member_idx int,
    role int,
    foreign key (member_idx) references member (idx)
);

create table pay(
    idx int auto_increment primary key,
    money int,
    total int,
    in_out int,
    member_idx int,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (member_idx) references member (idx)
);

CREATE TABLE productImage (
   idx int NOT NULL AUTO_INCREMENT,
   filename varchar(100) NOT NULL,
   productIdx int DEFAULT NULL,
   PRIMARY KEY (idx),
   KEY productIdx (productIdx),
   CONSTRAINT productimage_ibfk_1 FOREIGN KEY (productIdx) REFERENCES product (idx)
);

create table orders(
    idx int auto_increment primary key,
    member_idx int,
    product_idx int,
    amount int,
    status varchar(20) NOT NULL DEFAULT '입금대기',
    ordered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (member_idx) references member (idx),
    foreign key (product_idx) references product (idx)
);

-- '6 : 리뷰쓰기 / 5 : 배송완료 / 4 : 배송중 / 3 : 배송준비 / 2 : 결제완료 / 1 : 입금대기 / 0 : 주문취소(유효하지 않은 상태)'
-- 뭘 찜했는지를 저장.
create table likes (
    member_idx int,
    product_idx int,
    cabinet_idx int NULL,
    foreign key (cabinet_idx) references cabinet(idx),
    foreign key (member_idx) references member (idx),
    foreign key (product_idx) references product (idx),
    primary key (member_idx, product_idx)
);

-- 만원 충전, 이만원 충전. 내가 얼만큼 충전했나 내역 볼 때.. 만원 이만원 따로 있어야함. 그래서 idx, member_idx따로 만듬.
-- 1이 충전, 0이 출금
-- ex 방을 만드는 것. 분류 목적.

create table cabinet (
   idx int auto_increment primary key,
   cabinetName varchar(100) NOT NULL DEFAULT '기본',
   member_idx int,
   foreign key (member_idx) references member (idx)
);

-- 상품 조회
-- select * from likes left outer join product on product.idx=likes.product_idx left outer join productImage on productImage.productIdx=product.idx where cabinet_idx=1 and member_idx=3;

-- select * from likes left outer join product on product.idx=likes.product_idx where cabinet_idx is NULL and member_idx=3;

CREATE TABLE `order`
(
    `idx`              int NOT NULL AUTO_INCREMENT,
    `product_idx`       int NOT NULL,
    `member_idx`        int NOT NULL,
    `imp_uid`           varchar(16) NOT NULL,
    `create_timestamp` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_timestamp` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`idx`),
    foreign key (product_idx) references product (idx),
    foreign key (member_idx) references member (idx)
);




CREATE TABLE `cart`
(
    `idx`              int NOT NULL AUTO_INCREMENT,
    `product_idx`      int NOT NULL,
    `member_idx`       int NOT NULL,
    `amount`           int NOT NULL,
    status tinyint default 0,
    PRIMARY KEY (`idx`),
    foreign key (product_idx) references product (idx),
    foreign key (member_idx) references member (idx)
);