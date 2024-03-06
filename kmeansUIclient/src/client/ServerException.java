package client;

@SuppressWarnings("serial")

/**
 * Classe che estende Exception e che
 * gestisce le eccezioni scaturite dal server.
 */
public class ServerException extends Exception {

	/**
	 * Costruttore della classe che richiama il costruttore della superclasse
	 * e che inizializza il messaggio di errore.
	 * @param txt Stringa che rappresenta il messaggio di errore.
	 */
	public ServerException(String txt) {
		super(txt);
	}
}
