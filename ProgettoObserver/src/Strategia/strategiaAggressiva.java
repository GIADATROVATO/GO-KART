package Strategia;

import Interface.Strategia;

public class strategiaAggressiva implements Strategia {

	@Override
	public int calcola() {
		return (int)(Math.random()*150)+50;
	}

}
