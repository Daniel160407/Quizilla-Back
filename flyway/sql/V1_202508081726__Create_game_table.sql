CREATE TABLE `quizilla`.`game` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` TINYTEXT NOT NULL,
  `date_created` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));



ALTER TABLE `quizilla`.`category`
ADD COLUMN `game_id` INT NOT NULL AFTER `id`;


ALTER TABLE `quizilla`.`group`
ADD COLUMN `game_id` INT NOT NULL AFTER `id`;


ALTER TABLE `quizilla`.`quiz`
ADD COLUMN `game_id` INT NOT NULL AFTER `id`;
