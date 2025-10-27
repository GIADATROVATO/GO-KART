package Observer;

import java.util.Map;

import Interface.Observerr;
import model.Pilota;

public class ObserverClassifica implements Observerr{
	@Override
	public void aggiorna(Pilota p, int giro, int tempo) {	}
	@Override
	public void aggiornaTelemetria(Map<Pilota, Integer> mappa, double media, int giro) {	}
	@Override
	public void aggiornaClassifica(Map<Pilota, Integer> mappa, int giro) {
		mappa.forEach((nome,g)->System.out.println("[CLASSIFICA]: " +nome.getId() + " tempo " +g +" ms" ));
	}

}
