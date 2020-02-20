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


The Read_csv class 
---------------------------------------------------------
The Read_csv class reads from a csv (comma delimited) file and writes its data into the database. It does this by the creation of an object of the class and calling the methods which insert into either the observatory relation or the Galamsey relation. The method takes in a string data type which is the file directory or path for the csv file one wants to read into the database. The csv file must have the same number of columns with the each having a data integrity constraint has the attributes in the database. The columns of the csv file should be the same as the columns of each relation one is inserting to. The above creation of the individual relation shows the attributes and the data type of each attribute which should correspond with columns and data type of the csv file. The csv file must be comma delimited. 

How it works 
-----------------------------------------------------------------------------
Depending on the csv file you are reading from and the relation you want to insert to, the read_into_observatory(String filecsv1) method takes the csv file path of a csv file and inserts its data into the observatory relation. The Take_csv_Galamsey(String filecsv2) takes the string file path or directory of the 
