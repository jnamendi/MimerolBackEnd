DROP TABLE IF EXISTS `restaurant_work_time`;
CREATE TABLE `restaurant_work_time`  (
  `res_work_time_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `restaurant_id` bigint(20) NULL DEFAULT NULL,
  `close_time` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `open_time` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `weekday` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `modified_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`res_work_time_id`) USING BTREE,
  INDEX `fk_rs_wt`(`restaurant_id`) USING BTREE,
  CONSTRAINT `fk_rs_wt` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;