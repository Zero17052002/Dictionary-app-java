package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent; // Correct import
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

// Controller class for the home scene
public class homeSceneController implements Initializable {

    // FXML fields
    @FXML private Button Dashboard; // Button for Dashboard
    @FXML private AnchorPane DboardAnchorPane; // AnchorPane for Dashboard content
    @FXML private Button Edit; // Button for Edit
    @FXML private Button Game; // Button for Game
    @FXML private Button GoogleApi; // Button for Google API
    @FXML private BorderPane idborderPain; // BorderPane for main content

    // Button action method for navigating to the Dashboard scene
    @FXML
    void btnDashboard(ActionEvent event) throws IOException {
        // Load the Dashboard scene
        AnchorPane view = FXMLLoader.load(getClass().getResource("/view/dashboardScene.fxml"));
        idborderPain.setTop(view);
    }

    // Button action method for navigating to the Edit scene
    @FXML
    void btnEdit(ActionEvent event) throws IOException {
        // Load the Edit scene
        AnchorPane viewEdit = FXMLLoader.load(getClass().getResource("/view/editScene.fxml"));
        idborderPain.setTop(viewEdit);
    }

    // Button action method for navigating to the Game scene
    @FXML
    void btnGame(ActionEvent event) throws IOException {
        // Load the Game scene
        AnchorPane viewGame = FXMLLoader.load(getClass().getResource("/view/gameScene.fxml"));
        idborderPain.setTop(viewGame);
    }

    // Button action method for navigating to the Google API scene
    @FXML
    void btnGoogleApi(ActionEvent event) throws IOException {
        // Load the Google API scene
        AnchorPane viewGoogle = FXMLLoader.load(getClass().getResource("/view/googleapiScene.fxml"));
        idborderPain.setTop(viewGoogle);
    }

    // Initialization method to be called when the FXML file is loaded
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Initialization code
    }
}
