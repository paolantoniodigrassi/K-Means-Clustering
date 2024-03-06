package mining;

import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;
import data.*;

/**
 * Classe concreta che implementa l'interfaccia Serializable
 * e modella un cluster.
 */
class Cluster implements Serializable {

    /**
     * Oggetto di Tupla
     */
    private Tuple centroid;

    /**
     * Oggetto che rappresenta un insieme di interi
     */
    private Set<Integer> clusteredData;

    /**
     * Costruttore che assegna il centroide e crea un insieme di interi.
     * 
     * @param centroid centroide del cluster
     */
    Cluster(final Tuple centroid) {
        this.centroid = centroid;
        clusteredData = new HashSet<Integer>();

    }

    /**
     * Metodo che restituisce il centroide del cluster.
     * 
     * @return Centroide del cluster.
     */
    Tuple getCentroid() {
        return centroid;
    }

    /**
     * Metodo che assegna il centroide del cluster.
     * 
     * @param data Riferimento all'oggetto matrice.
     */
    void computeCentroid(final Data data) {
        for (int i = 0; i < centroid.getLength(); i++)
            centroid.get(i).update(data, clusteredData);
    }

    /**
     * Metodo che aggiunge una tupla al Cluster.
     * 
     * @param id Identificativo da inserire in clusteredData.
     * @return Valore booleano se la tupla è stata inserita.
     */
    boolean addData(final int id) {
        return clusteredData.add(id);
    }

    /**
     * Metodo che verifica se una transazione è clusterizzata nell'array corrente.
     * 
     * @param id Identificatore della tupla di cui si vuole effettuare la verifica.
     * @return Valore booleano se è presente.
     */
    boolean contain(final int id) {
        return clusteredData.contains(id);
    }

    /**
     * Metodo che rimuove la tupla che ha cambiato il cluster.
     * 
     * @param id Identificatore della tupla da rimuovere.
     */
    void removeTuple(final int id) {
        clusteredData.remove(id);
    }

    /**
     * Metodo che restituisce una stringa contenente il risultato della
     * clusterizzazione.
     * 
     * @param data Riferimento all'oggetto che contiene i dati.
     * @return Stringa contenente il risultato della clusterizzazione.
     */
    public String toString(final Data data) {
        String str = "Centroid = (";
        for (int i = 0; i < centroid.getLength(); i++) {
            str += centroid.get(i);
            if (i < centroid.getLength() - 1) {
                str += " ";
            }
        }
        str += ")\nExamples:\n";
        for (int i : clusteredData) {
            str += "[";
            for (int j = 0; j < data.getNumberOfExplanatoryAttributes(); j++) {
                str += data.getAttributeValue(i, j);
                if (j < data.getNumberOfExplanatoryAttributes() - 1) {
                    str += " ";
                }
            }
            str += "] dist=" + getCentroid().getDistance(data.getItemSet(i)) + "\n";
        }
        str += "AvgDistance=" + getCentroid().avgDistance(data, clusteredData) + "\n";
        return str;
    }

    /**
     * Metodo che sovrascrive il metodo equals della classe Object
     * per effettuare un confronto di uguaglianza tra due Cluster.
     * 
     * @param obj Oggetto da confrontare.
     * @return true se i due Cluster sono uguali, false altrimenti.
     */
    @Override
    public boolean equals(final Object obj) {
        Cluster c = (Cluster) obj;
        return centroid.equals(c.centroid) && clusteredData.equals(c.clusteredData);
    }

}
