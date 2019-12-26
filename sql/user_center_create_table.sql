USE `user_center` ;

-- -----------------------------------------------------
-- Table `user_center`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_center`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
  `wx_id` VARCHAR(64) NOT NULL COMMENT '微信ID',
  `wx_nickname` VARCHAR(45) NOT NULL COMMENT '微信昵称',
  `roles` VARCHAR(100) NOT NULL COMMENT '角色',
  `avatar_url` VARCHAR(255) NOT NULL COMMENT '头像',
  `create_time` DATETIME NOT NULL COMMENT '创建时间 ',
  `update_time` DATETIME NOT NULL COMMENT '修改时间',
  `bonus` INT NOT NULL COMMENT '积分',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_center`.`bouns_event_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_center`.`bouns_event_log` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'id ',
  `user_id` INT NOT NULL,
  `value` INT NOT NULL COMMENT '积分值',
  `event` VARCHAR(45) NOT NULL COMMENT '事件',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `description` VARCHAR(100) NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  INDEX `fk_bouns_event_log_user1_idx` (`user_id` ASC))
ENGINE = InnoDB;


