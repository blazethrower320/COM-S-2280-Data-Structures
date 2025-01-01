package edu.iastate.cs2280.hw1;

/**
 * 
 * @author Carson Torrey
 *	Also provide appropriate comments for this class
 *
 */
public abstract class TownCell {

	protected Town plain;
	protected int row;
	protected int col;
	
	
	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	public static final int NUM_CELL_TYPE = 5;
	
	//Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}
	
	/**
	 * Checks all neigborhood cell types in the neighborhood.
	 * Refer to homework pdf for neighbor definitions (all adjacent
	 * neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 *  
	 * @param counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0; 
		nCensus[EMPTY] = 0; 
		nCensus[CASUAL] = 0; 
		nCensus[OUTAGE] = 0; 
		nCensus[STREAMER] = 0; 
		
		
		//System.out.println("Running Census");
    	
		int currentRow = Math.max(0, this.row - 1); 
        int currentCol = Math.max(0, this.col-1); 

        if (currentRow < plain.getLength() - 1) {
        	TownCell aboveCell = plain.grid[currentRow + 1][currentCol];
            if (aboveCell != null) {
                nCensus[aboveCell.who().ordinal()]++;
            }
        }
        
        if (currentRow > 0) {
            TownCell belowCell = plain.grid[currentRow - 1][currentCol];
            if (belowCell != null) {
                nCensus[belowCell.who().ordinal()]++;
            }
        }
        
        if (currentCol > 0) {
        	TownCell leftCell = plain.grid[currentRow][currentCol - 1];
            if (leftCell != null) {
                nCensus[leftCell.who().ordinal()]++;
            }
        }
        
        if (currentCol < plain.getWidth() - 1) {
        	TownCell rightCell = plain.grid[currentRow][currentCol + 1];
            if (rightCell != null) {
                nCensus[rightCell.who().ordinal()]++;
            }
        }
        /*
    	System.out.println("Values : ");
    	System.out.println(nCensus[RESELLER]);
    	System.out.println(nCensus[EMPTY]);
    	System.out.println(nCensus[CASUAL]);
    	System.out.println(nCensus[OUTAGE]);
    	System.out.println(nCensus[STREAMER]);
    	*/
		
	}
	
	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);
}
