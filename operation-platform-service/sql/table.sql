CREATE TABLE `system_user` (
    `id` INT (11) NOT NULL AUTO_INCREMENT,
    `user_name` VARCHAR (20) NOT NULL,
    `real_name` VARCHAR (20) NOT NULL,
    `password` VARCHAR (32) NOT NULL,
    `description` VARCHAR (255) DEFAULT NULL,
    `email` VARCHAR (255) DEFAULT NULL,
    `mobile` VARCHAR (255) DEFAULT NULL,
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_user_name` (`user_name`)
) ENGINE = INNODB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8;