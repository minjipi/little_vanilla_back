
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create table authority (
    member_idx int,
    role int,
    foreign key (member_idx) references member (idx)
);

create table likes (
    member_idx int,
    product_idx int,
    foreign key (member_idx) references member (idx),
    foreign key (product_idx) references product (idx),
    primary key (member_idx, product_idx)
);

