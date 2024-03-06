package data;

import java.util.Set;
import java.io.Serializable;

/**
 * Classe astratta che modella un generico Item 
 * coppia attributo-valore.
 */
public abstract class Item implements Serializable {

    /**
     * Attributo coinvolto nell'item.
     */
    private Attribute attribute;
    /**
     * Valore assegnato all'attributo.
     */
    private Object value;

    /**
     * Costruttore della classe che inizializza la coppia attributo-valore.
     * @param attribute Attributo.
     * @param value Valore dell'attributo.
     */
    Item(final Attribute attribute, final Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * Metodo che restituisce l'attributo.
     * @return Attributo.
     */
    Attribute getAttribute() {
        return attribute;
    }

    /**
     * Metodo che restituisce il valore dell'attributo.
     * @return Valore dell'attributo.
     */
    Object getValue() {
        return value;
    }

    /**
     * Metodo che restituisce una stringa che descrive il valore assegnato all'item.
     * @return Stringa.
     */
    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Metodo astratto la cui implementazione sarà 
     * diversa per item discreto e item continuo.
     * @param a Oggetto di tipo Object.
     * @return Distanza tra Item.
     */
    abstract double distance(Object a);

    /**
	 * Modifica il membro value, assegnandogli il valore
	 * restituito dal metodo computePrototype della classe Data.
	 * @param data riferimento a Data
	 * @param clusteredData insieme di indici delle righe della matrice in data che formano il cluster.
	 */
    public void update(final Data data, final Set < Integer > clusteredData) {
        value = data.computePrototype(clusteredData, attribute);
    }
}
