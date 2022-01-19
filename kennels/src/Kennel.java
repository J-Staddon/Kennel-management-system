
import java.io.*;
import java.util.*;

/**
 * To model a Kennel - a collection of animals
 *
 * @author Jay Staddon
 * @version 24th March 2021
 */
public class Kennel {
    private String name;
    private ArrayList<Animal> animals;
    private int nextFreeLocation;
    private int capacity;
    private Scanner scan;
    private ArrayList<Animal> which; //Used to store animals temporary

    /**
     * Creates a kennel with a default size 20
     */
    public Kennel() {
        this(20);
        scan = new Scanner(System.in);
        which = new ArrayList<Animal>();
    }

    /**
     * Create a kennel
     *
     * @param maxNoAnimals The capacity of the kennel
     */
    public Kennel(int maxNoAnimals) {
        nextFreeLocation = 0; // no Animals in collection at start
        capacity = maxNoAnimals;
        animals = new ArrayList<Animal>(capacity); // sets up default
    }

    /**
     * This method sets the value for the name attribute
     * The purpose of the attribute is: The name of the kennel e.g. "DogsRUs"
     *
     * @param theName The name of the kennel
     */
    public void setName(String theName) {
        name = theName;
    }

    /**
     * Takes a maximum size and looks if it is a negative number or smaller than the animals ArrayList
     * Set the size of the kennel
     *
     * @param capacity The max animals we can house
     */
    public void setCapacity(int capacity) {
        if (capacity < 0){
            System.err.println("You can't have negative space in the kennel!");
        }
        else {
            if (animals.size() > capacity) {
                System.err.println("You have more than " + capacity + " animal in the kennel already!");
            } else {
                this.capacity = capacity;
            }
        }
    }

    /**
     * Maximum capacity of the kennels
     *
     * @return The max size of the kennel
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * This method gets the value for the name attribute
     * The purpose of the attribute is: The name of the Kennel e.g. "DogsRUs"
     *
     * @return String The name of the kennel
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the number of animals in a kennel
     *
     * @return int Current number of dogs in the kennel
     */
    public int getNumOfAnimals() {
        return nextFreeLocation;
    }

    /**
     * Enables a user to add a Animal to the Kennel
     * It compares the number of animals and the capacity
     */
    public void addAnimal(Animal thePet) {
        if (nextFreeLocation >= capacity) {
            System.out.println("Sorry kennel is full - cannot add animal");
            return;
        }
        // we add in the position indexed by nextFreeLocation
        // This starts at zero
        animals.add(thePet);

        // now increment index ready for next one
        nextFreeLocation = nextFreeLocation + 1;
    }

    /**
     * Enables a user to delete a Animal from the kennel
     * If the arraylist has two or more of the same named animal
     * which will hold them until the user decides which to delete
     *
     * @param who The animal to remove
     */
    public void removeAnimal(String who) {
        searchAnimal(who);
        if (!which.isEmpty()) {
            if (which.size() > 1){
                System.out.println("Enter the number of which " + who + " you want to remove" );
                int removeValue;
                removeValue = scan.nextInt();
                animals.remove(which.get(removeValue - 1));
            }
            else{
                animals.remove(which.get(0));
            }
            System.out.println("Removed " + who);
            nextFreeLocation = nextFreeLocation - 1;
        }
    }

    /**
     * Goes through the animals array and looks for the name the user entered
     * It will use the arraylist 'which' that will hold each animal with the name entered
     *
     * @param who Takes the name the user is searching for
     */
    public void searchAnimal(String who){
        int num = 0;
        for (Animal a : animals) {
            if (who.toLowerCase().equals(a.getName().toLowerCase())) {
                which.add(a);
                num++;
                System.out.println(num + "\n" + a);
            }
        }
        if (which.isEmpty()){
            System.err.println("cannot find animal - not in kennel");
        }
    }

    /**
     * Clears the arraylist which
     */
    public void clearWhich(){
        which.clear();
    }

    /**
     * @return String (built with a StringBuilder) showing information from the kennel
     * The user has a choice if they want to see just cats, just dogs or all animals
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Data in Kennel: " + name + " is: \n\n");
        Collections.sort(animals);
        System.out.println("Do you want to search for: \n1: Dogs\n2: Cats\n3: All");
        String viewer;
        viewer = scan.nextLine().toUpperCase();

        for (Animal a : animals) {

            switch (viewer) {
                case "1":
                case "DOGS":
                    if (a.getType().equals("Dog")) {
                        sb.append(a.toString()).append("\n");
                    }
                    break;
                case "2":
                case "CATS":
                    if (a.getType().equals("Cat")) {
                        sb.append(a.toString()).append("\n");
                    }
                    break;
                case "3":
                case "ALL":
                    sb.append(a.toString()).append("\n");
                    break;
                default:
                    System.err.println("This animal is not in our system, please try again");
            }
        }
        return sb.toString();
    }

    /**
     * Reads in Kennel information from the file
     *
     * @param infileName The file to read from
     * @throws FileNotFoundException if file doesn't exist
     * @throws IOException if some other IO error occurs
     */
    public void load(String infileName) throws IOException, FileNotFoundException {

        try (Scanner infile = new Scanner(new FileReader(infileName));) {
            infile.useDelimiter("\r?\n|\r");

            name = infile.next();
            capacity = infile.nextInt();

            while (infile.hasNext()) {

                Animal animal = null;
                String type = infile.next();
                switch (type) {
                    case "Dog":
                        animal = new Dog();
                        break;
                    case "Cat":
                        animal = new Cat();
                        break;
                }
                if (animal != null) {
                    animal.load(infile);
                    animal.setType(type);
                    this.addAnimal(animal);
                }
            }
        }
    }

    /**
     * Saves the kennel information
     *
     * @param filename The file to save to
     * @throws IOException If some IO error occurs
     */
    public void save(String filename) throws IOException {
        try (FileWriter fw = new FileWriter(filename);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw);) {

            outfile.println(name);
            outfile.println(capacity);
            for (Animal a : animals) {
                a.save(outfile);
            }
        }
    }
}
