package model;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

import Interface.Strategia;
import Singleton.GestoreGara;
import State.InGaraState;
import Subject.eventoGiro;

public class PilotaThread implements Runnable{
	private Pilota pilota;

    private GestoreGara logger = GestoreGara.getIstance();
	private BlockingQueue<eventoGiro> coda;
	private Random rand=new Random();
	private int numeroGiri;
	
	public PilotaThread(Pilota pilota,BlockingQueue<eventoGiro> coda, int numeroGiri ) {
		this.pilota=pilota;
		this.coda=coda;
		this.numeroGiri=numeroGiri;
	}
	/*
	 * il comportamento concorrente
	 * Fa “correre” il pilota in un thread separato e invia eventi (EventoGiro)
	 * 
	 */
	
	
	/*
	 * il  pilotathread dovrà avere un blockingQueue che userà per inviare ogni suo giro alla gara
	 * ogni pilotathread in un thread separato mette gli eventi nella coda
	 */
	/*
	@Override
	public void run() {
		try {
			for(int giro=1;giro<=numeroGiri;giro++) {
				int tempo=rand.nextInt(80)+20;
				int penalita= rand.nextInt(10)+1;
				coda.put(new eventoGiro(pilota, giro, tempo));
				
				System.out.println(" ** GIRO ** " + giro+ "[->prodotto] "+pilota.getId()+ " giro " +giro+" tempo "+tempo);
				Thread.sleep(2000);		//simulo tempo per ogni giro
			}
		}catch(InterruptedException e) {Thread.currentThread().interrupt();}
		
		
	}
	
	*/
	 @Override
	    public void run() {
	        try {
	            for (int giro = 1; giro <= numeroGiri; giro++) {
	                int tempo = rand.nextInt(80) + 20; // tempo casuale
	                coda.put(new eventoGiro(pilota, giro, tempo));

	                String msg = "[PILOTA] " + pilota.getId() + " ha completato il giro " + giro + " con tempo " + tempo;
	                System.out.println(msg);
	                logger.log(msg);

	                Thread.sleep(1000); // Simula attesa tra i giri
	            }

	            logger.log("[FINE] Pilota " + pilota.getId() + " ha terminato la gara");

	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	            logger.log("⚠️ Pilota " + pilota.getId() + " interrotto durante la gara");
	        }
	    }
	
	
}
