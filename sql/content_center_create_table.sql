
USE `content_center` ;

-- -----------------------------------------------------
-- Table `content_center`.`share`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `content_center`.`share` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `title` VARCHAR(200) NOT NULL COMMENT '标题',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '修改时间',
  `is_original` TINYINT(1) NOT NULL COMMENT '是否原创',
  `autor` VARCHAR(45) NOT NULL,
  `cover` VARCHAR(255) NOT NULL,
  `summary` VARCHAR(255) NULL,
  `price` INT NOT NULL,
  `download_url` VARCHAR(255) NOT NULL,
  `buy_count` INT NOT NULL,
  `show_flag` TINYINT(1) NOT NULL,
  `audit_status` VARCHAR(10) NULL,
  `reason` VARCHAR(200) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `content_center`.`mid_user_share`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `content_center`.`mid_user_share` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `share_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_mid_user_share_user1_idx` (`user_id` ASC, `share_id` ASC),
  INDEX `fk_mid_user_share_share1_idx` (`share_id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `content_center`.`notice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `content_center`.`notice` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(255) NOT NULL,
  `show_flag` TINYINT(1) NOT NULL,
  `create_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

