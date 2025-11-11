CREATE DATABASE  IF NOT EXISTS "bkartisan" /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bkartisan`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '9c654879-f1a2-11ee-afbb-da5310e8748c:1-3203,
a06e67a6-74ec-11ef-9987-6e2d6aab3351:1-135';

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `buyer` varchar(255) NOT NULL,
  `discount_id` int DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `order_id` varchar(12) DEFAULT NULL,
  `parent_id` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=269 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `parent` int DEFAULT NULL,
  `level` int DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `isSelected` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (8,'Trang sức & Phụ kiện',0,1,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',1),(10,'Đồ thủ công',0,1,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',1),(11,'Đồ cổ',0,1,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',1),(13,'Nhà cửa & Trang trí',0,1,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',1),(31,'Nhẫn',8,2,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(32,'Dây chuyển',8,2,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(33,'Vòng tay',8,2,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(34,'Khuyên tai',8,2,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(35,'Nhẫn midi',31,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(36,'Nhẫn khắc dấu',31,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(37,'Nhẫn đôi',31,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(38,'Nhẫn đính đá',31,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(39,'Nhẫn cưới',31,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(41,'Dây chuyền pha lê',32,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(42,'Dây chuyền đá',32,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(43,'Dây chuyền cameo',32,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(44,'Mặt dây chuyền',32,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(45,'Dây chuyền quyến rũ',32,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(46,'Vòng tay đính cườm',33,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(47,'Vòng tay chuỗi',33,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(48,'Vòng tay vải',33,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(49,'Vòng tay cứng',33,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(50,'Vòng tay đính cườm',33,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(51,'Vòng tay chuỗi',33,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(52,'Vòng tay vải',33,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(53,'Vòng tay cứng',33,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(54,'Khuyên tai vòng',33,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(55,'Khuyên tai chùm',33,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(56,'Khuyên  tai đơn',33,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(57,'Khuyên tai treo',33,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(58,'Đồ gia dụng',13,2,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(59,'Nội thất',13,2,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(60,'Trang trí nội thất',13,2,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(61,'Chén',58,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(62,'Ly',58,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(63,'Đũa',58,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(64,'Ghế',59,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(65,'Bàn',59,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(66,'Đèn',59,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(70,'Trang trí tường',60,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(71,'Trang trí bàn & kệ',60,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(72,'Trang trí phòng ngủ',60,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(73,'Trang trí bếp',60,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(74,'Trang trí phòng khách',60,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(77,'Board game',75,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(78,'Trò chơi thẻ bài',75,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(79,'Trò chơi xếp hình',75,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(80,'Búp bê',76,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(82,'Thú nhồi bông',76,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(84,'Đồ chơi trẻ em',76,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-fathers-day-L1.jpg?v=1696278259',0),(85,'Quà',0,1,'https://i.etsystatic.com/site-assets/gift-mode/gift-mode-editorial-banner.png',0),(86,'Quà theo lễ',85,2,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-christmas-L1.jpg?v=1696278259',0),(87,'Quà cho nam',85,2,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-him-L1.jpg?v=1696278259',0),(88,'Quà cho nữ',85,2,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-her-L1.jpg?v=1696278259',0),(89,'Quà cho trẻ em',85,2,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-kids-L1.jpg?v=1696278259',0),(90,'Quà cho gia đình',85,2,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-housewarmings-L1.jpg?v=1696278259',0),(91,'Quà đặc biệt',85,2,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-perso-L1.jpg?v=1696278259',0),(92,'Quốc tế phụ nữ',86,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-mothers-day-L1.jpg?v=1696278259',0),(93,'Ngày nhà giáo',86,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-teachers-L1.jpg?v=1696278259',0),(94,'Sinh nhật',86,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-housewarmings-L1.jpg?v=1696278259',0),(95,'Lễ tình nhân',86,3,'https://i.etsystatic.com/5167678/r/il/225a4e/3242578574/il_340x340.3242578574_nngj.jpg',0),(96,'Tết',86,3,'https://thuongdinhyen.com/uploads/images/anh-san-pham/2023-11-13-16-32-36-b-radius8-smoothing3-.jpg',0),(97,'Quốc tế thiếu nhi',86,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-kids-L1.jpg?v=1696278259',0),(98,'Giáng sinh',86,3,'https://i.etsystatic.com/site-assets/gift-category-pages/L0/gifts-for-christmas-L1.jpg?v=1696278259',0);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `commentId` int NOT NULL AUTO_INCREMENT,
  `productId` int NOT NULL,
  `content` varchar(255) NOT NULL,
  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `writer` varchar(255) NOT NULL,
  `numberOfUpvotes` int DEFAULT '0',
  `numberOfDownVotes` int DEFAULT '0',
  `parentId` int DEFAULT NULL,
  `numberOfStars` int DEFAULT NULL,
  `isDeleted` tinyint DEFAULT NULL,
  PRIMARY KEY (`commentId`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `options`
--

DROP TABLE IF EXISTS `options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `options` (
  `optionId` int NOT NULL AUTO_INCREMENT,
  `optionName` varchar(255) DEFAULT NULL,
  `parentOptionId` int DEFAULT NULL,
  PRIMARY KEY (`optionId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `options`
--

LOCK TABLES `options` WRITE;
/*!40000 ALTER TABLE `options` DISABLE KEYS */;
INSERT INTO `options` VALUES (1,'Màu sắc',0),(2,'Kích thước',0),(3,'Đỏ',1),(4,'Xanh dương',1),(5,'Xanh lá',1),(6,'Vàng',1),(7,'Tím',1),(8,'Nâu',1),(9,'S',2),(10,'M',2),(11,'L',2),(12,'XL',2),(13,'2XL',2),(14,'Đen',1),(15,'Hồng',1);
/*!40000 ALTER TABLE `options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `orderId` varchar(10) NOT NULL,
  `seller` varchar(50) DEFAULT NULL,
  `createAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('Chờ kết quả VNPay','Chờ xác nhận','Đang xử lý','Chờ lấy hàng','Đang vận chuyển','Đã giao','Yêu cầu trả hàng','Từ chối trả hàng','Đã trả hàng','Đã hủy') DEFAULT 'Chờ xác nhận',
  `paymentMethod` enum('momo','paypal','vnpay') DEFAULT 'vnpay',
  `hasGift` tinyint(1) DEFAULT '0',
  `totalPrice` varchar(45) DEFAULT NULL,
  `buyer` varchar(100) NOT NULL,
  `shipPrice` int DEFAULT '0',
  `discountPrice` int DEFAULT '0',
  `isReturn` tinyint(1) DEFAULT '0',
  `bankCode` varchar(50) DEFAULT NULL,
  `discountId` int DEFAULT NULL,
  `commonId` varchar(12) NOT NULL,
  `nation` varchar(50) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `isFinished` tinyint(1) DEFAULT '0',
  `numPhone` varchar(20) DEFAULT NULL,
  `buyerName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`orderId`),
  UNIQUE KEY `orderId_UNIQUE` (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `productId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `price` int NOT NULL DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  `category` int DEFAULT NULL,
  `material` varchar(45) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `isOnSale` tinyint(1) DEFAULT '0',
  `seller` varchar(50) DEFAULT NULL,
  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `approvedAt` timestamp NULL DEFAULT NULL,
  `approver` varchar(50) DEFAULT NULL,
  `status` enum('Đang bán','Tạm ngưng','Vi phạm','Đang duyệt','Từ chối','Đã xóa') DEFAULT NULL,
  `coverImage` varchar(255) DEFAULT NULL,
  `discount` int DEFAULT '0',
  `numberOfStar` int DEFAULT '0',
  `numberOfRating` int DEFAULT '0',
  `approvedByAI` tinyint(1) DEFAULT NULL,
  `introduction` varchar(255) DEFAULT NULL,
  `type` enum('item','box','card') DEFAULT 'item',
  PRIMARY KEY (`productId`),
  KEY `seller` (`seller`),
  KEY `approver` (`approver`),
  CONSTRAINT `Product_ibfk_1` FOREIGN KEY (`seller`) REFERENCES `user` (`username`),
  CONSTRAINT `Product_ibfk_2` FOREIGN KEY (`approver`) REFERENCES `user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `product_links`
--

DROP TABLE IF EXISTS `product_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_links` (
  `imageId` int NOT NULL AUTO_INCREMENT,
  `link` varchar(255) NOT NULL,
  `productId` int NOT NULL,
  `type` enum('image','video') DEFAULT NULL,
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`imageId`),
  KEY `ProductImage_ibfk_1_idx` (`productId`),
  CONSTRAINT `ProductImage_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `product` (`productId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `numPhone` varchar(30) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `loginType` enum('normal','facebook','google') DEFAULT 'normal',
  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `avatar` varchar(255) DEFAULT NULL,
  `role` varchar(50) DEFAULT 'buyer',
  `lockUntil` timestamp NULL DEFAULT NULL,
  `nation` varchar(50) DEFAULT NULL,
  `shopName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-06 16:19:21
