import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;
import java.util.*;
import java.sql.*;

public class Galamsey {
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

    public Galamsey(String veg_col, int col_value, double latitude, double longitude, int year) {
        this.Veg_col = veg_col;
        this.col_value = col_value;
        this.latitude = latitude;
        this.longitude = longitude;
        this.year = year;


    }

    public String getVeg_col() {
        return Veg_col;
    }

    public void setVeg_col(String veg_col) {
        Veg_col = veg_col;
    }

    public int getCol_value() {
        return col_value;
    }

    public void setCol_value(int col_value) {
        this.col_value = col_value;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }



    public String getObservatory_name() {
        return observatory_name;
    }

    public void setObservatory_name(String observatory_name) {
        this.observatory_name = observatory_name;
    }


    /**
     * A method that takes the data or details of the galamsey
     * and then adds it to the database
     */
    public void Ga_details(){
        try {

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the Vegetation color: ");
            String veg = sc.nextLine();
            veg.toLowerCase();
            if (veg.equals("green") || veg.equals("red") || veg.equals("yellow")){
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

        //  The insertion query
        String sql = "insert into galamsey(Veg_col, col_value, latitude , longitude , event_year, observatory_name) VALUES(?,?,?,?,?,?);" ;

        PreparedStatement pstmt = null;

        conn = null;

        // PreparedStatement is an interface used to execute the parameterized SQL query

        try{
            //Observatory observatory = new Observatory();
            // This load the driver's class at
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

    public void getData(){
        String query = "select * from galamsey";
        conn = null;

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");

            // This is use to register a driver for the mysql database and with the getConnection
            // method to establish a database connection with mysql
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ICP_Project?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");


            Statement st = conn.createStatement();
            // rs holds all the results of the query
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