package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

/**
 * This is the base class for all of your shapes.
 * Make sure they all extend this class.
 */
public abstract class Shape 
{
	
	/**
	 * Basic constructor that sets fields.
	 * It initializes rotation to 0.
	 * @param color the color for the new shape.
	 * @param center the center point of the new shape.
	 */
	public Shape(Color color, Point2D.Double center) 
	{
		this.color = color;
		this.center = center;
		rotation = 0.0;
	}
	
	///////////////////////////////////////////////////////////
	/////	User Defined Variables	///////////////////////////
	///////////////////////////////////////////////////////////
	
	//flag a shape as active
	protected boolean active;
	
	
	///////////////////////////////////////////////////////////
	/////	Abstracted/Program defined Variables	///////////
	///////////////////////////////////////////////////////////

	// The color of this shape.
	protected Color color;

	// The center of this shape.
	protected Point2D.Double center;

	// The rotation of this shape.
	protected double rotation;
		
		

	
	///////////////////////////////////////////////////////////
	/////	User Defined Methods	///////////////////////////
	///////////////////////////////////////////////////////////
	
	public boolean getActive()
	{	return active;	}
	
	public void setActive(boolean state)
	{	active = state;	}
	
	public abstract Point2D.Double getHandle();
	
	public Point2D.Double worldToObj(Point2D.Double world)
	{
		AffineTransform worldToObj = new AffineTransform();
		Point2D.Double obj = new Point2D.Double();
		
		worldToObj.rotate(-rotation);
		worldToObj.translate(-center.x, -center.y);
		
		worldToObj.transform(world, obj);
		
		return obj;
	}
	
	///////////////////////////////////////////////////////////
	/////	Abstracted/Program defined Methods	///////////////
	///////////////////////////////////////////////////////////
	/**
	 * Getter for this shape's color.
	 * @return the color of this shape.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for this shape's color
	 * @param color the new color for the shape.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Getter for this shape's center.
	 * @return this shape's center as a Java point.
	 */
	public Point2D.Double getCenter() {
		return center;
	}

	/**
	 * Setter for this shape's center.
	 * @param center the new center as a Java point.
	 */
	public void setCenter(Point2D.Double center) {
		this.center = center;
	}

	/**
	 * Getter for this shape's rotation.
	 * @return the rotation as a double.
	 */
	public double getRotation() {
		return rotation;
	}

	/**
	 * Setter for this shape's rotation.
	 * @param rotation the new rotation.
	 */
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	/**
	 * Used to test for whether the user clicked inside a shape or not.
	 * @param pt = the point to test whether it's in the shape or not.
	 * @param tolerance = the tolerance for testing. Mostly used for lines.
	 * @return true if pt is in the shape, false otherwise.
	 */
	public abstract boolean pointInShape(Point2D.Double pt, double tolerance);
	
	public boolean pointInHandle(Double pt) 
	{	
		Point2D.Double objPt = worldToObj(pt);
		
		double diff = Math.pow((objPt.x - getHandle().x), 2) + Math.pow((objPt.y - getHandle().y), 2);
		
		if(diff < Math.pow(8, 2))
		{
			System.out.println("TRUE");
			return true;
		}
		else
		{
			System.out.println("FALSE");
			return false;
		}
			
	}
}
