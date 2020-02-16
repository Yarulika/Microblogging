ALTER TABLE users CHANGE `privacy` `is_private` int;

ALTER TABLE users CHANGE `account_status` `is_blocked` int;

ALTER TABLE users CHANGE `user_role` `is_admin` int;