package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import src.model.Model;
import cs355.GUIFunctions;

/**
 * Add your square code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Square extends Shape 
{
	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param size the size of the new shape.
	 */
	public Square(Color color, Point2D.Double center, double size) 
	{
		// Initialize the superclass.
		super(color, center);

		// Set the field.
		this.size = size;
		this.fixedCorner = center;
	}
	
	///////////////////////////////////////////////////////////
	/////	User Defined Variables	///////////////////////////
	///////////////////////////////////////////////////////////
	
	// The upper left corner of this shape.
	//These will be generated from the center point, size, and rotation?
	private Point2D.Double fixedCorner;
	
	
	
	///////////////////////////////////////////////////////////
	/////	Abstracted/Program defined Variables	///////////
	///////////////////////////////////////////////////////////

	// The size of this Square.
		private double size;
	
		
		
	///////////////////////////////////////////////////////////
	/////	User Defined Methods	///////////////////////////
	///////////////////////////////////////////////////////////
	
	//returns the 2D coor of the upper right corner for the sake of rendering
	public Point2D.Double getUpperLeft()
	{
		Point2D.Double returnThis = new Point2D.Double();
		
		double x = center.x - (size/2);
		double y = center.y - (size/2);
		
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
		handle.y = -(size * 1)/2 - 15;
		
		return handle;
	}
	
	
	///////////////////////////////////////////////////////////
	/////	Abstracted/Program defined Methods	///////////////
	///////////////////////////////////////////////////////////
	
	/**
	 * Getter for this Square's size.
	 * @return the size as a double.
	 */
	public double getSize() 
	{	return size;	}

	/**
	 * Setter for this Square's size.
	 * @param size the new size.
	 */
	public void setSize(double size) 
	{	this.size = size;	}

	
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
//		throw new UnsupportedOperationException("Not supported yet.");
		if(ptObj.x < (0 - (size / 2)))
			return false;
		if(ptObj.x > (0 + (size / 2)))
			return false;
		if(ptObj.y < (0 - (size / 2)))
			return false;
		if(ptObj.y > (0 + (size / 2)))
			return false;
		
		System.out.println("HIT INSIDE A SQUARE");
		GUIFunctions.changeSelectedColor(color);
		Model.get().setColor(color);
		setActive(true);
		return true;
	}


	

}
