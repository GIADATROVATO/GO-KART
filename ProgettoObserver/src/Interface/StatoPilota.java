package Interface;

import java.util.*;
import model.Pilota;

public interface StatoPilota {
	public String getNome();
	public void eseguiGiro(Pilota pilota,int tempo, Map<Pilota,Integer> penalita, Map<Pilota,Integer> totali);
}
