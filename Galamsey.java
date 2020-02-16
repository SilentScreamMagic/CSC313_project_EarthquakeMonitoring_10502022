import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;
import java.util.*;
import java.sql.*;

public class Galamsey{
    private String Veg_col;
    private int col_value;
    private double latitude;
    private double longitude;
    private int year;
    private String observatory_name;
    private Connection conn;
    private Statement st;
    private ResultSet rs;

    public Galamsey(){} // default constructor

    public Galamsey(String veg_col, int col_value, double latitude, double longitude,int year, String observatory_name) {
        Veg_col = veg_col;
        this.col_value = col_value;
        this.latitude = latitude;
        this.longitude = longitude;
        this.year = year;
        this.observatory_name = observatory_name;
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
            setVeg_col(veg);

            System.out.println("Enter the color value: ");
            int col = sc.nextInt();
            setCol_value(col);

            System.out.println("Enter the latitude of the area: ");
            double lat = sc.nextDouble();
            setLatitude(lat);

            System.out.println("Enter the longitude of the area: ");
            double lon = sc.nextDouble();
            setLongitude(lon);

            System.out.println("Enter the year of event: ");
            int y = sc.nextInt();
            setYear(y);

            System.out.println("Enter the name of the Observatory: ");
            String o = sc.nextLine();
            setObservatory_name(o);
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
        String sql = "insert into galamsey(Veg_col, col_value, latitude,longitude, event_year, observatory_name) values(?,?,?,?,?,?)";

        String sql2 = "select count('"+ this.getObservatory_name()+"') as obervatorynameidentifier from observatory";

        PreparedStatement pstmt = null;

        /**
        try {
            int x = pstmt.executeUpdate(sql2);

            if (x <= 0){
                System.out.println("You can not insert into the galamsey because there is no data in the observatory");
            }
        }
        catch(SQLException ex){
            System.out.println("There is an error in the query");
        }
         */
        conn = null;

        // PreparedStatement is an interface used to execute the parameterized SQL query



        try{
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


            int i = pstmt.executeUpdate(sql);
            int x = pstmt.executeUpdate(sql2);

            if(i  > 0 && x > 0){
                System.out.println("Your data was successfully inserted collected");

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

}