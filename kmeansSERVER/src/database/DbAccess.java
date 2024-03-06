package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe che realizza la connessione al database MySQL.
 */
public class DbAccess {

    /**
     * Nome del driver da utilizzare per la connessione e il caricamento del database.
     */
    private static String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    /**
     * Nome del DBMS utilizzato.
     */
    private final String DBMS = "jdbc:mysql";

    /**
     * Porta su cui è in ascolto il server MySQL.
     */
    private final int PORT = 3306;

    /**
     * Nome del server su cui è in esecuzione il DBMS.
     */
    private String SERVER = "localhost";

    /**
     * Nome del database da utilizzare.
     */
    private String DATABASE = "MapDB";

    /**
     * Nome utente per la connessione al database.
     */
    private String USER_ID = "MapUser";

    /**
     * Password di autenticazione per l'utente.
     */
    private String PASSWORD = "map";

    /**
     * Oggetto che gestisce la connessione al database.
     */
    private Connection conn;

    /**
	 * Metodo che impartisce al class loader l'ordine di caricare il driver mysql, inizializza
	 * la connessione riferita da conn. Il metodo solleva e propaga una eccezione di
	 * tipo DatabaseConnectionException in caso di fallimento nella connessione al
	 * database.
	 * @throws DatabaseConnectionException Problemi di connessione al database.
	 */
    public void initConnection() throws DatabaseConnectionException {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE +
            "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
        System.out.println("Connessione in: " + connectionString);
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            throw new DatabaseConnectionException(
                "SQLException: " + e.getMessage());
        }
    }

    /**
     * Metodo che restituisce l'oggetto conn che rappresenta la connessione al database.
     * @return Oggetto che rappresenta la connessione al database.
     */
    Connection getConnection() {
        return conn;
    }

    /**
     * Metodo che chiude la connessione al database.
     * @throws DatabaseConnectionException Problemi di connessione al database.
     */
    public void closeConnection() throws DatabaseConnectionException {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseConnectionException
            	("Errore durante la chiusura della connessione: " + e.getMessage());
        }
    }
}
