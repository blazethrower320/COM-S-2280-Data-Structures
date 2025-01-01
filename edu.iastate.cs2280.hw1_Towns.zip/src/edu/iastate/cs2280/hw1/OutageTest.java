package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//@Author Carson Torrey
public class OutageTest {
	private Town town;
	private TownCell cell;
	
	@BeforeEach
	void setupTown()
	{
		town = new Town(4,4);
		cell = new Outage(town, 0,0);
	}
	@Test
	public void TestWho()
	{
		assertEquals(edu.iastate.cs2280.hw1.State.OUTAGE, cell.who());	
	}
	@Test
	public void TestNext()
	{
		assertEquals(edu.iastate.cs2280.hw1.State.OUTAGE, cell.next(town).who());
	}
}