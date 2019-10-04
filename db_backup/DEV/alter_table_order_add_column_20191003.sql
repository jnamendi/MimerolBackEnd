  ALTER TABLE `order`  ADD `discount_percent` bigint(20) NULL DEFAULT NULL;
  ALTER TABLE `order`  ADD `charge_fee` decimal(10, 2) NULL DEFAULT NULL;