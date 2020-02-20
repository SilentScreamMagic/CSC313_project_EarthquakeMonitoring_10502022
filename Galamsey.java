/**
 * This is a class for galamsey which takes the galamsey inputs,
 * and inserts it into a database (mysql database)
 *
 * @authors Bryan Ayitey
 *          Eugene Daniels
 *          Abena Okyere 
 *          Wisdom Kalu
 */


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;
import java.util.*;
import java.sql.*;

/**
 * Galamsey class which attributes of the Galamsey
 * and inserts those attributes into a databse
 */
public class Galamsey {
    // Creating instance variable of the galamsey class
    private String Veg_col;
    private int col_value;
    private double latitude;
    private double longitude;
    private int year;
    private Connection conn;
    private Statement st;
    private ResultSet rs;
    private String observatory_name;



    public Galamsey(){} // default constructor

    /**
     * A non default constructor of the galamsey class which initializes the
     * attributes of the galamsey
     * @param veg_col for the vegetation colour of the galamsey area
     * @param col_value for the colour value
     * @param latitude for the latitude of the galamsey position
     * @param longitude for the longitude of the galamsey position
     * @param year for the year of the galamsey observation
     */
    public Galamsey(String veg_col, int col_value, double latitude, double longitude, int year) {
        this.Veg_col = veg_col;
        this.col_value = col_value;
        this.latitude = latitude;
        this.longitude = longitude;
        this.year = year;


    }

    /**
     * An accessor method to get the vegetation colour of the galamsey class
     * or object that will be created
     * @return a string vegetation colour
     */
    public String getVeg_col() {
        return Veg_col;
    }

    // a mutator method for the vegetation colour
    public void setVeg_col(String veg_col) {
        Veg_col = veg_col;
    }

    /**
     * a accessor method for the colour value of the galamsey
     * @return an int of the colour value of the galamsey
     */
    public int getCol_value() {
        return col_value;
    }

    // a mutator method for the colour value of galamsey
    public void setCol_value(int col_value) {
        this.col_value = col_value;
    }

    /**
     * An accessor method which returns the latitudinal position of the galamsey
     * @return a double type of the latitudinal position
     */
    public double getLatitude() {
        return latitude;
    }

    // a mutator method for setting the latitude
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * This method returns the longitudinal position of the galamsey
     * @return a double type longitudinal position of the galamsey
     */
    public double getLongitude() {
        return longitude;
    }

    // a mutator method to for the longitudinal position
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * This method returns the year of the galamsey observation
     * @return an  int type of the year of the observation
     */
    public int getYear() {
        return year;
    }

    //set the year of the galamsey observation
    public void setYear(int year) {
        this.year = year;
    }


    /**
     * This method returns the observatory name of
     * observatory which the galamsey is from
     * @return the observatory name for the galamsey
     */
    public String getObservatory_name() {
        return observatory_name;
    }

    // sets the observatory name of the galamsey
    public void setObservatory_name(String observatory_name) {
        this.observatory_name = observatory_name;
    }


    /**
     * A method that takes the data or details of the galamsey
     * and then adds it to the database
     */
    public void Ga_details(){
        // Checking the input for any wrong inputs
        try {
            // Creating a scannner object to take inputs from the user
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the Vegetation color: ");
            String veg = sc.nextLine();

            // converting all input into a lower case to compare with the colour of the galamsey
            veg.toLowerCase();

            // comparing the colour of the galamsey to ensure the specification of the colour
            if (veg.equals("green") || veg.equals("red") || veg.equals("yellow")){
                // setting the colour of the vegetation
                setVeg_col(veg);
            }
            else
                System.out.println("You entered a wrong input here.");


            System.out.println("Enter the color value: ");
            int col = sc.nextInt();
            if (col == 1 || col == 2 || col == 3){
                setCol_value(col);
            }
            else{
                System.out.println("You entered the wrong assigned number");
            }

            System.out.println("Enter the latitude of the area: ");
            double lat = sc.nextDouble();
            setLatitude(lat);

            System.out.println("Enter the longitude of the area: ");
            double lon = sc.nextDouble();
            setLongitude(lon);

            System.out.println("Enter the year of event: ");
            int y = sc.nextInt();
            setYear(y);

            System.out.println("Enter the observation name: ");
            Scanner sc1 = new Scanner(System.in);
            String obs_name = sc1.nextLine();
            setObservatory_name(obs_name);

        }
        // Exception handling
        catch(InputMismatchException e){
            e.printStackTrace();
        }
    }



    // This is for the insertion of the database

    /**
     * This method performs the insertion of the data or inputs
     * taken from the method Ga_details and insert them into
     * the mysql database
     * @throws SQLDataException
     */

    public void intakeData_Galamsey () throws SQLDataException {

        //  An insertion query to insert into the database with table named galamsey
        String sql = "insert into galamsey(Veg_col, col_value, latitude , longitude , event_year, observatory_name) VALUES(?,?,?,?,?,?);" ;

        // PreparedStatement is an interface used to execute the parameterized SQL query
        PreparedStatement pstmt = null;

        conn = null;

        try{

            // This load the driver's class of the database(mysql)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // This is use to register a driver for the mysql database and with the getConnection
            // method to establish a database connection with mysql
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ICP_Project?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");

            // Executing the sql query
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, this.getVeg_col());
            pstmt.setInt(2, this.getCol_value());
            pstmt.setDouble(3, this.getLatitude());
            pstmt.setDouble(4, this.getLongitude());
            pstmt.setInt(5, this.getYear());
            pstmt.setString(6, this.getObservatory_name());

            // gets an int value to check the number of rows affected by the query
            int i = pstmt.executeUpdate();
            // Checking rows were affected by the query
            if(i  > 0){
                System.out.println("Your data was successfully inserted collected in the Galamsey database");
            }
            else {
                System.out.println("Your data was not collected");
            }

            // terminating the connection with mysql
            conn.close();

            /* Exception handling for any errors */
        } catch(Exception ex){
            ex.printStackTrace();
        }

    }

    /**
     * This method gets the data from the database
     * which is the galamsey data input into the database
     */
    public void getData(){
        // A query to select and read from the database of table galamsey
        String query = "select * from galamsey";

        // setting connection to null
        conn = null;

        // error handling
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");

            // This is use to register a driver for the mysql database and with the getConnection
            // method to establish a database connection with mysql
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ICP_Project?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");


            Statement st = conn.createStatement();
            // rs holds all the results of the query thus a table of the select query
            rs = st.executeQuery(query);
            System.out.println("Records from database");


            while(rs.next()){

                String Vegetation_colour = rs.getString("veg_col");
                int Colour_value = rs.getInt("col_value");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("latitude");
                int year = rs.getInt("event_year");
                String observatory_name = rs.getString("observatory_name");
                System.out.println("Veg_col: "+ Vegetation_colour  + "\ncol_value: " + Colour_value +"\nLatitude: "+
                        latitude +"\nLongitude: "+ longitude +"\nYear: "+ year +"\nObservatory name:"+ observatory_name );
            }
            st.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * This method returns the string representation of the
     * attribute of te galamsey class
     * @return
     */
    @Override
    public String toString() {
        return "Galamsey{" +
                "Veg_col='" + this.getVeg_col() + '\'' +
                ", col_value=" + this.getCol_value() +
                ", latitude=" + this.getLatitude() +
                ", longitude=" + this.getLongitude() +
                ", year=" + this.getYear() +
                '}';
    }





}
