
create table product (
    idx int auto_increment primary key,
    name varchar(50),
    brandIdx int,
    categoryIdx int,
    price int,
    salePrice int,
    deliveryType varchar(50),
    isTodayDeal varchar(1),
    contents varchar(900),
    status tinyint default 1,
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
    nickname varchar(30),
    password varchar(200),
    phoneNum varchar(11),
    gender varchar(1),
    birthday varchar(11),
    notification varchar(3),
    status tinyint default 1,
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
    status varchar(20) NOT NULL DEFAULT '????????????',
    ordered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (member_idx) references member (idx),
    foreign key (product_idx) references product (idx)
);

-- '6 : ???????????? / 5 : ???????????? / 4 : ????????? / 3 : ???????????? / 2 : ???????????? / 1 : ???????????? / 0 : ????????????(???????????? ?????? ??????)'

create table likes (
    member_idx int,
    product_idx int,
    cabinet_idx int NULL,
    foreign key (cabinet_idx) references cabinet(idx),
    foreign key (member_idx) references member (idx),
    foreign key (product_idx) references product (idx),
    primary key (member_idx, product_idx)
);

-- ?????? ??????, ????????? ??????. ?????? ????????? ???????????? ?????? ??? ???.. ?????? ????????? ?????? ????????????. ????????? idx, member_idx?????? ??????.
-- 1??? ??????, 0??? ??????
-- ex ?????? ????????? ???. ?????? ??????.

create table cabinet (
   idx int auto_increment primary key,
   cabinetName varchar(100) NOT NULL DEFAULT '??????',
   member_idx int,
   foreign key (member_idx) references member (idx)
);

-- ?????? ??????
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
    PRIMARY KEY (`idx`),
    foreign key (product_idx) references product (idx),
    foreign key (member_idx) references member (idx)
);