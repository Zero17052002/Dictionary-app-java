package Interface_management;

public interface IdictionaryManagement {
	public void addWords();

	public void editWords();

	public boolean deleteWords(String wordId);

	public void updateWords(String wordId, String newVietnameseWord, String newEnglishMeaning);

}
