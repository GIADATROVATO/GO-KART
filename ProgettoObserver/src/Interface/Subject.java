package Interface;

import model.Pilota;

public interface Subject {
	public void notificaObserver(Pilota p,int giro,int tempo);
	public void addObserver(Observerr o);
	public void rimuoviObserver(Observerr o);
}
