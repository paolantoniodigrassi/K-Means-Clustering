package database;

/**
 * Classe che estende la classe Exception e che 
 * modella un'eccezione che viene lanciata quando 
 * si tenta di accedere ad un insieme vuoto. 
 */
public class EmptySetException extends Exception {

  /**
   * Costruttore della classe che richiama il costruttore della superclasse.
   * @param txt messaggio di errore.
   */
  public EmptySetException(final String txt) {
    super(txt);
  }
}
