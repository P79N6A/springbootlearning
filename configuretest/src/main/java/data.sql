create table student
(
	id bigint auto_increment,
	name varchar(32) not null,
	age int not null,
	constraint student_pk
		primary key (id)
)
comment '学生';



