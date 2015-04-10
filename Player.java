/**
 * Player class for the trivia game.
 * 
 * @author Luke Gerhart
 * Date: 4/2/15
 *
 */
import java.util.Comparator;

public class Player {

	private int points;
	private int pin;
	private String name;
	private int tieValue;
	
	/**
	 * Constructor.
	 */
	public Player() {
		pin = 0;
		points = 0;
		name = "";
		tieValue=0;
	}
	
	/**
	 * Get how many points the user has.
	 * @return points
	 */
	public int getPoints() {
		return points;
	}
	
	/**
	 * Add one point to the player.
	 */
	public void addPoint() {
		points++;
	}
	
	/**
	 * Get the player's pin.
	 * @return pin
	 */
	public int getPin() {
		return pin;
	}
	
	/**
	 * Checks if the user entered the right pin.
	 * @param pin
	 * @return true if its the right pin. False otherwise.
	 */
	public boolean checkPin(int pin) {
		return (this.pin == pin);
	}
	
	/**
	 * Set the user's pin
	 * @param pin
	 */
	public void setPin(int pin) {
		this.pin = pin;
	}
	
	/**
	 * Set method for tieValue
	 * Used in tie break situations 
	 * to determine who is closer to the correct answer
	 * @param val (user's answer)
	 */ 
	public void setTieValue(int val){
		tieValue=Math.abs(val);
	}
	
	/**
	 * Get method for tie value
	 * @returns tieValue
	 */
	public int getTieValue(){
		return tieValue;
	}
	
	/**
	 * Set the player's name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get method for the player's name
	 * @return name
	 */
	public String getName() {
		return name;
	}	
	
	/**
	 * Comparator for Player class to compare players by their tieValue values.
	 */
	public static Comparator<Player> PlayerTieComparator = new Comparator<Player>() {
		public int compare(Player one, Player two) {
			return one.getTieValue()-two.getTieValue();
		}
	};
	
	/**
	 * Comparator for Player class to compare players by their points value.
	 */
	public static Comparator<Player> PlayerPointComparator = new Comparator<Player>() {
		public int compare(Player one, Player two) {
			return one.getPoints()-two.getPoints();
		}
	};
}
