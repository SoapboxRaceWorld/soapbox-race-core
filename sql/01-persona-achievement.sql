# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.5.5-10.2.14-MariaDB)
# Database: SOAPBOX
# Generation Time: 2018-07-06 13:09:13 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table PERSONA_ACHIEVEMENT
# ------------------------------------------------------------

DROP TABLE IF EXISTS `PERSONA_ACHIEVEMENT`;

CREATE TABLE `PERSONA_ACHIEVEMENT` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `canProgress` bit(1) DEFAULT NULL,
  `currentValue` bigint(20) DEFAULT NULL,
  `achievementId` int(11) DEFAULT NULL,
  `personaId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcqa9vd3g6bdunq8dglv0445gp` (`achievementId`),
  KEY `FK4hn43xa53d6ly1dm0bhj6jri3` (`personaId`),
  CONSTRAINT `FK4hn43xa53d6ly1dm0bhj6jri3` FOREIGN KEY (`personaId`) REFERENCES `PERSONA` (`ID`),
  CONSTRAINT `FKcqa9vd3g6bdunq8dglv0445gp` FOREIGN KEY (`achievementId`) REFERENCES `ACHIEVEMENT_DEFINITION` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



# Dump of table PERSONA_ACHIEVEMENT_RANK
# ------------------------------------------------------------

DROP TABLE IF EXISTS `PERSONA_ACHIEVEMENT_RANK`;

CREATE TABLE `PERSONA_ACHIEVEMENT_RANK` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `achievedOn` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `state` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `achievementId` int(11) DEFAULT NULL,
  `personaId` bigint(20) DEFAULT NULL,
  `rankId` int(11) DEFAULT NULL,
  `isNew` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfxuptaxlhx5m8l5sywnwikgrn` (`achievementId`),
  KEY `FK3qqs476v0455fa4ry8ctixmfc` (`personaId`),
  KEY `FK53v9bjbkt2cq5f88njcqf4ksh` (`rankId`),
  CONSTRAINT `FK3qqs476v0455fa4ry8ctixmfc` FOREIGN KEY (`personaId`) REFERENCES `PERSONA` (`ID`),
  CONSTRAINT `FK53v9bjbkt2cq5f88njcqf4ksh` FOREIGN KEY (`rankId`) REFERENCES `ACHIEVEMENT_RANK` (`id`),
  CONSTRAINT `FKfxuptaxlhx5m8l5sywnwikgrn` FOREIGN KEY (`achievementId`) REFERENCES `ACHIEVEMENT_DEFINITION` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
