import java.sql.SQLDataException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MonitoringIO{
    public void display_options(){
        System.out.println("1. Enter observatory data and List of events (Galamsey) \n2. Enter the Galamsey data");
        System.out.println("3. View monitoring statistics");
        System.out.println("4. Enter to view Galamsey records ");
        System.out.println("4. Enter to view Observatory records ");
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
        Galamsey galamsey = new Galamsey();
        Observatory observatory = new Observatory();

        Scanner input = new Scanner(System.in);
        display_options();
        switch (input.nextInt()){
            case 1:
                System.out.println ( "You picked option 1" );
                observatory.Observatory_intake();
                observatory.intake_Data_Observatory();


                galamsey = observatory.add_Galamsey();
                galamsey.intakeData_Galamsey();

                query();

                break;
            case 2:
                System.out.println ( "You picked option 2" );
                galamsey.Ga_details();
                galamsey.intakeData_Galamsey();
                query();
                break;
            case 3:
                System.out.println ( "You picked option 3" );
                observatory.getAvgValue();
                observatory.getLargestValue();
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

            default:
                System.err.println ( "Unrecognized option" );
                break;
        }
    }
    public static void main( String[] args) throws SQLDataException {
        new MonitoringIO();
    }

}
