package googleAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.TranslationResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Utility class for interacting with the Microsoft Translator Text API for translation.
 */
public class TranslateAPI {

    private static final String API_KEY = "8b71dd7be9f24cf4aefcf5c1b20ee2cc";
    private static final String API_LOCATION = "westus";
    private static final OkHttpClient CLIENT = new OkHttpClient();
    @SuppressWarnings("unused")
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = Logger.getLogger(TranslateAPI.class.getName());
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");

    /**
     * Translates the provided text from English to Vietnamese.
     *
     * @param text The text to translate.
     * @return A TranslationResponse object containing the translated text and other details.
     */
    public TranslationResponse translate(String text) {
        
        @SuppressWarnings("deprecation")
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, "[{\"Text\": \"" + text + "\"}]");
        Request request = new Request.Builder()
                .url("https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&from=en&to=vi")
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", API_KEY)
                .addHeader("Ocp-Apim-Subscription-Region", API_LOCATION)
                .addHeader("Content-type", "application/json")
                .build();
        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code());
            }
            String jsonResponse = response.body().string();
            LOGGER.log(Level.INFO, "Response from API: {0}", jsonResponse);
            return parseTranslationResponse(jsonResponse);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while translating text", e);
            return new TranslationResponse(new ArrayList<>(), null, true, e.getMessage());
        }
    }

    public TranslateAPI() {
		super();
	}

	/**
     * Parses the JSON response from the translation API into a TranslationResponse object.
     *
     * @param jsonText The JSON response text.
     * @return A TranslationResponse object containing the parsed data.
     */
    private TranslationResponse parseTranslationResponse(String jsonText) {
        JsonObject jsonObject = JsonParser.parseString(jsonText).getAsJsonArray().get(0).getAsJsonObject();
        JsonArray translations = jsonObject.getAsJsonArray("translations");
        List<String> translatedTexts = new ArrayList<>();
        for (JsonElement element : translations) {
            String translatedText = element.getAsJsonObject().get("text").getAsString();
            translatedTexts.add(translatedText);
        }
        String detectedSourceLanguage = jsonObject.has("detectedLanguage") ? jsonObject.get("detectedLanguage").getAsString() : null;
        boolean isError = jsonObject.has("error") && jsonObject.get("error").getAsJsonObject().has("code");
        String errorMessage = isError ? jsonObject.get("error").getAsJsonObject().get("message").getAsString() : null;
        return new TranslationResponse(translatedTexts, detectedSourceLanguage, isError, errorMessage);
    }

    /**
     * Main method for testing translation functionality.
     *
     * @param args Command-line arguments (not used).
     * @throws IOException If an error occurs during translation.
     */
   
}
