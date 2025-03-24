import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	
	public static void main(String[] args) {
        System.out.println("Searchable Billboard Hot 100 Database - January 2018 to May 2021");
        Scanner kb = new Scanner(System.in);
        System.out.print("Please enter name for output file: ");
        String outputFile = kb.next();

        try {
            processBillboardData(kb, outputFile);
        } catch (FileNotFoundException e) {
            System.out.println("Error! Unable to build song list.");
            e.printStackTrace();
        } finally {
            System.out.println("\nProgram exited successfully.");
        }
    }

    private static void processBillboardData(Scanner kb, String outputFile) throws FileNotFoundException {
        Scanner songsIn = new Scanner(new FileReader("hot.stuff.2018.csv"));
        Billboard100 songList = new Billboard100(songsIn);
        try (FileWriter writer = new FileWriter(outputFile)) {
            handleUserCommands(kb, songList, writer);
        } catch (IOException e) {
            System.out.println("Error! Unable to write to output file.");
            e.printStackTrace();
        }
    }

    private static void handleUserCommands(Scanner kb, Billboard100 songList, FileWriter writer) {
        boolean continueInput = true;
        while (continueInput) {
            printMenu();
            continueInput = selectOption(kb, songList, writer);
        }
    }

	private static void printMenu() {
		System.out.println("\nPlease select an option. All output will be printed to file.");
		System.out.println("0 - Quit program");
		System.out.println("1 - Print all Billboard Hot 100 Songs in the List");
		System.out.println("2 - Print all songs in the list that have been on the chart for more than 12 weeks");
		System.out.println("3 - Display all songs in a specific week");
		System.out.println("4 - Search for a performer");
		System.out.println("5 - Search for a song name");
		System.out.println("6 - Print all the songs in the list with an Instance greater than 1");
		System.out.println("7 - Print all the songs in the list whose peak position is 1");
		System.out.println("8 - Print all Songs in order by WeekID");
		System.out.println("9 - Print all Songs in alphabetical order by Song Name");
		System.out.println("10 - Print all songs in alphabetical order by Performer Name");
	}

	
	private static boolean selectOption(Scanner keyboard, Billboard100 songList, FileWriter writer) {
	    int option = getUserOption(keyboard);

	    if (option == 0) {
	        System.out.println("\nExiting program.");
	        return false; 
	    }

	  
	    try {
	        switch (option) {
	            case 1:
	                songList.displayAllSongs(writer);
	                break;
	            case 2:
	                songList.displaySongsOver12Weeks(writer);
	                break;
	            case 3:
	                songList.displayMultipleInstances(writer);
	                break;
	            case 4:
	                songList.displayTopChartSongs(writer);
	                break;
	            case 5:
	                songList.orderSongsByWeekID(writer);
	                break;
	            case 6:
	                songList.orderSongsAlphabeticallyByName(writer);
	                break;
	            case 7:
	                songList.orderSongsAlphabeticallyByPerformer(writer);
	                break;
	            default:
	                System.out.println("Invalid option selected. Please try again.");
	        }
	        System.out.println("Operation completed. Data written to file.");
	    } catch (IOException e) {
	        System.out.println("An error occurred while writing to the file: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return true; 
	}

	private static int getUserOption(Scanner keyboard) {
	    while (true) {
	        System.out.print("\nEnter option #: ");
	        try {
	            int option = keyboard.nextInt();
	            if (option >= 0 && option <= 10) {
	                return option;
	            }
	            System.out.println("Option must be between 0 and 10. Please try again.");
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input! Please enter an integer.");
	            keyboard.nextLine(); 
	        }
	    }
	}

	
	private static void printByWeek(Scanner kb, Billboard100 songList, FileWriter writer) throws IOException {
		Date selection = new Date();
		boolean continueInput = true;
		while (continueInput) {
			System.out.println("\nEnter date to display all songs for specific week.");
			System.out.println("Date must be between 1/1/2018 and 5/29/2021");
			try {
				System.out.print("Enter month: ");
				int month = kb.nextInt();
				if (month > 12 || month < 1) {
					System.out.println("\n***** Please enter valid month number *****");
					continue;
				}
				System.out.print("Enter day: ");
				int day = kb.nextInt();
				if (day > 31 || day < 1) {
					System.out.println("\n***** Please enter valid day number *****");
					continue;
				}
				System.out.print("Enter year: ");
				int year = kb.nextInt();
				if ((year >= 2021 && month > 5) || year < 2018) {
					System.out.println("\n***** Date not in range *****");
					continue;
				} else {
					selection = new Date(month, day, year);
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("");
				continue;
			}
		}
		songList.displaySongsInWeek(writer, selection);
	}

	
	 //Helper method
	 
	private static void printByPerformer(Scanner kb, Billboard100 songList, FileWriter writer) throws IOException {
		System.out.print("Enter performer(s) name: ");
		String performer = kb.next();
		songList.displaySongsByPerformer(writer, performer);

	}

	
	 //Helper method
	 
	private static void printBySong(Scanner kb, Billboard100 songList, FileWriter writer) throws IOException {
		System.out.print("Enter song name: ");
		String song = kb.next();
		songList.displaySongsByName(writer, song);
	}

}

