-- --------------------------------------------------------
-- 主機:                           127.0.0.1
-- 伺服器版本:                        10.6.5-MariaDB - mariadb.org binary distribution
-- 伺服器作業系統:                      Win64
-- HeidiSQL 版本:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 傾印 the_round_table 的資料庫結構
CREATE DATABASE IF NOT EXISTS `the_round_table` /*!40100 DEFAULT CHARACTER SET utf8mb3 */;
USE `the_round_table`;

-- 傾印  資料表 the_round_table.class_table 結構
CREATE TABLE IF NOT EXISTS `class_table` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` varchar(255) DEFAULT NULL,
  `seq_no` int(11) DEFAULT NULL,
  `speaker` varchar(255) DEFAULT NULL,
  `sub_title` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `weekly` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;

-- 正在傾印表格  the_round_table.class_table 的資料：~0 rows (近似值)
/*!40000 ALTER TABLE `class_table` DISABLE KEYS */;
INSERT IGNORE INTO `class_table` (`id`, `date`, `seq_no`, `speaker`, `sub_title`, `title`, `weekly`) VALUES
	(1, '01/20（四）1600-1800', 1, 'Oscar', '1.Java compiler編譯\r\n2.基本資料型別、宣告 int / Boolean\r\n3.String字串\r\n4.運算子 == / != …\r\n5.流程 if else / switch ….\r\n6.陣列 arrary\r\n7.類別與物件new / reference /\r\n', 'JAVA 1-7', '第一週'),
	(2, '02/17（四）1400-1600', 2, 'Michael', '8.覆載 fuction(X) / function(X,X)\r\n9.封裝 getter / setter\r\n10.繼承 superclass / subclass\r\n11.覆寫 overwrite\r\n12.多型 polymorphism', 'JAVA 8-12', '第二週'),
	(3, '02/24（四）1400-1600', 3, 'Oscar', '13.物件 定義物件\r\n14.例外 try catch / exception\r\n15.陣列List / Set / Map\r\n16.Lambda', 'JAVA 13-16', '第三週'),
	(4, '03/03（四）1400-1600', 4, 'Michael', '1.環境 tomcat\r\n2.專案建立\r\n3.JSP	jstl', 'Servlet 1-3', '第四週'),
	(5, '03/10（四）1400-1600', 5, 'Oscar', '3.JSP	jstl\r\n4.Response GET/POST\r\n5.導頁	forward / redirect', 'Servlet 3-5 ', '第五週'),
	(6, '03/17（四）1400-1600', 6, 'Michael', '1.Html5	h2 / div / span\r\n2.Javascrpit 	event / select / data', 'Html 1-2', '第六週'),
	(7, '03/24（四）1400-1600', 7, 'Oscar', '1.Html5	h2 / div / span\r\n3.Jquery	與JS 對照\r\n', 'html 1+3', '第七週'),
	(8, '03/31（四）1400-1600', 8, 'Michael', '4.Html5+JQ  event / data process json\r\n5.前後台AJAX / GET /POST\r\n6.前後台 data process', 'html 4-5', '第八週'),
	(9, '04/07（四）1400-1600', 9, 'Oscar', '1.環境	mvn / spring io\r\n2.架構	pom / MVC / properties\r\n3.模板引擎 thymeleaf /controller /Rest API GET POST', 'Spring boot 1-2', '第九週'),
	(10, '04/14（四）1400-1600', 10, 'Michael', '4.DB	mariadb driver / data process\r\n5.JpaRepository /entity /repository /service', 'Spring boot 4-5', '第十週'),
	(11, '04/21（四）1400-1600', 11, 'Oscar', '6.Login + Table / Controller / DB ', 'Spring boot 6', '第十一週'),
	(12, '04/28（四）1400-1600', 12, 'Michael', '7.UserRole / 專題實作', 'Spring boot 7 ', '第十二週');
/*!40000 ALTER TABLE `class_table` ENABLE KEYS */;

-- 傾印  資料表 the_round_table.sys_user 結構
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(20) NOT NULL,
  `account` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 正在傾印表格  the_round_table.sys_user 的資料：~3 rows (近似值)
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT IGNORE INTO `sys_user` (`id`, `account`, `password`, `user_name`) VALUES
	(1, '21050364', '0000', 'OscarLin'),
	(2, '20012321', '0000', 'LukaJay'),
	(3, '16046855', '0000', 'MichealChai');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
