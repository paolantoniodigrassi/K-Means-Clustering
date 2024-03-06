package database;

/**
 * Classe che estende la classe Exception e modella
 * un'eccezione di tipo NoValueException.
 */
public class NoValueException extends Exception {
	
  /**
   * Costruttore della classe.
   * Richiama il costruttore della superclasse Exception.
   * @param txt messaggio di errore.
   */
  public NoValueException(final String txt) {
    super(txt);
  }
}
