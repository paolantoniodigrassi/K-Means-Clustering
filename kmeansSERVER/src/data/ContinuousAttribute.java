package data;

/**
 * Classe concreta che estende la classe Attribute e modella un attributo
 * continuo (numerico).
 * Tale classe include i metodi per la "normalizzazione" del dominio
 * dell'attributo nell'intervallo [0,1]
 * al fine da rendere confrontabili attributi aventi domini diversi.
 */
class ContinuousAttribute extends Attribute {

	/**
	 * Estremo superiore del dominio che l'attributo può assumere.
	 */
	private double max;
	/**
	 * Estremo inferiore del dominio che l'attributo può assumere.
	 */
	private double min;

	/**
	 * Costruttore della classe che inizializza gli attributi della classe.
	 * Invoca il costruttore della superclasse.
	 * 
	 * @param name  Nome dell'attributo.
	 * @param index Indice dell'attributo.
	 * @param min   Valore minimo dell'intervallo del dominio che l'attributo può
	 *              assumere.
	 * @param max   Valore massimo dell'intervallo del dominio che l'attributo può
	 *              assumere.
	 */
	ContinuousAttribute(final String name, final int index, final double min, final double max) {
		super(name, index);
		this.max = max;
		this.min = min;
	}

	/**
	 * Metodo che calcola e restituisce il valore normalizzato del parametro.
	 * 
	 * @param v Valore dell'attributo da normalizzare.
	 * @return Valore normalizzato dell'attributo.
	 */
	double getScaledValue(final double v) {
		return (v - min) / (max - min);
	}

	/**
	 * Metodo che sovrascrive il metodo equals di Object in modo da
	 * verificare l'uguaglianza tra due ContinuousAttribute.
	 * 
	 * @param o Oggetto da confrontare.
	 * @return true se i ContinuousAttribute sono uguali, false altrimenti.
	 */
	@Override
	public boolean equals(final Object o) {
		ContinuousAttribute a = (ContinuousAttribute) o;
		return (a.min - min < 0.0001) && (a.max - max < 0.0001) &&
				a.getName().equals(getName()) && a.getIndex() == getIndex();
	}
}
