package edu.iastate.cs2280.hw1;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


// @Author Carson Torrey

public class TownCellTest{
	private Town town;
    private TownCell[][] cells;
    
    @BeforeEach
    public void setupTown()
    {
    	town = new Town(2,2);
    	cells = new TownCell[2][2];
    	
    	cells[0][0] = new Casual(town, 0,0);
		cells[0][1] = new Empty(town, 0,0);
		cells[1][0] = new Casual(town, 0,0);
		cells[1][1] = new Reseller(town, 0,0);
    }
    
    @Test
    public void TestCensus()
    {
    	int[] nCensus = new int[TownCell.NUM_CELL_TYPE];
    	var cell = cells[0][0];
    	cell.census(nCensus);
    	
    
    }
}
