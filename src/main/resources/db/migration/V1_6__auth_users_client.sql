INSERT INTO client (client_id, client_secret, granted_authority, name, id, internal_id)
VALUES ('fd497f3c-026e-4fa6-abc0-8f2d98cd335b', '$2a$04$fJpu/IdbYN3mTv80pGGt4OhpUHExdH0CNQGHleCbpcsCsXlb4dDBe',
        'password,refresh_token', 'web', 1000, 'ec0c7f58-a3fa-4687-99c3-a1c678cfbc83');

ALTER TABLE `users`
ADD COLUMN ` client_id` INT NOT NULL DEFAULT 1000 AFTER `user_role_id`,
ADD INDEX `users_client_fk_idx` (` client_id` ASC);
ALTER TABLE `users`
ADD CONSTRAINT `users_client_fk`
  FOREIGN KEY (` client_id`)
  REFERENCES `users` (`user_id`);

ALTER TABLE `users`
CHANGE COLUMN `password` `password` VARCHAR(200) NOT NULL ;

INSERT INTO `users` (`user_id`, `username`, `password`, `email`, `is_private`, `avatar`, `is_blocked`, `creation_date`, `user_role_id`, ` client_id`)
VALUES ('1000', 'adminTool', '$2a$10$CY8/7Tl7AH6YUOoMhZxeDe3PZaM8RgknYBVoHF1EvRXbPIZyVyi3K', 'admin@microbloging.com', '1', 'Admin', '0', '2020-03-11 13:50:12', '1', '1000');


