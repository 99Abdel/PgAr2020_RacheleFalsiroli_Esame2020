package it.unibs.ing.progettoarnaldo.esame2020;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import it.unibs.ing.progettoarnaldo.mylib.*;


public class IniziaUnoGiOh {

	private static final String RICHIESTA_NUMERO_GIOCATORI = "Quanti giocatori vogliono giocare?\n"
			+ "1. Un solo giocatore\n"
			+ "2. Due giocatori\n"
			+ "3. Tre giocatori\n"
			+ "4. Quattro giocatori\n"
			+ "Inserire opzione scelta: ";
	
	private static final String FINE_PARTITA = "\nIl giocatore %s ha vinto, perch� ha terminato le carte del suo mazzo.";
	
	private static final String MSG_INIZIALE = "\nINIZIA LA PARTITA!";
	private static final String RICHIESTA_NOME = "\nInserire nome giocatore: ";
	

	/**
	 * METODO iniziaPartita.
	 * Si occupa di gestire la partita con i turni.
	 */
	public void iniziaPartita()
	{
		IniziaUnoGiOh gioco = new IniziaUnoGiOh();
		Mazzo mazzo = new Mazzo();
		ArrayList<Giocatore> giocatori = new ArrayList<Giocatore>();

		System.out.println(MSG_INIZIALE);
		
		int numeroGiocatori = InputDati.leggiIntero(RICHIESTA_NUMERO_GIOCATORI, 1, 4);
		
		// LISTA GIOCATORI
		for (int i = 0; i < numeroGiocatori; i++) 
		{
			Giocatore g = null;
			g = new Giocatore(InputDati.leggiStringaNonVuota(RICHIESTA_NOME));
			giocatori.add(g);
			
		}
		
		// VIENE SCELTO ORDINE DI PARTENZA
		Map <Integer, Giocatore> ordineTurni = new TreeMap<Integer, Giocatore>();
		ordineTurni = gioco.iniziaTu(giocatori);
		
		
		do 
		{
			// CORPO DELLA PARTITA
			/*
				A inizio partita il mazzo viene mischiato e vengono date ad ogni giocatore 7 carte, successivamente
				viene estratta un�altra carta dal mazzo che sar� quella che indicher� il colore che �comanda� e verr�
				messa nel mazzo degli scarti.
			*/
			Carta cartaEstratta = new Carta();
			mazzo.mischiaMazzo(); // mischio il mazzo
			cartaEstratta = mazzo.aggiungiCartaScartata();
			
			Carta cartaGiocatore = new Carta();
			for (Giocatore g : ordineTurni.values())
			{
				cartaGiocatore = g.scartaCarta(cartaEstratta);
				if (cartaGiocatore != null)
					cartaEstratta = cartaGiocatore;
			}

			if (InputDati.yesOrNo("Vuoi vedere la situazione dei giocatori? ")) {
				for (int i = 0; i < giocatori.size(); i++)
					System.out.println(giocatori.get(i).toString());
			}
			
		} while(gioco.isFinita(giocatori) == false);
	
	}
	
	
	/*Per decidere chi inizia a giocare i giocatori lanciano un dado a 6 facce con numeri da 1 a 6
	(estremi inclusi), chi otterr� il numero pi� alto inizia il turno. Nel caso uscisse lo stesso numero a
	entrambi i giocatori il dado verr� rilanciato nuovamente da essi.*/
	
	private Map<Integer, Giocatore> iniziaTu (ArrayList<Giocatore> elencoGiocatori) 
	{
		Map<Integer, Giocatore> infoGiocatore = new TreeMap<Integer, Giocatore>();
		
		for (int i = 0; i < elencoGiocatori.size(); i++) 
		{
			int numeroEstrattoDado = EstrazioniCasuali.estraiIntero(1, 6);
			infoGiocatore.put(numeroEstrattoDado, elencoGiocatori.get(i));
		}
			
		return infoGiocatore;	
	}
	
	
	/**
	 * METODO isFinita
	 * Determina se continuare il gioco oppure no. Termina quando un giocatore ha finito le sue carte.
	 * @param elencoGiocatori
	 * @return true se � finita.
	 */
	private boolean isFinita(ArrayList<Giocatore> elencoGiocatori) 
	{
		boolean finita = false;
		
		for (int i = 0; i < elencoGiocatori.size(); i++) 
		{
			if (elencoGiocatori.get(i).numeroCarte() == 0) 
			{
				System.out.println(String.format(FINE_PARTITA, elencoGiocatori.get(i).getNome()));
				finita = true;
			}
		}
		return finita;
	}
}
