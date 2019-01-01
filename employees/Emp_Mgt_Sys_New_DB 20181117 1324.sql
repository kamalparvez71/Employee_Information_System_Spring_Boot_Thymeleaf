-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.22-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema employeesoriginal
--

CREATE DATABASE IF NOT EXISTS employeesoriginal;
USE employeesoriginal;

--
-- Definition of table `departments`
--

DROP TABLE IF EXISTS `departments`;
CREATE TABLE `departments` (
  `dept_no` char(4) NOT NULL,
  `dept_name` varchar(40) NOT NULL,
  PRIMARY KEY  (`dept_no`),
  UNIQUE KEY `dept_name` (`dept_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `departments`
--

/*!40000 ALTER TABLE `departments` DISABLE KEYS */;
INSERT INTO `departments` (`dept_no`,`dept_name`) VALUES 
 ('d009','Customer Service'),
 ('d005','Development'),
 ('d002','Finance'),
 ('d003','Human Resources'),
 ('d010','IT_DEPT'),
 ('d001','Marketing'),
 ('d004','Production'),
 ('d006','Quality Management'),
 ('d008','Research'),
 ('d007','Sales');
/*!40000 ALTER TABLE `departments` ENABLE KEYS */;


--
-- Definition of table `dept_emp`
--

DROP TABLE IF EXISTS `dept_emp`;
CREATE TABLE `dept_emp` (
  `emp_no` int(11) NOT NULL,
  `dept_no` char(4) NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  PRIMARY KEY  (`emp_no`,`dept_no`),
  KEY `dept_no` (`dept_no`),
  CONSTRAINT `dept_emp_ibfk_1` FOREIGN KEY (`emp_no`) REFERENCES `employees` (`emp_no`) ON DELETE CASCADE,
  CONSTRAINT `dept_emp_ibfk_2` FOREIGN KEY (`dept_no`) REFERENCES `departments` (`dept_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dept_emp`
--

/*!40000 ALTER TABLE `dept_emp` DISABLE KEYS */;
INSERT INTO `dept_emp` (`emp_no`,`dept_no`,`from_date`,`to_date`) VALUES 
 (1240901,'d010','2017-01-01','2018-11-15'),
 (1240902,'d002','2018-01-01','2018-11-15');
/*!40000 ALTER TABLE `dept_emp` ENABLE KEYS */;


--
-- Definition of table `dept_manager`
--

DROP TABLE IF EXISTS `dept_manager`;
CREATE TABLE `dept_manager` (
  `emp_no` int(11) NOT NULL,
  `dept_no` char(4) NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  PRIMARY KEY  (`emp_no`,`dept_no`),
  KEY `dept_no` (`dept_no`),
  CONSTRAINT `dept_manager_ibfk_1` FOREIGN KEY (`emp_no`) REFERENCES `employees` (`emp_no`) ON DELETE CASCADE,
  CONSTRAINT `dept_manager_ibfk_2` FOREIGN KEY (`dept_no`) REFERENCES `departments` (`dept_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dept_manager`
--

/*!40000 ALTER TABLE `dept_manager` DISABLE KEYS */;
INSERT INTO `dept_manager` (`emp_no`,`dept_no`,`from_date`,`to_date`) VALUES 
 (1240901,'d010','2017-01-01','2018-11-15'),
 (1240902,'d009','2018-01-01','2018-11-16');
/*!40000 ALTER TABLE `dept_manager` ENABLE KEYS */;


--
-- Definition of table `employees`
--

DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees` (
  `emp_no` int(11) NOT NULL,
  `birth_date` date NOT NULL,
  `first_name` varchar(14) NOT NULL,
  `last_name` varchar(16) NOT NULL,
  `gender` enum('M','F') NOT NULL,
  `hire_date` date NOT NULL,
  `photo` longblob NOT NULL,
  PRIMARY KEY  (`emp_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employees`
--

/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` (`emp_no`,`birth_date`,`first_name`,`last_name`,`gender`,`hire_date`,`photo`) VALUES 
 (1240901,'1990-08-24','Md.Kamal','Parvez','M','2017-01-01',0x6B616D616C2E6A7067),
 (1240902,'1991-12-28','Mst. Moushumi','Mou','F','2018-01-01',0x6D6F752E6A7067),
 (1240903,'1989-08-24','Mohammad Kamal','Parvez','M','2017-01-01',0x6B616D616C70617276657A2E6A7067);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;


--
-- Definition of table `salaries`
--

DROP TABLE IF EXISTS `salaries`;
CREATE TABLE `salaries` (
  `emp_no` int(11) NOT NULL,
  `salary` int(11) NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  PRIMARY KEY  (`emp_no`,`from_date`),
  CONSTRAINT `salaries_ibfk_1` FOREIGN KEY (`emp_no`) REFERENCES `employees` (`emp_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `salaries`
--

/*!40000 ALTER TABLE `salaries` DISABLE KEYS */;
INSERT INTO `salaries` (`emp_no`,`salary`,`from_date`,`to_date`) VALUES 
 (1240901,30000,'2017-01-01','2018-11-15'),
 (1240902,20000,'2018-01-01','2018-11-15');
/*!40000 ALTER TABLE `salaries` ENABLE KEYS */;


--
-- Definition of table `titles`
--

DROP TABLE IF EXISTS `titles`;
CREATE TABLE `titles` (
  `emp_no` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date default NULL,
  PRIMARY KEY  (`emp_no`,`title`,`from_date`),
  CONSTRAINT `titles_ibfk_1` FOREIGN KEY (`emp_no`) REFERENCES `employees` (`emp_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `titles`
--

/*!40000 ALTER TABLE `titles` DISABLE KEYS */;
INSERT INTO `titles` (`emp_no`,`title`,`from_date`,`to_date`) VALUES 
 (1240901,'Java Programmer','2017-01-01','2018-11-15'),
 (1240902,'Finance Officer','2018-01-01','2018-11-15');
/*!40000 ALTER TABLE `titles` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
