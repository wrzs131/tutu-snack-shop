-- 购物车表
CREATE TABLE IF NOT EXISTS `cart` (
    `id` INT NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
    `user_id` INT NOT NULL COMMENT '用户ID',
    `product_id` INT NOT NULL COMMENT '商品ID',
    `quantity` INT NOT NULL DEFAULT 1 COMMENT '商品数量',
    `price` DECIMAL(10, 2) NOT NULL COMMENT '商品单价',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_product_id` (`product_id`),
    UNIQUE KEY `uk_user_product` (`user_id`, `product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';
