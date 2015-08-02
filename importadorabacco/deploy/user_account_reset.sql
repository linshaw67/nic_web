use baccoimp_importadora;
DROP TABLE IF EXISTS `user_account_reset`;
CREATE TABLE IF NOT EXISTS `user_account_reset` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `email` VARCHAR(128) NOT NULL DEFAULT '' COMMENT 'user email',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '0 is not used, 1 is used',
  `token` VARCHAR(128) NOT NULL DEFAULT '' COMMENT 'token to reset password',
  `token_expire_time` TIMESTAMP NOT NULL DEFAULT 0 COMMENT 'token expire time',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY idx_email (`email`),
  UNIQUE KEY uniq_token (`token`),
  KEY idx_token_expire_time (`token_expire_time`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'user account info';
