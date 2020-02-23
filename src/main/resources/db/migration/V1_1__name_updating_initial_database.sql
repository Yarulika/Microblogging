alter table users CHANGE `privacy` `is_private` int;

alter table users CHANGE `account_status` `is_blocked` int;

alter table users CHANGE `user_role` `user_role_id` int;

alter table users CHANGE `username` `username` VARCHAR(45);

alter table user_role CHANGE `name` `title` varchar(20);

rename TABLE `user_role` to `user_roles`;

alter table followers add column id int PRIMARY KEY auto_increment;

alter table followers CHANGE following_user_id follower_user_id int;

alter table comments add COLUMN content varchar(160);

alter table comment_likes CHANGE `creation_date` `liked_date` DATETIME;

alter table post_likes CHANGE `creation_date` `liked_date` DATETIME;

alter table posts CHANGE contents content  varchar(160);

alter table comment_likes add COLUMN id int primary key  auto_increment;

alter table post_likes add COLUMN id int primary key  auto_increment;