
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
    FOREIGN KEY authority varchar(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP);

create table authority
(
    GUEST varchar(20),
    MEMBER varchar(20),
    SELLER varchar(20),
    ADMIN varchar(20)
);
