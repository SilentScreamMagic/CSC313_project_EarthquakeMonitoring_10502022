# ICP_project-_1

The MonitoringGUI class is the GUI class to display a form to add the Database in both the Galamsey and Observatory table. It also loads and views from the database from both tables and allows view at an arbitrary limit colour value in the form of a table to simulate a query view. The hashtable used to store all details is constantly updating from the database at every alteration to keep all the views and data accurate at all times.

The Galamsey class represents a potential occurence of Galamsey with it's details namely; vegetation colour, colour value, longitude 





 CREATE TABLE `observatory` (
  `observatory_name` varchar(200) NOT NULL,
  `country_located` varchar(100) NOT NULL,
  `year_obsv` int(11) NOT NULL,
  `area_covered` int(11) DEFAULT NULL,
  PRIMARY KEY (`observatory_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4



CREATE TABLE `galamsey` (
  `Veg_col` varchar(20) NOT NULL,
  `col_value` int(11) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `event_year` int(11) NOT NULL,
  `galam_id` int(11) NOT NULL AUTO_INCREMENT,
  `observatory_name` varchar(200) NOT NULL,
  PRIMARY KEY (`galam_id`),
  KEY `observatory_name` (`observatory_name`),
  CONSTRAINT `galamsey_ibfk_1` FOREIGN KEY (`observatory_name`) REFERENCES `observatory` (`observatory_name`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4
