package Singleton;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * 
 * @author giadatrovato
 * il file viene aperto una sola volta(quando viene creato) , poi ogni chiamata successiva a log() scrive dentro a sto file, senza riaprirlo
 * 
 * se mettessi il try in un metodo a parte dovrei ogni volta aprire-scrivere-chiudere ( rischioso perche 2 thread potrebbero accedere al file contemporaneamente)
 */
public class GestoreGara {
	
	private PrintWriter writer;
	private static GestoreGara instance;
		private GestoreGara() {												// il costruttore è il punto migliore in cui inizializzare le risorse per il singleton
			try {
				writer=new PrintWriter(new FileWriter("log.txt",true ));
			}catch(IOException io) { io.printStackTrace();	}
		}
		public static GestoreGara getIstance() {
			return Holder.INSTANCE;
		}
		private static final class Holder{
			private static GestoreGara INSTANCE= new GestoreGara();
		}
		public void log(String message) {
			String timeStamp=LocalDateTime.now()+" " +message;
			System.out.println("[LOG]"+ timeStamp);
			writer.println(timeStamp);										// scrive su file
			writer.flush();													//svuota il bueffer per scrivere subito su file
		}
}
