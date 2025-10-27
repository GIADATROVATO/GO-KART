package Observer;

import java.util.Map;

import Interface.Observerr;
import model.Pilota;

public class ObserverTelemetria  implements Observerr{

	@Override
	public void aggiorna(Pilota p, int giro, int tempo) {	}
	@Override
	public void aggiornaTelemetria(Map<Pilota, Integer> mappa, double media, int giro) {
		System.out.println("[TELEMTRIA aggiornata]: " +" giro "+ +giro + " media: " + String.format("%.2f", media));	
	}
	@Override
	public void aggiornaClassifica(Map<Pilota, Integer> mappa, int giro) {	}
}
