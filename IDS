package search;

/* Iterative Depth Search for 16-Puzzle
 * Author : Chirag Agarwal
*/

import java.util.*;

public class IDS {
	
	// Initializing
	private LinkedList<String> frontier;
	private HashMap<String, Integer> myMapDepth;
	private HashMap<String, String> myMapHistory;
	
	// Starting the Timer
	long startTime = System.currentTimeMillis();
	
	// Stores the currentState and the goal State of the Problem
	private String current;
	private String goal;
	int limit;
	
	IDS(){
		
	}
	
	IDS(String initialState, String finalState, int depth){
		frontier = new LinkedList<String>();
		myMapDepth = new HashMap<String, Integer>();
		myMapHistory = new HashMap<String, String>();
		
		current = initialState;
		goal = finalState;
		limit = depth;
	}
	
	public static void main(String args[]){
		
		// The input state given and the goal state in Array form
		int [] initialArr = {1,0,2,4,5,7,3,8,9,6,11,12,13,10,14,15};
		int [] goalArr = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
		
		// Converting the array to string
		String initialState = convert(initialArr);
		String goalState = convert(goalArr);
		
		IDS ids = new IDS(initialState,goalState,12);
		ids.operate();
	}

	// Method for Converting the Array to String
	private static String convert(int[] arr) {
		// TODO Auto-generated method stub
		String conStr = "";
		for (int i=0; i < arr.length; i++){
			if (arr[i] >=0 && arr[i] <=9)
				conStr = conStr + Integer.toString(arr[i]);
			else if (arr[i] == 10)
				conStr = conStr + 'a';
			else if (arr[i] == 11)
				conStr = conStr + 'b';
			else if (arr[i] == 12)
				conStr = conStr + 'c';
			else if (arr[i] == 13)
				conStr = conStr + 'd';
			else if (arr[i] == 14)
				conStr = conStr + 'e';
			else if (arr[i] == 15)
				conStr = conStr + 'f';
			else
				System.out.println("Invalid Input !!!");
				
		}
		//System.out.println(conStr);
		return conStr;
	}

	
	private void operate() {
		// TODO Auto-generated method stub
		System.out.println("--: Iterative Deepening Depth First Search :--");
		System.out.println("The Depth Limit of the Search is "+limit);
		int i=0;
		while(i < limit){
			System.out.println("\nDepth : " +(i+1));
			update(current,null);
			expand(i);
			i++;
		}

	}
	
	// Doing the respective operations and adding them to the frontier
	private void expand(int currentDepth) {
		// TODO Auto-generated method stub
		String newChild = "";
		int zeroPos = 0;
		int newChildDepth = 0;
		while(!frontier.isEmpty()){
			try{
				current = frontier.peekFirst();
				
				if (current.equals(goal)){
					System.out.println("SUCCESSSS !!!");
					System.out.println("\nFlow of the Solution :");
					printFlow();
					
					// Stopping the timer
					long endTime = System.currentTimeMillis();
					System.out.println("Time Elapsed : " +(endTime-startTime)+ " milliseconds");
					System.exit(0);
				}
				
				newChildDepth = myMapDepth.get(current);
				
				//visualizeFlow(current);
				if (newChildDepth < currentDepth && newChildDepth != -1){
					zeroPos = current.indexOf("0");
					
					// MOVE UP
					if(zeroPos != 0 && zeroPos != 1 && zeroPos != 2 && zeroPos != 3){
						newChild = current.substring(0,zeroPos-4) + "0" + current.substring(zeroPos-3, zeroPos) + 
								   current.charAt(zeroPos-4) + current.substring(zeroPos+1);
						update(newChild, current);
						//System.out.println("UP :- ");visualizeFlow(newChild);
					}
					
					// MOVE LEFT
					if(zeroPos != 0 && zeroPos != 4 && zeroPos != 8 && zeroPos != 12){
						newChild = current.substring(0,zeroPos-1) + "0" + current.charAt(zeroPos-1) + current.substring(zeroPos+1);
						update(newChild, current);
						//System.out.println("LEFT :- ");visualizeFlow(newChild);
					}
					
					// MOVE RIGHT
					if(zeroPos != 3 && zeroPos != 7 && zeroPos != 11 && zeroPos != 15){
						newChild = current.substring(0,zeroPos) + current.charAt(zeroPos+1) + "0" + current.substring(zeroPos+2);
						update(newChild, current);
						//System.out.println("RIGHT :- ");visualizeFlow(newChild);
					}
					
					// MOVE DOWN
					if(zeroPos != 12 && zeroPos != 13 && zeroPos != 14 && zeroPos != 15){
						newChild = current.substring(0,zeroPos) + current.charAt(zeroPos+4) + current.substring(zeroPos+1, zeroPos+4) + 
								   "0" + current.substring(zeroPos+5);
						update(newChild, current);
						//System.out.println("DOWN :- ");visualizeFlow(newChild);
					}
				}
				else{
					String remChild = frontier.pollFirst();
					myMapHistory.remove(remChild);
					myMapDepth.remove(remChild);
				}
				myMapDepth.put(current, -1);
			}
			catch (OutOfMemoryError E) 
			{
				System.out.println("Out of Memory!");
			}
		}
		System.out.println("No Solution found at Depth :" +(currentDepth+1));
	}

	// Updating the Depth table nda the history table
	private void update(String child, String parent) {
		// TODO Auto-generated method stub
		int childDepth = 0;
		if(!myMapHistory.containsKey(child)){
			frontier.addFirst(child);
			if (parent == null)
				childDepth = 0;
			else
				childDepth = myMapDepth.get(parent) + 1;
			
			myMapDepth.put(child, childDepth);
			myMapHistory.put(child, parent);
		}		
	}
	
	// Printing the flow solution
	private void printFlow()
	{		 
		if(current != null)
		{
			String abc = current;
			current = myMapHistory.get(current);
			printFlow();
			visualizeFlow(abc);
		}
	}
	
	// Visualizing the Board configuration after each operation
    private void visualizeFlow(String str) {
    	//System.out.println("Depth: " +n+ "-->");
    	ArrayList<Integer> modiArr = str2Int(str);
    	for(int i = 0; i < 16; i++)
		{
			if(i%4 == 0 && i != 0)
				System.out.println();
			System.out.printf(modiArr.get(i) + " ");
		}
		System.out.println("\n");
    	//System.out.println(modiArr);
    	//System.out.println();
	}

    // Convertng the string to respective Integer Input
	private ArrayList<Integer> str2Int(String str) {
		// TODO Auto-generated method stub
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for (int i = 0;i<str.length();i++){
			if(str.charAt(i) == 'a')
				arr.add(10);
			else if(str.charAt(i) == 'b')
				arr.add(11);
			else if(str.charAt(i) == 'c')
				arr.add(12);
			else if(str.charAt(i) == 'd')
				arr.add(13);
			else if(str.charAt(i) == 'e')
				arr.add(14);
			else if(str.charAt(i) == 'f')
				arr.add(15);
			else
				arr.add(str.charAt(i)-48);
		}
		return arr;
	}
}
