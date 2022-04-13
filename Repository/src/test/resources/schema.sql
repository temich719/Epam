create schema giftCertificates;

create table gift_certificate
(
    id               int auto_increment primary key,
    name             varchar(50)  not null,
    description      varchar(100) not null,
    price            varchar(20)  not null,
    duration         varchar(30)  not null,
    create_date      varchar(100) not null,
    last_update_date varchar(100) not null
);

create table tag
(
    id   int auto_increment primary key,
    name varchar(50) not null unique
);

create table certificates_and_tags
(
    gift_certificate_id int not null,
    tag_id              int not null,
    foreign key (gift_certificate_id) references gift_certificate (id),
    foreign key (tag_id) references tag (id)
);