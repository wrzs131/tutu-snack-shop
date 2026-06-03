-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: heima_shop
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
  `user_id` int NOT NULL COMMENT '用户ID',
  `product_id` int NOT NULL COMMENT '商品ID',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '商品数量',
  `price` decimal(10,2) NOT NULL COMMENT '商品单价',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_product` (`user_id`,`product_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='购物车表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'本店优惠','2026-03-16 11:01:17','2026-03-16 11:01:17'),(2,'新生军训必备产品','2026-03-16 11:01:17','2026-03-16 11:01:17'),(3,'果酒类','2026-03-16 11:01:17','2026-03-16 11:01:17'),(4,'帮泡服务','2026-03-16 11:01:17','2026-03-16 11:01:17'),(5,'早餐','2026-03-16 11:01:17','2026-03-16 11:01:17'),(6,'饮料','2026-03-16 11:01:17','2026-03-16 11:01:17'),(7,'小零食','2026-03-16 11:01:17','2026-03-16 11:01:17'),(8,'泡面','2026-03-16 11:01:17','2026-03-16 11:01:17'),(10,'默认分类','2026-03-22 02:01:21','2026-03-22 02:01:21');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,1,10.00,'已取消','2026-03-16 11:01:59','2026-06-02 02:18:13'),(3,1,29.90,'已完成','2026-03-21 08:05:45','2026-03-24 08:34:14'),(4,1,21.50,'已取消','2026-06-02 00:56:59','2026-06-02 02:19:20'),(5,1,98.00,'待确认','2026-06-02 02:19:00','2026-06-02 02:19:00'),(6,1,127.90,'待确认','2026-06-02 02:20:23','2026-06-02 02:20:23'),(7,1,23.00,'待确认','2026-06-02 02:47:38','2026-06-02 02:47:38'),(8,1,38.90,'已取消','2026-06-02 08:03:55','2026-06-02 08:06:04');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_item_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  CONSTRAINT `order_item_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,1,1,2,4.50,'2026-03-16 11:02:07'),(2,1,2,1,1.00,'2026-03-16 11:02:07'),(3,3,3,1,29.90,'2026-03-24 08:40:20'),(4,4,1,3,4.50,'2026-06-02 00:56:59'),(5,4,2,8,1.00,'2026-06-02 00:56:59'),(6,5,1,2,4.50,'2026-06-02 02:19:00'),(7,5,2,1,1.00,'2026-06-02 02:19:00'),(8,5,4,1,88.00,'2026-06-02 02:19:00'),(9,6,1,2,4.50,'2026-06-02 02:20:23'),(10,6,2,1,1.00,'2026-06-02 02:20:23'),(11,6,3,1,29.90,'2026-06-02 02:20:23'),(12,6,4,1,88.00,'2026-06-02 02:20:23'),(13,7,6,1,7.00,'2026-06-02 02:47:38'),(14,7,7,4,2.00,'2026-06-02 02:47:38'),(15,7,8,2,1.50,'2026-06-02 02:47:38'),(16,7,9,1,5.00,'2026-06-02 02:47:38'),(17,8,2,2,1.00,'2026-06-02 08:03:55'),(18,8,3,1,29.90,'2026-06-02 08:03:55'),(19,8,6,1,7.00,'2026-06-02 08:03:55');
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `original_price` decimal(10,2) DEFAULT NULL,
  `stock` int NOT NULL,
  `image` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `category_id` int NOT NULL,
  `discount` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'康师傅•海嗨',4.50,5.00,63,'/static/mian.png',1,'9.0折','2026-03-16 11:01:44','2026-03-22 02:18:43'),(2,'米饼',1.00,2.00,694,'/static/mian.png',1,'5.0折','2026-03-16 11:01:44','2026-03-22 02:18:43'),(3,'军训必备套餐',29.90,49.90,1794,'/static/mian.png',2,'6.0折','2026-03-16 11:01:44','2026-03-22 02:18:43'),(4,'果酒套餐',88.00,128.00,50,'/static/mian.png',3,'6.9折','2026-03-16 11:01:44','2026-03-22 02:18:43'),(6,'吐司面包',7.00,7.00,20,'/static/mian.png',5,'无折扣','2026-06-02 02:42:52','2026-06-02 02:42:52'),(7,'可乐',2.00,2.00,30,'/static/mian.png',6,'无折扣','2026-06-02 02:46:11','2026-06-02 02:46:11'),(8,'薯片',1.50,1.50,40,'/static/mian.png',7,'无折扣','2026-06-02 02:46:11','2026-06-02 02:46:11'),(9,'红烧牛肉面',5.00,5.00,45,'/static/mian.png',8,'无折扣','2026-06-02 02:46:11','2026-06-02 02:46:11'),(10,'巧克力',5.00,5.00,20,'/static/mian.png',7,'无折扣','2026-06-02 08:26:38','2026-06-02 08:26:38'),(11,'果冻',1.00,1.00,50,'/static/mian.png',7,'无折扣','2026-06-02 08:26:38','2026-06-02 08:26:38');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `logo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `business_hours` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
INSERT INTO `shop` VALUES (1,'图图零食店','/static/shop-header.jpg','休息中','12:00-次日1:00','13617761234','龙翔大道锤锤大学','2026-03-16 11:01:04','2026-03-22 02:18:03');
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `openid` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信用户唯一标识',
  `nickname` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatar` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'o03dP15dIh9dfQpa-HZ9HA0Hbu5g','美食探索者','13400138001','https://pcapi-xiaotuxian-front-devtest.itheima.net/miniapp/uploads/avatar_3.jpg','2026-03-16 11:01:52','2026-03-22 02:20:53'),(2,'test_openid_123456','微信用户_test_ope',NULL,NULL,'2026-03-21 09:07:16','2026-03-21 09:07:16');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-02 16:49:05
