package Subject;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import Interface.Observerr;
import Interface.Subject;
import Observer.GestorePitStop;
import model.Pilota;

public class GaraPilotaQueue implements Subject,Runnable{
	private BlockingQueue<eventoGiro> queue= new LinkedBlockingQueue<>();
	private static List <Pilota> pilotiAttivi;
	private Map<Pilota,Integer> totali=new HashMap<>();
	private GestorePitStop gestorePit=new GestorePitStop();
	private List<Observerr> observer= new ArrayList<>();
	private volatile boolean garaAttiva=true;
	private int numeroGiri=3;
	public GaraPilotaQueue( List <Pilota> piloti) {
		this.pilotiAttivi=piloti;
	}
	
	@Override
	public void notificaObserver(Pilota p, int giro, int tempo) {
		for(Observerr o: observer) {
			o.aggiorna(p, giro, tempo);
		}
	}
	@Override
	public void addObserver(Observerr o){observer.add(o);}
	@Override
	public void rimuoviObserver(Observerr o){observer.remove(o);}

	private void aggiornaTelemetria(Map<Pilota, Integer> totali, double media, int giro) {
		for(Observerr o: observer ) {
			o.aggiornaTelemetria(totali, media, giro);
	}}
	public void aggiornaClassifica(Map<Pilota, Integer> mappa, int giro) {	
		for(Observerr o: observer ) {
			o.aggiornaClassifica(mappa, giro);
	}}	
	
	public void aggiungiEvento(eventoGiro e) {
		queue.offer(e);								//metodo che permette ai piloti di inviare i loro eventi
	}
	
	@Override
	public void run() {
		System.out.println("la Gara è iniziata ");
		int eventiRicevuti=0;
		while(garaAttiva) {
			try {
				eventoGiro evento=queue.take();
				Pilota p= evento.getPilota();
				int tempo=evento.getTempo();
				int giro=evento.getGiro();
			//penalita gestore
				Map<Pilota,Integer> penalitaPene= gestorePit.aggiornaPene(totali, pilotiAttivi);
				int penna=penalitaPene.getOrDefault(p, 0);
				int tempoTotale= totali.getOrDefault(p, 0)+tempo+penna;
				totali.put(p, tempoTotale);

				double conteggio= (double) tempoTotale/giro;
				
				Map<Pilota,Integer> mappaAttivi= totali.entrySet().stream().filter(e->
					GaraPilotaQueue.pilotiAttivi.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
				
				System.out.println("** sommario **" + p.getId() + " totale "+tempoTotale +" ( penalità "+penna +")" +
						" Telemetria " +conteggio) ;
				notificaObserver(p,tempoTotale,giro);
				aggiornaTelemetria(mappaAttivi,conteggio,giro);
				
				p.getStatoPilota().eseguiGiro(p, tempoTotale, Collections.emptyMap(), mappaAttivi);
				eventiRicevuti++;
				
			//se tutti hanno finito il giro aggiorna la classifica
				if(eventiRicevuti % GaraPilotaQueue.pilotiAttivi.size()==0) {
					Map<Pilota,Integer> ordine= mappaAttivi.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(
						Map.Entry::getKey,Map.Entry::getValue,(e1,e2)->e1, LinkedHashMap::new));
						
					ordine.forEach((t,n)-> System.out.println("*** Vincitore *** "+t.getId()+ " tempo " +n));
					aggiornaClassifica(ordine,giro);
					
				}
				if(giro>=numeroGiri) {
					garaAttiva=false;
				}
				
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Gara interrotta ");
			}
		}
		// vincitore 
		if(!GaraPilotaQueue.pilotiAttivi.isEmpty()) {
			Optional<Entry<Pilota, Integer>> vincitore= totali.entrySet().stream().filter(e->GaraPilotaQueue.pilotiAttivi.contains(e.getKey()))
				.min(Map.Entry.comparingByValue());
			System.out.println(" *** Vincitore ***" + vincitore.get().getKey().getId());
			}
	}

	public BlockingQueue<eventoGiro> getQueu() {
		return queue;
	}
}
