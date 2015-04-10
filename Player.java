/**
 * Player class for the trivia game.
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
	 */
	public Player() {
		pin = 0;
		points = 0;
		name = "";
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
	 * Set the player's pin
	 * @param pin
	 */
	public void setPin(int pin) {
		this.pin = pin;
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
}
