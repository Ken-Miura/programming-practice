/**
 * Copyright (C) 2016 Ken Miura
 */
package test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

// git の練習。test branchへコミット。
//　git の練習。test branchの削除。
// 修正1
/**
 * @author Ken
 *
 */
public class Test extends Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Label label = new Label("This is JavaFX!");
		BorderPane pane = new BorderPane();
		pane.setCenter(label);
		Scene scene = new Scene(pane, 320, 240);
		stage.setScene(scene);
		stage.show();
	}

}
