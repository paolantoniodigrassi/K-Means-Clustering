package client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import keyboardinput.Keyboard;

/**
 * Classe che implementa il client per il programma di clustering.
 */
class MainTest {

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
     * @param ip Indirizzo ip del server.
     * @param port Porta del server.
     * @throws IOException Eccezione lanciata in caso di errore di connessione.
     */
    public MainTest(final String ip, final int port) throws IOException {
        InetAddress addr = InetAddress.getByName(ip);
        System.out.println("\nINFO DI CONNESSIONE:");
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, port);
        System.out.println(socket);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream()); // stream con richieste del client
    }

    /**
     * Metodo che comunica al server quale tabella del database utilizzare.
     * @throws SocketException Eccezione lanciata in caso di errore di connessione.
     * @throws ServerException Eccezione lanciata in caso di errore di esecuzione del server.
     * @throws IOException Eccezione lanciata in caso di errore di I/O.
     * @throws ClassNotFoundException Eccezione lanciata in caso di errore di caricamento di una classe.
     */
    private void storeTableFromDb() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(0);
        System.out.print("Nome tabella: ");
        String tabName = Keyboard.readString();
        out.writeObject(tabName);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    /**
     * Metodo che comunica al server di iniziare l'operazione di clustering dalla tabella
     * precedentemente memorizzata.
     * @throws SocketException Eccezione lanciata in caso di errore di connessione.
     * @throws ServerException Eccezione lanciata in caso di errore di esecuzione del server.
     * @throws IOException Eccezione lanciata in caso di errore di I/O.
     * @throws ClassNotFoundException Eccezione lanciata in caso di errore di caricamento di una classe.
     * @return Stringa che rappresenta i cluster.
     */
    private String learningFromDbTable() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(1);
        System.out.print("Numero di cluster: ");
        int k = Keyboard.readInt();
        out.writeObject(k);
        String result = (String) in.readObject();
        if (result.equals("OK")) {
            System.out.println("\nNumero iterazioni: " + in.readObject());
            return (String) in.readObject();
        } else throw new ServerException(result);

    }

    /**
     * Metodo che comuniva al server di salvare i dati clusterizzati in un file.
     * @throws SocketException Eccezione lanciata in caso di errore di connessione.
     * @throws ServerException Eccezione lanciata in caso di errore di esecuzione del server.
     * @throws IOException Eccezione lanciata in caso di errore di I/O.
     * @throws ClassNotFoundException Eccezione lanciata in caso di errore di caricamento di una classe.
     */
    private void storeClusterInFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(2);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    /**
     * Metodo che comunica al server di leggere il file.
     * @throws SocketException Eccezione lanciata in caso di errore di connessione.
     * @throws ServerException Eccezione lanciata in caso di errore di esecuzione del server.
     * @throws IOException Eccezione lanciata in caso di errore di I/O.
     * @throws ClassNotFoundException Eccezione lanciata in caso di errore di caricamento di una classe.
     * @return Stringa che rappresenta i cluster.
     */
    private String learningFromFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(3);
        System.out.print("Nome tabella: ");
        String tabName = Keyboard.readString();
        out.writeObject(tabName);
        System.out.print("Numero di cluster:");
		int k=Keyboard.readInt();
		out.writeObject(k);
        String result = (String) in.readObject();
        if (result.equals("OK"))
            return (String) in.readObject();
        else throw new ServerException(result);
    }

    /**
     * Metodo che chiude la connessione con il server.
     * @throws IOException Eccezione lanciata in caso di errore di I/O.
     */
    private void closeConnection() throws IOException{ 
       out.writeObject(4);
    }

    /**
     * Metodo che stampa il menu e legge la risposta dell'utente.
     * @return Risposta dell'utente.
     */
    private int menu() {
        int answer;
        System.out.println("Scegli una opzione:");
        do {
            System.out.println("(1) Carica Cluster da File");
            System.out.println("(2) Carica Dati");
            System.out.print("Risposta: ");
            answer = Keyboard.readInt();
            System.out.println();
        }
        while (answer <= 0 || answer > 2);
        return answer;
    }

    /**
     * Metodo main che crea un'istanza della classe MainTest e 
     * gestisce le operazioni del client.
     */
    public static void main(String[] args) {
        String answer = "y";
        String ip;
        int port;
        if (args.length > 0) {
            ip = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            ip = "127.0.0.1";
            port = 8083;
        }
        MainTest main = null;
        try {
            main = new MainTest(ip, port);
            System.out.println("+++ Connessione avvenuta +++\n");
        } catch (IOException e) {
            System.out.println("--- Connessione al server fallita, assicurati che sia online ---\n");
            return;
        }

        do {
            int menuAnswer = main.menu();
            switch (menuAnswer) {
            case 1:
                boolean flag = true;
                do {
                    try {
                        String kmeans = main.learningFromFile();
                        System.out.println("\n" + kmeans);
                        flag = true;
                    } catch (SocketException e) {
                        System.out.println(e);
                        return;
                    } catch (IOException e) {
                        System.out.println(e);
                        return;
                    } catch (ClassNotFoundException e) {
                        System.out.println(e);
                        return;
                    } catch (ServerException e) {
                        System.out.println("\n--- " + e.getMessage() + " ---\n");
                        flag = false;
                    }
                } while (!flag);
                break;
            case 2:
                while (true) {
                    try {
                        main.storeTableFromDb();
                        break; 
                    } catch (SocketException e) {
                        System.out.println(e);
                        return;
                    } catch (IOException e) {
                        System.out.println(e);
                        return;
                    } catch (ClassNotFoundException e) {
                        System.out.println(e);
                        return;
                    } catch (ServerException e) {
                    	System.out.println("\n--- " + e.getMessage() + " ---\n");
                    }
                }

                do {
                    try {
                        String clusterSet = main.learningFromDbTable();
                        System.out.println(clusterSet);

                        main.storeClusterInFile();
                        System.out.println("File salvato");
                        flag = true;
                    } catch (SocketException e) {
                        System.out.println(e);
                        return;
                    } catch (ClassNotFoundException e) {
                        System.out.println(e);
                        return;
                    } catch (IOException e) {
                        System.out.println(e);
                        return;
                    } catch (ServerException e) {
                    	System.out.println("\n--- " + e.getMessage() + " ---\n");
                        flag = false;
                    }
                    if (flag) {
                        System.out.println("Vuoi ripetere l'esecuzione?(y/n)");
                        do {
                            answer = Keyboard.readString();
                        } while (!answer.matches("[y|n]"));
                    }
                } while (answer.equals("y") || !flag);
                break; //fine case 2
            }
            System.out.println("Vuoi scegliere una nuova operazione da menu?(y/n)");
            do {
                answer = Keyboard.readString();
            } while (!answer.matches("[y|n]"));
            if (answer.equals("n")) {
                try {
                    main.closeConnection();
                } catch (IOException e) {
                    System.out.println(e);
                }
                break;
            }
        }
        while (true);
    }
}