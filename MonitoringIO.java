import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MonitoringIO{
    Monitoring mon;
    public void display_options(){
        System.out.println("1. Enter observatory data and List of events (Galamsey) \n2. Enter the Galamsey data");
        System.out.println("3. Enter to view the Average value and Largest colour value of an observatory");
        System.out.println("4. Enter to view Galamsey records ");
        System.out.println("5. Enter to view Observatory records ");
        System.out.println("6. View monitoring statistics ");
        System.out.println("What's your selection?; ");
    }

    public void query() throws SQLDataException {
        System.out.println("Do you want to proceed or quit?");
        System.out.println("To proceed enter 5");
        System.out.println("To quit enter 0");
        try {
            Scanner que = new Scanner(System.in);
            switch (que.nextInt()) {
                case 0:
                    System.out.println("Thank you and goodbye.");
                    break;

                case 5:
                    System.out.println("Please proceed.");
                    new MonitoringIO();
                    break;

                default:
                    System.err.println("Unrecognized option");
                    break;
            }
        }
        catch (InputMismatchException | SQLDataException ex){
            ex.printStackTrace();
        }
    }

    public MonitoringIO() throws SQLDataException {
        load();
        Galamsey galamsey = new Galamsey();
        Observatory observatory = new Observatory();

        Scanner input = new Scanner(System.in);
        display_options();
        switch (input.nextInt()){
            case 1:
                System.out.println ( "You picked option 1" );
                observatory.Observatory_intake();
                // The Hash table is been updated with the data from the user
                mon.observations.put(observatory.getName(),observatory);
                observatory.intake_Data_Observatory();


                galamsey = observatory.add_Galamsey();
                // The hash table is updated with data from the user
                mon.observations.get(observatory.getName()).add_Galamsey(galamsey);
                galamsey.intakeData_Galamsey();

                query();

                break;
            case 2:
                System.out.println ( "You picked option 2" );
                galamsey.Ga_details();
                // Getting the name of the observatory in order to add a galamsey
                // Indexing the hash table in order to get the observatory to add galamsey
                mon.observations.get(galamsey.getObservatory_name()).add_Galamsey(galamsey);
                galamsey.intakeData_Galamsey();
                query();
                break;
            case 3:
                System.out.println ( "You picked option 3" );
                // Taking the observatory name to find the average and largest value
                System.out.println("Enter the observatory name");
                Scanner in = new Scanner(System.in);
                // Handling white spaces
                String name = in.nextLine().trim();
                observatory= mon.observations.get(name);
                System.out.println(observatory.getAvgValue());
                System.out.println(observatory.getLargestValue());
                query();
                break;
            case 4:
                System.out.println("You picked option 4");
                galamsey.getData();
                query();
                break;
            case 5:
                System.out.println("You picked option 5");
                observatory.get_observatory_data();
                query();
                break;
            case 6:
                System.out.println("You picked option 6");
                System.out.println(mon.getLargestAvgColour());
                System.out.println(mon.getLargestColour());
                query();
                break;

            default:
                System.err.println ( "Unrecognized option" );
                break;
        }
    }
    public static void main( String[] args) throws SQLDataException {
        new MonitoringIO();
    }
    public void load() {
        mon = new Monitoring();
        Connection conn;
        ResultSet rs;

        String query = "select * from galamsey";
        conn = null;

        String sql = "select * from observatory";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ICP_Project?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
            Statement st = conn.createStatement();

            rs  = st.executeQuery(sql);

            // iterate through the java


            while(rs.next()) {
                String obser_name = rs.getString("observatory_name");
                String country = rs.getString("country_located");
                int year_event = rs.getInt("year_obsv");
                int area = rs.getInt("area_covered");

                mon.observations.put(obser_name.trim(),new Observatory(obser_name,country,year_event,area));

            }
            st = conn.createStatement();
            // rs holds all the results of the query
            rs = st.executeQuery(query);
            System.out.println("Records from database");


            while(rs.next()){
                String Vegetation_colour = rs.getString("veg_col");
                int Colour_value = rs.getInt("col_value");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("latitude");
                int year = rs.getInt("event_year");
                String observatory_name = rs.getString("observatory_name").trim();

                mon.observations.get(observatory_name.trim()).add_Galamsey(new Galamsey(Vegetation_colour,Colour_value,latitude,longitude,year));
            }


            st.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
