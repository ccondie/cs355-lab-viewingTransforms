package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.StringWriter;

import src.model.Model;
import cs355.GUIFunctions;

/**
 * Add your triangle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Triangle extends Shape 
{
	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param a the first point, relative to the center.
	 * @param b the second point, relative to the center.
	 * @param c the third point, relative to the center.
	 */
	public Triangle(Color color, Point2D.Double center, Point2D.Double a,
					Point2D.Double b, Point2D.Double c)
	{

		// Initialize the superclass.
		super(color, center);

		// Set fields.
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	///////////////////////////////////////////////////////////
	/////	User Defined Variables	///////////////////////////
	///////////////////////////////////////////////////////////
	
	// The three points of the triangle.
	private Point2D.Double a;
	private Point2D.Double b;
	private Point2D.Double c;

		
	///////////////////////////////////////////////////////////
	/////	Abstracted/Program defined Variables	///////////
	///////////////////////////////////////////////////////////
	

	
	///////////////////////////////////////////////////////////
	/////	User Defined Methods	///////////////////////////
	///////////////////////////////////////////////////////////
	
	public String toString()
	{
		StringWriter sw = new StringWriter();
		
		sw.append("PointA - x:" + a.x + " - y:" + a.y + '\n');
		sw.append("PointB - x:" + b.x + " - y:" + b.y + '\n');
		sw.append("PointC - x:" + c.x + " - y:" + c.y + '\n');
		sw.append("Center - x:" + center.x + " - y:" + center.y + '\n');
		
		return sw.toString();
	}
	
	public Point2D.Double getHandle()
	{	
		double smallestY = a.y;
		
		if(smallestY > b.y)
			smallestY = b.y;
		
		if(smallestY > c.y)
			smallestY = c.y;
		
		Point2D.Double handle = new Point2D.Double(0, smallestY - 15);
	
		
		return handle;
	}


		
	///////////////////////////////////////////////////////////
	/////	Abstracted/Program defined Methods	///////////////
	///////////////////////////////////////////////////////////
	
	/**
	 * Getter for the first point.
	 * @return the first point as a Java point.
	 */
	public Point2D.Double getA() {
		return a;
	}

	/**
	 * Setter for the first point.
	 * @param a the new first point.
	 */
	public void setA(Point2D.Double a)
	{
		this.a = a;
	}

	/**
	 * Getter for the second point.
	 * @return the second point as a Java point.
	 */
	public Point2D.Double getB() {
		return b;
	}

	/**
	 * Setter for the second point.
	 * @param b the new second point.
	 */
	public void setB(Point2D.Double b) {
		this.b = b;
	}

	/**
	 * Getter for the third point.
	 * @return the third point as a Java point.
	 */
	public Point2D.Double getC() {
		return c;
	}

	/**
	 * Setter for the third point.
	 * @param c the new third point.
	 */
	public void setC(Point2D.Double c) {
		this.c = c;
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
		System.out.println("Checking point in TRIANGLE");
		Point2D.Double ptObj = worldToObj(pt);
		boolean b1, b2, b3;

	    b1 = sign(ptObj, a, b) < 0;
	    b2 = sign(ptObj, b, c) < 0;
	    b3 = sign(ptObj, c, a) < 0;
	    
	    if((b1 == b2) && (b2 == b3))
	    {
	    	System.out.println("HIT INSIDE A Triangle");
			GUIFunctions.changeSelectedColor(color);
			Model.get().setColor(color);
			setActive(true);
			
			return true;
	    }
	    
	    return false;
	}
	
	public double sign (Point2D.Double p1, Point2D.Double p2, Point2D.Double p3)
	{	return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);	}

}
