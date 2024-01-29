package com.example.tanosvenyek;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static final DbConnector connector = new DbConnector();
    private final VBox root = new VBox();
    private final SceneManager sceneManager = new SceneManager(root);


    @Override
    public void start(Stage stage) throws IOException {
        root.setMinHeight(450);
        root.setMinWidth(450);
        root.getChildren().add(sceneManager.generateMenu());
        root.getChildren().add(sceneManager.generateRead());

        Scene scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        stage.setTitle("Tanösvények DB editor by: Zsolesz");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}