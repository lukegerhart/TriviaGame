import java.io.*;
import java.util.*;

public class Trivia {

	public static void main(String[] args) {
		
		ArrayList<Question> questions = new ArrayList<Question>();
		ArrayList<String> questionStrings = new ArrayList<String>();
		ArrayList<TieBreak> tieBreaks= new ArrayList<TieBreak>();
		ArrayList<Player> players = new ArrayList<Player>();
		ArrayList<Player> winners= new ArrayList<Player>();
		ArrayList<Integer> tieValue= new ArrayList<Integer>();
		
		
		File questionFile = new File("Questions.dat");
		Scanner questionScanner = null;
		File tieFile= new File("TieBreak.dat");
		Scanner tieScanner= null;
		Scanner keyboard = new Scanner(System.in);
		String input = "";
		int numPlayers = 0;
		int lineCount=0;
		int tieLineCount=0;
		int pin=-1;
		try{
			questionScanner = new Scanner(questionFile);
		} catch(FileNotFoundException e) {
			System.out.println("File not found");
		}
		try{
			tieScanner= new Scanner(tieFile);
		} catch(FileNotFoundException fnf){
			System.out.println("File not found");
		}
		while(questionScanner.hasNext()) {
			questions.add(new Question(questionScanner.nextLine()));
			lineCount++;
		}
		while(tieScanner.hasNext()){
			tieBreaks.add(new TieBreak(tieScanner.nextLine()));
			tieLineCount++;
		}
		System.out.print("How many players will there be?: ");
		input = keyboard.nextLine();
		boolean notInteger=true;
		while (numPlayers < 2 && notInteger) {
			try {
				numPlayers = Integer.parseInt(input);
				notInteger=false;
				if (numPlayers < 2) {
					System.out.println("There must be at least two players.");
					System.out.print("How many players will there be?: ");
					input = keyboard.nextLine();
					notInteger=true;
				}

			} catch(NumberFormatException e) {
				notInteger=true;
				System.out.print("Error: invalid input!\nHow many players will there be?: ");
				input = keyboard.nextLine();
			}
		}
		for(int i=0; i<numPlayers; i++){
			players.add(new Player());
			System.out.print("Enter Player Name: ");
			players.get(i).setName(keyboard.nextLine());
			System.out.print("Enter Player Pin: ");
			pin= validatePin(keyboard.nextLine());
			players.get(i).setPin(pin);
			System.out.println("");
			
			
		}
		
		int randomNum=-1;
		boolean underSix=true;
		String pinStr="";
		for(int counter=0; counter<6; counter++){
			randomNum=random(questions.size());
			for(int i=0; i<numPlayers; i++){
				System.out.println("\n"+players.get(i).getName()+"'s Turn\n");
				System.out.print("Enter Pin: ");
				pinStr= keyboard.nextLine();
				while(validatePin(pinStr)!= players.get(i).getPin()){
					System.out.println("\n Error!");
					System.out.print(players.get(i).getName()+", please enter pin: ");
					pinStr= keyboard.nextLine();
				}
				System.out.println(questions.get(randomNum).getQuestion());
				for (int k = 0; k < 4; k++) {
					System.out.println(questions.get(randomNum).getAnswers()[k]);
				}
				System.out.print("Answer: ");
				String answerChoice= keyboard.nextLine();
				if(answerChoice.equalsIgnoreCase(questions.get(randomNum).getRightAnswer())){
					//System.out.println("You are right");
					players.get(i).addPoint();
				}
				else{
					//System.out.println("You are wrong");
				}
				
			}	
			questions.remove(randomNum);
			for(int i=0; i<numPlayers; i++){
				System.out.println(players.get(i).getName()+"'s Points: "+players.get(i).getPoints());
			}
			
		}
		winners.add(players.get(0));
		for(int i=0; i<players.size(); i++){
			if(players.get(i).getPoints()>winners.get(0).getPoints()){
				winners.clear();
				winners.add(players.get(i));
			}
			else if(players.get(i).getPoints()==winners.get(0).getPoints()){
				winners.add(players.get(1));
			}
			else{}
		}
		if(winners.size()>1){
			int tieRandom=-1;
			int tieAnswer;
			tieRandom= random(winners.size());
			for(int i=0; i<winners.size()-1;i++){
				System.out.println("\n"+winners.get(i).getName()+"'s Turn\n");
				System.out.print("Enter Pin: ");
				pinStr= keyboard.nextLine();
				while(validatePin(pinStr)!= winners.get(i).getPin()){
					System.out.println("\n Error!");
					System.out.print(winners.get(i).getName()+", please enter pin: ");
					pinStr= keyboard.nextLine();
				}
			System.out.println(tieBreaks.get(tieRandom).getQuestion());
			System.out.print("Answer: ");
			tieAnswer= validateInt(keyboard.nextLine());
			tieValue.add(tieBreaks.get(tieRandom).getAnswers()-tieAnswer);
			}	
		}
		else{
			System.out.println("The winner is "+ winners.get(0).getName());
		}
	}
	
	public static int random(int num){
	
		Random generator= new Random ();
		int randomNumber;
		randomNumber= generator.nextInt(num);
		return randomNumber;
	}	
	
	public static int validatePin (String str)
	{
		Scanner keyboard= new Scanner(System.in);
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
					System.out.print("Pin must be a positive four(4) digit number \n Please enter a new pin:");
					str= keyboard.nextLine();
				}
			}
			catch(NumberFormatException n)
			{
				isItInt=false;
				System.out.print("Error: Enter a number:");
				str= keyboard.nextLine();		
			}
		}
		return number;
	}
	public static int validateInt (String str)
	{
		Scanner keyboard= new Scanner(System.in);
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
					System.out.print("Number must be 0 or greater\n Please enter a new number:");
					str= keyboard.nextLine();
				}
			}
			catch(NumberFormatException n)
			{
				isItInt=false;
				System.out.print("Error: Enter a number:");
				str= keyboard.nextLine();		
			}
		}
		return number;
	}
}
