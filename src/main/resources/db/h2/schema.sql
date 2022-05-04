DROP TABLE IF EXISTS product;
CREATE TABLE product
(
    ID           INT PRIMARY KEY,
    product_name VARCHAR(32),
    stock        INT,
    version      VARCHAR(20)
);

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    `id`       VARCHAR(32),
    `pid`     INT         DEFAULT NULL,
    `user_id` VARCHAR(32) DEFAULT NULL,
    PRIMARY KEY (`id`)
)