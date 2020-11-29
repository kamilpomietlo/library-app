-- create MySQL database script--
create table author (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB;
create table book (id bigint not null auto_increment, book_status varchar(255), cover_type varchar(255), isbn varchar(255), title varchar(255), year_of_release integer, genre_id bigint, publisher_id bigint, user_id bigint, primary key (id)) engine=InnoDB;
create table book_author (book_id bigint not null, author_id bigint not null, primary key (book_id, author_id)) engine=InnoDB;
create table genre (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB;
create table publisher (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB;
create table user (id bigint not null auto_increment, city varchar(255), country varchar(255), first_name varchar(255), home_number varchar(255), id_number varchar(255), last_name varchar(255), state varchar(255), street varchar(255), primary key (id)) engine=InnoDB;
alter table book add constraint FKm1t3yvw5i7olwdf32cwuul7ta foreign key (genre_id) references genre (id);
alter table book add constraint FKgtvt7p649s4x80y6f4842pnfq foreign key (publisher_id) references publisher (id);
alter table book add constraint FK1wxwagv6cm3vjrxqhmv884hir foreign key (user_id) references user (id);
alter table book_author add constraint FKbjqhp85wjv8vpr0beygh6jsgo foreign key (author_id) references author (id);
alter table book_author add constraint FKhwgu59n9o80xv75plf9ggj7xn foreign key (book_id) references book (id);