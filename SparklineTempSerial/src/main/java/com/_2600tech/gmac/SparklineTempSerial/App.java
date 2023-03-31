package com._2600tech.gmac.SparklineTempSerial;

import java.util.regex.Pattern;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;


public class App extends Application {
Tile gaugeSparklineTile,gaugeSparklineTile2;
SerialReader serialReader=new SerialReader();
PortListener portListener=new PortListener();
	@Override
	public void start(Stage stage) {
//		var javaVersion = SystemInfo.javaVersion();
//		var javafxVersion = SystemInfo.javafxVersion();
//		var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
		
		createGaugeSparklineTile();
		createSparklineTilehum();      
		GridPane rootPane = new GridPane();
		ComboBox<String> cmboPorts=new ComboBox<String>();
		    Button btnStart=new Button("Start");
		HBox controls = new HBox(cmboPorts,btnStart);
		       
		rootPane.add(gaugeSparklineTile, 1,0);
		rootPane.add(gaugeSparklineTile2, 2,0);

		rootPane.add(controls, 1,1);
//		rootPane.add(new StackPane(label), 1, 2);
//		       rootPane.add(new StackPane(label),1,2);
		controls.setAlignment(Pos.CENTER);
		     
		rootPane.setHgap(10);
		rootPane.setVgap(10);
		       
		var scene = new Scene(rootPane, 680, 420);
		stage.setScene(scene);
		stage.show();


		String portList[] = serialReader.getAvailablePortNames();
		for(String port : portList) {
		cmboPorts.getItems().add(port);
		}

		btnStart.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
		if (btnStart.getText().equals("Start")) {
		// TODO Auto-generated method stub
		btnStart.setText("Stop");
		int portIndex = cmboPorts.getSelectionModel().getSelectedIndex();
		serialReader.setPort(portIndex);
		portListener.tile = gaugeSparklineTile;
		portListener.tile2=gaugeSparklineTile2;
		serialReader.startListening(portListener);

		}
		else {
		btnStart.setText("Start");
		serialReader.closePort();

		}
		}

		});

	}
	@Override
	public void stop() throws Exception {
		super.stop();
		serialReader.closePort();
		}
    public static void main(String[] args) {
        launch();
    
    }
    private void createGaugeSparklineTile() {
    	gaugeSparklineTile = TileBuilder.create()
    		.skinType(SkinType.GAUGE)
    		.prefSize(800, 400)
      		.title("Temperature")
                    
    		.animated(true)
    		.textVisible(false)
    		.averagingPeriod(25)
    		.autoReferenceValue(true)               
    		.barColor(Tile.YELLOW_ORANGE)
    		.barBackgroundColor(Color.rgb(255, 255, 255, 0.1))
    		.sections(
          		new eu.hansolo.tilesfx.Section(0, 33, Tile.LIGHT_GREEN),
          		new eu.hansolo.tilesfx.Section(33, 67, Tile.YELLOW),
    			new eu.hansolo.tilesfx.Section(67, 100, Tile.LIGHT_RED))
    		.sectionsVisible(true)
    		.highlightSections(true)
    		.strokeWithGradient(true)
    		.fixedYScale(false)
    		.gradientStops(new Stop(0.0, Tile.LIGHT_GREEN),
    			new Stop(0.0, Tile.LIGHT_GREEN),
    			new Stop(0.33,Tile.YELLOW),
    			new Stop(0.67, Tile.YELLOW),
    			new Stop(0.67, Tile.LIGHT_RED),
    			new Stop(1.0, Tile.LIGHT_RED))
                    
    		.valueColor(Color.RED)
    		.unit("C")
    		.smoothing(true)
    		.build();
    	}
    private void createSparklineTilehum() {
    gaugeSparklineTile2  = TileBuilder.create()
    		.skinType(SkinType.GAUGE_SPARK_LINE)
    		.prefSize(800, 600)
      		.title("Humidity")
                    
    		.animated(true)
    		.textVisible(false)
    		.averagingPeriod(25)
    		.autoReferenceValue(true)
                    
                    
    		.barColor(Tile.YELLOW_ORANGE)
    		.barBackgroundColor(Color.rgb(255, 255, 255, 0.1))
    		.sections(
          		new eu.hansolo.tilesfx.Section(0, 33, Tile.LIGHT_GREEN),
          		new eu.hansolo.tilesfx.Section(33, 67, Tile.YELLOW),
    			new eu.hansolo.tilesfx.Section(67, 100, Tile.LIGHT_RED))
    		.sectionsVisible(true)
    		.highlightSections(true)
    		.strokeWithGradient(true)
    		.fixedYScale(false)
    		.gradientStops(new Stop(0.0, Tile.LIGHT_GREEN),
    			new Stop(0.0, Tile.LIGHT_GREEN),
    			new Stop(0.33,Tile.YELLOW),
    			new Stop(0.67, Tile.YELLOW),
    			new Stop(0.67, Tile.LIGHT_RED),
    			new Stop(1.0, Tile.LIGHT_RED))
                    
    		.valueColor(Color.RED)
    		.unit("%")
    		.smoothing(true)
    		.build();
}
}


