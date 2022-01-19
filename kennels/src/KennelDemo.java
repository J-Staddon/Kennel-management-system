import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class runs a Kennel
 *
 * @author Jay Staddon
 * @version 24th March 2021
 */
public class KennelDemo {
    private String filename; // holds the name of the file
    private Kennel kennel; // holds the kennel
    private Scanner scan; // so we can read from keyboard

    /*
     * Makes new kennel
     * Asks the user to enter the file before continuing
     */
    private KennelDemo() {
        scan = new Scanner(System.in);
        System.out.print("Please enter the filename of kennel information: ");
        filename = scan.nextLine();
        kennel = new Kennel();
    }

    /*
     * initialise() runs from main()
     * Reads from a file and loads data
     */
    private void initialise() {
        System.out.println("Using file " + filename);

        try {
            kennel.load(filename);
            //Displays kennel name when file opens
            System.out.println("\nKennel: " + kennel.getName());
        } catch (FileNotFoundException e) {
            System.err.println("The file: " + filename + " does not exist. Assuming first use and an empty file." +
                    " If this is not the first use then have you accidentally deleted the file?");
        } catch (IOException e) {
            System.err.println("An unexpected error occurred when trying to open the file " + filename);
            System.err.println(e.getMessage());
        }
    }

    /*
     * runMenu() method runs from main()
     * Allows entry of data etc
     */
    private void runMenu() {
        String response;
        do {
            printMenu();
            System.out.println("What would you like to do:");
            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch (response) {
                case "1":
                    admitAnimal();
                    break;
                case "2":
                    changeKennelName();
                    break;
                case "3":
                    printAll();
                    break;
                case "4":
                    searchForAnimal();
                    break;
                case "5":
                    removeAnimal();
                    break;
                case "6":
                    setKennelCapacity();
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Try again");
            }
        } while (!(response.equals("Q")));
    }

    /*
     * printMenu() runs from runMenu()
     * Displays all options to the user
     */
    private void printMenu() {
        System.out.println("\n1 -  Add a new Animal ");
        System.out.println("2 -  Set up Kennel name");
        System.out.println("3 -  Display all inmates");
        System.out.println("4 -  Search for an animal");
        System.out.println("5 -  Remove an animal");
        System.out.println("6 -  Set kennel capacity");
        System.out.println("q -  Quit");
    }

    /*
     * setKennelCapacity() runs from runMenu()
     * Takes the max kennel size from user input and passes it to kennel.setCapacity()
     */
    private void setKennelCapacity() {
        System.out.print("Enter max number of animals: ");
        int max = scan.nextInt();
        scan.nextLine();
        kennel.setCapacity(max);
    }

    /*
     * printAll() runs from runMenu()
     * Prints the toString of kennel
     */
    private void printAll() {
        System.out.println(kennel);
    }

    /*
     * removeAnimal() runs from runMenu()
     * Takes the name of the animal the user wants to remove
     */
    private void removeAnimal() {
        System.out.println("Which animal do you want to remove");
        String animalToRemove;
        animalToRemove = scan.nextLine();
        System.out.println("");
        kennel.removeAnimal(animalToRemove);
        //clears arrayList used to hold multiple animals in kennel.searchAnimal()
        kennel.clearWhich();
    }

    /*
     * searchForAnimal() runs from runMenu()
     * Takes the name of the animal the user wants to search for
     */
    private void searchForAnimal() {
        System.out.println("Which animal do you want to search for");
        String name = scan.nextLine();
        kennel.searchAnimal(name);
        //clears arrayList used to hold multiple animals in kennel.searchAnimal()
        kennel.clearWhich();
    }

    /*
     * changeKennelName() runs from runMenu()
     * Sets the name of the kennel to what the user enters
     */
    private void changeKennelName() {
        System.out.println("Enter new kennel name:");
        String name = scan.nextLine();
        kennel.setName(name);
    }

    /*
     * admitAnimal() runs from runMenu()
     * Asks the user what type of animal they want to add to the kennel
     * It then takes them through the creation process of making their chosen animal
     */
    private void admitAnimal() {
        Animal animal = null;
        System.out.println("What kind of animal do you want to add");
        System.out.println("1: Dog\n2: Cat\n0: Cancel");
        String type = scan.nextLine().toUpperCase();
        switch (type) {
            case "1":
            case "DOG":
                animal = new Dog();
                break;
            case "2":
            case "CAT":
                animal = new Cat();
                break;
                //Allows the user to change their mind and cancel making an animal
            case "0":
            case "CANCEL":
                break;
            default:
                System.err.println("This animal is not in our system, please try again");
        }
        if (animal != null){
            animal.readKeyboard();
            ArrayList<Owner> owners = getOwners();
            //Adds owners to the animal
            for(Owner o: owners){
                animal.addOriginalOwner(o);
            }
            //Adds the animal to the kennel
            kennel.addAnimal(animal);
        }
    }

    /*
     * getOwners() runs from admitAnimal()
     * Lets the user create as many owners as they want for their animal
     */
    private ArrayList<Owner> getOwners() {
        ArrayList<Owner> owners = new ArrayList<Owner>();
        String answer;
        do {
            System.out.println("Enter owners name");
            String ownName = scan.nextLine();
            System.out.println("Enter owners phone number");
            String ownPhone = scan.nextLine();
            Owner own = new Owner(ownName, ownPhone);
            owners.add(own);
            System.out.println("Another owner (Y/N)?");
            answer = scan.nextLine().toUpperCase();
        } while (!answer.equals("N"));
        return owners;
    }

    /*
     * save() runs from main()
     * Takes the filename and runs kennel.save()
     */
    private void save(String fileName) {
        try {
            kennel.save(fileName);
        } catch (IOException e) {
            System.err.println("Problem when trying to write to file: " + fileName);
        }
    }

    /*
     * saveFile runs from main()
     * Sends filenames to save(), allowing a backup file to exist
     */
    private void saveFile(){
        save(filename);
        save("data-bck.txt");
    }

    ////////////////////////////////////////////////////
    public static void main(String args[]) {
        System.out.println("\n***********HELLO***********\n");
        //Makes new kennel
        KennelDemo demo = new KennelDemo();
        //Loads file
        demo.initialise();
        //Displays main menu
        demo.runMenu();
        //Saves files
        demo.saveFile();
        System.out.println("***********GOODBYE***********");
    }
}
