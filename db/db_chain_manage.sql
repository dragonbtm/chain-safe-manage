/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50722
Source Host           : 192.168.5.144:3306
Source Database       : db_chain_manage

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-01-11 11:03:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for backup_file
-- ----------------------------
DROP TABLE IF EXISTS `backup_file`;
CREATE TABLE `backup_file` (
  `id` varchar(200) NOT NULL COMMENT '主键',
  `filepath` varchar(500) DEFAULT NULL COMMENT '文件上传路径',
  `filename` varchar(30) DEFAULT NULL COMMENT '文件名字',
  `upload_ip` varchar(45) DEFAULT NULL COMMENT '上传ip',
  `localstatus` int(2) DEFAULT NULL COMMENT '状态0(成功,1失败)',
  `remotestatus` int(2) DEFAULT NULL COMMENT '状态0(成功,1失败)',
  `serviceName` varchar(100) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '备份时间',
  `remark2` varchar(100) DEFAULT NULL,
  `remark3` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of backup_file
-- ----------------------------
INSERT INTO `backup_file` VALUES ('21cbdeec-b7a2-418a-85e6-10bafcbf0f55', null, 'edu.sqlite', '0:0:0:0:0:0:0:1', '0', null, 'witness1', '2018-12-10 18:57:48', null, null);
INSERT INTO `backup_file` VALUES ('3aab38af-de76-4ecf-b762-c3810ad36a28', '~/.config/edu-hub/2018/11/30/hub2/', 'edu.sqlite', '122.114.23.71', '0', '0', '1', '2018-12-10 18:21:45', null, null);
INSERT INTO `backup_file` VALUES ('64ffa8f2-32df-47af-bb49-b76035e6ba4c', '~/.config/edu-hub/2018/11/30/hub2/', 'edu.sqlite', '122.114.23.71', '0', '0', '1', '2018-12-10 18:21:47', null, null);
INSERT INTO `backup_file` VALUES ('92077a43-9298-4f94-8444-9cd60e4797f1', null, 'edu.sqlite', '0:0:0:0:0:0:0:1', '0', null, 'hub1', '2018-12-13 14:18:58', null, null);
INSERT INTO `backup_file` VALUES ('a83f615d-9d87-40f4-818f-b8d177c80f0e', null, 'edu.sqlite', '0:0:0:0:0:0:0:1', '0', null, 'witness1', '2018-12-10 18:57:55', null, null);
INSERT INTO `backup_file` VALUES ('df31f4f0-56ae-450d-9d75-5eef7ecefa45', '~/.config/edu-hub/2018/11/30/hub2/', 'edu.sqlite', '122.114.23.71', '0', '0', '1', '2018-12-10 18:21:49', null, null);

-- ----------------------------
-- Table structure for backup_log
-- ----------------------------
DROP TABLE IF EXISTS `backup_log`;
CREATE TABLE `backup_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_ip` varchar(45) DEFAULT NULL COMMENT '操作ip',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(300) DEFAULT NULL COMMENT '操作命令',
  `params` varchar(200) DEFAULT NULL COMMENT '参数',
  `error` varchar(300) DEFAULT NULL COMMENT '错误信息',
  `backupid` varchar(200) DEFAULT NULL COMMENT '备份id',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '时间',
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of backup_log
-- ----------------------------
INSERT INTO `backup_log` VALUES ('42', '122.114.23.70', '本地备份', 'mkdir -p ~/.config/edu-hub/2018/11/30/hub2/&&cp ~/.config/edu-hub/edu.sqlite ~/.config/edu-hub/2018/11/30/hub2/edu.sqlite', '[]', null, '64ffa8f2-32df-47af-bb49-b76035e6ba4c', '2018-11-30 15:39:45', null);
INSERT INTO `backup_log` VALUES ('43', '122.114.23.70', '远程备份', 'ssh gainet@122.114.23.71 mkdir -p ~/.config/edu-hub/2018/11/30/hub2/', '[]', null, '64ffa8f2-32df-47af-bb49-b76035e6ba4c', '2018-11-30 15:40:31', null);
INSERT INTO `backup_log` VALUES ('44', '122.114.23.70', '远程备份', 'scp -r ~/.config/edu-hub/edu.sqlite gainet@122.114.23.71:~/.config/edu-hub/2018/11/30/hub2/', '[]', null, '64ffa8f2-32df-47af-bb49-b76035e6ba4c', '2018-11-30 15:41:19', null);
INSERT INTO `backup_log` VALUES ('45', '122.114.23.70', '本地备份', 'mkdir -p ~/.config/edu-hub/2018/11/30/hub2/&&cp ~/.config/edu-hub/edu.sqlite ~/.config/edu-hub/2018/11/30/hub2/edu.sqlite', '[]', null, 'df31f4f0-56ae-450d-9d75-5eef7ecefa45', '2018-11-30 15:42:07', null);
INSERT INTO `backup_log` VALUES ('46', '122.114.23.70', '远程备份', 'ssh gainet@122.114.23.71 mkdir -p ~/.config/edu-hub/2018/11/30/hub2/', '[]', null, 'df31f4f0-56ae-450d-9d75-5eef7ecefa45', '2018-11-30 15:42:09', null);
INSERT INTO `backup_log` VALUES ('47', '122.114.23.70', '远程备份', 'scp -r ~/.config/edu-hub/edu.sqlite gainet@122.114.23.71:~/.config/edu-hub/2018/11/30/hub2/', '[]', null, 'df31f4f0-56ae-450d-9d75-5eef7ecefa45', '2018-11-30 15:42:41', null);
INSERT INTO `backup_log` VALUES ('48', '122.114.23.70', '本地备份', 'mkdir -p ~/.config/edu-hub/2018/11/30/hub2/&&cp ~/.config/edu-hub/edu.sqlite ~/.config/edu-hub/2018/11/30/hub2/edu.sqlite', '[]', null, '3aab38af-de76-4ecf-b762-c3810ad36a28', '2018-11-30 15:46:22', null);
INSERT INTO `backup_log` VALUES ('49', '122.114.23.70', '远程备份', 'ssh gainet@122.114.23.71 mkdir -p ~/.config/edu-hub/2018/11/30/hub2/', '[]', null, '3aab38af-de76-4ecf-b762-c3810ad36a28', '2018-11-30 15:46:23', null);
INSERT INTO `backup_log` VALUES ('50', '122.114.23.70', '远程备份', 'scp -r ~/.config/edu-hub/edu.sqlite gainet@122.114.23.71:~/.config/edu-hub/2018/11/30/hub2/', '[]', null, '3aab38af-de76-4ecf-b762-c3810ad36a28', '2018-11-30 15:46:57', null);

-- ----------------------------
-- Table structure for backup_manage
-- ----------------------------
DROP TABLE IF EXISTS `backup_manage`;
CREATE TABLE `backup_manage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ip` varchar(45) DEFAULT NULL COMMENT 'ip地址',
  `port` int(6) DEFAULT NULL,
  `user` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL COMMENT '密码',
  `pd_file` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '秘钥文件',
  `is_backup` int(1) DEFAULT NULL COMMENT '是否需要备份0不需要,1需要',
  `service_type` varchar(100) DEFAULT NULL COMMENT 'chain服务项目名字',
  `status` int(1) DEFAULT '1' COMMENT '心跳状态(0正常,1异常)',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `service_name` varchar(100) DEFAULT NULL COMMENT 'chain服务名称(servicename)',
  `remark3` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of backup_manage
-- ----------------------------
INSERT INTO `backup_manage` VALUES ('2', '122.114.23.71', '10022', 'user', null, 'D:\\idea\\java\\edu\\edu-manage\\src\\main\\resources\\sshkey\\zz.pem', '1', 'hub', '1', '2018-12-06 14:26:03', '2018-11-26 20:07:26', 'hub1', '');
INSERT INTO `backup_manage` VALUES ('7', '122.114.23.72', '10022', 'user', null, 'D:\\idea\\java\\edu\\edu-manage\\src\\main\\resources\\sshkey\\zz.pem', '0', 'backup', null, '2018-12-06 14:26:04', '2018-11-26 20:07:37', 'backup', null);
INSERT INTO `backup_manage` VALUES ('8', '122.114.23.68', '10022', 'user', null, 'D:\\idea\\java\\edu\\edu-manage\\src\\main\\resources\\sshkey\\zz.pem', '1', 'witness', '1', '2018-12-06 14:26:06', '2018-12-05 17:37:03', 'witness1', null);
INSERT INTO `backup_manage` VALUES ('9', '122.114.23.71', '10022', 'user', null, 'D:\\idea\\java\\edu\\edu-manage\\src\\main\\resources\\sshkey\\zz.pem', '1', 'headless', '1', '2018-12-06 14:26:07', '2018-12-05 17:48:17', 'headless1', null);
INSERT INTO `backup_manage` VALUES ('10', '122.114.23.69', '10022', 'user', null, 'D:\\idea\\java\\edu\\edu-manage\\src\\main\\resources\\sshkey\\zz.pem', '1', 'hub', '1', '2018-12-06 14:27:37', '2018-12-06 14:27:34', 'hub2', null);
