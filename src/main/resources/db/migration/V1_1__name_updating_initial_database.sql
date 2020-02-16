ALTER TABLE users CHANGE `privacy` `is_private` int;

ALTER TABLE users CHANGE `account_status` `is_blocked` int;

ALTER TABLE users CHANGE `user_role` `is_admin` int;

ALTER TABLE followers add column id int PRIMARY KEY;

ALTER TABLE followers CHANGE following_user_id follower_user_id int;