package Subject;

import model.Pilota;

public class eventoGiro {
	private Pilota pilota;
	private int giro;
	private int tempo;
	private int penalita;
		public eventoGiro(Pilota pilota,int giro , int tempo ) {
			this.pilota=pilota;
			this.giro=giro;
			this.tempo=tempo;
		
		}
		public Pilota getPilota() {
			return pilota;
		}
		public int getGiro() {
			return giro;
		}
		public int getTempo() {
			return tempo;
		}
		public int getPenalita() {
			return penalita;
		}
		@Override
		public String toString() {
			return "eventoGiro [pilota=" + pilota + ", giro=" + giro + ", tempo=" + tempo + ", penalita=" + penalita
					+ "]";
		}
		
}
