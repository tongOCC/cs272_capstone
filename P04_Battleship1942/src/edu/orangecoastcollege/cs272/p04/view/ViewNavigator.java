package edu.orangecoastcollege.cs272.p04.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewNavigator {
	public static final String NEW_CAPTAIN_SCENE = "newCaptainScene.fxml";
	public static final String ADD_NAME_FOR_THIS_SCENE_FGT = "SignInScene.fxml";
	public static final String HIGHSCORE_SCENE = "highScoreScene.fxml";
	public static final String LOBBY_SCENE = "LobbyScene.fxml";
	public static final String START_SCENE = "Start.fxml";
	public static final String VICTORY_SCENE = "Victory.fxml";
	public static final String DEFEAT_SCENE="Defeat.fxml";
	public static final String LOG_IN_SCENE="SignInScene.fxml";


	public static Stage mainStage;

	public static void setStage(Stage stage) {
		mainStage = stage;
	}

	public static void loadScene(String title, String sceneFXML) {

		try {
			mainStage.setTitle(title);
			Scene scene = new Scene(FXMLLoader.load(ViewNavigator.class.getResource(sceneFXML)));
			mainStage.setScene(scene);
			mainStage.show();
			
		} catch (IOException e) {
			System.err.println("Error loading: " + sceneFXML + "\n" + e.getMessage());
			e.printStackTrace();
			
		}
		
	}

}
