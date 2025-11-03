import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Singleton.GestoreGara;
import Strategia.strategiaAggressiva;
import Strategia.strategiaModerata;
import Subject.GaraPilota;
import Subject.GaraPilotaQueue;
import model.Pilota;
import model.PilotaThread;

public class TesterThread {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
	
		Pilota p1= new Pilota("Luca", new strategiaAggressiva());
		Pilota p2= new Pilota("Paolo", new strategiaModerata());
		Pilota p3= new Pilota("Chiara",new strategiaAggressiva());
		Pilota p4= new Pilota("Franco",new strategiaAggressiva());
		/*
		 * Arrays.asList(...) → restituisce una lista fissa, di lunghezza uguale all’array originale.
		 * Puoi fare get() o set(), ma non add() o remove().
		 * new ArrayList<>(Arrays.asList(...)) → crea una copia vera in memoria, quindi puoi aggiungere, rimuovere o iterare liberamente.
		 */
		
		
		List<Pilota> piloti = new ArrayList<>(Arrays.asList(p1, p2, p3, p4));

		GaraPilotaQueue gara = new GaraPilotaQueue(piloti);
		Thread threadGara = new Thread(gara);
		threadGara.start();

		// Avvia i piloti e tieni traccia dei thread
		List<Thread> threadsPiloti = new ArrayList<>();

		for (Pilota p : piloti) {
		    Thread t = new Thread(new PilotaThread(p, gara.getQueu(), 3));
		    threadsPiloti.add(t);
		    t.start();
		}

		// Attendi che TUTTI i piloti abbiano finito
		for (Thread t : threadsPiloti) {
		    try {
		        t.join();
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		}

		// Poi attendi la fine della gara
		try {
		    threadGara.join();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}

		System.out.println("🏁 Tutti i thread terminati — gara completa.");

		
		
	}
}
