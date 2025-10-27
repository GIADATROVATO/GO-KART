package model;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

import Interface.Strategia;
import State.InGaraState;
import Subject.eventoGiro;

public class PilotaThread implements Runnable{
	private Pilota pilota;
	private BlockingQueue<eventoGiro> coda;
	private Random rand=new Random();
	private int numeroGiri;
	
	public PilotaThread(Pilota pilota,BlockingQueue<eventoGiro> coda, int numeroGiri ) {
		this.pilota=pilota;
		this.coda=coda;
		this.numeroGiri=numeroGiri;
	}
	
	
	
	/*
	 * il  pilota dovrà avere un blockingQueue che userà per inviare ogni suo giro alla gara
	 * ogni pilota in un thread separato mette gli eventi nella coda
	 */
	
	@Override
	public void run() {
		try {
			for(int giro=1;giro<numeroGiri;giro++) {
				int tempo=rand.nextInt(80)+20;
				int penalita= rand.nextInt(10)+1;
				coda.put(new eventoGiro(pilota, giro, tempo));
				System.out.println("[PRODUCE] "+pilota.getId()+ " giro " +giro+" tempo "+tempo);
				Thread.sleep(2000);		//simulo tempo per ogni giro
			}
		}catch(InterruptedException e) {Thread.currentThread().interrupt();}
		
		
	}
	
}
