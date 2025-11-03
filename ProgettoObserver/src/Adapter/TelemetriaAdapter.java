package Adapter;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import Interface.Observerr;
import model.Pilota;

public class TelemetriaAdapter implements Observerr{
	private TelemetriaEsterna esterno;
	public TelemetriaAdapter(TelemetriaEsterna esterno) {
		this.esterno=esterno;

	}
	
	@Override
	public void aggiorna(Pilota p, int giro, int tempo) {}

	@Override
	public void aggiornaTelemetria(Map<Pilota, Integer> mappa, double velocita, int giro) {
		for(Entry<Pilota,Integer> pil :mappa.entrySet()) {
			if(giro>0) {
			double conteggio= (double) pil.getValue() / giro;
			esterno.inviaDati(pil.getKey().getId(), conteggio, giro);
			}
		}
		
	}

	@Override
	public void aggiornaClassifica(Map<Pilota, Integer> mappa, int giro) {}

	

}
