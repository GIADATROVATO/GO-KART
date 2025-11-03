package Adapter;

import java.util.List;

import model.Pilota;

public class TelemetriaEsterna {
	public void inviaDati(String nome, double velocita, int giro) {
	System.out.println("[Inviati dati al sistema esterno] " +nome+ " telemetria "+ velocita+ " --- " +giro);
	}
}
