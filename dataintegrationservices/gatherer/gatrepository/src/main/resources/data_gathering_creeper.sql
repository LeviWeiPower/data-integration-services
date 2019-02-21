/*
Navicat MySQL Data Transfer

Source Server         : 39.108.70.66
Source Server Version : 50717
Source Host           : 39.108.70.66:3306
Source Database       : data_gathering_creeper

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-02-21 15:39:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for common_rule
-- ----------------------------
DROP TABLE IF EXISTS `common_rule`;
CREATE TABLE `common_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sequence` varchar(64) NOT NULL COMMENT '采集者的唯一序列号。',
  `rule` text COMMENT '公共标签位置json',
  `is_delete` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N非删除 Y删除',
  `creator` varchar(20) NOT NULL COMMENT '创建者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(20) NOT NULL COMMENT '更新者',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='公共父级规则';

-- ----------------------------
-- Table structure for content_data
-- ----------------------------
DROP TABLE IF EXISTS `content_data`;
CREATE TABLE `content_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sequence` varchar(64) NOT NULL COMMENT '采集者的唯一序列号',
  `crawler_name` varchar(64) DEFAULT NULL COMMENT '爬虫人物名',
  `data_classify` varchar(30) DEFAULT NULL COMMENT '数据分类（避免数据混乱）',
  `content_classify` varchar(68) DEFAULT NULL COMMENT '内容类别',
  `content_url` varchar(300) NOT NULL COMMENT '内容地址',
  `content` text COMMENT '内容数据',
  `data_uuid` varchar(38) DEFAULT NULL COMMENT '数据uuid',
  `data_chain_uuid` varchar(38) DEFAULT NULL COMMENT '数据链uuid，有关联的数据',
  `is_delete` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N非删除 Y删除',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator` varchar(20) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(20) NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `sequence_index` (`sequence`,`data_chain_uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1619 DEFAULT CHARSET=utf8 COMMENT='内容数据';

-- ----------------------------
-- Table structure for cookie
-- ----------------------------
DROP TABLE IF EXISTS `cookie`;
CREATE TABLE `cookie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sequence` varchar(64) NOT NULL COMMENT '采集者的唯一序列号',
  `cookie_key` varchar(64) NOT NULL COMMENT '键',
  `cookie_value` varchar(128) DEFAULT NULL COMMENT '值',
  `is_delete` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N非删除 Y删除',
  `creator` varchar(20) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(20) NOT NULL COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='cookie信息表';

-- ----------------------------
-- Table structure for crawler
-- ----------------------------
DROP TABLE IF EXISTS `crawler`;
CREATE TABLE `crawler` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sequence` varchar(64) NOT NULL COMMENT '采集者的唯一序列号',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `url` varchar(300) NOT NULL COMMENT '地址',
  `main_url` varchar(100) DEFAULT NULL COMMENT '域名',
  `data_classify` varchar(30) DEFAULT NULL COMMENT '数据分类（避免数据混乱）',
  `hierarchy` int(11) NOT NULL DEFAULT '0' COMMENT '层级，从主链接页开始算，从0开始，没有填默认为0',
  `method` varchar(12) DEFAULT 'get' COMMENT '请求方式，没有则为get',
  `coding` varchar(8) DEFAULT 'UTF-8' COMMENT '编码，默认utf8',
  `request_num` int(11) DEFAULT '60' COMMENT '每分钟请求次数，默认为60次',
  `is_start` char(1) DEFAULT 'N' COMMENT '是否开启运行中，''N'' 否，''Y''是，默认为''N''',
  `is_delete` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N非删除 Y删除',
  `creator` varchar(20) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(20) NOT NULL COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='基础规则表';

-- ----------------------------
-- Table structure for executed_url
-- ----------------------------
DROP TABLE IF EXISTS `executed_url`;
CREATE TABLE `executed_url` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sequence` varchar(64) NOT NULL COMMENT '采集者的唯一序列号',
  `executed_url` text NOT NULL COMMENT '执行过的URL',
  `method` varchar(12) DEFAULT 'get' COMMENT '请求方式',
  `page_salting_val` varchar(68) DEFAULT NULL COMMENT '页面盐值编码，用于识别页面数据是否改变过，如果改变了则重新抓取数据。',
  `last_update_time` varchar(68) DEFAULT NULL COMMENT '数据包中的最后更新时间',
  `is_delete` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N非删除 Y删除',
  `creator` varchar(20) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(20) NOT NULL COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `def_index` (`sequence`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='执行过的URL';

-- ----------------------------
-- Table structure for extract_rule
-- ----------------------------
DROP TABLE IF EXISTS `extract_rule`;
CREATE TABLE `extract_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sequence` varchar(64) NOT NULL COMMENT '采集者的唯一序列号',
  `page_classify` varchar(68) DEFAULT NULL COMMENT '页面类别',
  `content_classify` varchar(68) DEFAULT NULL COMMENT '内容类别',
  `rule` text COMMENT '内容位置，以JSON进行存储',
  `is_delete` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N非删除 Y删除',
  `creator` varchar(20) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(20) NOT NULL COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='数据提取规则';

-- ----------------------------
-- Table structure for paging_index
-- ----------------------------
DROP TABLE IF EXISTS `paging_index`;
CREATE TABLE `paging_index` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sequence` varchar(64) NOT NULL COMMENT '采集者的唯一序列号',
  `rule` text COMMENT '位置规则，以JSON进行存储',
  `is_delete` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N非删除 Y删除',
  `creator` varchar(20) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(20) NOT NULL COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='翻页位置';

-- ----------------------------
-- Table structure for request_header
-- ----------------------------
DROP TABLE IF EXISTS `request_header`;
CREATE TABLE `request_header` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sequence` varchar(64) NOT NULL COMMENT '采集者的唯一序列号',
  `header_key` varchar(64) NOT NULL COMMENT '请求包头的key',
  `header_value` varchar(128) NOT NULL COMMENT '请求包头value',
  `is_delete` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N非删除 Y删除',
  `creator` varchar(20) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(20) NOT NULL COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请求包头信息表';

-- ----------------------------
-- Table structure for request_param
-- ----------------------------
DROP TABLE IF EXISTS `request_param`;
CREATE TABLE `request_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sequence` varchar(64) NOT NULL COMMENT '采集者的唯一序列号',
  `param_key` varchar(64) NOT NULL COMMENT '键',
  `param_value` varchar(128) DEFAULT NULL COMMENT '值',
  `is_delete` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N非删除 Y删除',
  `creator` varchar(20) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(20) NOT NULL COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请求参数';
