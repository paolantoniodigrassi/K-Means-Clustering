package mining;

import data.*;
import java.io.Serializable;

/**
 * Classe che implementa l'interfaccia Serializable
 * e memorizza tutti i cluster creati.
 */
public class ClusterSet implements Serializable {

	/**
	 * Array di oggetti cluster.
	 */
	private Cluster C[];

	/**
	 * Indice di posizione all'interno dell'array C.
	 */
	private int i = 0;

	/**
	 * Costruttore della classe ClusterSet che inizializza l'array C[].
	 * 
	 * @throws OutOfRangeSampleSize, eccezione scaturita qualora
	 *                               l'intero inserito dall'utente non rispetti
	 *                               il range dichiarato
	 * @param k intero raffigurante la dimensione di C[]
	 */
	ClusterSet(final int k) throws OutOfRangeSampleSize {
		if (k < 0) {
			throw new OutOfRangeSampleSize("Valore inserito non valido: non puo' essere negativo");
		}
		C = new Cluster[k];
	}

	/**
	 * Metodo che aggiunge un elemento all'interno di C[]
	 * in posizione i incrementadola di 1.
	 * 
	 * @param c cluster da inserire.
	 */
	void add(final Cluster c) {
		C[i] = c;
		i++;
	}

	/**
	 * Metodo che restituisce il cluster presente in posizione i.
	 * 
	 * @param i Intero che rappresenta la posizione.
	 * @return C[i], elemento i-esimo di C.
	 */
	Cluster get(final int i) {
		return C[i];
	}

	/**
	 * Metodo che inizializza i centroidi necessari per la creazione dei cluster.
	 * 
	 * @param data Insieme di dati da clusterizzare.
	 * @throws OutOfRangeSampleSize Eccezione scaturita qualora l'intero inserito
	 *                              dall'utente non rispetti il range dichiarato
	 */
	void initializeCentroids(final Data data) throws OutOfRangeSampleSize {
		int[] centroidIndexes = data.sampling(C.length);
		for (int i = 0; i < centroidIndexes.length; i++) {
			Tuple centroidI = data.getItemSet(centroidIndexes[i]);
			add(new Cluster(centroidI));
		}
	}

	/**
	 * Metodo che indica il cluster più vicino.
	 * 
	 * @param tuple Tupla su cui effettuare il controllo
	 * @return Cluster più vicino
	 */
	Cluster nearestCluster(final Tuple tuple) {
		double min = tuple.getDistance(C[0].getCentroid());
		double tmp;
		Cluster nearest = C[0];
		for (int i = 1; i < C.length; i++) {
			tmp = tuple.getDistance(C[i].getCentroid());
			if (tmp < min) {
				min = tmp;
				nearest = C[i];
			}
		}
		return nearest;
	}

	/**
	 * Metodo che restituisce il cluster corrente
	 * a cui appartiene un determinato elemento.
	 * 
	 * @param id Intero che rappresenta l'identificativo dell'elemento.
	 * @return I-esimo elemento di C.
	 */
	Cluster currentCluster(final int id) {
		for (int i = 0; i < C.length; i++) {
			if (C[i].contain(id)) {
				return C[i];
			}
		}
		return null;
	}

	/**
	 * Metodo che aggiorna i centroidi.
	 * 
	 * @param data Dati su cui calcolare i centroidi.
	 */
	void updateCentroids(Data data) {
		for (int i = 0; i < C.length; i++) {
			C[i].computeCentroid(data);
		}
	}

	/**
	 * Override di toString di Object, rappresenta testualmente gli elementi
	 * dell'array C.
	 * 
	 * @param data, insieme di dati
	 * @return str, stringa utilizzata
	 */
	public String toString(final Data data) {
		String str = "";
		for (int i = 0; i < C.length; i++) {
			if (C[i] != null) {
				str += (i + 1) + ": " + C[i].toString(data) + "\n";
			}
		}
		return str;
	}

	/**
	 * Metodo che sovrascrive il metodo equals di Object in modo da
	 * verificare l'uguaglianza tra due ClusterSet.
	 * 
	 * @param obj Oggetto da confrontare.
	 * @return true se i due ClusterSet sono uguali, false altrimenti.
	 */
	public boolean equals(Object obj) {
		ClusterSet cs = (ClusterSet) obj;
		return cs.C.equals(C) && cs.i == i;
	}
}
