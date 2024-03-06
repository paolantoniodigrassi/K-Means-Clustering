package data;

import java.util.TreeSet;
import java.util.Set;
import java.util.Iterator;

/**
 * Classe concreta che estende la classe Attribute e rappresenta un attributo
 * discreto (categorico).
 */
class DiscreteAttribute extends Attribute implements Iterable<String> {

    /**
     * TreeSet di stringhe che rappresenta i valori che l'attributo può assumere.
     */
    private TreeSet<String> values;

    /**
     * Costruttore della classe che inizializza gli attributi della classe.
     * Invoca il costruttore della superclasse.
     * 
     * @param name   Nome dell'attributo.
     * @param index  Indice dell'attributo.
     * @param values Insieme di stringhe che rappresentano i valori che l'attributo
     *               può assumere.
     */
    DiscreteAttribute(final String name, final int index, final Set<String> values) {
        super(name, index);
        this.values = new TreeSet<String>();
        for (String s : values)
            this.values.add(s);
    }

    /**
     * Metodo che restituisce un iteratore per utilizzabile sulla struttura TreeSet
     * di stringhe.
     * 
     * @return Iteratore.
     */
    @Override
    public Iterator<String> iterator() {
        return values.iterator();
    }

    /**
     * Metodo che restituisce il numero di valori distinti che l'attributo può
     * assumere.
     * 
     * @return Numero di valori discreti nel dominio dell'attributo.
     */
    int getNumberOfDistinctValues() {
        return values.size();
    }

    /**
     * Metodo che determina il numero di volte che il valore discreto compare
     * in corrispondenza dell'attributo corrente (indice di colonna) negli
     * esempi memorizzati in data e indicizzate (per riga) da idList.
     * 
     * @param data   oggetto di tipo Data.
     * @param idList l'insieme degli indici di riga di alcune tuple memorizzate in
     *               data.
     * @param v      valore dell'attributo.
     * @return Numero di occorenze del valore discreto.
     */
    int frequency(final Data data, final Set<Integer> idList, final String v) {
        int count = 0;
        for (int i : idList) {
            if (data.getAttributeValue(i, this.getIndex()).equals(v))
                count++;
        }
        return count;
    }

    /**
     * Metodo che sovrascrive il metodo equals di Object in modo da
     * verificare l'uguaglianza tra due DiscreteAttribute.
     * 
     * @param o Oggetto da confrontare.
     * @return true se i DiscreteAttribute sono uguali, false altrimenti.
     */
    @Override
    public boolean equals(final Object o) {
        DiscreteAttribute a = (DiscreteAttribute) o;
        return a.getName().equals(getName()) &&
                a.getIndex() == getIndex() && a.values.equals(values);
    }
}
