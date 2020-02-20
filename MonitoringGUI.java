import java.sql.DriverManager;
import java.sql.SQLDataException;
import java.sql.Statement;
import java.util.Collection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;


public class MonitoringGUI extends Application{
    Stage mainStage;

    GridPane gpane;
    Monitoring mon;
    MenuBar menu;

    ComboBox<String> whichObs;

    /**
     * The start method is the initial method of the GUI to create the first window
     * @param primaryStage is the intial Stage that is viewed
     */

    @Override
    public void start(Stage primaryStage)  {

        load();
        mainStage=primaryStage;
        mainStage.setTitle("Galamsey Monitoring Application");
        mainStage.setScene(addWindow());
        mainStage.show();


    }

    /**
     * The addWindow method create the menu to be displayed on the stages
     * @return selectScene is the view of the menu
     */
    public Scene addWindow() {

        menu = new MenuBar();
        Menu view = new Menu("View");
        Menu obs = new Menu("Observatory");
        obs.setOnAction(event->createObserTable(view));
        Menu gal = new Menu("Galamsey");
        gal.setOnAction(event->createGalamTable(view,null));

        view.getItems().addAll(obs,gal);


        Menu add = new Menu("Add Data");
        Menu adobs = new Menu("Add Observatory");
        Menu adgal = new Menu("Add Galamsey");
        adobs.setOnAction(event->observatory(add));
        adgal.setOnAction(event->galamsey(add));

        add.getItems().addAll(adgal,adobs);


        menu.getMenus().addAll(add,view);


        gpane = new GridPane();

        gpane.add(menu, 0, 0);
        Scene selectScene = new Scene(gpane,600,300);
        return selectScene;

    }
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * The galamsey method creates a form-like window to input new galamsey objects into the database
     * @param clicked Clicked is the menu used to call the method and is passed in order to hide the dropdown on click
     */
    public void galamsey(Menu clicked) {
        if (clicked!=null){
            clicked.hide();
        }
        load();

        GridPane grid = new GridPane();
        grid.add(menu, 0, 0);

        Label veg = new Label("Vegetation Colour");
        TextField veg1 = new TextField();
        grid.add(veg, 0, 2);
        grid.add(veg1, 2, 2);

        Label col = new Label("Colour Value");
        TextField col1 = new TextField();
        grid.add(col, 0, 3);
        grid.add(col1, 2, 3);

        Label pos = new Label("Position (Longitude,Latitude)");
        TextField pos1 = new TextField();
        TextField pos2 = new TextField();
        grid.add(pos, 0, 4);
        grid.add(pos1, 2, 4);
        grid.add(pos2, 4, 4);

        Label yr = new Label("Event Year");
        TextField yr1 = new TextField();
        grid.add(yr, 0, 5);
        grid.add(yr1, 2, 5);

        Label ob = new Label("Observation");
        ComboBox<String> obs1 = new ComboBox<String>();
        obs1.getItems().addAll(mon.observations.keySet());
        grid.add(ob, 0, 6);
        grid.add(obs1, 2, 6);

        Button submit = new Button("Submit");
        grid.add(submit, 2, 7);
        submit.setOnAction(event-> {
            try {
                galam_data(veg1.getText(), Integer.parseInt(col1.getText()),
                        Double.parseDouble(pos1.getText()), Double.parseDouble(pos1.getText()), Integer.parseInt(yr1.getText()), obs1.getValue());
            } catch (SQLDataException e) {
                e.printStackTrace();
            }
        });
        mainStage.setScene( new Scene(grid,600,300));
    }

    /**
     * The observatory method creates a form-like window to input new observatory objects into the database
     * @param clicked Clicked is the menu used to call the method and is passed in order to hide the dropdown on click
     */
    public void observatory(Menu clicked) {
        if (clicked!=null){
            clicked.hide();
        }

        GridPane grid = new GridPane();
        grid.add(menu, 0, 0);

        Label noObs = new Label("Name of observatory");
        TextField noObs1 = new TextField();
        grid.add(noObs, 0, 2);
        grid.add(noObs1, 2, 2);

        Label nOC = new Label("Name of Country");
        TextField nOC1 = new TextField();
        grid.add(nOC, 0, 3);
        grid.add(nOC1, 2, 3);

        Label yr = new Label("Year of observatory");
        TextField yr1 = new TextField();
        grid.add(yr, 0, 4);
        grid.add(yr1, 2, 4);

        Label area = new Label("Area under observatory");
        TextField area1 = new TextField();
        grid.add(area, 0, 5);
        grid.add(area1, 2, 5);

        Button submit = new Button("Submit");
        submit.setOnAction(event-> {
            try {
                obser_data(noObs1.getText(),nOC1.getText(),Integer.parseInt(yr1.getText()),Double.parseDouble(area1.getText()));
            } catch (SQLDataException e) {
                e.printStackTrace();
            }
        });
        grid.add(submit, 2, 6);

        mainStage.setScene(new Scene(grid,600,300));
    }

    /**
     * Creates a galamsey object to the appropriate observation hashtable and add the data to the galamsey table in the database 
     * to ensure that both the database and the code have the same data
     * @param veg_col Vegetation colour of the galamsey
     * @param col Colour value of the galamsey
     * @param lon Longitude value of the location of the galamsey occurence
     * @param lat Latitude value of the location of the galamsey occurence
     * @param year The year of the galamsey occurence
     * @param obs The name of the observatory the galamsey occurence is part of
     * @throws SQLDataException
     */
    public void galam_data(String veg_col,int col,double lon,double lat, int year,String obs) throws SQLDataException {
        Galamsey galamsey = new Galamsey(veg_col,col,lon,lat,year);
        galamsey.setObservatory_name(obs);

        mon.observations.get(obs).add_Galamsey(galamsey);
        galamsey(null);
        galamsey.intakeData_Galamsey();

    }
    /**
    * The load methods loads the data in the database into the hastable
    */
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
                String observatory_name = rs.getString("observatory_name");

                mon.observations.get(observatory_name.trim()).add_Galamsey(new Galamsey(Vegetation_colour,Colour_value,latitude,longitude,year));
            }


            st.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
    * The obser_data method adds the observatory data into the observatory table in the database as well as adding observatory
    * object to the hashtable 
    * @param name The name of the observatory
    * @param country The country of the observatory
    * @param year The year of the observatory
    * @param area The area covered by the observatory
    */
    public void obser_data(String name, String country, int year, double area) throws SQLDataException {
        Observatory observe = new Observatory(name,country,year,area);
        mon.observations.put(name, observe);
        // add to observatory table
        observatory(null);
        observe.intake_Data_Observatory();
    }

    /** 
    * View the galamsey table of a specified observatory
    * @param clicked Clicked is the menu used to call the method and is passed in order to hide the dropdown on click
    * @param name The observation name that the user wishes to view
    */
    public void createGalamTable(Menu clicked, String name) {
        if (clicked!=null)
            clicked.hide();
        ComboBox<String> whichObs = new ComboBox<String>();
        whichObs.getItems().addAll(mon.observations.keySet());
        whichObs.setOnAction(event->createGalamTable(null,whichObs.getValue()));
        GridPane b = new GridPane();
        Label lCol= new Label("Largest Colour Value: 0");
        Label aCol = new Label("Average Colour Value: 0");
        ObservableList<Galamsey> galam = FXCollections.observableArrayList();
        if (name!= null) {
            Observatory o =mon.observations.get(name);
            lCol.setText("Largest Colour Value: "+ o.getLargestValue());
            aCol.setText("Average Colour Value: "+ o.getAvgValue());
            for (int i = 0; i < o.getObservations().size(); i++) {
                galam.add(o.getObservations().get(i));
            }
        }

        TableColumn<Galamsey,String> vegcolColumn= new TableColumn<>("Vegetation Colour");
        vegcolColumn.setMinWidth(150);
        vegcolColumn.setCellValueFactory(new PropertyValueFactory<>("veg_col"));

        TableColumn<Galamsey,Integer> colColumn= new TableColumn<>("Colour Value");
        colColumn.setMinWidth(150);
        colColumn.setCellValueFactory(new PropertyValueFactory<>("col_value"));

        TableColumn<Galamsey,Double> lon= new TableColumn<>("longitude");
        lon.setMinWidth(100);
        lon.setCellValueFactory(new PropertyValueFactory<>("longitude"));

        TableColumn<Galamsey,Double> lat= new TableColumn<>("Latitude");
        lat.setMinWidth(100);
        lat.setCellValueFactory(new PropertyValueFactory<>("latitude"));

        TableColumn<Galamsey,Integer> year= new TableColumn<>("Year");
        year.setMinWidth(100);
        year.setCellValueFactory(new PropertyValueFactory<>("year"));



        TableView<Galamsey> galamTable = new TableView<>();
        galamTable.setItems(galam);
        galamTable.getColumns().addAll(vegcolColumn,colColumn,lon,lat,year);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(galamTable);

        Button limit = new Button("Galamsey at Limit");
        limit.setOnAction(event->fromGalamseyAtLimit(name));

        b.add(menu, 0, 0);
        b.add(whichObs,0,1);
        b.add(vbox,0,2);
        b.add(aCol, 0, 3);
        b.add(lCol,0,4);
        b.add(limit, 0, 5);

        Scene s = new Scene(b,600,400);
        mainStage.setScene(s);
    }
    /** 
    * View the observatory table
    * @param clicked Clicked is the menu used to call the method and is passed in order to hide the dropdown on click
    */
    public void createObserTable(Menu clicked) {
        clicked.hide();

        GridPane b = new GridPane();
        Label lCol= new Label("Largest Colour Value: 0");
        Label aCol = new Label("Average Colour Value: 0");

        ObservableList<Observatory> observe = FXCollections.observableArrayList();
        lCol.setText("Largest Ever Colour Value: "+mon.getLargestAvgColour());
        aCol.setText("Largest Average Colour Value: "+mon.getLargestAvgColour());
        System.out.println(mon.getLargestAvgColour() +" "+mon.getLargestColour() );
        Collection<Observatory> g =mon.observations.values();

        for (Observatory observatory : g) {

            observe.add(observatory);
        }

        TableColumn<Observatory,String> name= new TableColumn<>("Name");
        name.setMinWidth(150);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Observatory,String> country= new TableColumn<>("Country");
        country.setMinWidth(150);
        country.setCellValueFactory(new PropertyValueFactory<>("country"));

        TableColumn<Observatory,Integer> year= new TableColumn<>("Year");
        year.setMinWidth(100);
        year.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Observatory,Double> area= new TableColumn<>("Area Covered");
        area.setMinWidth(200);
        area.setCellValueFactory(new PropertyValueFactory<>("areaCovered"));

        TableView<Observatory> obsTable = new TableView<>();
        obsTable.setItems(observe);
        obsTable.getColumns().addAll(name,country,year,area);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(obsTable);

        Button limit = new Button("Galamsey at Limit");
        limit.setOnAction(event->fromObservatoryAtLimit());

        b.add(menu, 0, 0);

        b.add(obsTable,0,2);
        b.add(aCol, 0, 3);
        b.add(lCol,0,4);
        b.add(limit, 0, 5);

        Scene s = new Scene(b,600,400);
        mainStage.setScene(s);
    }
    /** 
    * View the Galamsey data in a given observatory that have a colour value greater than a given value
    * @param name The name of the observatory currently being viewed
    */
    public void fromGalamseyAtLimit(String name) {
        Stage displayLimit = new Stage();
        Label limit = new Label("Enter the colour value range");
        TextField text = new TextField();
        Button submit = new Button("Submit");

        submit.setOnAction(event->galamLimit(name, Integer.parseInt(text.getText())));
        VBox vbox= new VBox();
        vbox.getChildren().addAll(limit,text,submit);
        Scene s = new Scene(vbox,300,300);
        displayLimit.setScene(s);
        displayLimit.show();

    }
    /**
    * View the galamsey data with a colour value greater than the limit in the observatory with the given name 
    * @param name The observatory name to be viewed from
    * @param limit The limit at which the galamsey objects are filtered
    */
    public void galamLimit(String name,int limit) {
        Stage stage = new Stage();

        ObservableList<Galamsey> galam = FXCollections.observableArrayList();
        if (name!= null) {
            Observatory o =mon.observations.get(name);
            for (int i = 0; i < o.getObservations().size(); i++) {
                if(o.getObservations().get(i).getCol_value()>limit) {
                    galam.add(o.getObservations().get(i));
                }

            }
        }

        TableColumn<Galamsey,String> vegcolColumn= new TableColumn<>("Vegetation Colour");
        vegcolColumn.setMinWidth(150);
        vegcolColumn.setCellValueFactory(new PropertyValueFactory<>("veg_col"));

        TableColumn<Galamsey,Integer> colColumn= new TableColumn<>("Colour Value");
        colColumn.setMinWidth(150);
        colColumn.setCellValueFactory(new PropertyValueFactory<>("col_value"));

        TableColumn<Galamsey,Double> lon= new TableColumn<>("Longitude");
        lon.setMinWidth(100);
        lon.setCellValueFactory(new PropertyValueFactory<>("longitude"));

        TableColumn<Galamsey,Double> lat= new TableColumn<>("Latitude");
        lat.setMinWidth(100);
        lat.setCellValueFactory(new PropertyValueFactory<>("latitude"));

        TableColumn<Galamsey,Integer> year= new TableColumn<>("Year");
        year.setMinWidth(100);
        year.setCellValueFactory(new PropertyValueFactory<>("year"));



        TableView<Galamsey> galamTable = new TableView<>();
        galamTable.setItems(galam);
        galamTable.getColumns().addAll(vegcolColumn,colColumn,lon,lat,year);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(galamTable);

        Scene s = new Scene(vbox,600,400);
        stage.setScene(s);
        stage.show();

    }
    /**
    * View the galamsey data with a colour value greater than the limit in the database
    * @param limit The limit at which the galamsey objects are filtered
    */
    public void fromObservatoryAtLimit() {
        Stage displayLimit = new Stage();
        Label limit = new Label("Enter the colour value range");
        TextField text = new TextField();
        Button submit = new Button("Submit");

        submit.setOnAction(event->obGalamLimit( Integer.parseInt(text.getText())));
        VBox vbox= new VBox();
        vbox.getChildren().addAll(limit,text,submit);
        Scene s = new Scene(vbox,300,300);
        displayLimit.setScene(s);
        displayLimit.show();

    }
    /**
    * View the galamsey data with a colour value greater than the limit in the database 
    * @param limit The limit at which the galamsey objects are filtered
    */
    public void obGalamLimit(int limit) {
        Stage stage = new Stage();

        ObservableList<Galamsey> galam = FXCollections.observableArrayList();



        galam.addAll(mon.getObservations(limit));


        TableColumn<Galamsey,String> vegcolColumn= new TableColumn<>("Vegetation Colour");
        vegcolColumn.setMinWidth(150);
        vegcolColumn.setCellValueFactory(new PropertyValueFactory<>("veg_col"));

        TableColumn<Galamsey,Integer> colColumn= new TableColumn<>("Colour Value");
        colColumn.setMinWidth(150);
        colColumn.setCellValueFactory(new PropertyValueFactory<>("col_value"));

        TableColumn<Galamsey,Double> lon= new TableColumn<>("Longitude");
        lon.setMinWidth(100);
        lon.setCellValueFactory(new PropertyValueFactory<>("longitude"));

        TableColumn<Galamsey,Double> lat= new TableColumn<>("Latitude");
        lat.setMinWidth(100);
        lat.setCellValueFactory(new PropertyValueFactory<>("latitude"));

        TableColumn<Galamsey,Integer> year= new TableColumn<>("Year");
        year.setMinWidth(100);
        year.setCellValueFactory(new PropertyValueFactory<>("year"));



        TableView<Galamsey> galamTable = new TableView<>();
        galamTable.setItems(galam);
        galamTable.getColumns().addAll(vegcolColumn,colColumn,lon,lat,year);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(galamTable);

        Scene s = new Scene(vbox,600,400);
        stage.setScene(s);
        stage.show();

    }

}
