package Observer;

import java.util.Map;

import Interface.Observerr;
import model.Pilota;

public class ObserverDisplay  implements Observerr{

	@Override
	public void aggiorna(Pilota p, int giro, int tempo) {
		System.out.println("[DISPLAY] -> "+ p.getId() + "  " + giro+"  " +tempo);
		
	}
	@Override
	public void aggiornaTelemetria(Map<Pilota, Integer> mappa, double media, int giro) {}
	@Override
	public void aggiornaClassifica(Map<Pilota, Integer> mappa, int giro) {}

}
