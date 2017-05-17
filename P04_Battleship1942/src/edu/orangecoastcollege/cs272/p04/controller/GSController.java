package edu.orangecoastcollege.cs272.p04.controller;
import java.io.Console;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;


import edu.orangecoastcollege.cs272.p04.view.ViewNavigator;
import edu.orangecoastcollegee.cs272.model.Captain;
import edu.orangecoastcollegee.cs272.model.DBModel;
import edu.orangecoastcollegee.cs272.model.GameStateModel;
import edu.orangecoastcollegee.cs272.model.Grid;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
/**
 * David Guido
 * Teven Ong
 * CS 272 ~ Java II
*  
 * P04_Battleship1942
 * @version May 1, 2017
* 
 * ~ Source of Game Research: "http://stackoverflow.com/questions/555283/how-can-you-organize-the-code-for-a-game-to-fit-the-mvc-pattern"
 ***
 * ~ This <code>GSController</code> Class, the 'Controller' layer is the glue between the two.
 *   It should never have any of the actual game logic in it, nor should it be responsible for driving the View layer. 
 * ~ Instead it should translate actions taken in the View layer (clicking on buttons, clicking on areas of the screen, 
 *   joystick actions, whatever) into actions taken on the model. 
 * ~ For example, dropping an item, attacking an NPC, whatever. 
 *   It is also responsible for gathering up data and doing any conversion or processing to make it easier for the View layer to display it.
*/
public final class GSController 
{	
	Scene mMainScene; 
	Stage mMainStage;
	private static final String DB_NAME = "GameUser.db";
	private static final String USER_TABLE_NAME = "user";
	private static final String[] USER_FIELD_NAMES = { "id", "name", "email", "password"};
	private static final String[] USER_FIELD_TYPES = { "INTEGER PRIMARY KEY", "TEXT", "TEXT", "TEXT"};
	
	private ObservableList<Captain> mAllUsersList;
	
	private static int highscore=0;
	
	private static GSController theGame;
	
	private static String musicFile="03-main-menu.mp3";
	private static String sfx="explosion.mp3";
	private static MediaPlayer backgroundMusic;
	private static MediaPlayer mediaSFX;
	
	private Captain mUserNameCap;
	private String mUserName;
	
	private GameStateModel GSM;
	private Button mOrientationButton = new Button("Rotate");
	private DBModel mUserDB;
	private Label mConsole= new Label("Welcome to 1942\nChoose Spawn Locations");
	
	private GSController() {}
	
	public static GSController getInstance() 
	{
		if (theGame == null) {
			theGame = new GSController();
	
		theGame.GSM = new GameStateModel();
		theGame.mAllUsersList = FXCollections.observableArrayList();
		
		try {
			theGame.mUserDB = new DBModel(DB_NAME, USER_TABLE_NAME, USER_FIELD_NAMES, USER_FIELD_TYPES);

			ResultSet rs = theGame.mUserDB.getAllRecords();
			for(Captain user: theGame.mAllUsersList)
			{
				System.out.println(user.toString());
			}
			while (rs.next()) {
				int id = rs.getInt(USER_FIELD_NAMES[0]);
				String name = rs.getString(USER_FIELD_NAMES[1]);
				String email = rs.getString(USER_FIELD_NAMES[2]);
				theGame.mAllUsersList.add(new Captain(id, name, email));
			}

		

		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
		return theGame;
	}

	public static int getHighscore() {
		return highscore;
	}


	public static void setHighscore(int highscore) {
		GSController.highscore = highscore;
	}



	public Captain getmUserNameCap() {
		return mUserNameCap;
	}


	public void setmUserNameCap(Captain mUserNameCap) {
		this.mUserNameCap = mUserNameCap;
	}


	public String getmUserName() {
		return mUserName;
	}


	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}

	public void setMainStage(Stage stage)
	{
		mMainStage = stage;
	}
	public Stage getMainStage()
	{
		return mMainStage;
	}
	public void setMainScene(Scene scene)
	{
		mMainScene = scene;
	}
	public Scene getMainScene()
	{
		return mMainScene;
	}
	public Scene startScreen() //not gonna use
	{
		GridPane gP = new GridPane();
       
		Button startButton = new Button("Start");	
			startButton.setPrefWidth(160);
			startButton.setPrefHeight(40);
		Button hScoresButton = new Button("High Scores");
		hScoresButton.setPrefWidth(160);
		hScoresButton.setPrefHeight(40);
		Button settingsButton = new Button("Settings");
		
		settingsButton.setPrefWidth(160);
		settingsButton.setPrefHeight(40);
		Button rulesButton = new Button("Rules");		
		rulesButton.setPrefWidth(160);
		rulesButton.setPrefHeight(40);
		Button aboutButton = new Button("About");
		aboutButton.setPrefWidth(160);
		aboutButton.setPrefHeight(40);
		Button versionsButton = new Button("Versions");
		versionsButton.setPrefWidth(160);
		versionsButton.setPrefHeight(40);
		Button exitButton = new Button("Exit");
		exitButton.setPrefWidth(160);
		exitButton.setPrefHeight(40);
		
		gP.setVgap(13);
		gP.setPadding(new Insets(50, 75, 50, 75));
        gP.add(startButton,  0, 0);
        gP.add(hScoresButton, 0, 1);
        gP.add(settingsButton, 0, 2);
        gP.add(rulesButton, 0, 3);
        gP.add(aboutButton, 0, 4);
        gP.add(versionsButton, 0, 5);
        gP.add(exitButton, 0, 6);
        gP.setAlignment(Pos.CENTER);  
        gP.setBackground(GSM.setStartBackground());
        startButton.setOnAction(e -> start());
        settingsButton.setOnMouseClicked(e -> startToSettings());
       /*
        hScoresButton.setOnAction(e -> clearAddCarScene());
        settingsButton.setOnAction((e -> clearAddCarScene()));
        rulesButton.setOnAction((e -> clearAddCarScene()));
        aboutButton.setOnAction((e -> clearAddCarScene()));
        versionsButton.setOnAction((e -> clearAddCarScene()));
        exitButton.setOnAction((e -> ));
          **/
        mMainScene = new Scene(gP);
      
		return mMainScene;
	}
	
	public Scene settingsMenu() // transplanting to scenebuilder
	{
		GridPane bPane = new GridPane();

		Slider sound = new Slider(0.0, 100, 50.0);
		sound.setPrefWidth(200);
		sound.setShowTickMarks(true);
		sound.setShowTickLabels(true);
		sound.setMajorTickUnit(5.0f);
		sound.setBlockIncrement(1.0f);
			
		Slider diff = new Slider(1.0, 3.0, 0.0);
		diff.setPrefWidth(200);
		diff.setShowTickMarks(true);
		diff.setShowTickLabels(true);
		diff.setBlockIncrement(1.0f);
		diff.setMajorTickUnit(1.0f);
		
		Button Exit = new Button("Exit");
		
		Exit.setOnMouseClicked(e -> startScreen());
		Exit.setAlignment(Pos.CENTER);
		Exit.setPrefWidth(160);
		Exit.setPrefHeight(40);
		bPane.setVgap(13);
		bPane.setPadding(new Insets(5, 5, 5, 5));
		
		bPane.add(new Label("Difficulty Level"), 0, 3);
		bPane.add(diff, 0, 4);
		bPane.add(new Label("Sound Volume:"), 0, 0);
		bPane.add(sound, 0, 1);
		bPane.add(Exit, 0, 6);
        bPane.setAlignment(Pos.CENTER);  
        bPane.setBackground(GSM.setStartBackground());
	   return new Scene(bPane) ; // ~ Send this scene to Start.
	}
	
	public void startToSettings()
	{
		theGame.mMainStage.setScene(theGame.settingsMenu());
        theGame.mMainStage.setHeight(300);
        theGame.mMainStage.setWidth(200);
        theGame.mMainStage.setTitle("Battleship Settings"+ "");
        theGame.mMainStage.show();
	}
	
	public void start()
	{
		theGame.mMainStage.setScene(theGame.gameLoop()); 
	       theGame.mMainStage.setHeight(905);
	       theGame.mMainStage.setWidth(708);
	       theGame.mMainStage.setTitle("Battleship 1942");
	       theGame.mMainStage.show();
	}
	   /**
	    * ~ From top to bottom visually:						   
	    *     [ Stage ~> Scene ~> BorderPane ~> VBox ~> Enemy Grid ~> Enemy Unit ]
	    *     								  ~> HBox ~> User Grid  ~> User Unit  ] 
	    * @return Scene representing a display of the main game loop.
	   */
	public Scene gameLoop() 
	{
		
			mOrientationButton.setOnMouseClicked(event -> GSM.changeOrientation());   // ~ Orientation Handler.
			GSM.setUserGrid(new Grid(true, event -> {
				
			if (GSM.setUserGridResponse(event))
				mConsole.setText("Choose attack position!");
			
			}));     
			
			// ~ User Grid Handler.
			GSM.setEnemyGrid(new Grid(false, event -> GSM.setEnemyGridResponse(event)));    // ~ Enemy Grid Handler.
			
			BorderPane bPane = new BorderPane();
		
		//	console.setDisable(true);
			mConsole.setPrefSize(145, 35);
			//console.setMaxSize(120, 100);
			mConsole.setPadding(new Insets(0,10,0,0));
			mConsole.setAlignment(Pos.CENTER);
			mConsole.setTextAlignment(TextAlignment.CENTER);
			//console.setFont(null);
		
			//console.setPrefHeight(50);
			//console.setPrefWidth(10);
			VBox eVBox = new VBox(50, GSM.getEnemyGrid(), mConsole); 
			mOrientationButton.setPrefHeight(35);
			// bPane.setLeft(mOrientationButton);
			mOrientationButton.setPrefWidth(85);
			
			mOrientationButton.getTransforms().add(new Rotate(5,0,0));
			HBox uHBox = new HBox(29, mOrientationButton, GSM.getUserGrid());
			uHBox.setAlignment(Pos.CENTER);
			
			eVBox.setAlignment(Pos.CENTER);
			eVBox.setPadding(new Insets(45,2,0,0));
			uHBox.setPadding(new Insets(17,0,154,40));
			//HBox uHBox = new HBox(30, GSM.getUserGrid());   
			//uHBox.setAlignment(Pos.BOTTOM_LEFT);  
			 //mOrientationButton.setAlignment(Pos.BASELINE_LEFT);

			bPane.setPrefHeight(900);
			bPane.setPrefWidth(728);  // ~ Window Size.
		    bPane.setTop(eVBox);
		   // bPane.setBottom(uVBox);
		    bPane.setLeft(uHBox);
			//bPane.setBottom(uHBox);
			bPane.setPadding(new Insets(0, 0, 0, 0));
				@SuppressWarnings("static-access")
			ImageView seal = new ImageView(GSM.getImage1());
			 seal.setFitHeight(150);
			 seal.setFitWidth(150);
			//bPane.setLeft(seal);
			//bPane.setBackground(
			bPane.setBackground(GSM.setBackgroundImage());
		
		   return new Scene(bPane) ; // ~ Send this scene to Start.
		// ~ Send this scene to Start.
	}
	public void playMusic()
	{
	      // For example

	 Media sound = new Media(new File(musicFile).toURI().toString());
	 backgroundMusic = new MediaPlayer(sound);
	 backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
	 backgroundMusic.play();
	}
	
	


	public static void stopMusic() {
		
		 backgroundMusic.stop();
		
	}


	public static void setBackgroundMusic(MediaPlayer backgroundMusic) {
		GSController.backgroundMusic = backgroundMusic;
	}
	public static void playExplosion()
	{
		Media sound = new Media(new File(sfx).toURI().toString());
		 mediaSFX = new MediaPlayer(sound);
	
		 mediaSFX.play();
	}
	public String signInUser(String email, String password) {
		for(Captain user: theGame.mAllUsersList)
			
	        if(user.getEmail().equalsIgnoreCase(email))
	        {
	            // query the database for password
	            try
                {
                    ResultSet results= theGame.mUserDB.getRecord(String.valueOf(user.getId()));
                    System.out.println(results.toString());
                    String storedPassword= results.getString(USER_FIELD_NAMES[3]);
                    if(password.equals(storedPassword))
                    {
                        theGame.mUserNameCap=user;
                        return "SUCCESS";
                    }
                    else
                        return "pass boom";
                }
                catch (SQLException e)
                {

                    e.printStackTrace();
                }
	        }
		return "Email and password combination invalid.  Please try again.";
}
	public String signUpUser(String name, String email, String password)
	{
		for(Captain user: theGame.mAllUsersList)
		{
		    if(user.getEmail().equalsIgnoreCase(email))
                return "email already exists please use different address.";
		}
		// add the user to the database (keep track of new ID generated
		String[] values= {name, email,password};
		// insert the user to the database
		try
        {
          int id= theGame.mUserDB.createRecord(Arrays.copyOfRange(USER_FIELD_NAMES,1,USER_FIELD_NAMES.length), values);
          // make the user for the record update
         Captain newUser= new Captain(id, name,email);
          theGame.mAllUsersList.add(newUser);
         theGame.mUserNameCap=newUser;
         System.out.println("success");
          return "SUCCESS";
        }
        catch (SQLException e)
        {
           return "Account not created. Please try again";
        }




	}
	public boolean isValidEmail(String email)
	{
		return email.matches(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	}
	public boolean isValidPassword(String password)
	{
		// Valid password must contain (see regex below):
		// At least one lower case letter
		// At least one digit
		// At least one special character (@, #, $, %, !)
		// At least one upper case letter
		// At least 8 characters long, but no more than 16
		return password.matches("((?=.*[a-z])(?=.*d)(?=.*[@#$%!])(?=.*[A-Z]).{8,16})");
	}
}