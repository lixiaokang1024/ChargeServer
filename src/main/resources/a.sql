/*
SQLyog Professional v12.08 (64 bit)
MySQL - 5.6.40 : Database - charge
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`charge` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `charge`;

/*Table structure for table `charge_project` */

DROP TABLE IF EXISTS `charge_project`;

CREATE TABLE `charge_project` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `project_name` varchar(64) NOT NULL DEFAULT '' COMMENT '收费项目',
  `amount` decimal(8,2) NOT NULL DEFAULT '0.00',
  `grade_id` int(11) NOT NULL DEFAULT '0' COMMENT '所属年级',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_unique` (`project_name`,`grade_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='收费项目';

/*Table structure for table `class_info` */

DROP TABLE IF EXISTS `class_info`;

CREATE TABLE `class_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '',
  `grade_id` int(11) NOT NULL DEFAULT '0',
  `creat_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='班级信息';

/*Table structure for table `discount_setting` */

DROP TABLE IF EXISTS `discount_setting`;

CREATE TABLE `discount_setting` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `discount` decimal(3,2) NOT NULL DEFAULT '0.00',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='折扣配置';

/*Table structure for table `grade_info` */

DROP TABLE IF EXISTS `grade_info`;

CREATE TABLE `grade_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `level` int(11) NOT NULL,
  `create_time` int(11) NOT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='年级信息表';

/*Table structure for table `pay_project` */

DROP TABLE IF EXISTS `pay_project`;

CREATE TABLE `pay_project` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `project_name` varchar(32) NOT NULL DEFAULT '',
  `project_type` int(11) NOT NULL DEFAULT '0' COMMENT '0:收入 1:支出',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_name` (`project_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支出费用项目';

/*Table structure for table `pay_project_io` */

DROP TABLE IF EXISTS `pay_project_io`;

CREATE TABLE `pay_project_io` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pay_project_id` int(11) NOT NULL DEFAULT '0',
  `pay_amount` decimal(8,2) NOT NULL DEFAULT '0.00',
  `pay_time` int(11) NOT NULL DEFAULT '0',
  `remark` varchar(256) NOT NULL DEFAULT '',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支出流水表';

/*Table structure for table `receipt_id_record` */

DROP TABLE IF EXISTS `receipt_id_record`;

CREATE TABLE `receipt_id_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `record_id` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `resource` */

DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `menu_key` varchar(32) NOT NULL DEFAULT '',
  `menu_name` varchar(32) NOT NULL DEFAULT '',
  `parent_menu_key` varchar(32) NOT NULL DEFAULT '',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='资源表';

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Table structure for table `role_resource` */

DROP TABLE IF EXISTS `role_resource`;

CREATE TABLE `role_resource` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL DEFAULT '0',
  `resource_id` int(11) NOT NULL DEFAULT '0',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 COMMENT='角色资源表';

/*Table structure for table `student_charge_info` */

DROP TABLE IF EXISTS `student_charge_info`;

CREATE TABLE `student_charge_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL DEFAULT '0',
  `charge_project_id` int(11) NOT NULL DEFAULT '0',
  `charge_amount` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '应缴费金额(基数金额乘以系数)',
  `charge_coefficient` double NOT NULL DEFAULT '0' COMMENT '缴费系数',
  `actual_charge_amount` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '实际缴费金额',
  `discount` decimal(3,2) NOT NULL DEFAULT '1.00' COMMENT '折扣',
  `use_deposit_amount` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '使用预缴费金额',
  `custom_offer_amount` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '用户自定义优惠基恩',
  `charge_time` int(11) NOT NULL DEFAULT '0' COMMENT '缴费日期',
  `actual_charge_time` int(11) NOT NULL DEFAULT '0',
  `pay_type` int(11) NOT NULL DEFAULT '0' COMMENT '缴费方式(0:现金 1:非现金 2:其他)',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '缴费状态(0:未缴费 1:部分缴费 2:已缴费 3:已欠费)',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='学生缴费信息表';

/*Table structure for table `student_charge_io` */

DROP TABLE IF EXISTS `student_charge_io`;

CREATE TABLE `student_charge_io` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_charge_info_id` int(11) NOT NULL DEFAULT '0',
  `student_id` int(11) NOT NULL DEFAULT '0',
  `receipt_id` varchar(32) NOT NULL DEFAULT '' COMMENT '小票编号',
  `charge_amount` decimal(8,2) NOT NULL DEFAULT '0.00',
  `actual_charge_amount` decimal(8,2) NOT NULL DEFAULT '0.00',
  `use_deposit_amount` decimal(8,2) NOT NULL DEFAULT '0.00',
  `left_deposit_amount` decimal(8,2) NOT NULL DEFAULT '0.00',
  `custom_offer_amount` decimal(8,2) NOT NULL DEFAULT '0.00',
  `actual_charge_time` int(11) NOT NULL DEFAULT '0',
  `charge_type` int(11) NOT NULL DEFAULT '0',
  `pay_type` int(11) NOT NULL DEFAULT '0',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

/*Table structure for table `student_class_info` */

DROP TABLE IF EXISTS `student_class_info`;

CREATE TABLE `student_class_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL DEFAULT '0',
  `class_id` int(11) NOT NULL DEFAULT '0',
  `is_graduate` int(11) NOT NULL DEFAULT '0',
  `remark` varchar(60) NOT NULL DEFAULT '',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `student_class_index` (`student_id`,`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Table structure for table `student_ext_info` */

DROP TABLE IF EXISTS `student_ext_info`;

CREATE TABLE `student_ext_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL DEFAULT '0',
  `is_graduate` int(11) NOT NULL DEFAULT '0' COMMENT '是否毕业(0：否  1：是)',
  `admission_time` int(11) NOT NULL DEFAULT '0' COMMENT '入学时间',
  `graduate_time` int(11) NOT NULL DEFAULT '0' COMMENT '毕业时间',
  `prepayment_amount` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '预缴费剩余金额',
  `deposit` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '押金',
  `study_type` varchar(10) NOT NULL DEFAULT '' COMMENT '就读方式',
  `is_only_child` varchar(4) NOT NULL DEFAULT '' COMMENT '是否独生子女',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='学生扩展表信息';

/*Table structure for table `student_info` */

DROP TABLE IF EXISTS `student_info`;

CREATE TABLE `student_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '',
  `sex` int(11) NOT NULL DEFAULT '0' COMMENT '性别(0:男 1:女)',
  `year` int(11) NOT NULL DEFAULT '0',
  `month` int(11) NOT NULL DEFAULT '0',
  `day` int(11) NOT NULL DEFAULT '0',
  `age` int(11) NOT NULL DEFAULT '0',
  `parent_name` varchar(32) NOT NULL DEFAULT '',
  `parent_id_card_type` varchar(32) NOT NULL DEFAULT '' COMMENT '监护人身份证件类型',
  `parent_id_card_number` varchar(32) NOT NULL DEFAULT '' COMMENT '监护人身份证件号码',
  `relation` varchar(32) NOT NULL DEFAULT '',
  `mobile` varchar(32) NOT NULL DEFAULT '',
  `address` varchar(256) NOT NULL DEFAULT '',
  `is_graduate` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否毕业(0:否 1:是)',
  `id_card_type` varchar(32) NOT NULL DEFAULT '' COMMENT '身份证类型',
  `id_card_number` varchar(32) NOT NULL DEFAULT '' COMMENT '身份证件号码',
  `country` varchar(10) NOT NULL DEFAULT '' COMMENT '国籍',
  `nation` varchar(10) NOT NULL DEFAULT '' COMMENT '民族',
  `oversea` varchar(32) NOT NULL DEFAULT '' COMMENT '港澳台侨外',
  `born_place` varchar(256) NOT NULL DEFAULT '' COMMENT '出生地',
  `native_place` varchar(256) NOT NULL DEFAULT '' COMMENT '籍贯',
  `account_character` varchar(10) NOT NULL DEFAULT '' COMMENT '户口性质',
  `non_agricultural_account_type` varchar(10) NOT NULL DEFAULT '' COMMENT '非农业户口类型',
  `registered_residence` varchar(256) NOT NULL DEFAULT '' COMMENT '户口所在地',
  `creat_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

/*Table structure for table `test_table` */

DROP TABLE IF EXISTS `test_table`;

CREATE TABLE `test_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `age_index` (`age`),
  KEY `name_index` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL DEFAULT '',
  `password` varchar(32) NOT NULL DEFAULT '',
  `age` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `role_id` int(11) NOT NULL DEFAULT '0',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
insert  into `user`(`id`,`user_name`,`password`,`age`) values (1,'admin','admin',100);
insert  into `user_role`(`id`,`user_id`,`role_id`,`create_time`,`modify_time`) values (1,1,1,0,'2019-12-27 16:45:43');
insert  into `role`(`id`,`name`,`create_time`,`modify_time`) values (1,'管理员',0,'2019-12-25 17:43:52');
insert  into `resource`(`id`,`menu_key`,`menu_name`,`parent_menu_key`,`create_time`,`modify_time`) values (1,'school/gradeIndex','年级配置','',0,'2020-03-11 12:05:24'),(2,'school/classIndex','班级配置','',0,'2020-03-11 12:05:54'),(3,'user/userIndex','用户管理','',0,'2020-03-11 18:47:14'),(4,'user/roleIndex','角色管理','',0,'2020-03-11 18:47:27'),(5,'user/menuIndex','权限管理','',0,'2020-03-11 18:47:39'),(6,'studentChargeInfo/countIndex','学生缴费统计','',0,'2020-03-11 19:06:21'),(7,'charge/payProjectIndex','日常支出项目配置','',0,'2020-03-11 21:03:37'),(8,'charge/incomeProjectIndex','日常收入项目配置','',0,'2020-03-11 21:03:52'),(9,'discount/discountIndex','折扣配置','',0,'2020-03-11 21:04:07'),(10,'student/index','学生基础信息','',0,'2020-03-11 21:04:39'),(11,'studentClassInfo/index','学生班级维护','',0,'2020-03-11 21:04:55'),(12,'studentChargeInfo/index','应缴费学生','',0,'2020-03-11 21:05:08'),(13,'studentChargeInfo/expireIndex','已欠费学生','',0,'2020-03-11 21:05:21'),(14,'studentChargeInfo/historyIndex','学生历史缴费','',0,'2020-03-11 21:05:33'),(15,'studentChargeInfo/receiptIndex','补打小票','',0,'2020-03-11 21:05:48'),(16,'charge/payProjectIoIndex','日常支出','',0,'2020-03-11 21:06:02'),(17,'charge/incomeProjectIoIndex','日常收入','',0,'2020-03-11 21:06:16'),(18,'studentChargeInfo/countIndex','学生缴费统计','',0,'2020-03-11 21:06:32'),(19,'charge/index','缴费项目配置','',0,'2020-03-11 21:08:31');
insert  into `role_resource`(`id`,`role_id`,`resource_id`,`create_time`,`modify_time`) values (35,1,1,0,'2020-03-11 21:08:51'),(36,1,2,0,'2020-03-11 21:08:51'),(37,1,3,0,'2020-03-11 21:08:51'),(38,1,4,0,'2020-03-11 21:08:51'),(39,1,5,0,'2020-03-11 21:08:51'),(40,1,6,0,'2020-03-11 21:08:51'),(41,1,7,0,'2020-03-11 21:08:51'),(42,1,8,0,'2020-03-11 21:08:51'),(43,1,9,0,'2020-03-11 21:08:51'),(44,1,10,0,'2020-03-11 21:08:51'),(45,1,11,0,'2020-03-11 21:08:51'),(46,1,12,0,'2020-03-11 21:08:51'),(47,1,13,0,'2020-03-11 21:08:51'),(48,1,14,0,'2020-03-11 21:08:51'),(49,1,15,0,'2020-03-11 21:08:51'),(50,1,16,0,'2020-03-11 21:08:51'),(51,1,17,0,'2020-03-11 21:08:51'),(52,1,18,0,'2020-03-11 21:08:51'),(53,1,19,0,'2020-03-11 21:08:51');
