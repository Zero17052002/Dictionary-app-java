package models;

public class dictionaryManagement {
	private String word_id;
	private String english_word;
	private String vietnamese_mearning;

	public dictionaryManagement(String word_id, String english_word, String vietnamese_mearning) {
		super();
		this.word_id = word_id;
		this.english_word = english_word;
		this.vietnamese_mearning = vietnamese_mearning;
	}

	public String getWord_id() {
		return word_id;
	}

	public void setWord_id(String word_id) {
		this.word_id = word_id;
	}

	public String getEnglish_word() {
		return english_word;
	}

	public void setEnglish_word(String english_word) {
		this.english_word = english_word;
	}

	public String getVietnamese_mearning() {
		return vietnamese_mearning;
	}

	public void setVietnamese_mearning(String vietnamese_mearning) {
		this.vietnamese_mearning = vietnamese_mearning;
	}

}
