package data;

import java.util.Set;
import java.io.Serializable;

/**
 * Classe concreta Tuple che rappresenta una tupla
 * come sequenza di coppie Attributo - valore.
 */
public class Tuple implements Serializable {

    /**
     * Array di Item.
     */
    private Item[] tuple;

    /**
     * Costruttore della classe che inizializza la dimensione della tupla.
     * 
     * @param size Dimensione della tupla.
     */
    Tuple(final int size) {
        tuple = new Item[size];
    }

    /**
     * Metodo che restituisce il numero di item che costituisce la tupla.
     * 
     * @return Numero di item che costituisce la tupla.
     */
    public int getLength() {
        return tuple.length;
    }

    /**
     * Metodo che restituisce l'item in posizione i.
     * 
     * @param i Indice dell'array.
     * @return Item in posizione i.
     */
    public Item get(final int i) {
        return tuple[i];
    }

    /**
     * Metodo che inserisce l'item c in posizione i.
     * 
     * @param c Item da inserire.
     * @param i Indice dell'array.
     */
    void add(final Item c, final int i) {
        tuple[i] = c;
    }

    /**
     * Metodo che determina la distanza tra la tupla
     * riferita da obj e la tupla corrente.
     * La distanza è ottenuta come la somma delle
     * distanze tra gli item in posizioni eguali nelle due tuple.
     * 
     * @param obj Tupla da cui calcolare la distanza.
     * @return Distanza tra le due tuple.
     */
    public double getDistance(final Tuple obj) {
        double distance = 0.0;
        for (int i = 0; i < getLength(); i++) {
            distance += tuple[i].distance(obj.get(i).getValue());
        }
        return distance;
    }

    /**
     * Metodo che restituisce la media delle distanze
     * tra la tupla corrente e quelle ottenibili dall'insieme
     * di dati aventi indice in clusteredData.
     * 
     * @param data          Riferimento all'oggetto data.
     * @param clusteredData Insieme di indici di posizione in data.
     * @return Media delle distanze.
     */
    public double avgDistance(final Data data, final Set<Integer> clusteredData) {
        double p = 0.0, sumD = 0.0;
        for (int i : clusteredData) {
            double d = getDistance(data.getItemSet(i));
            sumD += d;
        }
        p = sumD / clusteredData.size();
        return p;
    }

    /**
     * Metodo che sovrascrive il metodo equals della classe Object
     * per effettuare un confronto tra due tuple.
     * 
     * @param obj Tupla da confrontare.
     * @return true se le due tuple sono uguali, false altrimenti.
     */
    @Override
    public boolean equals(final Object obj) {
        if (getLength() != ((Tuple) obj).getLength())
            return false;
        for (int i = 0; i < getLength(); i++) {
            if (!tuple[i].equals(((Tuple) obj).get(i)))
                return false;
        }
        return true;
    }
}