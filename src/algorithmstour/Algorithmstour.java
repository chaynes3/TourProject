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
			"Administration", // 0 
			"The Water Dome", // 1
			"Thad Buckner Building", // 2
			"Annie Pfeiffer Chapel", // 3
			"William H. Danforth Chapel", // 4
			"Polk Science Building", // 5
			"The Esplanades", // 6
			"L.A. Raulerson Building/Three Seminars", // 7 (AKA financial)
			//"Theatre-in-the-Round" -> (Merged with ordway)
			"Lucius Pond Ordway Building", // 8
		};
		
		// Create adjacency matrix (Task 3)
		AdjMatrix graph = new AdjMatrix(buildingList.length);
		// Annie Pfiefer -> ...
		graph.addEdge(3, 4, 245);
		graph.addEdge(3, 2, 222);
		graph.addEdge(3, 6, 230);
		// Polk Science -> ...
		graph.addEdge(5, 3, 165);
		graph.addEdge(5, 6, 334);
		graph.addEdge(5, 2, 334);
		graph.addEdge(5, 7, 175);
		// Buckner -> ...
		graph.addEdge(2, 0, 242);
		// Admissions -> ...
		graph.addEdge(0, 1, 101);
		graph.addEdge(0, 7, 309);
		// Waterdome -> ...
		graph.addEdge(1, 7, 111);
		// Ordway -> ...
		graph.addEdge(8, 6, 444);
		graph.addEdge(8, 7, 442);
		graph.addEdge(8, 1, 553);

		//graph.printMatrix();
		  
		// Print intro message
		printIntro();
		
		// ----------
		// MAIN LOOP
		// ----------
		while (flag == false) {
			
			printMenu();
			String mainMenuSelection = userInput(in);

			// ----------
			// MAIN MENU
			// ----------
			switch (mainMenuSelection) {
				
				// ----------------------------
				// OPTION 1 - GENERATE SUBSETS
				// ----------------------------
				case "1":
					// Calculate and print number of subsets
					printSubsetHeader(buildingList.length);
					
					// Generate subsets for buildingsList
					List<String> allSubsets = brgc(buildingList.length);
					System.out.println("");
					
					for (int i = 0; i < allSubsets.size(); i++) {
						System.out.println(allSubsets.get(i));
					}	System.out.println("");
					break;
					
				// ------------------------
				// OPTION 2 - START A TOUR
				// ------------------------
				case "2":
					System.out.println("");
					
					// Ask for num of buildings they want to visit
					int numBuildings = getNumBuildingsInput(in);
					
					// Create Array that holds user selected buildings
					int[] requestedBldgs = new int[numBuildings];
					
					// Print list of buildings for user to see
					System.out.println("");
					printAllBuildings(buildingList);
					
					// Get buildings that user wants to visit
					requestedBldgs = requestDesiredBuildings(requestedBldgs, numBuildings, in);
					System.out.println("");
					
					// Print out the buildings that the user chose
					printChosenBuildings(requestedBldgs, buildingList);



					// Find optimal route

					// Generate subsets for requestedBldgs
					List<String> chosenSubsets = brgc(requestedBldgs.length);
					System.out.println("");

					// Make new Adjacency Matrix to host our requested buildings only
					AdjMatrix tourGraph = graph.subMatrix(requestedBldgs);
					System.out.println("Adjacency Matrix depicting selected architecture");
					tourGraph.printMatrix();
					System.out.println("");
					
					
					// Here we check to ensure the graph is connected.  
					// If it isn't, we display an error message to the user.
					if (!isConnected(tourGraph)) {
						System.out.println("Sorry, one or more buildings doesn't have a path.");
						System.out.println("");
					}
					

					break;

				// ------------------------
				// OPTION 3 - Display Adjacency Matrix
				// ------------------------
				case "3":
					
					// Print Adjacency Matrix
					System.out.println();
					graph.printMatrix();
					System.out.println();
					
					break;
					
				// ------------------------
				// OPTION 4 - EXIT PROGRAM
				// ------------------------
				default:
					
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
        System.out.println("Christopher Haynes & Brock Wilson");
        System.out.println("April 11th, 2021");
        System.out.println("");
        System.out.println("");
	}
	
	
	/*
	Purpose: Prints a menu for the user to make selections
	*/
	public static void printMenu() {
		String[] options = {
			"Generate All Subsets",
			"Start a Tour",
			"Display Adjacency Matrix",
			"Exit the program"
		};
		for (int i = 0; i < options.length; i++) {
			System.out.printf("%d. %s\n", i+1, options[i]);
		}
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
	public static void printSubsetHeader(int numBuildings) {
		
		// Print result header
		System.out.println("");
		System.out.println("----------------------------");
		System.out.printf("------- %d subsets -------\n", 
				(int) Math.pow(2, numBuildings));
		System.out.println("----------------------------");
		System.out.println("");
	}
	
	
	/*
	Purpose: Obtain/return user input for any numerical menu
	*/
	public static String userInput(Scanner in) {
		System.out.print("Please make your selection from the numbers above: ");
		return in.next();
	}
	
	
	/*
	Purpose: Get list of desired bldgs to visit from user
	*/
	public static int[] requestDesiredBuildings(int[] requestedBldgs, int numBuildings, Scanner in) {
		// For loop to obtain desired buildings from user
		// loops until we reach desired num provided previously
		System.out.println("Please enter each building you'd like to visit...");
		for (int i = 0; i < numBuildings; i++) {
			System.out.printf("#%d, Building ID: ", i+1);
			requestedBldgs[i] = in.nextInt();
		}
		return requestedBldgs;
	}
	
	/*
	Purpose: Print out the buildings that the user selected for their tour
	*/
	public static void printChosenBuildings(int[] requestedBldgs, String[] buildingList) {
		System.out.println("You have selected the following buildings...");
		for (int i = 0; i < requestedBldgs.length; i++) {
			System.out.printf("%d. %s\n", requestedBldgs[i], buildingList[requestedBldgs[i]-1]);
		}
		System.out.println("");
	}
	
	
	/*
	Purpose: Check to ensure user-selected buildings all have edges
	
	Note: Check textbook page 124, last paragraph on page regarding the use
			of DFS for verifying connectivity of a graph
	*/
	public static boolean isConnected(AdjMatrix tourGraph) {
		int count = 0;
		
		// Create list to mark nodes once they've been visited
		// Make same length as numNodes in Matrix & set all to false
		List<Boolean> visited = new ArrayList<>();
		for (int i = 0; i < tourGraph.totalNodes; i++) {
			visited.add(Boolean.FALSE);
		}
		
		// Run DFS
		dfs(tourGraph, visited, 0);
		
		// Iterate through visited list and count num of visited nodes
		for (int i = 0; i < tourGraph.getTotalNodes(); i++) {
			if (visited.get(i)) {
				count++;
			}
		}
		
		// If num visited nodes match num of nodes, return true
		// else return false
		return count == tourGraph.getTotalNodes();
		
	}
	
	
	/*
	Purpose: Our implementation of DFS
	*/
	public static void dfs(AdjMatrix tourGraph, List<Boolean> visited, int count) {

		// If we haven't visited the node, mark it visited
		if (!visited.get(count)) { 
			visited.set(count, Boolean.TRUE);
			count++;
			
			// Loop over nodes in AdjMtx and recursively call dfs,
			//   following edges and stopping on nodes that are not visited.
			for (int i = 0; i < tourGraph.getTotalNodes(); i++) {
				if ((tourGraph.getMatrix()[count][i] >= 1) && !visited.get(i)) {
					dfs(tourGraph, visited, count);
				}
			}
		}
		
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
	Purpose: To generate all permutations and check which has the lowest weight
	
	NOTE: In progress...algorithm in textbook, page 146
	*/
	public static List<Integer> checkAllPermutations(int n) {
		
		// If we even need this...not sure how to store the permutations yet
		List<Integer> results = new ArrayList<>();
		return results;
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
