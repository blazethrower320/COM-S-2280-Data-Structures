package edu.iastate.cs2280.hw1;


public class Empty extends TownCell{
	public Empty(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.EMPTY;
	}

	@Override
	public TownCell next(Town tNew) {
		
		
		this.census(nCensus);
    	
		return new Casual(tNew, tNew.getWidth(), tNew.getLength());
	}
}
