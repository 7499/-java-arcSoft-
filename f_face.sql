/*
 Navicat Premium Data Transfer

 Source Server         : aliyun_mysql8
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : rm-bp1ij0mf385bs0ml2vo.mysql.rds.aliyuncs.com:3306
 Source Schema         : java-face

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 05/02/2023 12:57:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for f_face
-- ----------------------------
DROP TABLE IF EXISTS `f_face`;
CREATE TABLE `f_face`  (
  `face_id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `face_info` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`face_id`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
