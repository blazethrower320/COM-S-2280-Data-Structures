package edu.iastate.cs2280.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


/**
 *  @author Carson Torrey
 *
 */
public class Town {
	
	private int length, width;  //Row and col (first and second indices)
	public TownCell[][] grid;
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		//TODO: Write your code here.
		this.length = length;
		this.width = width;
		this.grid = new TownCell[length][width];
		for (int row = 0; row < length; row++) {
	        for (int col = 0; col < width; col++) {
	            //System.out.println(grid[row][col]);
	        }
	    }
		
		
	}
	
	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		//TODO: Write your code here.
		int rowCounter = 0;
		
		File file = new File(inputFileName);
		Scanner scnr = new Scanner(file);
		
		String nextLine = scnr.nextLine();
		String[] gameSize = nextLine.split(" ");
		int columns = Integer.parseInt(gameSize[0]);
		int rows = Integer.parseInt(gameSize[1]);
		//
		this.length = rows;
		this.width = columns;
		this.grid = new TownCell[rows][columns];
		
		//
		//System.out.println("Col: " + columns + " | Rows: " + rows);
		while(scnr.hasNextLine())
		{
			String nextLineLoop = scnr.nextLine().trim();
			char[] characters = nextLineLoop.toCharArray();
			for(int i = 0; i < characters.length; i++)
			{
				char charr = characters[i];
				if(charr == ' ')
					continue;
				int currentColumn = i/2;
				
				if(charr == 'C')
				{
					grid[rowCounter][currentColumn] = new Casual(this, rowCounter, currentColumn);
				}
				else if(charr == 'S')
				{
					grid[rowCounter][currentColumn] = new Streamer(this, rowCounter, currentColumn);
				}
				else if(charr == 'O')
				{
					grid[rowCounter][currentColumn] = new Outage(this, rowCounter, currentColumn);
				}
				else if(charr == 'E') {
					grid[rowCounter][currentColumn] = new Empty(this, rowCounter, currentColumn);
				}
				else if(charr == 'R')
				{
					grid[rowCounter][currentColumn] = new Reseller(this, rowCounter, currentColumn);
				}
				else
				{
					System.out.println("Character Not found! Char: " + charr);
				}
			}
			rowCounter++;
		}
		scnr.close();
		System.out.println(this.toString());
		
	}
	
	/**
	 * Returns width of the grid.
	 * @return
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Returns length of the grid.
	 * @return
	 */
	public int getLength() {
		return this.length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);
		rand.nextInt(6);
		for (int row = 0; row < length; row++) {
	        for (int col = 0; col < width; col++) {
	        	int randomNum = rand.nextInt(5) + 1;
	        	if(randomNum == 1)
	        	{
	        		this.grid[row][col] = new Casual(this, row,col);
	        	}
	        	else if(randomNum == 2)
	        	{
	        		this.grid[row][col] = new Streamer(this, row,col);
	        	}
	        	else if(randomNum == 3)
	        	{
	        		this.grid[row][col] = new Outage(this, row,col);
	        	}
	        	else if(randomNum == 4)
	        	{
	        		this.grid[row][col] = new Empty(this, row,col);
	        	}
	        	else if(randomNum == 5)
	        	{
	        		this.grid[row][col] = new Reseller(this, row,col);
	        	}
	        	else
	        	{
	        		System.out.println("Random Number made a wrong number");
	        	}
	        }
        }
	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 */
	@Override
	public String toString() {
		
		String s = "---------- GRID ----------\n";
		s += "Dimensions:\n";
		s += getWidth() + " " + getLength() + "\n";
		s += "Layout:\n";
		
		for (int col = 0; col < getLength(); col++) {
			if (col > 0) {
				s += "\n";
			}
			for (int row = 0; row < getWidth(); row++) {
				TownCell town = this.grid[col][row];
				if(town == null)
					continue;
				State type = town.who();
				if(type == State.CASUAL)
				{
					s += "C ";
				}
				else if(type == State.EMPTY)
				{
					s += "E ";
				}
				else if(type == State.OUTAGE)
				{
					s += "O ";
				}
				else if(type == State.RESELLER)
				{
					s += "R ";
				}
				else if(type == State.STREAMER)
				{
					s += "S ";
				}
			}
		}
		s += "\n--------------------------\n";
		return s;
	}
}
