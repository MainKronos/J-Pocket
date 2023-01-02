insert into user (`id`,`username`, `password`) values (1,'Admin', 'admin');
insert into user (`id`,`username`, `password`) values (2,'User', 'user');

insert into transaction (`title`, `amount`, `date`, `type`, `user_id`) values ('Salary', 1000, '2019-01-01', 0, 1);
insert into transaction (`title`, `amount`, `date`, `type`, `user_id`) values ('Rent', 500, '2019-01-01', 1, 2);
insert into transaction (`title`, `amount`, `date`, `type`, `user_id`) values ('Salary', 1000, '2019-01-01', 0, 2);
insert into transaction (`title`, `amount`, `date`, `type`, `user_id`) values ('pppp', 500, '2019-01-01', 1, 1);
