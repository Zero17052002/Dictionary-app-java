package controller;

import okhttp3.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import googleAPI.TranslateAPI; // Import for TranslateAPI
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import models.TranslationResponse; // Import for TranslationResponse

// Controller class for the Google API scene
public class googleapiSceneController implements Initializable {

    // FXML fields
    @FXML private TextField input_EN; // TextField for input English text
    @FXML private TextField input_VN; // TextField for displaying translated Vietnamese text

    
    // Initialize method to be called when the FXML file is loaded
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize TranslateAPI instance
    	 TranslateAPI api = new TranslateAPI();
    }

    // Button action method for translating text
    @FXML
    void btnTranslate(ActionEvent event) {
    	String text = input_EN.getText();
 
    }
}
