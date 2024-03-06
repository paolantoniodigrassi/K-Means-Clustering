package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
@SuppressWarnings("resource")

/**
 * Classe che implementa il client per il programma di clustering.
 */
public class Client {
	
    /**
     * Stream di output verso il server.
     */
    private ObjectOutputStream out;
    /**
     * Stream di input dal server.
     */
    private ObjectInputStream in ;

    /**
     * Costruttore della classe che inizializza gli stream di input e output e si connette al server.
     * @throws IOException Eccezione lanciata in caso di errore di connessione.
     */
    public Client(String ip, int port) throws IOException {
    	InetAddress addr = InetAddress.getByName(ip);
		Socket socket = new Socket(addr, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Metodo che comunica al server quale tabella del database utilizzare.
     * @param tabName nome della tabella.
     * @throws SocketException Eccezione lanciata in caso di errore di connessione.
     * @throws ServerException Eccezione lanciata in caso di errore di esecuzione del server.
     * @throws IOException Eccezione lanciata in caso di errore di I/O.
     * @throws ClassNotFoundException Eccezione lanciata in caso di errore di caricamento di una classe.
     */
    public void storeTableFromDb(String tabName) throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(0);
        out.writeObject(tabName);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    /**
     * Metodo che comunica al server di iniziare l'operazione di clustering dalla tabella
     * precedentemente memorizzata.
     * @param k numero di cluster scelti
     * @return stringa rappresentativa dei dati elaborati.
     * @throws SocketException Eccezione lanciata in caso di errore di connessione.
     * @throws ServerException Eccezione lanciata in caso di errore di esecuzione del server.
     * @throws IOException Eccezione lanciata in caso di errore di I/O.
     * @throws ClassNotFoundException Eccezione lanciata in caso di errore di caricamento di una classe.
     */
    public String learningFromDbTable(int k) throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(1);
        out.writeObject(k);
        String result = (String) in.readObject();
        if (result.equals("OK")) {
            String it = "\nNumero iterazioni: " + in.readObject() + "\n";
            return it + (String) in.readObject();
        } else throw new ServerException(result);
    }

    /**
     * Metodo che comuniva al server di salvare i dati clusterizzati in un file.
     * @throws SocketException Eccezione lanciata in caso di errore di connessione.
     * @throws ServerException Eccezione lanciata in caso di errore di esecuzione del server.
     * @throws IOException Eccezione lanciata in caso di errore di I/O.
     * @throws ClassNotFoundException Eccezione lanciata in caso di errore di caricamento di una classe.
     */
    public void storeClusterInFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(2);

        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    /**
     * Metodo che comunica al server di leggere il file.
     * @param tabName nome della tabella del database.
     * @param k numero di cluster nel file.
     * @return stringa rappresentativa dei dati letti dal file.
     * @throws SocketException Eccezione lanciata in caso di errore di connessione.
     * @throws ServerException Eccezione lanciata in caso di errore di esecuzione del server.
     * @throws IOException Eccezione lanciata in caso di errore di I/O.
     * @throws ClassNotFoundException Eccezione lanciata in caso di errore di caricamento di una classe.
     */
    public String learningFromFile(String tabName, int k) throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(3);
        out.writeObject(tabName);
		out.writeObject(k);
        String result = (String) in.readObject();
        if (result.equals("OK"))
            return (String) in.readObject();
        else throw new ServerException(result);
    }
}