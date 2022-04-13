
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
    email varchar(50),
    nickname varchar(30),
    password varchar(200),
    point int  NOT NULL DEFAULT 0,
    grade VARCHAR(45) NOT NULL DEFAULT 'WELCOME',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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