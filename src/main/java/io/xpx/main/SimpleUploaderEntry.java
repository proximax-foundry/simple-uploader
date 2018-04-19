package io.xpx.main;

import java.io.IOException;

import io.xpx.uploader.app.process.ProximaXDaemonRunner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SimpleUploaderEntry extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/SplashLog.fxml"));
			Parent root;
			root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			runNode();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Run monitor tool
	}

	private void runNode() {
		new Thread(new ProximaXDaemonRunner()).start();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
