import java.io.PrintWriter;
import java.util.Scanner;

/**
 * To support an individual cat
 *
 * @author Jay Staddon
 * @version 24th March 2021
 */
public class Cat extends Animal{

    private Boolean shareRuns;

    /**
     * No argument constructor
     */
    public Cat() {}

    /**
     * Constructor for the cat
     *
     * @param name The cats name
     * @param type The animal type
     * @param food The kind of food it eats
     * @param mealsPerDay Number of feeds per day
     * @param shareRuns If it can share runs
     */
    public Cat(String name, String type, String food,
               int mealsPerDay, Boolean shareRuns){
        super(name, food, mealsPerDay);
        this.shareRuns = shareRuns;
        super.setType("Cat");
    }

    /**
     * Find out if it can share runs
     * @return A boolean on if it can share runs
     */
    public Boolean getShareRuns() {
        return shareRuns;
    }

    /**
     * Set if it can share runs
     * @param shareRuns A boolean which represents if it can share runs
     */
    public void setShareRuns(Boolean shareRuns) {
        this.shareRuns = shareRuns;
    }

    /**
     * Used to set values when making a cat
     */
    public void readKeyboard(){
        super.readKeyboard();
        Scanner in = new Scanner(System.in);
        String sharesRuns;
        System.out.println("Can they share runs (Y or N)");
        sharesRuns = in.nextLine().toUpperCase();
        if (sharesRuns.equals("Y")) {
            shareRuns = true;
        }
        else if (sharesRuns.equals("N")){
            shareRuns = false;
        }
        super.setType("Cat");
    }

    /**
     * Saves the cat to a file
     * @param pw Writes the data to the file
     */
    public void save(PrintWriter pw) {
        pw.println("Cat");
        super.save(pw);
        pw.println(shareRuns);
    }

    /**
     * Loads the cat from a file
     * @param scan Takes in a boolean
     */
    public void load(Scanner scan) {
        super.load(scan);
        shareRuns = scan.nextBoolean();
    }

    /**
     * Prints the cat
     * @return A toString of the animals info
     */
    public String toString() {
        return super.toString() + "Shares runs: " + shareRuns + '\n';
    }

}

