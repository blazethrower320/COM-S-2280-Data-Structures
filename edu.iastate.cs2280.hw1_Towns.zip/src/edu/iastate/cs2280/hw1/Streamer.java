package edu.iastate.cs2280.hw1;


public class Streamer extends TownCell{
	public Streamer(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.STREAMER;
	}

	@Override
	public TownCell next(Town tNew) {
		//System.out.println("Streamer");
		
		this.census(nCensus);
    	
		if(nCensus[STREAMER] >= 1)
		{
			new Outage(tNew, tNew.getWidth(), tNew.getLength());
		}
		if(nCensus[OUTAGE] >= 1)
		{
			new Empty(tNew, tNew.getWidth(), tNew.getLength());
		}
		
		return this;
	}

}
