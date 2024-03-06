package mining;

import data.*;
import java.io.*;

/**
 * Classe concrete che implementa Serializable e che
 * implementa l'algoritmo K-means per la clustering dei dati.
 */
public class KMeansMiner implements Serializable{

    /**
     * Oggetto cluster su cui applicare kmeans.
     */
    private ClusterSet C;

    /**
     * Costruttore della classe che inizializza il ClusterSet C
     * @param k Intero che rappresenta la dimensione del ClusterSet.
     * @throws OutOfRangeSampleSize Eccezione scaturita qualora l'intero 
     * inserito dall'utente non rispetti il range dichiarato.
     */
    public KMeansMiner(final int k) throws OutOfRangeSampleSize {
        C = new ClusterSet(k);
    }

    /**
     * Costruttore che apre il file identificato da fileName,
     * legge il ClusterSet e lo assegna a C.
     * @param fileName Nome del file da aprire.
     * @throws FileNotFoundException Eccezione scaturita qualora il file non venga trovato.
     * @throws IOException Eccezione scaturita qualora si verifichino errori di input/output.
     * @throws ClassNotFoundException Eccezione scaturita qualora la classe non venga trovata.
     */
    public KMeansMiner(final String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
        C = (ClusterSet) in.readObject();
        in.close();
    }

    /**
     * Metodo che apre il file identificato da fileName,
     * e salva il ClusterSet C nel file.
     * @param fileName Nome del file da aprire.
     * @throws FileNotFoundException Eccezione scaturita qualora il file non venga trovato.
     * @throws IOException Eccezione scaturita qualora si verifichino errori di input/output.
     */
    public void SaveKMeansMiner(final String fileName) throws FileNotFoundException, IOException {
        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
        out.writeObject(C);
        out.close();
    }

    /**
     * Metodo che restituisce il ClusterSet C.
     * @return ClusterSet.
     */
    public ClusterSet getC() {
        return C;
    }

    /**
     * Metodo che implementa l'algoritmo K-means per la clustering dei dati, eseguendo i seguenti passi:
     * 1) Scelta casuale di centroidi per k cluster;
	 * 2) Assegnazione di ciascuna riga della matrice al cluster avente centroide più vicino all'esempio;
	 * 3) Calcolo dei nuovi centroidi per ciascun cluster;
	 * 4) Ripete i passi 2 e 3 finchè due iterazioni consecutive non restituiscono centroidi uguali.
     * @throws OutOfRangeSampleSize Eccezione scaturita qualora l'intero inserito dall'utente non rispetti il range dichiarato.
     * @param data Insieme di dati su cui applicare il k-means.
     * @return Numero di iterazioni effettuate durante l'esecuzione del k-means.
     */
    public int kmeans(Data data) throws OutOfRangeSampleSize {
        int numberOfIterations = 0;
        //STEP 1
        C.initializeCentroids(data);
        boolean changedCluster = false;
        do {
            numberOfIterations++;
            //STEP 2
            changedCluster = false;
            for (int i = 0; i < data.getNumberOfExamples(); i++) {
                Cluster nearestCluster = C.nearestCluster(data.getItemSet(i));
                Cluster oldCluster = C.currentCluster(i);
                boolean currentChange = nearestCluster.addData(i);
                if (currentChange)
                    changedCluster = true;
                //rimuovo la tupla dal vecchio cluster
                if (currentChange && oldCluster != null)
                    //il nodo va rimosso dal suo vecchio cluster
                    oldCluster.removeTuple(i);
            }
            //STEP 3
            C.updateCentroids(data);
        } while (changedCluster);
        return numberOfIterations;
    }
}