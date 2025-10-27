package State;

import java.util.List;
import java.util.Map;

import Interface.StatoPilota;
import model.Pilota;

public class InGaraState implements StatoPilota{

	@Override
	public String getNome() { return "IN GARA";}

	@Override
	public void eseguiGiro(Pilota pilota,int tempo, Map<Pilota,Integer> penalita, Map<Pilota,Integer> totali) {
		int penna=penalita.getOrDefault(pilota, 0);
		int tempoTotale= totali.getOrDefault(pilota, 0)+penna+tempo;
		totali.put(pilota,tempoTotale);
		
		if(tempo>100) {
			System.out.println("[SQUALIFICA]: " +pilota.getId() +" ritirato");
			pilota.setStatoPilota(new RitiratoState());
		}if(tempo>=50 && tempo<=60) {
			System.out.println("[AI BOX]: " +pilota.getId() +" ai box");
			pilota.setStatoPilota(new PitStopState());
		}	
	}
}
