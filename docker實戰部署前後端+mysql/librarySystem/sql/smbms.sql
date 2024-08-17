create database if not exists school_books_system default charset utf8 COLLATE utf8_general_ci;
USE `school_books_system`;

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `role` int(2) DEFAULT '1' COMMENT '⻆⾊：0，表示超級管理員，⾮ 0 表示普
通管理員',
 `username` varchar(20) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '帳號',
 `password` varchar(64) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '密碼',
 `nick_name` varchar(20) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '暱稱',
 `create_time` datetime DEFAULT NULL COMMENT '建立時間',
 PRIMARY KEY (`id`),
 UNIQUE KEY `un` (`nick_name`),
 UNIQUE KEY `un01` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `title` varchar(50) COLLATE utf8mb4_german2_ci NOT NULL COMMENT'標題',
 `content` varchar(1000) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '內容',
 `weight` int(4) DEFAULT '10' COMMENT '權重（越⼩越前）',
 `create_time` datetime DEFAULT NULL COMMENT '建立時間',
 `creator` varchar(20) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '建立⼈',
 `update_time` datetime DEFAULT NULL COMMENT '修改時間',
 `modifier` varchar(20) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '修改⼈',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `book_number` varchar(20) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '圖書編號',
 `cover_url` varchar(500) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '封⾯url',
 `book_name` varchar(50) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '書名',
 `author` varchar(20) COLLATE utf8mb4_german2_ci NOT NULL COMMENT'作者',
 `category_id` bigint(20) NOT NULL COMMENT '類別',
 `price` decimal(10,2) DEFAULT '0.00' COMMENT '單價',
 `publishing_house` varchar(50) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '出版社',
 `publication_date` date NOT NULL COMMENT '出版⽇期',
 `total` int(4) NOT NULL DEFAULT '0' COMMENT '總數',
 `extant_total` int(4) NOT NULL DEFAULT '0' COMMENT '目前數量',
 `intro` varchar(1000) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '內容摘要',
 `create_time` datetime DEFAULT NULL COMMENT '⼊庫時間，建立時間',
`creator` varchar(50) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '建立⼈',
 `update_time` datetime DEFAULT NULL COMMENT '修改時間',
 `modifier` varchar(50) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '修改⼈',
 PRIMARY KEY (`id`),
 UNIQUE KEY `un` (`book_number`),
 UNIQUE KEY `un02` (`book_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

DROP TABLE IF EXISTS `book_borrow_info`;
CREATE TABLE `book_borrow_info` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `user_id` bigint(20) NOT NULL COMMENT '⽤戶id',
 `book_id` bigint(20) NOT NULL COMMENT '圖書id',
 `borrow_count` int(4) NOT NULL DEFAULT '0' COMMENT '借閱數量',
 `return_count` int(4) DEFAULT '0' COMMENT '歸還數量',
 `create_time` datetime DEFAULT NULL COMMENT '建立時間',
 `update_time` datetime DEFAULT NULL COMMENT '修改時間',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

DROP TABLE IF EXISTS `book_category`;
CREATE TABLE `book_category` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `category_name` varchar(50) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '類別名稱',
 `create_time` datetime DEFAULT NULL COMMENT '建立時間',
 `creator` varchar(20) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '建立⼈',
 `update_time` datetime DEFAULT NULL COMMENT '修改時間',
 `modifier` varchar(20) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '修改⼈',
 PRIMARY KEY (`id`),
 UNIQUE KEY `un` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

DROP TABLE IF EXISTS `carousel_map`;
CREATE TABLE `carousel_map` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `img_url` varchar(500) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '圖⽚url',
 `weight` int(4) DEFAULT '10' COMMENT '權重（越⼩越前）',
 `create_time` datetime DEFAULT NULL COMMENT '建立時間',
 `creator` varchar(20) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '建立⼈',
 `update_time` datetime DEFAULT NULL COMMENT '修改時間',
 `modifier` varchar(20) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '修改⼈',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

DROP TABLE IF EXISTS `sys_token`;
CREATE TABLE `sys_token` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `token` varchar(64) COLLATE utf8mb4_german2_ci NOT NULL COMMENT 'token字串',
 `auth_info` varchar(1000) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '認證訊息，json 內容',
 `expired_time` datetime NOT NULL COMMENT 'token的過期時間',
 PRIMARY KEY (`id`),
 UNIQUE KEY `un` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `student_number` varchar(32) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '學號',
 `password` varchar(64) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '密碼',
 `name` varchar(20) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '姓名',
 `sex` tinyint(1) DEFAULT '1' COMMENT '性別',
 `college` varchar(50) COLLATE utf8mb4_german2_ci NOT NULL COMMENT'學院',
 `grade` varchar(20) COLLATE utf8mb4_german2_ci NOT NULL COMMENT'班級',
 `phone` varchar(11) COLLATE utf8mb4_german2_ci NOT NULL COMMENT'電話',
 `dormitory` varchar(50) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '宿舍',
 `create_time` datetime DEFAULT NULL COMMENT '建立時間',
 `update_time` datetime DEFAULT NULL COMMENT '修改時間',
 PRIMARY KEY (`id`),
 UNIQUE KEY `un` (`student_number`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

DROP TABLE IF EXISTS `user_return_book_info`;
CREATE TABLE `user_return_book_info` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `user_id` bigint(20) NOT NULL COMMENT '⽤戶id',
 `book_id` bigint(20) NOT NULL COMMENT '圖書id',
 `return_count` int(4) NOT NULL COMMENT '歸還數量',
 `create_time` datetime DEFAULT NULL COMMENT '建立時間',
 `update_time` datetime DEFAULT NULL COMMENT '修改時間',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;


