/*
Christopher Haynes (FSC ID: 1239792)
Brock Wilson
CSC3380 - M5 Assignment 2
20210404
 */

package algorithmstour;


public class AdjMatrix {
	
	// Variables needed for Adjacency Matrix (Represent weighted graph)
	int edgeWeight;
	int totalNodes;
	int matrix[][];
	
	
	// -------------
	// CONSTRUCTORS
	// -------------
	public AdjMatrix(int totalNodes) {
		this.totalNodes = totalNodes;
		matrix = new int[totalNodes][totalNodes];
		this.edgeWeight = 0;
	}
	
	
	// --------------
	//    METHODS
	// --------------
	
	/*
	Purpose: Add edges to our directed graph (Adjacency Matrix)
	*/
	public void addEdge(int startNode, int endNode, int edgeWeight) {
		matrix[startNode][endNode] = edgeWeight;
	}
	
			
}
