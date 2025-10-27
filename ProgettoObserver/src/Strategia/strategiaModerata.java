package Strategia;

import Interface.Strategia;

public class strategiaModerata implements Strategia{

	@Override
	public int calcola() {
		return (int)(Math.random()*20)+20;	}

}
