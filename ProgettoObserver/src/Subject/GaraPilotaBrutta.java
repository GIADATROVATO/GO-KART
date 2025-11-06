package Subject;

import java.util.*;
import java.util.Map.*;
import java.util.concurrent.*;
import java.util.stream.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Interface.Observerr;
import Interface.Subject;
import Observer.GestorePitStop;
import Singleton.GestoreGara;
import Utility.SingletonClass;
import model.Pilota;

public class GaraPilotaBrutta implements Subject {
	private List <Observerr> obs= new ArrayList<>();
	private List<Pilota> pilotiAttivi;
	
	private static SingletonClass fileLogger= SingletonClass.getInstance();
	private static Logger logger = LogManager.getLogger(GaraPilota.class);
	
	public GaraPilotaBrutta(List<Pilota> pilota) {
		this.pilotiAttivi=pilota;
	}
	@Override
	public void notificaObserver(Pilota p, int giro, int tempo) {
		for(Observerr o: obs) {
			o.aggiorna(p, giro, tempo);
		}
	}
	@Override
	public void addObserver(Observerr o) { obs.add(o);}
	@Override
	public void rimuoviObserver(Observerr o) { obs.remove(o);}
	
	private void aggiornaTelemetria(Map<Pilota, Integer> totali, double media, int giro) {
		for(Observerr o: obs ) {
			o.aggiornaTelemetria(totali, media, giro);
	}}
	public void aggiornaClassifica(Map<Pilota, Integer> mappa, int giro) {	
		for(Observerr o: obs ) {
			o.aggiornaClassifica(mappa, giro);
	}}
	public static Callable<Void> corriGiro(GaraPilotaBrutta gara){
		return()->{
			
			Map<Pilota,Integer> totali= new HashMap<>();
			for(int giro=1;giro<=3;giro++) {
			GestorePitStop pitStop= GestorePitStop.getInstance();
				if(gara.pilotiAttivi.isEmpty()) {
					logger.warn("nessun pilota attivo. Terminazione gara");
					
					break;
				}
				logger.info(" *** GIRO *** " ,giro);
				
				Map<Pilota,Integer> conta= gara.pilotiAttivi.stream().collect(Collectors.toMap(p->p, p->(int)(Math.random()*100)));
				conta.forEach((n,t)->System.out.println("nome " +n.getId() + " tempo " +t));
				logger.debug("[DEBUG] Tempi generati "+mappaToString(conta));
				
				Map<Pilota,Integer> nuovaPenalita= pitStop.aggiornaPene(conta,gara.pilotiAttivi);
				logger.debug("[DEBUG] Penalità assegnate" +mappaToString(nuovaPenalita));
				
				for(Entry<Pilota,Integer> entry: conta.entrySet()) {
					Pilota p= entry.getKey();
					if(!gara.pilotiAttivi.contains(p)) continue;
					int tempo= entry.getValue();
					int penna= nuovaPenalita.getOrDefault(p, 0);

					if( p.getStatoPilota().getNome().equals("RITIRATO")) continue; 		
					int tempoTotale= tempo+penna+ totali.getOrDefault(p, 0);
					double conteggio =(double) tempoTotale/ giro;
			
					totali.put(p, tempoTotale);
					p.setTempoTotaleComposite(tempoTotale);								 
					
					logger.info( "*** conteggio ***" +p.getId() + " totale " +tempoTotale+" (penalità "+penna+" ) "+" telemetria "+conteggio);
					fileLogger.logging(p.getId()+" - Totale: " +tempoTotale+ "- Penalità: "+penna);
					
					gara.notificaObserver(p, giro, tempoTotale);
				}
				
				Map<Pilota,Integer> mappaAttivi= totali.entrySet().stream().filter(e->gara.pilotiAttivi.contains(e.getKey())).
					collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
				
				double mediaGlobale= mappaAttivi.values().stream().mapToInt(Integer::intValue).average().orElse(0);
				gara.aggiornaTelemetria(mappaAttivi, mediaGlobale, giro);
				logger.info("[TELEMETRIA] Media su piloti attivi " +mediaGlobale+ " giro " +giro);
				
				for(Entry<Pilota,Integer> pilot: mappaAttivi.entrySet()) {
					Pilota ferrari=pilot.getKey();
					/*
					 * se scrivessi️ pilot.getValue() non sarebbe il tempo dell’ultimo giro, ma il tempo totale cumulativo. 
					 * mi servono i tempi generati al tempo corrente dalla mappa conta
					 */
					int tempo=conta.getOrDefault(ferrari, 0);
					ferrari.getStatoPilota().eseguiGiro(ferrari, tempo, mappaAttivi, totali);
				}

				
				logger.info("*** classifica ordinata dopo il giro con eliminazione ***" +giro);
				Map<Pilota,Integer> ordine= mappaAttivi.entrySet().stream().sorted(Map.Entry.comparingByValue())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2)->e1, LinkedHashMap::new));
				ordine.forEach((n,t)-> logger.info(n.getId() + " " +t));
				
			
				System.out.println(" *** Piloti ancora in gara " +gara.pilotiAttivi.size()+ " ***");
				gara.pilotiAttivi.forEach( s-> logger.info(" - " +s.getId()));
				try {
					Thread.sleep(2000);					
				}catch(InterruptedException e) {
					Thread.currentThread().interrupt();
					System.out.println("Thread interrottto durante la pausa");
				}
				
				gara.aggiornaClassifica(ordine,giro);
			}
				if(!gara.pilotiAttivi.isEmpty()) {
					Optional<Entry<Pilota,Integer>> vincitore= totali.entrySet().stream().filter(e->gara.pilotiAttivi.contains(e.getKey()))
						.min(Map.Entry.comparingByValue());
				logger.info(" *** Vincitore ***" + vincitore.get().getKey().getId());
				fileLogger.logging("Vincitore " +vincitore.get().getKey().getId() );
				}
		return null;
		};
	}
	public static String mappaToString(Map<Pilota,Integer> map) {
		return map.entrySet().stream().map(m->m.getKey().getId() +" = "+m.getValue()).collect(Collectors.joining(",", "{","}"));
	}
	
	
	
	
	
}
