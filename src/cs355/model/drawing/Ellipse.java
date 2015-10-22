package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import src.model.Model;
import cs355.GUIFunctions;

/**
 * Add your ellipse code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Ellipse extends Shape 
{
	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param width the width of the new shape.
	 * @param height the height of the new shape.
	 */
	public Ellipse(Color color, Point2D.Double center, double width, double height) 
	{

		// Initialize the superclass.
		super(color, center);

		// Set fields.
		this.width = width;
		this.height = height;
		this.fixedCorner = center;
	}
	

	///////////////////////////////////////////////////////////
	/////	User Defined Variables	///////////////////////////
	///////////////////////////////////////////////////////////
	
	private Point2D.Double fixedCorner;
	
	
	///////////////////////////////////////////////////////////
	/////	Abstracted/Program defined Variables	///////////
	///////////////////////////////////////////////////////////
	
	// The width of this shape.
	private double width;

	// The height of this shape.
	private double height;

	
	
	///////////////////////////////////////////////////////////
	/////	User Defined Methods	///////////////////////////
	///////////////////////////////////////////////////////////

	//returns the 2D coor of the upper right corner for the sake of rendering
	public Point2D.Double getUpperLeft()
	{
		Point2D.Double returnThis = new Point2D.Double();
		
		double x = center.x - (width/2);
		double y = center.y - (height/2);
		
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
		handle.y = -(height/2) - 15;
		
		return handle;
	}
	
	
	///////////////////////////////////////////////////////////
	/////	Abstracted/Program defined Methods	///////////////
	///////////////////////////////////////////////////////////
	
	/**
	 * Getter for this shape's width.
	 * @return this shape's width as a double.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Setter for this shape's width.
	 * @param width the new width.
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * Getter for this shape's height.
	 * @return this shape's height as a double.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Setter for this shape's height.
	 * @param height the new height.
	 */
	public void setHeight(double height) {
		this.height = height;
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
		
		if((Math.pow(ptObj.x / (width / 2), 2)) + (Math.pow(ptObj.y / (height / 2), 2)) <= 1)
		{
			System.out.println("HIT INSIDE A Ellipse");
			GUIFunctions.changeSelectedColor(color);
			Model.get().setColor(color);
			setActive(true);
			
			return true;
		}
		
		return false;

	}


}
