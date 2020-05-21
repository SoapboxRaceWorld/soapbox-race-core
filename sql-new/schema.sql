-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: 209.97.187.156    Database: SOAPBOX
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `ACHIEVEMENT`
--

DROP TABLE IF EXISTS `ACHIEVEMENT`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ACHIEVEMENT`
(
    `ID`                        bigint NOT NULL AUTO_INCREMENT,
    `auto_update`               bit(1)                                                           DEFAULT NULL,
    `category`                  varchar(255)                                                     DEFAULT NULL,
    `name`                      varchar(255)                                                     DEFAULT NULL,
    `progress_text`             varchar(255)                                                     DEFAULT NULL,
    `should_overwrite_progress` bit(1)                                                           DEFAULT b'0',
    `stat_conversion`           enum ('None','FromMetersToDistance','FromMillisecondsToMinutes') DEFAULT NULL,
    `update_trigger`            text,
    `update_value`              text,
    `visible`                   bit(1)                                                           DEFAULT NULL,
    `badge_definition_id`       bigint NOT NULL,
    PRIMARY KEY (`ID`),
    UNIQUE KEY `UK_so6kbq2i8oy15k8f9m7vuylu5` (`badge_definition_id`),
    CONSTRAINT `FK6xo8y7evmxkq6rbqgqlngxnxh` FOREIGN KEY (`badge_definition_id`) REFERENCES `BADGE_DEFINITION` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ACHIEVEMENT_RANK`
--

DROP TABLE IF EXISTS `ACHIEVEMENT_RANK`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ACHIEVEMENT_RANK`
(
    `ID`                  bigint NOT NULL AUTO_INCREMENT,
    `points`              int          DEFAULT NULL,
    `rank`                int          DEFAULT NULL,
    `rarity`              float        DEFAULT NULL,
    `reward_description`  varchar(255) DEFAULT NULL,
    `reward_type`         varchar(255) DEFAULT NULL,
    `reward_visual_style` varchar(255) DEFAULT NULL,
    `threshold_value`     int          DEFAULT NULL,
    `achievement_id`      bigint NOT NULL,
    PRIMARY KEY (`ID`),
    KEY `FK276d2ojf04ey397aixbri1j42` (`achievement_id`),
    CONSTRAINT `FK276d2ojf04ey397aixbri1j42` FOREIGN KEY (`achievement_id`) REFERENCES `ACHIEVEMENT` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ACHIEVEMENT_REWARD`
--

DROP TABLE IF EXISTS `ACHIEVEMENT_REWARD`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ACHIEVEMENT_REWARD`
(
    `ID`                          bigint NOT NULL AUTO_INCREMENT,
    `internal_reward_description` varchar(255) DEFAULT NULL,
    `reward_description`          varchar(255) DEFAULT NULL,
    `rewardScript`                text   NOT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `AMPLIFIERS`
--

DROP TABLE IF EXISTS `AMPLIFIERS`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `AMPLIFIERS`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT,
    `ampType`        varchar(255) DEFAULT NULL,
    `cashMultiplier` float        DEFAULT NULL,
    `repMultiplier`  float        DEFAULT NULL,
    `product_id`     varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_dpq9a1cdlxuukwbqor9ua1j2a` (`product_id`),
    CONSTRAINT `FK8dw7pn0b32ngrloirp6m9xx5x` FOREIGN KEY (`product_id`) REFERENCES `PRODUCT` (`productId`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BADGE_DEFINITION`
--

DROP TABLE IF EXISTS `BADGE_DEFINITION`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BADGE_DEFINITION`
(
    `ID`          bigint NOT NULL AUTO_INCREMENT,
    `background`  varchar(255) DEFAULT NULL,
    `border`      varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `icon`        varchar(255) DEFAULT NULL,
    `name`        varchar(255) DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BAN`
--

DROP TABLE IF EXISTS `BAN`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BAN`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `data`         varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `endsAt`       datetime                                DEFAULT NULL,
    `reason`       varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `type`         varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `user_id`      bigint                                  DEFAULT NULL,
    `started`      datetime                                DEFAULT NULL,
    `willEnd`      bit(1)                                  DEFAULT NULL,
    `banned_by_id` bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKlwawdqh4uid0isnsc9uomdyfx` (`user_id`),
    KEY `FK_BANNED_BY` (`banned_by_id`),
    CONSTRAINT `FK_BAN_USER` FOREIGN KEY (`user_id`) REFERENCES `USER` (`ID`),
    CONSTRAINT `FK_BANNED_BY` FOREIGN KEY (`banned_by_id`) REFERENCES `PERSONA` (`ID`),
    CONSTRAINT `FKlwawdqh4uid0isnsc9uomdyfx` FOREIGN KEY (`user_id`) REFERENCES `USER` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BASKETDEFINITION`
--

DROP TABLE IF EXISTS `BASKETDEFINITION`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BASKETDEFINITION`
(
    `productId`     varchar(255) NOT NULL,
    `ownedCarTrans` longtext,
    PRIMARY KEY (`productId`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CARD_PACK`
--

DROP TABLE IF EXISTS `CARD_PACK`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CARD_PACK`
(
    `ID`             bigint NOT NULL AUTO_INCREMENT,
    `entitlementTag` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CARD_PACK_ITEM`
--

DROP TABLE IF EXISTS `CARD_PACK_ITEM`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CARD_PACK_ITEM`
(
    `ID`                bigint NOT NULL AUTO_INCREMENT,
    `script`            text   NOT NULL,
    `cardPackEntity_ID` bigint NOT NULL,
    PRIMARY KEY (`ID`),
    KEY `FK3wo6p0la6hflhtss9iqj5t5h6` (`cardPackEntity_ID`),
    CONSTRAINT `FK3wo6p0la6hflhtss9iqj5t5h6` FOREIGN KEY (`cardPackEntity_ID`) REFERENCES `CARD_PACK` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CARSLOT`
--

DROP TABLE IF EXISTS `CARSLOT`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CARSLOT`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `ownedCarTrans` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci,
    `PersonaId`     bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_CARSLOT_PERSONA` (`PersonaId`),
    CONSTRAINT `FK_CARSLOT_PERSONA` FOREIGN KEY (`PersonaId`) REFERENCES `PERSONA` (`ID`),
    CONSTRAINT `FKc8km27r2ln6pxi1wfr5r8qjrd` FOREIGN KEY (`PersonaId`) REFERENCES `PERSONA` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CAR_CLASSES`
--

DROP TABLE IF EXISTS `CAR_CLASSES`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CAR_CLASSES`
(
    `store_name` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
    `full_name`  text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
    `manufactor` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
    `model`      text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
    `ts_stock`   int                                                     DEFAULT NULL,
    `ts_var1`    int                                                     DEFAULT NULL,
    `ts_var2`    int                                                     DEFAULT NULL,
    `ts_var3`    int                                                     DEFAULT NULL,
    `ac_stock`   int                                                     DEFAULT NULL,
    `ac_var1`    int                                                     DEFAULT NULL,
    `ac_var2`    int                                                     DEFAULT NULL,
    `ac_var3`    int                                                     DEFAULT NULL,
    `ha_stock`   int                                                     DEFAULT NULL,
    `ha_var1`    int                                                     DEFAULT NULL,
    `ha_var2`    int                                                     DEFAULT NULL,
    `ha_var3`    int                                                     DEFAULT NULL,
    `hash`       int                                                     DEFAULT NULL,
    `product_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`store_name`(255))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CATEGORY`
--

DROP TABLE IF EXISTS `CATEGORY`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CATEGORY`
(
    `idcategory`           bigint NOT NULL AUTO_INCREMENT,
    `catalogVersion`       varchar(255) DEFAULT NULL,
    `categories`           varchar(255) DEFAULT NULL,
    `displayName`          varchar(255) DEFAULT NULL,
    `filterType`           int          DEFAULT NULL,
    `icon`                 varchar(255) DEFAULT NULL,
    `id`                   bigint       DEFAULT NULL,
    `longDescription`      varchar(255) DEFAULT NULL,
    `name`                 varchar(255) DEFAULT NULL,
    `priority`             smallint     DEFAULT NULL,
    `shortDescription`     varchar(255) DEFAULT NULL,
    `showInNavigationPane` bit(1)       DEFAULT NULL,
    `showPromoPage`        bit(1)       DEFAULT NULL,
    `webIcon`              varchar(255) DEFAULT NULL,
    PRIMARY KEY (`idcategory`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CHAT_ANNOUNCEMENT`
--

DROP TABLE IF EXISTS `CHAT_ANNOUNCEMENT`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CHAT_ANNOUNCEMENT`
(
    `id`                   int NOT NULL AUTO_INCREMENT,
    `announcementInterval` int          DEFAULT NULL,
    `announcementMessage`  varchar(255) DEFAULT NULL,
    `channelMask`          varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CHAT_ROOM`
--

DROP TABLE IF EXISTS `CHAT_ROOM`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CHAT_ROOM`
(
    `ID`        bigint NOT NULL AUTO_INCREMENT,
    `amount`    int                                                     DEFAULT NULL,
    `longName`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    `shortName` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CUSTOMCAR`
--

DROP TABLE IF EXISTS `CUSTOMCAR`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CUSTOMCAR`
(
    `id`                 bigint NOT NULL AUTO_INCREMENT,
    `baseCar`            int    NOT NULL,
    `carClassHash`       int    NOT NULL,
    `isPreset`           bit(1) NOT NULL,
    `level`              int    NOT NULL,
    `name`               varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    `physicsProfileHash` int    NOT NULL,
    `rating`             int    NOT NULL,
    `resalePrice`        float  NOT NULL,
    `rideHeightDrop`     float  NOT NULL,
    `skillModSlotCount`  int    NOT NULL,
    `version`            int    NOT NULL,
    `ownedCarId`         bigint                                                  DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKjgdh4g5me6ljh5srjkn2t0i71` (`ownedCarId`),
    CONSTRAINT `FK_CUSTOMCAR_OWNEDCAR` FOREIGN KEY (`ownedCarId`) REFERENCES `OWNEDCAR` (`id`),
    CONSTRAINT `FKjgdh4g5me6ljh5srjkn2t0i71` FOREIGN KEY (`ownedCarId`) REFERENCES `OWNEDCAR` (`id`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `EVENT`
--

DROP TABLE IF EXISTS `EVENT`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `EVENT`
(
    `ID`                         int        NOT NULL AUTO_INCREMENT,
    `baseCashReward`             int        NOT NULL,
    `baseRepReward`              int        NOT NULL,
    `eventModeId`                int        NOT NULL,
    `finalCashRewardMultiplier`  float      NOT NULL,
    `finalRepRewardMultiplier`   float      NOT NULL,
    `isEnabled`                  bit(1)     NOT NULL,
    `isLocked`                   bit(1)     NOT NULL,
    `rewardsTimeLimit`           bigint     NOT NULL,
    `levelCashRewardMultiplier`  float      NOT NULL,
    `levelRepRewardMultiplier`   float      NOT NULL,
    `maxCarClassRating`          int        NOT NULL,
    `maxLevel`                   int        NOT NULL,
    `maxPlayers`                 int        NOT NULL,
    `minCarClassRating`          int        NOT NULL,
    `minLevel`                   int        NOT NULL,
    `minTopSpeedTrigger`         float      NOT NULL,
    `name`                       varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    `perfectStartCashMultiplier` float      NOT NULL,
    `perfectStartRepMultiplier`  float      NOT NULL,
    `rank1CashMultiplier`        float      NOT NULL,
    `rank1RepMultiplier`         float      NOT NULL,
    `rank2CashMultiplier`        float      NOT NULL,
    `rank2RepMultiplier`         float      NOT NULL,
    `rank3CashMultiplier`        float      NOT NULL,
    `rank3RepMultiplier`         float      NOT NULL,
    `rank4CashMultiplier`        float      NOT NULL,
    `rank4RepMultiplier`         float      NOT NULL,
    `rank5CashMultiplier`        float      NOT NULL,
    `rank5RepMultiplier`         float      NOT NULL,
    `rank6CashMultiplier`        float      NOT NULL,
    `rank6RepMultiplier`         float      NOT NULL,
    `rank7CashMultiplier`        float      NOT NULL,
    `rank7RepMultiplier`         float      NOT NULL,
    `rank8CashMultiplier`        float      NOT NULL,
    `rank8RepMultiplier`         float      NOT NULL,
    `topSpeedCashMultiplier`     float      NOT NULL,
    `topSpeedRepMultiplier`      float      NOT NULL,
    `carClassHash`               int        NOT NULL,
    `trackLength`                float      NOT NULL,
    `rewardTable_rank1_id`       bigint                                                  DEFAULT NULL,
    `rewardTable_rank2_id`       bigint                                                  DEFAULT NULL,
    `rewardTable_rank3_id`       bigint                                                  DEFAULT NULL,
    `rewardTable_rank4_id`       bigint                                                  DEFAULT NULL,
    `rewardTable_rank5_id`       bigint                                                  DEFAULT NULL,
    `rewardTable_rank6_id`       bigint                                                  DEFAULT NULL,
    `rewardTable_rank7_id`       bigint                                                  DEFAULT NULL,
    `rewardTable_rank8_id`       bigint                                                  DEFAULT NULL,
    `isRotationEnabled`          tinyint(1) NOT NULL                                     DEFAULT '0',
    `dnfTimerTime`               int                                                     DEFAULT '60000',
    `lobbyCountdownTime`         int                                                     DEFAULT '60000',
    `legitTime`                  bigint     NOT NULL,
    `isDnfEnabled`               tinyint(1) NOT NULL                                     DEFAULT '1',
    `isRaceAgainEnabled`         tinyint(1) NOT NULL                                     DEFAULT '1',
    PRIMARY KEY (`ID`),
    KEY `FKhky4tfvfpo91vixa09jkofjnc` (`rewardTable_rank1_id`),
    KEY `FKdwjw797e5pirj7rnvcr6d71pi` (`rewardTable_rank2_id`),
    KEY `FK5r8fjqwem02m1b4oyc6yy1esq` (`rewardTable_rank3_id`),
    KEY `FK8g17bk7hkmabmnf88srvslxwm` (`rewardTable_rank4_id`),
    KEY `FKalw5jfl728ahfpvdwhegdty6w` (`rewardTable_rank5_id`),
    KEY `FK2xj5h4dvsmiy3fpyayhmgsm7y` (`rewardTable_rank6_id`),
    KEY `FKfpntheixvd1mgifia39l2tf4b` (`rewardTable_rank7_id`),
    KEY `FKfh6oit4kajywxhmxtid3yfukk` (`rewardTable_rank8_id`),
    CONSTRAINT `FK2xj5h4dvsmiy3fpyayhmgsm7y` FOREIGN KEY (`rewardTable_rank6_id`) REFERENCES `REWARD_TABLE` (`ID`),
    CONSTRAINT `FK5r8fjqwem02m1b4oyc6yy1esq` FOREIGN KEY (`rewardTable_rank3_id`) REFERENCES `REWARD_TABLE` (`ID`),
    CONSTRAINT `FK8g17bk7hkmabmnf88srvslxwm` FOREIGN KEY (`rewardTable_rank4_id`) REFERENCES `REWARD_TABLE` (`ID`),
    CONSTRAINT `FKalw5jfl728ahfpvdwhegdty6w` FOREIGN KEY (`rewardTable_rank5_id`) REFERENCES `REWARD_TABLE` (`ID`),
    CONSTRAINT `FKdwjw797e5pirj7rnvcr6d71pi` FOREIGN KEY (`rewardTable_rank2_id`) REFERENCES `REWARD_TABLE` (`ID`),
    CONSTRAINT `FKfh6oit4kajywxhmxtid3yfukk` FOREIGN KEY (`rewardTable_rank8_id`) REFERENCES `REWARD_TABLE` (`ID`),
    CONSTRAINT `FKfpntheixvd1mgifia39l2tf4b` FOREIGN KEY (`rewardTable_rank7_id`) REFERENCES `REWARD_TABLE` (`ID`),
    CONSTRAINT `FKhky4tfvfpo91vixa09jkofjnc` FOREIGN KEY (`rewardTable_rank1_id`) REFERENCES `REWARD_TABLE` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `EVENT_DATA`
--

DROP TABLE IF EXISTS `EVENT_DATA`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `EVENT_DATA`
(
    `ID`                                   bigint NOT NULL AUTO_INCREMENT,
    `alternateEventDurationInMilliseconds` bigint NOT NULL,
    `bestLapDurationInMilliseconds`        bigint NOT NULL,
    `bustedCount`                          int    NOT NULL,
    `carId`                                bigint NOT NULL,
    `copsDeployed`                         int    NOT NULL,
    `copsDisabled`                         int    NOT NULL,
    `copsRammed`                           int    NOT NULL,
    `costToState`                          int    NOT NULL,
    `distanceToFinish`                     float  NOT NULL,
    `eventDurationInMilliseconds`          bigint NOT NULL,
    `eventModeId`                          int    NOT NULL,
    `eventSessionId`                       bigint     DEFAULT NULL,
    `finishReason`                         int    NOT NULL,
    `fractionCompleted`                    float  NOT NULL,
    `hacksDetected`                        bigint NOT NULL,
    `heat`                                 float  NOT NULL,
    `infractions`                          int    NOT NULL,
    `longestJumpDurationInMilliseconds`    bigint NOT NULL,
    `numberOfCollisions`                   int    NOT NULL,
    `perfectStart`                         int    NOT NULL,
    `personaId`                            bigint     DEFAULT NULL,
    `rank`                                 int    NOT NULL,
    `roadBlocksDodged`                     int    NOT NULL,
    `spikeStripsDodged`                    int    NOT NULL,
    `sumOfJumpsDurationInMilliseconds`     bigint NOT NULL,
    `topSpeed`                             float  NOT NULL,
    `EVENTID`                              int        DEFAULT NULL,
    `isLegit`                              tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `esi_personaId` (`eventSessionId`, `personaId`),
    KEY `FK_EVENTDATA_EVENT` (`EVENTID`),
    KEY `eid_finishreason_index` (`EVENTID`, `finishReason`),
    CONSTRAINT `FK_EVENTDATA_EVENT` FOREIGN KEY (`EVENTID`) REFERENCES `EVENT` (`ID`),
    CONSTRAINT `FKjqbikfyvivd8pf6ke5ke5rv1k` FOREIGN KEY (`EVENTID`) REFERENCES `EVENT` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `EVENT_SESSION`
--

DROP TABLE IF EXISTS `EVENT_SESSION`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `EVENT_SESSION`
(
    `ID`          bigint NOT NULL AUTO_INCREMENT,
    `EVENTID`     int    DEFAULT NULL,
    `ENDED`       bigint DEFAULT NULL,
    `STARTED`     bigint DEFAULT NULL,
    `LOBBYID`     bigint DEFAULT NULL,
    `NEXTLOBBYID` bigint DEFAULT NULL,
    PRIMARY KEY (`ID`),
    KEY `FK_EVENTSESSION_EVENT` (`EVENTID`),
    KEY `FK_EVENTSESSION_LOBBY` (`LOBBYID`),
    KEY `FK_EVENTSESSION_NEXTLOBBY` (`NEXTLOBBYID`),
    CONSTRAINT `FK_EVENTSESSION_EVENT` FOREIGN KEY (`EVENTID`) REFERENCES `EVENT` (`ID`),
    CONSTRAINT `FK_EVENTSESSION_LOBBY` FOREIGN KEY (`LOBBYID`) REFERENCES `LOBBY` (`ID`),
    CONSTRAINT `FK_EVENTSESSION_NEXTLOBBY` FOREIGN KEY (`NEXTLOBBYID`) REFERENCES `LOBBY` (`ID`),
    CONSTRAINT `FKj7j77j10kjso12h57nw1vdb1` FOREIGN KEY (`EVENTID`) REFERENCES `EVENT` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `GIFT_CODE`
--

DROP TABLE IF EXISTS `GIFT_CODE`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GIFT_CODE`
(
    `ID`       bigint NOT NULL AUTO_INCREMENT,
    `code`     varchar(255) DEFAULT NULL,
    `endsAt`   datetime     DEFAULT NULL,
    `startsAt` datetime     DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `HARDWARE_INFO`
--

DROP TABLE IF EXISTS `HARDWARE_INFO`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `HARDWARE_INFO`
(
    `ID`           bigint NOT NULL AUTO_INCREMENT,
    `banned`       bit(1) NOT NULL,
    `hardwareHash` varchar(255) DEFAULT NULL,
    `hardwareInfo` longtext,
    `userId`       bigint       DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `INVENTORY`
--

DROP TABLE IF EXISTS `INVENTORY`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `INVENTORY`
(
    `id`                            bigint NOT NULL AUTO_INCREMENT,
    `performancePartsCapacity`      int    DEFAULT NULL,
    `performancePartsUsedSlotCount` int    DEFAULT NULL,
    `skillModPartsCapacity`         int    DEFAULT NULL,
    `skillModPartsUsedSlotCount`    int    DEFAULT NULL,
    `visualPartsCapacity`           int    DEFAULT NULL,
    `visualPartsUsedSlotCount`      int    DEFAULT NULL,
    `personaId`                     bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_INVENTORY_PERSONA` (`personaId`),
    CONSTRAINT `FK_INVENTORY_PERSONA` FOREIGN KEY (`personaId`) REFERENCES `PERSONA` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `INVENTORY_ITEM`
--

DROP TABLE IF EXISTS `INVENTORY_ITEM`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `INVENTORY_ITEM`
(
    `id`                 bigint       NOT NULL AUTO_INCREMENT,
    `expirationDate`     datetime DEFAULT NULL,
    `remainingUseCount`  int      DEFAULT NULL,
    `resellPrice`        int      DEFAULT NULL,
    `status`             varchar(255) NOT NULL,
    `inventoryEntity_id` bigint       NOT NULL,
    `productId`          varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKpt9o6wxhd3m4ufvl7dgifl9l3` (`inventoryEntity_id`),
    KEY `FK1ii2plfjt9hme45210mfpr15e` (`productId`),
    CONSTRAINT `FK1ii2plfjt9hme45210mfpr15e` FOREIGN KEY (`productId`) REFERENCES `PRODUCT` (`productId`),
    CONSTRAINT `FKpt9o6wxhd3m4ufvl7dgifl9l3` FOREIGN KEY (`inventoryEntity_id`) REFERENCES `INVENTORY` (`id`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `INVITE_TICKET`
--

DROP TABLE IF EXISTS `INVITE_TICKET`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `INVITE_TICKET`
(
    `ID`           bigint NOT NULL AUTO_INCREMENT,
    `DISCORD_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    `TICKET`       varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    `USERID`       bigint                                                  DEFAULT NULL,
    PRIMARY KEY (`ID`),
    KEY `FK_INVITETICKET_USER` (`USERID`),
    CONSTRAINT `FK3plac3qijtk0ciw43aoro0ogn` FOREIGN KEY (`USERID`) REFERENCES `USER` (`ID`),
    CONSTRAINT `FK_INVITETICKET_USER` FOREIGN KEY (`USERID`) REFERENCES `USER` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LEVEL_REP`
--

DROP TABLE IF EXISTS `LEVEL_REP`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LEVEL_REP`
(
    `level`    bigint NOT NULL AUTO_INCREMENT,
    `expPoint` bigint DEFAULT NULL,
    PRIMARY KEY (`level`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LOBBY`
--

DROP TABLE IF EXISTS `LOBBY`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LOBBY`
(
    `ID`                 bigint NOT NULL AUTO_INCREMENT,
    `isPrivate`          bit(1)   DEFAULT NULL,
    `lobbyDateTimeStart` datetime DEFAULT NULL,
    `personaId`          bigint   DEFAULT NULL,
    `EVENTID`            int      DEFAULT NULL,
    `startedTime`        datetime DEFAULT NULL,
    PRIMARY KEY (`ID`),
    KEY `FK_LOBBY_EVENT` (`EVENTID`),
    CONSTRAINT `FK_LOBBY_EVENT` FOREIGN KEY (`EVENTID`) REFERENCES `EVENT` (`ID`),
    CONSTRAINT `FKig5wp7wc5nuwille2w0hahp77` FOREIGN KEY (`EVENTID`) REFERENCES `EVENT` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LOBBY_ENTRANT`
--

DROP TABLE IF EXISTS `LOBBY_ENTRANT`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LOBBY_ENTRANT`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `gridIndex` int    NOT NULL,
    `LOBBYID`   bigint DEFAULT NULL,
    `PERSONAID` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_LOBBYENTRANT_LOBBY` (`LOBBYID`),
    KEY `FK_LOBBYENTRANT_PERSONA` (`PERSONAID`),
    CONSTRAINT `FK_LOBBYENTRANT_LOBBY` FOREIGN KEY (`LOBBYID`) REFERENCES `LOBBY` (`ID`),
    CONSTRAINT `FK_LOBBYENTRANT_PERSONA` FOREIGN KEY (`PERSONAID`) REFERENCES `PERSONA` (`ID`),
    CONSTRAINT `FKn2yjevc0kdgpj7juvn0udr2mn` FOREIGN KEY (`LOBBYID`) REFERENCES `LOBBY` (`ID`),
    CONSTRAINT `FKqy9gynb01x729yacdubgmjier` FOREIGN KEY (`PERSONAID`) REFERENCES `PERSONA` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LOGIN_ANNOUCEMENT`
--

DROP TABLE IF EXISTS `LOGIN_ANNOUCEMENT`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LOGIN_ANNOUCEMENT`
(
    `id`       int NOT NULL AUTO_INCREMENT,
    `imageUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    `target`   varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    `type`     varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NEWS_ARTICLE`
--

DROP TABLE IF EXISTS `NEWS_ARTICLE`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `NEWS_ARTICLE`
(
    `id`                    bigint NOT NULL AUTO_INCREMENT,
    `filters`               varchar(255)  DEFAULT NULL,
    `iconType`              int           DEFAULT NULL,
    `longHALId`             varchar(255)  DEFAULT NULL,
    `parameters`            varchar(1000) DEFAULT NULL,
    `shortHALId`            varchar(255)  DEFAULT NULL,
    `sticky`                int           DEFAULT NULL,
    `timestamp`             bigint        DEFAULT NULL,
    `type`                  varchar(255)  DEFAULT NULL,
    `persona_id`            bigint        DEFAULT NULL,
    `referenced_persona_id` bigint        DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKrgi3drtymu1h23a4jjgd092yg` (`persona_id`),
    KEY `FK41ahos5r10lc5v0o1ttd3tqt3` (`referenced_persona_id`),
    CONSTRAINT `FK41ahos5r10lc5v0o1ttd3tqt3` FOREIGN KEY (`referenced_persona_id`) REFERENCES `PERSONA` (`ID`),
    CONSTRAINT `FKrgi3drtymu1h23a4jjgd092yg` FOREIGN KEY (`persona_id`) REFERENCES `PERSONA` (`ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ONLINE_USERS`
--

DROP TABLE IF EXISTS `ONLINE_USERS`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ONLINE_USERS`
(
    `ID`            int NOT NULL,
    `numberOfUsers` int NOT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `OWNEDCAR`
--

DROP TABLE IF EXISTS `OWNEDCAR`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OWNEDCAR`
(
    `id`             bigint NOT NULL AUTO_INCREMENT,
    `durability`     int    NOT NULL,
    `expirationDate` datetime                                                DEFAULT NULL,
    `heat`           float  NOT NULL,
    `ownershipType`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    `carSlotId`      bigint                                                  DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK9i0ql538ftgc26i0eri8keeqp` (`carSlotId`),
    CONSTRAINT `FK9i0ql538ftgc26i0eri8keeqp` FOREIGN KEY (`carSlotId`) REFERENCES `CARSLOT` (`id`),
    CONSTRAINT `FK_OWNEDCAR_CARSLOT` FOREIGN KEY (`carSlotId`) REFERENCES `CARSLOT` (`id`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PAINT`
--

DROP TABLE IF EXISTS `PAINT`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PAINT`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `paintGroup`  int    DEFAULT NULL,
    `hue`         int    NOT NULL,
    `sat`         int    NOT NULL,
    `slot`        int    NOT NULL,
    `paintVar`    int    DEFAULT NULL,
    `customCarId` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKpxxc02fcm311w9odwx6j6wu6u` (`customCarId`),
    CONSTRAINT `FK_PAINT_CUSTOMCAR` FOREIGN KEY (`customCarId`) REFERENCES `CUSTOMCAR` (`id`),
    CONSTRAINT `FKpxxc02fcm311w9odwx6j6wu6u` FOREIGN KEY (`customCarId`) REFERENCES `CUSTOMCAR` (`id`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PARAMETER`
--

DROP TABLE IF EXISTS `PARAMETER`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PARAMETER`
(
    `name`  varchar(255) NOT NULL,
    `value` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PERFORMANCEPART`
--

DROP TABLE IF EXISTS `PERFORMANCEPART`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PERFORMANCEPART`
(
    `id`                        bigint NOT NULL AUTO_INCREMENT,
    `performancePartAttribHash` int    NOT NULL,
    `customCarId`               bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKbehavbux872md9t4uedp5xw68` (`customCarId`),
    CONSTRAINT `FK_PERFPART_CUSTOMCAR` FOREIGN KEY (`customCarId`) REFERENCES `CUSTOMCAR` (`id`),
    CONSTRAINT `FKbehavbux872md9t4uedp5xw68` FOREIGN KEY (`customCarId`) REFERENCES `CUSTOMCAR` (`id`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PERSONA`
--

DROP TABLE IF EXISTS `PERSONA`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PERSONA`
(
    `ID`                bigint NOT NULL AUTO_INCREMENT,
    `boost`             double NOT NULL,
    `cash`              double NOT NULL,
    `curCarIndex`       int    NOT NULL,
    `iconIndex`         int    NOT NULL,
    `level`             int    NOT NULL,
    `motto`             varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    `name`              varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    `percentToLevel`    float  NOT NULL,
    `rating`            double NOT NULL,
    `rep`               double NOT NULL,
    `repAtCurrentLevel` int    NOT NULL,
    `score`             int    NOT NULL,
    `USERID`            bigint                                                  DEFAULT NULL,
    `created`           datetime                                                DEFAULT NULL,
    `badges`            varchar(2048)                                           DEFAULT NULL,
    `first_login`       datetime                                                DEFAULT NULL,
    `last_login`        datetime                                                DEFAULT NULL,
    PRIMARY KEY (`ID`),
    KEY `FK_PERSONA_USER` (`USERID`),
    CONSTRAINT `FK_PERSONA_USER` FOREIGN KEY (`USERID`) REFERENCES `USER` (`ID`),
    CONSTRAINT `FKon9k3f1y35051t3y7x6ogd6k7` FOREIGN KEY (`USERID`) REFERENCES `USER` (`ID`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PERSONA_ACHIEVEMENT`
--

DROP TABLE IF EXISTS `PERSONA_ACHIEVEMENT`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PERSONA_ACHIEVEMENT`
(
    `ID`             bigint NOT NULL AUTO_INCREMENT,
    `can_progress`   bit(1) DEFAULT NULL,
    `current_value`  bigint DEFAULT NULL,
    `achievement_id` bigint NOT NULL,
    `persona_id`     bigint NOT NULL,
    PRIMARY KEY (`ID`),
    KEY `FKi0362rxl6y75pcw9v1n7hmecu` (`achievement_id`),
    KEY `FKmci5vxxoedblsncmosjfbtfxk` (`persona_id`),
    CONSTRAINT `FKi0362rxl6y75pcw9v1n7hmecu` FOREIGN KEY (`achievement_id`) REFERENCES `ACHIEVEMENT` (`ID`),
    CONSTRAINT `FKmci5vxxoedblsncmosjfbtfxk` FOREIGN KEY (`persona_id`) REFERENCES `PERSONA` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PERSONA_ACHIEVEMENT_RANK`
--

DROP TABLE IF EXISTS `PERSONA_ACHIEVEMENT_RANK`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PERSONA_ACHIEVEMENT_RANK`
(
    `ID`                     bigint NOT NULL AUTO_INCREMENT,
    `achieved_on`            datetime                                                 DEFAULT NULL,
    `state`                  enum ('Locked','InProgress','Completed','RewardWaiting') DEFAULT NULL,
    `achievement_rank_id`    bigint NOT NULL,
    `persona_achievement_id` bigint NOT NULL,
    PRIMARY KEY (`ID`),
    KEY `FKc1w7mcx2mdb9bg30d0ed5efd9` (`achievement_rank_id`),
    KEY `FKbbg8f49c6fgkbwwhe25i2ely0` (`persona_achievement_id`),
    CONSTRAINT `FKbbg8f49c6fgkbwwhe25i2ely0` FOREIGN KEY (`persona_achievement_id`) REFERENCES `PERSONA_ACHIEVEMENT` (`ID`),
    CONSTRAINT `FKc1w7mcx2mdb9bg30d0ed5efd9` FOREIGN KEY (`achievement_rank_id`) REFERENCES `ACHIEVEMENT_RANK` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PERSONA_BADGE`
--

DROP TABLE IF EXISTS `PERSONA_BADGE`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PERSONA_BADGE`
(
    `ID`                  bigint NOT NULL AUTO_INCREMENT,
    `slot`                int DEFAULT NULL,
    `badge_definition_id` bigint NOT NULL,
    `persona_id`          bigint NOT NULL,
    PRIMARY KEY (`ID`),
    KEY `FK4c2e5u8ymnlh4xc8hiem6uota` (`badge_definition_id`),
    KEY `FKah3mnx6lcx9mg3ant8n1uorii` (`persona_id`),
    CONSTRAINT `FK4c2e5u8ymnlh4xc8hiem6uota` FOREIGN KEY (`badge_definition_id`) REFERENCES `BADGE_DEFINITION` (`ID`),
    CONSTRAINT `FKah3mnx6lcx9mg3ant8n1uorii` FOREIGN KEY (`persona_id`) REFERENCES `PERSONA` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PRODUCT`
--

DROP TABLE IF EXISTS `PRODUCT`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PRODUCT`
(
    `id`              bigint       NOT NULL AUTO_INCREMENT,
    `accel`           int          DEFAULT NULL,
    `brand`           varchar(255) DEFAULT NULL,
    `categoryId`      varchar(255) DEFAULT NULL,
    `categoryName`    varchar(255) DEFAULT NULL,
    `currency`        varchar(255) NOT NULL,
    `description`     varchar(255) DEFAULT NULL,
    `dropWeight`      double       DEFAULT NULL,
    `durationMinute`  int          NOT NULL,
    `enabled`         bit(1)       NOT NULL,
    `entitlementTag`  varchar(255) DEFAULT NULL,
    `handling`        int          DEFAULT NULL,
    `hash`            int          DEFAULT NULL,
    `icon`            varchar(255) NOT NULL,
    `isDropable`      bit(1)       NOT NULL,
    `level`           int          NOT NULL,
    `longDescription` varchar(255) DEFAULT NULL,
    `minLevel`        int          NOT NULL,
    `premium`         bit(1)       NOT NULL,
    `price`           float        NOT NULL,
    `priority`        int          NOT NULL,
    `productId`       varchar(255) NOT NULL,
    `productTitle`    varchar(255) DEFAULT NULL,
    `productType`     varchar(255) NOT NULL,
    `rarity`          int          DEFAULT NULL,
    `resalePrice`     float        NOT NULL,
    `secondaryIcon`   varchar(255) DEFAULT NULL,
    `skillValue`      float        DEFAULT NULL,
    `subType`         varchar(255) DEFAULT NULL,
    `topSpeed`        int          DEFAULT NULL,
    `useCount`        int          NOT NULL,
    `visualStyle`     varchar(255) DEFAULT NULL,
    `webIcon`         varchar(255) DEFAULT NULL,
    `webLocation`     varchar(255) DEFAULT NULL,
    `parentProductId` bigint       DEFAULT NULL,
    `bundleItems`     text,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_10bw1u87a77ibq6hdbivxp1kp` (`productId`),
    KEY `prod_id_index` (`productId`),
    KEY `FK8obn8l9i769slt8pjub191l52` (`parentProductId`),
    CONSTRAINT `FK8obn8l9i769slt8pjub191l52` FOREIGN KEY (`parentProductId`) REFERENCES `PRODUCT` (`id`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PROMO_CODE`
--

DROP TABLE IF EXISTS `PROMO_CODE`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PROMO_CODE`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `isUsed`    bit(1)       DEFAULT NULL,
    `promoCode` varchar(255) DEFAULT NULL,
    `USERID`    bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_PROMOCODE_USER` (`USERID`),
    CONSTRAINT `FK_PROMOCODE_USER` FOREIGN KEY (`USERID`) REFERENCES `USER` (`ID`),
    CONSTRAINT `FKf3q30eb9w1j5o89cumpi72gry` FOREIGN KEY (`USERID`) REFERENCES `USER` (`ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RECOVERY_PASSWORD`
--

DROP TABLE IF EXISTS `RECOVERY_PASSWORD`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `RECOVERY_PASSWORD`
(
    `id`             bigint NOT NULL AUTO_INCREMENT,
    `expirationDate` datetime                                                DEFAULT NULL,
    `isClose`        bit(1)                                                  DEFAULT NULL,
    `randomKey`      varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    `userId`         bigint                                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `REPORT`
--

DROP TABLE IF EXISTS `REPORT`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `REPORT`
(
    `id`              bigint NOT NULL AUTO_INCREMENT,
    `abuserPersonaId` bigint                                                        DEFAULT NULL,
    `chatMinutes`     int                                                           DEFAULT NULL,
    `customCarID`     int                                                           DEFAULT NULL,
    `description`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `hacksdetected`   bigint                                                        DEFAULT NULL,
    `personaId`       bigint                                                        DEFAULT NULL,
    `petitionType`    int                                                           DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `REWARD_TABLE`
--

DROP TABLE IF EXISTS `REWARD_TABLE`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `REWARD_TABLE`
(
    `ID`   bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `REWARD_TABLE_ITEM`
--

DROP TABLE IF EXISTS `REWARD_TABLE_ITEM`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `REWARD_TABLE_ITEM`
(
    `ID`                   bigint NOT NULL AUTO_INCREMENT,
    `dropWeight`           double DEFAULT NULL,
    `script`               text   NOT NULL,
    `rewardTableEntity_ID` bigint NOT NULL,
    PRIMARY KEY (`ID`),
    KEY `FK4qxgxc2phadj0ig58lsepsx9y` (`rewardTableEntity_ID`),
    CONSTRAINT `FK4qxgxc2phadj0ig58lsepsx9y` FOREIGN KEY (`rewardTableEntity_ID`) REFERENCES `REWARD_TABLE` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SERVER_INFO`
--

DROP TABLE IF EXISTS `SERVER_INFO`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SERVER_INFO`
(
    `messageSrv`                       varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
    `country`                          varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci           DEFAULT NULL,
    `adminList`                        varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci           DEFAULT NULL,
    `bannerUrl`                        varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci           DEFAULT NULL,
    `discordUrl`                       varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci           DEFAULT NULL,
    `facebookUrl`                      varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci           DEFAULT NULL,
    `homePageUrl`                      varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci           DEFAULT NULL,
    `numberOfRegistered`               int                                                               DEFAULT NULL,
    `ownerList`                        varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci           DEFAULT NULL,
    `serverName`                       varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NOT NULL,
    `timezone`                         int                                                               DEFAULT NULL,
    `activatedHolidaySceneryGroups`    varchar(255)                                             NOT NULL DEFAULT '',
    `disactivatedHolidaySceneryGroups` varchar(255)                                             NOT NULL DEFAULT '',
    `allowedCountries`                 varchar(255)                                                      DEFAULT NULL,
    PRIMARY KEY (`serverName`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SKILLMODPART`
--

DROP TABLE IF EXISTS `SKILLMODPART`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SKILLMODPART`
(
    `id`                     bigint NOT NULL AUTO_INCREMENT,
    `isFixed`                bit(1) NOT NULL,
    `skillModPartAttribHash` int    NOT NULL,
    `customCarId`            bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK8hnhp2he4tg3rxtpyad7payt1` (`customCarId`),
    CONSTRAINT `FK8hnhp2he4tg3rxtpyad7payt1` FOREIGN KEY (`customCarId`) REFERENCES `CUSTOMCAR` (`id`),
    CONSTRAINT `FK_SKILLPART_CUSTOMCAR` FOREIGN KEY (`customCarId`) REFERENCES `CUSTOMCAR` (`id`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SOCIAL_RELATIONSHIP`
--

DROP TABLE IF EXISTS `SOCIAL_RELATIONSHIP`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SOCIAL_RELATIONSHIP`
(
    `ID`              int NOT NULL AUTO_INCREMENT,
    `remotePersonaId` bigint DEFAULT NULL,
    `status`          bigint DEFAULT NULL,
    `fromUserId`      bigint DEFAULT NULL,
    `userId`          bigint DEFAULT NULL,
    PRIMARY KEY (`ID`),
    KEY `FKih34hpy2rt97e269pop7ehcwm` (`fromUserId`),
    KEY `FKb9v7qy291m237rtenur72v61l` (`userId`),
    CONSTRAINT `FKb9v7qy291m237rtenur72v61l` FOREIGN KEY (`userId`) REFERENCES `USER` (`ID`),
    CONSTRAINT `FKih34hpy2rt97e269pop7ehcwm` FOREIGN KEY (`fromUserId`) REFERENCES `USER` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TOKEN_SESSION`
--

DROP TABLE IF EXISTS `TOKEN_SESSION`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TOKEN_SESSION`
(
    `ID`                varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
    `activeLobbyId`     bigint                                                           DEFAULT NULL,
    `activePersonaId`   bigint                                                           DEFAULT NULL,
    `expirationDate`    datetime                                                         DEFAULT NULL,
    `premium`           bit(1)                                                  NOT NULL DEFAULT b'0',
    `relayCryptoTicket` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci          DEFAULT NULL,
    `userId`            bigint                                                           DEFAULT NULL,
    `clientHostIp`      varchar(255)                                                     DEFAULT NULL,
    `webToken`          varchar(255)                                                     DEFAULT NULL,
    PRIMARY KEY (`ID`),
    UNIQUE KEY `UK_9ranmagnxgrp70u76q860goeb` (`userId`),
    CONSTRAINT `FKomwojh6l6a26jsu4jiqpjnuvn` FOREIGN KEY (`userId`) REFERENCES `USER` (`ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TREASURE_HUNT`
--

DROP TABLE IF EXISTS `TREASURE_HUNT`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TREASURE_HUNT`
(
    `personaId`      bigint NOT NULL,
    `coinsCollected` int    DEFAULT NULL,
    `isStreakBroken` bit(1) DEFAULT NULL,
    `numCoins`       int    DEFAULT NULL,
    `seed`           int    DEFAULT NULL,
    `streak`         int    DEFAULT NULL,
    `thDate`         date   DEFAULT NULL,
    `isCompleted`    bit(1) NOT NULL,
    PRIMARY KEY (`personaId`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TREASURE_HUNT_CONFIG`
--

DROP TABLE IF EXISTS `TREASURE_HUNT_CONFIG`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TREASURE_HUNT_CONFIG`
(
    `ID`              bigint NOT NULL AUTO_INCREMENT,
    `base_cash`       float  DEFAULT NULL,
    `base_rep`        float  DEFAULT NULL,
    `cash_multiplier` float  DEFAULT NULL,
    `rep_multiplier`  float  DEFAULT NULL,
    `streak`          int    DEFAULT NULL,
    `reward_table_id` bigint DEFAULT NULL,
    PRIMARY KEY (`ID`),
    KEY `FK5tyssnup3qalmjfmr4q27jpxf` (`reward_table_id`),
    CONSTRAINT `FK5tyssnup3qalmjfmr4q27jpxf` FOREIGN KEY (`reward_table_id`) REFERENCES `REWARD_TABLE` (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER`
(
    `ID`               bigint NOT NULL AUTO_INCREMENT,
    `EMAIL`            varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    `PASSWORD`         varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci  DEFAULT NULL,
    `premium`          bit(1) NOT NULL                                         DEFAULT b'0',
    `isAdmin`          bit(1)                                                  DEFAULT NULL,
    `HWID`             varchar(255)                                            DEFAULT NULL,
    `IP_ADDRESS`       varchar(255)                                            DEFAULT NULL,
    `created`          datetime                                                DEFAULT NULL,
    `lastLogin`        datetime                                                DEFAULT NULL,
    `gameHardwareHash` varchar(255)                                            DEFAULT NULL,
    `isLocked`         bit(1)                                                  DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `VINYL`
--

DROP TABLE IF EXISTS `VINYL`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `VINYL`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `hash`        int    NOT NULL,
    `hue1`        int    NOT NULL,
    `hue2`        int    NOT NULL,
    `hue3`        int    NOT NULL,
    `hue4`        int    NOT NULL,
    `layer`       int    NOT NULL,
    `mir`         bit(1) NOT NULL,
    `rot`         int    NOT NULL,
    `sat1`        int    NOT NULL,
    `sat2`        int    NOT NULL,
    `sat3`        int    NOT NULL,
    `sat4`        int    NOT NULL,
    `scalex`      int    NOT NULL,
    `scaley`      int    NOT NULL,
    `shear`       int    NOT NULL,
    `tranx`       int    NOT NULL,
    `trany`       int    NOT NULL,
    `var1`        int    NOT NULL,
    `var2`        int    NOT NULL,
    `var3`        int    NOT NULL,
    `var4`        int    NOT NULL,
    `customCarId` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK83pxksxc4fx2efkwnpqet3f3l` (`customCarId`),
    CONSTRAINT `FK83pxksxc4fx2efkwnpqet3f3l` FOREIGN KEY (`customCarId`) REFERENCES `CUSTOMCAR` (`id`),
    CONSTRAINT `FK_VINYL_CUSTOMCAR` FOREIGN KEY (`customCarId`) REFERENCES `CUSTOMCAR` (`id`)
) ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `VINYLPRODUCT`
--

DROP TABLE IF EXISTS `VINYLPRODUCT`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `VINYLPRODUCT`
(
    `id`               bigint NOT NULL AUTO_INCREMENT,
    `bundleItems`      varchar(255) DEFAULT NULL,
    `categoryId`       varchar(255) DEFAULT NULL,
    `categoryName`     varchar(255) DEFAULT NULL,
    `currency`         varchar(255) DEFAULT NULL,
    `description`      varchar(255) DEFAULT NULL,
    `durationMinute`   int    NOT NULL,
    `enabled`          bit(1) NOT NULL,
    `entitlementTag`   varchar(255) DEFAULT NULL,
    `hash`             int          DEFAULT NULL,
    `icon`             varchar(255) DEFAULT NULL,
    `level`            int    NOT NULL,
    `longDescription`  varchar(255) DEFAULT NULL,
    `minLevel`         int    NOT NULL,
    `premium`          bit(1) NOT NULL,
    `price`            float  NOT NULL,
    `priority`         int    NOT NULL,
    `productId`        varchar(255) DEFAULT NULL,
    `productTitle`     varchar(255) DEFAULT NULL,
    `productType`      varchar(255) DEFAULT NULL,
    `secondaryIcon`    varchar(255) DEFAULT NULL,
    `useCount`         int    NOT NULL,
    `visualStyle`      varchar(255) DEFAULT NULL,
    `webIcon`          varchar(255) DEFAULT NULL,
    `webLocation`      varchar(255) DEFAULT NULL,
    `parentCategoryId` bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_VINYLPRODUCT_CATEGORY` (`parentCategoryId`),
    CONSTRAINT `FK_VINYLPRODUCT_CATEGORY` FOREIGN KEY (`parentCategoryId`) REFERENCES `CATEGORY` (`idcategory`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `VIRTUALITEM`
--

DROP TABLE IF EXISTS `VIRTUALITEM`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `VIRTUALITEM`
(
    `itemName`         varchar(255) NOT NULL,
    `brand`            varchar(255) DEFAULT NULL,
    `hash`             int          DEFAULT NULL,
    `icon`             varchar(255) DEFAULT NULL,
    `longdescription`  varchar(255) DEFAULT NULL,
    `rarity`           int          DEFAULT NULL,
    `resellprice`      int          DEFAULT NULL,
    `shortdescription` varchar(255) DEFAULT NULL,
    `subType`          varchar(255) DEFAULT NULL,
    `tier`             int          DEFAULT NULL,
    `title`            varchar(255) DEFAULT NULL,
    `type`             varchar(255) DEFAULT NULL,
    `warnondelete`     bit(1)       DEFAULT NULL,
    PRIMARY KEY (`itemName`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `VISUALPART`
--

DROP TABLE IF EXISTS `VISUALPART`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `VISUALPART`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `partHash`    int    NOT NULL,
    `slotHash`    int    NOT NULL,
    `customCarId` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_VISUALPART_CUSTOMCAR` (`customCarId`),
    CONSTRAINT `FK_VISUALPART_CUSTOMCAR` FOREIGN KEY (`customCarId`) REFERENCES `CUSTOMCAR` (`id`)
) ENGINE = InnoDB

  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `serveritems_skillmod_part`
--

DROP TABLE IF EXISTS `serveritems_skillmod_part`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serveritems_skillmod_part`
(
    `CollectionName`        varchar(255) NOT NULL,
    `Name`                  varchar(255) DEFAULT NULL,
    `SkillModCategory`      varchar(255) DEFAULT NULL,
    `SkillModEffects_ARRAY` varchar(255) DEFAULT NULL,
    `SkillModPartQuality`   smallint     DEFAULT NULL,
    `Template`              bit(1)       DEFAULT NULL,
    PRIMARY KEY (`CollectionName`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2020-05-03  0:12:08
