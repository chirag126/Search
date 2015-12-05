/* Breadth First Search for 8-Puzzle
	 * Author : Chirag Agarwal
*/

import java.util.*;
 
public class BFSPuzzle {
	    Queue<String> q = new LinkedList<String>();    			  		 // Use of Queue Implemented using LinkedList for Storing All the Nodes in BFS.
	    Map<String,Integer> myMapDepth = new HashMap<String, Integer>(); // HashMap is used to store new nodes and ignore repeated nodes
	    Map<String,String> myMapHistory = new HashMap<String, String>(); // HashMap is used to store new nodes and ignore repeated nodes
	    long startTime = System.currentTimeMillis();					 // Starting the Timer
	    static String initialState="724506831";            			     // input the initial board state as a string with '0' as the blank space
	    public static void main(String args[]){
	    	BFSPuzzle e = new BFSPuzzle();          					 // a new instance of the class 'BFSPuzzle'
	        e.add(initialState,null);                           	     // adding the initial state to the HashMap
	        while(e.q.peek()!=null){
	            e.up(e.q.peek());                   					 // Move the blank space up    and add new state to Queue
	            e.down(e.q.peek());                 				     // Move the blank space down  and add new state to Queue 
	            e.left(e.q.peek());                 					 // Move the blank space left  and add new state to Queue
	            e.right(e.q.remove());              					 // Move the blank space right and add new state to Queue
	        }
	        System.out.println("Solution doesn't exist");
	    }
	     
	    //Add method to add the new string to the Map and Queue
	    void add(String newState,String oldState ){
	        if(!myMapDepth.containsKey(newState)){
	        	int newValue = (oldState == null) ? 0 : myMapDepth.get(oldState) + 1;
	            myMapDepth.put(newState, newValue);
	            q.add(newState);
	            myMapHistory.put(newState,oldState);
	        }     
	    }
	    
	    // Visualizing the Board configuration after each operation
	    void visualizeFlow(String str, int n) {
	    	System.out.println("Depth: " +n+ "-->");
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

	    /* Following is the respective Up, Down, Left and Right Operation on the Blank space and then they are put in the Map if not repeated */

		// Left operation on the Blank space    
	    void left(String str){
	        int val = str.indexOf("0");
	        if(val!=0 && val!=3 && val!=6){
	            String s = str.substring(0,val-1)+"0"+str.charAt(val-1)+str.substring(val+1);
	            add(s,str);
	            // Checks whether the respective State is the Goal State
	            printFlow(s);
	        }     
	    }
	    
	    // Right operation on the Blank space
	    void right(String str){       
	    	int val = str.indexOf("0");
	        if(val!=2 && val!=5 && val!=8){
	            String s = str.substring(0,val)+str.charAt(val+1)+"0"+str.substring(val+2);
	            add(s,str);
	            // Checks whether the respective State is the Goal State
	            printFlow(s);
	        }
	    }
	    
	 // Up operation on the Blank space
	    void up(String str){
	        int val = str.indexOf("0");
	        if(val>2){
	            String s = str.substring(0,val-3)+"0"+str.substring(val-2,val)+str.charAt(val-3)+str.substring(val+1);
	            add(s,str);
	            // Checks whether the respective State is the Goal State
	            printFlow(s);
	        }     
	    }
	    
	    // Down operation on the Blank space
	    void down(String str){
	        int val = str.indexOf("0");
	        if(val<6){
	            String s = str.substring(0,val)+str.substring(val+3,val+4)+str.substring(val+1,val+3)+"0"+str.substring(val+4);
	            add(s,str);
	            // Checks whether the respective State is the Goal State
	            printFlow(s);
	        }
	    }
	    void printFlow(String data){		//new State = GOAL
	    	if(data.equals("123456780")) {
	    		System.out.println("8 Puzzle using Breadth First Search ");
                System.out.println("Solution exists at Depth : "+myMapDepth.get(data)+" of the tree");
                String trackState = data;
                while (trackState != null) {
                	visualizeFlow(trackState,myMapDepth.get(trackState));
                    trackState = myMapHistory.get(trackState);
                }
                long endTime   = System.currentTimeMillis();
                System.out.println("Time taken : " +(endTime-startTime)+ " milliseconds");
                System.exit(0);
            }
	    }
}
