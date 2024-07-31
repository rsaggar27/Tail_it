create database tail_it;
use tail_it;
create table customers(mobile varchar(20) primary key, cname varchar(30), gender varchar(20),address varchar(200), city varchar(30), state varchar(30), dob date, doe date);
select * from customers;
ALTER TABLE customers ADD email varchar(100);

create table measurements(orderid int primary key auto_increment,mobile varchar(30),dress varchar(100),dod date, quantity int,bill int, advpaid int, worker varchar(100),despic varchar(200), measurements varchar(400));
ALTER TABLE measurements ADD pprice varchar(100);
ALTER TABLE measurements ADD doo date;
ALTER TABLE measurements ADD pstatus int;

select * from measurements;

create table workers(mobile varchar(20) primary key, cname varchar(30), gender varchar(20),address varchar(200), city varchar(30), state varchar(30), dob date, doe date,splz varchar(200));
select * from workers;