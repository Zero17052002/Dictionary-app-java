package Interface_management;

public interface IdictionaryManagement {
	public void addWords();

	public void editWords();

	public void deleteWords(String wordId);

	public void updateWords(String wordId, String newVietnameseWord, String newEnglishMeaning);

}
