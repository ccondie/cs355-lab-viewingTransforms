package src.control;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import src.model.Model;
import cs355.GUIFunctions;
import cs355.JsonShape;
import cs355.controller.CS355Controller;
import cs355.model.drawing.*;


public class Control implements CS355Controller
{
	private static final Gson gson = new GsonBuilder().setPrettyPrinting()
			.registerTypeAdapter(Shape.class, new JsonShape()).create();
	
	boolean activeShape = false;
	ArrayList<Point2D.Double> activeTriangle = new ArrayList<Point2D.Double>();
	
	Point2D.Double clickStart;
	
	public boolean dragging;
	public boolean rotating;
	public boolean lineSelected;
	
	
	public boolean currentSel;

	@Override
	public void mouseClicked(MouseEvent arg0) 
	{	}

	@Override
	public void mouseEntered(MouseEvent arg0) 
	{	}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		clickStart = new Point2D.Double(arg0.getX(), arg0.getY());
		
		// TODO Auto-generated method stub
		System.out.println("Mouse Pressed: x-" + arg0.getX() + " y-" + arg0.getY());
		if(Model.get().currentMode <= 5)
		{
			if(activeShape)
			{
				if(Model.get().getRecent() instanceof Line)
				{	activeShape = false;	}
				
				if(Model.get().getRecent() instanceof Square)
				{	activeShape = false;	}
				
				if(Model.get().getRecent() instanceof Rectangle)
				{	activeShape = false;	}
				
				if(Model.get().getRecent() instanceof Circle)
				{	activeShape = false;	}
				
				if(Model.get().getRecent() instanceof Ellipse)
				{	activeShape = false;	}
				
				if(Model.get().getRecent() instanceof Triangle)
				{	
					if(activeTriangle.size() == 1)
					{
						//add second corner
						System.out.println("ADDING SECOND CORNER");
						activeTriangle.add(new Point2D.Double(arg0.getX(), arg0.getY()));
					}
					else if(activeTriangle.size() == 2)
					{
						//add third corner
						System.out.println("ADDING THIRD CORNER");
						activeTriangle.add(new Point2D.Double(arg0.getX(), arg0.getY()));
						//calculate center
						Point2D.Double center = new Point2D.Double();
						center.x = (activeTriangle.get(0).x + activeTriangle.get(1).x + activeTriangle.get(2).x) / 3;
						center.y = (activeTriangle.get(0).y + activeTriangle.get(1).y + activeTriangle.get(2).y) / 3;
						
						System.out.println("center.x:" + center.x + " - center.y:" + center.y);
						
						//calculate a,b,c
						Point2D.Double a = new Point2D.Double(activeTriangle.get(0).x - center.x, activeTriangle.get(0).y - center.y);
						Point2D.Double b = new Point2D.Double(activeTriangle.get(1).x - center.x, activeTriangle.get(1).y - center.y);
						Point2D.Double c = new Point2D.Double(activeTriangle.get(2).x - center.x, activeTriangle.get(2).y - center.y);
						
						System.out.println("a.x:" + a.x + " - a.y:" + a.y);
						System.out.println("b.x:" + b.x + " - b.y:" + b.y);
						System.out.println("c.x:" + c.x + " - c.y:" + c.y);
						
						//create Triangle
						Triangle focus = new Triangle(Model.selectedColor, center, a, b, c);
						Model.get().setRecent(focus);
						System.out.println(focus.toString());
						
						//reset activeTriangle
						activeTriangle.clear();
						
						//refresh GUI
						//GUIFunctions.refresh();
						Model.get().notifyObservers();
						activeShape = false;
					}
				}
			}
			else
			{
				switch(Model.get().currentMode)
				{
				case 0:
					Model.get().addShape(new Line(Model.selectedColor, new Point2D.Double(arg0.getX(), arg0.getY()), new Point2D.Double(0, 0)));
					activeShape = true;
					break;
				case 1: 
					Model.get().addShape(new Square(Model.selectedColor, new Point2D.Double(arg0.getX(), arg0.getY()), 0));
					activeShape = true;
					break;
				case 2: 
					Model.get().addShape(new Rectangle(Model.selectedColor, new Point2D.Double(arg0.getX(), arg0.getY()),0, 0));
					activeShape = true;
					break;
				case 3: 
					Model.get().addShape(new Circle(Model.selectedColor, new Point2D.Double(arg0.getX(), arg0.getY()),0));
					activeShape = true;
					break;
				case 4: 
					Model.get().addShape(new Ellipse(Model.selectedColor, new Point2D.Double(arg0.getX(), arg0.getY()),0, 0));
					activeShape = true;
					break;
				case 5: 
					Model.get().addShape(new Triangle(Model.selectedColor, null, null, null, null));
					System.out.println("ADDING FIRST CORNER");
					activeTriangle.add(new Point2D.Double(arg0.getX(), arg0.getY()));
					activeShape = true;
					break;
				}
			}
		}
		
		if(Model.get().currentMode == 6)
		{
			//check if there is an active shape
				//if there is then check to see if the handle was hit
			boolean handleSelected = false;
			if(currentSel)
			{
				handleSelected = Model.get().getActive().pointInHandle(clickStart);
				if(handleSelected == true)
				{
					rotating = true;
				}
			}

			if(!rotating)
			{
				//if there isn't an active shape, loop for hit selection
				System.out.println("RUNNING HIT SELECTION ON SHAPES");
				lineSelected = false;
				//Handle selection
				boolean runLoop = true;
				boolean shapeSelected = false;
				int count = Model.get().getShapes().size() - 1;
				
				
				
				//defaults all the shapes to NOT active
				Model.get().clearActiveShapes();
				
				//Loop through all the shapes
				while(runLoop && (count >= 0 ))
				{		
					shapeSelected = Model.get().getShape(count).pointInShape(clickStart, 5);
					if(shapeSelected == true)
					{
						if(Model.get().getShape(count) instanceof Line)
							lineSelected = true;
						
						currentSel = true;
						dragging = true;
						runLoop = false;
						Model.activeShape = count;
					}
					count--;
				}
			}
			
			//GUIFunctions.refresh();
			Model.get().notifyObservers();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{			
		dragging = false;
		rotating = false;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) 
	{	
		if(dragging)
		{
			System.out.print("***DRAGGING***");
			System.out.println(" rotating:" + rotating + " - dragging:" + dragging);
			
			double deltaX = clickStart.x - arg0.getX();
			double deltaY = clickStart.y - arg0.getY();
			
			Model.get().getActive().getCenter().x = Model.get().getActive().getCenter().x - deltaX;
			Model.get().getActive().getCenter().y = Model.get().getActive().getCenter().y - deltaY;
			
			if(Model.get().getActive() instanceof Line)
			{
				((Line)Model.get().getActive()).getEndWorld().x = ((Line)Model.get().getActive()).getEndWorld().x - deltaX;
				((Line)Model.get().getActive()).getEndWorld().y = ((Line)Model.get().getActive()).getEndWorld().y - deltaY;
			}
			
			clickStart.x = arg0.getX();
			clickStart.y = arg0.getY();
		}
		
		if(rotating && !lineSelected)
		{
			Point2D.Double clickStartObj = Model.get().getActive().worldToObj(clickStart);
			Point2D.Double arg0Obj = Model.get().getActive().worldToObj(new Point2D.Double(arg0.getX(), arg0.getY()));
			double startT = Model.get().getActive().getRotation();
			
			double vX = clickStartObj.x;
			double vY = clickStartObj.y;
			double wX = arg0Obj.getX();
			double wY = arg0Obj.getY();
			
			boolean counter = false;
			if(arg0Obj.getX() < clickStartObj.getX())
				counter = true;
			
			double vm;
			double magV;
			double magW;
			
			vm = (vX * wX) + (vY * wY);
			
			magV = Math.sqrt(Math.pow(vX, 2) + Math.pow(vY, 2));
			magW = Math.sqrt(Math.pow(wX, 2) + Math.pow(wY, 2));
			
			double deltaT = Math.acos((vm)/(magV * magW));
			
			if(counter)
				Model.get().getActive().setRotation(startT - deltaT);
			else
				Model.get().getActive().setRotation(startT + deltaT);
			
			
			
			clickStart.x = arg0.getX();
			clickStart.y = arg0.getY();
			
			System.out.println("deltaT:" + deltaT);
		}
		
		if(rotating && lineSelected)
		{
			System.out.println("BALLS BALLS BALSS");
			//handle line endpoint movement
			double deltaX = clickStart.x - arg0.getX();
			double deltaY = clickStart.y - arg0.getY();
			
			((Line)Model.get().getActive()).getEndWorld().x = ((Line)Model.get().getActive()).getEndWorld().x - deltaX;
			((Line)Model.get().getActive()).getEndWorld().y = ((Line)Model.get().getActive()).getEndWorld().y - deltaY;
			
			clickStart.x = arg0.getX();
			clickStart.y = arg0.getY();
			
		}
		
		//GUIFunctions.refresh();
		Model.get().notifyObservers();
	}
	
	
	
	
	
	

	@Override
	public void mouseMoved(MouseEvent arg0) 
	{	
//		System.out.println("Mouse Moved: x-" + arg0.getX() + " y-" + arg0.getY());
		
		if(activeShape)
		{	
			Shape focus = Model.get().getRecent();
			
			if(focus instanceof Line)
			{	handleActiveLine(arg0);	}
			
			if(focus instanceof Square)
			{	handleActiveSquare(arg0);	}
			
			if(focus instanceof Rectangle)
			{	handleActiveRectangle(arg0);	}
			
			
			if(focus instanceof Circle)
			{	handleActiveCircle(arg0);	}
			
			
			if(focus instanceof Ellipse)
			{	handleActiveEllipse(arg0);	}
			
			
			if(focus instanceof Triangle)
			{	}
		
			//GUIFunctions.refresh();
			Model.get().notifyObservers();
		}
	}
	
	
	public void handleActiveSquare(MouseEvent arg0)
	{
//		System.out.println("instance: Square");
		Square active = (Square) Model.get().getRecent();
		
		Point2D.Double fixed = active.getFixedCorner();
		
		//find the shortest size;
		double sizeX = fixed.x - arg0.getX();
		double sizeY = fixed.y - arg0.getY();
		double newSize = Math.min(Math.abs(sizeX), Math.abs(sizeY));
		active.setSize(newSize);
		
		System.out.println("sizeX: " + sizeX + " - sizeY: " + sizeY + " - newSize: " + newSize);
		
		
		//set the new center
		double newX;
		double newY;
		
		if(sizeX >= 0)
			newX = fixed.x - (newSize / 2);
		else
			newX = fixed.x + (newSize / 2);
		
		
		if(sizeY >= 0)
			newY = fixed.y - (newSize / 2);
		else
			newY = fixed.y + (newSize / 2);
		
		Point2D.Double newCenter = new Point2D.Double(newX, newY);
		active.setCenter(newCenter);
		
		Model.get().setRecent(active);
	}
	
	public void handleActiveCircle(MouseEvent arg0)
	{
		
		Circle active = (Circle) Model.get().getRecent();
		
		Point2D.Double fixed = active.getFixedCorner();
		
		//find the shortest size;
		double sizeX = fixed.x - arg0.getX();
		double sizeY = fixed.y - arg0.getY();
		double newSize = Math.min(Math.abs(sizeX), Math.abs(sizeY));
		active.setRadius(newSize / 2);
		
		//set the new center
		double newX;
		double newY;
		
		if(sizeX >= 0)
			newX = fixed.x - (newSize / 2);
		else
			newX = fixed.x + (newSize / 2);
		
		
		if(sizeY >= 0)
			newY = fixed.y - (newSize / 2);
		else
			newY = fixed.y + (newSize / 2);
		
		Point2D.Double newCenter = new Point2D.Double(newX, newY);
		active.setCenter(newCenter);
		
		Model.get().setRecent(active);
	}

	public void handleActiveLine(MouseEvent arg0)
	{
		Line focus = (Line) Model.get().getRecent();
		focus.setEnd(new Point2D.Double(arg0.getX(), arg0.getY()));
		
		Model.get().setRecent(focus);
	}
	
	public void handleActiveRectangle(MouseEvent arg0)
	{
		Rectangle focusRect = (Rectangle) Model.get().getRecent();
		
		Point2D.Double fixed = focusRect.getFixedCorner();
		
		//set the new center
		double newX = fixed.x - ((fixed.x - arg0.getX()) / 2);
		double newY = fixed.y - ((fixed.y - arg0.getY()) / 2);
		
		Point2D.Double newCenter = new Point2D.Double(newX, newY);
		focusRect.setCenter(newCenter);
		
		//set width and height
		focusRect.setWidth(Math.abs(fixed.x - arg0.getX()));
		focusRect.setHeight(Math.abs(fixed.y - arg0.getY()));
		
		Model
		.get().setRecent(focusRect);
	}
	
	public void handleActiveEllipse(MouseEvent arg0)
	{	
		Ellipse active = (Ellipse) Model.get().getRecent();
		
		Point2D.Double fixed = active.getFixedCorner();
		
		//set the new center
		double newX = fixed.x - ((fixed.x - arg0.getX()) / 2);
		double newY = fixed.y - ((fixed.y - arg0.getY()) / 2);
		
		Point2D.Double newCenter = new Point2D.Double(newX, newY);
		active.setCenter(newCenter);
		
		//set width and height
		active.setWidth(Math.abs(fixed.x - arg0.getX()));
		active.setHeight(Math.abs(fixed.y - arg0.getY()));
		
		Model.get().setRecent(active);
	}
	
	
	@Override
	public void colorButtonHit(Color c) 
	{	
		System.out.println("Color: r-" + c.getRed() + " g-" + c.getGreen() + " b-" + c.getBlue());
		
		if(Model.activeShape >= 0)
			Model.get().getShape(Model.activeShape).setColor(c);
		
		Model.get().setColor(c);
		GUIFunctions.changeSelectedColor(c);
	}

	@Override
	public void lineButtonHit() 
	{	
		Model.get().setMode(0);
		Model.get().clearActiveShapes();
	}

	@Override
	public void squareButtonHit() 
	{	
		Model.get().setMode(1);
		Model.get().clearActiveShapes();
	}
	

	@Override
	public void rectangleButtonHit() 
	{	
		Model.get().setMode(2);
		Model.get().clearActiveShapes();
	}

	@Override
	public void circleButtonHit() 
	{	
		Model.get().setMode(3);
		Model.get().clearActiveShapes();
	}

	@Override
	public void ellipseButtonHit() 
	{	
		Model.get().setMode(4);
		Model.get().clearActiveShapes();
	}

	@Override
	public void triangleButtonHit() 
	{	
		Model.get().setMode(5);	
		Model.get().clearActiveShapes();
	}

	@Override
	public void selectButtonHit() 
	{	
		Model.get().setMode(6);	
		Model.get().clearActiveShapes();
	}
	
	@Override
	public void zoomInButtonHit() 
	{	
		System.out.println("Zoom in Pressed");
		Model.incScale();
	}

	@Override
	public void zoomOutButtonHit() 
	{	
		System.out.println("Zoom out pressed");
		Model.decScale();
	}

	@Override
	public void hScrollbarChanged(int value) 
	{	
		System.out.println("hScrollbar Changed");
	}

	@Override
	public void vScrollbarChanged(int value) 
	{	
		System.out.println("vScrollbar Changed");
	}

	@Override
	public void openScene(File file) 
	{	}

	@Override
	public void toggle3DModelDisplay() 
	{	}

	@Override
	public void keyPressed(Iterator<Integer> iterator) 
	{	}

	@Override
	public void openImage(File file) 
	{	}

	@Override
	public void saveImage(File file) 
	{	}

	@Override
	public void toggleBackgroundDisplay() 
	{	}

	@Override
	public void saveDrawing(File file) 
	{	
		try (FileOutputStream fos = new FileOutputStream(file + ".json")) 
		{
			// Get the list from the concrete class.
			List<Shape> data = Model.get().getShapes();
	
			// Convert the List to an array.
			Shape[] shapes = new Shape[data.size()];
			data.toArray(shapes);
	
			// Convert to JSON text and write it out.
			String json = gson.toJson(shapes, Shape[].class);
			fos.write(json.getBytes());
		}
		catch (IOException ex) 
		{
		Logger.getLogger(CS355Drawing.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void openDrawing(File file) 
	{	
		// Make a blank list.
		List<Shape> shapes = null;
		
		try 
		{
			// Read the entire file in. I hate partial I/O.
			byte[] b = Files.readAllBytes(file.toPath());
			// Validation.
			if (b == null) 
			{
				throw new IOException("Unable to read drawing");
			}

			// Convert it to text.System.out.println("A");
			String data = new String(b, StandardCharsets.UTF_8);
			// Use Gson to convert the text to a list of Shapes.
			Shape[] list = gson.fromJson(data, Shape[].class);
			shapes = new ArrayList<>(Arrays.asList(list));
		}
		catch (IOException | JsonSyntaxException ex) 
		{
			Logger.getLogger(CS355Drawing.class.getName()).log(Level.SEVERE, null, ex);
		}

		// Set the new shape list.
		Model.get().setShapes(shapes);
		//GUIFunctions.refresh();
		Model.get().notifyObservers();
	}

	@Override
	public void doDeleteShape() 
	{	
		System.out.println("doDeleteShape()");
		currentSel = false;
		Model.get().deleteActive();
	}

	@Override
	public void doEdgeDetection() 
	{	}

	@Override
	public void doSharpen() 
	{	}

	@Override
	public void doMedianBlur() 
	{	}

	@Override
	public void doUniformBlur() 
	{	}
	
	@Override
	public void doGrayscale() 
	{	}

	@Override
	public void doChangeContrast(int contrastAmountNum) 
	{	}

	@Override
	public void doChangeBrightness(int brightnessAmountNum) 
	{	}

	@Override
	public void doMoveForward() 
	{	
		System.out.println("doMoveForward()");
		Model.get().promoteShape();
	}

	@Override
	public void doMoveBackward() 
	{	
		System.out.println("doMoveBackward()");
		Model.get().demoteShape();
	}

	@Override
	public void doSendToFront() 
	{	
		System.out.println("doSendToFront()");
		Model.get().makeKing();
	}

	@Override
	public void doSendtoBack() 
	{	
		System.out.println("doSendtoBack()");
		Model.get().makePawn();
	}

}
