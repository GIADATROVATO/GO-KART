package Subject;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import Interface.Observerr;
import Interface.Subject;
import Observer.GestorePitStop;
import Singleton.GestoreGara;
import model.Pilota;

public class GaraPilotaQueue implements Subject,Runnable{
	private BlockingQueue<eventoGiro> queue= new LinkedBlockingQueue<>();
	private static List <Pilota> pilotiAttivi;
	private Map<Pilota,Integer> totali=new HashMap<>();
	private GestorePitStop gestorePit= GestorePitStop.getInstance();
	private List<Observerr> observer= new ArrayList<>();
	private volatile boolean garaAttiva=true;
	private int numeroGiri=3;
	GestoreGara logger= GestoreGara.getIstance();
	
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
/*	
	@Override
	public void run() {
		logger.log("la Gara è iniziata ");
		int eventiRicevuti=0;
		int eventiTotaliAttesi= numeroGiri*pilotiAttivi.size();
		try {
			while(eventiRicevuti<eventiTotaliAttesi) {
				eventoGiro evento=queue.take();
				Pilota p= evento.getPilota();
				int tempo=evento.getTempo();
				int giro=evento.getGiro();
			//penalita gestore
				Map<Pilota,Integer> penalitaPene= gestorePit.aggiornaPene(totali, pilotiAttivi);
				int penna=penalitaPene.getOrDefault(p, 0);
				int tempoTotale= totali.getOrDefault(p, 0)+tempo+penna;
				totali.put(p, tempoTotale);
				double conteggio = (giro > 0) ?  (double) tempoTotale / giro:0;
				
				Map<Pilota,Integer> mappaAttivi= totali.entrySet().stream().filter(e->
					GaraPilotaQueue.pilotiAttivi.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			// stampa e log per ogni giro
				String msg= "** " +p.getId()+ " totale "+tempoTotale +" ( penalità "+penna +")" +" Telemetria " +conteggio;
				System.out.println(msg);
				logger.log(msg);
					
				notificaObserver(p,tempoTotale,giro);
				aggiornaTelemetria(mappaAttivi,conteggio,giro);
				
				p.getStatoPilota().eseguiGiro(p, tempoTotale, Collections.emptyMap(), mappaAttivi);
				eventiRicevuti++;
				
			//se tutti hanno finito il giro aggiorna la classifica
				if(eventiRicevuti % GaraPilotaQueue.pilotiAttivi.size()==0) {
					Map<Pilota,Integer> ordine= mappaAttivi.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(
						Map.Entry::getKey,Map.Entry::getValue,(e1,e2)->e1, LinkedHashMap::new));
					
					logger.log(" Classifica parziale giro " +giro +":");	
					ordine.forEach((t,n)-> logger.log("-> " +t.getId()+ " tempo " +n));
					
					
					aggiornaClassifica(ordine,giro);
				}
				if(eventiRicevuti>=eventiTotaliAttesi) {
					garaAttiva=false;
				}
			}
		} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				logger.log("Gara interrotta ");
		}
		
		// vincitore 
		if(!pilotiAttivi.isEmpty()) {
			Optional<Entry<Pilota, Integer>> vincitore= totali.entrySet().stream().filter(e->GaraPilotaQueue.pilotiAttivi.contains(e.getKey()))
				.min(Map.Entry.comparingByValue());
			vincitore.ifPresent(v->{
				String nome= v.getKey().getId();
				int tempistica= v.getValue();
				String risultato= "Vincitore: "+nome+" con tempo totale "+tempistica;
				System.out.println(risultato);
				logger.log(risultato);
			});
		}
		logger.log(" Gara terminata correttamente");
	}
*/


	
	@Override
    public void run() {
        logger.log("La Gara è iniziata!");
        int eventiRicevuti = 0;
        int eventiTotaliAttesi = numeroGiri * pilotiAttivi.size();

        try {
            while (eventiRicevuti < eventiTotaliAttesi) {
                eventoGiro evento = queue.take();
                Pilota p = evento.getPilota();
                int tempo = evento.getTempo();
                int giro = evento.getGiro();
                if (giro <= 0) {
                    logger.log("⚠️ Evento anomalo ricevuto da " + p.getId() + " con giro=" + giro);
                    continue; // Salta questo evento
                }
                // Penalità e ritiro
                Map<Pilota, Integer> penalitaPene = gestorePit.aggiornaPene(totali, pilotiAttivi);
                int penna = penalitaPene.getOrDefault(p, 0);

                int tempoTotale = totali.getOrDefault(p, 0) + tempo + penna;
                totali.put(p, tempoTotale);

                double media = (giro > 0) ? (double) tempoTotale / giro : 0;

                // Log e stampa
                String msg = "** " + p.getId() + " totale " + tempoTotale + " (penalità " + penna + ") Telemetria " + media;
                System.out.println(msg);
                logger.log(msg);

                notificaObserver(p, giro, tempoTotale);
                aggiornaTelemetria(totali, media, giro);

                eventiRicevuti++;

                // Aggiorna classifica ogni giro completo
                if (eventiRicevuti % pilotiAttivi.size() == 0) {
                    Map<Pilota, Integer> classifica = totali.entrySet().stream()
                            .filter(e -> pilotiAttivi.contains(e.getKey()))
                            .sorted(Map.Entry.comparingByValue())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (e1, e2) -> e1, LinkedHashMap::new));

                    logger.log("Classifica parziale giro " + giro + ":");
                    classifica.forEach((t, n) -> logger.log("-> " + t.getId() + " tempo " + n));
                    aggiornaClassifica(classifica, giro);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log("Gara interrotta!");
        }

        // Stampa vincitore
        if (!pilotiAttivi.isEmpty()) {
            Optional<Entry<Pilota, Integer>> vincitore = totali.entrySet().stream()
                    .filter(e -> pilotiAttivi.contains(e.getKey()))
                    .min(Map.Entry.comparingByValue());

            vincitore.ifPresent(v -> {
                String nome = v.getKey().getId();
                int tempoTotale = v.getValue();
                String risultato = "🏆 Vincitore: " + nome + " con tempo totale " + tempoTotale;
                System.out.println(risultato);
                logger.log(risultato);
            });
        }

        logger.log("Gara terminata correttamente");
    }

	

	public BlockingQueue<eventoGiro> getQueu() {
		return queue;
	}
}
