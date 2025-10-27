package State;

import java.util.Map;

import Interface.StatoPilota;
import model.Pilota;

public class RitiratoState implements StatoPilota {

	@Override
	public String getNome() {
		return "RITIRATO";
	}

	@Override
	public void eseguiGiro(Pilota pilota,int tempo, Map<Pilota,Integer> penalita, Map<Pilota,Integer> totali){
		System.out.println(" STOP " +pilota.getId());
		
	}

}
