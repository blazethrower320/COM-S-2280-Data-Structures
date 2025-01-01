package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.Future.State;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//@Author Carson Torrey
public class CasualTest{

	private Town town;
	private TownCell cell;
	
	@BeforeEach
	void setupTown()
	{
		town = new Town(4,4);
		cell = new Casual(town, 0,0);
	}
	@Test
	public void TestWho()
	{
		assertEquals(edu.iastate.cs2280.hw1.State.CASUAL, cell.who());	
	}
	@Test
	public void TestNext()
	{
		assertEquals(edu.iastate.cs2280.hw1.State.CASUAL, cell.next(town).who());
	}
}
