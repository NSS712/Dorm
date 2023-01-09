-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- 主机： db
-- 生成日期： 2022-12-05 20:26:01
-- 服务器版本： 5.7.36
-- PHP 版本： 7.4.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `dorm`
--

-- --------------------------------------------------------

--
-- 表的结构 `auth`
--

CREATE TABLE `auth` (
  `id` int(10) NOT NULL COMMENT '主键，自增',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `type` int(10) NOT NULL COMMENT '0-学号，1-邮箱，2-电话',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `uid` int(10) NOT NULL COMMENT 'user表中的uid',
  `add_time` int(11) NOT NULL DEFAULT '0' COMMENT '添加时间',
  `remarks` varchar(1000) NOT NULL COMMENT '备注，仅管理员可见',
  `is_del` int(10) NOT NULL DEFAULT '0' COMMENT '1代表删除',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '备用'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `auth`
--

INSERT INTO `auth` (`id`, `username`, `type`, `password`, `uid`, `add_time`, `remarks`, `is_del`, `status`) VALUES
(1, '1001', 1, 'a96736ba2ad14cd3301cd8cb011e27f8', 2201001, 0, '1', 0, 0),
(2, '1002', 1, 'a96736ba2ad14cd3301cd8cb011e27f8', 10, 0, '', 0, 0),
(3, '1003', 1, 'a96736ba2ad14cd3301cd8cb011e27f8', 3, 0, '', 0, 0),
(6, '1005', 1, 'a96736ba2ad14cd3301cd8cb011e27f8', 111111, 0, '1', 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `beds`
--

CREATE TABLE `beds` (
  `id` int(10) NOT NULL COMMENT '主键，自增',
  `uid` int(10) DEFAULT '0' COMMENT 'user表中的id',
  `room_id` int(10) NOT NULL COMMENT 'room表中的id',
  `name` varchar(100) NOT NULL COMMENT '床编号',
  `order_num` int(10) NOT NULL COMMENT '排序号码，号码小的在前面',
  `is_valid` int(10) NOT NULL DEFAULT '1' COMMENT '是否有效，无效的床位不参加在线选择',
  `remarks` varchar(1000) NOT NULL COMMENT '备注，仅管理员可见',
  `is_del` int(10) NOT NULL DEFAULT '0' COMMENT '1代表删除',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '0 空闲 1 已选'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `beds`
--

INSERT INTO `beds` (`id`, `uid`, `room_id`, `name`, `order_num`, `is_valid`, `remarks`, `is_del`, `status`) VALUES
(1, 2201001, 1, '1号床', 1, 1, '', 0, 0),
(2, 2, 1, '2号床', 2, 1, '', 0, 0),
(3, 3, 1, '3号床', 3, 1, '', 0, 0),
(4, 0, 2, '1号床', 1, 1, '', 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `buildings`
--

CREATE TABLE `buildings` (
  `id` int(10) NOT NULL COMMENT '主键，自增',
  `name` varchar(100) NOT NULL COMMENT '楼名称',
  `order_num` int(10) NOT NULL COMMENT '排序号码，号码小的在前面',
  `is_valid` int(10) NOT NULL DEFAULT '1' COMMENT '是否有效，无效的楼不参加在线选择',
  `remarks` varchar(1000) NOT NULL COMMENT '备注，仅管理员可见',
  `description` varchar(1000) NOT NULL COMMENT '描述，可支持富文本，用户可见',
  `image_url` varchar(100) NOT NULL COMMENT '图片地址，仅支持一张图片',
  `is_del` int(10) NOT NULL DEFAULT '0' COMMENT '1代表删除',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '备用'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `buildings`
--

INSERT INTO `buildings` (`id`, `name`, `order_num`, `is_valid`, `remarks`, `description`, `image_url`, `is_del`, `status`) VALUES
(1, '8号楼', 8, 1, '', '环境艰苦', 'https://www.vcg.com/creative/1412458271', 0, 0),
(2, '9号楼', 9, 1, '', '没有门禁', 'https://www.vcg.com/creative/1412991670', 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `class`
--

CREATE TABLE `class` (
  `id` int(10) NOT NULL COMMENT '主键，自增',
  `name` varchar(20) NOT NULL COMMENT '班级姓名',
  `remarks` varchar(1000) NOT NULL COMMENT '备注，仅管理员可见',
  `is_del` int(10) NOT NULL DEFAULT '0' COMMENT '1代表删除',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '备用'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `class`
--

INSERT INTO `class` (`id`, `name`, `remarks`, `is_del`, `status`) VALUES
(1, '燕南一苑', '', 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `class_room`
--

CREATE TABLE `class_room` (
  `id` int(10) NOT NULL COMMENT '主键，自增',
  `class_id` int(10) NOT NULL COMMENT '班级表中的id',
  `room_id` int(10) NOT NULL COMMENT '宿舍表中的id',
  `remarks` varchar(1000) NOT NULL COMMENT '备注，仅管理员可见',
  `is_del` int(10) NOT NULL DEFAULT '0' COMMENT '1代表删除',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '备用'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `groupers`
--

CREATE TABLE `groupers` (
  `id` int(10) NOT NULL COMMENT '主键，自增',
  `uid` int(10) NOT NULL COMMENT 'user表中的id',
  `is_creator` int(10) NOT NULL DEFAULT '0' COMMENT '是否为创建人，1为是，0为否',
  `group_id` int(10) NOT NULL COMMENT '	groups表中的id',
  `is_del` int(10) NOT NULL DEFAULT '0' COMMENT '1代表删除，离开前置为1',
  `join_time` int(11) DEFAULT NULL COMMENT '加入时间',
  `leave_time` int(11) DEFAULT NULL COMMENT '离开时间',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '备用'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `groupers`
--

INSERT INTO `groupers` (`id`, `uid`, `is_creator`, `group_id`, `is_del`, `join_time`, `leave_time`, `status`) VALUES
(1, 2, 1, 1, 0, NULL, NULL, 0),
(2, 3, 0, 1, 0, NULL, NULL, 0),
(3, 2201001, 0, 1, 0, NULL, NULL, 0);

-- --------------------------------------------------------

--
-- 表的结构 `groups`
--

CREATE TABLE `groups` (
  `id` int(10) NOT NULL COMMENT '主键，自增',
  `name` varchar(100) NOT NULL COMMENT '队伍名称',
  `invite_code` varchar(100) NOT NULL COMMENT '加入口令',
  `description` varchar(1000) NOT NULL COMMENT '队伍描述，用户可见',
  `is_del` int(10) NOT NULL DEFAULT '0' COMMENT '只有队伍中没有其他成员，方可删除',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '如果改组分配宿舍成功，置为1，将不可以再进行队伍的操作'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `groups`
--

INSERT INTO `groups` (`id`, `name`, `invite_code`, `description`, `is_del`, `status`) VALUES
(1, 'aaa', '123456', '测试', 0, 0),
(2, '第一组', '123123', '测试1', 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `logs`
--

CREATE TABLE `logs` (
  `id` int(10) NOT NULL COMMENT '主键，自增',
  `uid` int(10) DEFAULT '0' COMMENT '操作用户id',
  `operation` varchar(500) NOT NULL COMMENT '操作模块名称',
  `client_ip` varchar(50) NOT NULL COMMENT '客户端IP',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  `content` varchar(1000) NOT NULL COMMENT '操作详情',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '0 成功 1 失败',
  `is_del` int(10) NOT NULL DEFAULT '0' COMMENT '1代表删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `orders`
--

CREATE TABLE `orders` (
  `id` int(10) NOT NULL COMMENT '主键，自增',
  `uid` int(10) DEFAULT '0' COMMENT '提交人，user表中的id',
  `group_id` int(10) NOT NULL DEFAULT '0' COMMENT 'groups表中的id，如果未组队，该值为0',
  `building_id` int(10) NOT NULL COMMENT 'building表中的id',
  `submit_time` int(11) DEFAULT NULL COMMENT '下单时间，用户提交表单时间',
  `create_time` int(11) DEFAULT NULL COMMENT '订单生成时间，仅后台可见',
  `finish_time` int(11) DEFAULT '0' COMMENT '订单完成时间，仅后台可见',
  `room_id` int(10) NOT NULL COMMENT 'room表中的id',
  `remarks` varchar(1000) NOT NULL COMMENT '备注，仅管理员可见',
  `is_del` int(10) NOT NULL DEFAULT '0' COMMENT '1代表删除',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '0未处理，1处理成功，2处理失败'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `orders`
--

INSERT INTO `orders` (`id`, `uid`, `group_id`, `building_id`, `submit_time`, `create_time`, `finish_time`, `room_id`, `remarks`, `is_del`, `status`) VALUES
(1, 2201001, 22, 11, 0, 0, 0, 101, '0', 0, 2),
(2, 2201001, 22, 11, 0, 0, 0, 101, '0', 0, 1),
(3, 2201001, 1, 1, 0, 0, 0, 0, '?????', 0, 2),
(4, 2201001, 1, 1, 0, 0, 0, 0, '?????', 0, 2),
(5, 2201001, 1, 1, 0, 0, 0, 0, '?????', 0, 2),
(6, 2201001, 1, 1, 0, 0, 0, 0, '?????', 0, 2),
(7, 2201001, 1, 1, 0, 0, 0, 0, '?????', 0, 2),
(8, 2201001, 1, 1, 0, 0, 0, 0, '?????', 0, 2),
(9, 2201001, 1, 1, 0, 0, 0, 0, '?????', 0, 2);

-- --------------------------------------------------------

--
-- 表的结构 `rooms`
--

CREATE TABLE `rooms` (
  `id` int(10) NOT NULL COMMENT '主键，自增',
  `building_id` int(10) NOT NULL COMMENT 'buildings表中的id',
  `name` varchar(100) NOT NULL COMMENT '宿舍名称',
  `gender` int(10) NOT NULL COMMENT '性别：0为女 1为男',
  `order_num` int(10) NOT NULL COMMENT '排序号码，号码小的在前面',
  `is_valid` int(10) NOT NULL DEFAULT '1' COMMENT '是否有效，无效的宿舍不参加在线选择',
  `remarks` varchar(1000) NOT NULL COMMENT '备注，仅管理员可见',
  `description` varchar(1000) NOT NULL COMMENT '描述，可支持富文本，用户可见',
  `imageUrl` varchar(100) NOT NULL COMMENT '图片地址，仅支持一张图片',
  `isDel` int(10) NOT NULL DEFAULT '0' COMMENT '1代表删除',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '备用'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `rooms`
--

INSERT INTO `rooms` (`id`, `building_id`, `name`, `gender`, `order_num`, `is_valid`, `remarks`, `description`, `imageUrl`, `isDel`, `status`) VALUES
(1, 1, '8E1234', 1, 1, 1, '', 'free', 'https://www.vcg.com/creative/1411226036', 0, 0),
(2, 2, '9E1234', 1, 1, 1, '', '美羊羊', 'https://tenfei04.cfp.cn/creative/vcg/nowater800/new/VCG211411007391.jpg', 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `student_info`
--

CREATE TABLE `student_info` (
  `id` int(10) NOT NULL COMMENT '主键，自增',
  `uid` int(10) NOT NULL COMMENT 'user表中的id',
  `studentid` varchar(50) NOT NULL COMMENT '学号',
  `verification_code` varchar(10) NOT NULL DEFAULT '0' COMMENT '校验码',
  `class_id` int(10) NOT NULL COMMENT '班级id',
  `remarks` varchar(1000) NOT NULL COMMENT '备注，仅管理员可见',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '备用'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `student_info`
--

INSERT INTO `student_info` (`id`, `uid`, `studentid`, `verification_code`, `class_id`, `remarks`, `status`) VALUES
(1, 2201001, '22201001', '51166', 1, '1', 0);

-- --------------------------------------------------------

--
-- 表的结构 `sys`
--

CREATE TABLE `sys` (
  `id` int(10) NOT NULL COMMENT '主键，自增',
  `key_name` varchar(100) NOT NULL COMMENT '配置名称',
  `key_value` varchar(1000) NOT NULL COMMENT '配置值',
  `is_del` int(10) NOT NULL DEFAULT '0' COMMENT '1代表删除',
  `remarks` varchar(1000) NOT NULL COMMENT '备注，仅管理员可见'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `sys`
--

INSERT INTO `sys` (`id`, `key_name`, `key_value`, `is_del`, `remarks`) VALUES
(1, 'start_time', '2022-09-01 10:00:00', 0, ''),
(2, 'end_time', '2022-09-01 19:00:00', 0, ''),
(3, 'group_limit', '0', 0, ''),
(4, 'group_num', '4', 0, ''),
(5, 'class_limit', '0', 0, '');

-- --------------------------------------------------------

--
-- 表的结构 `users`
--

CREATE TABLE `users` (
  `uid` int(10) NOT NULL COMMENT '主键，自增，系统中其他表中的uid字段引用该字段',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `gender` int(10) NOT NULL COMMENT '性别：0为女 1为男',
  `email` varchar(100) NOT NULL COMMENT '电子邮箱',
  `tel` varchar(100) NOT NULL COMMENT '电话号码',
  `type` int(10) NOT NULL DEFAULT '1' COMMENT '1为学生。此字段保留方便以后扩展',
  `add_time` int(11) NOT NULL DEFAULT '0' COMMENT '添加时间',
  `is_deny` int(10) NOT NULL DEFAULT '0' COMMENT '是否禁止使用系统，1为禁止，0为允许登录',
  `last_login_time` int(11) NOT NULL DEFAULT '0' COMMENT '最后一次登录时间',
  `remarks` varchar(1000) NOT NULL COMMENT '备注，仅管理员可见',
  `is_del` int(10) NOT NULL DEFAULT '0' COMMENT '1代表删除',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '备用'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `users`
--

INSERT INTO `users` (`uid`, `name`, `gender`, `email`, `tel`, `type`, `add_time`, `is_deny`, `last_login_time`, `remarks`, `is_del`, `status`) VALUES
(2, 'Ishii Kenta', 0, 'ikenta201@outlook.com', 'CzxWSLpj7I', 107, 976, 857, 277, '5KkrHvB1BL', 938, 298),
(3, 'Chan Fat', 0, 'fatchan@gmail.com', 'EuefZspVm0', 367, 753, 995, 818, 'rdtDGZ0tw8', 99, 17),
(4, 'Ng Sze Yu', 118, 'ngsze@outlook.com', 'eokGiMO0X0', 491, 101, 914, 222, 'UwRxZ2nVrH', 663, 33),
(5, 'Ueda Seiko', 558, 'seikou47@gmail.com', 'GhtTnJvXDG', 65, 577, 293, 701, 'WEYwKSMKQV', 594, 916),
(6, 'Siu Ka Ming', 999, 'kmsiu5@gmail.com', 'f84i3YnWcN', 354, 792, 529, 386, 'B5rylAcncr', 591, 192),
(7, 'Fan Ziyi', 454, 'ziyfan@icloud.com', 'DNFXb1jMwU', 577, 680, 90, 533, '6aMNrBxTgf', 886, 773),
(8, 'Yao Yunxi', 511, 'yunxiy221@icloud.com', 'XoM7qId3JM', 258, 174, 120, 650, 'UoGLdwjjuP', 726, 854),
(9, 'Shawn Jones', 497, 'js7@yahoo.com', '4SbYz6lmRh', 513, 619, 11, 205, 'OdU99me56J', 411, 76),
(10, 'Kobayashi Kasumi', 590, 'kasukobay@outlook.com', 'EEp0Nclyx2', 236, 683, 931, 228, 'oDPcaGqU8u', 265, 59),
(111111, 'xiaoxiao', 1, '201@outlook.com', 'CzxWSLpj7I', 107, 976, 857, 277, '5KkrHvB1BL', 938, 298),
(2201001, 'Cheng Wing Kuen', 1, 'chengwing@outlook.com', 'slMvYKlbQo', 521, 224, 454, 882, 'MiAg2l1d98', 633, 19);

--
-- 转储表的索引
--

--
-- 表的索引 `auth`
--
ALTER TABLE `auth`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- 表的索引 `beds`
--
ALTER TABLE `beds`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `buildings`
--
ALTER TABLE `buildings`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `class_room`
--
ALTER TABLE `class_room`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `groupers`
--
ALTER TABLE `groupers`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `student_info`
--
ALTER TABLE `student_info`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `sys`
--
ALTER TABLE `sys`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`uid`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `auth`
--
ALTER TABLE `auth`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键，自增', AUTO_INCREMENT=7;

--
-- 使用表AUTO_INCREMENT `beds`
--
ALTER TABLE `beds`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键，自增', AUTO_INCREMENT=5;

--
-- 使用表AUTO_INCREMENT `buildings`
--
ALTER TABLE `buildings`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键，自增', AUTO_INCREMENT=3;

--
-- 使用表AUTO_INCREMENT `class`
--
ALTER TABLE `class`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键，自增', AUTO_INCREMENT=2;

--
-- 使用表AUTO_INCREMENT `class_room`
--
ALTER TABLE `class_room`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键，自增';

--
-- 使用表AUTO_INCREMENT `groupers`
--
ALTER TABLE `groupers`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键，自增', AUTO_INCREMENT=4;

--
-- 使用表AUTO_INCREMENT `groups`
--
ALTER TABLE `groups`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键，自增', AUTO_INCREMENT=3;

--
-- 使用表AUTO_INCREMENT `logs`
--
ALTER TABLE `logs`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键，自增';

--
-- 使用表AUTO_INCREMENT `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键，自增', AUTO_INCREMENT=10;

--
-- 使用表AUTO_INCREMENT `rooms`
--
ALTER TABLE `rooms`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键，自增', AUTO_INCREMENT=3;

--
-- 使用表AUTO_INCREMENT `student_info`
--
ALTER TABLE `student_info`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键，自增', AUTO_INCREMENT=2;

--
-- 使用表AUTO_INCREMENT `sys`
--
ALTER TABLE `sys`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键，自增', AUTO_INCREMENT=6;

--
-- 使用表AUTO_INCREMENT `users`
--
ALTER TABLE `users`
  MODIFY `uid` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键，自增，系统中其他表中的uid字段引用该字段', AUTO_INCREMENT=111112;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
