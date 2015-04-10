/**
 * TieBreak class that represents a tie break question.
 * @author Luke Gerhart
 * Date: 4/9/2015
 *
 */
public class TieBreak {

	private String question;
	private int answer;
	
	/**
	* Constructor.
	* @param fromFile Question from the tie break file.
	*/
	public TieBreak(String fromFile) {
		question = parseQuestion(fromFile);
		answer = parseAnswer(fromFile);
	}

	/**
	 * Get method for the question
	 * @return question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Get method for the answer
	 * @return answer
	 */
	public int getAnswers() {
		return answer;
	}
	
	/**
	 * Method that extracts just the question from the tie break file.
	 * @param fromFile
	 * @return just the question without the answer
	 */
	private String parseQuestion(String fromFile) {
		String parsed = "";
		for (int i = 0; i < fromFile.length(); i++) {
			if (fromFile.charAt(i) != '?') {
				parsed = parsed.concat(String.valueOf(fromFile.charAt(i)));
			}
			else
				break;
		}
		return parsed.concat("?");
	}
	
	/**
	 * Method that extracts just the answer from the tie break file.
	 * @param fromFile
	 * @return just the answer without the question
	 */
	private int parseAnswer(String fromFile) {
		String parsed = "";
		int index = 0;
		for (int i = 0; i < fromFile.length(); i++) {
			if (fromFile.charAt(i) == '?') {
				index = i+2;
				break;
			}
		}
		for (int i = index; i < fromFile.length(); i++) {
			parsed = parsed.concat(String.valueOf(fromFile.charAt(i)));
		}
		return Integer.valueOf(parsed);
	}
	
	
}
