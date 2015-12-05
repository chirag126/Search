/* Author: Chirag Agarwal
 */

package search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.lang.OutOfMemoryError;
import java.lang.management.MemoryUsage;


public class MisplacedTileDepth {
	// frontier is a linked list that stores the Fringe elements
	// parentTable to stores the parent key associate with the child value
	// depthTable to store the height of the node
	private LinkedList<String> frontier;
	private HashMap<String, ChildInfo> parentTable;
	private HashMap<String, String> myMapHistory;
	private String goal, current;
	private int numNodes, addDepth;
	private int NumberMoves, f_limit, f_nextLimit;
	
    // To know the children's at the same level to expand 
    int CurrentHeight;
	MisplacedTileDepth()
	{
		// do nothing
	}
	
	MisplacedTileDepth(String intial, String finalGoal)
	{
		frontier = new LinkedList<String>();
		parentTable = new HashMap<String, ChildInfo >();
		myMapHistory = new HashMap<String, String>();
		current = intial;
		goal = finalGoal;
		numNodes = 0;		
		CurrentHeight = 0;
		addDepth = 0;
		NumberMoves = -1;
		f_nextLimit = 100; // Limit
	}

	public void search(){
		f_limit = misTiles(current);
		update(current, null, null);
		findSolution();
	}
	
	
	public void findSolution()
	{
		long startTime = System.nanoTime();
		System.out.println("Misplaced tile IDA* search!");
		System.out.printf("Initial State: \n");
		visualizeFlow(current);
		System.out.println();
		int zeroPos = 0;
		String nextChild = "";
		LinkedList<String> currentLevelChildren = new LinkedList<String>();

		// if Empty then there is no solution
		while(!frontier.isEmpty())
		{
			try{
				// test if the solution is found
				current = frontier.removeFirst();
				
				if(current.equals(goal))
				{
					System.out.println("Number of Nodes generated: " + numNodes);
					System.out.println("Number of Nodes in the memory: " + (frontier.size() + parentTable.size()));
					long endTime = System.nanoTime();
					System.out.println("Time Elapsed : " +(endTime - startTime) + " ns"); 
					System.out.printf("\nFlow of the Solution :");
					printFlow();
					System.out.println("Number of moves: " + NumberMoves);
					
					System.exit(0);
				}				

				zeroPos = current.indexOf("0");

                // Expansion and Generate New child 
				// MOVE UP
				if(zeroPos != 0 && zeroPos != 1 && zeroPos != 2 && zeroPos != 3){
					nextChild = current.substring(0,zeroPos-4) + "0" + current.substring(zeroPos-3, zeroPos) + 
							   current.charAt(zeroPos-4) + current.substring(zeroPos+1);

					if (misTiles(nextChild) <= f_limit)
						update(nextChild, current, "U");
					else
						f_nextLimit = Math.min(f_nextLimit, misTiles(nextChild));
				}
				
				// MOVE LEFT
				if(zeroPos != 0 && zeroPos != 4 && zeroPos != 8 && zeroPos != 12){
					nextChild = current.substring(0,zeroPos-1) + "0" + current.charAt(zeroPos-1) + current.substring(zeroPos+1);
					
					//System.out.println("L" + misTiles(nextChild));
					if (misTiles(nextChild) <= f_limit)
						update(nextChild, current, "L");
					else
						f_nextLimit = Math.min(f_nextLimit, misTiles(nextChild));
				}
				
				// MOVE DOWN
				if(zeroPos != 12 && zeroPos != 13 && zeroPos != 14 && zeroPos != 15){
					nextChild = current.substring(0,zeroPos) + current.charAt(zeroPos+4) + current.substring(zeroPos+1, zeroPos+4) + 
							   "0" + current.substring(zeroPos+5);
					
					if (misTiles(nextChild) <= f_limit)
						update(nextChild, current, "D");
					else
						f_nextLimit = Math.min(f_nextLimit, misTiles(nextChild));
				}
				
				// MOVE RIGHT
				if(zeroPos != 3 && zeroPos != 7 && zeroPos != 11 && zeroPos != 15){
					nextChild = current.substring(0,zeroPos) + current.charAt(zeroPos+1) + "0" + current.substring(zeroPos+2);
					
					if (misTiles(nextChild) <= f_limit)
						update(nextChild, current, "R");
					else
						f_nextLimit = Math.min(f_nextLimit, misTiles(nextChild));
				}
				
				CurrentHeight = CurrentHeight + addDepth;
				addDepth = 0;
				
				// Putting the same level children together to determine which child to expand
				while(!frontier.isEmpty() && parentTable.get(frontier.getFirst()).getHeight() == CurrentHeight)
				{
					currentLevelChildren.add(frontier.removeFirst());
				}
				
				// Current height
				if(currentLevelChildren.isEmpty() && !frontier.isEmpty())
				{
					CurrentHeight = parentTable.get(frontier.getFirst()).getHeight();
				}
				
				String temp = null;
				if(!currentLevelChildren.isEmpty())
				{
					temp = currentLevelChildren.removeFirst();
				
					while(!currentLevelChildren.isEmpty())
					{
						int currentMisplace1 = parentTable.get(temp).getMisplace();
						int currentMisplace2 = parentTable.get(currentLevelChildren.getFirst()).getMisplace();
						if( currentMisplace1 < currentMisplace2 )
						{
							frontier.addFirst(currentLevelChildren.removeFirst());
						}
						else
						{
							frontier.addFirst(temp);
							temp = currentLevelChildren.removeFirst();
						}
					}
				}
				if(temp != null)
					frontier.addFirst(temp);
				
				f_limit = f_nextLimit;
			}
			catch (OutOfMemoryError E) 
			{
				System.out.println("Out of Memory!");
				System.exit(0);
			}
		}
		System.out.println("No solution!");
	}
	
	private void update(String child, String parent, String direction)
	{
            // check for duplicate child
			if(!parentTable.containsKey(child)){
				numNodes++;
				addDepth = 1;
				int numberOfMisplaced = misTiles(child);
				int height;
				if(parent == null)
					height = 0;
				else
					height = parentTable.get(parent).getHeight() + 1;
				
				ChildInfo newChild = new ChildInfo();
				
				newChild.setHeight(height);
				newChild.setMisplace(numberOfMisplaced);
				newChild.setState(child);
				newChild.setParentState(parent);
				newChild.setDirection(direction);
				frontier.addFirst(child);
				parentTable.put(child, newChild);
				myMapHistory.put(child, parent);
			}
	}
	
    // Calculating the number of Misplaced Tiles
	private int misTiles(String child)
	{
		int stringLength = child.length();
		int i;
		int numberOfMisplaced = 0;
		for(i = 0; i < stringLength; i++)
		{
			if(child.charAt(i) != goal.charAt(i))
				numberOfMisplaced++;
		}
		
		return numberOfMisplaced;
	}
	
	private void printFlow()
	{		
		if(current != null)
		{
			NumberMoves++;
			String abc = current;
			current = myMapHistory.get(current);
			printFlow();
			visualizeFlow(abc);
		}
	}
	
	// Visualizing the Board configuration after each operation
    private void visualizeFlow(String str) {
    	ArrayList<Integer> modiArr = str2Int(str);
    	for(int i = 0; i < 16; i++)
		{
			if(i%4 == 0 && i != 0)
				System.out.println();
			System.out.printf(modiArr.get(i) + " ");
		}
		System.out.println("\n");
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
