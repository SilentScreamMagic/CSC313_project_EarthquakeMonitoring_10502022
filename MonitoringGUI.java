package code;



import java.util.Hashtable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MonitoringGUI extends Application{
	Stage mainStage;
	ComboBox<String> type;
	GridPane gpane;
	Hashtable<String, Observatory> ob;

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		load();
		mainStage=primaryStage;
		
		type = new ComboBox<String>();
		type.getItems().addAll("Observation","Galamsey");
		type.setOnAction(event->form());
		
		gpane = new GridPane();
		gpane.add(type,0,0);
	
		Scene selectScene = new Scene(gpane,600,300);
		mainStage.setTitle("Galamsey Research");
		mainStage.setScene(selectScene);
		mainStage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	public void form() {
		if(!(type.getValue()==null)){
			if (type.getValue().equals("Galamsey")) {
				mainStage.setScene(galamsey());
			}else {
				mainStage.setScene(observatory());
			}
		}
		
		mainStage.show();
		
	}
	
	public Scene galamsey() {
		load();
		GridPane grid = new GridPane();
		grid.add(type, 0, 0);
		
		Label veg = new Label("Vegetation Colour");
		TextField veg1 = new TextField();
		grid.add(veg, 0, 2);
		grid.add(veg1, 2, 2);
		
		Label col = new Label("Colour Value");
		TextField col1 = new TextField();
		grid.add(col, 0, 3);
		grid.add(col1, 2, 3);
		
		Label pos = new Label("Position (Longitute,Latitude)");
		TextField pos1 = new TextField();
		TextField pos2 = new TextField();
		grid.add(pos, 0, 4);
		grid.add(pos1, 2, 4);
		grid.add(pos2, 4, 4);
		
		Label yr = new Label("Event Year");
		TextField yr1 = new TextField();
		grid.add(yr, 0, 5);
		grid.add(yr1, 2, 5);
		
		Label obs = new Label("Observation");
		ComboBox<String> obs1 = new ComboBox<String>();
		obs1.getItems().addAll(ob.keySet());
		grid.add(obs, 0, 6);
		grid.add(obs1, 2, 6);
		
		Button submit = new Button("Submit");
		grid.add(submit, 2, 7);
		submit.setOnAction(event->galam_data(Integer.parseInt(veg1.getText()), Integer.parseInt(col1.getText()),
				Double.parseDouble(pos1.getText()), Double.parseDouble(pos1.getText()), Integer.parseInt(yr1.getText()), obs1.getValue()));
		return new Scene(grid,600,300);
	}
	
	public Scene observatory() {
		GridPane grid = new GridPane();
		grid.add(type, 0, 0);
		
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
		submit.setOnAction(event-> obser_data(noObs.getText(),nOC.getText(),Integer.parseInt(yr.getText()),Double.parseDouble(area.getText())));
		grid.add(submit, 2, 6);
		
		return new Scene(grid,600,300);
	}
	
	public void galam_data(int veg_col,int col,double lon,double lat, int year,String obs) {
		Galamsey galamsey = new Galamsey(veg_col, col, lon, lat, year);
	   
		ob.get(obs).addGalamsey(galamsey);
		//Insert galamsey int the galamsey table
	}
	
	public void load() {
		ob = new Hashtable<String, Observatory>();
		/*
		 * for( int i =0;i<#(The observation table length);i++){
		 * 		#(create observation object from tuple data)
		 * 		ob.put(#(observation name), #(observation object))
		 */
	}
	public void obser_data(String name, String country,int year,double area) {
		Observatory observe = new Observatory(name,country,year,area);
		ob.put(name, observe);
	}
	
	

}

