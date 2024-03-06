package data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Iterator;
import java.sql.SQLException;
import database.*;

/**
 * Classe concreta per modellare l'insieme di transazioni (o tuple).
 */
public class Data {

    /**
     * Lista di tipo Example dove ogni riga modella una transazione (o tupla).
     */
    private List < Example > data;

    /**
     * Cardinalità dell'insieme di transazioni (numero di righe in data).
     */
    private int numberOfExamples;

    /**
     * Lista di tipo Attribute che rappresenta gli attributi di ciascuna tupla.
     */
    private List < Attribute > attributeSet;

    /**
     * Costruttore della classe che inizializza 
     * l'insieme dei dati con tuple del database.
     * @param table Nome della tabella.
     * @throws DatabaseConnectionException Eccezione lanciata quando si verifica un errore durante la connessione al database.
     * @throws SQLException Eccezione lanciata quando si verifica un errore durante l'accesso al database.
     * @throws NoValueException Eccezione lanciata quando non è presente alcun valore.
     * @throws EmptySetException Eccezione lanciata quando la tabella è vuota.
     */
    public Data(final String table) throws DatabaseConnectionException, SQLException, NoValueException, EmptySetException {
        DbAccess db = new DbAccess();
        db.initConnection();
        TableData td = new TableData(db);
        TableSchema ts = new TableSchema(db, table);
        data = td.getDistinctTransazioni(table);
        numberOfExamples = data.size();
        attributeSet = new ArrayList < > ();
        double qMIN;
        double qMAX;
        for (int i = 0; i < ts.getNumberOfAttributes(); i++) {
            if (ts.getColumn(i).isNumber()) {
                qMIN = (double) td.getAggregateColumnValue(table, ts.getColumn(i), QUERY_TYPE.MIN);
                qMAX = (double) td.getAggregateColumnValue(table, ts.getColumn(i), QUERY_TYPE.MAX);
                attributeSet.add(new ContinuousAttribute(ts.getColumn(i).getColumnName(), i, qMIN, qMAX));
            } else {
                HashSet < Object > distValues = (HashSet < Object > ) td.getDistinctColumnValues(table, ts.getColumn(i));
                HashSet < String > values = new HashSet < > ();
                for (Object o: distValues)
                    values.add((String) o);
                attributeSet.add(new DiscreteAttribute(ts.getColumn(i).getColumnName(), i, values));
            }
        }
    }

    /**
     * Metodo che restituisce la cardinalità dell'insieme di transazioni.
     * @return Cardinalità dell'insieme di transazioni.
     */
    public int getNumberOfExamples() {
        return numberOfExamples;
    }

    /**
	 * Metodo che restituisce la dimensione dell'insieme degli attributi.
	 * @return Cardinalità dell'insieme di attributi.
	 */
    public int getNumberOfExplanatoryAttributes() {
        return attributeSet.size();
    }

    /**
     * Metodo che restituisce il valore dell'attributo alla colonna 
     * specificata in posizione attributeIndex e nella riga in posizione exampleIndex.
     * @param exampleIndex Indice di riga.
     * @param attributeIndex Indice di colonna.
     * @return Valore della matrice alla riga e colonna specificate.
     */
    public Object getAttributeValue(final int exampleIndex, final int attributeIndex) {
        return data.get(exampleIndex).get(attributeIndex);
    }

    /**
     * Metodo che restituisce l'attributo 
     * @param index Indice dell'attributo.
     * @return Attributo alla colonna specificata.
     */
    Attribute getAttribute(final int index) {
        return attributeSet.get(index);
    }

    /**
     * Metodo che crea una stringa in cui memorizza lo schema 
     * della tabella e le transazioni memorizzate in data, 
     * opportunamente enumerate. 
     * @return Stringa che modella lo stato dell'oggetto.
     */
    public String toString() {
        String s = new String(" ");
        for (Attribute i: attributeSet)
            s += i.getName() + " ";
        s += "\n";
        int i = 1;
        for (Example ex: data) {
            s += i + ": " + ex.toString() + "\n";
            i++;
        }
        return s;
    }

    /**
     * Metodo che crea e restituisce un oggetto di Tuple che modella 
     * come sequenza di coppie Attributo-valore la i-esima riga in data.
     * @param index Indice della riga.
     * @return Tupla che modella la riga specificata.
     */
    public Tuple getItemSet(final int index) {
        Tuple tuple = new Tuple(attributeSet.size());
        for (Attribute a: attributeSet) {
            if (a instanceof DiscreteAttribute)
                tuple.add(new DiscreteItem((DiscreteAttribute) a, (String) data.get(index).get(a.getIndex())), a.getIndex());
            else
                tuple.add(new ContinuousItem((ContinuousAttribute) a, (Double) data.get(index).get(a.getIndex())), a.getIndex());
        }
        return tuple;
    }

    /**
     * Genera un campione di indici casuali univoci da utilizzare come indici dei centroidi.
     * @param k numero dei centroidi da generare.
     * @return Array di k interi.
     * @throws OutOfRangeSampleSize Eccezione lanciata quando il valore inserito non è compreso tra 1 e il numero di righe in data.
     */
    public int[] sampling(final int k) throws OutOfRangeSampleSize {
        if (k < 1 || k > data.size()) throw new OutOfRangeSampleSize
        	("Valore inserito non valido: deve essere compreso tra 1 e " + data.size());
        int[] centroidIndexes = new int[k];
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i = 0; i < k; i++) {
            int c;
            boolean found = false;
            do {
                found = false;
                c = rand.nextInt(getNumberOfExamples());
                for (int j = 0; j < i; j++)
                    if (compare(centroidIndexes[j], c)) {
                        found = true;
                        break;
                    }
            } while (found);
            centroidIndexes[i] = c;
        }
        return centroidIndexes;
    }

    /**
     * Metodo che restituisce true se le tuple in posizione i e j
     * hanno gli stessi valori per tutti gli attributi, false altrimenti.
     * @param i indice della tupla da confrontare.
     * @param j indice della tupla da confrontare.
     * @return true se le tuple in posizione i e j hanno gli stessi 
     * valori per tutti gli attributi, false altrimenti.
     */
    private boolean compare(final int i, final int j) {
        for (Attribute a: attributeSet)
            if (!data.get(i).get(a.getIndex()).equals(data.get(j).get(a.getIndex())))
                return false;
        return true;
    }

    /**
     * Metodo che distingue attraverso RTTI il prototipo basato 
     * sull'insieme di identificatori e sull'attributo forniti.
     * @param idList Lista di interi che rappresenta gli indici di riga delle tuple.
     * @param attribute Attributo (discreto o continuo) di cui calcolare il centroide.
     * @return Valore centroide rispetto all'attributo specificato.
     */
    Object computePrototype(final Set < Integer > idList, final Attribute attribute) {
        if (attribute instanceof DiscreteAttribute)
            return computePrototype(idList, (DiscreteAttribute) attribute);
        else
            return computePrototype(idList, (ContinuousAttribute) attribute);
    }

    /**
     * Metodo che determina il valore prototipo più frequente per 
     * attribute nelle transazioni di data aventi indice di riga in idList.
     * @param idList Lista di interi che rappresenta gli indici di riga delle tuple.
     * @param attribute Attributo discreto di cui calcolare il centroide.
     * @return Valore centroide rispetto all'attributo specificato.
     */
    String computePrototype(final Set < Integer > idList, final DiscreteAttribute attribute) {
        Iterator < String > i = attribute.iterator();
        String prototype = i.next();
        int max = attribute.frequency(this, idList, prototype);
        int tmp;
        String temp;
        while (i.hasNext()) {
            temp = i.next();
            tmp = attribute.frequency(this, idList, temp);
            if (tmp > max) {
                max = tmp;
                prototype = temp;
            }
        }
        return prototype;
    }

    /**
     * Metodo che determina il valore prototipo come media dei valori osservati 
     * per attribute nelle transazioni di data aventi indice di riga in idList.
     * @param idList Lista di interi che rappresenta gli indici di riga delle tuple.
     * @param attribute Attributo continuo di cui calcolare il centroide.
     * @return Valore centroide rispetto all'attributo specificato.
     */
    double computePrototype(final Set < Integer > idList, final ContinuousAttribute attribute) {
        double sum = 0;
        for (int i: idList)
            sum += (Double) data.get(i).get(attribute.getIndex());
        return sum / idList.size();
    }
}