# ICP_project-_1

This program is used for managing, colecting and retrieving data from a database(MYSQL) by the use of a java program. It has both the console version and a GUI version for easy interaction of the user and the application. Data of both an observatory and Galamsey events can be taken from both the console and the GUI. The program also provides some manipulation of these data to give statistics of the data such as largest value, average colour value. The version one is an interaction with the console and version two is with the GUI. The application is also able to read from a csv(comma delimited) file if it becomes tedious for the user to input the data one after the other. 

THINGS NEEDED:
1. A MYSQL connector driver
2. A csv file path or directory with the file (Read_csv program)
3. The creation of the tables in mysql database using the sql query syntax below
4. A change of the connection and the database depending on what you will be using
5. Kindly find attached two csv files 
6. Version runs from the MonitoringIO
7. Version two runs from the MonitoringGUI


--------------------------------------------------------------------------------------------------------
The MonitoringGUI class is the GUI class to display a form to add the Database in both the Galamsey and Observatory table. It also loads and views from the database from both tables and allows view at an arbitrary limit colour value in the form of a table to simulate a query view. The hashtable used to store all details is constantly updating from the database at every alteration to keep all the views and data accurate at all times.


Galamsey class
--------------------------------------------------------------
The Galamsey class represents a likely occurrence of Galamsey with its details, namely; vegetation color, color value, longitude, latitude, and year of the event. In the Galamsey class, Ga_details() method takes input from the user through the console. The Ga_details() method has been used in the MonitoringGUI to take inputs from the user in the GUI. The user inputs his/her data through the console or GUI, then they are been set to the mutator methods of the Galamsey class.
Then the accessor methods with the help of the intakeData_Galamsey () method performs the insertion of the data collected into the database. The data are been inserted into the database and the hash tables  created in the Monitoring class simultaneously. The intakeData_Galamsey () creates a connection with the MYSQL database to inserts the data into the single relation. The getData() method  retrieve data from the Galamsey class objects.  


SQL syntax for the creation of the relation in the database
-----------------------------------------------------------------------------------

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
The Read_csv class reads from a csv (comma delimited) file and writes its data into the database. There are methods which takes in a string data type of the file directory or path for the csv file one wants to read into the database. The csv file must have the same number of columns with the each having a data integrity constraint as the attributes in the database. The columns of the csv file should be the same as the columns of each relation one is inserting to. The above creation of the individual relation shows the attributes and the data type of each attribute which should correspond with columns and data type of the csv file. The csv file must be comma delimited. 
How it works 
-----------------------------------------------------------------------------
Depending on the csv file you are reading from and the relation you want to insert to, the read_into_observatory(String filecsv1) method takes the csv file path of a csv file and inserts its data into the observatory relation. The Take_csv_Galamsey(String filecsv2) takes the string file path or directory of the csv file and adds it data into the galamsey relation in the database. The connection between the java program and the database is by the use of the mysql connector. The methods to enable the program to insert into the database are implemented in the class. First, the program takes in the file reads the first line of the file sets it to null since the first line contains the names of the fields such as observatory name, vegetation colour, etc.It does not need to be added to the database so it is sets to null then from the second line of the csv file an iteration is done using the while loop. The iteration continues until the line is null. A String array, a string line variable are all predefined. When a line has been read, that line is been splitted into individual string elements by splitting according to the comma that separates them. That is the reason why the csv file must be saved as a comma delimited file extension. Then the array is been indexed to insert it individual index into it respective fields in the database. That's done till the readline gets to return null then its ends the program and at the same time the connection is close and the file openned for reading is also close then the program ends for that method call. 

MonitoringIO
The monitoringIO is the contains the main method of the version one part of the project. It performs functionality like the insetion of galamsey data, insertion of observatory data, monitoring statistics etc. By the use of object oriented programming the classes such as the galamsey, observatory are been called and their instances are been created to call their methods which makes the programming must easier and realistic. The insertion of the data into the database is taken up here as well since after the data is taken they are been inserted. 

