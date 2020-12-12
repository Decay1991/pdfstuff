package com.tiago.pdfstuff;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class App extends Application
{

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("introPage.fxml"));
		stage.setTitle("PDF Stuff");
		stage.setScene(new Scene(root, 700, 400));
		stage.show();
	}

	public static void main(String[] args) {

		launch(args);
	}
}


