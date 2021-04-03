/*
Christopher Haynes (FSC ID: 1239792)
Brock Wilson
CSC3380 - M5 Assignment 2
20210411

Documentation --
https://docs.google.com/document/d/18K3Xmr4B9mN02K525hkjJeEoXWnh6NqYuYJC-IPQ2XU/edit?usp=sharing
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
		
		// Create array to hold buildings
		String[] buildingList = {
			"Emile A. Watson Administration Building", 
			"Benjamin Fine Administration Building",
			"The Water Dome",
			"Thad Buckner Building",
			"Annie Pfeiffer Chapel",
			"William H. Danforth Chapel",
			"Polk Science Building",
			"The Esplanades",
			"L.A. Raulerson Building/Three Seminars",
			"Theatre-in-the-Round",
			"Lucius Pond Ordway Building",
		};
		
		// Create adjacency matrix (Task 3)
		AdjMatrix graph = new AdjMatrix(buildingList.length);
		  
		// Print intro message
		printIntro();
		
		// ----------
		// MAIN LOOP
		// ----------
		while (flag == false) {
			
			printMenu();
			int mainMenuSelection = userInput(in);

			// ----------
			// MAIN MENU
			// ----------
			switch (mainMenuSelection) {
				
				// ----------------------------
				// OPTION 1 - GENERATE SUBSETS
				// ----------------------------
				case 1:
					// Print all subsets of building list
					generateSubsets(buildingList);
					
					// Get and print list of subsets
					List<String> result = brgc(buildingList.length);
					System.out.println("");
					
					for (int i = 0; i < result.size(); i++) {
						System.out.println(result.get(i));
					}	System.out.println("");
					break;
					
				// ------------------------
				// OPTION 2 - START A TOUR
				// ------------------------
				case 2:
					System.out.println("");
					
					// Ask for num of buildings they want to visit
					int numBuildings = getNumBuildingsInput(in);
					
					// Create Array that holds user selected buildings
					int[] requestedBldgs = new int[numBuildings];
					
					// Print list of buildings for user to see
					System.out.println("");
					printAllBuildings(buildingList);
					
					// Get buildings that user wants to visit
					requestedBldgs = getDesiredBuildings(requestedBldgs, numBuildings, in);
					System.out.println("");
					
					// Print out the buildings that the user chose
					printChosenBuildings(requestedBldgs, buildingList);
					
					break;
					
				// ------------------------
				// OPTION 3 - EXIT PROGRAM
				// ------------------------
				default:
					
					// Print messages to user
					System.out.println("");
					System.out.println("Exiting Program...");
					System.out.println("Goodbye!");
					
					// Throw flag to break from while loop
					flag = true;
					break;
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
	public static int[] getDesiredBuildings(int[] requestedBldgs, int numBuildings, Scanner in) {
		// For loop to obtain desired buildings from user
		// loops until we reach desired num provided previously
		System.out.println("Please enter each building you'd like to visit...");
		for (int i = 0; i < numBuildings; i++) {
			System.out.printf("%d: ", i+1);
			int bldgChoice = in.nextInt();
			requestedBldgs[i] = bldgChoice;
		}
		return requestedBldgs;
	}
	
	/*
	Purpose: Print out the buildings that the user selected for their tour
	*/
	public static void printChosenBuildings(int[] requestedBldgs, String[] buildingList) {
				System.out.println("You have selected the following buildings...");
		for (int i = 0; i < requestedBldgs.length; i++) {
			System.out.printf("%d. %s\n", i+1, buildingList[i]);
		}
		System.out.println("");
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
