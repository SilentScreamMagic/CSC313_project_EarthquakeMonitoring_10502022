import java.io.*;
import java.util.*;
import java.sql.*;


public class Reader_Csv {

    private Connection conn;
    private Statement st;

    public void read(String filecsv1) throws SQLException {
        String sql = "insert into Observatory(observatory_name, country_located, year_obsv ,area_covered) values(?,?,?,?)";
        PreparedStatement pstmt1 = null;
        conn = null;
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ICP_Project?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
            pstmt1 = conn.prepareStatement(sql);
            Scanner sc = new Scanner(System.in);

            File file = new File(filecsv1);

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            String [] Array;
            br.readLine(); // This will read the heading of the cvs file
            String first_line = null;


            // The loop will start from line 2 of the csv file
            while((line = br.readLine()) != null){
                Array = line.split(","); // the comma delimiter for the csv file
                    pstmt1.setString(1, Array[0]);
                    pstmt1.setString(2, Array[1]);
                    pstmt1.setInt(3, Integer.parseInt(Array[2]));
                    pstmt1.setDouble(4, Double.parseDouble(Array[3]));

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

                }
                System.out.println();
            conn.close();
            br.close();
            } catch (IOException | ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void Take_csv_Galamsey(String filecsv2){
        String sql = "insert into galamsey(Veg_col, col_value, latitude , longitude , event_year, observatory_name) VALUES(?,?,?,?,?,?);" ;

        PreparedStatement pstmt1 = null;

        conn = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ICP_Project?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
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

                pstmt1.setString(1, Array[0]);
                pstmt1.setInt(2, Integer.parseInt(Array[1]));
                pstmt1.setDouble(3, Double.parseDouble(Array[2]));
                pstmt1.setDouble(4, Double.parseDouble(Array[3]));
                pstmt1.setInt(5, Integer.parseInt(Array[4]));
                pstmt1.setString(6, Array[5]);


                int i = pstmt1.executeUpdate();
                // Checking rows were affected by the query
                if(i  > 0){
                    System.out.println("Your data was successfully inserted collected in the Galamsey database");
                }
                else {
                    System.out.println("Your data was not collected");
                }

            }
            // terminating the connection with mysq
            conn.close();
            br.close();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }

    public static void main(String [] args) throws SQLException {
        Reader_Csv bn = new Reader_Csv();
        String csvfile1 = "C:\\Users\\Eugene Daniels\\Desktop\\Book3.csv";
        String csvfile2 =  "C:\\Users\\Eugene Daniels\\Desktop\\Book4.csv";
        bn.Take_csv_Galamsey(csvfile2);
    }
}
