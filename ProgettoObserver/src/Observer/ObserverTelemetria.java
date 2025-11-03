package Observer;

import java.util.Map;

import Interface.Observerr;
import model.Pilota;

public class ObserverTelemetria  implements Observerr{

	@Override
	public void aggiorna(Pilota p, int giro, int tempo) {	}
	@Override
	public void aggiornaTelemetria(Map<Pilota, Integer> mappa, double media, int giro) {
		System.out.println("[TELEMETRIA aggiornata]: " + " media: " + String.format("%.2f", media)+ " giro " +giro );	
	/*
	 *  io qui metto la media totale di tutti i piloti ancora in gara
	 *  double mediaGlobale= mappaAttivi.values().stream().mapToInt(Integer::intValue).average().orElse(0);		
	 */
	}
	@Override
	public void aggiornaClassifica(Map<Pilota, Integer> mappa, int giro) {	}
}
