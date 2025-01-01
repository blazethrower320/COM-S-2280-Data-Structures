package edu.iastate.cs2280.hw1;

//@Author Carson Torrey

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TownTestand {
	@Test
	public void TestTownCreation()
	{
		Town town = new Town(3,3);
		assertEquals(3, town.getLength());
		assertEquals(3, town.getWidth());
	}
}
