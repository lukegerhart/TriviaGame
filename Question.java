/**
 * Question class that represents a trivia question
 * @author Luke Gerhart
 * Date: 4/2/15
 */
public class Question {

	private String rightAnswer;
	private String[] answers;
	private String question;
	
	/**
	 * Initializer Constructor
	 * @param fromFile
	 */
	public Question(String fromFile) {
		rightAnswer = parseRightAnswer(fromFile);
		answers = parseAnswers(fromFile);
		question = parseQuestion(fromFile);
	}
	
	/**
	 * Extracts just the question from the string stored in the file.
	 * @param questionString
	 * @return The question
	 */
	private String parseQuestion(String questionString) {
		String temp = "";
		for (int i = 0; i < questionString.length(); i++) {
			if (questionString.charAt(i) != '?') {
				temp = temp.concat(String.valueOf(questionString.charAt(i)));
			}
			else {
				temp = temp.concat("?");
				break;
			}
		}
		return temp;
	}
	
	/**
	 * Extracts the answers from string in the questions file.
	 * @param questionString
	 * @return String[] answers
	 */
	private String[] parseAnswers(String questionString) {
		String[] answers = new String[4];
		int startIndex = 0;
		int endIndex = questionString.length()-2;
		String trimmed = "";
		for (int i = 0; i < questionString.length(); i++) {
			if (questionString.charAt(i) == '?') {
				startIndex = i + 2;
				break;
			}
		}
		trimmed = questionString.substring(startIndex, endIndex);
		int[] startIndices = new int[4];
		int counter = 0;
		for (int i = 0; i < trimmed.length(); i++) {
			if (trimmed.charAt(i) == '.') {
				startIndices[counter] = i-1;
				counter++;
			}
		}
		int[] endIndices = new int[4];
		for (int i = 0; i < 3; i++) {
			endIndices[i] = startIndices[i+1] - 1;
		}
		endIndices[3] = trimmed.length();
		for (int i = 0; i < 4; i ++) {
			answers[i] = trimmed.substring(startIndices[i], endIndices[i]);
		}
		return answers;
	}
	
	/**
	 * Extracts the letter of the correct answer from the string stored in the file.<br>
	 * In other words, it returns the last char of questionString.
	 * @param questionString
	 * @return letter of right answer
	 */
	private String parseRightAnswer(String questionString) {
		return String.valueOf(questionString.charAt(questionString.length()-1));
	}
	
	/**
	 * Returns the question.
	 * @return question
	 */
	public String getQuestion() {
		return question;
	}
	
	/**
	 * Returns the letter of the right answer.
	 * @return rightAnswer
	 */
	public String getRightAnswer() {
		return rightAnswer;
	}

	/**
	 * Get method for String[] answers
	 * @return String[] answers
	 */
	public String[] getAnswers() {
		return answers;
	}
}
