package data;

/**
 * Classe concreta che estende la classe Item e
 * rappresenta una coppia Attributo continuo - valore continuo.
 */
class ContinuousItem extends Item {

	/**
	 * Costruttore di classe che inizializza gli attributi della classe.
	 * Invoca il costruttore della superclasse.
	 * 
	 * @param attribute Elemento di tipo attributo.
	 * @param value     Valore dell'attributo.
	 */
	ContinuousItem(final Attribute attribute, final double value) {
		super(attribute, value);
	}

	/**
	 * Metodo che restituisce 0 se i due item sono uguali, 1 altrimenti.
	 * 
	 * @param a Oggetto di tipo Object.
	 * @return Distanza tra due ContinuousItem.
	 */
	double distance(final Object a) {
		return Math.abs(((ContinuousAttribute) getAttribute()).getScaledValue((Double) getValue())
				- ((ContinuousAttribute) getAttribute()).getScaledValue((Double) a));
	}

	/**
	 * Metodo che sovrascrive il metodo equals di Object in modo da
	 * verificare l'uguaglianza tra due ContinuousItem.
	 * 
	 * @param o Oggetto da confrontare.
	 * @return true se i ContinuousItem sono uguali, false altrimenti.
	 */
	@Override
	public boolean equals(final Object o) {
		ContinuousItem a = (ContinuousItem) o;
		return a.getAttribute().equals(getAttribute()) && a.getValue().equals(getValue());
	}
}
