package edu.orangecoastcollege.cs272.p04.view;

import java.io.File;

import edu.orangecoastcollege.cs272.p04.controller.GSController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class VictoryScene {
	private static GSController controller = GSController.getInstance();

	@FXML
	private Button continueButton;
	
	@FXML
	public Object startPressed()
	{
		GSController.stopMusic();
		ViewNavigator.loadScene("Welcome to Battleships 1942", ViewNavigator.START_SCENE);
		controller.playMusic();
	
		return this;
	}
}
