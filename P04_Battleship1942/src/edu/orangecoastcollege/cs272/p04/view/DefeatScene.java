package edu.orangecoastcollege.cs272.p04.view;

import edu.orangecoastcollege.cs272.p04.controller.GSController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DefeatScene {
	private static GSController controller = GSController.getInstance();
	@FXML
	private Button continueButton;
	
	@FXML
	public Object startPressed()
	{
		controller.stopMusic();
		ViewNavigator.loadScene("Welcome to Battleships 1942", ViewNavigator.START_SCENE);
		controller.playMusic();
	
		return this;
	}
}
