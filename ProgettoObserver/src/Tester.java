
import java.util.*;
import java.util.concurrent.*;
//sistema di logging 
import java.io.IOException;
import java.util.logging.*;

// import 

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.sun.tools.javac.Main;

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

	//private static final Logger logger= Logger.getLogger(Main.class.getName());					//metodo per la classe e istanzio il logger
    private static final Logger logger = LogManager.getLogger(Main.class);
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
	
	/*
	 * 	versione 1 
	 */
		
	/*	
	setupLogger();	
	logger.info("applicazione avviata");
	logger.warning("questo è un warning");
	logger.severe("questo è un errore");
	logger.fine("questo è un messaggio di debug");
	logger.info("applicazione terminata");
	*/
		
	/*
	 * versione 2 	
	 */
	logger.info("Applicazione avviata!");
	logger.debug("Messaggio di debug (visibile solo se livello >= DEBUG)");
	logger.error("Esempio di errore");	
		
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
	

	// COMPITI 
	/* 1.faccio un package con le utility, con dentor una classe Logger che è di tipo Singleton , voglio un solo logger che scrive su file
	 * se viene istanziato restiutisce un logger 
	 * 2. sostuisco i printl coi logger e dove non li ho messi li aggiungo per completezza con info,debug ecc 
	 *  
	 */
	
	}
	/*   questo metodo lo ho ora sull xml 
	private static void setupLogger() {
        try {
            // Disattiva i log duplicati sulla console, fa un reset e imposta il lvl globale del logger
            LogManager.getLogManager().reset();

            // Imposta il livello globale del logger
            logger.setLevel(Level.ALL);

            // Handler per console, per log su console 
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            logger.addHandler(consoleHandler);

            // Handler per file, per log su file 
            FileHandler fileHandler = new FileHandler("app.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
