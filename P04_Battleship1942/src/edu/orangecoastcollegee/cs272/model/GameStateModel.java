package edu.orangecoastcollegee.cs272.model;
import java.util.Random;

import edu.orangecoastcollege.cs272.p04.controller.GSController;
import edu.orangecoastcollege.cs272.p04.view.ViewNavigator;
import edu.orangecoastcollegee.cs272.model.Grid.Unit;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
 * ~ This <code>GameStateModel</code> Class, The 'Model' layer can be though of as a kind of game API. 
 * ~ What would your game be reduced to if there were no UI at all for the game ordained from the beginning? 
* 
 * ~> (example): 
 * ~ You mention that what you have in mind is an RPG, so in this case you can imagine having the player character, 
 *   his/her inventory, spells, abilities, NPCs, and even things like the map and combat rules all being part of the model. 
 *   It is like the rules and pieces of Monopoly without the specifics of how the final game displays that or how the user 
 *   is going to interact with it. It is like Quake as an abstract set of 3D objects moving through a level with things 
 *   like intersection and collision calculated but no rendering, shadows, or sound effects.
*/
public class GameStateModel 
{
	private Grid mUserGrid, mEnemyGrid;
	private Random mRandom = new Random();
	private boolean mGameStarted = false;
	private boolean mUserTurn = true;
	private int mShipsToInsert = 5;
	private boolean mHorizontal = true;
	
	private static String MAIN_BACKGROUND = "boardv85.png";   // ~ Made it super easy to switch Background
	private static String IMAGE_1 = "navyseal.png";		  //   and Images with just copy -> paste URL.
	private static String START_BACKGROUND = "BF4.jpg"; 
	
	public GameStateModel() 
	{
		super();   // ~ Obj.
	}
	
	public boolean setUserGridResponse(MouseEvent specificUnit)
	{
		if (gameHasStarted())   // ~ One game starts, no need for user to click their own Grid.
			return true;								   		    //~> Else:
		Unit userUnit = (Unit) specificUnit.getSource();   //  ~ Get access to the single Unit click listener for it's coordinates.
		Battleship battleship = new Battleship(mShipsToInsert, isHorizontal());   // ~ Insert BS at the responded listeners location.

		if (mUserGrid.insertBattleShip(battleship, userUnit.mX, userUnit.mY)){
			if (--mShipsToInsert == 0) // ~ All done, move on.
			{	GSController.setHighscore(GSController.getHighscore()+1);
				placeEnemyShips();
				return true;
			}
		}
		return false;
	}	
	
	public void setEnemyGridResponse(MouseEvent specificUnit) 
	{
		if (!gameHasStarted()) // ~ If user hasn't finished placing ships.
			return;
		Unit enemyUnit = (Unit) specificUnit.getSource();
		if (!enemyUnit.isUndamaged()) // ~ Enemy unit already hit.
			return;
		mUserTurn = enemyUnit.unitHit(); // ~ Returns true upon successful
											// battleship contact.
		if (mEnemyGrid.getCurrentShips() == 0) {
			System.out.println("You Win!"); // ***Pop up ~> High scores etc.
			ViewNavigator.loadScene("Victory", ViewNavigator.VICTORY_SCENE);
			//System.exit(0); // ~> Check against High Scores DB and ObsList.
		}
		if (!mUserTurn) // ~ If the hit streak is over, give the AI a chance.
			enemyTurn();
	}
	
	private void placeEnemyShips()
	{
		int shipsToInsert = 5;
		
		while (shipsToInsert > 0)
		{
			 Battleship enemyShip = new Battleship(shipsToInsert, Math.random() > 0.5);
			int x = mRandom.nextInt(10);   // ~ 10 Exclusive (0-9)
			int y = mRandom.nextInt(10);   // ~ Very close to 50% chance.
			
			if (mEnemyGrid.insertBattleShip(enemyShip, x, y))
				shipsToInsert--;
		}
	   mGameStarted = true;
	}
	
	private void enemyTurn()
	{
		while (!mUserTurn)   // ~ Not the user's turn.
		{
			
			int xShot = mRandom.nextInt(10);
			int yShot = mRandom.nextInt(10);
			Unit enemyChoice = mUserGrid.getUnit(xShot, yShot);

			if (!enemyChoice.isUndamaged())   // ~ Keep going until AI misses.
				continue;
			mUserTurn = !enemyChoice.unitHit();   // ~ Returns true if AI hits your Battleship, add '!' to  
												  //   set false for User turn since another is aloud.
			if (mUserGrid.getCurrentShips() == 0)
			{
				ViewNavigator.loadScene("Defeat", ViewNavigator.DEFEAT_SCENE);
				//System.exit(0);  // ~ Popup "YOU LOSE!"
			}
		}
	}
	public boolean gameHasStarted(){
		return mGameStarted;
	}
	public boolean isHorizontal(){
		return mHorizontal;
	}	
	public void changeOrientation() {
		mHorizontal = !mHorizontal;
	}
	public void setEnemyGrid(Grid enemyGrid){
		mEnemyGrid = enemyGrid;
	}
	public void setUserGrid(Grid userGrid){
		mUserGrid = userGrid;
	}
	public Grid getEnemyGrid(){
		return mEnemyGrid;
	}	
	public Grid getUserGrid(){
		return mUserGrid;
	}
	public static String getMainBackground() {
		return MAIN_BACKGROUND;
	}
	public static String getStartBackground() {
		return START_BACKGROUND;
	}
	public static String getImage1() {
		return IMAGE_1;
	}
	public Background setBackgroundImage() {
		Image img = new Image(getMainBackground());
		BackgroundImage bgImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, new BackgroundSize(728, 903, false, true, true, false));
		return new Background(bgImg);
	}
	public Background setStartBackground() {
		Image img = new Image(getStartBackground());
		BackgroundImage bgImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, new BackgroundSize(825, 500, false, false, false, false));
		return new Background(bgImg);
	}
	public Image setImage1() {
		return new Image(getImage1());
	}
}