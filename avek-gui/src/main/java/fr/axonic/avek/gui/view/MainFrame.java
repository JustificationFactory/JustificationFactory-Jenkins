package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.util.ConcurrentTaskManager;
import fr.axonic.avek.gui.util.Util;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MainFrame extends Application {
	private final static Logger logger = Logger.getLogger(MainFrame.class);

	private final BorderPane frame;

	public MainFrame() {
		frame = new BorderPane();
	}

	public void setView(AbstractView view) {
		frame.getChildren().clear();
		frame.setCenter(view);
	}



	//  //  //  //  //    Application MAIN    //  //  //  //  //

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		ConcurrentTaskManager ctm = new ConcurrentTaskManager();

		logger.debug("Loading Frame...");

		primaryStage.setTitle("#AVEK analyzer");
		GeneralizedView generalizedView = new GeneralizedView();

		Scene s = new Scene(frame, 800, 600);
		primaryStage.setScene(s);

		primaryStage.show();
		logger.debug("Frame created.");


		ctm.runLaterOnThread(() -> {
			try {
				Thread.sleep(2000);
				ctm.runLaterOnPlatform(() -> setView(generalizedView));
				Thread.sleep(2000);
				ctm.runLaterOnPlatform(() -> generalizedView.setMonitoredSystem(Util.getFileContent("files/subjectFile.json")));
				Thread.sleep(2000);
				ctm.runLaterOnPlatform(() -> generalizedView.setExperimentParameters(Util.getFileContent("files/parametersFile.json")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}