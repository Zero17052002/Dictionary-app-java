package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.Database_management; // Import database management class
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import models.dictionaryManagement; // Import dictionary management class
import text_to_speech.text_to_speech; // Import text-to-speech class

/**
 * Controller class for the dashboard scene.
 */
public class dashboardSceneController implements Initializable {

    // FXML fields
    @FXML
    private ImageView Export;
    @FXML
    private Button id_Search;
    @FXML
    private TextField id_textField;
    @FXML
    private TableView<dictionaryManagement> idtableview;
    @FXML
    private TableColumn<dictionaryManagement, String> tableColumn_englishword;
    @FXML
    private TableColumn<dictionaryManagement, String> tableColumn_id;
    @FXML
    private TableColumn<dictionaryManagement, String> table_columnvn;

    // Instance variables
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    dictionaryManagement eng_VNModel = null;

    /**
     * Export data to a text file upon clicking the Export button.
     * @param event The mouse event triggering the action
     */
    @FXML
    void Export(MouseEvent event) {
        // Create a file chooser dialog for saving data to a text file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Data to Text File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(idtableview.getScene().getWindow());
        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                // Write data to the selected text file
                ObservableList<dictionaryManagement> dataList = idtableview.getItems();
                for (dictionaryManagement item : dataList) {
                    writer.println(
                            item.getWord_id() + "\t" + item.getEnglish_word() + "\t" + item.getVietnamese_mearning());
                }
                // Display success message upon successful data export
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export Successful");
                alert.setHeaderText(null);
                alert.setContentText("Data has been exported to text file successfully!");
                alert.showAndWait();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // Handle file not found error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Export Error");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while exporting data to text file!");
                alert.showAndWait();
            }
        }
    }

    /**
     * Search for data based on the input search text.
     * @param event The action event triggering the search
     */
    @FXML
    void btnSearch(ActionEvent event) {
        // Get search text from the text field
        String searchText = id_textField.getText().trim();
        if (!isValidInput(searchText)) {
            // Display error message if the input is invalid
            System.out.println("Invalid input. Please enter alphabetic or numeric characters only.");
            return;
        }

        if (!searchText.isEmpty()) {
            if (searchText.matches("\\d+")) {
                searchById(searchText);
            } else {
                searchByEnglishWord(searchText);
            }
        }
    }

    /**
     * Perform text-to-speech upon clicking the loudspeaker icon.
     * @param event The mouse event triggering the action
     */
    @FXML
    void Clickloudspeaker(MouseEvent event) {
        String Textts = id_textField.getText().trim();
        text_to_speech text_to_speech = new text_to_speech();
        text_to_speech.TextToSpeech(Textts);
    }

    /**
     * Check if the input contains only alphabetic or numeric characters.
     * @param input The input string to be checked
     * @return True if the input is valid, otherwise false
     */
    private boolean isValidInput(String input) {
        return input.matches("[a-zA-Z0-9]+");
    }

    /**
     * Initialize the controller and set up the table columns.
     * @param arg0 The URL
     * @param arg1 The resource bundle
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Initialize table columns and load data
        tableColumn_id.setCellValueFactory(new PropertyValueFactory<>("word_id"));
        tableColumn_englishword.setCellValueFactory(new PropertyValueFactory<>("english_word"));
        table_columnvn.setCellValueFactory(new PropertyValueFactory<>("vietnamese_mearning"));
        loadData();
    }

    /**
     * Load data into the table view from the database.
     */
    private void loadData() {
        connection = Database_management.connectDb();
        if (connection != null) {
            try {
                String query = "SELECT * FROM dictionary_english_vietnamese";
                preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                idtableview.getItems().clear();
                while (resultSet.next()) {
                    String id = resultSet.getString("word_id");
                    String englishWord = resultSet.getString("english_word");
                    String vietnameseMeaning = resultSet.getString("vietnamese_meaning");

                    dictionaryManagement engVNModel = new dictionaryManagement(id, englishWord,
                            vietnameseMeaning);
                    idtableview.getItems().add(engVNModel);
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

    /**
     * Search for data by ID in the database.
     * @param id The ID to search for
     */
    private void searchById(String id) {
        connection = Database_management.connectDb();
        if (connection != null) {
            try {
                String query = "SELECT * FROM dictionary_english_vietnamese WHERE word_id=?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                idtableview.getItems().clear();
                while (resultSet.next()) {
                    String wordId = resultSet.getString("word_id");
                    String englishWord = resultSet.getString("english_word");
                    String vietnameseMeaning = resultSet.getString("vietnamese_meaning");

                    dictionaryManagement engVNModel = new dictionaryManagement(wordId,
                            englishWord, vietnameseMeaning);
                    idtableview.getItems().add(engVNModel);
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

    /**
     * Search for data by English word in the database.
     * @param englishWord The English word to search for
     */
    private void searchByEnglishWord(String englishWord) {
        connection = Database_management.connectDb();
        if (connection != null) {
            try {
                String query = "SELECT * FROM dictionary_english_vietnamese WHERE english_word LIKE ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "%" + englishWord + "%");
                ResultSet resultSet = preparedStatement.executeQuery();
                idtableview.getItems().clear();
                while (resultSet.next()) {
                    String wordId = resultSet.getString("word_id");
                    String engWord = resultSet.getString("english_word");
                    String vietnameseMeaning = resultSet.getString("vietnamese_meaning");

                    dictionaryManagement engVNModel = new dictionaryManagement(wordId, engWord,
                            vietnameseMeaning);
                    idtableview.getItems().add(engVNModel);
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
}
