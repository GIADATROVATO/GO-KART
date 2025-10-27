package Subject;

import java.util.*;
import java.util.Map.*;
import java.util.concurrent.*;
import java.util.stream.*;

import Interface.Observerr;
import Interface.Subject;
import Observer.GestorePitStop;
import model.Pilota;

public class GaraPilota implements Subject{
	private List<Observerr> observer=new ArrayList<>();
	private List<Pilota> pilotiAttivi;
	
	public GaraPilota(List<Pilota> piloti) {
		this.pilotiAttivi=piloti;
	}
	@Override
	public void notificaObserver(Pilota p, int giro, int tempo) {
		for(Observerr o: observer ) {
			o.aggiorna(p, giro, tempo);
		}
	}
	@Override
	public void addObserver(Observerr o) { observer.add(o);	}
	@Override
	public void rimuoviObserver(Observerr o) { observer.remove(o); }
	
	private void aggiornaTelemetria(Map<Pilota, Integer> totali, double media, int giro) {
		for(Observerr o: observer ) {
			o.aggiornaTelemetria(totali, media, giro);
	}}
	public void aggiornaClassifica(Map<Pilota, Integer> mappa, int giro) {	
		for(Observerr o: observer ) {
			o.aggiornaClassifica(mappa, giro);
	}}
	public static Callable<Void> corriGiro(GaraPilota gara){
		return()->{
			Map<Pilota,Integer> totali= new HashMap<>();
			GestorePitStop pena= new GestorePitStop();
			for(int giro=1;giro<3;giro++) {
				if(gara.pilotiAttivi.isEmpty()) {
					System.out.println("nessun pilota attivo");
					break;
				}
				System.out.println(" *** GIRO *** " +giro);
				Map<Pilota,Integer> conta= gara.pilotiAttivi.stream().collect(Collectors.toMap(p->p, p-> (int)(Math.random()*100)));
				conta.forEach((n,t)->{
					System.out.println("nome " +n.getId() + " tempo "+ t);
				});
				Map<Pilota, Integer> nuovaPenalita= pena.aggiornaPene(conta,gara.pilotiAttivi);
				Map<Pilota,Integer> mappaAttivi= totali.entrySet().stream().filter(e->gara.pilotiAttivi.contains(e.getKey())).collect(
						Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
				
				for(Entry<Pilota,Integer> entry: conta.entrySet()) {
					Pilota p= entry.getKey();

				    if (!gara.pilotiAttivi.contains(p)) continue;
					int penna= nuovaPenalita.getOrDefault(p, 0);
					int tempo= entry.getValue();

					if( p.getStatoPilota().getNome()=="RITIRATO") continue;
					
					int tempoTotale= totali.getOrDefault(p, 0)+tempo+penna;
					double conteggio= (double) tempoTotale/giro;
					totali.put(p,tempoTotale);
					System.out.println("** sommario **" + p.getId() + " totale "+tempoTotale +" ( penalità "+penna +")" +
							" Telemetria " +conteggio) ;
					gara.notificaObserver(p,tempoTotale,giro);
					gara.aggiornaTelemetria(mappaAttivi,conteggio,giro);
					
					p.getStatoPilota().eseguiGiro(p, tempo, mappaAttivi, totali);
				}
					
				
				System.out.println("*** classifica ordinata dopo il giro ***" +giro);
				Map<Pilota, Integer> ordine= totali.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (e1,e2)->e1, LinkedHashMap::new));
				ordine.forEach( (n,t)->System.out.println( n.getId() +"	"+ t));
				
				System.out.println(" *** Piloti ancora in gara " +gara.pilotiAttivi.size()+ " ***");
				gara.pilotiAttivi.forEach( s-> System.out.println(" - " +s.getId()));
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					System.out.println("Thread interrotto durante la pausa.");
				}
				gara.aggiornaClassifica(ordine,giro);
			}
			if(!gara.pilotiAttivi.isEmpty()) {
			Optional<Entry<Pilota, Integer>> vincitore= totali.entrySet().stream().filter(e->gara.pilotiAttivi.contains(e.getKey()))
				.min(Map.Entry.comparingByValue());
			System.out.println(" *** Vincitore ***" + vincitore.get().getKey().getId());
			}
		return null;
		};
	}
	

}
