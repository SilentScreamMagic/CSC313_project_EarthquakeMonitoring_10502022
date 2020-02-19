/**
 * @authors Bryan Nii
 * @authors Eugene Daniels
 * @author Abena
 * @author Wisdom
 *
 * @version 1.0
 */

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class Observatory{
    // Creating instance variable of the observatory class
    private String name;
    private String country;
    private int year;
    private double areaCovered;
    private ArrayList<Galamsey> observation;
    private int highCol;
    private Connection conn;
    private Statement st;
    private ResultSet rs;

    public Observatory(){} // default constructor of the Observatory class

    // Non default constructor
    public Observatory(String name, String country, int year, double areaCovered) {
        this.name = name;
        this.country = country;
        this.year = year;
        this.areaCovered = areaCovered;
        this.observation = new ArrayList<Galamsey>(); //ArrayList of Galamsey objects

    }

    // Setter method for observation instance variable
    public Observatory(ArrayList<Galamsey> observation) {
        this.observation = observation;
    }

    /**
     * A method that returns the Largest value of the colour value
     * @return an int largest value
     */
    public int getLargestValue() {
        return highCol;
    }

    public double getAvgValue() {
        int total=0;
        for (Galamsey galamsey : observation) {
            total=total+galamsey.getCol_value();
        }
        return ((double) total/observation.size());
    }

    public Galamsey[] listGalamsey(int limit) {

        Galamsey[] g=new Galamsey[observation.size()];

        int i =0;
        for (Galamsey galamsey : observation) {
            if (galamsey.getCol_value() >limit) {
                g[i++]=galamsey;
            }
        }
        return g;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getAreaCovered() {
        return areaCovered;
    }

    public void setAreaCovered(double areaCovered) {
        this.areaCovered = areaCovered;
    }

    public ArrayList<Galamsey> getObservations() {
        return observation;
    }


    public void setObservation(ArrayList<Galamsey> observation) {
        this.observation = observation;
    }

    public void add_Galamsey(Galamsey galamsey){
        if (galamsey.getCol_value()>highCol) {
            highCol= galamsey.getCol_value();
        }
        observation.add(galamsey);
    }

    public Galamsey add_Galamsey(){
        Galamsey galamsey1 = new Galamsey();
        Scanner sc = new Scanner(System.in);
        observation = new ArrayList<Galamsey>();

        System.out.println("Enter the number of galamsey events you want to input: ");
        int num = sc.nextInt();

        for (int i =0 ; i < num; i ++){
            galamsey1 = new Galamsey();   // <- Creating a fresh instance of galamsey!
            galamsey1.Ga_details();

            if (galamsey1.getCol_value()>highCol) {
                highCol= galamsey1.getCol_value();
            }
            observation.add(galamsey1);
        }

        Iterator<Galamsey> galamseyIterator =  observation.iterator(); // Iterator for the Galamsey ArrayList

        while(galamseyIterator.hasNext()){   // Iterating through the arraylist
            System.out.println(galamseyIterator.next());

        }
        return galamsey1;

    }


    public void Observatory_intake(){
        try {

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the name of the observatory: ");
            String n = sc.nextLine();
            setName(n);


            System.out.println("Enter the Country observatory is located: ");
            String c = sc.nextLine();
            setCountry(c);

            System.out.println("Enter the year of the Observatory: ");
            int y = sc.nextInt();
            setYear(y);


            System.out.println("Enter the area covered by the Observatory: ");
            double a = sc.nextDouble();
            setAreaCovered(a);

        }
        catch(InputMismatchException e){
            e.printStackTrace();
        }
    }

    public void intake_Data_Observatory() throws SQLDataException {

        String sql = "insert into Observatory(observatory_name, country_located, year_obsv ,area_covered) values(?,?,?,?)";

        PreparedStatement pstmt1 = null;
        conn = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ICP_Project?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
            pstmt1 = conn.prepareStatement(sql);


            // This is for sql query 1
            pstmt1.setString(1, this.getName());
            pstmt1.setString(2, this.getCountry());
            pstmt1.setInt(3, this.getYear());
            pstmt1.setDouble(4, this.getAreaCovered());

            /**
             * This returns an int which shows the number of rows
             * affected by the query
             */
            int i = pstmt1.executeUpdate();
            // Checking rows were affected by the query
            if(i  > 0){
                System.out.println("Your data was successfully inserted collected in the Observatory database");
            }
            else {
                System.out.println("Your data was not collected");
            }

            // Terminating connection with mysql
            conn.close();

            /* error handling */
        } catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }


    public void get_observatory_data(){
        String sql = "select * from observatory";
        conn = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ICP_Project?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
            Statement st = conn.createStatement();

            ResultSet rs  = st.executeQuery(sql);

            // iterate through the java


            while(rs.next()){
                String obser_name = rs.getString("observatory_name");
                String country = rs.getString("country_located");
                int year = rs.getInt("year_obsv");
                int area = rs.getInt("area_covered");

                System.out.println("Observatory Name: "+ obser_name + "Country located: "+ country +"Year: "+ year +"Area covered: "+ area);
            }

            st.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "Observatory{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", year=" + year +
                ", areaCovered=" + areaCovered +
                ", observation=" + observation +
                ", highCol=" + highCol +
                '}';
    }


}
