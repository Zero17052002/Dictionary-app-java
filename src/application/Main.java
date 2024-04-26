package application;
import java.util.logging.Level;

import controller.googleapiSceneController;
import googleAPI.TranslateAPI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.TranslationResponse;

/**
 * The Main class for the Dictionary application. Extends Application class
 * provided by JavaFX.
 */
public class Main extends Application {
	// Constant for currency
	public static final String CURRENCY = "$";

	/**
	 * The main entry point for the application.
	 *
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Override the start method to initialize and display the primary stage.
	 *
	 * @param primaryStage The primary stage for the application
	 * @throws Exception If an error occurs during startup
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Load the FXML file
		Parent root = FXMLLoader.load(getClass().getResource("/view/homeScene.fxml"));

		// Set up the scene and show the stage
		primaryStage.setTitle("Dictionary app");
		Scene scene = new Scene(root);

		// Set the minimum width and height for the window
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(400);

		// Set the maximum width and height for the window
		primaryStage.setMaxWidth(1200);
		primaryStage.setMaxHeight(800);
		
		// Set the scene for the primary stage
		primaryStage.setScene(scene);
		primaryStage.show();
		 TranslateAPI api = new TranslateAPI();
	     TranslationResponse translationResponse = api.translate("Hello");
	        

	}
}
