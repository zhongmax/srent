/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : srent

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 01/04/2021 11:00:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for srent_address
-- ----------------------------
DROP TABLE IF EXISTS `srent_address`;
CREATE TABLE `srent_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '收获人名称',
  `user_id` int NOT NULL DEFAULT '0' COMMENT '用户表的用户ID',
  `province` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '行政区域表的省ID',
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '行政区域表的市ID',
  `county` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '行政区域表的区县ID',
  `address_detail` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '详细收获地址',
  `area_code` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地区编码',
  `postal_code` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮政编码',
  `tel` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号码',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认地址',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='收获地址表';

-- ----------------------------
-- Records of srent_address
-- ----------------------------
BEGIN;
INSERT INTO `srent_address` VALUES (8, '测试', 11, '重庆市', '市辖区', '巴南区', '重庆市巴南区xx路xx号', '500113', NULL, '15923022184', 1, '2019-09-24 23:57:31', '2019-09-24 23:57:31', 0);
COMMIT;

-- ----------------------------
-- Table structure for srent_admin
-- ----------------------------
DROP TABLE IF EXISTS `srent_admin`;
CREATE TABLE `srent_admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '管理员名称',
  `password` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '管理员密码',
  `last_login_ip` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最近一次登录IP地址',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近一次登录时间',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_sv_0900_ai_ci DEFAULT '''' COMMENT '头像图片',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  `role_ids` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '[]' COMMENT '角色列表',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='管理员表';

-- ----------------------------
-- Records of srent_admin
-- ----------------------------
BEGIN;
INSERT INTO `srent_admin` VALUES (1, 'maxwell', '$2a$10$7CGMbt4WnGCPiQJQVIRfP.SF07K/NJAnkT1Ti3ZX1G1cBwrBCvDti', '0:0:0:0:0:0:0:1', '2021-04-01 02:56:15', '', '2021-04-01 08:48:42', '2021-04-01 02:56:15', 0, '[1]');
COMMIT;

-- ----------------------------
-- Table structure for srent_category
-- ----------------------------
DROP TABLE IF EXISTS `srent_category`;
CREATE TABLE `srent_category` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `name` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型名称',
  `keywords` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '类型关键词，以JSON数组格式',
  `pid` int NOT NULL DEFAULT '0' COMMENT '父类ID',
  `icon_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '类目图标',
  `level` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'L1',
  `sort_order` tinyint DEFAULT NULL COMMENT '排序',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of srent_category
-- ----------------------------
BEGIN;
INSERT INTO `srent_category` VALUES (1, '手机', '', 0, 'http://pxc4naypd.bkt.clouddn.com/pad.png', 'L1', 1, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (2, '相机', '', 0, 'http://pxc4naypd.bkt.clouddn.com/camera.png', 'L1', 2, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (3, '游戏机', '', 0, 'http://pxc4naypd.bkt.clouddn.com/game.png', 'L1', 3, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (4, '电脑', '', 0, 'http://pxc4naypd.bkt.clouddn.com/computer.png', 'L1', 4, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (5, '华为', '', 1, 'http://pxc4naypd.bkt.clouddn.com/huawei.jpg', 'L2', 1, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (6, 'OPPO', '', 1, 'http://pxc4naypd.bkt.clouddn.com/oppo.jpg', 'L2', 2, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (7, 'vivo', '', 1, 'http://pxc4naypd.bkt.clouddn.com/vivo.jpg', 'L2', 3, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (8, '苹果', '', 1, 'http://pxc4naypd.bkt.clouddn.com/apple.jpg', 'L2', 4, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (9, '三星', '', 1, 'http://pxc4naypd.bkt.clouddn.com/sansung.jpg', 'L2', 5, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (10, '小米', '', 1, 'http://pxc4naypd.bkt.clouddn.com/xiaomi.jpg', 'L2', 6, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (11, '魅族', '', 1, 'http://pxc4naypd.bkt.clouddn.com/meizu.jpg', 'L2', 7, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (12, '努比亚', '', 1, 'http://pxc4naypd.bkt.clouddn.com/nubiq.jpg', 'L2', 8, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (13, '一加', '', 1, 'http://pxc4naypd.bkt.clouddn.com/oneplus.jpg', 'L2', 9, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (14, '索尼', '', 1, 'http://pxc4naypd.bkt.clouddn.com/sony.jpg', 'L2', 10, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (15, 'HTC', '', 1, 'http://pxc4naypd.bkt.clouddn.com/htc.jpg', 'L2', 11, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (16, '佳能', '', 2, 'http://pxc4naypd.bkt.clouddn.com/canon.jpg', 'L2', 1, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (17, '尼康', '', 2, 'http://pxc4naypd.bkt.clouddn.com/nikon.jpg', 'L2', 2, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (18, '索尼', '', 2, 'http://pxc4naypd.bkt.clouddn.com/sony_camera.jpg', 'L2', 3, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (19, '富士', '', 2, 'http://pxc4naypd.bkt.clouddn.com/fujifilm.jpg', 'L2', 4, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (20, '松下', '', 2, 'http://pxc4naypd.bkt.clouddn.com/panasonic.jpg', 'L2', 5, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (21, '徕卡', '', 2, 'http://pxc4naypd.bkt.clouddn.com/leica.jpg', 'L2', 6, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (22, '柯达', '', 2, 'http://pxc4naypd.bkt.clouddn.com/kodak.jpg', 'L2', 7, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (23, '卡西欧', '', 2, 'http://pxc4naypd.bkt.clouddn.com/casio.jpg', 'L2', 8, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (24, '任天堂', '', 3, 'http://pxc4naypd.bkt.clouddn.com/nintendo.png', 'L2', 1, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (25, '微软', '', 3, 'http://pxc4naypd.bkt.clouddn.com/xbox.png', 'L2', 2, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (26, '索尼', '', 3, 'http://pxc4naypd.bkt.clouddn.com/ps.png', 'L2', 3, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (27, 'NVIDIA', '', 3, 'http://pxc4naypd.bkt.clouddn.com/nvidia.jpg', 'L2', 4, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (28, '联想', '', 4, 'http://pxc4naypd.bkt.clouddn.com/lenovo.jpg', 'L2', 1, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (29, '华硕', '', 4, 'http://pxc4naypd.bkt.clouddn.com/asus.jpg', 'L2', 2, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (30, '戴尔', '', 4, 'http://pxc4naypd.bkt.clouddn.com/dell.jpg', 'L2', 3, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (31, '惠普', '', 4, 'http://csmaxwell.com/blog/asset/201907/brand/hp.jpg', 'L2', 4, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (32, '宏碁', '', 4, 'http://csmaxwell.com/blog/asset/201907/brand/acer.jpg', 'L2', 5, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (33, '微星', '', 4, 'http://csmaxwell.com/blog/asset/201907/brand/msi.jpg', 'L2', 6, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (34, '神舟', '', 4, 'http://csmaxwell.com/blog/asset/201907/brand/hasee.jpg', 'L2', 7, '2019-08-16 15:51:51', '2019-08-16 15:51:51', 0);
INSERT INTO `srent_category` VALUES (36, '其他', '', 1, 'http://qiniu.csmaxwell.xyz/Other.png', 'L2', 12, NULL, NULL, 0);
INSERT INTO `srent_category` VALUES (37, '其他', '', 2, 'http://qiniu.csmaxwell.xyz/Other.png', 'L2', 9, NULL, NULL, 0);
INSERT INTO `srent_category` VALUES (38, '其他', '', 3, 'http://qiniu.csmaxwell.xyz/Other.png', 'L2', 5, NULL, NULL, 0);
INSERT INTO `srent_category` VALUES (39, '其他', '', 4, 'http://qiniu.csmaxwell.xyz/Other.png', 'L2', 8, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for srent_chat
-- ----------------------------
DROP TABLE IF EXISTS `srent_chat`;
CREATE TABLE `srent_chat` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL DEFAULT '0' COMMENT '买家ID',
  `post_user_id` int NOT NULL DEFAULT '0' COMMENT '卖家ID',
  `goods_id` int NOT NULL COMMENT '商品ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for srent_collect
-- ----------------------------
DROP TABLE IF EXISTS `srent_collect`;
CREATE TABLE `srent_collect` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL DEFAULT '0' COMMENT '用户表的用户ID',
  `value_id` int NOT NULL DEFAULT '0' COMMENT 'type=0商品 1专题',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '收藏类型 0 商品ID 1 专题ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `goods_id` (`value_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='收藏表';

-- ----------------------------
-- Records of srent_collect
-- ----------------------------
BEGIN;
INSERT INTO `srent_collect` VALUES (85, 5, 81, 0, '2019-09-24 23:22:29', '2019-09-24 23:22:29', 0);
INSERT INTO `srent_collect` VALUES (86, 5, 84, 0, '2019-09-24 23:22:37', '2019-09-24 23:22:37', 0);
INSERT INTO `srent_collect` VALUES (87, 11, 78, 0, '2019-09-24 23:37:57', '2019-09-24 23:37:57', 0);
INSERT INTO `srent_collect` VALUES (88, 11, 83, 0, '2019-09-24 23:38:02', '2019-09-24 23:38:02', 0);
INSERT INTO `srent_collect` VALUES (89, 11, 86, 0, '2019-09-24 23:38:06', '2019-09-24 23:38:06', 0);
COMMIT;

-- ----------------------------
-- Table structure for srent_footprint
-- ----------------------------
DROP TABLE IF EXISTS `srent_footprint`;
CREATE TABLE `srent_footprint` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL DEFAULT '0' COMMENT '用户表的用户ID',
  `goods_id` int NOT NULL DEFAULT '0' COMMENT '浏览商品ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1630 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户浏览足迹表';

-- ----------------------------
-- Records of srent_footprint
-- ----------------------------
BEGIN;
INSERT INTO `srent_footprint` VALUES (1559, 5, 89, '2019-09-24 23:21:31', '2019-09-24 23:21:31', 0);
INSERT INTO `srent_footprint` VALUES (1560, 5, 86, '2019-09-24 23:21:35', '2019-09-24 23:21:35', 0);
INSERT INTO `srent_footprint` VALUES (1561, 5, 85, '2019-09-24 23:21:39', '2019-09-24 23:21:39', 0);
INSERT INTO `srent_footprint` VALUES (1562, 5, 81, '2019-09-24 23:22:26', '2019-09-24 23:22:26', 0);
INSERT INTO `srent_footprint` VALUES (1563, 5, 80, '2019-09-24 23:22:32', '2019-09-24 23:22:32', 0);
INSERT INTO `srent_footprint` VALUES (1564, 5, 84, '2019-09-24 23:22:35', '2019-09-24 23:22:35', 0);
INSERT INTO `srent_footprint` VALUES (1565, 5, 85, '2019-09-24 23:24:04', '2019-09-24 23:24:04', 0);
INSERT INTO `srent_footprint` VALUES (1566, 5, 85, '2019-09-24 23:24:21', '2019-09-24 23:24:21', 0);
INSERT INTO `srent_footprint` VALUES (1567, 5, 85, '2019-09-24 23:24:24', '2019-09-24 23:24:24', 0);
INSERT INTO `srent_footprint` VALUES (1568, 5, 83, '2019-09-24 23:24:40', '2019-09-24 23:24:40', 0);
INSERT INTO `srent_footprint` VALUES (1569, 5, 83, '2019-09-24 23:24:46', '2019-09-24 23:24:46', 0);
INSERT INTO `srent_footprint` VALUES (1570, 5, 85, '2019-09-24 23:24:48', '2019-09-24 23:24:48', 0);
INSERT INTO `srent_footprint` VALUES (1571, 5, 85, '2019-09-24 23:24:50', '2019-09-24 23:24:50', 0);
INSERT INTO `srent_footprint` VALUES (1572, 5, 85, '2019-09-24 23:25:42', '2019-09-24 23:25:42', 0);
INSERT INTO `srent_footprint` VALUES (1573, 5, 85, '2019-09-24 23:25:46', '2019-09-24 23:25:46', 0);
INSERT INTO `srent_footprint` VALUES (1574, 5, 85, '2019-09-24 23:25:53', '2019-09-24 23:25:53', 0);
INSERT INTO `srent_footprint` VALUES (1575, 5, 83, '2019-09-24 23:25:55', '2019-09-24 23:25:55', 0);
INSERT INTO `srent_footprint` VALUES (1576, 5, 83, '2019-09-24 23:25:59', '2019-09-24 23:25:59', 0);
INSERT INTO `srent_footprint` VALUES (1577, 5, 84, '2019-09-24 23:26:05', '2019-09-24 23:26:05', 0);
INSERT INTO `srent_footprint` VALUES (1578, 5, 84, '2019-09-24 23:26:07', '2019-09-24 23:26:07', 0);
INSERT INTO `srent_footprint` VALUES (1579, 5, 84, '2019-09-24 23:26:16', '2019-09-24 23:26:16', 0);
INSERT INTO `srent_footprint` VALUES (1580, 5, 79, '2019-09-24 23:26:21', '2019-09-24 23:26:21', 0);
INSERT INTO `srent_footprint` VALUES (1581, 5, 84, '2019-09-24 23:26:30', '2019-09-24 23:26:30', 0);
INSERT INTO `srent_footprint` VALUES (1582, 5, 84, '2019-09-24 23:26:32', '2019-09-24 23:26:32', 0);
INSERT INTO `srent_footprint` VALUES (1583, 5, 84, '2019-09-24 23:26:38', '2019-09-24 23:26:38', 0);
INSERT INTO `srent_footprint` VALUES (1584, 5, 89, '2019-09-24 23:26:40', '2019-09-24 23:26:40', 0);
INSERT INTO `srent_footprint` VALUES (1585, 5, 89, '2019-09-24 23:27:09', '2019-09-24 23:27:09', 0);
INSERT INTO `srent_footprint` VALUES (1586, 5, 86, '2019-09-24 23:28:15', '2019-09-24 23:28:15', 0);
INSERT INTO `srent_footprint` VALUES (1587, 5, 85, '2019-09-24 23:28:19', '2019-09-24 23:28:19', 0);
INSERT INTO `srent_footprint` VALUES (1588, 5, 83, '2019-09-24 23:28:21', '2019-09-24 23:28:21', 0);
INSERT INTO `srent_footprint` VALUES (1589, 5, 81, '2019-09-24 23:28:24', '2019-09-24 23:28:24', 0);
INSERT INTO `srent_footprint` VALUES (1590, 5, 85, '2019-09-24 23:28:29', '2019-09-24 23:28:29', 0);
INSERT INTO `srent_footprint` VALUES (1591, 5, 85, '2019-09-24 23:29:53', '2019-09-24 23:29:53', 0);
INSERT INTO `srent_footprint` VALUES (1592, 5, 57, '2019-09-24 23:30:12', '2019-09-24 23:30:12', 0);
INSERT INTO `srent_footprint` VALUES (1593, 11, 78, '2019-09-24 23:37:54', '2019-09-24 23:37:54', 0);
INSERT INTO `srent_footprint` VALUES (1594, 11, 83, '2019-09-24 23:38:00', '2019-09-24 23:38:00', 0);
INSERT INTO `srent_footprint` VALUES (1595, 11, 86, '2019-09-24 23:38:05', '2019-09-24 23:38:05', 0);
INSERT INTO `srent_footprint` VALUES (1596, 11, 78, '2019-09-24 23:38:21', '2019-09-24 23:38:21', 0);
INSERT INTO `srent_footprint` VALUES (1597, 11, 79, '2019-09-24 23:38:23', '2019-09-24 23:38:23', 0);
INSERT INTO `srent_footprint` VALUES (1598, 11, 79, '2019-09-24 23:38:34', '2019-09-24 23:38:34', 0);
INSERT INTO `srent_footprint` VALUES (1599, 11, 79, '2019-09-24 23:38:48', '2019-09-24 23:38:48', 0);
INSERT INTO `srent_footprint` VALUES (1600, 5, 79, '2019-09-24 23:40:11', '2019-09-24 23:40:11', 0);
INSERT INTO `srent_footprint` VALUES (1601, 11, 79, '2019-09-24 23:41:13', '2019-09-24 23:41:13', 0);
INSERT INTO `srent_footprint` VALUES (1602, 5, 79, '2019-09-24 23:46:23', '2019-09-24 23:46:23', 0);
INSERT INTO `srent_footprint` VALUES (1603, 5, 79, '2019-09-24 23:49:34', '2019-09-24 23:49:34', 0);
INSERT INTO `srent_footprint` VALUES (1604, 11, 79, '2019-09-24 23:49:39', '2019-09-24 23:49:39', 0);
INSERT INTO `srent_footprint` VALUES (1605, 11, 82, '2019-09-24 23:50:55', '2019-09-24 23:50:55', 0);
INSERT INTO `srent_footprint` VALUES (1606, 11, 82, '2019-09-24 23:50:58', '2019-09-24 23:50:58', 0);
INSERT INTO `srent_footprint` VALUES (1607, 11, 79, '2019-09-24 23:51:07', '2019-09-24 23:51:07', 0);
INSERT INTO `srent_footprint` VALUES (1608, 11, 79, '2019-09-24 23:52:52', '2019-09-24 23:52:52', 0);
INSERT INTO `srent_footprint` VALUES (1609, 5, 78, '2019-09-24 23:53:49', '2019-09-24 23:53:49', 0);
INSERT INTO `srent_footprint` VALUES (1610, 11, 78, '2019-09-24 23:53:55', '2019-09-24 23:53:55', 0);
INSERT INTO `srent_footprint` VALUES (1611, 11, 79, '2019-09-24 23:53:58', '2019-09-24 23:53:58', 0);
INSERT INTO `srent_footprint` VALUES (1612, 11, 78, '2019-09-24 23:54:02', '2019-09-24 23:54:02', 0);
INSERT INTO `srent_footprint` VALUES (1613, 11, 78, '2019-09-24 23:54:03', '2019-09-24 23:54:03', 0);
INSERT INTO `srent_footprint` VALUES (1614, 5, 78, '2019-09-24 23:54:21', '2019-09-24 23:54:21', 0);
INSERT INTO `srent_footprint` VALUES (1615, 11, 78, '2019-09-24 23:55:48', '2019-09-24 23:55:48', 0);
INSERT INTO `srent_footprint` VALUES (1616, 11, 78, '2019-09-24 23:56:08', '2019-09-24 23:56:08', 0);
INSERT INTO `srent_footprint` VALUES (1617, 11, 78, '2019-09-24 23:57:53', '2019-09-24 23:57:53', 0);
INSERT INTO `srent_footprint` VALUES (1618, 11, 80, '2019-09-25 00:02:07', '2019-09-25 00:02:07', 0);
INSERT INTO `srent_footprint` VALUES (1619, 11, 85, '2019-09-25 00:02:10', '2019-09-25 00:02:10', 0);
INSERT INTO `srent_footprint` VALUES (1620, 11, 85, '2019-09-25 00:02:58', '2019-09-25 00:02:58', 0);
INSERT INTO `srent_footprint` VALUES (1621, 11, 79, '2019-09-25 00:08:07', '2019-09-25 00:08:07', 0);
INSERT INTO `srent_footprint` VALUES (1622, 11, 78, '2019-09-25 00:08:21', '2019-09-25 00:08:21', 0);
INSERT INTO `srent_footprint` VALUES (1623, 11, 85, '2019-09-25 00:12:22', '2019-09-25 00:12:22', 0);
INSERT INTO `srent_footprint` VALUES (1624, 11, 86, '2019-09-25 00:12:26', '2019-09-25 00:12:26', 0);
INSERT INTO `srent_footprint` VALUES (1625, 11, 86, '2019-09-25 00:12:27', '2019-09-25 00:12:27', 0);
INSERT INTO `srent_footprint` VALUES (1626, 11, 86, '2019-09-25 00:12:34', '2019-09-25 00:12:34', 0);
INSERT INTO `srent_footprint` VALUES (1627, 3, 86, '2019-09-25 00:12:45', '2019-09-25 00:12:45', 0);
INSERT INTO `srent_footprint` VALUES (1628, 11, 86, '2019-09-25 00:12:54', '2019-09-25 00:12:54', 0);
INSERT INTO `srent_footprint` VALUES (1629, 3, 96, '2019-09-29 19:55:14', '2019-09-29 19:55:14', 0);
COMMIT;

-- ----------------------------
-- Table structure for srent_goods
-- ----------------------------
DROP TABLE IF EXISTS `srent_goods`;
CREATE TABLE `srent_goods` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_sn` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品编号',
  `user_id` int NOT NULL COMMENT '用户ID',
  `rent_price` decimal(10,2) NOT NULL COMMENT '每日租金',
  `deposit` decimal(10,2) NOT NULL COMMENT '押金',
  `name` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `desc` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '描述',
  `category_id` int NOT NULL COMMENT '商品品牌ID',
  `keywords` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品关键字，逗号分隔，从type还有brand中取',
  `gallery` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品图片列表，采用JSON数组格式',
  `sort_order` smallint DEFAULT '100',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `order_id` int DEFAULT NULL COMMENT '订单ID，平时为空，只有生成订单时，向改字段插入订单ID',
  `rented` tinyint(1) DEFAULT '0' COMMENT '是否被租出',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `goods_sn` (`goods_sn`) USING BTREE,
  KEY `sort_order` (`sort_order`) USING BTREE,
  KEY `cat_id` (`category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of srent_goods
-- ----------------------------
BEGIN;
INSERT INTO `srent_goods` VALUES (95, '633', 3, 10.00, 10.00, '戴尔XPS', '少时诵诗书', 30, '戴尔', '[\"http://localhost:8081/wx/storage/fetch/kmcs2i1dppy8jl3mhr4r.jpg\",\"http://localhost:8081/wx/storage/fetch/53a0yaubu4cako4fj8ul.jpg\",\"http://localhost:8081/wx/storage/fetch/fwe594qn23hgcbf0fqdu.jpg\",\"http://localhost:8081/wx/storage/fetch/vexvvmb39op4y1zustm0.jpg\"]', 100, '2019-09-29 19:53:36', '2019-09-29 19:53:36', NULL, 0, 1);
INSERT INTO `srent_goods` VALUES (96, '113', 3, 10.00, 10.00, '戴尔XPS', '1234234', 30, '戴尔', '[\"http://localhost:8080/wx/storage/fetch/3xjgmczl7zytnzn47cer.jpg\",\"http://localhost:8080/wx/storage/fetch/sn31xiewx9qorygtcsvf.jpg\",\"http://localhost:8080/wx/storage/fetch/p5db1n3mmhxlv7ya4pda.jpg\",\"http://localhost:8080/wx/storage/fetch/oudkoapv7wec8jfokn3h.jpg\"]', 100, '2019-09-29 19:55:10', '2019-09-29 19:55:10', NULL, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for srent_keyword
-- ----------------------------
DROP TABLE IF EXISTS `srent_keyword`;
CREATE TABLE `srent_keyword` (
  `id` int NOT NULL AUTO_INCREMENT,
  `keyword` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '关键字',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '关键字的跳转链接',
  `is_hot` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是热门关键字',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是默认关键字',
  `sort_order` int NOT NULL DEFAULT '100' COMMENT '排序',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of srent_keyword
-- ----------------------------
BEGIN;
INSERT INTO `srent_keyword` VALUES (1, '荣耀', '', 0, 0, 100, NULL, NULL, 0);
INSERT INTO `srent_keyword` VALUES (2, '华为', '', 1, 1, 100, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for srent_log
-- ----------------------------
DROP TABLE IF EXISTS `srent_log`;
CREATE TABLE `srent_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `admin` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '管理员',
  `ip` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'IP地址',
  `type` int DEFAULT NULL COMMENT '操作分类',
  `action` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作状态',
  `status` tinyint(1) DEFAULT NULL COMMENT '操作状态',
  `result` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作结果',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '补充信息',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of srent_log
-- ----------------------------
BEGIN;
INSERT INTO `srent_log` VALUES (1, '匿名用户', '0:0:0:0:0:0:0:1', 1, '登录', 0, '认证失败', '', '2021-04-01 01:07:42', '2021-04-01 01:07:42', 0);
INSERT INTO `srent_log` VALUES (2, '匿名用户', '0:0:0:0:0:0:0:1', 1, '登录', 0, '认证失败', '', '2021-04-01 01:08:14', '2021-04-01 01:08:14', 0);
INSERT INTO `srent_log` VALUES (3, '匿名用户', '0:0:0:0:0:0:0:1', 1, '登录', 0, '认证失败', '', '2021-04-01 01:11:28', '2021-04-01 01:11:28', 0);
INSERT INTO `srent_log` VALUES (4, '匿名用户', '0:0:0:0:0:0:0:1', 1, '登录', 0, '认证失败', '', '2021-04-01 01:17:41', '2021-04-01 01:17:41', 0);
INSERT INTO `srent_log` VALUES (5, '匿名用户', '0:0:0:0:0:0:0:1', 1, '登录', 0, '认证失败', '', '2021-04-01 02:02:18', '2021-04-01 02:02:18', 0);
INSERT INTO `srent_log` VALUES (6, '匿名用户', '0:0:0:0:0:0:0:1', 1, '登录', 0, '认证失败', '', '2021-04-01 02:05:01', '2021-04-01 02:05:01', 0);
INSERT INTO `srent_log` VALUES (7, '匿名用户', '0:0:0:0:0:0:0:1', 1, '登录', 0, '认证失败', '', '2021-04-01 02:09:17', '2021-04-01 02:09:17', 0);
INSERT INTO `srent_log` VALUES (8, '匿名用户', '0:0:0:0:0:0:0:1', 1, '登录', 0, '认证失败', '', '2021-04-01 02:22:00', '2021-04-01 02:22:00', 0);
INSERT INTO `srent_log` VALUES (9, '匿名用户', '0:0:0:0:0:0:0:1', 1, '登录', 0, '用户帐号或密码不正确', '', '2021-04-01 02:43:21', '2021-04-01 02:43:21', 0);
INSERT INTO `srent_log` VALUES (10, 'maxwell', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', '2021-04-01 02:43:31', '2021-04-01 02:43:31', 0);
INSERT INTO `srent_log` VALUES (11, 'maxwell', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', '2021-04-01 02:43:40', '2021-04-01 02:43:40', 0);
INSERT INTO `srent_log` VALUES (12, '匿名用户', '0:0:0:0:0:0:0:1', 1, '登录', 0, '用户帐号或密码不正确', '', '2021-04-01 02:55:45', '2021-04-01 02:55:45', 0);
INSERT INTO `srent_log` VALUES (13, 'maxwell', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', '2021-04-01 02:56:15', '2021-04-01 02:56:15', 0);
COMMIT;

-- ----------------------------
-- Table structure for srent_message
-- ----------------------------
DROP TABLE IF EXISTS `srent_message`;
CREATE TABLE `srent_message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '买家ID',
  `post_user_id` int NOT NULL COMMENT '卖家ID',
  `goods_id` int NOT NULL COMMENT '商品ID',
  `group_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '房间ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of srent_message
-- ----------------------------
BEGIN;
INSERT INTO `srent_message` VALUES (87, 5, 11, 79, '11579', '2019-09-24 23:38:34', '2019-09-24 23:38:34', 0);
INSERT INTO `srent_message` VALUES (88, 11, 5, 79, '11579', '2019-09-24 23:41:12', '2019-09-24 23:41:12', 0);
INSERT INTO `srent_message` VALUES (89, 11, 5, 78, '11578', '2019-09-24 23:54:03', '2019-09-24 23:54:03', 0);
INSERT INTO `srent_message` VALUES (90, 5, 11, 78, '11578', '2019-09-24 23:54:03', '2019-09-24 23:54:03', 0);
INSERT INTO `srent_message` VALUES (91, 11, 3, 86, '11386', '2019-09-25 00:12:27', '2019-09-25 00:12:27', 0);
INSERT INTO `srent_message` VALUES (92, 3, 11, 86, '11386', '2019-09-25 00:12:27', '2019-09-25 00:12:27', 0);
COMMIT;

-- ----------------------------
-- Table structure for srent_order
-- ----------------------------
DROP TABLE IF EXISTS `srent_order`;
CREATE TABLE `srent_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户表的用户ID',
  `post_user_id` int NOT NULL COMMENT '发布人ID',
  `order_sn` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `order_status` smallint NOT NULL COMMENT '订单状态 \n1：等待见面交易\n2：等待发货\n3：已发货\n4：租期中\n5：归还物品\n6：交易成功\n7：取消订单',
  `type` int NOT NULL DEFAULT '0' COMMENT '交易方式 0见面交易 1快递',
  `consignee` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人名称',
  `mobile` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人手机号',
  `address` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货具体地址',
  `goods_price` decimal(10,2) NOT NULL COMMENT '商品总费用',
  `freight_price` decimal(10,2) NOT NULL COMMENT '配送费用',
  `order_price` decimal(10,2) NOT NULL COMMENT '订单费用， = goods_price + freight_price',
  `ship_sn` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '发货编号',
  `ship_channel` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '发货快递公司',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for srent_order_goods
-- ----------------------------
DROP TABLE IF EXISTS `srent_order_goods`;
CREATE TABLE `srent_order_goods` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL DEFAULT '0' COMMENT '订单表的订单ID',
  `goods_id` int NOT NULL DEFAULT '0' COMMENT '商品表的商品ID',
  `goods_name` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品名称',
  `goods_sn` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品编号',
  `days` smallint NOT NULL DEFAULT '0' COMMENT '商品租赁日期',
  `rent_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '每日租金',
  `deposit` decimal(10,2) NOT NULL COMMENT '押金',
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品货品图片或者商品图片',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `order_id` (`order_id`) USING BTREE,
  KEY `goods_id` (`goods_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for srent_permission
-- ----------------------------
DROP TABLE IF EXISTS `srent_permission`;
CREATE TABLE `srent_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int DEFAULT NULL COMMENT '角色ID',
  `permission` varchar(63) DEFAULT NULL COMMENT '权限',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限表';

-- ----------------------------
-- Records of srent_permission
-- ----------------------------
BEGIN;
INSERT INTO `srent_permission` VALUES (1, 1, '*', '2021-04-01 10:52:34', '2021-04-01 10:52:36', 0);
COMMIT;

-- ----------------------------
-- Table structure for srent_region
-- ----------------------------
DROP TABLE IF EXISTS `srent_region`;
CREATE TABLE `srent_region` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int NOT NULL DEFAULT '0' COMMENT '行政区域父ID，例如区县的pid指向市，市的pid指向省，省的pid则是0',
  `name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '行政区域名称',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '行政区域类型，如如1则是省， 如果是2则是市，如果是3则是区县',
  `code` int NOT NULL DEFAULT '0' COMMENT '行政区域编码',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `parent_id` (`pid`) USING BTREE,
  KEY `region_type` (`type`) USING BTREE,
  KEY `agency_id` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='行政区域表';

-- ----------------------------
-- Table structure for srent_role
-- ----------------------------
DROP TABLE IF EXISTS `srent_role`;
CREATE TABLE `srent_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(63) NOT NULL COMMENT '角色名称',
  `desc` varchar(1023) DEFAULT NULL COMMENT '角色描述',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

-- ----------------------------
-- Records of srent_role
-- ----------------------------
BEGIN;
INSERT INTO `srent_role` VALUES (1, '管理员', '所有权限', 1, '2021-04-01 10:46:49', '2021-04-01 10:46:52', 0);
COMMIT;

-- ----------------------------
-- Table structure for srent_search_history
-- ----------------------------
DROP TABLE IF EXISTS `srent_search_history`;
CREATE TABLE `srent_search_history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户表的用户ID',
  `keyword` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '搜索关键字',
  `from` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '搜索来源，如pc、wx、app',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='搜索历史表';

-- ----------------------------
-- Records of srent_search_history
-- ----------------------------
BEGIN;
INSERT INTO `srent_search_history` VALUES (1, 3, 'test', 'wx', '2019-08-27 15:30:54', '2019-08-27 15:30:54', 1);
INSERT INTO `srent_search_history` VALUES (2, 3, '荣耀', 'wx', '2019-08-27 15:31:43', '2019-08-27 15:31:43', 1);
INSERT INTO `srent_search_history` VALUES (3, 3, '荣耀', 'wx', '2019-08-27 15:32:53', '2019-08-27 15:32:53', 1);
INSERT INTO `srent_search_history` VALUES (4, 3, 'nihao', 'wx', '2019-08-27 15:42:10', '2019-08-27 15:42:10', 1);
INSERT INTO `srent_search_history` VALUES (5, 3, '华为', 'wx', '2019-08-27 17:26:29', '2019-08-27 17:26:29', 1);
INSERT INTO `srent_search_history` VALUES (6, 3, '华为', 'wx', '2019-08-27 17:29:24', '2019-08-27 17:29:24', 1);
INSERT INTO `srent_search_history` VALUES (7, 3, '华为', 'wx', '2019-08-27 17:32:48', '2019-08-27 17:32:48', 1);
INSERT INTO `srent_search_history` VALUES (8, 5, '手机', 'wx', '2019-09-08 14:29:19', '2019-09-08 14:29:19', 0);
INSERT INTO `srent_search_history` VALUES (9, 5, '手机', 'wx', '2019-09-08 14:29:22', '2019-09-08 14:29:22', 0);
INSERT INTO `srent_search_history` VALUES (10, 5, '手机', 'wx', '2019-09-08 14:29:23', '2019-09-08 14:29:23', 0);
INSERT INTO `srent_search_history` VALUES (11, 5, 'oppo', 'wx', '2019-09-08 14:29:37', '2019-09-08 14:29:37', 0);
INSERT INTO `srent_search_history` VALUES (12, 5, 'oppo', 'wx', '2019-09-08 14:29:41', '2019-09-08 14:29:41', 0);
INSERT INTO `srent_search_history` VALUES (13, 5, 'oppo', 'wx', '2019-09-08 14:29:42', '2019-09-08 14:29:42', 0);
INSERT INTO `srent_search_history` VALUES (14, 5, 'oppo', 'wx', '2019-09-08 14:29:43', '2019-09-08 14:29:43', 0);
INSERT INTO `srent_search_history` VALUES (15, 5, 'oppo', 'wx', '2019-09-08 14:29:44', '2019-09-08 14:29:44', 0);
INSERT INTO `srent_search_history` VALUES (16, 5, 'oppo', 'wx', '2019-09-08 14:29:45', '2019-09-08 14:29:45', 0);
INSERT INTO `srent_search_history` VALUES (17, 5, 'oppo', 'wx', '2019-09-08 14:29:47', '2019-09-08 14:29:47', 0);
INSERT INTO `srent_search_history` VALUES (18, 5, 'oppo', 'wx', '2019-09-08 14:29:49', '2019-09-08 14:29:49', 0);
INSERT INTO `srent_search_history` VALUES (19, 5, 'oppo', 'wx', '2019-09-08 14:29:50', '2019-09-08 14:29:50', 0);
INSERT INTO `srent_search_history` VALUES (20, 5, 'oppo', 'wx', '2019-09-08 14:29:52', '2019-09-08 14:29:52', 0);
INSERT INTO `srent_search_history` VALUES (21, 5, 'oppo', 'wx', '2019-09-08 14:29:53', '2019-09-08 14:29:53', 0);
INSERT INTO `srent_search_history` VALUES (22, 5, '索尼', 'wx', '2019-09-08 14:41:20', '2019-09-08 14:41:20', 0);
INSERT INTO `srent_search_history` VALUES (23, 5, '索尼', 'wx', '2019-09-08 14:41:31', '2019-09-08 14:41:31', 0);
INSERT INTO `srent_search_history` VALUES (24, 3, '华为', 'wx', '2019-09-08 20:35:15', '2019-09-08 20:35:15', 1);
INSERT INTO `srent_search_history` VALUES (25, 3, '华为', 'wx', '2019-09-08 20:35:23', '2019-09-08 20:35:23', 1);
INSERT INTO `srent_search_history` VALUES (26, 3, '华为', 'wx', '2019-09-08 20:35:24', '2019-09-08 20:35:24', 1);
INSERT INTO `srent_search_history` VALUES (27, 3, '华为', 'wx', '2019-09-08 20:35:25', '2019-09-08 20:35:25', 1);
INSERT INTO `srent_search_history` VALUES (28, 3, '华为', 'wx', '2019-09-08 20:35:27', '2019-09-08 20:35:27', 1);
INSERT INTO `srent_search_history` VALUES (29, 3, '索尼', 'wx', '2019-09-17 14:11:21', '2019-09-17 14:11:21', 0);
INSERT INTO `srent_search_history` VALUES (30, 4, '戴尔', 'wx', '2019-09-17 14:12:13', '2019-09-17 14:12:13', 0);
INSERT INTO `srent_search_history` VALUES (31, 4, '戴尔', 'wx', '2019-09-17 14:12:20', '2019-09-17 14:12:20', 0);
INSERT INTO `srent_search_history` VALUES (32, 4, '戴尔', 'wx', '2019-09-17 14:17:29', '2019-09-17 14:17:29', 0);
INSERT INTO `srent_search_history` VALUES (33, 3, 'Sony', 'wx', '2019-09-17 18:37:02', '2019-09-17 18:37:02', 0);
INSERT INTO `srent_search_history` VALUES (34, 3, '索尼', 'wx', '2019-09-17 18:37:59', '2019-09-17 18:37:59', 0);
INSERT INTO `srent_search_history` VALUES (35, 3, 'Sony', 'wx', '2019-09-18 10:27:23', '2019-09-18 10:27:23', 0);
INSERT INTO `srent_search_history` VALUES (36, 3, '索尼', 'wx', '2019-09-18 10:27:29', '2019-09-18 10:27:29', 0);
INSERT INTO `srent_search_history` VALUES (37, 3, 'Sony', 'wx', '2019-09-22 20:15:00', '2019-09-22 20:15:00', 0);
INSERT INTO `srent_search_history` VALUES (38, 5, '电脑', 'wx', '2019-09-24 23:20:34', '2019-09-24 23:20:34', 0);
INSERT INTO `srent_search_history` VALUES (39, 5, '手机', 'wx', '2019-09-24 23:20:38', '2019-09-24 23:20:38', 0);
INSERT INTO `srent_search_history` VALUES (40, 5, '戴尔', 'wx', '2019-09-24 23:21:06', '2019-09-24 23:21:06', 0);
INSERT INTO `srent_search_history` VALUES (41, 11, '联想', 'wx', '2019-09-24 23:36:59', '2019-09-24 23:36:59', 1);
INSERT INTO `srent_search_history` VALUES (42, 11, '联想', 'wx', '2019-09-24 23:37:02', '2019-09-24 23:37:02', 1);
INSERT INTO `srent_search_history` VALUES (43, 11, '联想', 'wx', '2019-09-24 23:37:03', '2019-09-24 23:37:03', 1);
INSERT INTO `srent_search_history` VALUES (44, 11, '联想', 'wx', '2019-09-24 23:37:03', '2019-09-24 23:37:03', 1);
INSERT INTO `srent_search_history` VALUES (45, 11, '联想', 'wx', '2019-09-24 23:37:03', '2019-09-24 23:37:03', 1);
INSERT INTO `srent_search_history` VALUES (46, 11, '联想', 'wx', '2019-09-24 23:37:05', '2019-09-24 23:37:05', 1);
INSERT INTO `srent_search_history` VALUES (47, 11, '尼康', 'wx', '2019-09-24 23:37:29', '2019-09-24 23:37:29', 0);
INSERT INTO `srent_search_history` VALUES (48, 11, '其他', 'wx', '2019-09-24 23:37:36', '2019-09-24 23:37:36', 0);
COMMIT;

-- ----------------------------
-- Table structure for srent_storage
-- ----------------------------
DROP TABLE IF EXISTS `srent_storage`;
CREATE TABLE `srent_storage` (
  `id` int NOT NULL AUTO_INCREMENT,
  `key` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件的唯一索引',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件名',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件类型',
  `size` int NOT NULL COMMENT '文件大小',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '文件访问链接',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=482 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='文件存储表';

-- ----------------------------
-- Records of srent_storage
-- ----------------------------
BEGIN;
INSERT INTO `srent_storage` VALUES (474, 'kmcs2i1dppy8jl3mhr4r.jpg', 'wx233751238b7f98f3.o6zAJs-BCbg8cuaF_JnyGXo1ZPp0.Dr1dCKgjqgYRca98948a0c66d80edc2bf275b0bd6298.jpg', 'image/jpeg', 160421, 'http://localhost:8081/wx/storage/fetch/kmcs2i1dppy8jl3mhr4r.jpg', '2019-09-29 19:52:55', '2019-09-29 19:52:55', 0);
INSERT INTO `srent_storage` VALUES (475, '53a0yaubu4cako4fj8ul.jpg', 'wx233751238b7f98f3.o6zAJs-BCbg8cuaF_JnyGXo1ZPp0.nMFUok2aTotNb8fca4fd5bd65f3e0e2dc524d796ab27.jpg', 'image/jpeg', 47993, 'http://localhost:8081/wx/storage/fetch/53a0yaubu4cako4fj8ul.jpg', '2019-09-29 19:52:55', '2019-09-29 19:52:55', 0);
INSERT INTO `srent_storage` VALUES (476, 'fwe594qn23hgcbf0fqdu.jpg', 'wx233751238b7f98f3.o6zAJs-BCbg8cuaF_JnyGXo1ZPp0.4EBS9QEEulKW95f2d1e3bec5fb02ee70e2fffc8b36d9.jpg', 'image/jpeg', 25875, 'http://localhost:8081/wx/storage/fetch/fwe594qn23hgcbf0fqdu.jpg', '2019-09-29 19:52:56', '2019-09-29 19:52:56', 0);
INSERT INTO `srent_storage` VALUES (477, 'vexvvmb39op4y1zustm0.jpg', 'wx233751238b7f98f3.o6zAJs-BCbg8cuaF_JnyGXo1ZPp0.4P3amIlYznG1c7b4a6e1c9151a4dfe12eb9edef30e24.jpg', 'image/jpeg', 16167, 'http://localhost:8081/wx/storage/fetch/vexvvmb39op4y1zustm0.jpg', '2019-09-29 19:52:56', '2019-09-29 19:52:56', 0);
INSERT INTO `srent_storage` VALUES (478, '3xjgmczl7zytnzn47cer.jpg', 'wx233751238b7f98f3.o6zAJs-BCbg8cuaF_JnyGXo1ZPp0.WcKLq6iG8hGdb8fca4fd5bd65f3e0e2dc524d796ab27.jpg', 'image/jpeg', 47993, 'http://localhost:8080/wx/storage/fetch/3xjgmczl7zytnzn47cer.jpg', '2019-09-29 19:54:56', '2019-09-29 19:54:56', 0);
INSERT INTO `srent_storage` VALUES (479, 'sn31xiewx9qorygtcsvf.jpg', 'wx233751238b7f98f3.o6zAJs-BCbg8cuaF_JnyGXo1ZPp0.iTMGrYrhIkeica98948a0c66d80edc2bf275b0bd6298.jpg', 'image/jpeg', 160421, 'http://localhost:8080/wx/storage/fetch/sn31xiewx9qorygtcsvf.jpg', '2019-09-29 19:54:56', '2019-09-29 19:54:56', 0);
INSERT INTO `srent_storage` VALUES (480, 'p5db1n3mmhxlv7ya4pda.jpg', 'wx233751238b7f98f3.o6zAJs-BCbg8cuaF_JnyGXo1ZPp0.vWuAR4LIlICT95f2d1e3bec5fb02ee70e2fffc8b36d9.jpg', 'image/jpeg', 25875, 'http://localhost:8080/wx/storage/fetch/p5db1n3mmhxlv7ya4pda.jpg', '2019-09-29 19:54:56', '2019-09-29 19:54:56', 0);
INSERT INTO `srent_storage` VALUES (481, 'oudkoapv7wec8jfokn3h.jpg', 'wx233751238b7f98f3.o6zAJs-BCbg8cuaF_JnyGXo1ZPp0.U2eIznt9IFhyc7b4a6e1c9151a4dfe12eb9edef30e24.jpg', 'image/jpeg', 16167, 'http://localhost:8080/wx/storage/fetch/oudkoapv7wec8jfokn3h.jpg', '2019-09-29 19:54:56', '2019-09-29 19:54:56', 0);
COMMIT;

-- ----------------------------
-- Table structure for srent_system
-- ----------------------------
DROP TABLE IF EXISTS `srent_system`;
CREATE TABLE `srent_system` (
  `id` int NOT NULL AUTO_INCREMENT,
  `key_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '系统配置名',
  `key_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '系统配置值',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='系统配置表';

-- ----------------------------
-- Records of srent_system
-- ----------------------------
BEGIN;
INSERT INTO `srent_system` VALUES (2, 'test-system-key', 'test-system-value', NULL, NULL, 0);
INSERT INTO `srent_system` VALUES (3, 'test-system-key', 'test-system-value', NULL, NULL, 0);
INSERT INTO `srent_system` VALUES (4, 'test-system-key', 'test-system-value', NULL, NULL, 0);
INSERT INTO `srent_system` VALUES (5, 'test-system-key', 'test-system-value', NULL, NULL, 0);
INSERT INTO `srent_system` VALUES (6, 'test-system-key', 'test-system-value', NULL, NULL, 0);
INSERT INTO `srent_system` VALUES (8, 'litemall_order_unpaid', '30', '2019-08-13 16:51:13', '2019-08-13 16:51:13', 0);
INSERT INTO `srent_system` VALUES (9, 'litemall_wx_index_new', '6', '2019-08-13 16:51:13', '2019-08-13 16:51:13', 0);
INSERT INTO `srent_system` VALUES (10, 'litemall_order_unconfirm', '7', '2019-08-13 16:51:13', '2019-08-13 16:51:13', 0);
INSERT INTO `srent_system` VALUES (11, 'litemall_wx_share', 'false', '2019-08-13 16:51:13', '2019-08-13 16:51:13', 0);
INSERT INTO `srent_system` VALUES (12, 'litemall_express_freight_min', '88', '2019-08-13 16:51:13', '2019-08-13 16:51:13', 0);
INSERT INTO `srent_system` VALUES (13, 'litemall_mall_name', 'srent', '2019-08-13 16:51:13', '2019-08-13 16:51:13', 0);
INSERT INTO `srent_system` VALUES (14, 'litemall_express_freight_value', '8', '2019-08-13 16:51:13', '2019-08-13 16:51:13', 0);
INSERT INTO `srent_system` VALUES (15, 'litemall_mall_qq', '738696120', '2019-08-13 16:51:13', '2019-08-13 16:51:13', 0);
INSERT INTO `srent_system` VALUES (16, 'litemall_wx_index_hot', '6', '2019-08-13 16:51:13', '2019-08-13 16:51:13', 0);
INSERT INTO `srent_system` VALUES (17, 'litemall_order_comment', '7', '2019-08-13 16:51:13', '2019-08-13 16:51:13', 0);
INSERT INTO `srent_system` VALUES (18, 'litemall_wx_catlog_goods', '4', '2019-08-13 16:51:13', '2019-08-13 16:51:13', 0);
INSERT INTO `srent_system` VALUES (19, 'litemall_mall_phone', '021-xxxx-xxxx', '2019-08-13 16:51:13', '2019-08-13 16:51:13', 0);
INSERT INTO `srent_system` VALUES (20, 'litemall_wx_catlog_list', '4', '2019-08-13 16:51:13', '2019-08-13 16:51:13', 0);
INSERT INTO `srent_system` VALUES (21, 'litemall_mall_address', '上海', '2019-08-13 16:51:13', '2019-08-13 16:51:13', 0);
INSERT INTO `srent_system` VALUES (22, 'litemall_wx_index_brand', '4', '2019-08-13 16:51:13', '2019-08-13 16:51:13', 0);
INSERT INTO `srent_system` VALUES (23, 'litemall_wx_index_topic', '4', '2019-08-13 16:51:13', '2019-08-13 16:51:13', 0);
INSERT INTO `srent_system` VALUES (24, 'srent_wx_index_all', '10', NULL, NULL, 0);
INSERT INTO `srent_system` VALUES (25, 'srent_wx_index_new', '6', '2021-04-01 00:43:21', '2021-04-01 00:43:21', 0);
INSERT INTO `srent_system` VALUES (26, 'srent_order_unconfirm', '7', '2021-04-01 00:43:21', '2021-04-01 00:43:21', 0);
INSERT INTO `srent_system` VALUES (27, 'srent_order_unpaid', '30', '2021-04-01 00:43:21', '2021-04-01 00:43:21', 0);
INSERT INTO `srent_system` VALUES (28, 'srent_mall_phone', '021-xxxx-xxxx', '2021-04-01 00:43:21', '2021-04-01 00:43:21', 0);
INSERT INTO `srent_system` VALUES (29, 'srent_express_freight_min', '88', '2021-04-01 00:43:21', '2021-04-01 00:43:21', 0);
INSERT INTO `srent_system` VALUES (30, 'srent_wx_index_brand', '4', '2021-04-01 00:43:21', '2021-04-01 00:43:21', 0);
INSERT INTO `srent_system` VALUES (31, 'srent_order_comment', '7', '2021-04-01 00:43:21', '2021-04-01 00:43:21', 0);
INSERT INTO `srent_system` VALUES (32, 'srent_wx_catlog_goods', '4', '2021-04-01 00:43:21', '2021-04-01 00:43:21', 0);
INSERT INTO `srent_system` VALUES (33, 'srent_wx_share', 'false', '2021-04-01 00:43:21', '2021-04-01 00:43:21', 0);
INSERT INTO `srent_system` VALUES (34, 'srent_mall_name', 'srent', '2021-04-01 00:43:21', '2021-04-01 00:43:21', 0);
INSERT INTO `srent_system` VALUES (35, 'srent_mall_address', '上海', '2021-04-01 00:43:21', '2021-04-01 00:43:21', 0);
INSERT INTO `srent_system` VALUES (36, 'srent_wx_index_topic', '4', '2021-04-01 00:43:21', '2021-04-01 00:43:21', 0);
INSERT INTO `srent_system` VALUES (37, 'srent_wx_index_hot', '6', '2021-04-01 00:43:21', '2021-04-01 00:43:21', 0);
INSERT INTO `srent_system` VALUES (38, 'srent_wx_catlog_list', '4', '2021-04-01 00:43:21', '2021-04-01 00:43:21', 0);
INSERT INTO `srent_system` VALUES (39, 'srent_mall_qq', '738696120', '2021-04-01 00:43:21', '2021-04-01 00:43:21', 0);
INSERT INTO `srent_system` VALUES (40, 'srent_express_freight_value', '8', '2021-04-01 00:43:21', '2021-04-01 00:43:21', 0);
COMMIT;

-- ----------------------------
-- Table structure for srent_topic
-- ----------------------------
DROP TABLE IF EXISTS `srent_topic`;
CREATE TABLE `srent_topic` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '''' COMMENT '专题标题',
  `subtitle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '''' COMMENT '专题子标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '专题内容，富文本格式',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '专题相关商品最低价',
  `read_count` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1k' COMMENT '专题阅读量',
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '专题图片',
  `sort_order` int DEFAULT '100' COMMENT '排序',
  `goods` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '专题相关商品，采用JSON数组格式',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `topic_id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='专题表';

-- ----------------------------
-- Table structure for srent_user
-- ----------------------------
DROP TABLE IF EXISTS `srent_user`;
CREATE TABLE `srent_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名称',
  `password` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户密码',
  `gender` tinyint NOT NULL DEFAULT '0' COMMENT '性别：0 未知， 1男， 1 女',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近一次登录时间',
  `last_login_ip` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '最近一次登录IP地址',
  `nickname` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户昵称或网络名称',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户手机号码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户头像图片',
  `weixin_openid` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '微信登录openid',
  `session_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '微信登录会话KEY',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '0 可用, 1 禁用, 2 注销',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of srent_user
-- ----------------------------
BEGIN;
INSERT INTO `srent_user` VALUES (1, '测试1', '123456', 0, NULL, '', '测试1', '', 'http://yanxuan.nosdn.127.net/5dc0a00f2b5b066be3b01bd36952a5f7.jpg', 'test1', '', 0, NULL, NULL, 0);
INSERT INTO `srent_user` VALUES (2, '测试2', '123456', 0, NULL, '', '测试2', '', 'http://yanxuan.nosdn.127.net/5dc0a00f2b5b066be3b01bd36952a5f7.jpg', 'test2', '', 0, NULL, NULL, 0);
INSERT INTO `srent_user` VALUES (3, 'oWIXj5FwMqDCqEHGrnOToE6TSJ4g', 'oWIXj5FwMqDCqEHGrnOToE6TSJ4g', 1, '2019-09-29 19:53:34', '0:0:0:0:0:0:0:1', 'maxwell', '', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ0wMgiaCzgOAloFR8J3ia1WBZWxwbwW0ud0cZq0BSfZcx3xslmTzVObDKA2BQUrBgDwwBoL8CC572g/132', 'oWIXj5FwMqDCqEHGrnOToE6TSJ4g', 'ecd3Rbrm0V5hyBj1T2jWxQ==', 0, '2019-08-16 22:04:05', '2019-09-29 19:53:34', 0);
INSERT INTO `srent_user` VALUES (4, 'test123', '$2a$10$sLP75SnBfFetk.6.o6G91eQKEXyABi/O9CX6FZvtTea2i3LgDqeLS', 0, '2019-08-16 23:22:23', '192.168.1.112', 'test123', '15923122184', 'http://pxc4naypd.bkt.clouddn.com/avatar_3.jpg', 'test4', '', 0, '2019-08-16 23:22:23', '2019-09-17 08:53:38', 0);
INSERT INTO `srent_user` VALUES (5, '123456', '$2a$10$giXFowFKeGGmlaIWin6U5OtFwBJfu3cEPCz1HjA63Ev0tv6xpFeFC', 0, '2019-08-31 15:39:34', '192.168.10.107', '123456', '15923022186', 'http://pxc4naypd.bkt.clouddn.com/avatar_4.jpg', 'test5', '', 0, '2019-08-31 15:39:34', '2019-08-31 15:39:34', 0);
INSERT INTO `srent_user` VALUES (6, 'maxwell', '$2a$10$7CGMbt4WnGCPiQJQVIRfP.SF07K/NJAnkT1Ti3ZX1G1cBwrBCvDti', 0, '2019-09-04 08:28:29', '0:0:0:0:0:0:0:1', 'maxwells', '15923022184', 'http://yanxuan.nosdn.127.net/5dc0a00f2b5b066be3b01bd36952a5f7.jpg?imageView&quality=90&thumbnail=64x64', 'test6', '', 0, '2019-09-04 08:28:29', '2019-09-04 08:28:29', 0);
INSERT INTO `srent_user` VALUES (7, 'cmking', '$2a$10$vbdg/RGHDnNMZONF9H60KerPZ0R5bhIb/lSmNWj8B.sg3O5tXETt6', 0, '2019-09-04 17:32:12', '192.168.10.150', 'cmking', '18996887682', 'http://yanxuan.nosdn.127.net/5dc0a00f2b5b066be3b01bd36952a5f7.jpg?imageView&quality=90&thumbnail=64x64', 'test7', '', 0, '2019-09-04 17:32:12', '2019-09-04 17:32:12', 0);
INSERT INTO `srent_user` VALUES (8, 'zhongpeng1998', '$2a$10$d0bYGuX6O64DyNK7KAUxI.RNSNfqMsqDi524yT6NgyecbXVeQklnq', 0, '2019-09-08 23:07:19', '192.168.10.107', 'zhongpeng1998', '15922322184', 'http://yanxuan.nosdn.127.net/5dc0a00f2b5b066be3b01bd36952a5f7.jpg?imageView&quality=90&thumbnail=64x64', 'test8', '', 0, '2019-09-08 23:07:19', '2019-09-08 23:07:19', 0);
INSERT INTO `srent_user` VALUES (9, 'hahaha', '$2a$10$mdyFiSwIuu7z.dedPw1mf.3q1M/f.2RiYT1USh5IMsoNZfh.AQALG', 0, '2019-09-17 08:56:35', '0:0:0:0:0:0:0:1', 'hahaha', '15322022184', 'http://yanxuan.nosdn.127.net/5dc0a00f2b5b066be3b01bd36952a5f7.jpg?imageView&quality=90&thumbnail=64x64', 'hahaha', '', 0, '2019-09-17 08:56:35', '2019-09-17 08:56:35', 0);
INSERT INTO `srent_user` VALUES (10, 'oWIXj5EQiRhm3l-brbtKPZJ89xnw', 'oWIXj5EQiRhm3l-brbtKPZJ89xnw', 1, '2019-09-24 19:34:23', '192.168.10.149', '牧犬娃子', '', 'https://wx.qlogo.cn/mmopen/vi_32/ia1QiaCs36ZE5rtbuhIPia5akUVqZo6bg8Yu4ZqL6ACv7Ln7czvH9LFiaaqrXqaD0lIAHUuvAzGLkbLU7vFl0ibFPVw/132', 'oWIXj5EQiRhm3l-brbtKPZJ89xnw', 'Nrqe/htf15QV+SRFhLGVXg==', 0, '2019-09-17 18:39:48', '2019-09-24 19:34:23', 0);
INSERT INTO `srent_user` VALUES (11, 'values', '$2a$10$KPO8V1E/3HDWlzI5sWskDu.WtDfqnF4i3AVzvBek4AwfIM21aMBn6', 0, '2019-09-24 23:36:26', '192.168.10.118', 'values', '15923022188', 'http://yanxuan.nosdn.127.net/5dc0a00f2b5b066be3b01bd36952a5f7.jpg?imageView&quality=90&thumbnail=64x64', 'values', '', 0, '2019-09-24 23:36:26', '2019-09-24 23:36:26', 0);
COMMIT;

-- ----------------------------
-- Table structure for srent_user_formid
-- ----------------------------
DROP TABLE IF EXISTS `srent_user_formid`;
CREATE TABLE `srent_user_formid` (
  `id` int NOT NULL AUTO_INCREMENT,
  `formId` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '缓存的FormId',
  `isprepay` tinyint(1) NOT NULL COMMENT '是FormId还是prepayId',
  `useAmount` int NOT NULL COMMENT '可用次数，fromId为1，prepay为3，用1次减1',
  `expire_time` datetime NOT NULL COMMENT '过期时间，腾讯规定为7天',
  `openId` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '微信登录openid',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
