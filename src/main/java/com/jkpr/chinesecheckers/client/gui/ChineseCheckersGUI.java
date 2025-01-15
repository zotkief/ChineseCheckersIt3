package com.jkpr.chinesecheckers.client.gui;

import com.jkpr.chinesecheckers.client.boards.AbstractBoardClient;
import com.jkpr.chinesecheckers.client.Client;
import com.jkpr.chinesecheckers.client.gui.LoadingScreen;
import com.jkpr.chinesecheckers.client.gui.ScorePanel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ChineseCheckersGUI extends Application {
    private Client client;
    private BoardView boardView;
    private BorderPane root;
    private ScorePanel scorePanel;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) {
        client = new Client();

        root = new BorderPane();
        root.setCenter(new LoadingScreen());
        root.setStyle("-fx-background-color: #87CEEB;");

        scene = new Scene(root, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chinese Checkers - Oczekiwanie na planszę");
        primaryStage.show();

        client.setOnBoardGenerated(this::handleBoardGenerated);
        client.start();
        client.setOnBoardUpdate(() -> {
            Platform.runLater(() -> {
                boardView.update();
                scorePanel.refresh();
            });
        });
    }

    private void handleBoardGenerated(AbstractBoardClient board) {
        Platform.runLater(() -> {
            boardView = new BoardView(board, client);
            scorePanel = new ScorePanel(board, client);
            root.setTop(scorePanel);
            root.setCenter(boardView);
            root.setStyle("-fx-background-color: #87CEEB;");
            Stage stage = (Stage) scene.getWindow();
            stage.setTitle("Chinese Checkers - Gracz " + board.getId());
            scorePanel.refresh();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
