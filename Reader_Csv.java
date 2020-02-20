/**
 *
 * This program takes in a file path directory of a csv file
 * and reads form the file it data into a connected database
 * This reads from both Observatory.csv and Galamsey.csv
 * into a database. The csv file must be comma delimited.
 *
 * @author Eugene Daniels
 *          Bryan Ayetey
 *          Abena
 *          Wisdom Kalu
 *
 * @version 1.0
 */

import java.io.*;
import java.util.*;
import java.sql.*;


public class Reader_Csv {

    private Connection conn;
    private Statement st;

    public void read_into_observatory(String filecsv1) throws SQLException {
        // a query for inserting into the database will the table name Observatory and its fields or attributes
        String sql = "insert into Observatory(observatory_name, country_located, year_obsv ,area_covered) values(?,?,?,?)";

        PreparedStatement pstmt1 = null; // PreparedStatement is used for executing queries

        conn = null;

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ICP_Project?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");

            pstmt1 = conn.prepareStatement(sql);

            File file = new File(filecsv1);

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            String [] Array; // an array of string of empty sting objects

            br.readLine(); // This will read the heading of the cvs file
            String first_line = null; // Ignoring the heading of the csv which contains the field names


            // The loop will start from line 2 of the csv file
            while((line = br.readLine()) != null){
                Array = line.split(","); // the comma delimiter for the csv file
                // go through the array and add its field data to be inserted into the database
                pstmt1.setString(1, Array[0]);
                pstmt1.setString(2, Array[1]);
                pstmt1.setInt(3, Integer.parseInt(Array[2]));
                pstmt1.setDouble(4, Double.parseDouble(Array[3]));

                    /**
                     * This returns an int which shows the number of rows
                     * affected by the query
                     */
                    int i = pstmt1.executeUpdate(); // executing the query
                    // Checking rows were affected by the query
                    if(i  > 0){
                        System.out.println("Your data was successfully inserted collected in the Observatory database");
                    }
                    else {
                        System.out.println("Your data was not collected");
                    }

                }
                System.out.println();
            // Closing the connection with the database
            conn.close();
            // closing the file being read
            br.close();
            /**
             * Exception handling errors for file, class and sql
             */
            } catch (IOException | ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method takes a string of the file directory path and
     * reads from the csv(comma delimited) which insert it data into the
     * database
     * @param filecsv2 This is the file path of the csv file
     */
    public void Take_csv_Galamsey(String filecsv2){
        String sql = "insert into galamsey(Veg_col, col_value, latitude , longitude , event_year, observatory_name) VALUES(?,?,?,?,?,?);" ;

        PreparedStatement pstmt1 = null;

        conn = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ICP_Project?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
            // for executing the query
            pstmt1 = conn.prepareStatement(sql);
            Scanner sc = new Scanner(System.in);

            File file = new File(filecsv2);

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            String [] Array;
            br.readLine(); // This will read the heading of the cvs file
            String first_line = null;

            // Executing the sql query
            pstmt1 = conn.prepareStatement(sql);

            while((line = br.readLine()) != null) {
                Array = line.split(","); // the comma delimiter for the csv file

                // Inserting the elements in the array into the database fields
                pstmt1.setString(1, Array[0]);
                pstmt1.setInt(2, Integer.parseInt(Array[1]));
                pstmt1.setDouble(3, Double.parseDouble(Array[2]));
                pstmt1.setDouble(4, Double.parseDouble(Array[3]));
                pstmt1.setInt(5, Integer.parseInt(Array[4]));
                pstmt1.setString(6, Array[5]);


                int i = pstmt1.executeUpdate();
                // Checking rows were affected by the query
                if(i  > 0){
                    System.out.println("Your data was successfully inserted or collected in the Galamsey database");
                }
                else {
                    System.out.println("Your data was not collected");
                }

            }
            // terminating the connection with mysq
            conn.close();

            // Closing the bufferreader object
            br.close();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * This is the main method of the class that reads from a csv(comma delimited)
     * into the database
     * @param args
     * @throws SQLException this throws all sql exception errors if any
     */
    public static void main(String [] args) throws SQLException {
        Reader_Csv bn = new Reader_Csv(); // Creating an instance of the Reader_Csv class

        // Create a file path directory of the csv(comma delimited) file
        // Example of a csv file path directory
        String file_galamsey = "C:\\Users\\Eugene Daniels\\Desktop\\Galamsey.csv";
        String file_observatory =  "C:\\Users\\Eugene Daniels\\Desktop\\Observatory.csv";

        // Inserting from Book4 which contains observatory data into the database
        bn.read_into_observatory(file_observatory); // Reading from the observatory csv file
        bn.Take_csv_Galamsey(file_galamsey); // reading from the Galamsey csv file
    }
}
