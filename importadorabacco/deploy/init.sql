CREATE DATABASE IF NOT EXISTS `baccoimp_importadora` CHARACTER SET 'utf8';
use baccoimp_importadora;

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
  `quantity` INT NOT NULL DEFAULT 0 COMMENT 'product quantity',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY uniq_user_product (`user_id`, `product_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'user cart info';

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `name` VARCHAR(128) NOT NULL DEFAULT '' COMMENT 'product name',
  `cat_id` INT NOT NULL DEFAULT 0 COMMENT 'product category id',
  `cat_name` VARCHAR(64) NOT NULL DEFAULT '' COMMENT 'product category name',
  `price` DECIMAL NOT NULL DEFAULT 0 COMMENT 'price',
  `image_url` VARCHAR(128) NOT NULL DEFAULT '' COMMENT 'product image url',
  `desc` TEXT NOT NULL COMMENT 'product desc',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY idx_cat_id (`cat_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'product info';

DROP TABLE IF EXISTS `user_order`;
CREATE TABLE IF NOT EXISTS `user_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `user_id` BIGINT NOT NULL DEFAULT 0 COMMENT 'user account id',
  `name` VARCHAR(64) NOT NULL DEFAULT '' COMMENT 'user name',
  `mobile` VARCHAR(32) NOT NULL DEFAULT '' COMMENT 'user mobile',
  `email` VARCHAR(128) NOT NULL DEFAULT '' COMMENT 'user mobile',
  `address1` VARCHAR(1024) DEFAULT '' COMMENT 'user address',
  `address2` VARCHAR(1024) DEFAULT '' COMMENT 'user address',
  `city` VARCHAR(64) NOT NULL DEFAULT '' COMMENT 'user city',
  `zip_code` VARCHAR(32) NOT NULL DEFAULT '' COMMENT 'zip code',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY idx_user_id (`user_id`),
  KEY idx_create_time(`create_time`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'user order info';

DROP TABLE IF EXISTS `order_cart`;
CREATE TABLE IF NOT EXISTS `order_cart` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `order_id` BIGINT NOT NULL DEFAULT 0 COMMENT 'user account id',
  `product_id` BIGINT NOT NULL DEFAULT 0 COMMENT 'product id',
  `quantity` INT NOT NULL DEFAULT 0 COMMENT 'product quantity',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY idx_order_id (`order_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'order cart info';


INSERT INTO product (name, cat_id, cat_name, price, image_url, `desc`) VALUES
  ("Olio di Arachide", 1, "Olio di Semi", 0, "/image/products/Olio_di_Semi/Olio_di_Arachide/mid.jpg", "demowine 1"),
  ("Grana padano", 2, "Formaggi", 0, "/image/products/Formaggi/Grana_padano/mid.jpg", "demowine 2"),
  ("Parmigiano Reggiano", 2, "Formaggi", 0, "/image/products/Formaggi/Parmigiano_Reggiano/mid.jpg", "demowine 2"),
  ("Filetti di Acciughe sott’olio", 3, "Specialita", 0, "/image/products/Specialita/Filetti_di_Acciughe_sott_olio/mid.jpg", "demowine 2"),
  ("Aceto Balsamico di Modena IGP 'ORO NOBILE'", 4, "Aceti", 0, "/image/products/Aceti/Aceto_Balsamico_di_Modena_IGP_ORO_NOBILE_/mid.jpg", "demowine 2"),
  ("Aceto Balsamico di Modena IGP 'TRESOR'", 4, "Aceti", 0, "/image/products/Aceti/Aceto_Balsamico_di_Modena_IGP_TRESOR_/mid.jpg", "demowine 2"),
  ("Aceto di Vino Rosso 'Antica Madre'", 4, "Aceti", 0, "/image/products/Aceti/Aceto_di_Vino_Rosso_Antica_Madre_/mid.jpg", "demowine 2"),
  ("Olio Extra Vergine di Oliva", 5, "Olio Di Oliva", 0, "/image/products/Olio_Di_Oliva/Olio_Extra_Vergine_di_Oliva/mid.jpg", "demowine 2"),
  ("Olio Extra Vergine di Oliva 2", 5, "Olio Di Oliva", 0, "/image/products/Olio_Di_Oliva/Olio_Extra_Vergine_di_Oliva_2/mid.jpg", "demowine 2"),
  ("Olio Extravergine di Oliva Taggiasca", 5, "Olio Di Oliva", 0, "/image/products/Olio_Di_Oliva/Olio_Extravergine_di_Oliva_Taggiasca/mid.jpg", "demowine 2"),
  ("Lasgna liscia Napoletana", 6, "Pasta", 0, "/image/products/Pasta/Lasgna_liscia_Napoletana/mid.jpg", "demowine 1"),
  ("Paccheri Lisci", 6, "Pasta", 0, "/image/products/Pasta/Paccheri_Lisci/mid.jpg", "demowine 1"),
  ("Penne a Zita Rigate", 6, "Pasta", 0, "/image/products/Pasta/Penne_a_Zita_Rigate/mid.jpg", "demowine 1"),
  ("Rigatoni", 6, "Pasta", 0, "/image/products/Pasta/Rigatoni/mid.jpg", "demowine 1");

