//package code;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Observatory{
    private String name;
    private String country;
    private int year;
    private double areaCovered;
    private Galamsey[] observation;
    private int count;
    private int highCol;
    private Connection conn;
    private Statement st;
    private ResultSet rs;

    public Observatory(){} // default constructor of the Observatory class

    public Observatory(String name, String country, int year, double areaCovered, Galamsey[] observation) {
        super();
        this.name = name;
        this.country = country;
        this.year = year;
        this.areaCovered = areaCovered;
        this.observation = new Galamsey[20];
        this.count=0;
    }

    public int getLargestValue() {
        return highCol;
    }

    public double getAvgValue() {
        Galamsey g1 = new Galamsey();
        int total=0;
        for (Galamsey galamsey : observation) {
            total=total+g1.getCol_value();
        }
        return (total/observation.length);
    }

    public Galamsey[] listGalamsey(int limit) {
        Galamsey g2 = new Galamsey();
        Galamsey[] g=new Galamsey[observation.length];
        int i =0;
        for (Galamsey galamsey : observation) {
            if (g2.getCol_value() >limit) {
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

    public Galamsey[] getObservations() {
        return observation;
    }

    public void addGalamsey(Galamsey galamsey) {
        Galamsey g2 = new Galamsey();
        if (count>=observation.length) {
            expand();
        }
        if (g2.getCol_value()>highCol) {
            highCol=g2.getCol_value();
        }
        observation[count++]=galamsey;
    }
    private void expand() {
        Galamsey[] g = new Galamsey[count*2];
        System.arraycopy(observation, 0, g, 0,observation.length);
        observation=g;
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

            System.out.println("Thanks for your data!");

        }
        catch(InputMismatchException e){
            e.printStackTrace();
        }
    }

    public void intake_Data_Observatory() throws SQLDataException {
        Observatory observatory = new Observatory();
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
                System.out.println("Your data was successfully inserted collected");
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



}
