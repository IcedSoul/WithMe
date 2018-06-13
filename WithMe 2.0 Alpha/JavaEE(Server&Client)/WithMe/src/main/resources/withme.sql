create database withme;
use withme;
create table user_detail(
	user_detail_id int not null,
	user_detail_name varchar(20) not null,
	user_detail_nickname varchar(20) not null,
	user_detail_password varchar(20) not null,
	user_mail_number varchar(30),
	user_phone_number varchar(20),
	user_register_time datetime not null,
	primary key (user_detail_id),
	unique(user_detail_name)
);
create table user (
	user_id int not null,
	user_name varchar(20) not null,
	user_nickname varchar(20),
	user_is_online int not null,
	user_role int not null,
	user_img_path varchar(100),
	user_relations varchar(5000),
	user_groups varchar(5000),
	primary key (user_id),
	unique(user_name),
	foreign key (user_id) references user_detail(user_detail_id)
);
create table user_relation (
	user_id_a int not null,
	user_id_b int not null,
	relation_status int not null,
	relation_start datetime,
	primary key(user_id_a,user_id_b),
	foreign key (user_id_a) references user_main(user_id),
	foreign key (user_id_b) references user_main(user_id)
);
create table message (
	id int not null,
	from_id int not null,
	to_id int not null,
	content varchar(5000) not null,
	type int not null,
	time datetime not null,
	is_transport int not null,
	primary key(id),
	foreign key (from_id) references user_main(user_id),
	foreign key (to_id) references user_main(user_id)
);
create table groups(
	id int not null,
	group_id varchar(10) not null,
	group_name varchar(20) not null,
	group_creater_id int not null,
	group_create_time datetime not null,
	group_introduction varchar(50),
	group_user_count int not null,
	group_members varchar(5000),
	primary key(id),
	unique(group_id),
	foreign key (group_creater_id) references user_main(user_id)
);
create table user_group_relation(
	user_id int not null,
	group_id int not null,
	group_level int not null,
	group_user_nickname varchar(20) not null,
	enter_group_time datetime not null,
	primary key(user_id,group_id),
	foreign key (user_id) references user_main(user_id),
	foreign key (group_id) references group_main(id)
);