package controller;

import javafx.scene.control.Alert;

// Subclass of gameController
public class MyGameController extends gameController {

    // Override the checkAnswer method to add additional functionality
    @Override
	protected void checkAnswer(char selectedOption) {
        // Call the parent class method to check the answer
        super.checkAnswer(selectedOption);

        // Add additional functionality here
        // For example, you can show a custom message or perform some specific actions
        if (selectedOption == 'A') {
            showAlert("My Custom Message", "You selected option A!");
        }
    }

    protected void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
