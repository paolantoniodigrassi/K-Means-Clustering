package data;
import java.io.Serializable;

/**
 * Classe astratta che modella l'entità attributo.
 */
abstract class Attribute implements Serializable {
	
	/**
	 * Nome simbolico dell'attributo.
	 */
	private String name;
	/**
	 * Indice dell'attributo.
	 */
	private int index;
	
	/**
	 * Costruttore di classe che inizializza gli attributi name e index.
	 * @param name nome dell'attributo.
	 * @param index indice dell'attributo.
	 */
	Attribute(final String name, final int index) {
		this.name = name;
		this.index = index;
	}
	
	/**
	 * Metodo che restituisce il nome dell'attributo.
	 * @return Nome dell'attributo.
	 */
	String getName() {
		return name;
	}
	
	/**
	 * Metodo che restituisce l'indice dell'attributo.
	 * @return Indice dell'attributo.
	 */
	int getIndex() {
		return index;
	}
	
	/**
	 * Metodo che restituisce una stringa che rappresenta l'attributo.
	 * @return Stringa contenente il nome dell'attributo.
	 */
	@Override
	public String toString() {
		return name; 
	}
}
