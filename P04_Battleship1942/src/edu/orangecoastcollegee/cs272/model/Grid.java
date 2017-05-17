package edu.orangecoastcollegee.cs272.model;
import java.util.ArrayList;
import java.util.List;

import edu.orangecoastcollege.cs272.p04.controller.GSController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
*/
public class Grid extends Parent
{
	private int mCurrentShips = 5;        // ~ Max 5.
	private boolean mUserTurn = false;    // ~ Start with user.
	private VBox mAllRows = new VBox();   // ~ Each Grid is displayed within a single VBox.
	
	private double mStandardUnit = 21; 
	private double mDecrementUnit = 30;
	
	public Grid (boolean userTurn, EventHandler<MouseEvent> handler)
	{
		mUserTurn = userTurn;
		populateGrid(handler);   // ~ Pass any mouse event through lambda, from our 2 ViewGameFX Boards (mUser & mEnemy) into populateGrid, where the initialization 
	}							 //   and handler assignment's for all 100 Unit objects within (this) corresponding Grid object will take place.
	public void populateGrid (EventHandler<MouseEvent> handler)
	{
		for (int y = 0; y < 10; y++,mDecrementUnit++, mStandardUnit++)        // ~ 10 Rows parsed.
		{									// ~ Each row is represented 
			HBox currentRow = new HBox();   //   by one Horizontal Box.
			for (int x = 0; x < 10; x++)   
			{
				Unit singlePositon;
				
				if (mUserTurn)
					singlePositon = new Unit(this, x, y, mDecrementUnit, mStandardUnit);   // ~ Mouse click handler applied to every unit object.
				
				else 
					singlePositon = new Unit(this, x, y, 28.5, 21); 
				
				singlePositon.setOnMouseClicked(handler);
				currentRow.setAlignment(Pos.CENTER);
				currentRow.getChildren().add(singlePositon);   // ~ We can now feed a lambda expression directly from a mouse click
			}			 							
			//mDecrementUnit += .75;//   which corresponds to the given Grids -> (Unit) Object being clicked.
			//mStandardUnit += .50;
			mAllRows.getChildren().add(currentRow);  
		}
		mAllRows.setAlignment(Pos.BASELINE_CENTER);
		getChildren().add(mAllRows);
	}
	
		/**
		 * ~ Contains 3 necessary methods that allow for the handling of each Unit within this Grid class.
		 * @author Dguido1
		 * @author 
		*/
	public class Unit extends Rectangle
	{
		public int mX, mY;   // ~ Getters returning an int, within a class of type Rectangle are not aloud ~> public.
		private Battleship mBattleship = null;
		private boolean mUndamaged = true;
		
		List<Battleship> mBShipUnits;
		
		public Unit (Grid grid, int x, int y, double hSize, double vSize)
		{
			super (hSize, vSize);   // ~ Simply call Rectangle to set corresponding 
			mX = x;			 //   object's width and height to 30dp.
			mY = y;
			
				// ~ Create a new blue for the grid, since default blue's look like trash.
			
			Color gridFill = new Color(0.0, 0.0, 0.0, 0.0);
			Color uGridOL = new Color(0.0, 0.5
					, 0.0, 0.2);
			Color eGridOL = new Color(0.5, 0.0, 0.0, 0.0);
			
			if(mUserTurn)
				setUnitCosmetics(gridFill, uGridOL);   // ~ Default.
			else
				setUnitCosmetics(gridFill, eGridOL);   // ~ Default.
		}
		
		public boolean isUndamaged()   // ~ Unit untouched by damage.
		{
			return mUndamaged;
		}
			/**
			 * ~ Allows for the manipulated display of any 
			 *   Unit position amongst the 100 Unit Grid. 
			*/
		public void setUnitCosmetics(Color fillColor, Color strokeColor)
		{
			this.setFill(fillColor);
			this.setStroke(strokeColor);
		}
			/**
			 * ~ Deals with the individual single unit response after 
			 *   the corresponding Unit Object has been hit.
			 * @return Status of successful contact upon a Battleship Object.
			*/
		public boolean unitHit()
		{
			mUndamaged = false;
			 if (mBattleship != null)   // ~ If at least part of a Battleship 
			 {						   //   occupies this single unit.
				 if (mUserTurn)
					 setUnitCosmetics(Color.INDIANRED, Color.LIMEGREEN);
				 else
					 setUnitCosmetics(Color.INDIANRED, Color.YELLOW);
				 
				 mBattleship.targetHit();   // ~ (--mDurability)
												 // ~ If the Battleship object has been sunk, reduce the current number
				 if (!mBattleship.isAfloat())    //   of the corresponding Grid Objects current ships variable.
				 {
					setSunkShip();
					GSController.playExplosion();
					// ~ Change the appearance of this and the rest of the cells 
					mCurrentShips--;   //   which retain this fully sunken Battleship object.
				 }					   //   Finally, subtract 1 from current Grid Objects remaining ships variable.
					 return true;	   // ~> Contact was made and dealt with.
			 }
		 setUnitCosmetics(Color.LIGHTGRAY, Color.BLACK);   // ~ MISS!
		 return false;    
		}	

		/**
		 * ~ Cosmetic: Sets only a FULLY sunk ship to the corresponding Color, 
		 *   simulating a reduction in current ships occupying the enemy fleet. 
		*/
	public void setSunkShip() 
	{
		int size = mBattleship.getSize();
		
		if (mUserTurn)
			getUnit(mX, mY).setUnitCosmetics(Color.RED, Color.LIMEGREEN);   // Last unit selected.
		else
			getUnit(mX, mY).setUnitCosmetics(Color.RED, Color.YELLOW); 
		
		if (mBattleship.isHorizontal()) 
		{
			if (mX - 1 >= 0 && getUnit(mX - 1, mY).mBattleship != null)  // ~ Moving 1 left is at least not off the Grid.
					for (int i = mX - 1; i >= 0 && getUnit(i, mY).mBattleship != null; i -= 1)		
					{//   or a null Battleship, color at least one.
						if (mUserTurn)
							getUnit(i, mY).setUnitCosmetics(Color.RED, Color.LIMEGREEN);
						else
							getUnit(i, mY).setUnitCosmetics(Color.RED, Color.YELLOW);	
					}
						
			if (mX + 1 < 10 && getUnit(mX + 1, mY).mBattleship != null)  // ~ Moving 1 Right is at least not off the Grid.														 //   or a null Battleship, color at least one.
				for (int i = mX + 1; i < 10 && getUnit(i, mY).mBattleship != null; i += 1)
				{//   or a null Battleship, color at least one.
					if ( mUserTurn)
						getUnit(i, mY).setUnitCosmetics(Color.RED, Color.LIMEGREEN);
					else
						getUnit(i, mY).setUnitCosmetics(Color.RED, Color.YELLOW);	
				}
		}
		else   // ~ Vertical. 
		{
			if (mY - 1 >= 0 && getUnit(mX, mY - 1).mBattleship != null)  // ~ Moving 1 up is at least not off the Grid.
					for (int i = mY - 1; i >= 0 && getUnit(mX, i).mBattleship != null; i -= 1)													 //   or a null Battleship, color at least one.
					{//   or a null Battleship, color at least one.
						if ( mUserTurn)
							getUnit(mX, i).setUnitCosmetics(Color.RED, Color.LIMEGREEN);
						else
							getUnit(mX, i).setUnitCosmetics(Color.RED, Color.YELLOW);	
					}
			
			if (mY + 1 < 10 && getUnit(mX, mY + 1).mBattleship != null)  // ~ Moving 1 down is at least not off the Grid.														 //   or a null Battleship, color at least one.
				for (int i = mY + 1; i < 10 && getUnit(mX, i).mBattleship != null; i += 1)
				{//   or a null Battleship, color at least one.
					if ( mUserTurn)
						getUnit(mX, i).setUnitCosmetics(Color.RED, Color.LIMEGREEN);
					else
						getUnit(mX, i).setUnitCosmetics(Color.RED, Color.YELLOW);	
				}
		}	
	}
}				
/*
	*******************************
	 ***** END OF UNIT CLASS *****
	*******************************
*/
		/**
		 * ~ Get Child of Rows ~> (Single Row) ~> Get (y) variable representing 
		 *   what row number (1 ~> 10) the corresponding single row occupies.
		 * ~> Finally get x, the horizontal (column) coordinate of the (y) row we just found, then Type cast to Unit.
		 * @param x
		 * @param y
		 * @return Single Unit Object occupying the position amongst the corresponding Grid Object, 
		 *         which contains the parameters passed in for x and y.
		*/
	public Unit getUnit (int x, int y)
	{
		return (Unit)((HBox)mAllRows.getChildren().get(y)).getChildren().get(x);
	}
	
	public boolean insertBattleShip(Battleship battleship, int x, int y)
	{
		if (insertionInRange(battleship, x, y))   // ~ Check for validity of desired battleships insertion coordinates, 
		{										  //   as well as surrounding units for presence of a non null Battleship.
				int size = battleship.getSize();
				
			if (battleship.isHorizontal())   // ~ Battleship boolean variable for orientation.
			{
				for ( int i = x; i < x + size; i++)
				{
					Unit singleUnit = getUnit(i, y);
					singleUnit.mBattleship = battleship;
					
					List<Battleship> enemyBattleships = new ArrayList<>();
					List<Battleship> userBattleships = new ArrayList<>();
					//singleUnit.
					
					if (mUserTurn)   // ~ Only set to visible if it's the users turn.
						singleUnit.setUnitCosmetics(Color.MEDIUMSEAGREEN, Color.BLACK);
				}						
			}
			else
			{
				for (int i = y; i< y + size; i++)
				{
					Unit singleUnit = getUnit(x, i);
					singleUnit.mBattleship = battleship;
					
					if (mUserTurn)  
						singleUnit.setUnitCosmetics(Color.MEDIUMSEAGREEN, Color.BLACK);
				}		
			}
		   return true;
		}
	   return false;
	}
		
	private boolean insertionInRange (Battleship battleship, int x, int y)
	{
		int size = battleship.getSize();
		
		if (battleship.isHorizontal())
		{
			for ( int i = x; i < x + size; i++)   // ~ Set (i) to Horizontal coordinate of Unit that was clicked, run until
			{									  //   that unit + the length of the battleship are addressed.
				if (!isMoveAccessible(i, y))   // ~ Variable (x), stationary (y), since horizontal case.
					return false;
				Unit singlePositon = getUnit(i, y);
				if (singlePositon.mBattleship != null)  // ~ If a ship already exists on this Unit, return invalid.
					return false;
				for (Unit surroundingUnits : getSurroundingUnits(i, y))
				{
					if (!isMoveAccessible(i, y))
						return false;
					if (surroundingUnits.mBattleship != null)  // ~ If a ship already exists on surrounding Units within 1 square
						return false;						   //   unit, return invalid according to the rules of battleship.
				}
			}
		}
		else   // ~ Vertical.
		{	
			for (int i = y; i < y + size; i++)   // ~ Set (i) to Vertical coordinate of Unit that was clicked, run until
			{									 //   that unit + the length of the battleship are addressed.
				if (!isMoveAccessible(x, i))     // ~ Variable (y), stationary (x), since vertical case.
					return false;
				Unit singlePositon = getUnit(x, i);
				if (singlePositon.mBattleship != null)  // ~ If a ship already exists on this Unit, return invalid.
					return false;
				for (Unit surroundingUnits : getSurroundingUnits(x, i)) 
				{
					if (!isMoveAccessible(x, i))
						return false;
					if (surroundingUnits.mBattleship != null)
						return false;
				}
			}
		}
	   return true;
	}
	
	private Unit[] getSurroundingUnits(int x, int y)           // ~ Check all 4 bordering Unit objects for valid corresponding
	{														   //   coordinates within our Grid's movement limit.
		List<Unit> surroundingUnits = new ArrayList<Unit>();
		int x1 = x - 1, x2 = x + 1 ,   // ~ Possible 
				y1 = y - 1, y2 = y + 1;
		
		if (isMoveAccessible(x1, y))		
			surroundingUnits.add(getUnit(x1, y));
		if (isMoveAccessible(x2, y))		
			surroundingUnits.add(getUnit(x2, y));
		if (isMoveAccessible(x, y1))		
			surroundingUnits.add(getUnit(x, y1));
		if (isMoveAccessible(x, y2))		
			surroundingUnits.add(getUnit(x, y2));
		return surroundingUnits.toArray(new Unit[0]);
	}
	private boolean isMoveAccessible(int x, int y)   // ~ Grid movement limit defined.
	{
		return x < 10 && x >= 0 && y < 10 && y >= 0;
	}
	public int getCurrentShips() 
	{
		return mCurrentShips;
	}
	public void setCurrentShips(int mCurrentShips) 
	{
		this.mCurrentShips = mCurrentShips;
	}
}