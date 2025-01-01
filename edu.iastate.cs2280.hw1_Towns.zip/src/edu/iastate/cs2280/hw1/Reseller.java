package edu.iastate.cs2280.hw1;


public class Reseller extends TownCell{
	public Reseller(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.RESELLER;
	}

	@Override
	public TownCell next(Town tNew) {
		//System.out.println("Reseller");
		
		this.census(nCensus);
    	
		if(nCensus[CASUAL] >= 3)
		{
			return new Empty(tNew, tNew.getWidth(), tNew.getLength());
		}
		if(nCensus[EMPTY] >= 3)
		{
			new Empty(tNew, tNew.getWidth(), tNew.getLength());
		}
		
		return this;
	}
}
