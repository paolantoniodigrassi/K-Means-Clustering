package application;

import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Classe che contiene metodi per il caricamento delle pagine dell'UI.
 */
public class Paging {
	
	/**
	 * instanza della classe singleton
	 */
	private static Paging instance;
	
	/**
	 * costruttore privato
	 */
	private Paging() {
	}
	
	/**
	 * Metodoget per l'istanza
	 * @return instance del paging
	 */
	static Paging getInstance() {
		if (instance == null) {
			instance = new Paging();
		}
		return instance;
	}
	
	/**
	 * Metodo che carica e mostra la pagina principale.
	 * @param primaryStage finestra principale su cui caricare la pagina UI.
	 */
	void loadPage(Stage primaryStage) {
		try {
			Paging p = new Paging();
			Image icon = new Image("Icon.png");
			primaryStage.getIcons().add(icon);
			Parent root = FXMLLoader.load(p.getClass().getResource("Start.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("K-Means client");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(IOException e) {
			System.err.println("--- Errore nell'apertura di: Start.fxml ---");
		}
	}
	
	/**
	 * Metodo che carica e mostra le pagine secondarie a seconda della chiamata.
	 * @param fileName nome del file da caricare.
	 * @param title titolo della finestra.
	 * @param o oggetto su cui verrà applicato RTTI per ottenere le informazioni sul tipo di finestra.
	 */
	void loadPage(String fileName, String title, Object o) {
		try {
			Paging p = new Paging();
            FXMLLoader fxmlLoader = new FXMLLoader(p.getClass().getResource(fileName));
            Parent root = fxmlLoader.load();
            Scene newScene = new Scene(root);
            Stage stage;
            
            if (o instanceof Button) {
            	Button b = (Button) o;
            	stage = (Stage) b.getScene().getWindow();
            } else  {
            	ProgressBar pb = (ProgressBar) o;
            	stage = (Stage) pb.getScene().getWindow();
            }

            stage.setScene(newScene);
			stage.setTitle("K-Means client: " + title);
        } catch (IOException e) {
        	System.err.println("--- Errore nell'apertura di: " + fileName + " ---");
        }
	}

	/**
	 * Metodo che permette di visualizzare finestre di eccezione.
	 * @param errorMessage messaggio da visualizzare nella finestra.
	 */
    void showAlert(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
	}
}
