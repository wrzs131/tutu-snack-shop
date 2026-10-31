-- ============================================================
-- 图图零食店 后台管理端 数据库升级脚本
-- 作用：为 user 表增加角色/账号/密码/状态字段，并预置管理员、商家、骑手账号
-- 执行方式：在 MySQL 中选中 heima_shop 库后整体执行本脚本
-- ============================================================

-- 1. 扩展 user 表字段
ALTER TABLE `user`
    ADD COLUMN `role`     varchar(20)  NOT NULL DEFAULT 'customer' COMMENT '角色: admin/merchant/rider/customer',
    ADD COLUMN `username` varchar(50)  DEFAULT NULL COMMENT '员工登录账号',
    ADD COLUMN `password` varchar(100) DEFAULT NULL COMMENT '员工登录密码',
    ADD COLUMN `status`   varchar(20)  NOT NULL DEFAULT 'active'    COMMENT '账号状态: active/disabled';

-- 2. 账号唯一约束（普通微信用户 username 为空，不受影响）
ALTER TABLE `user` ADD UNIQUE KEY `uk_username` (`username`);

-- 3. 预置后台账号（演示用明文密码，生产环境请改为加密存储）
INSERT INTO `user` (`username`, `nickname`, `role`, `password`, `status`, `created_at`, `updated_at`) VALUES
('admin',    '系统管理员',   'admin',    'admin123',    'active', now(), now()),
('merchant', '图图零食店长', 'merchant', 'merchant123', 'active', now(), now()),
('rider',    '配送骑手',     'rider',    'rider123',    'active', now(), now());

-- 4. 将历史普通用户角色统一标记为 customer
UPDATE `user` SET `role` = 'customer' WHERE `role` IS NULL OR `role` = '';
