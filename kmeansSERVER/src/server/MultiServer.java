package server;

import java.net.*;
import java.io.IOException;

/**
 * Classe concreta che gestisce il comportamento di un server 
 * in attesa di richiesta di connessioni da parte dei client.
 */
public class MultiServer {

	/**
	 * Porta su cui il server e' in ascolto.
	 */
	 private static int PORT;
	 
	/**
	 * Costruttore della classe che inizializza la porta 
	 * su cui il server e' in ascolto e avvia il thread.
	 * @param port Porta su cui il server e' in ascolto.
	 */
	public MultiServer(final int port) {
		MultiServer.PORT = port;
		try {
			run();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Metodo che crea un oggetto di tipo MultiServer.
	 * @param args argomenti in input
	 */
	public static void main(String[] args) {
		if(args.length > 0) 
			new MultiServer(Integer.parseInt(args[0]));
		else
			new MultiServer(8080);
	}
	
	/**
	 * Metodo che permette al server di rimanere 
	 * in attesa di richieste di connessione
	 * da parte dei client.
	 * @throws IOException Eccezione di I/O.
	 */
	private void run() throws IOException {
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("+++ Server ON +++");
		try {
			while(true) {
					Socket socket = s.accept();
					try {
							new ServerOneClient(socket);
					} catch(IOException e) {
						socket.close();
					}
				}
		} finally {
			s.close();
		}
	}
}
