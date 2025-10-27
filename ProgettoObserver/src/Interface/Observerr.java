package Interface;

import java.util.Map;

import model.Pilota;

public interface Observerr {
	public void aggiorna(Pilota p,int giro,int tempo);
	public void aggiornaTelemetria(Map<Pilota,Integer> mappa ,double media, int giro);
	public void aggiornaClassifica(Map<Pilota,Integer> mappa,int giro);
}
