package edu.orangecoastcollege.cs272.p04.view;

import edu.orangecoastcollege.cs272.p04.controller.GSController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartScene {

private static GSController controller = GSController.getInstance();

@FXML
private Button StartButton;

@FXML
private Button ExitButton;
@FXML
private Button HighScoreButton;

@FXML
private Button aboutButton;
@FXML
private Button rulesButton;

@FXML
private Button VersionsButton;
@FXML
private Button settingsButton;

@FXML
public Object startPressed()
{
	
			controller.setMainScene(controller.gameLoop());
			controller.getMainStage().setScene(controller.getMainScene());
			controller.getMainStage().setResizable(false);
			
			controller.getMainStage().setTitle("BS42");
			controller.getMainStage().show();
	
	
	
	
	return this;
}
@FXML
public Object settingsPressed()
{
	ViewNavigator.loadScene("LOBBY", ViewNavigator.LOBBY_SCENE);
	return this;
}
@FXML
public Object rulesPressed()
{
	ViewNavigator.loadScene("LOBBY", ViewNavigator.LOBBY_SCENE);
	return this;
}
@FXML
public Object HighScorePressed()
{
	ViewNavigator.loadScene("HighScores", ViewNavigator.HIGHSCORE_SCENE);
	return this;
}
@FXML
public Object ExitPressed()
{
	
	System.exit(0);
	return this;
}
@FXML
public Object VersionsPressed()
{
	ViewNavigator.loadScene("LOBBY", ViewNavigator.LOBBY_SCENE);
	return this;
}
@FXML
public Object AboutPressed()
{
	ViewNavigator.loadScene("LOBBY", ViewNavigator.LOBBY_SCENE);
	return this;
}

}
