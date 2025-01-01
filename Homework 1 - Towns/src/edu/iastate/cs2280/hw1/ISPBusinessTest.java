package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//@Author Carson Torrey
public class ISPBusinessTest {
	public Town town;
	public TownCell[][] cells;
	
	@BeforeEach
	public void setUpTown()
	{
		town = new Town(2,2);
		cells = new TownCell[2][2];
		cells[0][0] = new Casual(town, 0,0);
		cells[0][1] = new Empty(town, 0,0);
		cells[1][0] = new Casual(town, 0,0);
		cells[1][1] = new Reseller(town, 0,0);
	}
	
	@Test
	public void testProfit()
	{
		int profit = ISPBusiness.getProfit(town);
		assertEquals(50, profit);
	}
}
