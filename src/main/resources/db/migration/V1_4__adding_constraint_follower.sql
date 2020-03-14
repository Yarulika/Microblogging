ALTER TABLE followers MODIFY id INTEGER NOT NULL AUTO_INCREMENT;

ALTER TABLE `followers` ADD UNIQUE `unique_index`(`user_id`, `follower_user_id`);