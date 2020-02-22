ALTER TABLE users CHANGE `privacy` `is_private` int;

ALTER TABLE users CHANGE `account_status` `is_blocked` int;

ALTER TABLE users CHANGE `user_role` `user_role_id` int;

ALTER TABLE users CHANGE `username` `username` VARCHAR(45);

ALTER TABLE user_role CHANGE `name` `title` varchar(20);

RENAME TABLE `user_role` TO `user_roles`;

ALTER TABLE followers add column id int PRIMARY KEY;

ALTER TABLE followers CHANGE following_user_id follower_user_id int;

ALTER TABLE comments ADD COLUMN content varchar(160);

ALTER TABLE comment_likes CHANGE `creation_date` `liked_date` DATETIME;

ALTER TABLE post_likes CHANGE `creation_date` `liked_date` DATETIME;

ALTER TABLE posts CHANGE contents content  varchar(160);

ALTER TABLE comment_likes ADD COLUMN id int primary key  auto_increment;

ALTER TABLE post_likes ADD COLUMN id int primary key  auto_increment;