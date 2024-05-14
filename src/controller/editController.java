package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Interface_management.IdictionaryManagement;
import databaseDAO.Database_management;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.dictionaryManagement;

// Controller for Edit View
public class editController implements Initializable, IdictionaryManagement {

	// FXML fields
	@FXML
	private Button Id_Edit;
	@FXML
	private TableColumn<dictionaryManagement, String> TB_VN;
	@FXML
	private TableColumn<dictionaryManagement, String> Tb_EN;
	@FXML
	private TableColumn<dictionaryManagement, String> Tb_id;
	@FXML
	private Button add;
	@FXML
	private Button id_Delete;
	@FXML
	private TextField id_inputVN;
	@FXML
	private TableView<dictionaryManagement> id_tableView;
	@FXML
	private Button id_update;
	@FXML
	private TextField input_EN;
	@FXML
	private TextField inputid;
	String query = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	dictionaryManagement eng_VNModeledit = null;

	@FXML
	void btnDelete(ActionEvent event) {
		String wordId = inputid.getText().trim();
		boolean deleted = deleteWords(wordId);
		if (deleted) {
            refreshUI();
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("Word deleted successfully!");
			alert.showAndWait();
		} else {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Failed to delete word!");
			alert.showAndWait();
		}
	}

	// Edit button action
	@FXML
	void btnEdit(ActionEvent event) {
		editWords();
	}

	// Update button action
	@FXML
	void btnUpdate(ActionEvent event) {
		String wordId = inputid.getText().trim();
		String newVietnameseWord = id_inputVN.getText().trim();
		String newEnglishMeaning = input_EN.getText().trim();

		if (wordId.isEmpty() || newVietnameseWord.isEmpty() || newEnglishMeaning.isEmpty()) {
          
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("Please fill in all fields!");
			alert.showAndWait();
			return;
		}

		try {

			Connection conn = Database_management.connectDb();

			String sql = "UPDATE dictionary_english_vietnamese SET english_word = ?, vietnamese_meaning = ? WHERE word_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, newEnglishMeaning);
			statement.setString(2, newVietnameseWord);
			statement.setString(3, wordId);

			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated > 0) {
                refreshUI();
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setHeaderText(null);
				alert.setContentText("Word updated successfully!");
				alert.showAndWait();

				loadDataEdit();
			} else {

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Failed to update word!");
				alert.showAndWait();
			}

			conn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// Add button action
	@FXML
	void btnadd(ActionEvent event) {
		addWords();
	}

	// Method to add new words
	@Override
	public void addWords() {

		String newWordId = inputid.getText().trim();
		String newEnglishWord = input_EN.getText().trim();
		String newVietnameseMeaning = id_inputVN.getText().trim();

		if (newWordId.isEmpty() || newEnglishWord.isEmpty() || newVietnameseMeaning.isEmpty()) {

			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("Please fill in all fields!");
			alert.showAndWait();
			return;
		}

		try {
			refreshUI();

			Connection conn = Database_management.connectDb();

			String checkSql = "SELECT * FROM dictionary_english_vietnamese WHERE word_id = ?";
			PreparedStatement checkStatement = conn.prepareStatement(checkSql);
			checkStatement.setString(1, newWordId);
			ResultSet resultSet = checkStatement.executeQuery();

			if (resultSet.next()) {

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Word ID already exists!");
				alert.showAndWait();
				return;
			}

			String sql = "INSERT INTO dictionary_english_vietnamese (word_id, english_word, vietnamese_meaning) VALUES (?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, newWordId);
			statement.setString(2, newEnglishWord);
			statement.setString(3, newVietnameseMeaning);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setHeaderText(null);
				alert.setContentText("New word added successfully!");
				alert.showAndWait();

				inputid.clear();
				input_EN.clear();
				id_inputVN.clear();

				loadDataEdit();
			} else {

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Failed to add new word!");
				alert.showAndWait();
			}

			// Đóng kết nối
			conn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// Method to edit words
	@Override
	public void editWords() {
		// Method to edit words (to be implemented if needed)
	}

	// Method to update words
	@Override
	public void updateWords(String wordId, String newVietnameseWord, String newEnglishMeaning) {
		// Implementation to update words
	}

	// Method to delete words
	@Override
	public boolean deleteWords(String wordId) {
		try {

			Connection conn = Database_management.connectDb();

			conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0").execute();

			String sql = "DELETE FROM dictionary_english_vietnamese WHERE word_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, wordId);

			int rowsDeleted = statement.executeUpdate();

			conn.prepareStatement("SET FOREIGN_KEY_CHECKS=1").execute();

			if (rowsDeleted > 0) {

				conn.close();
				return true;
			} else {

				conn.close();
				return false;
			}
		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		}
	}

	// Initialize method
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialize method to load data
		Tb_id.setCellValueFactory(new PropertyValueFactory<>("word_id"));
		Tb_EN.setCellValueFactory(new PropertyValueFactory<>("english_word"));
		TB_VN.setCellValueFactory(new PropertyValueFactory<>("vietnamese_mearning"));
		loadDataEdit();
	}

	// Method to load data for editing
	private void loadDataEdit() {
		connection = Database_management.connectDb();
		if (connection != null) {
			try {
				String query = "SELECT * FROM dictionary_english_vietnamese";
				preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();
				id_tableView.getItems().clear();
				while (resultSet.next()) {
					String id = resultSet.getString("word_id");
					String englishWord = resultSet.getString("english_word");
					String vietnameseMeaning = resultSet.getString("vietnamese_meaning");

					dictionaryManagement engVNModel = new dictionaryManagement(id, englishWord, vietnameseMeaning);
					id_tableView.getItems().add(engVNModel);
				}
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Failed to connect to the database.");
		}
	}
	public void refreshUI() {
	   loadDataEdit();
	}

}
