DROP TABLE IF EXISTS `restaurant_delivery_cost`;
CREATE TABLE `restaurant_delivery_cost`  (
  `restaurant_delivery_cost_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `restaurant_id` bigint(20) NULL DEFAULT NULL,
  `district_id` bigint(20) NULL DEFAULT NULL,
  `delivery_cost` decimal(10,2) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `modified_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`restaurant_delivery_cost_id`) USING BTREE,
  INDEX `fk_rs_r`(`restaurant_id`) USING BTREE,
  INDEX `fk_rs_d`(`restaurant_id`) USING BTREE,
  CONSTRAINT `fk_rs_r` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rs_d` FOREIGN KEY (`district_id`) REFERENCES `district` (`district_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)c

SET FOREIGN_KEY_CHECKS = 1;