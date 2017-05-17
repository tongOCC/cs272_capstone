package edu.orangecoastcollege.cs272.p04.view;


import java.io.File;

import edu.orangecoastcollege.cs272.p04.controller.GSController;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
/**
 * David Guido
 * Teven Ong
 * CS 272 ~ Java II
*
 * P04_Battleship1942
 * @version May 1, 2017
* 
 * @author Dguido1
 * @author 
* 
 * ~ Source of Game Research: "http://stackoverflow.com/questions/555283/how-can-you-organize-the-code-for-a-game-to-fit-the-mvc-pattern"
 ***
 *
 * ~ This <code>ViewGameFX</code> Class, the 'View' layer is the UI dependent layer. 
 * ~ It reflects the specific choice of UI you went with 
 *   and will be very much dedicated to that technology. 
 * ~ It might be responsible for reading the state of the model and 
 *   drawing it in 3D, ASCII, or images and HTML for a web page. 
 * ~ It is also responsible for displaying any control mechanisms the player needs to use to interact with the game.
*/
public class ViewGameFX extends Application 
{

	private static GSController GSC = GSController.getInstance();


	@Override
	public void start(Stage mainStage) throws Exception 
	{
		 
		
		 
		ViewNavigator.setStage(mainStage);
		GSC.setMainStage(mainStage);
		
		ViewNavigator.loadScene("Welcome to VG BATTLESHIPS 1942", ViewNavigator.LOG_IN_SCENE);
		
		
		//GSC.setMainScene(GSC.startScreen());	
		//GSC.getMainStage().setScene(GSC.getMainScene());
		//GSC.getMainStage().setResizable(false);
		
		//GSC.getMainStage().setTitle("BS42");
		//GSC.getMainStage().show();
		
		
	}
	public static void main(String[] args) 
	{
		launch(args);
	}						
}