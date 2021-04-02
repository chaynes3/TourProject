/*
Christopher Haynes (FSC ID: 1239792)
CSC3380 - M5 Assignment 2
20210404
 */

package algorithmstour;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Algorithmstour {

    public static void main(String[] args) {
		
		// While user has not chosen to exit the program
		boolean flag = false;
		
		// Create scanner to obtain user input
		Scanner in = new Scanner(System.in);
		
		// Create adjacency matrix (Task 3)
		
		
		// Create array to hold buildings
		String[] buildingList = {
			"W Admin", 
			"B Admin",
			"Water Dome",
			"Raulerson",
			"Buckner",
			"AP Chapel",
			"WD Chapel",
			"Polk Science",
			"Esplanades",
			"LP Ordway",
			"Theatre ITR",
		};
		  
		// Print intro message
		printIntro();
		
		
		while (flag == false) {
			// Print main menu and save user choice from main menu
			printMenu();
			int mainMenuSelection = userInput(in);

			// If user chose #1 - Generate All Subsets
			if (mainMenuSelection == 1) {

				// Print all subsets of building list
				generateSubsets(buildingList);
				
				// Get and print list of subsets
				List<String> result = brgc(buildingList.length);
				System.out.println("");
				for (int i = 0; i < result.size(); i++) {
					System.out.println(result.get(i));
				}
				System.out.println("");
			}

			// User selected option 2 from main menu (Start a Tour)
			else if (mainMenuSelection == 2) {
				System.out.println("");
				
				// Ask for num of buildings they want to visit
				int numBuildings = getNumBuildingsInput(in);
				
				// Create Array that holds buildings desired by user
				String[] requestedBldgs = new String[numBuildings];
				
				// Print list of buildings for user to see
				System.out.println("");
				printAllBuildings(buildingList);
				
				requestedBldgs = getDesiredBuildings(requestedBldgs, numBuildings, in);
				
				
			}

			// User elected to exit the program via option 3 of the main menu
			else {
				
				// Print messages to user
				System.out.println("");
				System.out.println("Exiting Program...");
				System.out.println("Goodbye!");
				
				// Throw flag to break from while loop
				flag = true;
			}
		}
    }
	
	
	/*
	Purpose: Prints the assignment details and student info
	*/
	public static void printIntro() {
        System.out.println("");
        System.out.println("");
        System.out.println("CSC3380 - M5 Assignment 2");
        System.out.println("Optimal Frank Lloyd Wright Tour");
        System.out.println("Christopher Haynes (FSC ID: 1239792)");
        System.out.println("March xx, 2021");
        System.out.println("");
        System.out.println("");
	}
	
	
	/*
	Purpose: Prints a menu for the user to make selections
	*/
	public static void printMenu() {
		System.out.println("1. Generate All Subsets");
		System.out.println("2. Start a Tour");
		System.out.println("3. Exit the Program");
	}
	
	
	/*
	Purpose: Print list of buildings for user to see
	*/
	public static void printAllBuildings(String[] buildingList) {
		for (int i = 0; i < buildingList.length; i++) {
			System.out.printf("%d. %s\n", i+1, buildingList[i]);
		}
		System.out.println("");
	}
	
	
	/*
	Purpose: Ask the user how many bldgs they want to visit
	*/
	public static int getNumBuildingsInput(Scanner in) {
		
		// Print user messages/directions
		System.out.println("How many buildings would you like to visit on your tour?");
		System.out.print("Please enter a number from 3 to 6 --> ");
		return in.nextInt();
	}
	
	
	/*
	Purpose: Generate all possible subsets from the passed array.
	Note: 2^n - 1, where n == num of buildings
	*/
	public static void generateSubsets(String[] buildingList) {
		
		// Print result header
		System.out.println("");
		System.out.println("----------------------------");
		System.out.printf("------- %d subsets -------\n", 
				(int) Math.pow(2, buildingList.length));
		System.out.println("----------------------------");
		System.out.println("");
	}
	
	
	/*
	Purpose: Obtain/return user input for any numerical menu
	*/
	public static int userInput(Scanner in) {
		System.out.print("Please make your selection from the numbers above: ");
		return in.nextInt();
	}
	
	
	/*
	Purpose: Get list of desired bldgs to visit from user
	*/
	public static String[] getDesiredBuildings(String[] requestedBldgs, int numBuildings, Scanner in) {
		// For loop to obtain desired buildings from user
		// loops until we reach desired num provided previously
		System.out.println("Please enter each building you'd like to visit...");
		for (int i = 0; i < numBuildings; i++) {
			System.out.printf("%d: ", i+1);
			String bldgChoice = in.next();
			requestedBldgs[i] = bldgChoice;
		}
		return requestedBldgs;
	}
	
	
	/*
	Purpose: Algorithm - Binary Reflected Gray Code of order n
	Note: This algorithm generates all bitstring subsets
	*/
	public static List<String> brgc(int n) {
		
		// Base Case #1
		if (n == 0) {
			List<String> base1List = new ArrayList<>();
			base1List.add("0");
			return base1List;
		}
		
		// Base Case #2
		if (n == 1) {
			List<String> base2List = new ArrayList<>();
			base2List.add("0");
			base2List.add("1");
			return base2List;			
		}
		
		// Recursive call saved into list
		List<String> tempList = brgc(n - 1);
		
		// Create list to hold results
		List<String> result = new ArrayList<>();

		// Add zeros
		addZeros(result, tempList);

		// Add ones
		addOnes(result, tempList);
				
		return result;
	}
	
	
	/*
	Purpose: Based on Subset Generating Algorithm (Binary Reflected Gray Code),
			add zeros in front of each bit string
	*/
	public static List<String> addZeros(List<String> result, List<String> tempList) {
		for (int i = 0; i < tempList.size(); i++) {
			String tempString = tempList.get(i);
			result.add("0" + tempString);
		}
		
		return result;
	}
	
	
	/*
	Purpose: Based on Subset Generating Algorithm (Binary Reflected Gray Code),
			add ones in front of each bit string
	*/
	public static List<String> addOnes(List<String> result, List<String> tempList) {
		for (int i = tempList.size() - 1; i >= 0; i--) {
			String tempString = tempList.get(i);
			result.add("1" + tempString);
		}
		
		return result;
	}
	

}
