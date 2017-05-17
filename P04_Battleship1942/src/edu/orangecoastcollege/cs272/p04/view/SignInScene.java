package edu.orangecoastcollege.cs272.p04.view;


import edu.orangecoastcollege.cs272.p04.controller.GSController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignInScene {

	private static GSController controller = GSController.getInstance();

	@FXML
	private TextField emailAddressTF;
	@FXML
	private TextField passwordTF;
	@FXML
	private Label emailErrorLabel;
	@FXML
	private Label passwordErrorLabel;
	@FXML
	private Label signInErrorLabel;
	@FXML
	private Label signUpButton;


	@FXML
	public Object signIn() {


        String email= emailAddressTF.getText().toString();
        String password= passwordTF.getText().toString();
        
        String result=controller.signInUser(email, password);
        if(result.equals("SUCCESS"))
        {
        	controller.playMusic();
            ViewNavigator.loadScene("LOG IN SUCCESS WELCOME TO BS42", ViewNavigator.START_SCENE);
        }
        else
        {
            signInErrorLabel.setText(result);
            signInErrorLabel.setVisible(true);
        }
        return this;
	}

	@FXML
	public Object loadSignUp()
	{
	    ViewNavigator.loadScene("Sign UP", ViewNavigator.NEW_CAPTAIN_SCENE);
        return this;
	}

}
