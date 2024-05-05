-- CREATE DATABASE job_search;
-- USE job_search;
drop database job_hunting_db;
CREATE DATABASE job_hunting_db;
USE job_hunting_db;

CREATE TABLE role_db
(
	role_id int(11) not null auto_increment,
    role_name varchar(255),
    primary key (role_id)
);

INSERT INTO role_db(role_name)
VALUES ('Admin'),
('User');

CREATE TABLE user_db
(
	user_id int(11) not null auto_increment,
    user_full_name varchar(255),
    user_email varchar(255),
    user_address varchar(255),
    user_password varchar(128),
    user_image varchar(255),
	phone_number varchar(255),
    user_description varchar(255),
    status int(11),
    role_id int(11),
    company_id int (11),
    primary key (user_id),
    constraint foreign key (role_id) references role_db (role_id),
    constraint foreign key (company_id) references company_db (company_id)
);
INSERT INTO user_db(user_full_name,user_email,user_address,user_password,phone_number,user_description,status,role_id,company_id)
VALUES ('Nguyen Thi Xuan','xuan001@email.com','123 HCM','{noop}123456','0010010011','tai khoan cua Xuan',1,1,1),
('Tran Minh Tuan','tuan002@email.com','567 HN','{noop}123456','0020020022','tai khoan cua Tuan',1,1,2),
('Lam Anh Dung','dung003@email.com','255 HP','{noop}123456','0030030033','tai khoan cua Dung',1,1,3),
('Ngo Van Sinh','sinh004@email.com','887 QB','{noop}123456','0040040044','tai khoan cua Sinh',1,1,4),
('Mai Minh ANh','anh003@email.com','7425 QN','{noop}123456','0030050033','tai khoan cua Minh Anh',1,2,5),
('Hoan Mai Huong','huong001@email.com','92883 HCM','{noop}123456','0010060011','tai khoan cua Huong',1,2,6),
('Lai Son Nam','nam006@email.com','567 BH','{noop}123456','0090090099','tai khoan cua Nam',1,2,7),
('Hoang Tuyet Van','van007@email.com','28386 PQND','{noop}123456','009010010','tai khoan cua Van',1,2,8);

CREATE TABLE company_db
(
	company_id int(11) not null auto_increment,
    company_address varchar(255),
    company_email varchar(255),
    company_logo varchar(255),
    company_name varchar(255),
    company_phone_number varchar(255),
    company_description text,
    status int(11),
    primary key (company_id)
);

SET foreign_key_checks = 0;
INSERT INTO company_db(company_address,company_email,company_name,company_phone_number,company_description,status)
VALUES ('589 HCM','qwe@email.com','Comp1','0902222221','Company No.1',1),
('344 MKO','ert4@email.com','Comp2','0705559990','Company No.2',1),
('666 IWP','dfg7@email.com','Comp3','0904444555','Company No.3',1),
('2934 AAA','9pown@email.com','Comp4','0118889929','Company No.4',1),
('0023 OWJ','bnm@email.com','Comp5','0905553332','Company No.5',1),
('738/2 HEN','lkj1@email.com','Comp6','0703336665','Company No.6',1),
('8222 JQK','zxc3@email.com','Comp7','01012444566','Company No.7',1),
('21323 BBOIK','dedkoj0083@email.com','Comp8','02839900292','Company No.8',1);

CREATE TABLE cv_db
(
	cv_id int(11) not null auto_increment,
    file_name varchar(255),
    user_id int(11),
    primary key (cv_id),
    constraint foreign key (user_id) 
    references user_db (user_id) 
);

INSERT INTO cv_db(file_name,user_id)
VALUES ('Cv Xuan',8),
('Cv Tuan',9),
('Cv Dung',10),
('Cv Sinh',11),
('Cv Anh',12),
('Cv Huong',13),
('Cv Nam',14),
('Cv Xuan2',8),
('Cv Xuan2',8),
('Cv Nam1',14),
('Cv Xuan3',8),
('Cv Sinh1',11),
('Cv Xuan4',8),
('Cv Sinh2',11);

alter table user_db add column  cv_id int(11);
alter table user_db add foreign key (cv_id) references cv_db (cv_id);

CREATE TABLE follow_company_db
(
	follow_company_id int(11) not null auto_increment,
	user_id int(11),
	company_id int(11),
    primary key (follow_company_id),
    constraint foreign key (user_id) 
    references user_db (user_id) ,
    constraint foreign key (company_id) 
    references company_db (company_id) 
);

INSERT INTO follow_company_db(user_id,company_id)
VALUES (8,12),(8,8),(9,12),(11,12),(10,8),(13,8),(14,9),(11,11),(13,12),(11,9),(10,12),(14,10);

CREATE TABLE recruitment_db
(
	recruitment_id int(11) not null auto_increment,
    recruitment_address varchar(255),
    quantity int(11),
    experience varchar(255),
    recruitment_rank varchar(255),
    salary varchar(255),
    title varchar(255),
    recruitment_type varchar(255),
	recruitment_description varchar(255),
    deadline varchar(255),
    created_at varchar(255),
    status int(11),
	view int(11),
    category_id int(11),
    company_id int(11),
    primary key (recruitment_id),
    constraint foreign key (category_id) 
    references category_db (category_id),
    constraint foreign key (company_id) 
    references company_db (company_id) 
);

INSERT INTO recruitment_db(recruitment_address,quantity,experience,recruitment_rank,salary,title,recruitment_type,recruitment_description,status,deadline,view,category_id,company_id)
VALUES ('001 HHP',2,'3y','high',15000,'Looking for A','Full Time','Good Job No.1',1,'2023-09-30',10,1,1),
('112 MMW',1,'5y','low',25000,'Looking for A,B','Full Time','High-Quality Job',1,'2024-02-01',8,1,2),
('223 IIO',5,'1y','high',10000,'Looking for A','Part Time','Easy Job',1,'2023-08-17',10,2,3),
('334 AAK',2,'1y','medium',12000,'Looking for C','Full Time','Friendly Envi',1,'2023-10-13',3,3,4),
('445 IWN',4,'3y','low',18000,'Looking for C,D','Full Time','Easy-going Colleague',1,'2024-01-25',6,1,5),
('556 UUE',7,'4y','medium',20000,'Looking for A,C','Part Time','Competative Salary',1,'2023-12-23',16,4,6),
('667 JJQ',5,'3y','high',16500,'Looking for B','Full Time','Decent Benefit',1,'2023-11-30',5,3,7);

CREATE TABLE apply_post_db
(
	apply_post_id int(11) not null auto_increment,
	cv_name varchar(255),
    apply_post_text varchar(255),
    created_at varchar(255),
	user_id int(11),
	recruitment_id int(11),
    status int(11),
    primary key (apply_post_id),
    constraint foreign key (user_id) 
    references user_db (user_id) ,
    constraint foreign key (recruitment_id) 
    references recruitment_db (recruitment_id) 
);
INSERT INTO apply_post_db(cv_name,apply_post_text,user_id,recruitment_id)
VALUES ('Cv Xuan','Apply for A',8,12),
('Cv Huong','Apply for A,B',13,8),
('Cv Tuan','Apply for C',9,12),
('Cv Sinh2','Apply for A',11,12),
('Cv Dung','Apply for A,C',10,9),
('Cv Huong','Apply for B',13,9),
('Cv Huong','Apply for B,C',13,12),
('Cv Huong1','Apply for B,A',13,6),
('Cv Dung1','Apply for B,A',10,6);

CREATE TABLE category_db
(
	category_id int(11) not null auto_increment,
    category_name varchar(255),
	number_choose int(11),
    primary key (category_id)
);

-- constraint foreign key (number_choose) 
--     references role_db (number_choose)

INSERT INTO category_db(category_name)
VALUES ('NODEJS'),('PHP'),('JAVA'),('ASP .NET'),('C#'),('TYPESCRIPT'),('RUBY'),('JAVASCRIPT'),('PYTHON');

CREATE TABLE save_job_db
(
	save_job_id int(11) not null auto_increment,
	user_id int(11),
	recruitment_id int(11),
    primary key (save_job_id),
    constraint foreign key (user_id) 
    references user_db (user_id) ,
    constraint foreign key (recruitment_id) 
    references recruitment_db (recruitment_id) 
);
