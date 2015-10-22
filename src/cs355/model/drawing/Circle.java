package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import src.model.Model;
import cs355.GUIFunctions;

/**
 * Add your circle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Circle extends Shape 
{
	
	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param radius the radius of the new shape.
	 */
	public Circle(Color color, Point2D.Double center, double radius) {

		// Initialize the superclass.
		super(color, center);

		// Set the field.
		this.radius = radius;
		this.fixedCorner = center;
	}
	
	///////////////////////////////////////////////////////////
	/////	User Defined Variables	///////////////////////////
	///////////////////////////////////////////////////////////
	
	private Point2D.Double fixedCorner;
	
	///////////////////////////////////////////////////////////
	/////	Abstracted/Program defined Variables	///////////
	///////////////////////////////////////////////////////////

	// The radius.
	private double radius;

	///////////////////////////////////////////////////////////
	/////	User Defined Methods	///////////////////////////
	///////////////////////////////////////////////////////////
	
	//returns the 2D coor of the upper right corner for the sake of rendering
	public Point2D.Double getUpperLeft()
	{
		Point2D.Double returnThis = new Point2D.Double();
		
		double x = center.x - (radius);
		double y = center.y - (radius);
		
		returnThis.x = x;
		returnThis.y = y;
		
		return returnThis;
	}
	
	public Point2D.Double getFixedCorner()
	{	return fixedCorner;	}
			
	public void setFixedCorner(Point2D.Double point)
	{	 fixedCorner = point;	}
	
	public Point2D.Double getHandle()
	{	
		Point2D.Double handle = new Point2D.Double();
	
		handle.x = 0;
		handle.y = -(radius) - 15;
		
		return handle;	
	}
	
	///////////////////////////////////////////////////////////
	/////	Abstracted/Program defined Methods	///////////////
	///////////////////////////////////////////////////////////
	

	/**
	 * Getter for this Circle's radius.
	 * @return the radius of this Circle as a double.
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Setter for this Circle's radius.
	 * @param radius the new radius of this Circle.
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * Add your code to do an intersection test
	 * here. You shouldn't need the tolerance.
	 * @param pt = the point to test against.
	 * @param tolerance = the allowable tolerance.
	 * @return true if pt is in the shape,
	 *		   false otherwise.
	 */
	@Override
	public boolean pointInShape(Point2D.Double pt, double tolerance) 
	{
		Point2D.Double ptObj = worldToObj(pt);
		
		double mag = Math.sqrt(Math.pow(ptObj.x, 2) + Math.pow(ptObj.y, 2));
		if(mag <= radius)
		{
			System.out.println("HIT INSIDE A Circle");
			GUIFunctions.changeSelectedColor(color);
			Model.get().setColor(color);
			setActive(true);
			
			return true;
		}
		else
			return false;
	}

}
