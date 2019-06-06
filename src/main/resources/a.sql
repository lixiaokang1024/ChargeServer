/*
SQLyog v10.2
MySQL - 5.5.36 : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `test`;

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='收费项目';

/*Data for the table `charge_project` */

insert  into `charge_project`(`id`,`project_name`,`amount`,`grade_id`,`create_time`,`modify_time`,`is_deleted`) values (1,'学费','1800.00',1,0,'2019-06-06 16:13:32',0),(2,'学费','2000.00',2,0,'2019-06-06 16:13:39',0),(3,'住宿费','300.00',1,0,'2019-06-06 16:14:04',0),(4,'住宿费','400.00',2,0,'2019-06-06 16:14:09',0),(5,'团建费','190.00',-1,1559815716,'2019-06-06 18:08:36',0);

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

insert  into `class_info`(`id`,`name`,`grade_id`,`creat_time`,`modify_time`,`is_deleted`) values (1,'A班',1,0,'2019-06-06 14:37:27',0),(2,'B班',2,0,'2019-06-06 14:37:49',0),(3,'C班',3,0,'2019-06-06 14:37:54',0),(4,'D班',1,0,'2019-06-06 14:37:56',0),(5,'E班',2,0,'2019-06-06 14:37:59',0),(6,'F班',3,0,'2019-06-06 14:38:02',0);

/*Table structure for table `grade_info` */

DROP TABLE IF EXISTS `grade_info`;

CREATE TABLE `grade_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `level` int(11) NOT NULL,
  `create_time` int(11) NOT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='年级信息表';

/*Data for the table `grade_info` */

insert  into `grade_info`(`id`,`name`,`level`,`create_time`,`modify_time`,`is_deleted`) values (1,'小班',1,0,'2019-06-06 14:29:23',0),(2,'中班',2,0,'2019-06-06 14:29:33',0),(3,'大班',3,0,'2019-06-06 14:29:42',0);

/*Table structure for table `student_charge_info` */

DROP TABLE IF EXISTS `student_charge_info`;

CREATE TABLE `student_charge_info` (
  `id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `charge_project_id` int(11) NOT NULL,
  `charge_amount` decimal(8,2) NOT NULL,
  `charge_time` int(11) NOT NULL COMMENT '缴费日期',
  `pay_type` int(11) NOT NULL COMMENT '缴费方式(0:现金 1:预缴费扣除 2:其他)',
  `status` int(11) NOT NULL COMMENT '缴费状态(0:未缴费 1:已缴费)',
  `create_time` int(11) NOT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生缴费信息表';

/*Data for the table `student_charge_info` */

/*Table structure for table `student_class_info` */

DROP TABLE IF EXISTS `student_class_info`;

CREATE TABLE `student_class_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `grade_id` int(11) NOT NULL,
  `is_graduate` int(11) NOT NULL,
  `create_time` int(11) NOT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `student_class_info` */

/*Table structure for table `student_ext_info` */

DROP TABLE IF EXISTS `student_ext_info`;

CREATE TABLE `student_ext_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `is_graduate` int(11) NOT NULL COMMENT '是否毕业(0：否  1：是)',
  `admission_time` int(11) NOT NULL COMMENT '入学时间',
  `graduate_time` int(11) NOT NULL COMMENT '毕业时间',
  `prepayment_amount` decimal(8,2) NOT NULL COMMENT '预缴费剩余金额',
  `deposit` decimal(8,2) NOT NULL COMMENT '押金',
  `create_time` int(11) NOT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生扩展表信息';

/*Data for the table `student_ext_info` */

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
  `relation` varchar(32) NOT NULL DEFAULT '',
  `mobile` varchar(32) NOT NULL DEFAULT '',
  `address` varchar(256) NOT NULL DEFAULT '',
  `is_graduate` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否毕业(0:否 1:是)',
  `creat_time` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

/*Data for the table `student_info` */

insert  into `student_info`(`id`,`name`,`sex`,`year`,`month`,`day`,`age`,`parent_name`,`relation`,`mobile`,`address`,`is_graduate`,`creat_time`,`modify_time`,`is_deleted`) values (1,'lxk',1,1988,10,24,31,'1','1','1','1',1,1,'2019-06-05 16:09:07',0),(2,'李晓康',0,1988,10,24,31,'','','','',0,0,'2019-06-05 16:09:08',0),(3,'李晓康',0,1988,10,24,31,'','','','',0,0,'2019-06-05 16:09:10',0);

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
  `user_name` varchar(32) NOT NULL,
  `age` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`user_name`,`age`) values (1,'01179085',30);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
