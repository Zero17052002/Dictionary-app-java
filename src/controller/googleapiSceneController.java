package controller;

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
	@FXML
	private TextField input_EN; // TextField for input English text
	@FXML
	private TextField input_VN; // TextField for displaying translated Vietnamese text

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialize TranslateAPI instance here if needed
	}

	// Button action method for translating text
	@FXML
	void btnTranslate(ActionEvent event) {
		String text = input_EN.getText();
		TranslateAPI api = new TranslateAPI();

		// Tạo một Task để thực hiện việc dịch trên một luồng khác
		Task<Void> translateTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				// Gọi phương thức dịch của TranslateAPI
				TranslationResponse response = api.translate(text);

				// Kiểm tra xem có lỗi không và hiển thị kết quả dịch nếu không có lỗi
				if (!response.isError()) {
					String translatedText = response.getTranslatedTexts().get(0);

					// Cập nhật giao diện người dùng trên luồng UI chính
					javafx.application.Platform.runLater(() -> {
					    input_VN.setText(translatedText);
					});
				} else {
					// Xử lý lỗi nếu có
					String errorMessage = response.getErrorMessage();
					System.err.println("Translation Error: " + errorMessage);
				}
				return null;
			}
		};

		// Bắt đầu thực hiện task trên một luồng khác
		new Thread(translateTask).start();
	}

	
}
