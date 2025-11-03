package Composite;

import java.util.*;

import Interface.PartecipanteComposite;

public class TeamComposite implements PartecipanteComposite {
	private List<PartecipanteComposite> comp= new ArrayList<>();
	private String nomeTeam;
	public TeamComposite( String nomeTeam) {
		this.nomeTeam=nomeTeam;
	}
	public void add(PartecipanteComposite com) { comp.add(com);}
	public void remove(PartecipanteComposite com) { comp.remove(com);}
	@Override
	public String getIdComposite() {
		return nomeTeam;
	}
	@Override
	public int getTempoTotaleTeamComposite() {
		int tot=0;
		for( PartecipanteComposite parte: comp) {
			tot+= parte.getTempoTotaleTeamComposite();
		}
		return tot;
	}
	  public void stampaDettagli() {
	        System.out.println("[@] Team: " + nomeTeam);
	        for (PartecipanteComposite parte : comp) {
	            System.out.println("nome " +parte.getIdComposite()+ " "+ parte.getTempoTotaleTeamComposite() + " ("  + " sec)");
	        }

	    }


	
}
