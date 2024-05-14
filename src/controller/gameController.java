package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import databaseDAO.Database_management;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import models.gameManagement; // Import for game model

// Controller class for the game view
public class gameController implements Initializable {

	// FXML fields
	@FXML
	private Text Question;
	@FXML
	private Button answer1;
	@FXML
	private Button answer2;
	@FXML
	private Button answer3;
	@FXML
	private Button answer4;

	// Instance variables
	private Connection connection;
	private gameManagement gameModel;
	private int currentQuestionID = 1; 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Connect to the database
		connection = Database_management.connectDb();

		// Load question from database
		loadQuestionFromDatabase();

	}

	 // Method to load question data from the database
	private void loadQuestionFromDatabase() {
		String query = "SELECT * FROM VocabularyGame WHERE ID = ?";
		try {
			// Prepare SQL statement
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, currentQuestionID);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				// Retrieve question data from result set
				int id = resultSet.getInt("ID");
				String question = resultSet.getString("Question");
				String optionA = resultSet.getString("OptionA");
				String optionB = resultSet.getString("OptionB");
				String optionC = resultSet.getString("OptionC");
				String optionD = resultSet.getString("OptionD");
				char correctOption = resultSet.getString("CorrectOption").charAt(0);

				// Create GameModel object from retrieved data
				gameModel = new gameManagement(id, question, optionA, optionB, optionC, optionD, correctOption);

				// Display question and answer options on the interface
				Question.setText(gameModel.getQuestion());
				answer1.setText(gameModel.getOptionA());
				answer2.setText(gameModel.getOptionB());
				answer3.setText(gameModel.getOptionC());
				answer4.setText(gameModel.getOptionD());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Button action method for answer option 1
	@FXML
	void btnanswer1(ActionEvent event) {
		checkAnswer('A');
	}

	// Button action method for answer option 2
	@FXML
	void btnanswer2(ActionEvent event) {
		checkAnswer('B');
	}

	// Button action method for answer option 3
	@FXML
	void btnanswer3(ActionEvent event) {
		checkAnswer('C');
	}

	// Button action method for answer option 4
	@FXML
	void btnanswer4(ActionEvent event) {
		checkAnswer('D');
	}

	// Method to check the selected answer
	protected void checkAnswer(char selectedOption) {
		// Compare selected option with correct option
		if (selectedOption == gameModel.getCorrectOption()) {
			// Show success alert for correct answer
			showAlert("Correct!", "Congratulations! You chose the correct answer.");

			// Play applause sound
			// playApplauseSound();
			
			// Move to next question
			currentQuestionID++;
			loadQuestionFromDatabase(); // Load next question
		} else {
			// Show failure alert for incorrect answer
			showAlert("Incorrect!", "Sorry, the correct answer is " + gameModel.getCorrectOption());
		}
	}

	// Method to display an alert dialog
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
