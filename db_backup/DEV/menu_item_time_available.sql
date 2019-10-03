use ofo;
DROP TABLE IF EXISTS `menu_item_time_available`;
CREATE TABLE `menu_item_time_available`  (
  `menu_item_time_available_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_item_id` bigint(20) NULL DEFAULT NULL,
  `close_time` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `open_time` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `weekday` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `modified_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`menu_item_time_available_id`) USING BTREE,
  INDEX `mi_ta`(`menu_item_id`) USING BTREE,
  CONSTRAINT `mi_ta` FOREIGN KEY (`menu_item_id`) REFERENCES `menu_item` (`menu_item_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE 'restaurant_area'
ADD 'zone_id' bigint(20)