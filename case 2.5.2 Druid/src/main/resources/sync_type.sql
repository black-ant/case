/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : expengine

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 21/01/2020 22:36:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sync_type
-- ----------------------------
DROP TABLE IF EXISTS `sync_type`;
CREATE TABLE `sync_type`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加载类',
  `type_policy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '策略类型',
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型名',
  `supplier_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商类型',
  `supplier_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商名称',
  `data_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理类型',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code`(`type_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sync_type
-- ----------------------------
INSERT INTO `sync_type` VALUES ('1', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '1', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('11', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '1', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('12', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '1', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('13', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '1', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('15', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '1', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('2', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '2', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('21', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '2', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('22', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '2', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('23', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '2', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('25', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '2', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('3', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '3', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('31', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '3', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('32', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '3', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('33', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '3', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('35', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '3', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('4', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '3', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('41', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '3', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('42', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '3', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('43', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '3', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');
INSERT INTO `sync_type` VALUES ('45', NULL, 'com.gang.ext.sdk.workwechat.logic.OrgLogic', '3', 'WORK_WECHAT_ORG', 'WORK_WECHAT', 'WORK_WECHAT', 'ORG');

SET FOREIGN_KEY_CHECKS = 1;
