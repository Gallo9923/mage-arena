package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.GameManager;
import model.Score;
import model.User;

public class Main extends Application {

	public static final String SCORES_PATH = "data" + File.separator + "scores.sav";
	public static final String MODEL_PATH = "data" + File.separator + "model.sav";

	private GameManager gameManager;
	private GUIController controller;

	public Main() {

		loadModel();
		controller = new GUIController(gameManager, this);
		loadScore();
	}

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void 
	start(Stage primaryStage) throws Exception {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainPane.fxml"));
		fxmlLoader.setController(controller);
		BorderPane pane = fxmlLoader.load();

		FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("Login.fxml"));
		fxmlLoader2.setController(controller);
		StackPane stackPane = fxmlLoader2.load();

		pane.setCenter(stackPane);

		Scene scene = new Scene(pane);

		primaryStage.setTitle("Mage Arena");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				try {
					
					//TODO Add log
					if(gameManager.getSessionStart() != -1) {
						gameManager.addLog();
					}
				
					//TODO reset session start
					gameManager.resetSessionStart();
					
					
					serializeScore();
					serializeModel();
					
		
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

	}

	public void loadModel() {

		try {

			File model = new File(MODEL_PATH);

			if (model.exists()) {

				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(model));
				GameManager gameManager = (GameManager) ois.readObject();
				this.gameManager = gameManager;
				ois.close();
				
			}else {
				this.gameManager = new GameManager();
	
			}

		} catch (IOException e) {
			e.printStackTrace();
			
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}

	public void serializeModel() {
		try {
			
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(MODEL_PATH)));
			oos.writeObject(gameManager);
			oos.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			File file = new File(MODEL_PATH);
			file.delete();
		}
	}

	public void loadScore() {

		try {

			File file = new File(SCORES_PATH);

			if (file.exists()) {

				BufferedReader br = new BufferedReader(new FileReader(new File(SCORES_PATH)));

				String line = br.readLine();

				while (line != null) {

					String[] input = line.split(",");

					if (input.length == 4) {

						User user = controller.queryUser(input[0]);
						double score = Double.parseDouble(input[1]);
						long duration = Long.parseLong(input[2]);
						LocalDate date = LocalDate.parse(input[3]);

						gameManager.addScore(user, score, duration, date);

					}

					line = br.readLine();
				}

				br.close();

			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void serializeScore() throws FileNotFoundException {

		PrintWriter pw = new PrintWriter(new File(SCORES_PATH));

		ArrayList<Score> scoresAL = gameManager.getScores();

		for (int i = 0; i < scoresAL.size(); i++) {

			Score s = scoresAL.get(i);

			pw.print(s.getUser() + "," + s.getScore() + "," + s.getDuration() + "," + s.getDate());
			pw.print("\n");
		}

		pw.close();

	}

}
