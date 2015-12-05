/* Author: Chirag Agarwal
 */

package search;


public class ChildInfo {
private String State, parentState;
private int Misplace;
private int height;
private String Direction;

	ChildInfo()
	{
		State = "";
		parentState = "";
		Misplace = 0;
		height = -1;
	}
	
	public void setState(String newState)
	{
		State = newState;
	}
	public String getState()
	{
		return State;
	}
	public void setParentState(String newParent)
	{
		parentState = newParent;
	}
	public String getParentState()
	{
		return parentState;
	}
	public void setMisplace(int newMisplace)
	{
		Misplace = newMisplace;
	}
	public int getMisplace()
	{
		return Misplace;
	}
	public void setHeight(int newHeight)
	{
		height = newHeight; 
	}
	public int getHeight()
	{
		return height;
	}
	public void setDirection(String newDirection)
	{
		Direction = newDirection;
	}
	public String getDirection()
	{
		return Direction;
	}
	
}

