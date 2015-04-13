/**
 * Trivia class that runs the trivia game.
 * There must be at least two players, and each player has a personal PIN 
 * that they enter at the beginning at the round.
 * The game consists of 6 randomly chosen questions. Each question has 4 possible answers, labeled A, B, C, D.
 * The player chooses their answer in the drop down menu.
 * If two or more players have the same number of points at the end of the 6th question, the game enters tie-break mode
 * tie break questions are all free response questions, and all answers are positive integers.
 * At the end of each tie-break round, the player with the answer closest to the actual answer
 * (regardless of if they are lower or higher than the right answer) is declared the winner
 * if two or more players have the same difference between their answers and the right answer, a new 
 * tie-break question is given until a winner can be declared.
 * @author Matthew Walheim
 * @author Luke Gerhart
 * Date: 4/13/15
 */

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class Trivia {

	public static void main(String[] args) {
		
		ArrayList<Question> questions = new ArrayList<Question>();
		ArrayList<TieBreak> tieBreaks= new ArrayList<TieBreak>();
		ArrayList<Player> players = new ArrayList<Player>();
		ArrayList<Player> winners= new ArrayList<Player>();
		ArrayList<Player> tieWinners= new ArrayList<Player>();
		
		
		File questionFile = new File("Questions.dat");
		Scanner questionScanner = null;
		File tieFile= new File("TieBreak.dat");
		Scanner tieScanner= null;
		String input = "";
		int numPlayers = 0;
		int lineCount=0;
		int tieLineCount=0;
		int pin=-1;
		//Opening files needed for the program.
		try{
			questionScanner = new Scanner(questionFile);
		} catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		try{
			tieScanner= new Scanner(tieFile);
		} catch(FileNotFoundException fnf){
			JOptionPane.showMessageDialog(null, "File not found!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		while(questionScanner.hasNext()) {
			questions.add(new Question(questionScanner.nextLine()));
			lineCount++;
		}
		while(tieScanner.hasNext()){
			tieBreaks.add(new TieBreak(tieScanner.nextLine()));
			tieLineCount++;
		}
		input = JOptionPane.showInputDialog("How many players will there be?");
		boolean notInteger=true;
		//Validate number of players.
		while (numPlayers < 2 && notInteger) {
			try {
				numPlayers = Integer.parseInt(input);
				notInteger=false;
				if (numPlayers < 2) {
					Object[] options = { "OK", "CANCEL" };
					JOptionPane.showOptionDialog(null, "There must be at least two players.", "Warning", 
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					        null, options, options[0]);
					input = JOptionPane.showInputDialog("How many players will there be?");
					notInteger=true;
				}

			} catch(NumberFormatException e) {
				notInteger=true;
				input = JOptionPane.showInputDialog("Error: invalid input!\nHow many players will there be?: ");
			}
		}
		//Adding players to players ArrayList
		for(int i=0; i<numPlayers; i++){
			players.add(new Player());
			players.get(i).setName(JOptionPane.showInputDialog(null, "Enter Player " + (i+1) + " Name.", "Player Name", JOptionPane.QUESTION_MESSAGE));
			pin= validatePin(JOptionPane.showInputDialog(null, "Enter " + players.get(i).getName() + "'s Pin.", "PIN", JOptionPane.QUESTION_MESSAGE));
			players.get(i).setPin(pin);
		}
		
		int randomNum=-1;
		String pinStr="";
		String message = "";
		Object answerChoice = null;
		Object[] answers = new Object[4];
		String ansChoice = "";
		for(int counter=0; counter < 6; counter++){
			randomNum=random(questions.size());
			for(int i=0; i<numPlayers; i++){
				pinStr = JOptionPane.showInputDialog(null, players.get(i).getName()+"'s Turn. \nEnter Pin: ", "PIN", JOptionPane.QUESTION_MESSAGE);
				while(validatePin(pinStr)!= players.get(i).getPin()){
					pinStr = JOptionPane.showInputDialog(null, "Error! \nPlease enter correct pin.", "Warning", JOptionPane.WARNING_MESSAGE);
				}				
				for (int k = 0; k < 4; k++) {
					answers[k] = questions.get(randomNum).getAnswers()[k];
				}
				//Ask question and record answer.
				answerChoice = JOptionPane.showInputDialog(null, questions.get(randomNum).getQuestion(), "Question " + (counter + 1), 
						JOptionPane.QUESTION_MESSAGE, null, answers, answers[0]);
				//answerChoice is an Object object, so it needs to be cast to a String
				ansChoice = ((String) answerChoice);
				ansChoice = String.valueOf(ansChoice.charAt(0));
				if(ansChoice.equalsIgnoreCase(questions.get(randomNum).getRightAnswer())){
					players.get(i).addPoint();
				}
				else{
					//Do nothing
				}
				
			}
			//Remove used question
			questions.remove(randomNum);
			for(int i=0; i<numPlayers; i++){
				message = message.concat(players.get(i).getName()+"'s Points: "+players.get(i).getPoints() + "\n");
			}
			JOptionPane.showMessageDialog(null, message, "Points", JOptionPane.INFORMATION_MESSAGE);
			message = "";
		}
		
		//Determine who tied after the 6th question.
		winners.add(players.get(0));
		for(int i=1; i<players.size(); i++){
			if(players.get(i).getPoints()>winners.get(0).getPoints()){
				//If the next player in the ArrayList has a higher point value than the others,
				//The ArrayList is cleared and the player is added.
				winners.clear();
				winners.add(players.get(i));
			}
			else if(players.get(i).getPoints()==winners.get(0).getPoints()){
				winners.add(players.get(i));
			}
		}
		if(winners.size()>1){
			boolean flag= true;
			int tieRandom=0;
			int tieAnswer;
			while(flag){
				tieRandom= random(tieLineCount);
				for(int i=0; i<winners.size();i++){
					pinStr = JOptionPane.showInputDialog(null, winners.get(i).getName()+"'s Turn. \nEnter Pin: ", "PIN", JOptionPane.QUESTION_MESSAGE);
					while(validatePin(pinStr)!= winners.get(i).getPin()){
						pinStr = JOptionPane.showInputDialog(null, "Error! \nPlease enter correct pin.", "Warning", JOptionPane.WARNING_MESSAGE);
					}
					//Display tie-break question and record answer.
					tieAnswer = validateInt(JOptionPane.showInputDialog(null, tieBreaks.get(tieRandom).getQuestion(), "Tie Break Question", JOptionPane.QUESTION_MESSAGE));
					winners.get(i).setTieValue(tieBreaks.get(tieRandom).getAnswers()-tieAnswer);
				}
					
				//Determine if there are ties during the 
				tieWinners.add(winners.get(0));
				for(int i=1; i<winners.size(); i++){
					if(winners.get(i).getTieValue()<tieWinners.get(0).getTieValue()){
						tieWinners.clear();
						tieWinners.add(winners.get(i));
					}
					else if(winners.get(i).getTieValue()==tieWinners.get(0).getTieValue()){
						tieWinners.add(winners.get(i));
					}	
				}
				//If there is a tie, the tie-break round starts again.
				if(tieWinners.size()>1){
					winners.clear();
					for(int i=0; i<tieWinners.size(); i++){
						winners.add(tieWinners.get(i));
						flag= true;
					}
					tieWinners.clear();
				} 
				else {
					flag= false;
					//Sort the winner of the tie-break by lowest tieValue
					Collections.sort(tieWinners,Player.PlayerTieComparator);
					JOptionPane.showMessageDialog(null, "The winner is "+ tieWinners.get(0).getName(), "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		else {
			//Sort the player's points in increasing order. Winner is the last player in the ArrayList.
			Collections.sort(players,Player.PlayerPointComparator);
			JOptionPane.showMessageDialog(null, "The winner is "+ players.get(players.size()-1).getName(), "Congratualations!", JOptionPane.INFORMATION_MESSAGE);
		}
	}	
	
	/**
	 * Method used to generate random number
	 * @param num max value of the random number 
	 * @return randomNumber
	 */
	public static int random(int num){
	
		Random generator= new Random ();
		int randomNumber;
		randomNumber= generator.nextInt(num);
		return randomNumber;
	}	
	
	/**
	 * Method used to validate user's pin
	 * @param str String pin
	 * @return int pin
	 */
	public static int validatePin (String str){
		int number=-1;
		boolean isItInt=false;
		boolean fourDigit=false;
		while (isItInt==false&& fourDigit==false)
		{
			try
			{	
				number= Integer.parseInt(str);
				isItInt=true;
				if(str.length()==4 && number>=0)
				{
					fourDigit=true;
				}
				else
				{
					isItInt=false;
					str = JOptionPane.showInputDialog(null, "Pin must be a positive four(4) digit number. \nPlease enter a new pin.", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
			catch(NumberFormatException n)
			{
				isItInt=false;
				str = JOptionPane.showInputDialog(null, "Error.\nEnter a number.", "Warning", JOptionPane.WARNING_MESSAGE);
			}
		}
		return number;
	}
	
	/**
	 * Method used to validate user input integers
	 * @param str String of integer that user entered
	 * @return correct integer value 
	 */
	public static int validateInt (String str) {
		int number=-1;
		boolean isItInt=false;
		boolean greaterThan0=false;
		while (isItInt==false&& greaterThan0==false)
		{
			try
			{	
				number= Integer.parseInt(str);
				isItInt=true;
				if(number>=0)
				{
					greaterThan0=true;
				}
				else
				{
					isItInt=false;
					str = JOptionPane.showInputDialog(null, "Number must be 0 or greater.\nPlease enter a new number.", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
			catch(NumberFormatException n)
			{
				isItInt=false;
				str = JOptionPane.showInputDialog(null, "Error.\nEnter a number.", "Warning", JOptionPane.WARNING_MESSAGE);
			}
		}
		return number;
	}
}
