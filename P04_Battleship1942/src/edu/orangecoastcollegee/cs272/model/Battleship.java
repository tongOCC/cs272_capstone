package edu.orangecoastcollegee.cs272.model;

import javafx.scene.Parent;

/**
 * David Guido
 * Teven Ong
 * CS 272 ~ Java II
 * @version May 1, 2017
* 
 * @author Dguido1
 * @author
*
 * ~ The <code>Battleship</code> Class allows for the construction of Battleship objects.
 * ~ Each of these objects simulate a battle ship during WWII, which 
 *   make up part of either the player or enemy controlled fleet (Limited at five ships).
 * ~ Each ship ranges from 1 ~> 5 units on the corresponding 100 unit board.
 * ~ As of the start of the game, the user will first have access to the placement of their fleets 5 ships, 
 *   starting with the largest (5 units), until they have placed the last (1 unit). 
*/
public class Battleship extends Parent

{			// ~ mSize is the category of Battleship that the corresponding object specifies (1 -> 5)
			// ~ mDurability is set to mSize then manipulated as the object is struck by artillery.
		private int mSize;
		private int mDurability;
		private boolean mHorizontal;
	
	public Battleship (int size, boolean horizontal)
	{
		mSize = size;
		mHorizontal = horizontal;
		mDurability = size;   // ~ Initialize durability  
	}						  //   same as size (1 ~> 5).
		/**
		 * ~ To be used with orientation button listener.
		 * @param mHorizontal
		 */
	public int getSize() 
	{
		return mSize;
	}
	public int getDurability() 
	{
		return mDurability;
	}
	public boolean isHorizontal() 
	{
		return mHorizontal;
	}
	public void setHorizontal (boolean mHorizontal) 
	{
		this.mHorizontal = mHorizontal;
	}		
	    /**
		 * ~ Simulates a damage (1 durability) being subtracted from the corresponding <code>Battleship</code>
		 *   object after contact upon the <code>Unit</code> object's coordinates initiated recognition 
		 *   of a this non null <code>Battleship</code> object.
		*/
	public void targetHit()
	{
		mDurability--;
	}
	    /**
		 * ~ Returns the survival status of the corresponding <code>Battleship</code> object.
		 * @return Survival status of the <code>Battleship</code> object.
		*/
	public boolean isAfloat()
	{
		return mDurability > 0;
	}
}