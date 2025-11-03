
import java.util.*;
import java.util.concurrent.*;

import Adapter.TelemetriaAdapter;
import Adapter.TelemetriaEsterna;
import Composite.TeamComposite;
import Interface.Observerr;
import Interface.PartecipanteComposite;
import Interface.StatoPilota;
import Interface.Strategia;
import Observer.ObserverClassifica;
import Observer.ObserverDisplay;
import Observer.ObserverTelemetria;
import State.InGaraState;
import State.PitStopState;
import State.RitiratoState;
import Strategia.strategiaAggressiva;
import Strategia.strategiaModerata;
import Subject.GaraPilota;
import model.Pilota;

public class Tester {

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
	
	List<Pilota> piloti = new ArrayList<>(Arrays.asList(p1, p2, p3,p4));
	GaraPilota gara= new GaraPilota(piloti);
 
	Observerr o = new ObserverTelemetria();
	Observerr o1= new ObserverDisplay();
	Observerr o2= new ObserverClassifica();
	gara.addObserver(o);
	gara.addObserver(o1);
	gara.addObserver(o2);
	
	StatoPilota stato1= new InGaraState();
	StatoPilota stato2= new RitiratoState();
	StatoPilota stato3= new PitStopState();
	
	TelemetriaEsterna sistemaEsterno= new TelemetriaEsterna();
	TelemetriaAdapter adapter= new TelemetriaAdapter(sistemaEsterno);
	gara.addObserver(adapter);
	
	 Pilota cp1 = new Pilota("Leclerc", 250);
	 Pilota cp2 = new Pilota("Sainz", 260);
     Pilota cp3 = new Pilota("Verstappen", 240);
     Pilota cp4 = new Pilota("Perez", 245);
     TeamComposite ferrari = new TeamComposite("Ferrari");
     ferrari.add(cp1);
     ferrari.add(cp2);
     TeamComposite redbull = new TeamComposite("RedBull");
     redbull.add(cp3);
     redbull.add(cp4);
     TeamComposite campionato = new TeamComposite("Campionato");
     campionato.add(redbull);
     campionato.add(ferrari);
     ferrari.stampaDettagli();
     redbull.stampaDettagli();
     System.out.println("TEMPO GENERALE "+campionato.getTempoTotaleTeamComposite());
     System.out.println();
	
	ExecutorService executor= Executors.newFixedThreadPool(1);
	Future<Void> task1= executor.submit(GaraPilota.corriGiro(gara));
	task1.get();
	executor.shutdown();
	

	
	
	}
}
