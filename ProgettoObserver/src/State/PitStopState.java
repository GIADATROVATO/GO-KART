package State;

import java.util.Map;

import Interface.StatoPilota;
import model.Pilota;

public class PitStopState implements StatoPilota {
	private int giriFermo;
	@Override
	public String getNome() {
		return "PIT STOP";
	}

	@Override
	public void eseguiGiro(Pilota pilota,int tempo, Map<Pilota,Integer> penalita, Map<Pilota,Integer> totali) {
		
	if (giriFermo > 0) {
		System.out.println("[AI BOX]: " + pilota.getId() + " è fermo ai box (" + giriFermo + " giro)");
        giriFermo--;
        return; // non aggiunge tempo, non rientra ancora
	}

	        // Dopo essersi fermato, rientra in gara
	 System.out.println("[RIENTRO]: " + pilota.getId() + " rientra in gara!");
	 pilota.setStatoPilota(new InGaraState());
	}
	
}
