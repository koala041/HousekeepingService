-- MySQL dump 10.13  Distrib 5.7.31, for Linux (x86_64)
--
-- Host: localhost    Database: springcloud-alibaba3krib874
-- ------------------------------------------------------
-- Server version	5.7.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `springcloud-alibaba3krib874`
--

/*!40000 DROP DATABASE IF EXISTS `springcloud-alibaba3krib874`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `springcloud-alibaba3krib874` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `springcloud-alibaba3krib874`;

--
-- Table structure for table `caiwujiesuan`
--

DROP TABLE IF EXISTS `caiwujiesuan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `caiwujiesuan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `yuyuebianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预约编号',
  `xiangmubianhao` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目编号',
  `xiangmumingcheng` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目名称',
  `xiangmutupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '项目图片',
  `fuwuleixing` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务类型',
  `fuwufeiyong` double DEFAULT NULL COMMENT '服务费用',
  `yongjinzhanbi` double DEFAULT NULL COMMENT '佣金占比',
  `shouyi` double DEFAULT NULL COMMENT '收益',
  `yuangongzhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '员工账号',
  `yuangongxingming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '员工姓名',
  `jiesuanriqi` datetime DEFAULT NULL COMMENT '结算日期',
  `tixianzhuangtai` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提现状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='财务结算';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caiwujiesuan`
--

LOCK TABLES `caiwujiesuan` WRITE;
/*!40000 ALTER TABLE `caiwujiesuan` DISABLE KEYS */;
INSERT INTO `caiwujiesuan` VALUES (1,'2026-04-28 01:33:45','预约编号1','项目编号1','冰箱消毒清洁','upload/caiwujiesuan_冰箱消毒清洁1.jpg,upload/caiwujiesuan_冰箱消毒清洁2.jpg,upload/caiwujiesuan_冰箱消毒清洁3.jpg','家电清洗类',100,0.9,29,'16','刘敏','2026-04-28 09:33:45','已提现'),(2,'2026-04-28 01:33:45','预约编号2','项目编号2','家庭餐上门制作','upload/caiwujiesuan_家庭餐上门制作1.jpg,upload/caiwujiesuan_家庭餐上门制作2.jpg,upload/caiwujiesuan_家庭餐上门制作3.jpg','餐饮服务类',150,0.6,25,'17','周凯','2026-04-28 09:33:45','已提现'),(3,'2026-04-28 01:33:45','预约编号3','项目编号3','厨房深度清洁','upload/caiwujiesuan_厨房深度清洁1.jpg,upload/caiwujiesuan_厨房深度清洁2.jpg,upload/caiwujiesuan_厨房深度清洁3.jpg','深度保洁类',200,0.85,9,'11','张强','2026-04-28 09:33:45','已提现'),(4,'2026-04-28 01:33:45','预约编号4','项目编号4','房屋收纳整理','upload/caiwujiesuan_房屋收纳整理1.jpg,upload/caiwujiesuan_房屋收纳整理2.jpg,upload/caiwujiesuan_房屋收纳整理3.jpg','空间规划类',260,0.85,18,'15','赵阳','2026-04-28 09:33:45','已提现'),(5,'2026-04-28 01:33:45','预约编号5','项目编号5','窗帘上门清洗','upload/caiwujiesuan_窗帘上门清洗1.jpg,upload/caiwujiesuan_窗帘上门清洗2.jpg,upload/caiwujiesuan_窗帘上门清洗3.jpg','布艺清洁类',180,0.9,4,'12','李娜','2026-04-28 09:33:45','已提现'),(6,'2026-04-28 01:33:45','预约编号6','项目编号6','母婴日常照护','upload/caiwujiesuan_母婴日常照护1.jpg,upload/caiwujiesuan_母婴日常照护2.jpg,upload/caiwujiesuan_母婴日常照护3.jpg','母婴护理类',300,0.8,26,'14','陈曦','2026-04-28 09:33:45','已提现'),(7,'2026-04-28 01:33:45','预约编号7','项目编号7','日常居家保洁','upload/caiwujiesuan_日常居家保洁1.jpg,upload/caiwujiesuan_日常居家保洁2.jpg,upload/caiwujiesuan_日常居家保洁3.jpg','日常保洁类',120,0.7,20,'18','吴芳','2026-04-28 09:33:45','已提现'),(8,'2026-04-28 01:33:45','预约编号8','项目编号8','油烟机专项清洗','upload/caiwujiesuan_油烟机专项清洗1.jpg,upload/caiwujiesuan_油烟机专项清洗2.jpg,upload/caiwujiesuan_油烟机专项清洗3.jpg','家电清洗类',150,0.9,17,'13','王浩','2026-04-28 09:33:45','已提现');
/*!40000 ALTER TABLE `caiwujiesuan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `adminid` bigint(20) DEFAULT NULL COMMENT '管理员id',
  `ask` longtext COLLATE utf8mb4_unicode_ci COMMENT '提问',
  `reply` longtext COLLATE utf8mb4_unicode_ci COMMENT '回复',
  `isreply` int(11) DEFAULT NULL COMMENT '是否回复',
  `isread` int(11) DEFAULT '0' COMMENT '已读/未读(1:已读,0:未读)',
  `uname` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `uimage` longtext COLLATE utf8mb4_unicode_ci COMMENT '用户头像',
  `type` int(11) DEFAULT '1' COMMENT '内容类型(1:文本,2:图片,3:视频,4:文件,5:表情)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='在线客服';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat`
--

LOCK TABLES `chat` WRITE;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
INSERT INTO `chat` VALUES (1,'2026-04-28 01:33:46',1,1,'提问1','回复1',1,1,'用户名1','upload/chat_uimage1.jpg,upload/chat_uimage2.jpg,upload/chat_uimage3.jpg',1),(2,'2026-04-28 01:33:46',2,2,'提问2','回复2',2,2,'用户名2','upload/chat_uimage2.jpg,upload/chat_uimage3.jpg,upload/chat_uimage4.jpg',2),(3,'2026-04-28 01:33:46',3,3,'提问3','回复3',3,3,'用户名3','upload/chat_uimage3.jpg,upload/chat_uimage4.jpg,upload/chat_uimage5.jpg',3),(4,'2026-04-28 01:33:46',4,4,'提问4','回复4',4,4,'用户名4','upload/chat_uimage4.jpg,upload/chat_uimage5.jpg,upload/chat_uimage6.jpg',4),(5,'2026-04-28 01:33:46',5,5,'提问5','回复5',5,5,'用户名5','upload/chat_uimage5.jpg,upload/chat_uimage6.jpg,upload/chat_uimage7.jpg',5),(6,'2026-04-28 01:33:46',6,6,'提问6','回复6',6,6,'用户名6','upload/chat_uimage6.jpg,upload/chat_uimage7.jpg,upload/chat_uimage8.jpg',6),(7,'2026-04-28 01:33:46',7,7,'提问7','回复7',7,7,'用户名7','upload/chat_uimage7.jpg,upload/chat_uimage8.jpg,upload/chat_uimage1.jpg',7),(8,'2026-04-28 01:33:46',8,8,'提问8','回复8',8,8,'用户名8','upload/chat_uimage8.jpg,upload/chat_uimage1.jpg,upload/chat_uimage2.jpg',8);
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chathelper`
--

DROP TABLE IF EXISTS `chathelper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chathelper` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ask` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提问',
  `reply` longtext COLLATE utf8mb4_unicode_ci COMMENT '回复',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='聊天助手表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chathelper`
--

LOCK TABLES `chathelper` WRITE;
/*!40000 ALTER TABLE `chathelper` DISABLE KEYS */;
INSERT INTO `chathelper` VALUES (1,'2026-04-28 01:33:46','提问1','回复1'),(2,'2026-04-28 01:33:46','提问2','回复2'),(3,'2026-04-28 01:33:46','提问3','回复3'),(4,'2026-04-28 01:33:46','提问4','回复4'),(5,'2026-04-28 01:33:46','提问5','回复5'),(6,'2026-04-28 01:33:46','提问6','回复6'),(7,'2026-04-28 01:33:46','提问7','回复7'),(8,'2026-04-28 01:33:46','提问8','回复8');
/*!40000 ALTER TABLE `chathelper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatmessage`
--

DROP TABLE IF EXISTS `chatmessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chatmessage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `fid` bigint(20) NOT NULL COMMENT '好友用户ID',
  `content` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `format` int(11) DEFAULT NULL COMMENT '格式(1:文字，2:图片)',
  `isread` int(11) DEFAULT '0' COMMENT '消息已读(0:未读，1:已读)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatmessage`
--

LOCK TABLES `chatmessage` WRITE;
/*!40000 ALTER TABLE `chatmessage` DISABLE KEYS */;
/*!40000 ALTER TABLE `chatmessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config`
--

DROP TABLE IF EXISTS `config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '配置参数名称',
  `value` varchar(200) DEFAULT NULL COMMENT '配置参数值',
  `url` varchar(500) DEFAULT NULL COMMENT 'url',
  `type` int(11) DEFAULT NULL COMMENT '参数类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='配置文件';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config`
--

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` VALUES (1,'picture1','upload/picture1.jpg',NULL,1),(2,'picture2','upload/picture2.jpg',NULL,1),(3,'picture3','upload/picture3.jpg',NULL,1),(10,'gaode','{\"key\":\"\",\"code\":\"\"}',NULL,2),(11,'baidu','{\"appId\":\"49214550\",\"apiKey\":\"7Otjpv2kn0ljQk45qXOXh5MO\",\"secretKey\":\"BMfbXRbTIVaB4C3SbRTtGqDv1wHDvyXS\"}',NULL,2),(13,'aliyun','{\"accessKeyId\":\"\",\"accessKeySecret\":\"\"}',NULL,2),(14,'deepseek','{\"key\":\"sk-961af215e60e4f269c2808b3a55eb9e8\"}',NULL,2),(21,'bLoginBackgroundImg','',NULL,3),(22,'bRegisterBackgroundImg','',NULL,3),(23,'bIndexBackgroundImg','',NULL,3),(24,'bTopLogo','',NULL,3),(25,'bHomeLogo','',NULL,3),(29,'appLoginBackgroundImg','',NULL,3),(30,'appRegisterBackgroudImg','',NULL,3),(31,'appLoginLogo','',NULL,3),(32,'appRegLogo','',NULL,3);
/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discussfuwuxinxi`
--

DROP TABLE IF EXISTS `discussfuwuxinxi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discussfuwuxinxi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `refid` bigint(20) NOT NULL COMMENT '关联表id',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `avatarurl` longtext COLLATE utf8mb4_unicode_ci COMMENT '头像',
  `nickname` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论内容',
  `score` double DEFAULT NULL COMMENT '评分',
  `reply` longtext COLLATE utf8mb4_unicode_ci COMMENT '回复内容',
  `thumbsupnum` int(11) DEFAULT '0' COMMENT '赞',
  `crazilynum` int(11) DEFAULT '0' COMMENT '踩',
  `istop` int(11) DEFAULT '0' COMMENT '置顶(1:置顶,0:非置顶)',
  `tuserids` longtext COLLATE utf8mb4_unicode_ci COMMENT '赞用户ids',
  `cuserids` longtext COLLATE utf8mb4_unicode_ci COMMENT '踩用户ids',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服务信息评论';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discussfuwuxinxi`
--

LOCK TABLES `discussfuwuxinxi` WRITE;
/*!40000 ALTER TABLE `discussfuwuxinxi` DISABLE KEYS */;
/*!40000 ALTER TABLE `discussfuwuxinxi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend`
--

DROP TABLE IF EXISTS `friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `fid` bigint(20) NOT NULL COMMENT '好友用户ID',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `picture` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片',
  `role` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色',
  `tablename` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '表名',
  `alias` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '别名',
  `type` int(11) DEFAULT '0' COMMENT '类型(0:好友申请，1:好友，2:消息)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='好友表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend`
--

LOCK TABLES `friend` WRITE;
/*!40000 ALTER TABLE `friend` DISABLE KEYS */;
/*!40000 ALTER TABLE `friend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fuwudingdan`
--

DROP TABLE IF EXISTS `fuwudingdan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fuwudingdan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `yuyuebianhao` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '预约编号',
  `xiangmubianhao` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目编号',
  `xiangmumingcheng` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目名称',
  `xiangmutupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '项目图片',
  `fuwuleixing` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务类型',
  `fuwufeiyong` double DEFAULT NULL COMMENT '服务费用',
  `fuwuneirong` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务内容',
  `weizhi` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '位置',
  `yuyueriqi` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预约日期',
  `jiedanshijian` datetime NOT NULL COMMENT '接单时间',
  `yuyueshiduan` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预约时段',
  `fuwuzhuangtai` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务状态',
  `yonghuzhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户账号',
  `yonghuxingming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户姓名',
  `shoujihao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `fuwudizhi` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务地址',
  `yuangongzhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '员工账号',
  `yuangongxingming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '员工姓名',
  `yujidaodashijian` datetime DEFAULT NULL COMMENT '预计到达时间',
  `sfsh` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '待审核' COMMENT '是否审核',
  `shhf` longtext COLLATE utf8mb4_unicode_ci COMMENT '审核回复',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服务订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fuwudingdan`
--

LOCK TABLES `fuwudingdan` WRITE;
/*!40000 ALTER TABLE `fuwudingdan` DISABLE KEYS */;
INSERT INTO `fuwudingdan` VALUES (1,'2026-04-28 01:33:45','预约编号1','项目编号1','冰箱消毒清洁','upload/fuwudingdan_冰箱消毒清洁1.jpg,upload/fuwudingdan_冰箱消毒清洁2.jpg,upload/fuwudingdan_冰箱消毒清洁3.jpg','家电清洗类',100,'内胆隔板消毒除味','位置1','预约日期1','2026-04-28 09:33:45','预约时段1','已服务','003','赵子轩','13823888881','服务地址1','16','刘敏','2026-04-28 09:33:45','是',''),(2,'2026-04-28 01:33:45','预约编号2','项目编号2','家庭餐上门制作','upload/fuwudingdan_家庭餐上门制作1.jpg,upload/fuwudingdan_家庭餐上门制作2.jpg,upload/fuwudingdan_家庭餐上门制作3.jpg','餐饮服务类',150,'3餐食材采购制作','位置2','预约日期2','2026-04-28 09:33:45','预约时段2','已服务','002','王强','13823888882','服务地址2','17','周凯','2026-04-28 09:33:45','是',''),(3,'2026-04-28 01:33:45','预约编号3','项目编号3','厨房深度清洁','upload/fuwudingdan_厨房深度清洁1.jpg,upload/fuwudingdan_厨房深度清洁2.jpg,upload/fuwudingdan_厨房深度清洁3.jpg','深度保洁类',200,'灶台墙面橱柜油污清理','位置3','预约日期3','2026-04-28 09:33:45','预约时段3','已服务','005','林小雨','13823888883','服务地址3','11','张强','2026-04-28 09:33:45','是',''),(4,'2026-04-28 01:33:45','预约编号4','项目编号4','房屋收纳整理','upload/fuwudingdan_房屋收纳整理1.jpg,upload/fuwudingdan_房屋收纳整理2.jpg,upload/fuwudingdan_房屋收纳整理3.jpg','空间规划类',260,'衣物书籍用品分类整理','位置4','预约日期4','2026-04-28 09:33:45','预约时段4','已服务','006','孙伟','13823888884','服务地址4','15','赵阳','2026-04-28 09:33:45','是',''),(5,'2026-04-28 01:33:45','预约编号5','项目编号5','窗帘上门清洗','upload/fuwudingdan_窗帘上门清洗1.jpg,upload/fuwudingdan_窗帘上门清洗2.jpg,upload/fuwudingdan_窗帘上门清洗3.jpg','布艺清洁类',180,'免拆洗除尘消毒','位置5','预约日期5','2026-04-28 09:33:45','预约时段5','已服务','004','杨洁','13823888885','服务地址5','12','李娜','2026-04-28 09:33:45','是',''),(6,'2026-04-28 01:33:45','预约编号6','项目编号6','母婴日常照护','upload/fuwudingdan_母婴日常照护1.jpg,upload/fuwudingdan_母婴日常照护2.jpg,upload/fuwudingdan_母婴日常照护3.jpg','母婴护理类',300,'新生儿喂奶拍嗝洗澡抚触','位置6','预约日期6','2026-04-28 09:33:45','预约时段6','已服务','001','陈浩','13823888886','服务地址6','14','陈曦','2026-04-28 09:33:45','是',''),(7,'2026-04-28 01:33:45','预约编号7','项目编号7','日常居家保洁','upload/fuwudingdan_日常居家保洁1.jpg,upload/fuwudingdan_日常居家保洁2.jpg,upload/fuwudingdan_日常居家保洁3.jpg','日常保洁类',120,'客厅卧室厨房基础清洁','位置7','预约日期7','2026-04-28 09:33:45','预约时段7','已服务','007','徐婷','13823888887','服务地址7','18','吴芳','2026-04-28 09:33:45','是',''),(8,'2026-04-28 01:33:45','预约编号8','项目编号8','油烟机专项清洗','upload/fuwudingdan_油烟机专项清洗1.jpg,upload/fuwudingdan_油烟机专项清洗2.jpg,upload/fuwudingdan_油烟机专项清洗3.jpg','家电清洗类',150,'内外部油污拆解清洁','位置8','预约日期8','2026-04-28 09:33:45','预约时段8','已服务','008','张明','13823888888','服务地址8','13','王浩','2026-04-28 09:33:45','是','');
/*!40000 ALTER TABLE `fuwudingdan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fuwujilu`
--

DROP TABLE IF EXISTS `fuwujilu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fuwujilu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `yuyuebianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预约编号',
  `xiangmubianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '项目编号',
  `xiangmumingcheng` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目名称',
  `yuangongzhanghao` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '员工账号',
  `yuangongxingming` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '员工姓名',
  `fuwuleixing` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务类型',
  `fuwufeiyong` double DEFAULT NULL COMMENT '服务费用',
  `yonghuzhanghao` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户账号',
  `yonghuxingming` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户姓名',
  `shoujihao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `fuwushijian` datetime DEFAULT NULL COMMENT '服务时间',
  `fuwuqueren` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务确认',
  `fuwutupian` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服务图片',
  `fuwujilu` longtext COLLATE utf8mb4_unicode_ci COMMENT '服务记录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服务记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fuwujilu`
--

LOCK TABLES `fuwujilu` WRITE;
/*!40000 ALTER TABLE `fuwujilu` DISABLE KEYS */;
INSERT INTO `fuwujilu` VALUES (1,'2026-04-28 01:33:45','预约编号1','项目编号1','冰箱消毒清洁','16','刘敏','家电清洗类',100,'003','赵子轩','13823888881','2026-04-28 09:33:45','已确认','upload/fuwujilu_冰箱消毒清洁1.jpg,upload/fuwujilu_冰箱消毒清洁2.jpg,upload/fuwujilu_冰箱消毒清洁3.jpg','服务记录1'),(2,'2026-04-28 01:33:45','预约编号2','项目编号2','家庭餐上门制作','17','周凯','餐饮服务类',150,'002','王强','13823888882','2026-04-28 09:33:45','已确认','upload/fuwujilu_家庭餐上门制作1.jpg,upload/fuwujilu_家庭餐上门制作2.jpg,upload/fuwujilu_家庭餐上门制作3.jpg','服务记录2'),(3,'2026-04-28 01:33:45','预约编号3','项目编号3','厨房深度清洁','11','张强','深度保洁类',200,'005','林小雨','13823888883','2026-04-28 09:33:45','已确认','upload/fuwujilu_厨房深度清洁1.jpg,upload/fuwujilu_厨房深度清洁2.jpg,upload/fuwujilu_厨房深度清洁3.jpg','服务记录3'),(4,'2026-04-28 01:33:45','预约编号4','项目编号4','房屋收纳整理','15','赵阳','空间规划类',260,'006','孙伟','13823888884','2026-04-28 09:33:45','已确认','upload/fuwujilu_房屋收纳整理1.jpg,upload/fuwujilu_房屋收纳整理2.jpg,upload/fuwujilu_房屋收纳整理3.jpg','服务记录4'),(5,'2026-04-28 01:33:45','预约编号5','项目编号5','窗帘上门清洗','12','李娜','布艺清洁类',180,'004','杨洁','13823888885','2026-04-28 09:33:45','已确认','upload/fuwujilu_窗帘上门清洗1.jpg,upload/fuwujilu_窗帘上门清洗2.jpg,upload/fuwujilu_窗帘上门清洗3.jpg','服务记录5'),(6,'2026-04-28 01:33:45','预约编号6','项目编号6','母婴日常照护','14','陈曦','母婴护理类',300,'001','陈浩','13823888886','2026-04-28 09:33:45','已确认','upload/fuwujilu_母婴日常照护1.jpg,upload/fuwujilu_母婴日常照护2.jpg,upload/fuwujilu_母婴日常照护3.jpg','服务记录6'),(7,'2026-04-28 01:33:45','预约编号7','项目编号7','日常居家保洁','18','吴芳','日常保洁类',120,'007','徐婷','13823888887','2026-04-28 09:33:45','已确认','upload/fuwujilu_日常居家保洁1.jpg,upload/fuwujilu_日常居家保洁2.jpg,upload/fuwujilu_日常居家保洁3.jpg','服务记录7'),(8,'2026-04-28 01:33:45','预约编号8','项目编号8','油烟机专项清洗','13','王浩','家电清洗类',150,'008','张明','13823888888','2026-04-28 09:33:45','已确认','upload/fuwujilu_油烟机专项清洗1.jpg,upload/fuwujilu_油烟机专项清洗2.jpg,upload/fuwujilu_油烟机专项清洗3.jpg','服务记录8');
/*!40000 ALTER TABLE `fuwujilu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fuwuleixing`
--

DROP TABLE IF EXISTS `fuwuleixing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fuwuleixing` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `fuwuleixing` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服务类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `fuwuleixing` (`fuwuleixing`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服务类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fuwuleixing`
--

LOCK TABLES `fuwuleixing` WRITE;
/*!40000 ALTER TABLE `fuwuleixing` DISABLE KEYS */;
INSERT INTO `fuwuleixing` VALUES (1,'2026-04-28 01:33:45','家电清洗类'),(2,'2026-04-28 01:33:45','餐饮服务类'),(3,'2026-04-28 01:33:45','深度保洁类'),(4,'2026-04-28 01:33:45','空间规划类'),(5,'2026-04-28 01:33:45','布艺清洁类'),(6,'2026-04-28 01:33:45','母婴护理类'),(7,'2026-04-28 01:33:45','日常保洁类'),(8,'2026-04-28 01:33:45','服务类型8');
/*!40000 ALTER TABLE `fuwuleixing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fuwuqueren`
--

DROP TABLE IF EXISTS `fuwuqueren`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fuwuqueren` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `yuyuebianhao` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '预约编号',
  `xiangmubianhao` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目编号',
  `xiangmumingcheng` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目名称',
  `fuwuleixing` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务类型',
  `fuwufeiyong` double DEFAULT NULL COMMENT '服务费用',
  `wanchengshijian` datetime NOT NULL COMMENT '完成时间',
  `fankuitupian` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '反馈图片',
  `pingfen` int(11) DEFAULT NULL COMMENT '评分',
  `fuwupingjia` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服务评价',
  `yuangongxingming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '员工姓名',
  `yuangongzhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '员工账号',
  `yonghuzhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户账号',
  `yonghuxingming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户姓名',
  `jiesuanzhuangtai` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结算状态',
  `shhf` longtext COLLATE utf8mb4_unicode_ci COMMENT '回复内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服务确认';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fuwuqueren`
--

LOCK TABLES `fuwuqueren` WRITE;
/*!40000 ALTER TABLE `fuwuqueren` DISABLE KEYS */;
INSERT INTO `fuwuqueren` VALUES (1,'2026-04-28 01:33:45','预约编号1','项目编号1','冰箱消毒清洁','家电清洗类',100,'2026-04-28 09:33:45','upload/fuwuqueren_冰箱消毒清洁1.jpg,upload/fuwuqueren_冰箱消毒清洁2.jpg,upload/fuwuqueren_冰箱消毒清洁3.jpg',10,'服务评价1','刘敏','16','003','赵子轩','已结算',''),(2,'2026-04-28 01:33:45','预约编号2','项目编号2','家庭餐上门制作','餐饮服务类',150,'2026-04-28 09:33:45','upload/fuwuqueren_家庭餐上门制作1.jpg,upload/fuwuqueren_家庭餐上门制作2.jpg,upload/fuwuqueren_家庭餐上门制作3.jpg',1,'服务评价2','周凯','17','002','王强','已结算',''),(3,'2026-04-28 01:33:45','预约编号3','项目编号3','厨房深度清洁','深度保洁类',200,'2026-04-28 09:33:45','upload/fuwuqueren_厨房深度清洁1.jpg,upload/fuwuqueren_厨房深度清洁2.jpg,upload/fuwuqueren_厨房深度清洁3.jpg',4,'服务评价3','张强','11','005','林小雨','已结算',''),(4,'2026-04-28 01:33:45','预约编号4','项目编号4','房屋收纳整理','空间规划类',260,'2026-04-28 09:33:45','upload/fuwuqueren_房屋收纳整理1.jpg,upload/fuwuqueren_房屋收纳整理2.jpg,upload/fuwuqueren_房屋收纳整理3.jpg',8,'服务评价4','赵阳','15','006','孙伟','已结算',''),(5,'2026-04-28 01:33:45','预约编号5','项目编号5','窗帘上门清洗','布艺清洁类',180,'2026-04-28 09:33:45','upload/fuwuqueren_窗帘上门清洗1.jpg,upload/fuwuqueren_窗帘上门清洗2.jpg,upload/fuwuqueren_窗帘上门清洗3.jpg',2,'服务评价5','李娜','12','004','杨洁','已结算',''),(6,'2026-04-28 01:33:45','预约编号6','项目编号6','母婴日常照护','母婴护理类',300,'2026-04-28 09:33:45','upload/fuwuqueren_母婴日常照护1.jpg,upload/fuwuqueren_母婴日常照护2.jpg,upload/fuwuqueren_母婴日常照护3.jpg',7,'服务评价6','陈曦','14','001','陈浩','已结算',''),(7,'2026-04-28 01:33:45','预约编号7','项目编号7','日常居家保洁','日常保洁类',120,'2026-04-28 09:33:45','upload/fuwuqueren_日常居家保洁1.jpg,upload/fuwuqueren_日常居家保洁2.jpg,upload/fuwuqueren_日常居家保洁3.jpg',9,'服务评价7','吴芳','18','007','徐婷','已结算',''),(8,'2026-04-28 01:33:45','预约编号8','项目编号8','油烟机专项清洗','家电清洗类',150,'2026-04-28 09:33:45','upload/fuwuqueren_油烟机专项清洗1.jpg,upload/fuwuqueren_油烟机专项清洗2.jpg,upload/fuwuqueren_油烟机专项清洗3.jpg',6,'服务评价8','王浩','13','008','张明','已结算','');
/*!40000 ALTER TABLE `fuwuqueren` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fuwurenyuan`
--

DROP TABLE IF EXISTS `fuwurenyuan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fuwurenyuan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `yuangongzhanghao` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '员工账号',
  `mima` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `yuangongxingming` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '员工姓名',
  `touxiang` longtext COLLATE utf8mb4_unicode_ci COMMENT '头像',
  `fuwujineng` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务技能',
  `fuwuzhuangtai` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务状态',
  `shouyi` double DEFAULT NULL COMMENT '收益',
  `zizhizhengshu` longtext COLLATE utf8mb4_unicode_ci COMMENT '资质证书',
  `fuwufanwei` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务范围',
  `fuwuleixing` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务类型',
  `mobile` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `status` int(11) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `yuangongzhanghao` (`yuangongzhanghao`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服务人员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fuwurenyuan`
--

LOCK TABLES `fuwurenyuan` WRITE;
/*!40000 ALTER TABLE `fuwurenyuan` DISABLE KEYS */;
INSERT INTO `fuwurenyuan` VALUES (21,'2026-04-28 01:33:45','16','123456','刘敏','upload/fuwurenyuan_touxiang1.jpg','无化学残留','空闲',29,'','窗帘清洗','家电清洗类','13823888881',0),(22,'2026-04-28 01:33:45','17','123456','周凯','upload/fuwurenyuan_touxiang2.jpg','营养搭配定制','空闲',25,'','搬家整理','餐饮服务类','13823888882',0),(23,'2026-04-28 01:33:45','11','123456','张强','upload/fuwurenyuan_touxiang3.jpg','高温去油不留痕','空闲',9,'','洗衣机清洗','深度保洁类','13823888883',0),(24,'2026-04-28 01:33:45','15','123456','赵阳','upload/fuwurenyuan_touxiang4.jpg','空间最大化利用','空闲',18,'','日常代购','空间规划类','13823888884',0),(25,'2026-04-28 01:33:45','12','123456','李娜','upload/fuwurenyuan_touxiang5.jpg','免拆洗省时省力','空闲',4,'','植物养护','布艺清洁类','13823888885',0),(26,'2026-04-28 01:33:45','14','123456','陈曦','upload/fuwurenyuan_touxiang6.jpg','持证月嫂经验足','空闲',26,'','空调清洗','母婴护理类','13823888886',0),(27,'2026-04-28 01:33:45','18','123456','吴芳','upload/fuwurenyuan_touxiang7.jpg','自带工具无二次污染','空闲',20,'','衣物湿洗','日常保洁类','13823888887',0),(28,'2026-04-28 01:33:45','13','123456','王浩','upload/fuwurenyuan_touxiang8.jpg','深度除油保养','空闲',17,'','收纳规划','家电清洗类','13823888888',0);
/*!40000 ALTER TABLE `fuwurenyuan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fuwuxinxi`
--

DROP TABLE IF EXISTS `fuwuxinxi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fuwuxinxi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `xiangmubianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '项目编号',
  `xiangmumingcheng` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '项目名称',
  `xiangmutupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '项目图片',
  `fuwuleixing` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务类型',
  `fuwufeiyong` double DEFAULT NULL COMMENT '服务费用',
  `fuwuneirong` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务内容',
  `fuwushuoming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务说明',
  `weizhi` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '位置',
  `yingyeshijian` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '营业时间',
  `lianxidianhua` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `onshelves` int(11) DEFAULT NULL COMMENT '上下架',
  `xiaoliang` int(11) DEFAULT NULL COMMENT '销量',
  `thumbsupnum` int(11) DEFAULT '0' COMMENT '赞',
  `crazilynum` int(11) DEFAULT '0' COMMENT '踩',
  `clicktime` datetime DEFAULT NULL COMMENT '最近点击时间',
  `discussnum` int(11) DEFAULT '0' COMMENT '评论数',
  `totalscore` double DEFAULT '0' COMMENT '评分',
  `storeupnum` int(11) DEFAULT '0' COMMENT '收藏数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `xiangmubianhao` (`xiangmubianhao`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服务信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fuwuxinxi`
--

LOCK TABLES `fuwuxinxi` WRITE;
/*!40000 ALTER TABLE `fuwuxinxi` DISABLE KEYS */;
INSERT INTO `fuwuxinxi` VALUES (1,'2026-04-28 01:33:45','1111111111','冰箱消毒清洁','upload/fuwuxinxi_冰箱消毒清洁1.jpg,upload/fuwuxinxi_冰箱消毒清洁2.jpg,upload/fuwuxinxi_冰箱消毒清洁3.jpg','家电清洗类',100,'内胆隔板消毒除味','对冰箱内胆隔板抽屉进行消毒除味清洁去除食物残留防止细菌滋生','位置1','8:00-24:00','13456789012',1,1,1,1,'2026-04-28 09:33:45',0,0,1),(2,'2026-04-28 01:33:45','2222222222','家庭餐上门制作','upload/fuwuxinxi_家庭餐上门制作1.jpg,upload/fuwuxinxi_家庭餐上门制作2.jpg,upload/fuwuxinxi_家庭餐上门制作3.jpg','餐饮服务类',150,'3餐食材采购制作','根据客户口味和营养需求采购新鲜食材上门制作一日三餐确保健康美味','位置2','8:00-24:00','13545678901',1,2,2,2,'2026-04-28 09:33:45',0,0,2),(3,'2026-04-28 01:33:45','3333333333','厨房深度清洁','upload/fuwuxinxi_厨房深度清洁1.jpg,upload/fuwuxinxi_厨房深度清洁2.jpg,upload/fuwuxinxi_厨房深度清洁3.jpg','深度保洁类',200,'灶台墙面橱柜油污清理','针对厨房灶台墙面橱柜油污进行高温溶解清理去除顽固油渍擦拭台面整理厨具还原厨房洁净环境','位置3','8:00-24:00','13490123456',1,3,3,3,'2026-04-28 09:33:45',0,0,3),(4,'2026-04-28 01:33:45','4444444444','房屋收纳整理','upload/fuwuxinxi_房屋收纳整理1.jpg,upload/fuwuxinxi_房屋收纳整理2.jpg,upload/fuwuxinxi_房屋收纳整理3.jpg','空间规划类',260,'衣物书籍用品分类整理','对家庭衣物书籍用品进行分类整理规划收纳空间最大化利用家居空间','位置4','8:00-24:00','13489012345',1,4,4,4,'2026-04-28 09:33:45',0,0,4),(5,'2026-04-28 01:33:45','5555555555','窗帘上门清洗','upload/fuwuxinxi_窗帘上门清洗1.jpg,upload/fuwuxinxi_窗帘上门清洗2.jpg,upload/fuwuxinxi_窗帘上门清洗3.jpg','布艺清洁类',180,'免拆洗除尘消毒','采用免拆洗技术上门为窗帘进行除尘清洗消毒无需拆卸安装省时省力保护窗帘','位置5','8:00-24:00','13589012345',1,5,5,5,'2026-04-28 09:33:45',0,0,5),(6,'2026-04-28 01:33:45','6666666666','母婴日常照护','upload/fuwuxinxi_母婴日常照护1.jpg,upload/fuwuxinxi_母婴日常照护2.jpg,upload/fuwuxinxi_母婴日常照护3.jpg','母婴护理类',300,'新生儿喂奶拍嗝洗澡抚触','持证月嫂提供新生儿喂奶拍嗝洗澡抚触协助产妇进行产后恢复观察母婴健康状况','位置6','8:00-24:00','13523456789',1,6,6,6,'2026-04-28 09:33:45',0,0,6),(7,'2026-04-28 01:33:45','7777777777','日常居家保洁','upload/fuwuxinxi_日常居家保洁1.jpg,upload/fuwuxinxi_日常居家保洁2.jpg,upload/fuwuxinxi_日常居家保洁3.jpg','日常保洁类',120,'客厅卧室厨房基础清洁','负责客厅卧室厨房基础清洁擦拭桌面地面除尘自带清洁工具无需客户准备确保环境整洁无二次污染','位置7','8:00-24:00','13556789012',1,7,7,7,'2026-04-28 09:33:45',0,0,7),(8,'2026-04-28 01:33:45','8888888888','油烟机专项清洗','upload/fuwuxinxi_油烟机专项清洗1.jpg,upload/fuwuxinxi_油烟机专项清洗2.jpg,upload/fuwuxinxi_油烟机专项清洗3.jpg','家电清洗类',150,'内外部油污拆解清洁','拆解油烟机内部部件进行深度除油清洁保养恢复油烟机吸力延长使用年限','位置8','8:00-24:00','13501234567',1,8,8,8,'2026-04-28 09:33:45',0,0,8);
/*!40000 ALTER TABLE `fuwuxinxi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fuwuyuyue`
--

DROP TABLE IF EXISTS `fuwuyuyue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fuwuyuyue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `yuyuebianhao` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '预约编号',
  `xiangmubianhao` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目编号',
  `xiangmumingcheng` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目名称',
  `xiangmutupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '项目图片',
  `fuwuleixing` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务类型',
  `fuwufeiyong` double DEFAULT NULL COMMENT '服务费用',
  `weizhi` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '位置',
  `fuwuneirong` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务内容',
  `yuyueriqi` date NOT NULL COMMENT '预约日期',
  `yuyueshiduan` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '预约时段',
  `yuyueshijian` datetime DEFAULT NULL COMMENT '预约时间',
  `dingdanzhuangtai` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单状态',
  `yonghuzhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户账号',
  `yonghuxingming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户姓名',
  `fuwudizhi` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务地址',
  `shoujihao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `ispay` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '未支付' COMMENT '是否支付',
  PRIMARY KEY (`id`),
  UNIQUE KEY `yuyuebianhao` (`yuyuebianhao`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服务预约';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fuwuyuyue`
--

LOCK TABLES `fuwuyuyue` WRITE;
/*!40000 ALTER TABLE `fuwuyuyue` DISABLE KEYS */;
INSERT INTO `fuwuyuyue` VALUES (1,'2026-04-28 01:33:45','1111111111','项目编号1','冰箱消毒清洁','upload/fuwuyuyue_冰箱消毒清洁1.jpg,upload/fuwuyuyue_冰箱消毒清洁2.jpg,upload/fuwuyuyue_冰箱消毒清洁3.jpg','家电清洗类',100,'位置1','内胆隔板消毒除味','2026-04-28','上午','2026-04-28 09:33:45','已派单','003','赵子轩','服务地址1','13823888881','已支付'),(2,'2026-04-28 01:33:45','2222222222','项目编号2','家庭餐上门制作','upload/fuwuyuyue_家庭餐上门制作1.jpg,upload/fuwuyuyue_家庭餐上门制作2.jpg,upload/fuwuyuyue_家庭餐上门制作3.jpg','餐饮服务类',150,'位置2','3餐食材采购制作','2026-04-28','上午','2026-04-28 09:33:45','已派单','002','王强','服务地址2','13823888882','已支付'),(3,'2026-04-28 01:33:45','3333333333','项目编号3','厨房深度清洁','upload/fuwuyuyue_厨房深度清洁1.jpg,upload/fuwuyuyue_厨房深度清洁2.jpg,upload/fuwuyuyue_厨房深度清洁3.jpg','深度保洁类',200,'位置3','灶台墙面橱柜油污清理','2026-04-28','上午','2026-04-28 09:33:45','已派单','005','林小雨','服务地址3','13823888883','已支付'),(4,'2026-04-28 01:33:45','4444444444','项目编号4','房屋收纳整理','upload/fuwuyuyue_房屋收纳整理1.jpg,upload/fuwuyuyue_房屋收纳整理2.jpg,upload/fuwuyuyue_房屋收纳整理3.jpg','空间规划类',260,'位置4','衣物书籍用品分类整理','2026-04-28','上午','2026-04-28 09:33:45','已派单','006','孙伟','服务地址4','13823888884','已支付'),(5,'2026-04-28 01:33:45','5555555555','项目编号5','窗帘上门清洗','upload/fuwuyuyue_窗帘上门清洗1.jpg,upload/fuwuyuyue_窗帘上门清洗2.jpg,upload/fuwuyuyue_窗帘上门清洗3.jpg','布艺清洁类',180,'位置5','免拆洗除尘消毒','2026-04-28','上午','2026-04-28 09:33:45','已派单','004','杨洁','服务地址5','13823888885','已支付'),(6,'2026-04-28 01:33:45','6666666666','项目编号6','母婴日常照护','upload/fuwuyuyue_母婴日常照护1.jpg,upload/fuwuyuyue_母婴日常照护2.jpg,upload/fuwuyuyue_母婴日常照护3.jpg','母婴护理类',300,'位置6','新生儿喂奶拍嗝洗澡抚触','2026-04-28','上午','2026-04-28 09:33:45','已派单','001','陈浩','服务地址6','13823888886','已支付'),(7,'2026-04-28 01:33:45','7777777777','项目编号7','日常居家保洁','upload/fuwuyuyue_日常居家保洁1.jpg,upload/fuwuyuyue_日常居家保洁2.jpg,upload/fuwuyuyue_日常居家保洁3.jpg','日常保洁类',120,'位置7','客厅卧室厨房基础清洁','2026-04-28','上午','2026-04-28 09:33:45','已派单','007','徐婷','服务地址7','13823888887','已支付'),(8,'2026-04-28 01:33:45','8888888888','项目编号8','油烟机专项清洗','upload/fuwuyuyue_油烟机专项清洗1.jpg,upload/fuwuyuyue_油烟机专项清洗2.jpg,upload/fuwuyuyue_油烟机专项清洗3.jpg','家电清洗类',150,'位置8','内外部油污拆解清洁','2026-04-28','上午','2026-04-28 09:33:45','已派单','008','张明','服务地址8','13823888888','已支付');
/*!40000 ALTER TABLE `fuwuyuyue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `menujson` longtext COLLATE utf8mb4_unicode_ci COMMENT '菜单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'2026-04-28 01:33:46','[{\"backMenu\":[{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"支付\",\"预约金额统计\",\"预约服务类型统计\",\"导出\",\"首页统计\",\"派单\",\"取消\",\"地图\"],\"appFrontIcon\":\"cuIcon-vip\",\"buttons\":[\"查看\",\"支付\",\"取消\"],\"menu\":\"服务预约\",\"menuJump\":\"列表\",\"tableName\":\"fuwuyuyue\"}],\"menu\":\"服务预约管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"审核\",\"支付\"],\"appFrontIcon\":\"cuIcon-shop\",\"buttons\":[\"查看\"],\"menu\":\"取消预约\",\"menuJump\":\"列表\",\"tableName\":\"quxiaoyuyue\"}],\"menu\":\"取消预约管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"审核\",\"私聊\",\"服务反馈\",\"地图\"],\"appFrontIcon\":\"cuIcon-shop\",\"buttons\":[\"查看\",\"地图\"],\"menu\":\"服务订单\",\"menuJump\":\"列表\",\"tableName\":\"fuwudingdan\"}],\"menu\":\"服务订单管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"确认\"],\"appFrontIcon\":\"cuIcon-rank\",\"buttons\":[\"查看\",\"确认\"],\"menu\":\"服务记录\",\"menuJump\":\"列表\",\"tableName\":\"fuwujilu\"}],\"menu\":\"服务记录管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"审核\",\"服务评分统计\",\"导出\",\"结算\"],\"appFrontIcon\":\"cuIcon-time\",\"buttons\":[\"查看\"],\"menu\":\"服务确认\",\"menuJump\":\"列表\",\"tableName\":\"fuwuqueren\"}],\"menu\":\"服务确认管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-shop\",\"buttons\":[\"查看\"],\"menu\":\"我的评论\",\"menuJump\":\"81\",\"tableName\":\"storeup\"}],\"menu\":\"我的评论管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-favor\",\"buttons\":[\"查看\"],\"menu\":\"我的点赞\",\"menuJump\":\"21\",\"tableName\":\"storeup\"}],\"menu\":\"我的点赞管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-favor\",\"buttons\":[\"查看\"],\"menu\":\"我的收藏\",\"menuJump\":\"1\",\"tableName\":\"storeup\"}],\"menu\":\"我的收藏管理\"}],\"frontMenu\":[{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"服务类型占比\",\"服务项目收藏量\",\"服务项目评分值统计\",\"服务项目点赞量统计\",\"查看评论\",\"删除评论\",\"首页统计\",\"立即预约\",\"地图\"],\"appFrontIcon\":\"cuIcon-skin\",\"buttons\":[\"立即预约\",\"地图\"],\"menu\":\"服务信息列表\",\"menuJump\":\"列表\",\"tableName\":\"fuwuxinxi\"}],\"menu\":\"服务信息模块\"}],\"hasBackLogin\":\"否\",\"hasBackRegister\":\"否\",\"hasFrontLogin\":\"是\",\"hasFrontRegister\":\"是\",\"roleName\":\"用户\",\"tableName\":\"yonghu\"},{\"backMenu\":[{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"审核\",\"私聊\",\"服务反馈\",\"地图\"],\"appFrontIcon\":\"cuIcon-shop\",\"buttons\":[\"查看\",\"审核\",\"服务反馈\",\"地图\",\"私聊\"],\"menu\":\"服务订单\",\"menuJump\":\"列表\",\"tableName\":\"fuwudingdan\"}],\"menu\":\"服务订单管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"确认\"],\"appFrontIcon\":\"cuIcon-rank\",\"buttons\":[\"查看\"],\"menu\":\"服务记录\",\"menuJump\":\"列表\",\"tableName\":\"fuwujilu\"}],\"menu\":\"服务记录管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"审核\",\"服务评分统计\",\"导出\",\"结算\"],\"appFrontIcon\":\"cuIcon-time\",\"buttons\":[\"查看\",\"审核\",\"服务评分统计\"],\"menu\":\"服务确认\",\"menuJump\":\"列表\",\"tableName\":\"fuwuqueren\"}],\"menu\":\"服务确认管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"提现申请\"],\"appFrontIcon\":\"cuIcon-flashlightopen\",\"buttons\":[\"查看\",\"提现申请\"],\"menu\":\"财务结算\",\"menuJump\":\"列表\",\"tableName\":\"caiwujiesuan\"}],\"menu\":\"财务结算管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"月收益统计\",\"导出\",\"首页统计\"],\"appFrontIcon\":\"cuIcon-brand\",\"buttons\":[\"查看\",\"月收益统计\"],\"menu\":\"收入明细\",\"menuJump\":\"列表\",\"tableName\":\"shourumingxi\"}],\"menu\":\"收入明细管理\"}],\"frontMenu\":[{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"服务类型占比\",\"服务项目收藏量\",\"服务项目评分值统计\",\"服务项目点赞量统计\",\"查看评论\",\"删除评论\",\"首页统计\",\"立即预约\",\"地图\"],\"appFrontIcon\":\"cuIcon-skin\",\"buttons\":[\"立即预约\",\"地图\"],\"menu\":\"服务信息列表\",\"menuJump\":\"列表\",\"tableName\":\"fuwuxinxi\"}],\"menu\":\"服务信息模块\"}],\"hasBackLogin\":\"否\",\"hasBackRegister\":\"否\",\"hasFrontLogin\":\"是\",\"hasFrontRegister\":\"是\",\"roleName\":\"服务人员\",\"tableName\":\"fuwurenyuan\"},{\"backMenu\":[{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"首页总数\",\"私聊\"],\"appFrontIcon\":\"cuIcon-brand\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"首页总数\"],\"menu\":\"用户\",\"menuJump\":\"列表\",\"tableName\":\"yonghu\"}],\"menu\":\"用户管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"私聊\"],\"appFrontIcon\":\"cuIcon-pay\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"menu\":\"服务人员\",\"menuJump\":\"列表\",\"tableName\":\"fuwurenyuan\"}],\"menu\":\"服务人员管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-pay\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"menu\":\"服务类型\",\"menuJump\":\"列表\",\"tableName\":\"fuwuleixing\"}],\"menu\":\"服务类型管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"服务类型占比\",\"服务项目收藏量\",\"服务项目评分值统计\",\"服务项目点赞量统计\",\"查看评论\",\"删除评论\",\"首页统计\",\"立即预约\",\"地图\"],\"appFrontIcon\":\"cuIcon-goods\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"服务类型占比\",\"服务项目收藏量\",\"服务项目评论量统计\",\"服务项目点赞量统计\",\"查看评论\",\"删除评论\",\"首页统计\",\"服务项目评分值统计\"],\"menu\":\"服务信息\",\"menuJump\":\"列表\",\"tableName\":\"fuwuxinxi\"}],\"menu\":\"服务信息管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"支付\",\"预约金额统计\",\"预约服务类型统计\",\"导出\",\"首页统计\",\"派单\",\"取消\",\"地图\"],\"appFrontIcon\":\"cuIcon-vip\",\"buttons\":[\"查看\",\"修改\",\"删除\",\"预约金额统计\",\"预约服务类型统计\",\"导出\",\"首页统计\",\"派单\",\"地图\"],\"menu\":\"服务预约\",\"menuJump\":\"列表\",\"tableName\":\"fuwuyuyue\"}],\"menu\":\"服务预约管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"审核\",\"支付\"],\"appFrontIcon\":\"cuIcon-shop\",\"buttons\":[\"查看\",\"修改\",\"删除\",\"审核\",\"支付\"],\"menu\":\"取消预约\",\"menuJump\":\"列表\",\"tableName\":\"quxiaoyuyue\"}],\"menu\":\"取消预约管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"审核\",\"私聊\",\"服务反馈\",\"地图\"],\"appFrontIcon\":\"cuIcon-shop\",\"buttons\":[\"查看\",\"修改\",\"删除\",\"地图\"],\"menu\":\"服务订单\",\"menuJump\":\"列表\",\"tableName\":\"fuwudingdan\"}],\"menu\":\"服务订单管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"确认\"],\"appFrontIcon\":\"cuIcon-rank\",\"buttons\":[\"查看\",\"删除\",\"修改\"],\"menu\":\"服务记录\",\"menuJump\":\"列表\",\"tableName\":\"fuwujilu\"}],\"menu\":\"服务记录管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"审核\",\"服务评分统计\",\"导出\",\"结算\"],\"appFrontIcon\":\"cuIcon-time\",\"buttons\":[\"查看\",\"修改\",\"删除\",\"导出\",\"结算\",\"服务评分统计\"],\"menu\":\"服务确认\",\"menuJump\":\"列表\",\"tableName\":\"fuwuqueren\"}],\"menu\":\"服务确认管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"提现申请\"],\"appFrontIcon\":\"cuIcon-flashlightopen\",\"buttons\":[\"查看\",\"修改\",\"删除\"],\"menu\":\"财务结算\",\"menuJump\":\"列表\",\"tableName\":\"caiwujiesuan\"}],\"menu\":\"财务结算管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"月收益统计\",\"导出\",\"首页统计\"],\"appFrontIcon\":\"cuIcon-brand\",\"buttons\":[\"查看\",\"删除\",\"月收益统计\",\"导出\",\"首页统计\",\"修改\"],\"menu\":\"收入明细\",\"menuJump\":\"列表\",\"tableName\":\"shourumingxi\"}],\"menu\":\"收入明细管理\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-goods\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"menu\":\"通知公告\",\"menuJump\":\"列表\",\"tableName\":\"news\"}],\"menu\":\"通知公告管理\"},{\"child\":[{\"allButtons\":[\"查看\",\"编辑名称\",\"编辑父级\",\"编辑权限\",\"删除\"],\"appFrontIcon\":\"cuIcon-qrcode\",\"buttons\":[\"查看\",\"编辑名称\",\"编辑父级\",\"编辑权限\",\"删除\"],\"menu\":\"菜单列表\",\"tableName\":\"menu\"},{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-full\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"menu\":\"智能助手\",\"tableName\":\"chathelper\"},{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-service\",\"buttons\":[\"查看\",\"删除\"],\"menu\":\"在线客服\",\"tableName\":\"chat\"},{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-paint\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"menu\":\"轮播图管理\",\"tableName\":\"config/1\"},{\"allButtons\":[\"查看\",\"删除\"],\"appFrontIcon\":\"cuIcon-list\",\"buttons\":[\"查看\",\"删除\"],\"menu\":\"系统日志\",\"tableName\":\"syslog\"}],\"menu\":\"系统管理\"}],\"frontMenu\":[{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"服务类型占比\",\"服务项目收藏量\",\"服务项目评分值统计\",\"服务项目点赞量统计\",\"查看评论\",\"删除评论\",\"首页统计\",\"立即预约\",\"地图\"],\"appFrontIcon\":\"cuIcon-skin\",\"buttons\":[\"立即预约\",\"地图\"],\"menu\":\"服务信息列表\",\"menuJump\":\"列表\",\"tableName\":\"fuwuxinxi\"}],\"menu\":\"服务信息模块\"}],\"hasBackLogin\":\"是\",\"hasBackRegister\":\"否\",\"hasFrontLogin\":\"否\",\"hasFrontRegister\":\"否\",\"roleName\":\"管理员\",\"tableName\":\"users\"}]');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布人',
  `headportrait` longtext COLLATE utf8mb4_unicode_ci COMMENT '头像',
  `introduction` longtext COLLATE utf8mb4_unicode_ci COMMENT '简介',
  `picture` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知公告';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,'2026-04-28 01:33:46','优秀家政服务人员表彰公告','优秀家政服务人员表彰公告','发布人1','upload/news_headportrait1.jpg','优秀家政服务人员表彰公告','upload/news_picture1.jpg'),(2,'2026-04-28 01:33:46','家政服务投诉处理流程公示','家政服务投诉处理流程公示','发布人2','upload/news_headportrait2.jpg','家政服务投诉处理流程公示','upload/news_picture2.jpg'),(3,'2026-04-28 01:33:46','家政服务收费标准调整公示','家政服务收费标准调整公示','发布人3','upload/news_headportrait3.jpg','家政服务收费标准调整公示','upload/news_picture3.jpg'),(4,'2026-04-28 01:33:46','长期家政服务套餐续费福利通知','长期家政服务套餐续费福利通知','发布人4','upload/news_headportrait4.jpg','长期家政服务套餐续费福利通知','upload/news_picture4.jpg'),(5,'2026-04-28 01:33:46','暴雨天气家政服务上门注意事项','暴雨天气家政服务上门注意事项','发布人5','upload/news_headportrait5.jpg','暴雨天气家政服务上门注意事项','upload/news_picture5.jpg'),(6,'2026-04-28 01:33:46','钟点工服务时段调整通知','钟点工服务时段调整通知','发布人6','upload/news_headportrait6.jpg','钟点工服务时段调整通知','upload/news_picture6.jpg'),(7,'2026-04-28 01:33:46','春节期间家政服务预约须知','春节期间家政服务预约须知','发布人7','upload/news_headportrait7.jpg','春节期间家政服务预约须知','upload/news_picture7.jpg'),(8,'2026-04-28 01:33:46','居家保洁服务项目新增通知','居家保洁服务项目新增通知','发布人8','upload/news_headportrait8.jpg','居家保洁服务项目新增通知','upload/news_picture8.jpg');
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quxiaoyuyue`
--

DROP TABLE IF EXISTS `quxiaoyuyue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quxiaoyuyue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `quxiaobianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '取消编号',
  `yuyuebianhao` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '预约编号',
  `xiangmubianhao` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目编号',
  `xiangmumingcheng` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目名称',
  `xiangmutupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '项目图片',
  `fuwuleixing` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务类型',
  `fuwufeiyong` double DEFAULT NULL COMMENT '服务费用',
  `yuyueriqi` date DEFAULT NULL COMMENT '预约日期',
  `yuyueshiduan` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预约时段',
  `yonghuzhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户账号',
  `yonghuxingming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户姓名',
  `shoujihao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `quxiaoshuoming` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '取消说明',
  `quxiaoshijian` datetime DEFAULT NULL COMMENT '取消时间',
  `sfsh` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '待审核' COMMENT '是否审核',
  `shhf` longtext COLLATE utf8mb4_unicode_ci COMMENT '审核回复',
  `ispay` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '未支付' COMMENT '是否支付',
  PRIMARY KEY (`id`),
  UNIQUE KEY `yuyuebianhao` (`yuyuebianhao`),
  UNIQUE KEY `quxiaobianhao` (`quxiaobianhao`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='取消预约';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quxiaoyuyue`
--

LOCK TABLES `quxiaoyuyue` WRITE;
/*!40000 ALTER TABLE `quxiaoyuyue` DISABLE KEYS */;
INSERT INTO `quxiaoyuyue` VALUES (1,'2026-04-28 01:33:45','1111111111','预约编号1','项目编号1','冰箱消毒清洁','upload/quxiaoyuyue_冰箱消毒清洁1.jpg,upload/quxiaoyuyue_冰箱消毒清洁2.jpg,upload/quxiaoyuyue_冰箱消毒清洁3.jpg','家电清洗类',100,'2026-04-28','预约时段1','003','赵子轩','13823888881','突发感冒发烧身体乏力','2026-04-28 09:33:45','是','','已支付'),(2,'2026-04-28 01:33:45','2222222222','预约编号2','项目编号2','家庭餐上门制作','upload/quxiaoyuyue_家庭餐上门制作1.jpg,upload/quxiaoyuyue_家庭餐上门制作2.jpg,upload/quxiaoyuyue_家庭餐上门制作3.jpg','餐饮服务类',150,'2026-04-28','预约时段2','002','王强','13823888882','突发肠胃炎身体不适','2026-04-28 09:33:45','是','','已支付'),(3,'2026-04-28 01:33:45','3333333333','预约编号3','项目编号3','厨房深度清洁','upload/quxiaoyuyue_厨房深度清洁1.jpg,upload/quxiaoyuyue_厨房深度清洁2.jpg,upload/quxiaoyuyue_厨房深度清洁3.jpg','深度保洁类',200,'2026-04-28','预约时段3','005','林小雨','13823888883','临时被安排出差','2026-04-28 09:33:45','是','','已支付'),(4,'2026-04-28 01:33:45','4444444444','预约编号4','项目编号4','房屋收纳整理','upload/quxiaoyuyue_房屋收纳整理1.jpg,upload/quxiaoyuyue_房屋收纳整理2.jpg,upload/quxiaoyuyue_房屋收纳整理3.jpg','空间规划类',260,'2026-04-28','预约时段4','006','孙伟','13823888884','行程计划变更无法前往','2026-04-28 09:33:45','是','','已支付'),(5,'2026-04-28 01:33:45','5555555555','预约编号5','项目编号5','窗帘上门清洗','upload/quxiaoyuyue_窗帘上门清洗1.jpg,upload/quxiaoyuyue_窗帘上门清洗2.jpg,upload/quxiaoyuyue_窗帘上门清洗3.jpg','布艺清洁类',180,'2026-04-28','预约时段5','004','杨洁','13823888885','家中临时有急事需处理','2026-04-28 09:33:45','是','','已支付'),(6,'2026-04-28 01:33:45','6666666666','预约编号6','项目编号6','母婴日常照护','upload/quxiaoyuyue_母婴日常照护1.jpg,upload/quxiaoyuyue_母婴日常照护2.jpg,upload/quxiaoyuyue_母婴日常照护3.jpg','母婴护理类',300,'2026-04-28','预约时段6','001','陈浩','13823888886','通勤工具故障无法出行','2026-04-28 09:33:45','是','','已支付'),(7,'2026-04-28 01:33:45','7777777777','预约编号7','项目编号7','日常居家保洁','upload/quxiaoyuyue_日常居家保洁1.jpg,upload/quxiaoyuyue_日常居家保洁2.jpg,upload/quxiaoyuyue_日常居家保洁3.jpg','日常保洁类',120,'2026-04-28','预约时段7','007','徐婷','13823888887','突发临时加班无法按时到场','2026-04-28 09:33:45','是','','已支付'),(8,'2026-04-28 01:33:45','8888888888','预约编号8','项目编号8','油烟机专项清洗','upload/quxiaoyuyue_油烟机专项清洗1.jpg,upload/quxiaoyuyue_油烟机专项清洗2.jpg,upload/quxiaoyuyue_油烟机专项清洗3.jpg','家电清洗类',150,'2026-04-28','预约时段8','008','张明','13823888888','记错预约时间错过场次','2026-04-28 09:33:45','是','','已支付');
/*!40000 ALTER TABLE `quxiaoyuyue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shourumingxi`
--

DROP TABLE IF EXISTS `shourumingxi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shourumingxi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `shourubianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收入编号',
  `xiangmubianhao` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目编号',
  `xiangmumingcheng` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目名称',
  `xiangmutupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '项目图片',
  `fuwuleixing` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务类型',
  `shouyi` double DEFAULT NULL COMMENT '收益',
  `yuangongzhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '员工账号',
  `yuangongxingming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '员工姓名',
  `tixianriqi` datetime DEFAULT NULL COMMENT '提现日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `shourubianhao` (`shourubianhao`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收入明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shourumingxi`
--

LOCK TABLES `shourumingxi` WRITE;
/*!40000 ALTER TABLE `shourumingxi` DISABLE KEYS */;
INSERT INTO `shourumingxi` VALUES (1,'2026-04-28 01:33:45','1111111111','项目编号1','冰箱消毒清洁','upload/shourumingxi_冰箱消毒清洁1.jpg,upload/shourumingxi_冰箱消毒清洁2.jpg,upload/shourumingxi_冰箱消毒清洁3.jpg','家电清洗类',29,'16','刘敏','2025-06-05 19:24:10'),(2,'2026-04-28 01:33:45','2222222222','项目编号2','家庭餐上门制作','upload/shourumingxi_家庭餐上门制作1.jpg,upload/shourumingxi_家庭餐上门制作2.jpg,upload/shourumingxi_家庭餐上门制作3.jpg','餐饮服务类',25,'17','周凯','2025-03-14 08:45:12'),(3,'2026-04-28 01:33:45','3333333333','项目编号3','厨房深度清洁','upload/shourumingxi_厨房深度清洁1.jpg,upload/shourumingxi_厨房深度清洁2.jpg,upload/shourumingxi_厨房深度清洁3.jpg','深度保洁类',9,'11','张强','2025-01-18 10:53:24'),(4,'2026-04-28 01:33:45','4444444444','项目编号4','房屋收纳整理','upload/shourumingxi_房屋收纳整理1.jpg,upload/shourumingxi_房屋收纳整理2.jpg,upload/shourumingxi_房屋收纳整理3.jpg','空间规划类',18,'15','赵阳','2023-01-03 08:12:45'),(5,'2026-04-28 01:33:45','5555555555','项目编号5','窗帘上门清洗','upload/shourumingxi_窗帘上门清洗1.jpg,upload/shourumingxi_窗帘上门清洗2.jpg,upload/shourumingxi_窗帘上门清洗3.jpg','布艺清洁类',4,'12','李娜','2025-08-23 13:41:26'),(6,'2026-04-28 01:33:45','6666666666','项目编号6','母婴日常照护','upload/shourumingxi_母婴日常照护1.jpg,upload/shourumingxi_母婴日常照护2.jpg,upload/shourumingxi_母婴日常照护3.jpg','母婴护理类',26,'14','陈曦','2024-03-20 09:51:32'),(7,'2026-04-28 01:33:45','7777777777','项目编号7','日常居家保洁','upload/shourumingxi_日常居家保洁1.jpg,upload/shourumingxi_日常居家保洁2.jpg,upload/shourumingxi_日常居家保洁3.jpg','日常保洁类',20,'18','吴芳','2025-07-16 07:58:35'),(8,'2026-04-28 01:33:46','8888888888','项目编号8','油烟机专项清洗','upload/shourumingxi_油烟机专项清洗1.jpg,upload/shourumingxi_油烟机专项清洗2.jpg,upload/shourumingxi_油烟机专项清洗3.jpg','家电清洗类',17,'13','王浩','2025-05-12 11:09:43');
/*!40000 ALTER TABLE `shourumingxi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smsregistercode`
--

DROP TABLE IF EXISTS `smsregistercode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smsregistercode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mobile` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机',
  `role` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色',
  `code` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '验证码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='短信验证码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smsregistercode`
--

LOCK TABLES `smsregistercode` WRITE;
/*!40000 ALTER TABLE `smsregistercode` DISABLE KEYS */;
INSERT INTO `smsregistercode` VALUES (1,'2026-04-28 01:33:46','手机1','角色1','验证码1'),(2,'2026-04-28 01:33:46','手机2','角色2','验证码2'),(3,'2026-04-28 01:33:46','手机3','角色3','验证码3'),(4,'2026-04-28 01:33:46','手机4','角色4','验证码4'),(5,'2026-04-28 01:33:46','手机5','角色5','验证码5'),(6,'2026-04-28 01:33:46','手机6','角色6','验证码6'),(7,'2026-04-28 01:33:46','手机7','角色7','验证码7'),(8,'2026-04-28 01:33:46','手机8','角色8','验证码8');
/*!40000 ALTER TABLE `smsregistercode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storeup`
--

DROP TABLE IF EXISTS `storeup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storeup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `refid` bigint(20) DEFAULT NULL COMMENT '外键id',
  `tablename` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '表名',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `picture` longtext COLLATE utf8mb4_unicode_ci COMMENT '图片',
  `type` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '类型',
  `inteltype` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推荐类型',
  `remark` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收藏表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storeup`
--

LOCK TABLES `storeup` WRITE;
/*!40000 ALTER TABLE `storeup` DISABLE KEYS */;
/*!40000 ALTER TABLE `storeup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syslog`
--

DROP TABLE IF EXISTS `syslog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syslog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `username` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `operation` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户操作',
  `method` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求方法',
  `params` longtext COLLATE utf8mb4_unicode_ci COMMENT '请求参数',
  `time` bigint(20) DEFAULT NULL COMMENT '请求时长(毫秒)',
  `ip` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'IP地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syslog`
--

LOCK TABLES `syslog` WRITE;
/*!40000 ALTER TABLE `syslog` DISABLE KEYS */;
/*!40000 ALTER TABLE `syslog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `tablename` varchar(100) DEFAULT NULL COMMENT '表名',
  `role` varchar(100) DEFAULT NULL COMMENT '角色',
  `token` varchar(500) NOT NULL COMMENT '密码',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `expiratedtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='token表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `username` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `role` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '管理员' COMMENT '角色',
  `image` longtext COLLATE utf8mb4_unicode_ci COMMENT '头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2026-04-28 01:33:46','admin','admin','管理员','upload/image1.jpg');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yonghu`
--

DROP TABLE IF EXISTS `yonghu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yonghu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `yonghuzhanghao` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
  `mima` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `yonghuxingming` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户姓名',
  `xingbie` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `touxiang` longtext COLLATE utf8mb4_unicode_ci COMMENT '头像',
  `mobile` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `status` int(11) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `yonghuzhanghao` (`yonghuzhanghao`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yonghu`
--

LOCK TABLES `yonghu` WRITE;
/*!40000 ALTER TABLE `yonghu` DISABLE KEYS */;
INSERT INTO `yonghu` VALUES (11,'2026-04-28 01:33:45','003','123456','赵子轩','女','upload/yonghu_touxiang1.jpg','13823888881',0),(12,'2026-04-28 01:33:45','002','123456','王强','男','upload/yonghu_touxiang2.jpg','13823888882',0),(13,'2026-04-28 01:33:45','005','123456','林小雨','女','upload/yonghu_touxiang3.jpg','13823888883',0),(14,'2026-04-28 01:33:45','006','123456','孙伟','男','upload/yonghu_touxiang4.jpg','13823888884',0),(15,'2026-04-28 01:33:45','004','123456','杨洁','女','upload/yonghu_touxiang5.jpg','13823888885',0),(16,'2026-04-28 01:33:45','001','123456','陈浩','女','upload/yonghu_touxiang6.jpg','13823888886',0),(17,'2026-04-28 01:33:45','007','123456','徐婷','男','upload/yonghu_touxiang7.jpg','13823888887',0),(18,'2026-04-28 01:33:45','008','123456','张明','男','upload/yonghu_touxiang8.jpg','13823888888',0);
/*!40000 ALTER TABLE `yonghu` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-28  9:36:56
