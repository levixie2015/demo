CREATE TABLE `product` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(20) DEFAULT NULL,
  `stock` int(20) DEFAULT NULL,
  `version` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `product`(`id`, `product_name`, `stock`, `version`) VALUES (1, '大礼包', 0, 0);

CREATE TABLE `order` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `pid` int(20) DEFAULT NULL,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT `order`(`id`, `pid`, `user_id`) VALUES (1, 1, '544001464acb4c258469d7211fc31496');
INSERT `order`(`id`, `pid`, `user_id`) VALUES (2, 1, 'c311ce9b65b64d408aaffd93f39cd1c4');
INSERT `order`(`id`, `pid`, `user_id`) VALUES (3, 1, '99c681d579ab4234a0cad7648acc9ae4');
INSERT `order`(`id`, `pid`, `user_id`) VALUES (4, 1, '9b39bfbb8e634b94a891d48f15995b33');
INSERT `order`(`id`, `pid`, `user_id`) VALUES (5, 1, '86126ef1ed4c41a196e941ff9c6ad92f');
