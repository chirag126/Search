/* Depth First Search for 8-Puzzle
 * Author : Chirag Agarwal
*/

import java.util.*;
import java.lang.OutOfMemoryError;

public class DFSPuzzle {
	
	// frontier is the linkedlist that store frontiers
	// myMapHistory to stores the parent key associate with the child value
	// myMapDepth to store the depth of the node
	private LinkedList<String> frontier;
	private HashMap<String, String> myMapHistory;
	private HashMap<String, Integer> myMapDepth;
	long startTime = System.currentTimeMillis();					 // Starting the Timer
	
	private String goal, current;
	int limit;
	DFSPuzzle()
	{
		// do nothing
	}
	
	public static void main(String[] args) {	
		String start = "724506831";				// The Initial State of the Board
		String goal =  "123456780";				// Goal state of the Board
		int depthLimit = 25;					// Depth Limit
		
		// Sending the Initial State, Final State and the Depth Limit
		DFSPuzzle b = new DFSPuzzle(start, goal, depthLimit);
		b.search();
	}
	
	DFSPuzzle(String initial, String finalGoal, int theLimit)
	{
		frontier = new LinkedList<String>();
		myMapDepth = new HashMap<String, Integer>();
		myMapHistory = new HashMap<String, String>();
		
		// Initializing the Initial State and the Goal State and the Depth Limit of the search
		current = initial;
		goal = finalGoal;
		limit = theLimit;		
	}

	public void search()
	{
		System.out.println("8 Puzzle Depth First Search problem:");
		int i = 0;
		int temp;
		System.out.println("The limit is: " + limit+ "\n");
		
		// Check on the DepthLimit of the Search
		while(i < limit)
		{
			temp = i + 1;
			System.out.println(temp + "th Depth!");
			enFrontier(current, null);
			findSolution(i);
			i++;
		}
	}
	
	private void findSolution(int currentLimit)
	{ 
		int zeroPosition = 0;
		int swapPosition = 0;
		int childDepth = 0;
		String nextChild = "";

		// if it is empty and the solution has not been found then there is no solution
		while(!frontier.isEmpty())
		{
			try{
				current = frontier.getFirst();
				
				// Checking if we reached the GOAL state
				if(current.equals(goal))
				{
					// Visualizing the Board
					printFlow();
	                long endTime   = System.currentTimeMillis();
	                System.out.println("Time taken : " +(endTime-startTime)+ " milliseconds");
					System.exit(0);
				}
				
				childDepth = myMapDepth.get(current);
				
				if(childDepth < currentLimit && childDepth != -1)
				{	
					zeroPosition = current.indexOf("0");
			
					// expansion and generate child 
					
					// Move UP operation
					if(zeroPosition != 0 && zeroPosition != 1 && zeroPosition != 2){
						swapPosition = zeroPosition - 3;
						nextChild = moveBlank(zeroPosition, swapPosition);
						enFrontier(nextChild, current);
					}
					
					// Move LEFT operation
					if(zeroPosition != 0 && zeroPosition != 3 && zeroPosition != 6){
						swapPosition = zeroPosition - 1;
						nextChild = moveBlank(zeroPosition, swapPosition);
						enFrontier(nextChild, current);
					}
					
					// Move RIGHT operation
					if(zeroPosition != 2 && zeroPosition != 5 && zeroPosition != 8){
						swapPosition = zeroPosition + 1;
						nextChild = moveBlank(zeroPosition, swapPosition);
						enFrontier(nextChild, current);
					}
					
					// Move DOWN operation
					if(zeroPosition != 6 && zeroPosition != 7 && zeroPosition != 8){
						swapPosition = zeroPosition + 3;
						nextChild = moveBlank(zeroPosition, swapPosition);
						enFrontier(nextChild, current);
					}
				}
				else
				{
					String temp = frontier.removeFirst();
					myMapHistory.remove(temp);
					myMapDepth.remove(temp);
				}
				
				myMapDepth.put(current, -1);
			}
			
			// For Checking if we go out of memory
			catch (OutOfMemoryError E) 
			{
				System.out.println("Out of Memory!");
			}
		}
		System.out.println("No solution!");
		System.out.println("\n");
	}
	
	// Used for performing the UP,DOWN,LEFT & RIGHT operation
	private String moveBlank(int zeroPosition, int swapPosition)
	{
		String originalString = current;
		char[] c = originalString.toCharArray();
		char temp = c[zeroPosition];
		c[zeroPosition] = c[swapPosition];
		c[swapPosition] = temp;
		String swappedString = new String(c);
		return swappedString;
	}
	
	private void enFrontier(String child, String parent)
	{
		int childDepth = 0;
		
		// Checking if the child is already being visited and if not put the newChild in the queue 
		if(!myMapHistory.containsKey(child)){
			
			// If we don't find the solution then we add subsequent child in the first position of the
			// frontier and then we expand and discover current parent & then their children
			frontier.addFirst(child);
			if(parent == null)
				childDepth = 0;		// FOr the Initial state
			else
				childDepth = myMapDepth.get(parent) + 1;
			
			// Using the child state to find the parent state inorder to get the Output Flow
			myMapHistory.put(child, parent);
			myMapDepth.put(child, childDepth);
		}
	}
	
	// Used for visualizing the String into Board form
	private void printFlow()
	{		 
		if(current != null)
		{
			String temp = current;
			current = myMapHistory.get(current);
			printFlow();
			visualizeFlow(temp);
		}
	}
	
	// Visualizing the Board configuration after each operation
    private void visualizeFlow(String str) {
    	for (int c=0;c<str.length();c++){
    		if(c!=2 && c!=5 && c!=8){
    			if (str.charAt(c) != 0)
    				System.out.print(str.charAt(c)+ "   " );
    			else
    				System.out.print("      ");
    		}
    		else{
    			if (str.charAt(c) != 0)
    				System.out.println(str.charAt(c));
    			else
    				System.out.println(str.charAt(c));
    		}
    	}
    	System.out.println();
	}
}
