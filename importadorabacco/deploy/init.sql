CREATE DATABASE IF NOT EXISTS `importadora` CHARACTER SET 'utf8';
use importadora;

DROP TABLE IF EXISTS `user_account`;
CREATE TABLE IF NOT EXISTS `user_account` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `email` VARCHAR(128) NOT NULL DEFAULT '' COMMENT 'user email',
  `pwd` VARCHAR(128) NOT NULL DEFAULT '' COMMENT 'password',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '0 is frozen, 1 is activity',
  `token` VARCHAR(128) NOT NULL DEFAULT '' COMMENT 'token to confirm register',
  `token_expire_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'token expire time',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_login_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'last login time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_email` (`email`),
  KEY idx_status (`status`),
  KEY idx_token_expire_time (`token_expire_time`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'user account info';

DROP TABLE IF EXISTS `user_cart`;
CREATE TABLE IF NOT EXISTS `user_cart` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `user_id` BIGINT NOT NULL DEFAULT 0 COMMENT 'user account id',
  `product_id` BIGINT NOT NULL DEFAULT 0 COMMENT 'product id',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY uniq_user_product (`user_id`, `product_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'user cart info';

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `name` VARCHAR(128) NOT NULL DEFAULT '' COMMENT 'product name',
  `cat` VARCHAR(64) NOT NULL DEFAULT '' COMMENT 'product category',
  `image_url` VARCHAR(128) NOT NULL DEFAULT '' COMMENT 'product image url',
  `desc` VARCHAR(1024) NOT NULL DEFAULT '' COMMENT 'product desc',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY idx_cat (`cat`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'product info';

DROP TABLE IF EXISTS `user_order`;
CREATE TABLE IF NOT EXISTS `user_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `user_id` BIGINT NOT NULL DEFAULT 0 COMMENT 'user account id',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY idx_user_id (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'user order info';

DROP TABLE IF EXISTS `order_cart`;
CREATE TABLE IF NOT EXISTS `order_cart` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `order_id` BIGINT NOT NULL DEFAULT 0 COMMENT 'user account id',
  `product_id` BIGINT NOT NULL DEFAULT 0 COMMENT 'product id',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY idx_order_id (`order_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'order cart info';
