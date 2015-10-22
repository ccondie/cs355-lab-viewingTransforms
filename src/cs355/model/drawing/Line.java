package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import src.model.Model;
import cs355.GUIFunctions;

/**
 * Add your line code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Line extends Shape 
{
	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param start the starting point.
	 * @param end the ending point.
	 */
	public Line(Color color, Point2D.Double start, Point2D.Double end) {

		// Initialize the superclass.
		super(color, start);

		// Set the field.
		this.end = end;
	}
	
	///////////////////////////////////////////////////////////
	/////	User Defined Variables	///////////////////////////
	///////////////////////////////////////////////////////////
	
	
	
	
	///////////////////////////////////////////////////////////
	/////	Abstracted/Program defined Variables	///////////
	///////////////////////////////////////////////////////////
	
	// The ending point of the line.
	private Point2D.Double end;
	
	
	///////////////////////////////////////////////////////////
	/////	User Defined Methods	///////////////////////////
	///////////////////////////////////////////////////////////
	
	public Point2D.Double getHandle()
	{	
		return new Point2D.Double(0,0);	
	}

	
	///////////////////////////////////////////////////////////
	/////	Abstracted/Program defined Methods	///////////////
	///////////////////////////////////////////////////////////
	
	/**
	 * Getter for this Line's ending point.
	 * @return the ending point as a Java point.
	 */
	public Point2D.Double getEnd() {
		return worldToObj(end);
	}
	
	public Point2D.Double getEndWorld()
	{
		return end;
	}

	/**
	 * Setter for this Line's ending point.
	 * @param end the new ending point for the Line.
	 */
	public void setEnd(Point2D.Double end) 
	{
		this.end = end;
	}

	/**
	 * Add your code to do an intersection test
	 * here. You <i>will</i> need the tolerance.
	 * @param pt = the point to test against.
	 * @param tolerance = the allowable tolerance.
	 * @return true if pt is in the shape,
	 *		   false otherwise.
	 */
	@Override
	public boolean pointInShape(Point2D.Double pt, double tolerance) 
	{
		Point2D.Double objPt = worldToObj(pt);
		System.out.println("CHECKING FOR HIT ISIDE A LINE");
		
		double top = Math.abs((getEnd().y)*objPt.x - (getEnd().x)*objPt.y);
		double bottom = Math.sqrt(Math.pow(getEnd().y, 2) + Math.pow(getEnd().x,2));
		
		double distance = top/bottom;
		
		System.out.println("Distance:" + distance);
		
		if((distance) <= tolerance)
		{
			System.out.println("HIT INSIDE A Line");
			GUIFunctions.changeSelectedColor(color);
			Model.get().setColor(color);
			setActive(true);
			
			return true;
		}
		return false;
	}


	@Override
	public boolean pointInHandle(Double pt) 
	{
		pt = worldToObj(pt);
		
		double diff = Math.pow((pt.x - 0), 2) + Math.pow((pt.y - 0), 2);
		if(diff < Math.pow(8, 2))
		{
			Point2D.Double endObj = worldToObj(end);
			//if TRUE: set end to center and center to end
			System.out.println("LINE: hit the CENTER");	
			
			System.out.println("Center Start - x:" + center.x + " - y:" + center.y);
			System.out.println("End Start - x:" + end.x + " - y:" + end.y);
			
			Point2D.Double endWorld = end;
			end = getCenter();
			setCenter(endWorld);

			
			System.out.println("Center End - x:" + center.x + " - y:" + center.y);
			System.out.println("End End - x:" + end.x + " - y:" + end.y);
			return true;
		}
		
		diff = Math.pow((pt.x - getEnd().x), 2) + Math.pow((pt.y - getEnd().y), 2);
		if(diff < Math.pow(8, 2))
		{
			//check if the pt falls in the end handle
			System.out.println("LINE: hit the END");
			return true;
		}
		
		return false;
	}

}
