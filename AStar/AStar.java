/* Author: Chirag Agarwal
 * Implementation of A* Algorithm with both
 * Manhattan Distance and Misplaced Tile Heuristics
 */

package search;

public class AStar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] current = {1,2,0,3,4,5,6,7,8,9,10,11,12,13,14,15};
		int[] goal = 	{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		
		//MisplacedTile a = new MisplacedTile(convert(current),convert(goal));
		//a.search();
		
		//ManhattanDist b = new ManhattanDist(convert(current), convert(goal));
		//b.search();
	
		//MisplacedTileDepth c = new MisplacedTileDepth(convert(current), convert(goal));
		//c.search();
		
		ManhattanDistDepth d = new ManhattanDistDepth(convert(current), convert(goal));
		d.search();
		
		//IDS e = new IDS(convert(current),convert(goal),30);
		//e.operate();
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
		return conStr;
	}
}
