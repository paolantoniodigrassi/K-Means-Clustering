package database;

/**
 * Classe che modella un'eccezione che viene lanciata quiando 
 * si verifica un errore nella connessione al database.
 */
public class DatabaseConnectionException extends Exception{
	
	/**
	 * Costruttore della classe.
	 * Richiama il costruttore della superclasse Exception.
	 * @param txt messaggio di errore.
	 */
	public DatabaseConnectionException(final String txt) {
		super(txt);
	}
}
