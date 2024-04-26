package models;

import java.util.List;

public class TranslationResponse {
    private final List<String> translatedTexts;
    private final String detectedSourceLanguage;
    private final boolean isError;
    private final String errorMessage;

    public TranslationResponse(List<String> translatedTexts, String detectedSourceLanguage, boolean isError, String errorMessage) {
        this.translatedTexts = translatedTexts;
        this.detectedSourceLanguage = detectedSourceLanguage;
        this.isError = isError;
        this.errorMessage = errorMessage;
    }

    public List<String> getTranslatedTexts() {
        return translatedTexts;
    }

    public String getDetectedSourceLanguage() {
        return detectedSourceLanguage;
    }

    public boolean isError() {
        return isError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
