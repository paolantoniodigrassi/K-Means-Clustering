package application;

import client.serverInfo;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe principale di UIclient.
 */
public class Main extends Application {
	
	/**
	 * Metodo che richiama il caricamento della pagina principale.
	 */
	@Override
	public void start(Stage primaryStage) {
		Paging page = Paging.getInstance();
		page.loadPage(primaryStage);
	}

	/**
	 * Metodo main.
	 * @param args argomenti in input (ip e port).
	 */
	public static void main(String[] args) {
		if(args.length > 0) {
			serverInfo.setServerInfo(args[0], Integer.parseInt(args[2]));
		}
		launch(args);
	}
}
