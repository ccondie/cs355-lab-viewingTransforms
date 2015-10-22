package cs355.solution;

import src.control.Control;
import src.model.Model;
import src.view.View;
import cs355.GUIFunctions;

/**
 * This is the main class. The program starts here.
 * Make you add code below to initialize your model,
 * view, and controller and give them to the app.
 */
public class CS355 {

	/**
	 * This is where it starts.
	 * @param args = the command line arguments
	 */
	public static void main(String[] args) 
	{
		Control controller = new Control();
		View view = new View();
		Model.get().addObserver(view);

		// Fill in the parameters below with your controller and view.
		GUIFunctions.createCS355Frame(controller, view);

		GUIFunctions.refresh();
	}
}
