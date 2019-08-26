SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for restaurant_area
-- ----------------------------
DROP TABLE IF EXISTS `restaurant_area`;
CREATE TABLE `restaurant_area`  (
  `restaurant_area_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `restaurant_id` bigint(20) NOT NULL,
  `district_id` bigint(20) NOT NULL,
  PRIMARY KEY (`restaurant_area_id`) USING BTREE,
  INDEX `fk_res_area`(`restaurant_id`) USING BTREE,
  INDEX `fk_dis_res`(`district_id`) USING BTREE,
  CONSTRAINT `fk_res_area` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_dis_res` FOREIGN KEY (`district_id`) REFERENCES `district` (`district_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 389 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
