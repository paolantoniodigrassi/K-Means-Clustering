package database;

import java.sql.ResultSet;
import java.util.HashSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import database.TableSchema.Column;

public class TableData {

    /**
     * Oggetto di tipo DbAccess che rappresenta la connessione al database.
     */
    private DbAccess db;

    /**
     * Costruttore della classe che inizializza l'oggetto di tipo DbAccess.
     * @param db Gestore del'accesso al database.
     */
    public TableData(final DbAccess db) {
        this.db = db;
    }

    /**
     * Metodo che ricava lo schema della tabella, esegue la query e restituisce
     * la lista di oggetti di tipo Example.
     * @param table Nome della tabella.
     * @return Lista di oggetti di tipo Example.
     * @throws SQLException Eccezione SQL generica.
     * @throws EmptySetException Eccezione che viene lanciata quando la tabella e' vuota.
     */
    public List < Example > getDistinctTransazioni(final String table)
    		throws SQLException, EmptySetException {
    	List < Example > list = new ArrayList < > ();
        TableSchema ts = new TableSchema(db, table);
        Statement s = db.getConnection().createStatement();
        ResultSet rs = s.executeQuery("SELECT DISTINCT * " 
        											+ "FROM " + table + ";");
        while (rs.next()) {
            Example ex = new Example();
            for (int i = 0; i < ts.getNumberOfAttributes(); i++) {
                if (ts.getColumn(i).isNumber()) 
                	ex.add(rs.getDouble(ts.getColumn(i).getColumnName()));
                else 
                	ex.add(rs.getString(ts.getColumn(i).getColumnName()));
            }
            list.add(ex);
        }
        s.close();
        rs.close();
        if (list.isEmpty()) 
        	throw new EmptySetException("Tabella " + table + " vuota");
        return list;
    }

    /**
     * Metodo che formula ed esegue un'interrogazione SQL 
     * per estrarre i valori distinti ordinati di una colonna
	 * e popola un insieme da restituire.
     * @param table Nome della tabella.
     * @param column Colonna della tabella.
     * @return Insieme di valori distinti per l'attributo identificato dal nome della colonna.
     * @throws SQLException Eccezione SQL generica.
     */
    public Set < Object > getDistinctColumnValues(final String table, final Column column) throws SQLException {
    	HashSet < Object > set = new HashSet < > ();
        Statement s = db.getConnection().createStatement();
        ResultSet rs = s.executeQuery("SELECT DISTINCT " + column.getColumnName() + " " 
        											+ "FROM " + table + " " 
        											+ "ORDER BY " + column.getColumnName() + ";");
        while (rs.next()) {
            if (column.isNumber()) 
            	set.add(rs.getDouble(column.getColumnName()));
            else 
            	set.add(rs.getString(column.getColumnName()));
        }
        s.close();
        rs.close();
        return set;
    }

    /**
     * Metodo che formula ed esegue una interrogazione SQL 
     * per estrarre il valore aggregato (valore minimo o valore massimo)
	 * cercato nella colonna di nome column della tabella di nome table.
     * @param table Nome della tabella.
     * @param column Colonna della tabella.
     * @param aggregate Operatore SQL di aggregazione(MIN, MAX).
     * @return Valore aggregato.
     * @throws SQLException Eccezione SQL generica.
     * @throws NoValueException Eccezione che viene lanciata quando il risultato è vuoto.
     */
    public Object getAggregateColumnValue(final String table, final Column column, final QUERY_TYPE aggregate) 
    		throws SQLException, NoValueException {
        Object ret;
        Statement s = db.getConnection().createStatement();
        ResultSet rs = s.executeQuery("SELECT " + aggregate + "(" + column.getColumnName() + ") AS aggregata " 
        											+ "FROM " + table + ";");
        try {
            if (rs.next()) {
                if (column.isNumber()) 
                	ret = rs.getDouble("aggregata");
                else 
                	ret = rs.getString("aggregata");
            } else {
            	throw new NoValueException("Nessun valore per la colonna " + column.getColumnName());
            }
        } finally {
            s.close();
            rs.close();
        }
        return ret;
    }
}
