/*
 Navicat Premium Data Transfer

 Source Server         : db_qq_game_beijing
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : bj-cdb-36wyknhl.sql.tencentcdb.com:63832
 Source Schema         : biserveranalysis

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 31/08/2019 16:18:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bi_economy
-- ----------------------------
DROP TABLE IF EXISTS `bi_economy`;
CREATE TABLE `bi_economy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(64) NOT NULL COMMENT '应用id',
  `who` varchar(64) NOT NULL COMMENT '用户id',
  `deviceid` varchar(64) NOT NULL COMMENT '设备id',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `itemname` varchar(64) NOT NULL COMMENT '游戏内虚拟物品的名称/ID',
  `itemamount` varchar(50) NOT NULL COMMENT '交易的数量',
  `itemtotalprice` varchar(50) NOT NULL COMMENT '交易的总价',
  `serverid` varchar(50) DEFAULT NULL COMMENT '服务器ID',
  `channelid` varchar(50) DEFAULT NULL COMMENT '渠道ID',
  `level` varchar(64) DEFAULT '-1' COMMENT '账户等级',
  `clientV` varchar(32) DEFAULT NULL COMMENT '前端版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31737 DEFAULT CHARSET=utf8 COMMENT='统计玩家在游戏内虚拟交易\r\n经济系统';

-- ----------------------------
-- Table structure for bi_event
-- ----------------------------
DROP TABLE IF EXISTS `bi_event`;
CREATE TABLE `bi_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(64) NOT NULL COMMENT '应用id',
  `who` varchar(64) NOT NULL COMMENT '用户id',
  `what` varchar(32) NOT NULL COMMENT '自定义事件的名称',
  `deviceid` varchar(64) NOT NULL COMMENT '设备id',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `serverid` varchar(50) DEFAULT NULL COMMENT '服务器ID',
  `channelid` varchar(50) DEFAULT NULL COMMENT '渠道ID',
  `define1` varchar(100) DEFAULT NULL COMMENT '用户自定义',
  `define2` varchar(100) DEFAULT NULL COMMENT '用户自定义',
  `clientV` varchar(32) DEFAULT NULL COMMENT '前端版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1779828 DEFAULT CHARSET=utf8 COMMENT='统计玩家自定义事件'
/*!50100 PARTITION BY RANGE (id)
(PARTITION p0 VALUES LESS THAN (500000) ENGINE = InnoDB,
 PARTITION p1 VALUES LESS THAN (1000000) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (1500000) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;

-- ----------------------------
-- Table structure for bi_heartbeat
-- ----------------------------
DROP TABLE IF EXISTS `bi_heartbeat`;
CREATE TABLE `bi_heartbeat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(64) NOT NULL COMMENT '应用id',
  `who` varchar(64) NOT NULL COMMENT '用户id',
  `deviceid` varchar(64) NOT NULL COMMENT '设备id',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `serverid` varchar(50) DEFAULT NULL COMMENT '服务器ID',
  `channelid` varchar(64) DEFAULT NULL COMMENT '渠道ID',
  `level` varchar(64) DEFAULT '-1' COMMENT '账户等级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9098351 DEFAULT CHARSET=utf8 COMMENT='统计玩家在线'
/*!50100 PARTITION BY RANGE (id)
(PARTITION p0 VALUES LESS THAN (500000) ENGINE = InnoDB,
 PARTITION p1 VALUES LESS THAN (1000000) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (1500000) ENGINE = InnoDB,
 PARTITION pend VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;

-- ----------------------------
-- Table structure for bi_install
-- ----------------------------
DROP TABLE IF EXISTS `bi_install`;
CREATE TABLE `bi_install` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(64) NOT NULL COMMENT '应用id',
  `deviceid` varchar(64) NOT NULL COMMENT '设备id',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `date` date NOT NULL COMMENT '设备日期',
  `time` time NOT NULL COMMENT '设备时间',
  `idfa` varchar(64) DEFAULT '' COMMENT '广告标识符',
  `idfv` varchar(64) DEFAULT '' COMMENT 'Vindor标示符',
  `channelid` varchar(100) DEFAULT NULL COMMENT '渠道ID',
  `clientV` varchar(32) DEFAULT NULL COMMENT '前端版本',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_appid_deviceid` (`appid`,`deviceid`) USING BTREE COMMENT '设备和游戏ID索引',
  KEY `index_deviceid` (`deviceid`) USING BTREE COMMENT '设备ID索引',
  KEY `index_createtime` (`createtime`) USING BTREE COMMENT '时间索引'
) ENGINE=InnoDB AUTO_INCREMENT=504454 DEFAULT CHARSET=utf8 COMMENT='统计玩家第一次打开应用\r\n安装应用';

-- ----------------------------
-- Table structure for bi_login
-- ----------------------------
DROP TABLE IF EXISTS `bi_login`;
CREATE TABLE `bi_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(64) NOT NULL COMMENT '应用id',
  `who` varchar(64) NOT NULL COMMENT '用户id',
  `deviceid` varchar(64) NOT NULL COMMENT '设备id',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `date` date NOT NULL COMMENT '设备日期',
  `time` time NOT NULL COMMENT '设备时间',
  `idfa` varchar(64) DEFAULT '' COMMENT '广告标识符',
  `idfv` varchar(64) DEFAULT '' COMMENT 'Vindor标示符',
  `serverid` varchar(50) DEFAULT NULL COMMENT '服务器ID',
  `channelid` varchar(64) DEFAULT NULL COMMENT '渠道ID',
  `level` varchar(64) DEFAULT '-1' COMMENT '账户等级',
  `clientV` varchar(32) DEFAULT NULL COMMENT '前端版本',
  PRIMARY KEY (`id`),
  KEY `index_deviceid` (`deviceid`) USING BTREE COMMENT '设备ID索引',
  KEY `index_createtime` (`createtime`) USING BTREE COMMENT '时间索引',
  KEY `index_who` (`who`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2618734 DEFAULT CHARSET=utf8 COMMENT='统计玩家登陆'
/*!50100 PARTITION BY RANGE (id)
(PARTITION p0 VALUES LESS THAN (500000) ENGINE = InnoDB,
 PARTITION p1 VALUES LESS THAN (1000000) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (1500000) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;

-- ----------------------------
-- Table structure for bi_payment
-- ----------------------------
DROP TABLE IF EXISTS `bi_payment`;
CREATE TABLE `bi_payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(64) NOT NULL COMMENT '应用id',
  `who` varchar(64) NOT NULL COMMENT '用户id',
  `deviceid` varchar(64) NOT NULL COMMENT '设备id',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `transactionid` varchar(64) NOT NULL COMMENT '交易的流水号',
  `paymenttype` varchar(64) NOT NULL COMMENT '支付类型，例如支付宝，银联，苹果、谷歌官方等,如果是系统赠送的，paymentType为：free',
  `currencytype` varchar(64) NOT NULL COMMENT '货币类型，按照国际标准组织ISO 4217中规范的3位字母，例如CNY人民币、USD美金等，详情请点击',
  `currencyamount` varchar(64) NOT NULL COMMENT '支付的真实货币的金额',
  `virtualcoinamount` varchar(64) NOT NULL COMMENT '通过充值获得的游戏内货币的数量',
  `iapname` varchar(64) NOT NULL COMMENT '游戏内购买道具的名称',
  `iapamount` varchar(64) NOT NULL COMMENT '游戏内购买道具的数量',
  `serverid` varchar(50) DEFAULT NULL COMMENT '服务器ID',
  `channelid` varchar(64) DEFAULT NULL COMMENT '渠道ID',
  `idfa` varchar(64) DEFAULT '' COMMENT '广告标识符',
  `idfv` varchar(64) DEFAULT '' COMMENT 'Vindor标示符',
  `level` varchar(64) DEFAULT '-1' COMMENT '账户等级',
  `clientV` varchar(32) DEFAULT NULL COMMENT '前端版本',
  PRIMARY KEY (`id`),
  KEY `index_deviceid` (`deviceid`) USING BTREE COMMENT '设备ID索引',
  KEY `index_createtime` (`createtime`) USING BTREE COMMENT '时间索引',
  KEY `index_who` (`who`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=81728 DEFAULT CHARSET=utf8 COMMENT='统计玩家充值'
/*!50100 PARTITION BY RANGE (id)
(PARTITION p0 VALUES LESS THAN (500000) ENGINE = InnoDB,
 PARTITION p1 VALUES LESS THAN (1000000) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (1500000) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;

-- ----------------------------
-- Table structure for bi_quest
-- ----------------------------
DROP TABLE IF EXISTS `bi_quest`;
CREATE TABLE `bi_quest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(64) NOT NULL COMMENT '应用id',
  `who` varchar(64) NOT NULL COMMENT '用户id',
  `deviceid` varchar(64) NOT NULL COMMENT '设备id',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `questid` varchar(64) NOT NULL COMMENT '当前任务/关卡/副本的编号或名称',
  `queststatus` varchar(16) NOT NULL COMMENT '当前任务/关卡/副本的状态，有如下三种类型：开始：a完成：c失败：f',
  `questtype` varchar(16) NOT NULL COMMENT '当前任务/关卡/副本的类型，例如： 新手任务：new 主线任务：main 支线任务：sub 开发者也可以根据自己游戏的特点自定义类型',
  `serverid` varchar(50) DEFAULT NULL COMMENT '服务器ID',
  `channelid` varchar(64) DEFAULT NULL COMMENT '渠道ID',
  `level` varchar(64) DEFAULT '-1' COMMENT '账户等级',
  `clientV` varchar(32) DEFAULT NULL COMMENT '前端版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31804 DEFAULT CHARSET=utf8 COMMENT='.统计玩家的任务/关卡/副本';

-- ----------------------------
-- Table structure for bi_register
-- ----------------------------
DROP TABLE IF EXISTS `bi_register`;
CREATE TABLE `bi_register` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(64) NOT NULL COMMENT '应用id',
  `who` varchar(64) NOT NULL COMMENT '用户id',
  `deviceid` varchar(64) NOT NULL COMMENT '设备id',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `idfa` varchar(64) DEFAULT '' COMMENT '广告标识符',
  `idfv` varchar(64) DEFAULT '' COMMENT 'Vindor标示符',
  `accounttype` varchar(64) DEFAULT NULL COMMENT '账户类型',
  `gender` enum('') DEFAULT NULL COMMENT '账户性别',
  `age` varchar(50) DEFAULT '-1' COMMENT '用户年龄',
  `serverid` varchar(50) DEFAULT NULL COMMENT '服务器ID',
  `channelid` varchar(64) DEFAULT NULL COMMENT '渠道ID',
  `clientV` varchar(32) DEFAULT NULL COMMENT '前端版本',
  PRIMARY KEY (`id`),
  KEY `index_deviceid` (`deviceid`) USING BTREE COMMENT '设备ID索引',
  KEY `index_createtime` (`createtime`) USING BTREE COMMENT '时间索引'
) ENGINE=InnoDB AUTO_INCREMENT=2143296 DEFAULT CHARSET=utf8 COMMENT='统计玩家账号注册'
/*!50100 PARTITION BY RANGE (id)
(PARTITION p0 VALUES LESS THAN (500000) ENGINE = InnoDB,
 PARTITION p1 VALUES LESS THAN (1000000) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (1500000) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;

-- ----------------------------
-- Table structure for bi_startup
-- ----------------------------
DROP TABLE IF EXISTS `bi_startup`;
CREATE TABLE `bi_startup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(64) NOT NULL COMMENT '应用id',
  `deviceid` varchar(64) NOT NULL COMMENT '设备id',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `date` date NOT NULL COMMENT '设备日期',
  `time` time NOT NULL COMMENT '设备时间',
  `idfa` varchar(64) DEFAULT '' COMMENT '广告标识符',
  `idfv` varchar(64) DEFAULT '' COMMENT 'Vindor标示符',
  `channelid` varchar(64) DEFAULT NULL COMMENT '渠道ID',
  `ip` varchar(64) DEFAULT '' COMMENT 'ip',
  `network` varchar(64) DEFAULT NULL COMMENT '网络制式2G,3G,WIFI',
  `devicetype` varchar(64) DEFAULT NULL COMMENT '设备类型 机型 iphone4s',
  `os` varchar(64) DEFAULT NULL COMMENT '操作系统 ios7.0',
  `op` varchar(64) DEFAULT NULL COMMENT '运营商',
  `resolution` varchar(64) DEFAULT NULL COMMENT '分辨率',
  `tz` varchar(200) DEFAULT '' COMMENT '时区',
  `clientV` varchar(32) DEFAULT NULL COMMENT '前端版本',
  `extra` varchar(255) DEFAULT NULL COMMENT '额外信息',
  PRIMARY KEY (`id`),
  KEY `index_createtime` (`createtime`) USING BTREE COMMENT '时间索引'
) ENGINE=InnoDB AUTO_INCREMENT=2184531 DEFAULT CHARSET=utf8 COMMENT='统计玩家打开应用'
/*!50100 PARTITION BY RANGE (id)
(PARTITION p0 VALUES LESS THAN (500000) ENGINE = InnoDB,
 PARTITION p1 VALUES LESS THAN (1000000) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (1500000) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;

-- ----------------------------
-- Table structure for bi_user
-- ----------------------------
DROP TABLE IF EXISTS `bi_user`;
CREATE TABLE `bi_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deviceid` varchar(64) NOT NULL COMMENT '设备id',
  `appID` varchar(64) NOT NULL DEFAULT '0' COMMENT '游戏appid',
  `userID` varchar(64) NOT NULL COMMENT '玩家用户id',
  `userOpenID` varchar(64) DEFAULT NULL COMMENT 'user open id',
  `userName` varchar(200) DEFAULT '' COMMENT '玩家数据信息',
  `userGold` varchar(64) DEFAULT NULL COMMENT '金币',
  `userDiamond` varchar(64) DEFAULT NULL COMMENT '砖石',
  `userSex` varchar(10) DEFAULT NULL COMMENT '性别',
  `userLevel` varchar(64) DEFAULT NULL COMMENT '等级',
  `userExp` varchar(64) DEFAULT NULL COMMENT '经验',
  `userIsRecharged` varchar(10) DEFAULT NULL COMMENT '是否充值过',
  `userVIPExp` varchar(64) DEFAULT NULL COMMENT 'vip经验',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `clientV` varchar(32) DEFAULT NULL COMMENT '前端版本',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_userid` (`userID`) USING BTREE COMMENT '用户id索引'
) ENGINE=InnoDB AUTO_INCREMENT=8828120 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户基本信息表';

SET FOREIGN_KEY_CHECKS = 1;
