package Observer;

import java.util.*;
import java.util.stream.Collectors;

import State.RitiratoState;
import model.Pilota;

public class GestorePitStop {
	
		private static final int SOGLIA_RITIRO=80;
	    public Map<Pilota,Integer> aggiornaPene(Map<Pilota,Integer> totali,List<Pilota> list) {
	    	
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
