package database;

import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe che modella lo schema di una tabella 
 * generica del database.
 */
public class TableSchema {

    /**
     * Lista di oggetti di tipo Column che rappresentano le colonne della tabella.
     */
    private List < Column > tableSchema = new ArrayList < Column > ();
    
    /**
     * Inner class che modella una colonna della tabella.
     * Ogni colonna e' caratterizzata dal tipo del dato e il nome della colonna.
     */
    public class Column {
        /**
         * Nome della colonna.
         */
        private String name;
        /**
         * Tipo del dato della colonna.
         */
        private String type;

        /**
         * Costruttore della classe.
         * @param name nome della colonna.
         * @param type tipo del dato della colonna.
         */
        Column(final String name, final String type) {
            this.name = name;
            this.type = type;
        }

        /**
         * Metodo che restituisce il nome della colonna.
         * @return nome della colonna.
         */
        public String getColumnName() {
            return name;
        }
        
        /**
         * Metodo che verifica se la colonna contiene valori numerici o meno.
         * @return true se la colonna contiene valori numerici, false altrimenti.
         */
        public boolean isNumber() {
            return type.equals("number");
        }
        
        /**
         * Metodo che restituisce una stringa che rappresenta 
         * il nome della colonna e il tipo del dato.
         */
        public String toString() {
            return name + " : " + type;
        }
    }

    /**
     * Metodo che gestisce il mapping tra i tipi di dato SQL e Java.
     * @param db Nome del database.
     * @param tableName Nome della tabella.
     * @throws SQLException eccezione generica SQL.
     */
    public TableSchema(final DbAccess db, final String tableName) throws SQLException {
        HashMap < String, String > mapSQL_JAVATypes = new HashMap < String, String > ();
        //http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
        mapSQL_JAVATypes.put("CHAR", "string");
        mapSQL_JAVATypes.put("VARCHAR", "string");
        mapSQL_JAVATypes.put("LONGVARCHAR", "string");
        mapSQL_JAVATypes.put("BIT", "string");
        mapSQL_JAVATypes.put("SHORT", "number");
        mapSQL_JAVATypes.put("INT", "number");
        mapSQL_JAVATypes.put("LONG", "number");
        mapSQL_JAVATypes.put("FLOAT", "number");
        mapSQL_JAVATypes.put("DOUBLE", "number");
        Connection conn = db.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * " + "FROM " + tableName + " " + "WHERE 1=0;");
        ResultSetMetaData md = rs.getMetaData();
        for (int i = 1; i <= md.getColumnCount(); i++) {
            if (mapSQL_JAVATypes.containsKey(md.getColumnTypeName(i)))
                tableSchema.add(new Column(md.getColumnName(i), mapSQL_JAVATypes.get(md.getColumnTypeName(i))));
        }
        st.close();
        rs.close();
    }

    /**
     * Metodo che restituisce il numero di colonne della tabella.
     * @return Numero di colonne della tabella.
     */
    public int getNumberOfAttributes() {
        return tableSchema.size();
    }

    /**
     * Metodo che restituisce la colonna della tabella
     * che si trova all'indice specificato.
     * @param index Indice della colonna.
     * @return Colonna della tabella indicata dall'indice.
     */
    public Column getColumn(final int index) {
        return tableSchema.get(index);
    }
}
