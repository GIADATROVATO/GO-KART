package model;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

import Interface.StatoPilota;
import Interface.Strategia;
import State.InGaraState;
import Subject.eventoGiro;

public class Pilota {
	String id;
	Strategia strategia;
	StatoPilota stato;
	int tempoTotale;
	
	public Pilota(String id, Strategia strategia) {
		this.id=id;
		this.strategia=strategia;
		this.stato= new InGaraState();

	}

	
	public String getId() {
		return id;
	}
	public void setStrategia(Strategia s) {
		this.strategia=s;
	}
	public Strategia getStrategia() {
		return this.strategia;
	}
	public void setStatoPilota(StatoPilota stato) {
		this.stato=stato;
	}
	public StatoPilota getStatoPilota() {
		return this.stato;
	}
	public int incremento(int tempo) {
		return tempoTotale+= tempo;
	}
	public int getTempoTotale() {
		return tempoTotale;
	}
	
	
}
