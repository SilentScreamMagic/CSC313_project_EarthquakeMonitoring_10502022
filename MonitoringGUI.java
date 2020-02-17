package code;

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

public class MonitoringGUI extends Application{
	Stage mainStage;
	TableView<Galamsey> galamTable;
	GridPane gpane;
	Monitoring mon;
	MenuBar menu;

	ComboBox<String> whichObs;

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		load();
		mainStage=primaryStage;
		mainStage.setTitle("Galamsey Research");
		mainStage.setScene(addWindow());
		mainStage.show();
		
		
	}
	public Scene addWindow() {
		
		menu = new MenuBar();
		Menu obs = new Menu("Observations");
		obs.setOnAction(event->createObserTable());
		Menu gal = new Menu("Galamsey");
		gal.setOnAction(event->createGalamTable(null));
		Menu view = new Menu("View");
		view.getItems().addAll(obs,gal);
		
		
		Menu add = new Menu("Add Data");
		Menu adobs = new Menu("Add Observatory");
		Menu adgal = new Menu("Add Galamsey");
		adobs.setOnAction(event->observatory());
		adgal.setOnAction(event->galamsey());
		
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
	
	
	
	public void galamsey() {
		
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
		
		Label ob = new Label("Observation");
		ComboBox<String> obs1 = new ComboBox<String>();
		obs1.getItems().addAll(mon.observations.keySet());
		grid.add(ob, 0, 6);
		grid.add(obs1, 2, 6);
		
		Button submit = new Button("Submit");
		grid.add(submit, 2, 7);
		submit.setOnAction(event->galam_data(Integer.parseInt(veg1.getText()), Integer.parseInt(col1.getText()),
				Double.parseDouble(pos1.getText()), Double.parseDouble(pos1.getText()), Integer.parseInt(yr1.getText()), obs1.getValue()));
		mainStage.setScene( new Scene(grid,600,300));
	}
	
	public void observatory() {
		
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
		submit.setOnAction(event-> obser_data(noObs.getText(),nOC.getText(),Integer.parseInt(yr.getText()),Double.parseDouble(area.getText())));
		grid.add(submit, 2, 6);
		
		mainStage.setScene(new Scene(grid,600,300));
	}
	
	public void galam_data(int veg_col,int col,double lon,double lat, int year,String obs) {
		Galamsey galamsey = new Galamsey(veg_col, col, lon, lat, year);
	   
		mon.observations.get(obs).addGalamsey(galamsey);
		//Insert galamsey into the galamsey table
	}
	
	public void load() {
		mon = new Monitoring();
		/*
		 * for( int i =0;i<#(The observation table length);i++){
		 * 		#(create observation object from tuple data)
		 * 		ob.put(#(observation name), #(observation object))
		 */
	}
	public void obser_data(String name, String country,int year,double area) {
		Observatory observe = new Observatory(name,country,year,area);
		mon.observations.put(name, observe);
		// add to observatory table
	}
	
	
	 public void createGalamTable(String name) {
		ComboBox<String> whichObs = new ComboBox<String>();
		whichObs.getItems().addAll(mon.observations.keySet());
		whichObs.setOnAction(event->createGalamTable(whichObs.getValue()));
		GridPane b = new GridPane();
		Label lCol= new Label("Largest Colour Value: 0");
		Label aCol = new Label("Average Colour Value: 0");
		ObservableList<Galamsey> galam = FXCollections.observableArrayList();
		if (name!= null) {
			Observatory o =mon.observations.get(name);
			lCol.setText("Largest Colour Value: "+ o.getLargestValue());
			aCol.setText("Average Colour Value: "+ o.getAvgValue());
			for (int i = 0; i < o.getObservations().length; i++) {
				galam.add(o.getObservations()[i]);
			}
		}
		
		TableColumn<Galamsey,Integer> vegcolColumn= new TableColumn<>("Vegetation Colour");
		vegcolColumn.setMinWidth(150);
		vegcolColumn.setCellValueFactory(new PropertyValueFactory<>("veg_col"));
		
		TableColumn<Galamsey,Integer> colColumn= new TableColumn<>("Colour Value");
		colColumn.setMinWidth(150);
		colColumn.setCellValueFactory(new PropertyValueFactory<>("col"));
		
		TableColumn<Galamsey,Double> lon= new TableColumn<>("Longitute");
		lon.setMinWidth(100);
		lon.setCellValueFactory(new PropertyValueFactory<>("lon"));
		
		TableColumn<Galamsey,Double> lat= new TableColumn<>("Latitude");
		lat.setMinWidth(100);
		lat.setCellValueFactory(new PropertyValueFactory<>("lat"));
		
		TableColumn<Galamsey,Integer> year= new TableColumn<>("Year");
		year.setMinWidth(100);
		year.setCellValueFactory(new PropertyValueFactory<>("year"));
		
		
		
		galamTable = new TableView<>();
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
	
	 public void createObserTable() {
			GridPane b = new GridPane();
			ObservableList<Observatory> observe = FXCollections.observableArrayList();
			Label lCol= new Label("Largest Colour Value: 0");
			Label aCol = new Label("Average Colour Value: 0");
			Collection<Observatory> g =mon.observations.values();
			
			for (Observatory observatory : g) {
				lCol.setText("Largest Ever Colour Value: "+mon.getLargestAvgColour());
				aCol.setText("Largest Average Colour Value: "+mon.getLargestAvgColour());
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
	 public void galamLimit(String name,int limit) {
		Stage stage = new Stage();
		
		ObservableList<Galamsey> galam = FXCollections.observableArrayList();
		if (name!= null) {
			Observatory o =mon.observations.get(name);
			for (int i = 0; i < o.getObservations().length; i++) {
				if(o.getObservations()[i].getCol()>limit) {
					galam.add(o.getObservations()[i]);
				}
				
			}
		}
		
		TableColumn<Galamsey,Integer> vegcolColumn= new TableColumn<>("Vegetation Colour");
		vegcolColumn.setMinWidth(150);
		vegcolColumn.setCellValueFactory(new PropertyValueFactory<>("veg_col"));
		
		TableColumn<Galamsey,Integer> colColumn= new TableColumn<>("Colour Value");
		colColumn.setMinWidth(150);
		colColumn.setCellValueFactory(new PropertyValueFactory<>("col"));
		
		TableColumn<Galamsey,Double> lon= new TableColumn<>("Longitute");
		lon.setMinWidth(100);
		lon.setCellValueFactory(new PropertyValueFactory<>("lon"));
		
		TableColumn<Galamsey,Double> lat= new TableColumn<>("Latitude");
		lat.setMinWidth(100);
		lat.setCellValueFactory(new PropertyValueFactory<>("lat"));
		
		TableColumn<Galamsey,Integer> year= new TableColumn<>("Year");
		year.setMinWidth(100);
		year.setCellValueFactory(new PropertyValueFactory<>("year"));
		
		
		
		galamTable = new TableView<>();
		galamTable.setItems(galam);
		galamTable.getColumns().addAll(vegcolColumn,colColumn,lon,lat,year);
		VBox vbox = new VBox();
		vbox.getChildren().addAll(galamTable);
		
		Scene s = new Scene(vbox,600,400);
		stage.setScene(s);
		stage.show();
		 
	 }
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
	 public void obGalamLimit(int limit) {
			Stage stage = new Stage();
			
			ObservableList<Galamsey> galam = FXCollections.observableArrayList();

			
			galam.addAll(mon.getObservations(limit));
				
			
			
			TableColumn<Galamsey,Integer> vegcolColumn= new TableColumn<>("Vegetation Colour");
			vegcolColumn.setMinWidth(150);
			vegcolColumn.setCellValueFactory(new PropertyValueFactory<>("veg_col"));
			
			TableColumn<Galamsey,Integer> colColumn= new TableColumn<>("Colour Value");
			colColumn.setMinWidth(150);
			colColumn.setCellValueFactory(new PropertyValueFactory<>("col"));
			
			TableColumn<Galamsey,Double> lon= new TableColumn<>("Longitute");
			lon.setMinWidth(100);
			lon.setCellValueFactory(new PropertyValueFactory<>("lon"));
			
			TableColumn<Galamsey,Double> lat= new TableColumn<>("Latitude");
			lat.setMinWidth(100);
			lat.setCellValueFactory(new PropertyValueFactory<>("lat"));
			
			TableColumn<Galamsey,Integer> year= new TableColumn<>("Year");
			year.setMinWidth(100);
			year.setCellValueFactory(new PropertyValueFactory<>("year"));
			
			
			
			galamTable = new TableView<>();
			galamTable.setItems(galam);
			galamTable.getColumns().addAll(vegcolColumn,colColumn,lon,lat,year);
			VBox vbox = new VBox();
			vbox.getChildren().addAll(galamTable);
			
			Scene s = new Scene(vbox,600,400);
			stage.setScene(s);
			stage.show();
			 
		 }

}

