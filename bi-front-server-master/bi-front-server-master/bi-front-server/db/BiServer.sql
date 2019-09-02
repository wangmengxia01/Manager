/*
 Navicat Premium Data Transfer

 Source Server         : db_qq_game_beijing
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : bj-cdb-36wyknhl.sql.tencentcdb.com:63832
 Source Schema         : BiServer

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 31/08/2019 16:16:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_activity
-- ----------------------------
DROP TABLE IF EXISTS `t_activity`;
CREATE TABLE `t_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `sub_id` int(11) NOT NULL,
  `cost_id` bigint(20) NOT NULL,
  `cost_count` bigint(20) NOT NULL,
  `got_id` bigint(20) NOT NULL,
  `got_count` bigint(20) NOT NULL,
  `detail` varchar(1024) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_activity_people
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_people`;
CREATE TABLE `t_activity_people` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_betwheel_bonus
-- ----------------------------
DROP TABLE IF EXISTS `t_betwheel_bonus`;
CREATE TABLE `t_betwheel_bonus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `cost` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_betwheel_top_bonus
-- ----------------------------
DROP TABLE IF EXISTS `t_betwheel_top_bonus`;
CREATE TABLE `t_betwheel_top_bonus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `rank` int(11) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_caibei_bet
-- ----------------------------
DROP TABLE IF EXISTS `t_caibei_bet`;
CREATE TABLE `t_caibei_bet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `guess_type` int(11) NOT NULL,
  `ratio` double NOT NULL,
  `chips` bigint(20) NOT NULL,
  `card` int(11) NOT NULL,
  `times` int(11) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_caibei_pump
-- ----------------------------
DROP TABLE IF EXISTS `t_caibei_pump`;
CREATE TABLE `t_caibei_pump` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `hour_of_day` int(11) NOT NULL,
  `minute` int(11) NOT NULL,
  `chips` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_caibei_reward
-- ----------------------------
DROP TABLE IF EXISTS `t_caibei_reward`;
CREATE TABLE `t_caibei_reward` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `times` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `item_count` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `total_worth` int(11) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_caibei_win
-- ----------------------------
DROP TABLE IF EXISTS `t_caibei_win`;
CREATE TABLE `t_caibei_win` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `guess_type` int(11) NOT NULL,
  `ratio` double NOT NULL,
  `chips` bigint(20) NOT NULL,
  `card` int(11) NOT NULL,
  `times` int(11) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_dau
-- ----------------------------
DROP TABLE IF EXISTS `t_dau`;
CREATE TABLE `t_dau` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `ip` varchar(40) NOT NULL,
  `platform` varchar(16) NOT NULL,
  `creative` varchar(64) NOT NULL,
  `market_info` varchar(128) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_ddz_choujiang
-- ----------------------------
DROP TABLE IF EXISTS `t_ddz_choujiang`;
CREATE TABLE `t_ddz_choujiang` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `type` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `num` int(11) NOT NULL,
  `choujiang_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_ddz_multiple
-- ----------------------------
DROP TABLE IF EXISTS `t_ddz_multiple`;
CREATE TABLE `t_ddz_multiple` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `inning_id` bigint(20) NOT NULL,
  `multiple` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_ddz_rake
-- ----------------------------
DROP TABLE IF EXISTS `t_ddz_rake`;
CREATE TABLE `t_ddz_rake` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `type` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `inning_id` bigint(20) NOT NULL,
  `gold` bigint(20) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_ddz_win
-- ----------------------------
DROP TABLE IF EXISTS `t_ddz_win`;
CREATE TABLE `t_ddz_win` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `type` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `inning_id` bigint(20) NOT NULL,
  `gold` bigint(20) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_dogsport_bet
-- ----------------------------
DROP TABLE IF EXISTS `t_dogsport_bet`;
CREATE TABLE `t_dogsport_bet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `chips` bigint(20) NOT NULL,
  `betkey` varchar(256) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_dogsport_jackpot
-- ----------------------------
DROP TABLE IF EXISTS `t_dogsport_jackpot`;
CREATE TABLE `t_dogsport_jackpot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `chips` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_dogsport_pump
-- ----------------------------
DROP TABLE IF EXISTS `t_dogsport_pump`;
CREATE TABLE `t_dogsport_pump` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `hour_of_day` int(11) NOT NULL,
  `minute` int(11) NOT NULL,
  `chips` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_dogsport_win
-- ----------------------------
DROP TABLE IF EXISTS `t_dogsport_win`;
CREATE TABLE `t_dogsport_win` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `chips` bigint(20) NOT NULL,
  `winner_id` int(11) NOT NULL,
  `multiple` double NOT NULL,
  `road_idx` int(11) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_download
-- ----------------------------
DROP TABLE IF EXISTS `t_download`;
CREATE TABLE `t_download` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `is_wechat` varchar(32) NOT NULL,
  `is_android` varchar(32) NOT NULL,
  `action` varchar(256) NOT NULL,
  `other_info` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_duobao_buy
-- ----------------------------
DROP TABLE IF EXISTS `t_duobao_buy`;
CREATE TABLE `t_duobao_buy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `act_id` bigint(20) NOT NULL,
  `buy_num` varchar(500) NOT NULL,
  `need_count` bigint(20) NOT NULL,
  `cost_id` bigint(20) NOT NULL,
  `cost_count` bigint(20) NOT NULL,
  `other_info` varchar(50) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_duobao_open
-- ----------------------------
DROP TABLE IF EXISTS `t_duobao_open`;
CREATE TABLE `t_duobao_open` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `win_user_id` bigint(20) NOT NULL,
  `win_num` bigint(20) NOT NULL,
  `act_id` bigint(20) NOT NULL,
  `item_type` int(5) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `need_count` bigint(20) NOT NULL,
  `name_info` varchar(50) NOT NULL,
  `other_info` varchar(50) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_exchange
-- ----------------------------
DROP TABLE IF EXISTS `t_exchange`;
CREATE TABLE `t_exchange` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `cost_id` bigint(20) NOT NULL,
  `cost_count` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_name` varchar(256) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `real_name` varchar(32) NOT NULL,
  `mobile_no` varchar(32) NOT NULL,
  `addr` varchar(256) NOT NULL,
  `other_info` varchar(512) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_exit
-- ----------------------------
DROP TABLE IF EXISTS `t_exit`;
CREATE TABLE `t_exit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `gold` bigint(20) NOT NULL,
  `items` varchar(8192) NOT NULL,
  `is_ccgames` char(1) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_farm_exchange
-- ----------------------------
DROP TABLE IF EXISTS `t_farm_exchange`;
CREATE TABLE `t_farm_exchange` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `price` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_farm_exchange_pump
-- ----------------------------
DROP TABLE IF EXISTS `t_farm_exchange_pump`;
CREATE TABLE `t_farm_exchange_pump` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `pump` bigint(20) NOT NULL,
  `buyer_id` bigint(20) NOT NULL,
  `order_id` varchar(128) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_farm_landext
-- ----------------------------
DROP TABLE IF EXISTS `t_farm_landext`;
CREATE TABLE `t_farm_landext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `cost` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_farm_mining
-- ----------------------------
DROP TABLE IF EXISTS `t_farm_mining`;
CREATE TABLE `t_farm_mining` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `producer_type` int(11) NOT NULL,
  `ground_idx` int(11) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_farm_produce
-- ----------------------------
DROP TABLE IF EXISTS `t_farm_produce`;
CREATE TABLE `t_farm_produce` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_farm_sell
-- ----------------------------
DROP TABLE IF EXISTS `t_farm_sell`;
CREATE TABLE `t_farm_sell` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `item_id` int(11) NOT NULL,
  `item_count` int(11) NOT NULL,
  `price` bigint(20) NOT NULL,
  `total` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_farm_ticket
-- ----------------------------
DROP TABLE IF EXISTS `t_farm_ticket`;
CREATE TABLE `t_farm_ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `cost` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_fish_get
-- ----------------------------
DROP TABLE IF EXISTS `t_fish_get`;
CREATE TABLE `t_fish_get` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `level` int(11) NOT NULL,
  `gold` bigint(20) NOT NULL,
  `other_info` varchar(1024) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`),
  KEY `user_id` (`user_id`,`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_fish_get_ietms
-- ----------------------------
DROP TABLE IF EXISTS `t_fish_get_ietms`;
CREATE TABLE `t_fish_get_ietms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `type` bigint(20) NOT NULL,
  `cost_item_id` bigint(20) NOT NULL,
  `cost_item_count` bigint(20) NOT NULL,
  `get_info` varchar(1024) NOT NULL,
  `other_info` varchar(1024) NOT NULL,
  `vip_level` varchar(128) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_fish_gm
-- ----------------------------
DROP TABLE IF EXISTS `t_fish_gm`;
CREATE TABLE `t_fish_gm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `gm` varchar(256) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `gold` bigint(20) NOT NULL,
  `other_info` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_fish_item
-- ----------------------------
DROP TABLE IF EXISTS `t_fish_item`;
CREATE TABLE `t_fish_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `level` int(11) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `other_info` varchar(1024) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_fish_jackpot
-- ----------------------------
DROP TABLE IF EXISTS `t_fish_jackpot`;
CREATE TABLE `t_fish_jackpot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `level` int(11) NOT NULL,
  `gold` bigint(20) NOT NULL,
  `other_info` varchar(1024) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_fish_mb_grants
-- ----------------------------
DROP TABLE IF EXISTS `t_fish_mb_grants`;
CREATE TABLE `t_fish_mb_grants` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `type` bigint(20) NOT NULL,
  `bouns` varchar(1024) NOT NULL,
  `other_info` varchar(1024) NOT NULL,
  `vip_level` varchar(128) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_fish_paiment
-- ----------------------------
DROP TABLE IF EXISTS `t_fish_paiment`;
CREATE TABLE `t_fish_paiment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `level` int(11) NOT NULL,
  `gold` bigint(20) NOT NULL,
  `other_info` varchar(1024) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`),
  KEY `user_id` (`user_id`),
  KEY `user_id_2` (`user_id`,`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_fruit_bet
-- ----------------------------
DROP TABLE IF EXISTS `t_fruit_bet`;
CREATE TABLE `t_fruit_bet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `chips` bigint(20) NOT NULL,
  `idx` int(11) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_fruit_jackpot
-- ----------------------------
DROP TABLE IF EXISTS `t_fruit_jackpot`;
CREATE TABLE `t_fruit_jackpot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `chips` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_fruit_pump
-- ----------------------------
DROP TABLE IF EXISTS `t_fruit_pump`;
CREATE TABLE `t_fruit_pump` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `hour_of_day` int(11) NOT NULL,
  `minute` int(11) NOT NULL,
  `chips` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_fruit_win
-- ----------------------------
DROP TABLE IF EXISTS `t_fruit_win`;
CREATE TABLE `t_fruit_win` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `chips` bigint(20) NOT NULL,
  `winkey` varchar(256) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_gdesk_bet
-- ----------------------------
DROP TABLE IF EXISTS `t_gdesk_bet`;
CREATE TABLE `t_gdesk_bet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `desk_id` bigint(20) NOT NULL,
  `desk_type` varchar(32) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `chips` bigint(20) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_gdesk_counter
-- ----------------------------
DROP TABLE IF EXISTS `t_gdesk_counter`;
CREATE TABLE `t_gdesk_counter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `desk_id` bigint(20) NOT NULL,
  `desk_type` varchar(32) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `duration` bigint(20) NOT NULL,
  `detail` varchar(256) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_gdesk_expense
-- ----------------------------
DROP TABLE IF EXISTS `t_gdesk_expense`;
CREATE TABLE `t_gdesk_expense` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `desk_id` bigint(20) NOT NULL,
  `desk_type` varchar(32) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `kind` varchar(32) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `chips` bigint(20) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_gdesk_hlhc
-- ----------------------------
DROP TABLE IF EXISTS `t_gdesk_hlhc`;
CREATE TABLE `t_gdesk_hlhc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `desk_id` bigint(20) NOT NULL,
  `desk_type` varchar(32) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `chips` bigint(20) NOT NULL,
  `is_robot` char(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_gdesk_jackpot
-- ----------------------------
DROP TABLE IF EXISTS `t_gdesk_jackpot`;
CREATE TABLE `t_gdesk_jackpot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `desk_id` bigint(20) NOT NULL,
  `desk_type` varchar(32) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `chips` bigint(20) NOT NULL,
  `cards` varchar(64) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_gdesk_play
-- ----------------------------
DROP TABLE IF EXISTS `t_gdesk_play`;
CREATE TABLE `t_gdesk_play` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `desk_id` bigint(20) NOT NULL,
  `desk_type` varchar(32) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `chips` bigint(20) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_gdesk_pump
-- ----------------------------
DROP TABLE IF EXISTS `t_gdesk_pump`;
CREATE TABLE `t_gdesk_pump` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `desk_id` bigint(20) NOT NULL,
  `desk_type` varchar(32) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `chips` bigint(20) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_gdesk_win
-- ----------------------------
DROP TABLE IF EXISTS `t_gdesk_win`;
CREATE TABLE `t_gdesk_win` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `desk_id` bigint(20) NOT NULL,
  `desk_type` varchar(32) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `chips` bigint(20) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_gdesk_win_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_gdesk_win_detail`;
CREATE TABLE `t_gdesk_win_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `winner_id` bigint(20) NOT NULL,
  `loser_id` bigint(20) NOT NULL,
  `desk_id` bigint(20) NOT NULL,
  `desk_type` varchar(32) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `chips` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_golden_egg_bonus
-- ----------------------------
DROP TABLE IF EXISTS `t_golden_egg_bonus`;
CREATE TABLE `t_golden_egg_bonus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `cost_item_id` bigint(20) NOT NULL,
  `cost_item_count` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_golden_egg_reward
-- ----------------------------
DROP TABLE IF EXISTS `t_golden_egg_reward`;
CREATE TABLE `t_golden_egg_reward` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_grab_red
-- ----------------------------
DROP TABLE IF EXISTS `t_grab_red`;
CREATE TABLE `t_grab_red` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `inning_id` bigint(20) NOT NULL,
  `round_id` bigint(20) NOT NULL,
  `gold` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_grants
-- ----------------------------
DROP TABLE IF EXISTS `t_grants`;
CREATE TABLE `t_grants` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `type` int(11) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `gold` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_grants2
-- ----------------------------
DROP TABLE IF EXISTS `t_grants2`;
CREATE TABLE `t_grants2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `type` int(11) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `gold` bigint(20) NOT NULL,
  `exp` bigint(20) NOT NULL,
  `hp` int(11) DEFAULT '0',
  `is_ccgames` char(1) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_handle
-- ----------------------------
DROP TABLE IF EXISTS `t_handle`;
CREATE TABLE `t_handle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `handle` varchar(128) NOT NULL,
  `other_info` varchar(1024) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_important_props
-- ----------------------------
DROP TABLE IF EXISTS `t_important_props`;
CREATE TABLE `t_important_props` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `send_appid` varchar(32) NOT NULL,
  `get_appid` varchar(32) NOT NULL,
  `send_user_id` bigint(20) NOT NULL,
  `get_user_id` bigint(20) NOT NULL,
  `send_user_info` varchar(256) NOT NULL,
  `get_user_info` varchar(256) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_install
-- ----------------------------
DROP TABLE IF EXISTS `t_install`;
CREATE TABLE `t_install` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `open_id` varchar(32) NOT NULL,
  `inviter_id` varchar(32) NOT NULL,
  `from_where` varchar(128) NOT NULL,
  `ip` varchar(40) NOT NULL,
  `remark` varchar(256) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`),
  KEY `appid` (`appid`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_lottery_top_bonus
-- ----------------------------
DROP TABLE IF EXISTS `t_lottery_top_bonus`;
CREATE TABLE `t_lottery_top_bonus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `rank` int(11) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mission_finish
-- ----------------------------
DROP TABLE IF EXISTS `t_mission_finish`;
CREATE TABLE `t_mission_finish` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `type` int(11) NOT NULL,
  `gold` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mission_finish2
-- ----------------------------
DROP TABLE IF EXISTS `t_mission_finish2`;
CREATE TABLE `t_mission_finish2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `type` int(11) NOT NULL,
  `gold` bigint(20) NOT NULL,
  `exp` bigint(20) NOT NULL,
  `hp` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mjdr_handle
-- ----------------------------
DROP TABLE IF EXISTS `t_mjdr_handle`;
CREATE TABLE `t_mjdr_handle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `type` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `inning_id` bigint(20) NOT NULL,
  `handle_type` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mjdr_hp
-- ----------------------------
DROP TABLE IF EXISTS `t_mjdr_hp`;
CREATE TABLE `t_mjdr_hp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `type` int(10) NOT NULL,
  `count` bigint(20) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mjdr_inning
-- ----------------------------
DROP TABLE IF EXISTS `t_mjdr_inning`;
CREATE TABLE `t_mjdr_inning` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `inning_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mjdr_kaifang
-- ----------------------------
DROP TABLE IF EXISTS `t_mjdr_kaifang`;
CREATE TABLE `t_mjdr_kaifang` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id1` bigint(20) NOT NULL,
  `user_id2` bigint(20) NOT NULL,
  `user_id3` bigint(20) NOT NULL,
  `user_id4` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `desk_id` int(10) NOT NULL,
  `game_type` int(10) NOT NULL,
  `fan` int(10) NOT NULL,
  `jushu` int(10) NOT NULL,
  `is_huansan` char(1) NOT NULL DEFAULT 'N',
  `other_info` varchar(256) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mjdr_lottery
-- ----------------------------
DROP TABLE IF EXISTS `t_mjdr_lottery`;
CREATE TABLE `t_mjdr_lottery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `level` int(11) NOT NULL,
  `lottery_level` int(11) NOT NULL,
  `inning_id` bigint(20) NOT NULL,
  `cost_score` bigint(20) NOT NULL,
  `own_score` bigint(20) NOT NULL,
  `bonus_id` bigint(20) NOT NULL,
  `bonus_count` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mjdr_online
-- ----------------------------
DROP TABLE IF EXISTS `t_mjdr_online`;
CREATE TABLE `t_mjdr_online` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `type` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `inning_id` bigint(20) NOT NULL,
  `millis` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mjdr_play
-- ----------------------------
DROP TABLE IF EXISTS `t_mjdr_play`;
CREATE TABLE `t_mjdr_play` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `type` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `inning_id` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mjdr_rake
-- ----------------------------
DROP TABLE IF EXISTS `t_mjdr_rake`;
CREATE TABLE `t_mjdr_rake` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `type` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `inning_id` bigint(20) NOT NULL,
  `gold` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mjdr_stat
-- ----------------------------
DROP TABLE IF EXISTS `t_mjdr_stat`;
CREATE TABLE `t_mjdr_stat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `type` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `inning_id` bigint(20) NOT NULL,
  `zimo` int(11) NOT NULL,
  `dianpao` int(11) NOT NULL,
  `minggang` int(11) NOT NULL,
  `angang` int(11) NOT NULL,
  `fan` varchar(1024) NOT NULL,
  `is_ccgames` char(1) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mjdr_upgrade
-- ----------------------------
DROP TABLE IF EXISTS `t_mjdr_upgrade`;
CREATE TABLE `t_mjdr_upgrade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `levels` int(11) NOT NULL,
  `gold` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mjdr_win
-- ----------------------------
DROP TABLE IF EXISTS `t_mjdr_win`;
CREATE TABLE `t_mjdr_win` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `type` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `inning_id` bigint(20) NOT NULL,
  `gold` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mjdr_win_streak
-- ----------------------------
DROP TABLE IF EXISTS `t_mjdr_win_streak`;
CREATE TABLE `t_mjdr_win_streak` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `level` int(11) NOT NULL,
  `game_type` int(10) NOT NULL,
  `num` int(11) NOT NULL,
  `get_info` varchar(256) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mp_cost
-- ----------------------------
DROP TABLE IF EXISTS `t_mp_cost`;
CREATE TABLE `t_mp_cost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `master_point` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `addr` varchar(1024) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mp_get
-- ----------------------------
DROP TABLE IF EXISTS `t_mp_get`;
CREATE TABLE `t_mp_get` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `master_point` bigint(20) NOT NULL,
  `source` int(11) NOT NULL,
  `is_robot` char(1) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mtl2_bet
-- ----------------------------
DROP TABLE IF EXISTS `t_mtl2_bet`;
CREATE TABLE `t_mtl2_bet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `bet_gold` bigint(20) NOT NULL,
  `bet_nb_gold` bigint(20) NOT NULL,
  `bet_xnb_gold` bigint(20) NOT NULL,
  `bet_info` varchar(256) NOT NULL,
  `win_gold` bigint(20) NOT NULL,
  `jackpot` bigint(20) NOT NULL,
  `challenge_rank` int(11) NOT NULL,
  `challenge_win` bigint(20) NOT NULL,
  `inning_type` varchar(32) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `champion` varchar(8) NOT NULL,
  `multiple` int(11) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mtl2_pump
-- ----------------------------
DROP TABLE IF EXISTS `t_mtl2_pump`;
CREATE TABLE `t_mtl2_pump` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `gold` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mtl_bet
-- ----------------------------
DROP TABLE IF EXISTS `t_mtl_bet`;
CREATE TABLE `t_mtl_bet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `chips` bigint(20) NOT NULL,
  `multiple` int(11) NOT NULL,
  `button` varchar(10) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mtl_jackpot
-- ----------------------------
DROP TABLE IF EXISTS `t_mtl_jackpot`;
CREATE TABLE `t_mtl_jackpot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `chips` bigint(20) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mtl_pump
-- ----------------------------
DROP TABLE IF EXISTS `t_mtl_pump`;
CREATE TABLE `t_mtl_pump` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `hour_of_day` int(11) NOT NULL,
  `minute` int(11) NOT NULL,
  `chips` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_mtl_win
-- ----------------------------
DROP TABLE IF EXISTS `t_mtl_win`;
CREATE TABLE `t_mtl_win` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_info` varchar(256) NOT NULL,
  `inning_id` varchar(32) NOT NULL,
  `chips` bigint(20) NOT NULL,
  `multiple` int(11) NOT NULL,
  `button` varchar(10) NOT NULL,
  `is_ccgames` char(1) NOT NULL DEFAULT 'Y',
  `is_robot` char(1) NOT NULL DEFAULT 'N',
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_newactivity
-- ----------------------------
DROP TABLE IF EXISTS `t_newactivity`;
CREATE TABLE `t_newactivity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `appid` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `cost_id` bigint(20) NOT NULL,
  `cost_count` bigint(20) NOT NULL,
  `get_info` varchar(1024) NOT NULL,
  `is_mobile` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
