import java.sql.SQLDataException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MonitoringIO{
    public void display_options(){
        System.out.println("1. Enter observatory data \n2. Enter 'galamsey' data \n3. View monitoring statistics");
        System.out.println("What's your selection?; ");
    }

    public void query() throws SQLDataException {
        System.out.println("Do you want to proceed or quit?");
        System.out.println("To proceed enter 9");
        System.out.println("To quit enter 0");
        try {
            Scanner que = new Scanner(System.in);
            switch (que.nextInt()) {
                case 0:
                    System.out.println("Thank you and goodbye.");
                    //display_options();
                    break;

                case 9:
                    System.out.println("Please proceed.");
                    new MonitoringIO();
                    break;

                default:
                    System.err.println("Unrecognized option");
                    break;
            }
        }
        catch (InputMismatchException ex){
            ex.printStackTrace();
        }
    }

    public MonitoringIO() throws SQLDataException {
        Galamsey galam = new Galamsey();
        Observatory obsv = new Observatory();
        DBConnection db = new DBConnection();
        Scanner input = new Scanner(System.in);
        display_options();
        switch (input.nextInt()){
            case 1:
                System.out.println ( "You picked option 1" );
                obsv.Observatory_intake();
                obsv.intake_Data_Observatory();
                query();
                //System.out.println("OBSERVATORY NAME: \nCOUNTRY: \nYEAR GALAMSEY OBSERVATIONS STARTED: \nAREA COVERED BY OBSERVATORY: ");
                break;
            case 2:
                System.out.println ( "You picked option 2" );
                galam.Ga_details();
                galam.intakeData_Galamsey();
                query();
                //System.out.println("VEGETATION COLOR: \nCOLOR VALUE: \nYEAR GALAMSEY OBSERVATIONS STARTED: \nAREA COVERED BY OBSERVATORY: ");
                break;
            case 3:
                System.out.println ( "You picked option 3" );
                //query();
                break;
            default:
                System.err.println ( "Unrecognized option" );
                break;
        }
    }
    public static void main( String[] args) throws SQLDataException {
        new MonitoringIO();
    }

}
