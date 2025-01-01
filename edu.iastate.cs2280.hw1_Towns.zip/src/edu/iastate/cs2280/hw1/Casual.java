package edu.iastate.cs2280.hw1;

public class Casual extends TownCell{
	public Casual(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.CASUAL;
	}

	@Override
	public TownCell next(Town tNew) {
		//System.out.println("Casuel");
    	this.census(nCensus);
    	
    	
    	if(nCensus[RESELLER] >= 1)
    	{
    		// Put a outage on the Casual Cell
    		return new Outage(tNew, tNew.getWidth(), tNew.getLength());
    	}
    	if(nCensus[STREAMER] >= 1)
    	{
    		return new Streamer(tNew, tNew.getWidth(), tNew.getLength());
    	}
		return new Casual(tNew, tNew.getWidth(), tNew.getLength());
	}
}
