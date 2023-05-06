/*
 Navicat Premium Data Transfer

 Source Server         : 49.234.85.17
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 49.234.85.17:3306
 Source Schema         : readygo-mall-order

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 06/05/2023 16:54:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for coupon_selected
-- ----------------------------
DROP TABLE IF EXISTS `coupon_selected`;
CREATE TABLE `coupon_selected`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id',
  `received_coupon_id` bigint(0) NULL DEFAULT NULL COMMENT '领取id',
  `is_del` tinyint(0) NULL DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon_selected
-- ----------------------------

-- ----------------------------
-- Table structure for invoice
-- ----------------------------
DROP TABLE IF EXISTS `invoice`;
CREATE TABLE `invoice`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '发票内容',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id',
  `invoice_type` int(0) NULL DEFAULT NULL COMMENT '发票类型 0:电子普通发票 1:增值税发票',
  `invoice_title_type` int(0) NULL DEFAULT NULL COMMENT '发票抬头 0：个人 1：单位',
  `invoice_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人名称或单位名称',
  `tax_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纳税人识别号',
  `invoice_content` int(0) NULL DEFAULT NULL COMMENT '发票内容 0:商品明细 1:商品类别',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收票人手机',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收票人邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of invoice
-- ----------------------------
INSERT INTO `invoice` VALUES (8, 1, 1, 1, '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` bigint(0) NULL DEFAULT NULL COMMENT '父订单id',
  `order_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父订单号',
  `sub_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子订单号',
  `goods_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `goods_sku_id` bigint(0) NULL DEFAULT NULL COMMENT '商品sku id',
  `goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `goods_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `goods_selling_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品销售价格',
  `goods_count` int(0) NULL DEFAULT NULL COMMENT '商品数量',
  `goods_sub_total` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品小计（价格）',
  `after_sales_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '售后状态',
  `comment_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评价状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `trade_id` bigint(0) NULL DEFAULT NULL COMMENT '交易id',
  `trade_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易号',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id(买家id)',
  `member_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员名称(买家名称)',
  `merchant_id` bigint(0) NULL DEFAULT NULL COMMENT '商户id(卖家id)',
  `merchant_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户名称(卖家名称)',
  `source` int(0) NULL DEFAULT NULL COMMENT '订单来源（0：PC端，1：移动端，2：小程序）',
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单状态, UNPAID(\"待付款\"), PAID(\"已付款\"), UNDELIVERED(\"待发货\"), DELIVERED(\"已发货\"), FINISHED(\"已完成\"),  CANCELLED(\"已取消\");',
  `cancel_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '取消订单原因',
  `pay_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式，支付宝：ALIPAY, 微信：WECHAT_PAY',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品总金额',
  `freight` decimal(10, 2) NULL DEFAULT NULL COMMENT '运费',
  `final_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '最终金额（应付金额）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '下单时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '订单更新时间',
  `recipient_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人姓名',
  `recipient_detail_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人详细地址',
  `recipient_mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人手机号',
  `region_names` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地区名称（省、市、区县）如（安徽省 淮南市 寿县）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------

-- ----------------------------
-- Table structure for order_invoice
-- ----------------------------
DROP TABLE IF EXISTS `order_invoice`;
CREATE TABLE `order_invoice`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` int(0) NULL DEFAULT NULL COMMENT '订单id',
  `invoice_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票类型 0:电子普通发票 1:增值税发票',
  `invoice_title_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票抬头类型 0：个人 1：单位',
  `invoice_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人名称或单位名称',
  `tax_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纳税人识别号',
  `invoice_content` int(0) NULL DEFAULT NULL COMMENT '发票内容 0:商品明细 1:商品类别',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收票人手机号',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收票人邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_invoice
-- ----------------------------

-- ----------------------------
-- Table structure for recipient_info_selected
-- ----------------------------
DROP TABLE IF EXISTS `recipient_info_selected`;
CREATE TABLE `recipient_info_selected`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `recipient_info_id` bigint(0) NULL DEFAULT NULL COMMENT '收货信息id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recipient_info_selected
-- ----------------------------
INSERT INTO `recipient_info_selected` VALUES (3, 1, 7);

-- ----------------------------
-- Table structure for trade
-- ----------------------------
DROP TABLE IF EXISTS `trade`;
CREATE TABLE `trade`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易号',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id(买家id)',
  `member_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员名称(买家名称)',
  `pay_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '付款状态',
  `trade_time` datetime(0) NULL DEFAULT NULL COMMENT '交易时间',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品总金额',
  `freight` decimal(10, 2) NULL DEFAULT NULL COMMENT '运费',
  `final_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '最终金额（应付金额）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '交易表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of trade
-- ----------------------------

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `branch_id` bigint(0) NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int(0) NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
