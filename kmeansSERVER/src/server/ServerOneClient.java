package server;

import data.Data;
import data.OutOfRangeSampleSize;
import mining.KMeansMiner;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValueException;

/** 
 * Classe concreta che estende Thread e gestisce il comportamento
 * del server in attesa di richieste di connessione da parte dei client.
 */
public class ServerOneClient extends Thread{
	/**
	 * Socket per la comunicazione con il client.
	 */
	private Socket socket;
	/**
	 * Stream di input.
	 */
	private ObjectInputStream in;
	/**
	 * Stream di output.
	 */
	private ObjectOutputStream out;
	/**
	 * Oggetto di tipo KMeansMiner.
	 */
	private KMeansMiner kmeans;
	
	/**
	 * Costruttore della classe che inizializza 
	 * gli attributi socket, gli stream di input e output
	 * e avvia il thread.
	 * @param s Socket per la comunicazione con il client.
	 * @throws IOException Eccezione di I/O.
	 */
	public ServerOneClient(final Socket s) throws IOException {
		socket = s;
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		start();
	}
	
	/**
	 * Metodo che esegue la richiesta del client.
	 */
	@Override
	public void run() {
		String tabName = null;
		Data data = null;
		int k = 0;
		String ip = socket.getInetAddress().getHostAddress();
		String info = "\n[" + getName() + ": " + ip + "] ";
		try {
			while (true) {
				int command = (Integer) in.readObject();
				switch (command) {
				case 0:
					tabName = (String) in.readObject();
					try {
						System.out.print(info);
						data = new Data(tabName);
						out.writeObject("OK");
						System.out.println(info + "Lettura tabella da DB");
					} catch (final EmptySetException e) {
						out.writeObject("Tabella vuota, sceglierne un'altra");
					} catch (SQLException e) {
						out.writeObject("Tabella non trovata, sceglierne un'altra");
					} catch (DatabaseConnectionException | NoValueException e) {
						out.writeObject(e.getMessage());
					}
					break;
				case 1:
					k = (Integer) in.readObject();
					int numIter = 0;
					try {
						kmeans=new KMeansMiner(k);
						numIter=kmeans.kmeans(data);
						System.out.println(info + "Clusterizzazione dati");
						out.writeObject("OK");
						out.writeObject(numIter);
						out.writeObject("\n" + kmeans.getC().toString(data));
					} catch (OutOfRangeSampleSize e) {
						out.writeObject(e.getMessage());
					}
					break;
				case 2:
					try {
	                    kmeans.SaveKMeansMiner(tabName + "_" + k + ".dat");
	                    out.writeObject("OK");
	                    System.out.println(info + tabName + "_" + k + ".dat: file salvato");
	                } catch (FileNotFoundException e) {
	                	out.writeObject("File non trovato");
	                } catch (IOException e) {
	                	out.writeObject("Errore di input");
	                }
					break;
				case 3:
					tabName = (String) in.readObject();
					k = (Integer) in.readObject();
					try {
						System.out.print(info);
						data = new Data(tabName);
						kmeans = new KMeansMiner(tabName + "_" + k+ ".dat");
		                out.writeObject("OK");
						out.writeObject(kmeans.getC().toString(data));
						System.out.println(info + tabName + "_" + k + ".dat: file letto");
					} catch (EmptySetException e) {
						out.writeObject("Tabella vuota, sceglierne un'altra");
					} catch (SQLException e) {
						out.writeObject("Tabella non trovata, sceglierne un'altra");
					} catch (DatabaseConnectionException | NoValueException e) {
						out.writeObject(e.getMessage());
					} catch (FileNotFoundException e) {
	                	out.writeObject("File non trovato");
	                } catch (IOException e) {
	                	out.writeObject("Errore di input/output");
	                } catch (ClassNotFoundException e) {
	                	out.writeObject("Classe non disponibile");
	                } 
					break;
				case 4:
					return;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			//chiusura socket attraverso finally
		} finally {
			try {
				socket.close();
				System.out.println("\n+++ [" + getName() + ": " + ip + "] Socket chiuso +++");
			} catch(IOException e) {
				System.err.println("\n--- [" + getName() + ": " + ip + "] Socket non chiuso ---");
			}
		}
	}
}
