CREATE DATABASE  IF NOT EXISTS `campus_24K_LI_p2_1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `campus_24K_LI_p2_1`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: project-db-campus.smhrd.com    Database: campus_24K_LI_p2_1
-- ------------------------------------------------------
-- Server version	8.0.26

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

--
-- Table structure for table `tb_smart_library`
--

DROP TABLE IF EXISTS `tb_smart_library`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_smart_library` (
  `lib_idx` bigint NOT NULL,
  `lib_nm` varchar(255) DEFAULT NULL,
  `lib_addr` varchar(255) DEFAULT NULL,
  `region_nm` varchar(255) DEFAULT NULL,
  `region_idx` int DEFAULT NULL,
  PRIMARY KEY (`lib_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='스마트 도서관';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_smart_library`
--

LOCK TABLES `tb_smart_library` WRITE;
/*!40000 ALTER TABLE `tb_smart_library` DISABLE KEYS */;
INSERT INTO `tb_smart_library` VALUES (1,'문화센터','광주광역시 동구 남문로 646 동구문화센터 정면','동구',1),(2,'국민체육센터','광주광역시 동구 필문대로 205번길 17 동구국민체육센터 측면','동구',1),(3,'용산','광주광역시 동구 용산3길 17 모아엘가 측면','동구',1),(4,'상무역','광주광역시 서구 치평동 1176-2 상무역 4번출구 인도 앞','서구',2),(5,'서구청','광주광역시 서구 농성동299 서구청 1층 민원실 옆','서구',2),(6,'운천역','광주광역시 서구 상무대로 지하883 운천역 지하 1층','서구',2),(7,'풍암동','광주광역시 서구 풍암1로 27 풍암동 행정복지센터','서구',2),(8,'동천동','광주광역시 서구 천변우하로 391 나라키움 광주통합청사','서구',2),(9,'꿈누리','광주광역시 서구 쌍촌동 1287-1번지 서구 청소년꿈누리센터','서구',2),(10,'푸른길','광주광역시 남구 서문대로 749번길 7 푸른길 공원 내 빅스포 뒤편','남구',3),(11,'주월2동행정복지센터','광주광역시 남구 군분로 23번길 11-3 주월2동 행정복지센터 뒤편','남구',3),(12,'남광주농협','광주광역시 남구 백양로 38-2 남광주농협 맞은편, 푸른길공원 인근','남구',3),(13,'다목적체육관','광주광역시 남구 화산로 110 남구다목적체육관 입구','남구',3),(14,'효천공원','광주광역시 남구 효천중앙로 45 효천공원 내','남구',3),(15,'광주역','광주광역시 북구 무등로 235 광주역 광장','북구',4),(16,'한국산업단지공단','광주광역시 북구 첨단과기로 313 한국산업단지공단 광주지역본부 1층','북구',4),(17,'북구청','광주광역시 북구 우치로 77 북구청 우측별관입구','북구',4),(18,'신창','광주광역시 광산구 왕버들로 291 1층','광산구',5),(19,'송정공원','광주광역시 광산구 상무대로 지하314-99 지하1층','광산구',5),(20,'평동','광주광역시 광산구 평동로 870 1층','광산구',5),(21,'광산구청','광주광역시 광산구 상무대로 234','광산구',5),(22,'첨단','광주광역시 광산구 첨단중앙로 98','광산구',5),(23,'수완','광주광역시 광산구 장덕로 150','광산구',5),(24,'하남','광주광역시 광산구 하남산단3번로 133-8 1층','광산구',5),(25,'월곡','광주광역시 광산구 사암로 306-1','광산구',5),(26,'쌍암공원','광주광역시 광산구 첨단중앙로 182번길 39','광산구',5);
/*!40000 ALTER TABLE `tb_smart_library` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-31 15:24:51
