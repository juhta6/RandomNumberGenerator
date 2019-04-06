package RNG;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class Main extends Application {
	
	private final GridPane gridPane = new GridPane();
	private final Scene scene = new Scene(gridPane, 400, 200);
	private final TextField input1 = new TextField();
	private final TextField input2 = new TextField();
	private final TextArea output = new TextArea();
	private final ToggleButton submit = new ToggleButton("Start");
	private final Button clear = new Button("Clear");
	private final HBox hBox1 = new HBox();
	private final HBox hBox2 = new HBox();
	
	private String in1,in2;
	private int int1, int2;
	private int min, max;
	private int no = 0;
	
	private TimerTask task;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		scene.getStylesheets().add("app.css");
		
		hBox1.getChildren().addAll(input1, input2);
		hBox1.setSpacing(10);
		HBox.setHgrow(input1, Priority.ALWAYS);
		HBox.setHgrow(input2, Priority.ALWAYS);
		
		hBox2.getChildren().addAll(submit, clear);
		hBox2.setSpacing(10);
		HBox.setHgrow(submit, Priority.ALWAYS);
		HBox.setHgrow(clear, Priority.ALWAYS);
		
		input1.setPromptText("Enter your first value here");
		input2.setPromptText("Enter your second value here");
		
		output.setEditable(false);
		output.setFocusTraversable(false);
		output.setPromptText("Output");
		
		gridPane.add(hBox1, 0, 0);
		gridPane.add(hBox2, 0, 1);
		gridPane.add(output, 0, 2);
		
		gridPane.setPadding(new Insets(10,10,10,10));
		gridPane.setVgap(10);
		
		primaryStage.centerOnScreen();
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Word check");
		primaryStage.show();
		
		submit.setDisable(true);
		
		input1.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
				if (newValue.intValue() > oldValue.intValue()) {
					  char ch = input1.getText().charAt(oldValue.intValue());
					  // Check if the new character is the number or other's
					  if (!(ch >= '0' && ch <= '9' )) {
						   // if it's not number then just setText to previous one
							  input1.setText(input1.getText().substring(0,input1.getText().length()-1));
					  }
				}
				checkInputs();
			}
		});
		
		input2.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
				if (newValue.intValue() > oldValue.intValue()) {
					  char ch = input2.getText().charAt(oldValue.intValue());
					  // Check if the new character is the number or other's
					  if (!(ch >= '0' && ch <= '9' )) {
						   // if it's not number then just setText to previous one
							  input2.setText(input2.getText().substring(0,input2.getText().length()-1));
					  }
				}
				checkInputs();
			}
		});
		
		submit.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				
				if(submit.isSelected()) {
					submit.setText("Pause");
					new Timer().schedule(task = new TimerTask() {
						public void run() {
							if(!in1.isEmpty() && !in2.isEmpty() && int1 != int2) {
								no++;
								String outputString = no+". "+getRandom(min, max)+"\n";
								output.appendText(outputString);
							}
						}
					}
				, 100L, 1000L);
				} else {
					submit.setText("Resume");
					submit.setSelected(false);
					task.cancel();
				}
			}
		});
		
		clear.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				input1.clear();
				input2.clear();
				output.clear();
				submit.setText("Start");
				submit.setSelected(false);
				task.cancel();
				no = 0;
			}
		});
	


	}
	
	private void checkInputs() {
		
		in1 = input1.getText();
		in2 = input2.getText();
		
		if(!in1.isEmpty() && !in2.isEmpty()) {
			 
			int1 = Integer.parseInt(in1);
			int2 = Integer.parseInt(in2);
			
			if(int1 != int2) {
				submit.setDisable(false);
				
				if(int1 > int2) {
					min = int2;
					max = int1;
				} else if(int1 < int2) {
					min = int1;
					max = int2;
				}
			} else submit.setDisable(true);
		} else submit.setDisable(true);
	}
	
	private static int getRandom(int min, int max) {
		   return (int)(Math.random() * max) + min;
	}

	public static void main(String[] args) {
		launch(args);

	}



}
