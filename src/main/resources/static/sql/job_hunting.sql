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
VALUES ('RECRUITER'),
('CANDIDATE');

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

CREATE TABLE company_db
(
	company_id int(11) not null auto_increment,
    company_address varchar(255),
    company_email varchar(255),
    company_logo varchar(255),
    company_name varchar(255),
    company_phone_number varchar(255),
    company_description text,
    created_at datetime,
    updated_at datetime,
    delete_status int(1),
    deleted_at datetime,
    status int(11),
    primary key (company_id)
);

SET foreign_key_checks = 0;
INSERT INTO company_db(company_address,company_email,company_name,company_phone_number,company_description,status,company_logo,delete_status)
VALUES ('589 HCM','qwe@email.com','Comp1','0902222221','Company No.1',1, "img/flower-logo.png",0),
('344 MKO','ert4@email.com','Comp2','0705559990','Company No.2',1,"img/leaves-logo.png",0),
('666 IWP','dfg7@email.com','Comp3','0904444555','Company No.3',1,"img/leaves-logo2.png",0),
('2934 AAA','9pown@email.com','Comp4','0118889929','Company No.4',1,"img/sakura-logo.png",0),
('0023 OWJ','bnm@email.com','Comp5','0905553332','Company No.5',1,"img/tea-leaves-logo.png",0),
('738/2 HEN','lkj1@email.com','Comp6','0703336665','Company No.6',1,"img/tropical-leaves-logo.png",0),
('8222 JQK','zxc3@email.com','Comp7','01012444566','Company No.7',1,"img/tropical-leaves-logo2.png",0),
('21323 BBOIK','dedkoj0083@email.com','Comp8','02839900292','Company No.8',1,"img/meal-logo.png",0);



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
    verification_status int(1),
    token varchar(255),
    token_expiry_date datetime,
    created_at datetime,
    role_id int(11),
    company_id int (11),
    updated_at datetime,
    delete_status int(1),
    deleted_at datetime,
    primary key (user_id),
    constraint foreign key (role_id) references role_db (role_id),
    constraint foreign key (company_id) references company_db (company_id)
);
INSERT INTO user_db(user_full_name,user_email,user_address,user_password,phone_number,user_description,verification_status,role_id,company_id,delete_status)
VALUES ('Nguyen Thi Xuan','xuan001@email.com','123 HCM','{noop}123456','0010010011','tai khoan cua Xuan',1,1,1,0),
('Tran Minh Tuan','tuan002@email.com','567 HN','{noop}123456','0020020022','tai khoan cua Tuan',1,1,2,0),
('Lam Anh Dung','dung003@email.com','255 HP','{noop}123456','0030030033','tai khoan cua Dung',1,1,3,0),
('Ngo Van Sinh','sinh004@email.com','887 QB','{noop}123456','0040040044','tai khoan cua Sinh',1,1,4,0),
('Mai Minh ANh','anh003@email.com','7425 QN','{noop}123456','0030050033','tai khoan cua Minh Anh',1,2,5,0),
('Hoan Mai Huong','huong001@email.com','92883 HCM','{noop}123456','0010060011','tai khoan cua Huong',1,2,6,0),
('Lai Son Nam','nam006@email.com','567 BH','{noop}123456','0090090099','tai khoan cua Nam',1,2,7,0),
('Hoang Tuyet Van','van007@email.com','28386 PQND','{noop}123456','009010010','tai khoan cua Van',1,2,8,0);


CREATE TABLE job_description_db
(
	job_description_id int(11) not null auto_increment,
    job_description_address varchar(255),
    quantity int(11),
    experience varchar(255),
    job_description_rank varchar(255),
    salary varchar(255),
    title varchar(255),
    position varchar(255),
    job_description_type varchar(255),
	description varchar(255),
    deadline varchar(255),
    created_at datetime,
    updated_at datetime,
    delete_status int(1),
    deleted_at datetime,
    status int(11),
	view int(11),
    company_id int(11),
    category_id int(11),
    primary key (job_description_id),
    constraint foreign key (company_id) references company_db(company_id),
    constraint foreign key (category_id) references category_db(category_id)
);

INSERT INTO job_description_db(job_description_address,quantity,experience,job_description_rank,salary,title,position,job_description_type,description,status,deadline,view,company_id,category_id,delete_status)
VALUES ('001 HHP',2,'3y','high',15000,'Looking for A','Developer','Full Time','Good Job No.1',1,'2023-09-30',10,1,1,0),
('112 MMW',1,'5y','low',25000,'Looking for A,B','Developer','Full Time','High-Quality Job',1,'2024-02-01',8,1,1,0),
('223 IIO',5,'1y','high',10000,'Looking for A','Tester','Part Time','Easy Job',1,'2023-08-17',10,2,1,0),
('334 AAK',2,'1y','medium',12000,'Looking for C','Infrastruture','Full Time','Friendly Envi',1,'2023-10-13',3,3,2,0),
('445 IWN',4,'3y','low',18000,'Looking for C,D','Tester','Full Time','Easy-going Colleague',1,'2024-01-25',6,1,2,0),
('556 UUE',7,'4y','medium',20000,'Looking for A,C','Operator','Part Time','Competative Salary',1,'2023-12-23',16,4,3,0),
('667 JJQ',5,'3y','high',16500,'Looking for B','Developer','Full Time','Decent Benefit',1,'2023-11-30',5,3,4,0);

CREATE TABLE cv_db
(
	cv_id int(11) not null auto_increment,
    cv_file_name varchar(255),
    user_id int(11),
    updated_at datetime,
    delete_status int(1),
    deleted_at datetime,
    primary key (cv_id),
    constraint foreign key (user_id) 
    references user_db (user_id) 
);

INSERT INTO cv_db(cv_file_name,user_id,delete_status)
VALUES ('Cv Xuan',1,0),('Cv Tuan',2,0),('Cv Dung',3,0),('Cv Sinh',4,0),('Cv Anh',5,0),('Cv Huong',6,0),('Cv Nam',7,0),('Cv Xuan2',8,0);
-- ('Cv Xuan2',8),('Cv Nam1',14),('Cv Xuan3',8),('Cv Sinh1',11),('Cv Xuan4',8),('Cv Sinh2',11)

CREATE TABLE candidate_company_db
(
	candidate_company_id int(11) not null auto_increment,
	user_id int(11),
	company_id int(11),
    primary key (candidate_company_id),
    constraint foreign key (user_id) references user_db (user_id),
    constraint foreign key (company_id) references company_db (company_id) 
);

INSERT INTO candidate_company_db(user_id,company_id)
VALUES (5,1),(5,2),(5,3),(6,1),(6,4),(7,1),(7,2),(8,5),(8,6),(6,7),(7,3),(6,8);



CREATE TABLE applied_job_db
(
	applied_job_id int(11) not null auto_increment,
	cv_name varchar(255),
    additional_info varchar(255),
    created_at datetime,
	user_id int(11),
	job_description_id int(11),
    status int(11),
    primary key (applied_job_id),
    constraint foreign key (user_id) 
    references user_db (user_id) ,
    constraint foreign key (job_description_id) 
    references job_description_db (job_description_id) 
);
INSERT INTO applied_job_db(cv_name,additional_info,user_id,job_description_id)
VALUES ('Cv Xuan','Apply for A',8,12),
('Cv Huong','Apply for A,B',13,8),
('Cv Tuan','Apply for C',9,12),
('Cv Sinh2','Apply for A',11,12),
('Cv Dung','Apply for A,C',10,9),
('Cv Huong','Apply for B',13,9),
('Cv Huong','Apply for B,C',13,12),
('Cv Huong1','Apply for B,A',13,6),
('Cv Dung1','Apply for B,A',10,6);

CREATE TABLE user_job_db
(
	user_job_id int(11) not null auto_increment,
	user_id int(11),
	job_description_id int(11),
    primary key (user_job_id),
    constraint foreign key (user_id) references user_db (user_id) ,
    constraint foreign key (job_description_id) references job_description_db (job_description_id) 
);

INSERT INTO user_job_db(user_id,job_description_id)
VALUES (5,1),(5,2),(5,3),(6,1),(6,4),(7,1),(7,2),(8,3),(8,6),(5,7),(0,5),(7,7);

-- INSERT INTO user_job_db(user_id,job_description_id)
-- VALUES (0,1),(0,2),(0,3),(0,1),(0,4),(0,1),(0,2),(0,3),(0,6),(0,7),(0,5),(0,7);