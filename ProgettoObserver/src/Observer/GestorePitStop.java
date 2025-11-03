package Observer;

import java.util.*;
import java.util.stream.Collectors;

import State.RitiratoState;
import model.Pilota;

public class GestorePitStop {
		//  Singleton 
	/**
	 * rendere l’intera classe GestorePitStop un Singleton in modo che:
	 * esista una sola istanza globale (non più new GestorePitStop() in ogni gara),
	 * i vari thread (PilotaThread, GaraPilotaQueue) condividano lo stesso oggetto,
	 * quindi le stesse penalità, soglia di ritiro e stato interno.
	 */
	
		private static final int SOGLIA_RITIRO=90;
		private GestorePitStop() {}
		
		private static class Holder {
			private static final GestorePitStop INSTANCE= new GestorePitStop();
		}
		public static GestorePitStop getInstance() {
			return Holder.INSTANCE;
		}
	//	
	    public  Map<Pilota,Integer> aggiornaPene(Map<Pilota,Integer> totali,List<Pilota> list) {
	    	
	    Random ran = new Random();
	    Map<Pilota,Integer> random= new HashMap<>();
	    Iterator<Pilota> it= list.iterator();
	    while(it.hasNext()) {
	    	Pilota p=it.next();
	    	int penalitaPilota= ran.nextInt(10)+1;
	    	int tempoCasuale= ran.nextInt(100);
	    	int tempo= totali.getOrDefault(p, 0);
	    		if(tempo>SOGLIA_RITIRO) {
	    			System.out.println("XXX " +p.getId()+" si è ritirato con un tempo di " +tempo);
	    			it.remove();
	    			p.setStatoPilota(new RitiratoState());
	    		}else {
	    		random.put(p, penalitaPilota);
	    		}
	    }

	    return random;
	    }
}
