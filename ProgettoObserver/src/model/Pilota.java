package model;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

import Interface.PartecipanteComposite;
import Interface.StatoPilota;
import Interface.Strategia;
import State.InGaraState;
import Subject.eventoGiro;

/*
 * il modello / entità logica
 * Ha nome, stato, strategia, tempo totale, ecc. (cioè i dati del pilota)
 */

public class Pilota implements PartecipanteComposite{
	String id;
	Strategia strategia;
	StatoPilota stato;
	int tempoTotale;
	
	public Pilota(String id, Strategia strategia) {
		this.id=id;
		this.strategia=strategia;
		this.stato= new InGaraState();

	}
	public Pilota(String id,int tempoTotale) {
		this.id=id;
		this.tempoTotale=tempoTotale;
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


	public int setTempoTotaleComposite(int tempo) {
		tempoTotale+=tempo;
		return tempoTotale;
	}
	@Override
	public int getTempoTotaleTeamComposite() {
		return tempoTotale;
	}
	@Override
	public String getIdComposite() {
		return this.id;
	}
	
	
}
