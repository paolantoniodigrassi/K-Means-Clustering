package data;

/**
 * Classe concreta che estende la superclasse Exception, 
 * modella l'eccezione OutOfRangeSampleSize.
 */

public class OutOfRangeSampleSize extends Exception{

	/**
	 * Costruttore della classe, richiama il costruttore della superclasses Exception. 
	 * @param txt Messaggio di errore.
	 */
	 public OutOfRangeSampleSize(final String txt) {
		super(txt);
	 }
	
}
