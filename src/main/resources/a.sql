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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='收费项目';

/*Data for the table `charge_project` */

insert  into `charge_project`(`id`,`project_name`,`amount`,`grade_id`,`create_time`,`modify_time`,`is_deleted`) values (1,'伙食费','180.00',1,1576050756,'2019-12-23 10:43:31',0),(2,'保育费','200.00',1,1576735685,'2019-12-19 14:08:05',0);

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='班级信息';

/*Data for the table `class_info` */

insert  into `class_info`(`id`,`name`,`grade_id`,`creat_time`,`modify_time`,`is_deleted`) values (1,'一班',1,1576050745,'2019-12-11 15:52:25',0),(2,'二班',1,1577186855,'2019-12-24 19:27:35',0),(3,'二班',2,1577186855,'2019-12-24 19:27:35',0),(4,'二班',3,1577186855,'2019-12-24 19:27:35',0),(5,'一班',2,1577186872,'2019-12-24 19:27:52',0),(6,'一班',3,1577186872,'2019-12-24 19:27:52',0);

/*Table structure for table `discount_setting` */

DROP TABLE IF EXISTS `discount_setting`;

CREATE TABLE `discount_setting` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `discount` decimal(3,2) NOT NULL DEFAULT '0.00',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='折扣配置';

/*Data for the table `discount_setting` */

insert  into `discount_setting`(`id`,`discount`,`create_time`,`modify_time`) values (3,'5.90',1577331707,'2019-12-26 11:41:47'),(4,'9.50',1577341955,'2019-12-26 14:32:35');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='年级信息表';

/*Data for the table `grade_info` */

insert  into `grade_info`(`id`,`name`,`level`,`create_time`,`modify_time`,`is_deleted`) values (1,'一年级',1,1576050739,'2019-12-11 15:52:19',0),(2,'二年级',2,1577186839,'2019-12-24 19:27:19',0),(3,'三年级',3,1577186846,'2019-12-24 19:27:26',0);

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

/*Data for the table `pay_project` */

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

/*Data for the table `pay_project_io` */

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='资源表';

/*Data for the table `resource` */

insert  into `resource`(`id`,`menu_key`,`menu_name`,`parent_menu_key`,`create_time`,`modify_time`) values (1,'/charge/school/gradeIndex','年级配置index','',0,'2019-12-27 16:45:25');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `role` */

insert  into `role`(`id`,`name`,`create_time`,`modify_time`) values (1,'管理员',0,'2019-12-25 17:43:52');

/*Table structure for table `role_resource` */

DROP TABLE IF EXISTS `role_resource`;

CREATE TABLE `role_resource` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL DEFAULT '0',
  `resource_id` int(11) NOT NULL DEFAULT '0',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色资源表';

/*Data for the table `role_resource` */

insert  into `role_resource`(`id`,`role_id`,`resource_id`,`create_time`,`modify_time`) values (1,1,1,0,'2019-12-27 16:45:34');

/*Table structure for table `student_charge_info` */

DROP TABLE IF EXISTS `student_charge_info`;

CREATE TABLE `student_charge_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL DEFAULT '0',
  `charge_project_id` int(11) NOT NULL DEFAULT '0',
  `charge_amount` decimal(8,2) NOT NULL DEFAULT '0.00',
  `actual_charge_amount` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '实际缴费金额',
  `discount` decimal(3,2) NOT NULL DEFAULT '1.00' COMMENT '折扣',
  `use_deposit_amount` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '使用预缴费金额',
  `charge_time` int(11) NOT NULL DEFAULT '0' COMMENT '缴费日期',
  `actual_charge_time` int(11) NOT NULL DEFAULT '0',
  `pay_type` int(11) NOT NULL DEFAULT '0' COMMENT '缴费方式(0:现金 1:预缴费扣除 2:其他)',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '缴费状态(0:未缴费 1:部分缴费 2:已缴费 3:已欠费)',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='学生缴费信息表';

/*Data for the table `student_charge_info` */

insert  into `student_charge_info`(`id`,`student_id`,`charge_project_id`,`charge_amount`,`actual_charge_amount`,`discount`,`use_deposit_amount`,`charge_time`,`actual_charge_time`,`pay_type`,`status`,`create_time`,`modify_time`,`is_deleted`) values (1,6,1,'180.00','180.00','1.00','0.00',1576339200,1576121142,0,2,1576053566,'2019-12-16 20:56:24',0),(2,6,1,'180.00','180.00','1.00','0.00',1576598400,1576121260,0,2,1576121228,'2019-12-16 19:54:34',0),(3,6,1,'180.00','162.00','0.90','0.00',1576497508,1576582678,0,2,1576208383,'2019-12-17 19:37:58',0),(4,6,1,'180.00','0.00','1.00','0.00',1576684800,0,0,3,1576667397,'2019-12-19 14:24:00',0),(5,6,1,'180.00','0.00','1.00','0.00',1577635200,0,0,0,1576735733,'2019-12-19 14:08:53',0),(6,6,2,'200.00','0.00','1.00','0.00',1577635200,0,0,0,1576735733,'2019-12-19 14:08:53',0);

/*Table structure for table `student_class_info` */

DROP TABLE IF EXISTS `student_class_info`;

CREATE TABLE `student_class_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL DEFAULT '0',
  `class_id` int(11) NOT NULL DEFAULT '0',
  `is_graduate` int(11) NOT NULL DEFAULT '0',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `student_class_index` (`student_id`,`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `student_class_info` */

insert  into `student_class_info`(`id`,`student_id`,`class_id`,`is_graduate`,`create_time`,`modify_time`,`is_deleted`) values (1,6,1,1,1576053377,'2019-12-23 16:14:11',0),(2,1,5,0,1577190153,'2019-12-24 20:22:33',0),(3,5,6,0,1577190304,'2019-12-24 20:25:04',0);

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='学生扩展表信息';

/*Data for the table `student_ext_info` */

insert  into `student_ext_info`(`id`,`student_id`,`is_graduate`,`admission_time`,`graduate_time`,`prepayment_amount`,`deposit`,`study_type`,`is_only_child`,`create_time`,`modify_time`,`is_deleted`) values (1,1,0,1569859200,0,'0.00','0.00','','',1574745794,'2019-11-26 13:23:14',0),(4,5,0,1575993600,0,'0.00','0.00','自费','否',1576046045,'2019-12-11 16:46:14',0),(5,6,1,1575993600,1577088851,'0.00','0.00','自费','否',1576047448,'2019-12-23 16:14:11',0);

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

/*Data for the table `student_info` */

insert  into `student_info`(`id`,`name`,`sex`,`year`,`month`,`day`,`age`,`parent_name`,`parent_id_card_type`,`parent_id_card_number`,`relation`,`mobile`,`address`,`is_graduate`,`id_card_type`,`id_card_number`,`country`,`nation`,`oversea`,`born_place`,`native_place`,`account_character`,`non_agricultural_account_type`,`registered_residence`,`creat_time`,`modify_time`,`is_deleted`) values (1,'李晓康',0,1988,12,1,30,'李晓康','','','李晓康','13641017418','河北省沙河市',0,'','','','','','','','','','',1574745794,'2019-11-26 13:23:14',0),(5,'小马',0,1989,12,12,29,'李晓康','身份证','130582198912120000','大表哥','13641017418','北京市通州区',0,'身份证','130582198912120001','中国','汉','无','河北省沙河市','河北省沙河市','农业','','河北省沙河市',1576046045,'2019-12-11 14:40:23',0),(6,'小马',0,1989,12,12,29,'李晓康','身份证','130582198912120000','大表哥','13641017418','北京市通州区',1,'身份证','130582198912120000','中国','汉','无','河北省沙河市','河北省沙河市','农业','','河北省沙河市',1576047447,'2019-12-23 16:14:11',0);

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

/*Data for the table `test_table` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL DEFAULT '',
  `password` varchar(32) NOT NULL DEFAULT '',
  `age` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`user_name`,`password`,`age`) values (1,'admin','admin',100),(2,'lxk','123',0);

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

/*Data for the table `user_role` */

insert  into `user_role`(`id`,`user_id`,`role_id`,`create_time`,`modify_time`) values (1,1,1,0,'2019-12-27 16:45:43');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
