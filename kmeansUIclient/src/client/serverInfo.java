package client;

/**
 * Classe singleton
 */
public class serverInfo {
	
	/**
	 * instanza della classe;
	 */
	private static serverInfo instance;
	
	/**
	 * Stringa che rappresenta l'ip a cui collegarsi.
	 */
	private static String ip = "127.0.0.1";
	
	/**
	 * Intero che rappresenta la porta su cui il server è in ascolto.
	 */
	private static int port = 8080;
	
	/**
	 * costruttore privato
	 */
	private serverInfo() {
	}
	
	/**
	 * Imposta i valori in input per ip e port
	 * @param ip stringa che rappresenta un ip
	 * @param port intero che rappresenta la porta
	 */
	public static void setServerInfo(String ip, int port) {
		serverInfo.ip = ip;
		serverInfo.port = port;
	}
	
	public static serverInfo getInstance() {
		if (instance == null) {
			instance = new serverInfo(); 
		}
		return instance;
	}
	
	/**
	 * Metodo get
	 * @return port ip serv
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * Metodo get
	 * @return ip stringa server
	 */
	public String getIP() {
		return ip;
	}
}