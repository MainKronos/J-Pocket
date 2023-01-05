insert into user (`id`,`username`, `password`) values (1,'admin', 'admin');
insert into user (`id`,`username`, `password`) values (2,'user', 'user');

insert into transaction (`title`, `amount`, `date`, `type`, `user_id`) values ('Stipendio', 1000, '2019-01-01', 0, 1);
insert into transaction (`title`, `amount`, `date`, `type`, `user_id`) values ('Spesa', 500, '2019-01-01', 1, 2);
insert into transaction (`title`, `amount`, `date`, `type`, `user_id`) values ('Stipendio', 1000, '2019-01-01', 0, 2);
insert into transaction (`title`, `amount`, `date`, `type`, `user_id`) values ('Spesa', 500, '2019-01-01', 1, 1);
