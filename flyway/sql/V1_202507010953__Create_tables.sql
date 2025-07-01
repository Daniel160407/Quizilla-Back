CREATE TABLE `quizilla`.`quiz` (
  `id` INT NOT NULL,
  `category_id` INT NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `question` LONGTEXT NOT NULL,
  `answer` LONGTEXT NOT NULL,
  `media_url` LONGTEXT NOT NULL,
  `points` DOUBLE NOT NULL,
  `hint` LONGTEXT NOT NULL,
  PRIMARY KEY (`id`));



CREATE TABLE `quizilla`.`category` (
  `id` INT NOT NULL,
  `name` TINYTEXT NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `quizilla`.`group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` TINYTEXT NOT NULL,
  `points` DOUBLE NOT NULL,
  PRIMARY KEY (`id`));


ALTER TABLE `quizilla`.`category`
CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT ;


ALTER TABLE `quizilla`.`quiz`
CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT ,
ADD INDEX `categoryId_idx` (`category_id` ASC) VISIBLE;
;
ALTER TABLE `quizilla`.`quiz`
ADD CONSTRAINT `categoryId`
  FOREIGN KEY (`category_id`)
  REFERENCES `quizilla`.`category` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

