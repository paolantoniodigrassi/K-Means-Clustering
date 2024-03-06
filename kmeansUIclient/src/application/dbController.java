package application;

import javafx.fxml.FXML;

import java.io.IOException;
import java.net.SocketException;

import client.ServerException;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Classe che controlla la pagina "db.fxml".
 */
public class dbController {
	
    /**
     * Campo di testo per inserire il numero di cluster.
     */
	 @FXML
	 private TextField nCluster;

    /**
     * Campo di testo per inserire il nome della tabella.
     */
    @FXML
    private TextField tabName;

    /**
     * Pulsante per avviare loadFromDB().
     */
    @FXML
    private Button cluster;
    
    /**
     * Pulsante per tornare indietro.
     */
    @FXML
    private Button back;

    /**
     * Area di testo dove visualizzare i risultati.
     */
    @FXML
    private TextArea result;

    /**
     * Inizializza result rendendolo non editabile.
     */
    @FXML
    public void initialize() {
    	result.setEditable(false);
    }

    /**
     * Metodo che attraverso Client esegue la clusterizzazione dal DB.
     */
    @FXML
    private void loadFromDB() {
    	Paging page = Paging.getInstance();
    	try {
    		if(tabName.getText().isEmpty() || nCluster.getText().isEmpty())
    			throw new ServerException("Inserisci dei valori");
			StartController.getClient().storeTableFromDb(tabName.getText());
			int k = Integer.parseInt(nCluster.getText());
			String res = StartController.getClient().learningFromDbTable(k);
			StartController.getClient().storeClusterInFile();
			result.setText(res + "File salvato");
    	} catch (SocketException e) {
    		page.showAlert(e.getMessage());
    	} catch (ClassNotFoundException e) {
    		page.showAlert(e.getMessage());
    	} catch (IOException e) {
    		page.showAlert(e.getMessage());
    	} catch (ServerException e) {
    		page.showAlert(e.getMessage());
    	} catch (NumberFormatException e) {
    		page.showAlert("Numero cluster non valido, scegliere un altro valore");
    	}
    	tabName.clear();
    	nCluster.clear();
    }

    /**
     * Metodo che richiama il caricamento alla pagina del menu.
     */
    @FXML
    private void backtoMenu() {
    	Paging page = Paging.getInstance();
		page.loadPage("Menu.fxml", "Menu", back);
    }

}
