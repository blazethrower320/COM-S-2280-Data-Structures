package edu.iastate.cs2280.hw1;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Carson Torrey
 *
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		for (int row = 0; row < tOld.getLength(); row++) {
	        for (int col = 0; col < tOld.getWidth(); col++) {
	        	var oldTownCell = tOld.grid[row][col];
	        	// This will replace the old Town cell with the new one.
	        	var newTownCell = oldTownCell.next(tNew);
	        	tNew.grid[row][col] = newTownCell;
	        }
		}
		System.out.println(tNew.toString());
		return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * @param town
	 * @return
	 */
	public static int getProfit(Town town) {
		//TODO: Write/update your code here.
		int CasuelUsers = 0;
		int size = town.getLength() * town.getWidth();
		for (int row = 0; row < town.getLength(); row++) {
	        for (int col = 0; col < town.getWidth(); col++) 
	        {
	        	var townCell = town.grid[row][col];
	        	if(townCell.who() == State.CASUAL)
	        	{
	        		//System.out.println("Adding +1 for Casuel User");
	        		CasuelUsers++;
	        	}
	        }
        }
		
		return (int)(100*CasuelUsers/size);
	}
	

	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * @throws FileNotFoundException 
	 * 
	 */
	public static void main(String []args) throws FileNotFoundException {
		//TODO: Write your code here.
		Town town;
		Scanner scnr = new Scanner(System.in);
		while(true)
		{
			System.out.println("How to populate grid (type 1 or 2): 1: from a file. 2: randomly with seed");
			String option = scnr.nextLine();
			System.out.println("Option: " + option);
			if(option.equals("1")) {
				System.out.println("Please enter file path:");
				String filePath = scnr.nextLine();
				System.out.println("Path: " + filePath);
				//town = new Town("ISP4x4.txt");
				try {
					town = new Town(filePath);
					var profit = getProfit(town);
					System.out.println("Profit: " + profit);
				}
				catch(Exception e)
				{
					System.out.println("Invalid Path... Restart Program");
					return;
				}
				break;
			}
			else if(option.equals("2"))
			{
				System.out.println("Number of Rows: ");
				int rows = scnr.nextInt();
				System.out.println("Number of Columns: ");
				int columns = scnr.nextInt();
				System.out.println("Seed Integer: ");
				int seed = scnr.nextInt();
				
				town = new Town(rows, columns);
				town.randomInit(seed);
				System.out.println(town.toString());
				break;
			}
		}
		for(int i = 1; i <= 12; i++)
		{
			int totalProfit = getProfit(town);
			System.out.println("Month: " + i + " | Profit: " + totalProfit);
			town = updatePlain(town);
		}
	}
}
