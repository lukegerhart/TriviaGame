/**
 * Player class for the trivia game.
 * 
 * @author Luke Gerhart
 * Date: 4/2/15
 *
 */
public class Player {

	private int points;
	private int pin;
	private String name;
	
	/**
	 * Constructor.
	 * @param password Player's pin
	 */
	public Player(int password) {
		points = 0;
		pin = password;
	}
	
	public Player() {
		
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
	
	
	public void setName(String name) {
		this.name = name;
	}
}
