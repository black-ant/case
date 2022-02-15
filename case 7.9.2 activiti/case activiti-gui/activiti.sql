/*
 Navicat Premium Data Transfer

 Source Server         : 5.7
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 172.16.26.113:3305
 Source Schema         : activiti

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 23/04/2021 11:54:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for leaveapply
-- ----------------------------
DROP TABLE IF EXISTS `leaveapply`;
CREATE TABLE `leaveapply`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `process_instance_id` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `end_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `leave_type` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reason` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `apply_time` datetime(0) NULL DEFAULT NULL,
  `reality_start_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reality_end_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of leaveapply
-- ----------------------------

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `permissionname` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`pid`) USING BTREE,
  UNIQUE INDEX `permissionname_UNIQUE`(`permissionname`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (2, '人事审批');
INSERT INTO `permission` VALUES (9, '出纳付款');
INSERT INTO `permission` VALUES (8, '总经理审批');
INSERT INTO `permission` VALUES (22, '收货确认');
INSERT INTO `permission` VALUES (17, '调整申请');
INSERT INTO `permission` VALUES (21, '调整采购申请');
INSERT INTO `permission` VALUES (3, '财务审批');
INSERT INTO `permission` VALUES (1, '部门领导审批');
INSERT INTO `permission` VALUES (18, '采购经理审批');
INSERT INTO `permission` VALUES (16, '销假');

-- ----------------------------
-- Table structure for purchase
-- ----------------------------
DROP TABLE IF EXISTS `purchase`;
CREATE TABLE `purchase`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `itemlist` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `total` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `applytime` datetime(0) NULL DEFAULT NULL,
  `applyer` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `processinstanceid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of purchase
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`rid`) USING BTREE,
  UNIQUE INDEX `rolename_UNIQUE`(`rolename`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (18, '普通用户');
INSERT INTO `role` VALUES (17, '管理员');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `rpid` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) NOT NULL,
  `permissionid` int(11) NOT NULL,
  PRIMARY KEY (`rpid`) USING BTREE,
  INDEX `a_idx`(`roleid`) USING BTREE,
  INDEX `b_idx`(`permissionid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (49, 17, 2);
INSERT INTO `role_permission` VALUES (50, 17, 9);
INSERT INTO `role_permission` VALUES (51, 17, 8);
INSERT INTO `role_permission` VALUES (52, 17, 22);
INSERT INTO `role_permission` VALUES (53, 17, 17);
INSERT INTO `role_permission` VALUES (54, 17, 21);
INSERT INTO `role_permission` VALUES (55, 17, 3);
INSERT INTO `role_permission` VALUES (56, 17, 1);
INSERT INTO `role_permission` VALUES (57, 17, 18);
INSERT INTO `role_permission` VALUES (58, 17, 16);
INSERT INTO `role_permission` VALUES (64, 18, 9);
INSERT INTO `role_permission` VALUES (65, 18, 22);
INSERT INTO `role_permission` VALUES (66, 18, 17);
INSERT INTO `role_permission` VALUES (67, 18, 21);
INSERT INTO `role_permission` VALUES (68, 18, 16);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `tel` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `username_UNIQUE`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (31, 'xiaomi', '1234', '110', 20);
INSERT INTO `user` VALUES (33, 'jon', '1234', '123', 23);
INSERT INTO `user` VALUES (34, 'xiaocai', '1234', '111', 32);
INSERT INTO `user` VALUES (35, 'WANG', '1234', '222', 33);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `urid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `roleid` int(11) NOT NULL,
  PRIMARY KEY (`urid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (93, 31, 17);
INSERT INTO `user_role` VALUES (94, 33, 18);
INSERT INTO `user_role` VALUES (96, 35, 18);
INSERT INTO `user_role` VALUES (97, 34, 17);

SET FOREIGN_KEY_CHECKS = 1;
