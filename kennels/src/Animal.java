import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * To make the basics of an animal
 * Implements Comparable <Animal> so it can compare different animals to each other
 *
 * @author Jay Staddon
 * @version 24th March 2021
 */
public class Animal implements Comparable <Animal> {

    private ArrayList<Owner> originalOwners;
    private String type;
    private String name;
    private String favFood;
    private int foodPerDay;

    /**
     * No argument constructor
     */
    public Animal(){
        this("unknown", "unknown", 0);
    }

    /**
     * Constructor for the animal
     *
     * @param name The animal's name
     * @param food The kind of food it eats
     * @param mealsPerDay Number of feeds per day
     */
    public Animal(String name, String food, int mealsPerDay) {
        this.name = name;
        originalOwners = new ArrayList<Owner>();
        this.favFood = food;
        this.foodPerDay = mealsPerDay;
    }

    /**
     * Has user enter values for new animal
     *
     * @throws InputMismatchException If value isn't the correct type
     */
    public void readKeyboard() throws InputMismatchException {
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\r?\n|\r");
        System.out.println("enter:");
        System.out.println("Name of animal");
        name = scan.nextLine();
        System.out.println("What is his/her favourite food?");
        favFood = scan.nextLine();
        try {
            System.out.println("How many times is he/she fed a day? (as a number)");
            foodPerDay = scan.nextInt();
        } catch (InputMismatchException e){
            throw new InputMismatchException("value must contain only number");
        }
    }

    /**
     * Obtain the name of the animal
     * @return The name of the animal
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the animal
     * @param newName The name of the animal
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Returns a copy of the original owners
     * @return A copy of the original owners as an array
     */
    public Owner[] getOriginalOwners(){
        Owner[] result = new Owner[originalOwners.size()];
        result = originalOwners.toArray(result);
        return result;
    }

    /**
     * Sets the type of animal
     * @param type The type of the animal
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtains the type of animal
     * @return The type of animal
     */
    public String getType() {
        return type;
    }

    /**
     * To add an original owner
     * @param o An original owner
     */
    public void addOriginalOwner(Owner o){
        originalOwners.add(o);
    }

    /**
     * How many times a day to feed the animal
     * @param feeds The number of feeds per day
     */
    public void setFeedsPerDay(int feeds){
        foodPerDay = feeds;
    }

    /**
     * The number of feeds per day the animal is fed
     * @return The number of feeds per day
     */
    public int getFeedsPerDay(){
        return foodPerDay;
    }

    /**
     * What's their favourite food?
     * @param food The food they like
     */
    public void setFavouriteFood(String food){
        favFood = food;
    }

    /**
     * The food the animal likes to eat
     * @return The food
     */
    public String getFavouriteFood(){
        return favFood;
    }

    /**
     * Reads in information about the animal from the file
     * @param infile Scans the file for each element
     */
    public void load(Scanner infile){
        name = infile.next();
        int numOwners = infile.nextInt();
        originalOwners = new ArrayList<>();
        for (int oCount = 0; oCount < numOwners; oCount++) {
            String name = infile.next();
            String phone = infile.next();
            Owner owner = new Owner(name, phone);
            originalOwners.add(owner);
        }
        foodPerDay = infile.nextInt();
        favFood = infile.next();
    }

    /**
     * Saves the animals to the file
     * @param pw Writes the data to the file
     */
    public void save(PrintWriter pw){
        pw.println(name);
        pw.println(originalOwners.size());
        for (Owner o : originalOwners) {
            pw.println(o.getName());
            pw.println(o.getPhone());
        }
        pw.println(foodPerDay);
        pw.println(favFood);
    }

    /**
     * Note that this only compares equality based on an animal's name.
     * @param obj the other animal to compare against.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Animal other = (Animal) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    /**
     * A basic implementation to just return all the data in string form
     */
    @Override
    public String toString() {
        return "Pet name: " + name + "\nType: " + type
                + "\nOriginal Owner: " + originalOwners + "\nFavfood: " + favFood
                + "\nFoodPerDay: " + foodPerDay + '\n';
    }

    /**
     * Orders a list by name
     *
     * @param animal Takes one animal at a time to compare to another
     * @return Returns the list ordered by name
     */
    @Override
    public int compareTo(Animal animal) {
        return this.name.toLowerCase().compareTo(animal.name.toLowerCase());
    }
}
