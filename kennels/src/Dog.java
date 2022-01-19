import java.io.PrintWriter;
import java.util.Scanner;

/**
 * To support an individual dog
 *
 * @author Jay Staddon
 * @version 24th March 2021
 */
public class Dog extends Animal{

	private boolean likesBones;

	/**
	 * No argument constructor
	 */
	public Dog(){}

	/**
	 * Constructor for the cat
	 *
	 * @param name The dogs name
	 * @param type The animal type
	 * @param food The kind of food it eats
	 * @param mealsPerDay Number of feeds per day
	 * @param likesBones If it likes bones
	 */
	public Dog(String name, String type, String food,
			   int mealsPerDay, Boolean likesBones){
		super(name, food, mealsPerDay);
		this.likesBones = likesBones;
		super.setType("Dog");
	}

	/**
	 * Find out if it likes bones
	 * @return A boolean on it's preference
	 */
	public boolean getLikesBones() {
		return likesBones;
	}

	/**
	 * Set if it likes bones
	 * @param likesBones A boolean which represents if it likes bones
	 */
	public void setLikesBones(boolean likesBones) {
		this.likesBones = likesBones;
	}

	/**
	 * Used to set values when making a dog
	 */
	public void readKeyboard(){
		super.readKeyboard();
		Scanner in = new Scanner(System.in);
		String likeBones;
		System.out.println("Do they likes bones (Y or N)");
		likeBones = in.nextLine().toUpperCase();
		if (likeBones.equals("Y")) {
			likesBones = true;
		}
		else if (likeBones.equals("N")){
			likesBones = false;
		}
		super.setType("Dog");
	}

	/**
	 * Saves the dog to a file
	 * @param pw Writes the data to the file
	 */
	public void save(PrintWriter pw) {
		pw.println("Dog");
		super.save(pw);
		pw.println(likesBones);
	}

	/**
	 * Loads the dog from a file
	 * @param scan Takes in a boolean
	 */
	public void load(Scanner scan) {
		super.load(scan);
		likesBones = scan.nextBoolean();
	}

	/**
	 * Prints the dog
	 * @return A toString of the animals info
	 */
	@Override
	public String toString() {
		return super.toString() + "likes Bones: " + likesBones + '\n';
	}
}
