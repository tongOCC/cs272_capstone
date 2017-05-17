package edu.orangecoastcollege.cs272.p04.view;



import edu.orangecoastcollege.cs272.p04.controller.GSController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignUpScene {

	private static GSController controller = GSController.getInstance();

	@FXML
	private TextField nameTF;
	
	@FXML
	private TextField emailTF;
	@FXML
	private TextField passwordTF;

	@FXML
	private Label nameErrorLabel;
	@FXML
	private Label emailErrorLabel;
	@FXML
	private Label passwordErrorLabel;
	@FXML
	private Button confirm;
	
	@FXML
	private Button cancel;


	

	@FXML
	public Object confirmButton()
	{
		String name= nameTF.getText().toString();
		String email= emailTF.getText().toString();
		String password= passwordTF.getText().toString();
		String result=controller.signUpUser(name, email, password);
		if(!controller.isValidPassword(password)){
			passwordErrorLabel.setText("invalid password was submitted try again");
			return this;
		}
		if(!controller.isValidEmail(email))
		{
			emailErrorLabel.setText("Email is not a valid email");
			return this;
		}
		
		if(result.equalsIgnoreCase("SUCCESS"))
		    {
			controller.playMusic();
		    ViewNavigator.loadScene("WELCOME NEW PLAYER TO BS42",ViewNavigator.START_SCENE);

		    }
		else
		{
		    emailErrorLabel.setText(result);
		    
		    emailErrorLabel.setVisible(true);

		}
		
		return this;
		}
	
	@FXML
	public Object cancelButton()
	{
		ViewNavigator.loadScene("WELCOME TO BATTLESHIPS 1942", ViewNavigator.LOG_IN_SCENE);
		return this;
	}


}
