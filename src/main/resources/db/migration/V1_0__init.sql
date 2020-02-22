-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


-- -----------------------------------------------------
-- Table `user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_role` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(10) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `privacy` CHAR(1) NOT NULL,
  `avatar` VARCHAR(45) NOT NULL,
  `account_status` CHAR(1) NOT NULL,
  `creation_date` DATETIME NOT NULL,
  `user_role` INT NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `user_role` (`user_role` ASC) VISIBLE,
  CONSTRAINT `users_ibfk_1`
    FOREIGN KEY (`user_role`)
    REFERENCES `user_role` (`role_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `blocked_users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `blocked_users` (
  `blocked_user_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  INDEX `blocked_user_id` (`blocked_user_id` ASC) VISIBLE,
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `blocked_users_ibfk_1`
    FOREIGN KEY (`blocked_user_id`)
    REFERENCES `users` (`user_id`),
  CONSTRAINT `blocked_users_ibfk_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `posts` (
  `post_id` INT NOT NULL AUTO_INCREMENT,
  `contents` DATETIME NOT NULL,
  `is_edited` INT NOT NULL,
  `owner_id` INT NOT NULL,
  `creation_date` DATETIME NOT NULL,
  `original_post_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`post_id`),
  INDEX `owner_id` (`owner_id` ASC) VISIBLE,
  INDEX `original_post_id` (`original_post_id` ASC) VISIBLE,
  CONSTRAINT `posts_ibfk_1`
    FOREIGN KEY (`owner_id`)
    REFERENCES `users` (`user_id`),
  CONSTRAINT `posts_ibfk_2`
    FOREIGN KEY (`original_post_id`)
    REFERENCES `posts` (`post_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comments` (
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `post_id` INT NOT NULL,
  `owner_id` INT NOT NULL,
  `creation_date` DATETIME NOT NULL,
  `comment_parent_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  INDEX `post_id` (`post_id` ASC) VISIBLE,
  INDEX `owner_id` (`owner_id` ASC) VISIBLE,
  INDEX `comment_parent_id` (`comment_parent_id` ASC) VISIBLE,
  CONSTRAINT `comments_ibfk_1`
    FOREIGN KEY (`post_id`)
    REFERENCES `posts` (`post_id`),
  CONSTRAINT `comments_ibfk_2`
    FOREIGN KEY (`owner_id`)
    REFERENCES `users` (`user_id`),
  CONSTRAINT `comments_ibfk_3`
    FOREIGN KEY (`comment_parent_id`)
    REFERENCES `comments` (`comment_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `comment_likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comment_likes` (
  `creation_date` DATETIME NOT NULL,
  `comment_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  INDEX `comment_id` (`comment_id` ASC) VISIBLE,
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `comment_likes_ibfk_1`
    FOREIGN KEY (`comment_id`)
    REFERENCES `comments` (`comment_id`),
  CONSTRAINT `comment_likes_ibfk_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `followers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `followers` (
  `following_date` DATETIME NOT NULL,
  `following_user_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  INDEX `FK_107` (`following_user_id` ASC) VISIBLE,
  INDEX `FK_110` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK_107`
    FOREIGN KEY (`following_user_id`)
    REFERENCES `users` (`user_id`),
  CONSTRAINT `FK_110`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `post_likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `post_likes` (
  `creation_date` DATETIME NOT NULL,
  `post_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  INDEX `post_id` (`post_id` ASC) VISIBLE,
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `post_likes_ibfk_1`
    FOREIGN KEY (`post_id`)
    REFERENCES `posts` (`post_id`),
  CONSTRAINT `post_likes_ibfk_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tags` (
  `tag_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`tag_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `post_tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `post_tags` (
  `post_id` INT NOT NULL,
  `tag_id` INT NOT NULL,
  INDEX `post_id` (`post_id` ASC) VISIBLE,
  INDEX `tag_id` (`tag_id` ASC) VISIBLE,
  CONSTRAINT `post_tags_ibfk_1`
    FOREIGN KEY (`post_id`)
    REFERENCES `posts` (`post_id`),
  CONSTRAINT `post_tags_ibfk_2`
    FOREIGN KEY (`tag_id`)
    REFERENCES `tags` (`tag_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
